package net.minecraft.world.gen;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

public class MapGenRavine extends MapGenBase {
   private float[] field_75046_d = new float[1024];

   public void func_180707_a(long l, int i, int j, ChunkPrimer chunkPrimer, double d, double e, double d2, float f, float g, float h, int k, int m, double d3) {
      Random random = new Random(l);
      double d0 = (double)(i * 16 + 8);
      double d1 = (double)(j * 16 + 8);
      float f1 = 0.0F;
      float f1x = 0.0F;
      if (m <= 0) {
         int i1 = super.range * 16 - 16;
         m = i1 - random.nextInt(i1 / 4);
      }

      boolean flag2 = false;
      if (k == -1) {
         k = m / 2;
         flag2 = true;
      }

      float f2 = 1.0F;

      for (int j1 = 0; j1 < 256; j1++) {
         if (j1 == 0 || random.nextInt(3) == 0) {
            f2 = 1.0F + random.nextFloat() * random.nextFloat() * 1.0F;
         }

         this.field_75046_d[j1] = f2 * f2;
      }

      for (; k < m; k++) {
         double d10 = 1.5 + (double)(MathHelper.sin((float)k * (float) Math.PI / (float)m) * f * 1.0F);
         double d21 = d10 * d3;
         d10 *= (double)random.nextFloat() * 0.25 + 0.75;
         d21 *= (double)random.nextFloat() * 0.25 + 0.75;
         float f3 = MathHelper.cos(h);
         float f4 = MathHelper.sin(h);
         d += (double)(MathHelper.cos(g) * f3);
         e += (double)f4;
         double d9 = d2 + (double)(MathHelper.sin(g) * f3);
         d2 = d9;
         h *= 0.7F;
         h += f1x * 0.05F;
         g += f1 * 0.05F;
         f1x *= 0.8F;
         f1 *= 0.5F;
         f1x += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
         f1 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;
         if (flag2 || random.nextInt(4) != 0) {
            double d31 = d - d0;
            double d4 = d9 - d1;
            double d5 = (double)(m - k);
            double d6 = (double)(f + 2.0F + 16.0F);
            double d12;
            int j3 = (d12 = d31 * d31 + d4 * d4 - d5 * d5 - d6 * d6) == 0.0 ? 0 : (d12 < 0.0 ? -1 : 1);
            if (j3 > 0) {
               return;
            }

            double d13;
            int k3 = (d13 = d - (d0 - 16.0 - d10 * 2.0)) == 0.0 ? 0 : (d13 < 0.0 ? -1 : 1);
            if (k3 >= 0) {
               double d14;
               int l3 = (d14 = d9 - (d1 - 16.0 - d10 * 2.0)) == 0.0 ? 0 : (d14 < 0.0 ? -1 : 1);
               if (l3 >= 0) {
                  double d15;
                  int i4 = (d15 = d - (d0 + 16.0 + d10 * 2.0)) == 0.0 ? 0 : (d15 < 0.0 ? -1 : 1);
                  if (i4 <= 0) {
                     double d16;
                     int j4 = (d16 = d9 - (d1 + 16.0 + d10 * 2.0)) == 0.0 ? 0 : (d16 < 0.0 ? -1 : 1);
                     if (j4 <= 0) {
                        int k1 = MathHelper.floor_double(d - d10) - i * 16 - 1;
                        int l1 = MathHelper.floor_double(d + d10) - i * 16 + 1;
                        int i1 = MathHelper.floor_double(e - d21) - 1;
                        int j1 = MathHelper.floor_double(e + d21) + 1;
                        int k1x = MathHelper.floor_double(d9 - d10) - j * 16 - 1;
                        int l1x = MathHelper.floor_double(d9 + d10) - j * 16 + 1;
                        if (k1 < 0) {
                           k1 = 0;
                        }

                        if (l1 > 16) {
                           l1 = 16;
                        }

                        if (i1 < 1) {
                           i1 = 1;
                        }

                        if (j1 > 248) {
                           j1 = 248;
                        }

                        if (k1x < 0) {
                           k1x = 0;
                        }

                        if (l1x > 16) {
                           l1x = 16;
                        }

                        boolean flag = false;

                        for (int i2 = k1; !flag && i2 < l1; i2++) {
                           for (int j2 = k1x; !flag && j2 < l1x; j2++) {
                              for (int k2 = j1 + 1; !flag && k2 >= i1 - 1; k2--) {
                                 if (k2 >= 0 && k2 < 256) {
                                    IBlockState iblockstate = chunkPrimer.getBlockState(i2, k2, j2);
                                    if (iblockstate.getBlock() == Blocks.flowing_water || iblockstate.getBlock() == Blocks.water) {
                                       flag = true;
                                    }

                                    if (k2 != i1 - 1 && i2 != k1 && i2 != l1 - 1 && j2 != k1x && j2 != l1x - 1) {
                                       k2 = i1;
                                    }
                                 }
                              }
                           }
                        }

                        if (!flag) {
                           BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                           for (int k4 = k1; k4 < l1; k4++) {
                              double d11 = ((double)(k4 + i * 16) + 0.5 - d) / d10;

                              for (int l2 = k1x; l2 < l1x; l2++) {
                                 double d7 = ((double)(l2 + j * 16) + 0.5 - d2) / d10;
                                 boolean flag1 = false;
                                 if (d11 * d11 + d7 * d7 < 1.0) {
                                    for (int i3 = j1; i3 > i1; i3--) {
                                       double d8 = ((double)(i3 - 1) + 0.5 - e) / d21;
                                       if ((d11 * d11 + d7 * d7) * (double)this.field_75046_d[i3 - 1] + d8 * d8 / 6.0 < 1.0) {
                                          IBlockState iblockstate1 = chunkPrimer.getBlockState(k4, i3, l2);
                                          if (iblockstate1.getBlock() == Blocks.grass) {
                                             flag1 = true;
                                          }

                                          if (iblockstate1.getBlock() == Blocks.stone
                                             || iblockstate1.getBlock() == Blocks.dirt
                                             || iblockstate1.getBlock() == Blocks.grass) {
                                             if (i3 - 1 < 10) {
                                                chunkPrimer.setBlockState(k4, i3, l2, Blocks.flowing_lava.getDefaultState());
                                             } else {
                                                chunkPrimer.setBlockState(k4, i3, l2, Blocks.air.getDefaultState());
                                                if (flag1 && chunkPrimer.getBlockState(k4, i3 - 1, l2).getBlock() == Blocks.dirt) {
                                                   blockpos$mutableblockpos.set(k4 + i * 16, 0, l2 + j * 16);
                                                   chunkPrimer.setBlockState(
                                                      k4, i3 - 1, l2, super.worldObj.getBiomeGenForCoords(blockpos$mutableblockpos).topBlock
                                                   );
                                                }
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }

                           if (flag2) {
                              break;
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @Override
   public void recursiveGenerate(World worldIn, int chunkX, int chunkZ, int i, int j, ChunkPrimer chunkPrimerIn) {
      if (super.rand.nextInt(50) == 0) {
         double d0 = (double)(chunkX * 16 + super.rand.nextInt(16));
         double d1 = (double)(super.rand.nextInt(super.rand.nextInt(40) + 8) + 20);
         double d2 = (double)(chunkZ * 16 + super.rand.nextInt(16));

         for (int i1 = 0; i1 < 1; i1++) {
            float f = super.rand.nextFloat() * (float) Math.PI * 2.0F;
            float f1 = (super.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
            float f2 = (super.rand.nextFloat() * 2.0F + super.rand.nextFloat()) * 2.0F;
            this.func_180707_a(super.rand.nextLong(), i, j, chunkPrimerIn, d0, d1, d2, f2, f, f1, 0, 0, 3.0);
         }
      }
   }
}
