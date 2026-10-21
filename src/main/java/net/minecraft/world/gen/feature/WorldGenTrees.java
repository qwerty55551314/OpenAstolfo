package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockDirectional;
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
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class WorldGenTrees extends WorldGenAbstractTree {
   private static final IBlockState field_181653_a = Blocks.log.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.OAK);
   private static final IBlockState field_181654_b = Blocks.leaves
      .getDefaultState()
      .withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.OAK)
      .withProperty(BlockLeaves.CHECK_DECAY, false);
   private final int minTreeHeight;
   private final boolean vinesGrow;
   private final IBlockState metaWood;
   private final IBlockState metaLeaves;

   public WorldGenTrees(boolean bl) {
      this(bl, 4, field_181653_a, field_181654_b, false);
   }

   public WorldGenTrees(boolean bl, int i, IBlockState iBlockState, IBlockState iBlockState2, boolean bl2) {
      super(bl);
      this.minTreeHeight = i;
      this.metaWood = iBlockState;
      this.metaLeaves = iBlockState2;
      this.vinesGrow = bl2;
   }

   @Override
   public boolean generate(World worldIn, Random rand, BlockPos position) {
      int i = rand.nextInt(3) + this.minTreeHeight;
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
            Block block1 = worldIn.getBlockState(position.down()).getBlock();
            if ((block1 == Blocks.grass || block1 == Blocks.dirt || block1 == Blocks.farmland) && position.getY() < 256 - i - 1) {
               this.func_175921_a(worldIn, position.down());
               int b1 = 3;
               int b2 = 0;

               for (int j2 = position.getY() - b1 + i; j2 <= position.getY() + i; j2++) {
                  int j3 = j2 - (position.getY() + i);
                  int i1 = b2 + 1 - j3 / 2;

                  for (int j1 = position.getX() - i1; j1 <= position.getX() + i1; j1++) {
                     int k1 = j1 - position.getX();

                     for (int l1 = position.getZ() - i1; l1 <= position.getZ() + i1; l1++) {
                        int i2 = l1 - position.getZ();
                        if (Math.abs(k1) != i1 || Math.abs(i2) != i1 || rand.nextInt(2) != 0 && j3 != 0) {
                           BlockPos blockpos = new BlockPos(j1, j2, l1);
                           Block block = worldIn.getBlockState(blockpos).getBlock();
                           if (block.getMaterial() == Material.air || block.getMaterial() == Material.leaves || block.getMaterial() == Material.vine) {
                              this.setBlockAndNotifyAdequately(worldIn, blockpos, this.metaLeaves);
                           }
                        }
                     }
                  }
               }

               for (int k2 = 0; k2 < i; k2++) {
                  Block block2 = worldIn.getBlockState(position.up(k2)).getBlock();
                  if (block2.getMaterial() == Material.air || block2.getMaterial() == Material.leaves || block2.getMaterial() == Material.vine) {
                     this.setBlockAndNotifyAdequately(worldIn, position.up(k2), this.metaWood);
                     if (this.vinesGrow && k2 > 0) {
                        if (rand.nextInt(3) > 0 && worldIn.isAirBlock(position.add(-1, k2, 0))) {
                           this.func_181651_a(worldIn, position.add(-1, k2, 0), BlockVine.EAST);
                        }

                        if (rand.nextInt(3) > 0 && worldIn.isAirBlock(position.add(1, k2, 0))) {
                           this.func_181651_a(worldIn, position.add(1, k2, 0), BlockVine.WEST);
                        }

                        if (rand.nextInt(3) > 0 && worldIn.isAirBlock(position.add(0, k2, -1))) {
                           this.func_181651_a(worldIn, position.add(0, k2, -1), BlockVine.SOUTH);
                        }

                        if (rand.nextInt(3) > 0 && worldIn.isAirBlock(position.add(0, k2, 1))) {
                           this.func_181651_a(worldIn, position.add(0, k2, 1), BlockVine.NORTH);
                        }
                     }
                  }
               }

               if (this.vinesGrow) {
                  for (int l2 = position.getY() - 3 + i; l2 <= position.getY() + i; l2++) {
                     int k3 = l2 - (position.getY() + i);
                     int l3 = 2 - k3 / 2;
                     BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();

                     for (int i4 = position.getX() - l3; i4 <= position.getX() + l3; i4++) {
                        for (int j4 = position.getZ() - l3; j4 <= position.getZ() + l3; j4++) {
                           blockpos$mutableblockpos1.set(i4, l2, j4);
                           if (worldIn.getBlockState(blockpos$mutableblockpos1).getBlock().getMaterial() == Material.leaves) {
                              BlockPos blockpos2 = blockpos$mutableblockpos1.west();
                              BlockPos blockpos3 = blockpos$mutableblockpos1.east();
                              BlockPos blockpos4 = blockpos$mutableblockpos1.north();
                              BlockPos blockpos1 = blockpos$mutableblockpos1.south();
                              if (rand.nextInt(4) == 0 && worldIn.getBlockState(blockpos2).getBlock().getMaterial() == Material.air) {
                                 this.func_181650_b(worldIn, blockpos2, BlockVine.EAST);
                              }

                              if (rand.nextInt(4) == 0 && worldIn.getBlockState(blockpos3).getBlock().getMaterial() == Material.air) {
                                 this.func_181650_b(worldIn, blockpos3, BlockVine.WEST);
                              }

                              if (rand.nextInt(4) == 0 && worldIn.getBlockState(blockpos4).getBlock().getMaterial() == Material.air) {
                                 this.func_181650_b(worldIn, blockpos4, BlockVine.SOUTH);
                              }

                              if (rand.nextInt(4) == 0 && worldIn.getBlockState(blockpos1).getBlock().getMaterial() == Material.air) {
                                 this.func_181650_b(worldIn, blockpos1, BlockVine.NORTH);
                              }
                           }
                        }
                     }
                  }

                  if (rand.nextInt(5) == 0 && i > 5) {
                     for (int i3 = 0; i3 < 2; i3++) {
                        for (Object object : EnumFacing.Plane.HORIZONTAL) {
                           if (rand.nextInt(4 - i3) == 0) {
                              EnumFacing enumfacing = ((EnumFacing)object).getOpposite();
                              this.func_181652_a(
                                 worldIn,
                                 rand.nextInt(3),
                                 position.add(enumfacing.getFrontOffsetX(), i - 5 + i3, enumfacing.getFrontOffsetZ()),
                                 (EnumFacing)object
                              );
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

   private void func_181652_a(World world, int i, BlockPos blockPos, EnumFacing enumFacing) {
      this.setBlockAndNotifyAdequately(
         world, blockPos, Blocks.cocoa.getDefaultState().withProperty(BlockCocoa.AGE, i).withProperty(BlockDirectional.FACING, enumFacing)
      );
   }

   private void func_181651_a(World world, BlockPos blockPos, PropertyBool propertyBool) {
      this.setBlockAndNotifyAdequately(world, blockPos, Blocks.vine.getDefaultState().withProperty(propertyBool, true));
   }

   private void func_181650_b(World world, BlockPos blockPos, PropertyBool propertyBool) {
      this.func_181651_a(world, blockPos, propertyBool);
      int i = 4;

      for (BlockPos blockpos = blockPos.down(); world.getBlockState(blockpos).getBlock().getMaterial() == Material.air && i > 0; i--) {
         this.func_181651_a(world, blockpos, propertyBool);
         blockpos = blockpos.down();
      }
   }
}
