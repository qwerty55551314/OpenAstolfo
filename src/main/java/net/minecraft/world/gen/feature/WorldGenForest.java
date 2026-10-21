package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenForest extends WorldGenAbstractTree {
   private static final IBlockState field_181629_a = Blocks.log.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.BIRCH);
   private static final IBlockState field_181630_b = Blocks.leaves
      .getDefaultState()
      .withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.BIRCH)
      .withProperty(BlockLeaves.CHECK_DECAY, false);
   private boolean useExtraRandomHeight;

   public WorldGenForest(boolean bl, boolean bl2) {
      super(bl);
      this.useExtraRandomHeight = bl2;
   }

   @Override
   public boolean generate(World worldIn, Random rand, BlockPos position) {
      int i = rand.nextInt(3) + 5;
      boolean flag1 = this.useExtraRandomHeight;
      if (flag1) {
         i += rand.nextInt(7);
      }

      boolean flag = true;
      int l1 = position.getY();
      if (l1 >= 1 && position.getY() + i + 1 <= 256) {
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

               for (int i2 = position.getY() - 3 + i; i2 <= position.getY() + i; i2++) {
                  int k2 = i2 - (position.getY() + i);
                  int l2 = 1 - k2 / 2;

                  for (int i3 = position.getX() - l2; i3 <= position.getX() + l2; i3++) {
                     int i1 = i3 - position.getX();

                     for (int j1 = position.getZ() - l2; j1 <= position.getZ() + l2; j1++) {
                        int k1 = j1 - position.getZ();
                        if (Math.abs(i1) != l2 || Math.abs(k1) != l2 || rand.nextInt(2) != 0 && k2 != 0) {
                           BlockPos blockpos = new BlockPos(i3, i2, j1);
                           Block block = worldIn.getBlockState(blockpos).getBlock();
                           if (block.getMaterial() == Material.air || block.getMaterial() == Material.leaves) {
                              this.setBlockAndNotifyAdequately(worldIn, blockpos, field_181630_b);
                           }
                        }
                     }
                  }
               }

               for (int j2 = 0; j2 < i; j2++) {
                  Block block2 = worldIn.getBlockState(position.up(j2)).getBlock();
                  if (block2.getMaterial() == Material.air || block2.getMaterial() == Material.leaves) {
                     this.setBlockAndNotifyAdequately(worldIn, position.up(j2), field_181629_a);
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
}
