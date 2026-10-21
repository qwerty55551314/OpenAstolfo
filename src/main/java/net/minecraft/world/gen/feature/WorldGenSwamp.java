package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenSwamp extends WorldGenAbstractTree {
   private static final IBlockState field_181648_a = Blocks.log.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.OAK);
   private static final IBlockState field_181649_b = Blocks.leaves
      .getDefaultState()
      .withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.OAK)
      .withProperty(BlockLeaves.CHECK_DECAY, false);

   public WorldGenSwamp() {
      super(false);
   }

   @Override
   public boolean generate(World worldIn, Random rand, BlockPos position) {
      int i = rand.nextInt(4) + 5;

      while (worldIn.getBlockState(position.down()).getBlock().getMaterial() == Material.water) {
         position = position.down();
      }

      boolean flag = true;
      if (position.getY() >= 1 && position.getY() + i + 1 <= 256) {
         for (int j = position.getY(); j <= position.getY() + 1 + i; j++) {
            int b0 = 1;
            if (j == position.getY()) {
               b0 = 0;
            }

            if (j >= position.getY() + 1 + i - 2) {
               b0 = 3;
            }

            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for (int k = position.getX() - b0; k <= position.getX() + b0 && flag; k++) {
               for (int l = position.getZ() - b0; l <= position.getZ() + b0 && flag; l++) {
                  if (j >= 0 && j < 256) {
                     Block block = worldIn.getBlockState(blockpos$mutableblockpos.set(k, j, l)).getBlock();
                     if (block.getMaterial() != Material.air && block.getMaterial() != Material.leaves) {
                        if (block != Blocks.water && block != Blocks.flowing_water) {
                           flag = false;
                        } else if (j > position.getY()) {
                           flag = false;
                        }
                     }
                  } else {
                     flag = false;
                  }
               }
            }
         }

         if (!flag) {
            return false;
         } else {
            Block block1 = worldIn.getBlockState(position.down()).getBlock();
            if ((block1 == Blocks.grass || block1 == Blocks.dirt) && position.getY() < 256 - i - 1) {
               this.func_175921_a(worldIn, position.down());

               for (int k1 = position.getY() - 3 + i; k1 <= position.getY() + i; k1++) {
                  int j2 = k1 - (position.getY() + i);
                  int l2 = 2 - j2 / 2;

                  for (int j3 = position.getX() - l2; j3 <= position.getX() + l2; j3++) {
                     int k3 = j3 - position.getX();

                     for (int i1 = position.getZ() - l2; i1 <= position.getZ() + l2; i1++) {
                        int j1 = i1 - position.getZ();
                        if (Math.abs(k3) != l2 || Math.abs(j1) != l2 || rand.nextInt(2) != 0 && j2 != 0) {
                           BlockPos blockpos = new BlockPos(j3, k1, i1);
                           if (!worldIn.getBlockState(blockpos).getBlock().isFullBlock()) {
                              this.setBlockAndNotifyAdequately(worldIn, blockpos, field_181649_b);
                           }
                        }
                     }
                  }
               }

               for (int l1 = 0; l1 < i; l1++) {
                  Block block2 = worldIn.getBlockState(position.up(l1)).getBlock();
                  if (block2.getMaterial() == Material.air
                     || block2.getMaterial() == Material.leaves
                     || block2 == Blocks.flowing_water
                     || block2 == Blocks.water) {
                     this.setBlockAndNotifyAdequately(worldIn, position.up(l1), field_181648_a);
                  }
               }

               for (int i2 = position.getY() - 3 + i; i2 <= position.getY() + i; i2++) {
                  int k2 = i2 - (position.getY() + i);
                  int i3 = 2 - k2 / 2;
                  BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();

                  for (int l3 = position.getX() - i3; l3 <= position.getX() + i3; l3++) {
                     for (int i4 = position.getZ() - i3; i4 <= position.getZ() + i3; i4++) {
                        blockpos$mutableblockpos1.set(l3, i2, i4);
                        if (worldIn.getBlockState(blockpos$mutableblockpos1).getBlock().getMaterial() == Material.leaves) {
                           BlockPos blockpos3 = blockpos$mutableblockpos1.west();
                           BlockPos blockpos4 = blockpos$mutableblockpos1.east();
                           BlockPos blockpos1 = blockpos$mutableblockpos1.north();
                           BlockPos blockpos2 = blockpos$mutableblockpos1.south();
                           if (rand.nextInt(4) == 0 && worldIn.getBlockState(blockpos3).getBlock().getMaterial() == Material.air) {
                              this.func_181647_a(worldIn, blockpos3, BlockVine.EAST);
                           }

                           if (rand.nextInt(4) == 0 && worldIn.getBlockState(blockpos4).getBlock().getMaterial() == Material.air) {
                              this.func_181647_a(worldIn, blockpos4, BlockVine.WEST);
                           }

                           if (rand.nextInt(4) == 0 && worldIn.getBlockState(blockpos1).getBlock().getMaterial() == Material.air) {
                              this.func_181647_a(worldIn, blockpos1, BlockVine.SOUTH);
                           }

                           if (rand.nextInt(4) == 0 && worldIn.getBlockState(blockpos2).getBlock().getMaterial() == Material.air) {
                              this.func_181647_a(worldIn, blockpos2, BlockVine.NORTH);
                           }
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

   private void func_181647_a(World world, BlockPos blockPos, PropertyBool propertyBool) {
      IBlockState iblockstate = Blocks.vine.getDefaultState().withProperty(propertyBool, true);
      this.setBlockAndNotifyAdequately(world, blockPos, iblockstate);
      int i = 4;

      for (BlockPos blockpos = blockPos.down(); world.getBlockState(blockpos).getBlock().getMaterial() == Material.air && i > 0; i--) {
         this.setBlockAndNotifyAdequately(world, blockpos, iblockstate);
         blockpos = blockpos.down();
      }
   }
}
