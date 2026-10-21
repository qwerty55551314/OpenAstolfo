package net.minecraft.src;

import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.world.chunk.IChunkProvider;

public class ClearWater {
   public static void updateWaterOpacity(GameSettings settings, World world) {
      if (settings != null) {
         int b0 = 3;
         if (settings.ofClearWater) {
            b0 = 1;
         }

         BlockLeavesBase.setLightOpacity(Blocks.water, b0);
         BlockLeavesBase.setLightOpacity(Blocks.flowing_water, b0);
      }

      if (world != null) {
         IChunkProvider ichunkprovider = world.getChunkProvider();
         if (ichunkprovider != null) {
            Entity entity = Config.getMinecraft().getRenderViewEntity();
            if (entity != null) {
               int i = (int)entity.posX / 16;
               int j = (int)entity.posZ / 16;
               int k = i - 512;
               int l = i + 512;
               int i1 = j - 512;
               int j1 = j + 512;
               int k1 = 0;

               for (int l1 = k; l1 < l; l1++) {
                  for (int i2 = i1; i2 < j1; i2++) {
                     if (ichunkprovider.chunkExists(l1, i2)) {
                        Chunk chunk = ichunkprovider.provideChunk(l1, i2);
                        if (chunk != null && !(chunk instanceof EmptyChunk)) {
                           int j2 = l1 << 4;
                           int k2 = i2 << 4;
                           int l2 = j2 + 16;
                           int i3 = k2 + 16;
                           BlockPosM blockposm = new BlockPosM(0, 0, 0);
                           BlockPosM blockposm1 = new BlockPosM(0, 0, 0);

                           for (int j3 = j2; j3 < l2; j3++) {
                              for (int k3 = k2; k3 < i3; k3++) {
                                 blockposm.setXyz(j3, 0, k3);
                                 BlockPos blockpos = world.getPrecipitationHeight(blockposm);

                                 for (int l3 = 0; l3 < blockpos.getY(); l3++) {
                                    blockposm1.setXyz(j3, l3, k3);
                                    IBlockState iblockstate = world.getBlockState(blockposm1);
                                    if (iblockstate.getBlock().getMaterial() == Material.water) {
                                       world.markBlocksDirtyVertical(j3, k3, blockposm1.getY(), blockpos.getY());
                                       k1++;
                                       break;
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }

               if (k1 > 0) {
                  String s = "server";
                  if (Config.isMinecraftThread()) {
                     s = "client";
                  }

                  Config.dbg("ClearWater (" + s + ") relighted " + k1 + " chunks");
               }
            }
         }
      }
   }
}
