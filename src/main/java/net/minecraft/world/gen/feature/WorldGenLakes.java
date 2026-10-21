package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class WorldGenLakes extends WorldGenerator {
   private Block block;

   public WorldGenLakes(Block blockIn) {
      this.block = blockIn;
   }

   @Override
   public boolean generate(World worldIn, Random rand, BlockPos position) {
      for (position = position.add(-8, 0, -8); position.getY() > 5; position = position.down()) {
         boolean flag = worldIn.isAirBlock(position);
         if (!flag) {
            break;
         }
      }

      int k1 = position.getY();
      if (k1 <= 4) {
         return false;
      } else {
         position = position.down(4);
         boolean[] aboolean = new boolean[2048];
         int i = rand.nextInt(4) + 4;

         for (int j = 0; j < i; j++) {
            double d0 = rand.nextDouble() * 6.0 + 3.0;
            double d1 = rand.nextDouble() * 4.0 + 2.0;
            double d2 = rand.nextDouble() * 6.0 + 3.0;
            double d3 = rand.nextDouble() * (16.0 - d0 - 2.0) + 1.0 + d0 / 2.0;
            double d4 = rand.nextDouble() * (8.0 - d1 - 4.0) + 2.0 + d1 / 2.0;
            double d5 = rand.nextDouble() * (16.0 - d2 - 2.0) + 1.0 + d2 / 2.0;

            for (int l = 1; l < 15; l++) {
               for (int i1 = 1; i1 < 15; i1++) {
                  for (int j1 = 1; j1 < 7; j1++) {
                     double d6 = ((double)l - d3) / (d0 / 2.0);
                     double d7 = ((double)j1 - d4) / (d1 / 2.0);
                     double d8 = ((double)i1 - d5) / (d2 / 2.0);
                     double d9 = d6 * d6 + d7 * d7 + d8 * d8;
                     if (d9 < 1.0) {
                        aboolean[(l * 16 + i1) * 8 + j1] = true;
                     }
                  }
               }
            }
         }

         for (int l1 = 0; l1 < 16; l1++) {
            for (int i3 = 0; i3 < 16; i3++) {
               for (int k = 0; k < 8; k++) {
                  boolean flag1 = !aboolean[(l1 * 16 + i3) * 8 + k]
                     && (
                        l1 < 15 && aboolean[((l1 + 1) * 16 + i3) * 8 + k]
                           || l1 > 0 && aboolean[((l1 - 1) * 16 + i3) * 8 + k]
                           || i3 < 15 && aboolean[(l1 * 16 + i3 + 1) * 8 + k]
                           || i3 > 0 && aboolean[(l1 * 16 + (i3 - 1)) * 8 + k]
                           || k < 7 && aboolean[(l1 * 16 + i3) * 8 + k + 1]
                           || k > 0 && aboolean[(l1 * 16 + i3) * 8 + (k - 1)]
                     );
                  if (flag1) {
                     Material material = worldIn.getBlockState(position.add(l1, k, i3)).getBlock().getMaterial();
                     if (k >= 4 && material.isLiquid()) {
                        return false;
                     }

                     if (k < 4 && !material.isSolid() && worldIn.getBlockState(position.add(l1, k, i3)).getBlock() != this.block) {
                        return false;
                     }
                  }
               }
            }
         }

         for (int i2 = 0; i2 < 16; i2++) {
            for (int j3 = 0; j3 < 16; j3++) {
               for (int j4 = 0; j4 < 8; j4++) {
                  if (aboolean[(i2 * 16 + j3) * 8 + j4]) {
                     worldIn.setBlockState(position.add(i2, j4, j3), j4 >= 4 ? Blocks.air.getDefaultState() : this.block.getDefaultState(), 2);
                  }
               }
            }
         }

         for (int j2 = 0; j2 < 16; j2++) {
            for (int k3 = 0; k3 < 16; k3++) {
               for (int k4 = 4; k4 < 8; k4++) {
                  if (aboolean[(j2 * 16 + k3) * 8 + k4]) {
                     BlockPos blockpos = position.add(j2, k4 - 1, k3);
                     if (worldIn.getBlockState(blockpos).getBlock() == Blocks.dirt && worldIn.getLightFor(EnumSkyBlock.SKY, position.add(j2, k4, k3)) > 0) {
                        BiomeGenBase biomegenbase = worldIn.getBiomeGenForCoords(blockpos);
                        if (biomegenbase.topBlock.getBlock() == Blocks.mycelium) {
                           worldIn.setBlockState(blockpos, Blocks.mycelium.getDefaultState(), 2);
                        } else {
                           worldIn.setBlockState(blockpos, Blocks.grass.getDefaultState(), 2);
                        }
                     }
                  }
               }
            }
         }

         Material material3 = this.block.getMaterial();
         Material material2 = Material.lava;
         Material material1 = material3;
         if (material1 == material2) {
            for (int k2 = 0; k2 < 16; k2++) {
               for (int l3 = 0; l3 < 16; l3++) {
                  for (int l4 = 0; l4 < 8; l4++) {
                     boolean flag2 = !aboolean[(k2 * 16 + l3) * 8 + l4]
                        && (
                           k2 < 15 && aboolean[((k2 + 1) * 16 + l3) * 8 + l4]
                              || k2 > 0 && aboolean[((k2 - 1) * 16 + l3) * 8 + l4]
                              || l3 < 15 && aboolean[(k2 * 16 + l3 + 1) * 8 + l4]
                              || l3 > 0 && aboolean[(k2 * 16 + (l3 - 1)) * 8 + l4]
                              || l4 < 7 && aboolean[(k2 * 16 + l3) * 8 + l4 + 1]
                              || l4 > 0 && aboolean[(k2 * 16 + l3) * 8 + (l4 - 1)]
                        );
                     if (flag2 && (l4 < 4 || rand.nextInt(2) != 0) && worldIn.getBlockState(position.add(k2, l4, l3)).getBlock().getMaterial().isSolid()) {
                        worldIn.setBlockState(position.add(k2, l4, l3), Blocks.stone.getDefaultState(), 2);
                     }
                  }
               }
            }
         }

         if (this.block.getMaterial() == Material.water) {
            for (int l2 = 0; l2 < 16; l2++) {
               for (int i4 = 0; i4 < 16; i4++) {
                  int b0 = 4;
                  if (worldIn.canBlockFreezeWater(position.add(l2, b0, i4))) {
                     worldIn.setBlockState(position.add(l2, b0, i4), Blocks.ice.getDefaultState(), 2);
                  }
               }
            }
         }

         return true;
      }
   }
}
