package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHugeMushroom;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenBigMushroom extends WorldGenerator {
   private Block mushroomType;

   public WorldGenBigMushroom(Block block) {
      super(true);
      this.mushroomType = block;
   }

   public WorldGenBigMushroom() {
      super(false);
   }

   @Override
   public boolean generate(World worldIn, Random rand, BlockPos position) {
      if (this.mushroomType == null) {
         this.mushroomType = rand.nextBoolean() ? Blocks.brown_mushroom_block : Blocks.red_mushroom_block;
      }

      int i = rand.nextInt(3) + 4;
      boolean flag = true;
      int j2 = position.getY();
      if (j2 >= 1 && position.getY() + i + 1 < 256) {
         for (int j = position.getY(); j <= position.getY() + 1 + i; j++) {
            int b0 = 3;
            if (j <= position.getY() + 3) {
               b0 = 0;
            }

            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for (int k = position.getX() - b0; k <= position.getX() + b0 && flag; k++) {
               for (int l = position.getZ() - b0; l <= position.getZ() + b0 && flag; l++) {
                  if (j >= 0 && j < 256) {
                     Block block = worldIn.getBlockState(blockpos$mutableblockpos.set(k, j, l)).getBlock();
                     if (block.getMaterial() != Material.air && block.getMaterial() != Material.leaves) {
                        flag = false;
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
            if (block1 != Blocks.dirt && block1 != Blocks.grass && block1 != Blocks.mycelium) {
               return false;
            } else {
               int k2 = position.getY() + i;
               if (this.mushroomType == Blocks.red_mushroom_block) {
                  k2 = position.getY() + i - 3;
               }

               for (int l2 = k2; l2 <= position.getY() + i; l2++) {
                  int j3 = 1;
                  if (l2 < position.getY() + i) {
                     j3++;
                  }

                  if (this.mushroomType == Blocks.brown_mushroom_block) {
                     j3 = 3;
                  }

                  int k3 = position.getX() - j3;
                  int l3 = position.getX() + j3;
                  int i1 = position.getZ() - j3;
                  int j1 = position.getZ() + j3;

                  for (int k1 = k3; k1 <= l3; k1++) {
                     for (int l1 = i1; l1 <= j1; l1++) {
                        int i2 = 5;
                        if (k1 == k3) {
                           i2--;
                        } else if (k1 == l3) {
                           i2++;
                        }

                        if (l1 == i1) {
                           i2 -= 3;
                        } else if (l1 == j1) {
                           i2 += 3;
                        }

                        BlockHugeMushroom.EnumType blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.byMetadata(i2);
                        if (this.mushroomType == Blocks.brown_mushroom_block || l2 < position.getY() + i) {
                           if ((k1 == k3 || k1 == l3) && (l1 == i1 || l1 == j1)) {
                              continue;
                           }

                           if (k1 == position.getX() - (j3 - 1) && l1 == i1) {
                              blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.NORTH_WEST;
                           }

                           if (k1 == k3 && l1 == position.getZ() - (j3 - 1)) {
                              blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.NORTH_WEST;
                           }

                           if (k1 == position.getX() + (j3 - 1) && l1 == i1) {
                              blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.NORTH_EAST;
                           }

                           if (k1 == l3 && l1 == position.getZ() - (j3 - 1)) {
                              blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.NORTH_EAST;
                           }

                           if (k1 == position.getX() - (j3 - 1) && l1 == j1) {
                              blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.SOUTH_WEST;
                           }

                           if (k1 == k3 && l1 == position.getZ() + (j3 - 1)) {
                              blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.SOUTH_WEST;
                           }

                           if (k1 == position.getX() + (j3 - 1) && l1 == j1) {
                              blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.SOUTH_EAST;
                           }

                           if (k1 == l3 && l1 == position.getZ() + (j3 - 1)) {
                              blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.SOUTH_EAST;
                           }
                        }

                        if (blockhugemushroom$enumtype == BlockHugeMushroom.EnumType.CENTER && l2 < position.getY() + i) {
                           blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.ALL_INSIDE;
                        }

                        if (position.getY() >= position.getY() + i - 1 || blockhugemushroom$enumtype != BlockHugeMushroom.EnumType.ALL_INSIDE) {
                           BlockPos blockpos = new BlockPos(k1, l2, l1);
                           if (!worldIn.getBlockState(blockpos).getBlock().isFullBlock()) {
                              this.setBlockAndNotifyAdequately(
                                 worldIn, blockpos, this.mushroomType.getDefaultState().withProperty(BlockHugeMushroom.VARIANT, blockhugemushroom$enumtype)
                              );
                           }
                        }
                     }
                  }
               }

               for (int i3 = 0; i3 < i; i3++) {
                  Block block2 = worldIn.getBlockState(position.up(i3)).getBlock();
                  if (!block2.isFullBlock()) {
                     this.setBlockAndNotifyAdequately(
                        worldIn, position.up(i3), this.mushroomType.getDefaultState().withProperty(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumType.STEM)
                     );
                  }
               }

               return true;
            }
         }
      } else {
         return false;
      }
   }
}
