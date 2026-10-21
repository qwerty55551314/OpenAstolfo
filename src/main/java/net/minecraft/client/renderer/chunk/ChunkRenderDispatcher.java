package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RegionRenderCacheBuilder;
import net.minecraft.client.renderer.VertexBufferUploader;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.util.EnumWorldBlockLayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

public class ChunkRenderDispatcher {
   private static final Logger logger = LogManager.getLogger();
   private static final ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("Chunk Batcher %d").setDaemon(true).build();
   private final List<ChunkRenderWorker> listThreadedWorkers = Lists.newArrayList();
   private final BlockingQueue<ChunkCompileTaskGenerator> queueChunkUpdates;
   private final BlockingQueue<RegionRenderCacheBuilder> queueFreeRenderBuilders;
   private final WorldVertexBufferUploader worldVertexUploader;
   private final VertexBufferUploader vertexUploader;
   private final Queue<ListenableFutureTask<?>> queueChunkUploads;
   private final ChunkRenderWorker renderWorker;

   public ChunkRenderDispatcher() {
      String s = "PFxko";
      this.queueChunkUpdates = Queues.newArrayBlockingQueue(100);
      this.queueFreeRenderBuilders = Queues.newArrayBlockingQueue(5);
      this.worldVertexUploader = new WorldVertexBufferUploader();
      this.vertexUploader = new VertexBufferUploader();
      this.queueChunkUploads = Queues.newArrayDeque();

      for (int i = 0; i < 2; i++) {
         ChunkRenderWorker chunkrenderworker = new ChunkRenderWorker(this);
         Thread thread = threadFactory.newThread(chunkrenderworker);
         thread.start();
         this.listThreadedWorkers.add(chunkrenderworker);
      }

      for (int j = 0; j < 5; j++) {
         this.queueFreeRenderBuilders.add(new RegionRenderCacheBuilder());
      }

      this.renderWorker = new ChunkRenderWorker(this, new RegionRenderCacheBuilder());
   }

   public String getDebugInfo() {
      return String.format("pC: %03d, pU: %1d, aB: %1d", this.queueChunkUpdates.size(), this.queueChunkUploads.size(), this.queueFreeRenderBuilders.size());
   }

   public boolean runChunkUploads(long l) {
      boolean flag = false;

      long i;
      do {
         boolean flag1 = false;
         synchronized (this.queueChunkUploads) {
            if (!this.queueChunkUploads.isEmpty()) {
               this.queueChunkUploads.poll().run();
               flag1 = true;
               flag = true;
            }
         }

         if (l == 0L || !flag1) {
            break;
         }

         i = l - System.nanoTime();
      } while (i >= 0L);

      return flag;
   }

   public boolean updateChunkLater(RenderChunk chunkRenderer) {
      chunkRenderer.getLockCompileTask().lock();

      boolean flag;
      try {
         ChunkCompileTaskGenerator chunkcompiletaskgenerator = chunkRenderer.makeCompileTaskChunk();
         chunkcompiletaskgenerator.addFinishRunnable(new ChunkRenderDispatcher.RemoveChunkTask(this, chunkcompiletaskgenerator));
         boolean flag1 = this.queueChunkUpdates.offer(chunkcompiletaskgenerator);
         if (!flag1) {
            chunkcompiletaskgenerator.finish();
         }

         flag = flag1;
      } finally {
         chunkRenderer.getLockCompileTask().unlock();
      }

      return flag;
   }

   public boolean updateChunkNow(RenderChunk chunkRenderer) {
      chunkRenderer.getLockCompileTask().lock();

      try {
         ChunkCompileTaskGenerator chunkcompiletaskgenerator = chunkRenderer.makeCompileTaskChunk();

         try {
            this.renderWorker.processTask(chunkcompiletaskgenerator);
         } catch (InterruptedException interruptedexception) {
         }
      } finally {
         chunkRenderer.getLockCompileTask().unlock();
      }

      return true;
   }

   public void stopChunkUpdates() {
      this.clearChunkUpdates();

      while (this.runChunkUploads(0L)) {
      }

      ArrayList arraylist = Lists.newArrayList();

      while (arraylist.size() != 5) {
         try {
            arraylist.add(this.allocateRenderBuilder());
         } catch (InterruptedException interruptedexception) {
         }
      }

      this.queueFreeRenderBuilders.addAll(arraylist);
   }

   public void freeRenderBuilder(RegionRenderCacheBuilder regionRenderCacheBuilder) {
      this.queueFreeRenderBuilders.add(regionRenderCacheBuilder);
   }

