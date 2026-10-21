package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenClay extends WorldGenerator {
   private Block T = Blocks.clay;
   private int numberOfBlocks;

   public WorldGenClay(int i) {
      this.numberOfBlocks = i;
   }

   @Override
   public boolean generate(World worldIn, Random rand, BlockPos position) {
      if (worldIn.getBlockState(position).getBlock().getMaterial() != Material.water) {
         return false;
      } else {
         int i = rand.nextInt(this.numberOfBlocks - 2) + 2;

         for (int j = position.getX() - i; j <= position.getX() + i; j++) {
            for (int k = position.getZ() - i; k <= position.getZ() + i; k++) {
               int l = j - position.getX();
               int i1 = k - position.getZ();
               if (l * l + i1 * i1 <= i * i) {
                  for (int j1 = position.getY() - 1; j1 <= position.getY() + 1; j1++) {
                     BlockPos blockpos = new BlockPos(j, j1, k);
                     Block block = worldIn.getBlockState(blockpos).getBlock();
                     if (block == Blocks.dirt || block == Blocks.clay) {
                        worldIn.setBlockState(blockpos, this.T.getDefaultState(), 2);
                     }
                  }
               }
            }
         }

         return true;
      }
   }
}
