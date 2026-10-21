package shadersmod.client;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.ViewFrustum;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class ShadowUtils {
   private static final long b = 446684746336960528L;

   public static Iterator<RenderChunk> makeShadowChunkIterator(
      WorldClient world, double partialTicks, Entity viewEntity, int renderDistanceChunks, ViewFrustum viewFrustum
   ) {
      float f = Shaders.getShadowRenderDistance();
      float f7;
      int i = (f7 = f - 0.0F) == 0.0F ? 0 : (f7 < 0.0F ? -1 : 1);
      if (i > 0) {
         float f8;
         int j = (f8 = f - (float)((renderDistanceChunks - 1) * (int)b)) == 0.0F ? 0 : (f8 < 0.0F ? -1 : 1);
         if (j < 0) {
            int l = MathHelper.ceiling_float_int(f / 16.0F) + 1;
            float f1 = world.getCelestialAngleRadians((float)partialTicks);
            float f2 = Shaders.sunPathRotation * (float) (Math.PI / 180.0);
            float f9;
            int k = (f9 = f1 - (float) (Math.PI / 2)) == 0.0F ? 0 : (f9 < 0.0F ? -1 : 1);
            float f3 = k > 0 && f1 < (float) (Math.PI * 3.0 / 2.0) ? f1 + (float) Math.PI : f1;
            float f4 = -MathHelper.sin(f3);
            float f5 = MathHelper.cos(f3) * MathHelper.cos(f2);
            float f6 = -MathHelper.cos(f3) * MathHelper.sin(f2);
            BlockPos blockpos = new BlockPos(
               MathHelper.floor_double(viewEntity.posX) >> 4, MathHelper.floor_double(viewEntity.posY) >> 4, MathHelper.floor_double(viewEntity.posZ) >> 4
            );
            BlockPos blockpos1 = blockpos.add((double)(-f4 * (float)l), (double)(-f5 * (float)l), (double)(-f6 * (float)l));
            BlockPos blockpos2 = blockpos.add(
               (double)(f4 * (float)renderDistanceChunks), (double)(f5 * (float)renderDistanceChunks), (double)(f6 * (float)renderDistanceChunks)
            );
            return new IteratorRenderChunks(viewFrustum, blockpos1, blockpos2, l, l);
         }
      }

      List list = Arrays.asList(viewFrustum.renderChunks);
      return list.iterator();
   }
}