   public RegionRenderCacheBuilder allocateRenderBuilder() throws InterruptedException {
      return this.queueFreeRenderBuilders.take();
   }

   public ChunkCompileTaskGenerator getNextChunkUpdate() throws InterruptedException {
      return this.queueChunkUpdates.take();
   }

   public boolean updateTransparencyLater(RenderChunk chunkRenderer) {
      chunkRenderer.getLockCompileTask().lock();

      boolean flag1;
      try {
         ChunkCompileTaskGenerator chunkcompiletaskgenerator = chunkRenderer.makeCompileTaskTransparency();
         if (chunkcompiletaskgenerator != null) {
            chunkcompiletaskgenerator.addFinishRunnable(new ChunkRenderDispatcher.RemoveTransparencyTask(this, chunkcompiletaskgenerator));
            return this.queueChunkUpdates.offer(chunkcompiletaskgenerator);
         }

         boolean flag = true;
         flag1 = flag;
      } finally {
         chunkRenderer.getLockCompileTask().unlock();
      }

      return flag1;
   }

   public ListenableFuture<Object> uploadChunk(
      final EnumWorldBlockLayer player, final WorldRenderer worldRenderer, final RenderChunk chunkRenderer, final CompiledChunk compiledChunkIn
   ) {
      if (Minecraft.getMinecraft().isCallingFromMinecraftThread()) {
         if (OpenGlHelper.useVbo()) {
            this.uploadVertexBuffer(worldRenderer, chunkRenderer.getVertexBufferByLayer(player.ordinal()));
         } else {
            this.uploadDisplayList(worldRenderer, ((ListedRenderChunk)chunkRenderer).getDisplayList(player, compiledChunkIn), chunkRenderer);
         }

         worldRenderer.setTranslation(0.0, 0.0, 0.0);
         return Futures.immediateFuture(null);
      } else {
         ListenableFutureTask listenablefuturetask = ListenableFutureTask.create(new Runnable() {
            @Override
            public void run() {
               ChunkRenderDispatcher.this.uploadChunk(player, worldRenderer, chunkRenderer, compiledChunkIn);
            }
         }, null);
         synchronized (this.queueChunkUploads) {
            this.queueChunkUploads.add(listenablefuturetask);
            return listenablefuturetask;
         }
      }
   }

   private void uploadDisplayList(WorldRenderer worldRenderer, int i, RenderChunk chunkRenderer) {
      GL11.glNewList(i, 4864);
      GlStateManager.pushMatrix();
      chunkRenderer.multModelviewMatrix();
      this.worldVertexUploader.draw(worldRenderer);
      GlStateManager.popMatrix();
      GL11.glEndList();
   }

   private void uploadVertexBuffer(WorldRenderer worldRenderer, VertexBuffer vertexBufferIn) {
      this.vertexUploader.setVertexBuffer(vertexBufferIn);
      this.vertexUploader.draw(worldRenderer);
   }

   public void clearChunkUpdates() {
      while (!this.queueChunkUpdates.isEmpty()) {
         ChunkCompileTaskGenerator chunkcompiletaskgenerator = this.queueChunkUpdates.poll();
         if (chunkcompiletaskgenerator != null) {
            chunkcompiletaskgenerator.finish();
         }
      }
   }

   public static BlockingQueue access$000(ChunkRenderDispatcher chunkRenderDispatcher) {
      return chunkRenderDispatcher.queueChunkUpdates;
   }

   public static class RemoveChunkTask implements Runnable {
      public final ChunkCompileTaskGenerator compileTask;
      public final ChunkRenderDispatcher dispatcher;

      public RemoveChunkTask(ChunkRenderDispatcher chunkRenderDispatcher, ChunkCompileTaskGenerator chunkCompileTaskGenerator) {
         this.dispatcher = chunkRenderDispatcher;
         this.compileTask = chunkCompileTaskGenerator;
      }

      @Override
      public void run() {
         ChunkRenderDispatcher.access$000(this.dispatcher).remove(this.compileTask);
      }
   }

   public static class RemoveTransparencyTask implements Runnable {
      public final ChunkCompileTaskGenerator compileTask;
      public final ChunkRenderDispatcher dispatcher;

      public RemoveTransparencyTask(ChunkRenderDispatcher chunkRenderDispatcher, ChunkCompileTaskGenerator chunkCompileTaskGenerator) {
         this.dispatcher = chunkRenderDispatcher;
         this.compileTask = chunkCompileTaskGenerator;
      }

      @Override
      public void run() {
         ChunkRenderDispatcher.access$000(this.dispatcher).remove(this.compileTask);
      }
   }
}
