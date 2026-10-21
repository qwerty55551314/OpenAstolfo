package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenIcePath extends WorldGenerator {
   private Block block = Blocks.packed_ice;
   private int basePathWidth;

   public WorldGenIcePath(int i) {
      this.basePathWidth = i;
   }

   @Override
   public boolean generate(World worldIn, Random rand, BlockPos position) {
      while (worldIn.isAirBlock(position) && position.getY() > 2) {
         position = position.down();
      }

      if (worldIn.getBlockState(position).getBlock() != Blocks.snow) {
         return false;
      } else {
         int i = rand.nextInt(this.basePathWidth - 2) + 2;

         for (int j = position.getX() - i; j <= position.getX() + i; j++) {
            int k1 = position.getZ() - i;

            for (int k = k1; k <= position.getZ() + i; k++) {
               int l = j - position.getX();
               int i1 = k - position.getZ();
               if (l * l + i1 * i1 <= i * i) {
                  for (int j1 = position.getY() - 1; j1 <= position.getY() + 1; j1++) {
                     BlockPos blockpos = new BlockPos(j, j1, k);
                     Block blockx = worldIn.getBlockState(blockpos).getBlock();
                     if (blockx == Blocks.dirt || blockx == Blocks.snow || blockx == Blocks.ice) {
                        worldIn.setBlockState(blockpos, this.block.getDefaultState(), 2);
                     }
                  }
               }
            }
         }

         return true;
      }
   }
}
