package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class WorldGenSavannaTree extends WorldGenAbstractTree {
   private static final IBlockState field_181643_a = Blocks.log2.getDefaultState().withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.ACACIA);
   private static final IBlockState field_181644_b = Blocks.leaves2
      .getDefaultState()
      .withProperty(BlockNewLeaf.VARIANT, BlockPlanks.EnumType.ACACIA)
      .withProperty(BlockLeaves.CHECK_DECAY, false);

   public WorldGenSavannaTree(boolean bl) {
      super(bl);
   }

   @Override
   public boolean generate(World worldIn, Random rand, BlockPos position) {
      int i = rand.nextInt(3) + rand.nextInt(3) + 5;
      boolean flag = true;
      if (position.getY() >= 1 && position.getY() + i + 1 <= 256) {
         for (int j = position.getY(); j <= position.getY() + 1 + i; j++) {
            int b0 = 1;
            if (j == position.getY()) {
               b0 = 0;
            }

            if (j >= position.getY() + 1 + i - 2) {
               b0 = 2;
            }

            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for (int k = position.getX() - b0; k <= position.getX() + b0 && flag; k++) {
               for (int l = position.getZ() - b0; l <= position.getZ() + b0 && flag; l++) {
                  if (j < 0 || j >= 256) {
                     flag = false;
                  } else if (!this.func_150523_a(worldIn.getBlockState(blockpos$mutableblockpos.set(k, j, l)).getBlock())) {
                     flag = false;
                  }
               }
            }
         }

         if (!flag) {
            return false;
         } else {
            Block block = worldIn.getBlockState(position.down()).getBlock();
            if ((block == Blocks.grass || block == Blocks.dirt) && position.getY() < 256 - i - 1) {
               this.func_175921_a(worldIn, position.down());
               EnumFacing enumfacing = EnumFacing.Plane.HORIZONTAL.random(rand);
               int k2 = i - rand.nextInt(4) - 1;
               int l2 = 3 - rand.nextInt(3);
               int i3 = position.getX();
               int i1 = position.getZ();
               int j1 = 0;

               for (int k1 = 0; k1 < i; k1++) {
                  int l1 = position.getY() + k1;
                  if (k1 >= k2 && l2 > 0) {
                     i3 += enumfacing.getFrontOffsetX();
                     i1 += enumfacing.getFrontOffsetZ();
                     l2--;
                  }

                  BlockPos blockpos = new BlockPos(i3, l1, i1);
                  Material material = worldIn.getBlockState(blockpos).getBlock().getMaterial();
                  if (material == Material.air || material == Material.leaves) {
                     this.R(worldIn, blockpos);
                     j1 = l1;
                  }
               }

               BlockPos blockpos2 = new BlockPos(i3, j1, i1);

               for (int j3 = -3; j3 <= 3; j3++) {
                  for (int l3 = -3; l3 <= 3; l3++) {
                     if (Math.abs(j3) != 3 || Math.abs(l3) != 3) {
                        this.P(worldIn, blockpos2.add(j3, 0, l3));
                     }
                  }
               }

               blockpos2 = blockpos2.up();

               for (int k3 = -1; k3 <= 1; k3++) {
                  for (int i4 = -1; i4 <= 1; i4++) {
                     this.P(worldIn, blockpos2.add(k3, 0, i4));
                  }
               }

               this.P(worldIn, blockpos2.east(2));
               this.P(worldIn, blockpos2.west(2));
               this.P(worldIn, blockpos2.south(2));
               this.P(worldIn, blockpos2.north(2));
               i3 = position.getX();
               i1 = position.getZ();
               EnumFacing enumfacing1 = EnumFacing.Plane.HORIZONTAL.random(rand);
               if (enumfacing1 != enumfacing) {
                  int j4 = k2 - rand.nextInt(2) - 1;
                  int k4 = 1 + rand.nextInt(3);
                  j1 = 0;

                  for (int i2 = j4; i2 < i && k4 > 0; k4--) {
                     if (i2 >= 1) {
                        int j2 = position.getY() + i2;
                        i3 += enumfacing1.getFrontOffsetX();
                        i1 += enumfacing1.getFrontOffsetZ();
                        BlockPos blockpos1 = new BlockPos(i3, j2, i1);
                        Material material1 = worldIn.getBlockState(blockpos1).getBlock().getMaterial();
                        if (material1 == Material.air || material1 == Material.leaves) {
                           this.R(worldIn, blockpos1);
                           j1 = j2;
                        }
                     }

                     i2++;
                  }

                  if (j1 > 0) {
                     BlockPos blockpos3 = new BlockPos(i3, j1, i1);

                     for (int l4 = -2; l4 <= 2; l4++) {
                        for (int j5 = -2; j5 <= 2; j5++) {
                           if (Math.abs(l4) != 2 || Math.abs(j5) != 2) {
                              this.P(worldIn, blockpos3.add(l4, 0, j5));
                           }
                        }
                     }

                     blockpos3 = blockpos3.up();

                     for (int i5 = -1; i5 <= 1; i5++) {
                        for (int k5 = -1; k5 <= 1; k5++) {
                           this.P(worldIn, blockpos3.add(i5, 0, k5));
                        }
                     }
                  }
               }

               return true;
            } else {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   private void R(World world, BlockPos blockPos) {
      this.setBlockAndNotifyAdequately(world, blockPos, field_181643_a);
   }

   private void P(World worldIn, BlockPos blockPos) {
      Material material = worldIn.getBlockState(blockPos).getBlock().getMaterial();
      if (material == Material.air || material == Material.leaves) {
         this.setBlockAndNotifyAdequately(worldIn, blockPos, field_181644_b);
      }
   }
}
