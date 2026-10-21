package net.minecraft.world.gen;

import com.google.common.base.Objects;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockMycelium;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

public class MapGenCaves extends MapGenBase {
   public void func_180703_a(long l, int i, int j, ChunkPrimer chunkPrimer, double d, double e, double d2) {
      this.func_180702_a(l, i, j, chunkPrimer, d, e, d2, 1.0F + super.rand.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5);
   }

   public void func_180702_a(long l, int i, int j, ChunkPrimer chunkPrimer, double d, double e, double d2, float f, float g, float h, int k, int m, double d3) {
      double d0 = (double)(i * 16 + 8);
      double d1 = (double)(j * 16 + 8);
      float f1 = 0.0F;
      float f1x = 0.0F;
      Random random = new Random(l);
      if (m <= 0) {
         int i1 = super.range * 16 - 16;
         m = i1 - random.nextInt(i1 / 4);
      }

      boolean flag3 = false;
      if (k == -1) {
         k = m / 2;
         flag3 = true;
      }

      int j1 = random.nextInt(m / 2) + m / 4;
      int j3 = random.nextInt(6);

      for (boolean flag = j3 == 0; k < m; k++) {
         double d21 = 1.5 + (double)(MathHelper.sin((float)k * (float) Math.PI / (float)m) * f * 1.0F);
         double d31 = d21 * d3;
         float f2 = MathHelper.cos(h);
         float f3 = MathHelper.sin(h);
         d += (double)(MathHelper.cos(g) * f2);
         e += (double)f3;
         d2 += (double)(MathHelper.sin(g) * f2);
         if (flag) {
            h *= 0.92F;
         } else {
            h *= 0.7F;
         }

         h += f1x * 0.1F;
         g += f1 * 0.1F;
         f1x *= 0.9F;
         f1 *= 0.75F;
         f1x += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
         f1 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;
         if (!flag3 && k == j1 && f > 1.0F && m > 0) {
            this.func_180702_a(random.nextLong(), i, j, chunkPrimer, d, e, d2, random.nextFloat() * 0.5F + 0.5F, g - (float) (Math.PI / 2), h / 3.0F, k, m, 1.0);
            this.func_180702_a(random.nextLong(), i, j, chunkPrimer, d, e, d2, random.nextFloat() * 0.5F + 0.5F, g + (float) (Math.PI / 2), h / 3.0F, k, m, 1.0);
            return;
         }

         if (flag3 || random.nextInt(4) != 0) {
            double d4 = d - d0;
            double d5 = d2 - d1;
            double d6 = (double)(m - k);
            double d7 = (double)(f + 2.0F + 16.0F);
            double d11;
            int k3 = (d11 = d4 * d4 + d5 * d5 - d6 * d6 - d7 * d7) == 0.0 ? 0 : (d11 < 0.0 ? -1 : 1);
            if (k3 > 0) {
               return;
            }

            double d12;
            int l3 = (d12 = d - (d0 - 16.0 - d21 * 2.0)) == 0.0 ? 0 : (d12 < 0.0 ? -1 : 1);
            if (l3 >= 0) {
               double d13;
               int i4 = (d13 = d2 - (d1 - 16.0 - d21 * 2.0)) == 0.0 ? 0 : (d13 < 0.0 ? -1 : 1);
               if (i4 >= 0) {
                  double d14;
                  int j4 = (d14 = d - (d0 + 16.0 + d21 * 2.0)) == 0.0 ? 0 : (d14 < 0.0 ? -1 : 1);
                  if (j4 <= 0) {
                     double d15;
                     int k4 = (d15 = d2 - (d1 + 16.0 + d21 * 2.0)) == 0.0 ? 0 : (d15 < 0.0 ? -1 : 1);
                     if (k4 <= 0) {
                        int k1 = MathHelper.floor_double(d - d21) - i * 16 - 1;
                        int l1 = MathHelper.floor_double(d + d21) - i * 16 + 1;
                        int i1 = MathHelper.floor_double(e - d31) - 1;
                        int j1x = MathHelper.floor_double(e + d31) + 1;
                        int k1x = MathHelper.floor_double(d2 - d21) - j * 16 - 1;
                        int l1x = MathHelper.floor_double(d2 + d21) - j * 16 + 1;
                        if (k1 < 0) {
                           k1 = 0;
                        }

                        if (l1 > 16) {
                           l1 = 16;
                        }

                        if (i1 < 1) {
                           i1 = 1;
                        }

                        if (j1x > 248) {
                           j1x = 248;
                        }

                        if (k1x < 0) {
                           k1x = 0;
                        }

                        if (l1x > 16) {
                           l1x = 16;
                        }

                        boolean flag1 = false;

                        for (int i2 = k1; !flag1 && i2 < l1; i2++) {
                           for (int j2 = k1x; !flag1 && j2 < l1x; j2++) {
                              for (int k2 = j1x + 1; !flag1 && k2 >= i1 - 1; k2--) {
                                 if (k2 >= 0 && k2 < 256) {
                                    IBlockState iblockstate = chunkPrimer.getBlockState(i2, k2, j2);
                                    if (iblockstate.getBlock() == Blocks.flowing_water || iblockstate.getBlock() == Blocks.water) {
                                       flag1 = true;
                                    }

                                    if (k2 != i1 - 1 && i2 != k1 && i2 != l1 - 1 && j2 != k1x && j2 != l1x - 1) {
                                       k2 = i1;
                                    }
                                 }
                              }
                           }
                        }

                        if (!flag1) {
                           BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                           for (int l4 = k1; l4 < l1; l4++) {
                              double d10 = ((double)(l4 + i * 16) + 0.5 - d) / d21;

                              for (int l2 = k1x; l2 < l1x; l2++) {
                                 double d8 = ((double)(l2 + j * 16) + 0.5 - d2) / d21;
                                 boolean flag2 = false;
                                 if (d10 * d10 + d8 * d8 < 1.0) {
                                    for (int i3 = j1x; i3 > i1; i3--) {
                                       double d9 = ((double)(i3 - 1) + 0.5 - e) / d31;
                                       if (d9 > -0.7 && d10 * d10 + d9 * d9 + d8 * d8 < 1.0) {
                                          IBlockState iblockstate1 = chunkPrimer.getBlockState(l4, i3, l2);
                                          IBlockState iblockstate2 = Objects.firstNonNull(
                                             chunkPrimer.getBlockState(l4, i3 + 1, l2), Blocks.air.getDefaultState()
                                          );
                                          if (iblockstate1.getBlock() == Blocks.grass || iblockstate1.getBlock() == Blocks.mycelium) {
                                             flag2 = true;
                                          }

                                          if (this.func_175793_a(iblockstate1, iblockstate2)) {
                                             if (i3 - 1 < 10) {
                                                chunkPrimer.setBlockState(l4, i3, l2, Blocks.lava.getDefaultState());
                                             } else {
                                                chunkPrimer.setBlockState(l4, i3, l2, Blocks.air.getDefaultState());
                                                if (iblockstate2.getBlock() == Blocks.sand) {
                                                   chunkPrimer.setBlockState(
                                                      l4,
                                                      i3 + 1,
                                                      l2,
                                                      iblockstate2.getValue(BlockSand.VARIANT) == BlockSand.EnumType.RED_SAND
                                                         ? Blocks.red_sandstone.getDefaultState()
                                                         : Blocks.sandstone.getDefaultState()
                                                   );
                                                }

                                                if (flag2 && chunkPrimer.getBlockState(l4, i3 - 1, l2).getBlock() == Blocks.dirt) {
                                                   blockpos$mutableblockpos.set(l4 + i * 16, 0, l2 + j * 16);
                                                   chunkPrimer.setBlockState(
                                                      l4,
                                                      i3 - 1,
                                                      l2,
                                                      super.worldObj.getBiomeGenForCoords(blockpos$mutableblockpos).topBlock.getBlock().getDefaultState()
                                                   );
                                                }
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }

                           if (flag3) {
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

   public boolean func_175793_a(IBlockState iBlockState, IBlockState iBlockState2) {
      Block block11 = iBlockState.getBlock();
      Block block1 = Blocks.stone;
      Block block = block11;
      boolean flag;
      if (block == block1) {
         flag = true;
      } else if (iBlockState.getBlock() == Blocks.dirt) {
         flag = true;
      } else {
         block11 = iBlockState.getBlock();
         BlockGrass blockgrass = Blocks.grass;
         Block block2 = block11;
         if (block2 == blockgrass) {
            flag = true;
         } else {
            block11 = iBlockState.getBlock();
            Block block4 = Blocks.hardened_clay;
            Block block3 = block11;
            if (block3 == block4) {
               flag = true;
            } else {
               block11 = iBlockState.getBlock();
               Block block6 = Blocks.stained_hardened_clay;
               Block block5 = block11;
               if (block5 == block6) {
                  flag = true;
               } else {
                  block11 = iBlockState.getBlock();
                  Block block8 = Blocks.sandstone;
                  Block block7 = block11;
                  if (block7 == block8) {
                     flag = true;
                  } else if (iBlockState.getBlock() == Blocks.red_sandstone) {
                     flag = true;
                  } else {
                     block11 = iBlockState.getBlock();
                     BlockMycelium blockmycelium = Blocks.mycelium;
                     Block block9 = block11;
                     if (block9 == blockmycelium) {
                        flag = true;
                     } else if (iBlockState.getBlock() == Blocks.snow_layer) {
                        flag = true;
                     } else {
                        Block block10 = iBlockState.getBlock();
                        flag = (block10 == Blocks.sand || iBlockState.getBlock() == Blocks.gravel) && iBlockState2.getBlock().getMaterial() != Material.water;
                     }
                  }
               }
            }
         }
      }

      return flag;
   }

   @Override
   public void recursiveGenerate(World worldIn, int chunkX, int chunkZ, int i, int j, ChunkPrimer chunkPrimerIn) {
      int i1 = super.rand.nextInt(super.rand.nextInt(super.rand.nextInt(15) + 1) + 1);
      int i1x = super.rand.nextInt(7);
      if (i1x != 0) {
         i1 = 0;
      }

      for (int j1 = 0; j1 < i1; j1++) {
         double d0 = (double)(chunkX * 16 + super.rand.nextInt(16));
         double d1 = (double)super.rand.nextInt(super.rand.nextInt(120) + 8);
         double d2 = (double)(chunkZ * 16 + super.rand.nextInt(16));
         int k = 1;
         int j1x = super.rand.nextInt(4);
         if (j1x == 0) {
            this.func_180703_a(super.rand.nextLong(), i, j, chunkPrimerIn, d0, d1, d2);
            k += super.rand.nextInt(4);
         }

         for (int l = 0; l < k; l++) {
            float f = super.rand.nextFloat() * (float) Math.PI * 2.0F;
            float f1 = (super.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
            float f2 = super.rand.nextFloat() * 2.0F + super.rand.nextFloat();
            if (super.rand.nextInt(10) == 0) {
               f2 *= super.rand.nextFloat() * super.rand.nextFloat() * 3.0F + 1.0F;
            }

            this.func_180702_a(super.rand.nextLong(), i, j, chunkPrimerIn, d0, d1, d2, f2, f, f1, 0, 0, 1.0);
         }
      }
   }
}
