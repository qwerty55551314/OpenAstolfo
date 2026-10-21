package net.minecraft.client.renderer;

import java.util.ArrayDeque;
import java.util.Arrays;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.src.Config;
import net.minecraft.src.DynamicLights;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3i;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class RegionRenderCache extends ChunkCache {
   private static final IBlockState DEFAULT_STATE = Blocks.air.getDefaultState();
   private final BlockPos position;
   private int[] combinedLights;
   private IBlockState[] blockStates;
   private static ArrayDeque<int[]> cacheLights = new ArrayDeque<>();
   private static ArrayDeque<IBlockState[]> cacheStates = new ArrayDeque<>();
   private static int maxCacheSize = Config.limit(Runtime.getRuntime().availableProcessors(), 1, 32);

   public RegionRenderCache(World worldIn, BlockPos posFromIn, BlockPos posToIn, int subIn) {
      super(worldIn, posFromIn, posToIn, subIn);
      this.position = posFromIn.subtract(new Vec3i(subIn, subIn, subIn));
      this.combinedLights = allocateLights(8000);
      Arrays.fill(this.combinedLights, -1);
      this.blockStates = allocateStates(8000);
   }

   @Override
   public TileEntity getTileEntity(BlockPos pos) {
      int i = (pos.getX() >> 4) - super.chunkX;
      int j = (pos.getZ() >> 4) - super.chunkZ;
      return super.chunkArray[i][j].getTileEntity(pos, Chunk.EnumCreateEntityType.QUEUED);
   }

   @Override
   public int getCombinedLight(BlockPos pos, int lightValue) {
      int i = this.getPositionIndex(pos);
      int j = this.combinedLights[i];
      if (j == -1) {
         j = super.getCombinedLight(pos, lightValue);
         if (Config.isDynamicLights() && !this.getBlockState(pos).getBlock().isOpaqueCube()) {
            j = DynamicLights.getCombinedLight(pos, j);
         }

         this.combinedLights[i] = j;
      }

      return j;
   }

   @Override
   public IBlockState getBlockState(BlockPos pos) {
      int i = this.getPositionIndex(pos);
      IBlockState iblockstate = this.blockStates[i];
      if (iblockstate == null) {
         iblockstate = this.getBlockStateRaw(pos);
         this.blockStates[i] = iblockstate;
      }

      return iblockstate;
   }

   private IBlockState getBlockStateRaw(BlockPos pos) {
      int k = pos.getY();
      if (k >= 0) {
         int l = pos.getY();
         if (l < 256) {
            int i = (pos.getX() >> 4) - super.chunkX;
            int j = (pos.getZ() >> 4) - super.chunkZ;
            return super.chunkArray[i][j].getBlockState(pos);
         }
      }

      return DEFAULT_STATE;
   }

   private int getPositionIndex(BlockPos blockPos) {
      int i = blockPos.getX() - this.position.getX();
      int j = blockPos.getY() - this.position.getY();
      int k = blockPos.getZ() - this.position.getZ();
      return i * 400 + k * 20 + j;
   }

   public void freeBuffers() {
      freeLights(this.combinedLights);
      freeStates(this.blockStates);
   }

   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static int[] allocateLights(int i) {
      boolean flag = false;
      ArrayDeque arraydeque = cacheLights;
      synchronized (cacheLights){} // $VF: monitorenter 

      try {
         int[] aint = cacheLights.pollLast();
         if (aint == null || aint.length < i) {
            aint = new int[i];
         }

         // $VF: monitorexit
         return aint;
      } finally {
         // $VF: monitorexit
      }
   }

   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void freeLights(int[] ints) {
      int b0 = 16;
      ArrayDeque arraydeque = cacheLights;
      synchronized (cacheLights){} // $VF: monitorenter 

      try {
         if (cacheLights.size() < maxCacheSize) {
            cacheLights.add(ints);
         }

         // $VF: monitorexit
      } finally {
         // $VF: monitorexit
      }
   }

   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static IBlockState[] allocateStates(int i) {
      boolean flag = false;
      ArrayDeque arraydeque = cacheStates;
      synchronized (cacheStates){} // $VF: monitorenter 

      try {
         IBlockState[] aiblockstate = cacheStates.pollLast();
         if (aiblockstate != null && aiblockstate.length >= i) {
            Arrays.fill(aiblockstate, null);
         } else {
            aiblockstate = new IBlockState[i];
         }

         // $VF: monitorexit
         return aiblockstate;
      } finally {
         // $VF: monitorexit
      }
   }

   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void freeStates(IBlockState[] iBlockStates) {
      boolean flag = false;
      ArrayDeque arraydeque = cacheStates;
      synchronized (cacheStates){} // $VF: monitorenter 

      try {
         if (cacheStates.size() < maxCacheSize) {
            cacheStates.add(iBlockStates);
         }

         // $VF: monitorexit
      } finally {
         // $VF: monitorexit
      }
   }
}
