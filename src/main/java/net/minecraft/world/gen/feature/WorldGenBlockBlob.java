package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenBlockBlob extends WorldGenerator {
   private final Block Q;
   private final int Z;

   public WorldGenBlockBlob(Block block, int i) {
      super(false);
      this.Q = block;
      this.Z = i;
   }

   @Override
   public boolean generate(World worldIn, Random rand, BlockPos position) {
      while (true) {
         label50: {
            if (position.getY() > 3) {
               BlockPos blockpos1 = position.down();
               if (worldIn.isAirBlock(blockpos1)) {
                  break label50;
               }

               Block block = worldIn.getBlockState(position.down()).getBlock();
               BlockGrass blockgrass = Blocks.grass;
               if (block != blockgrass) {
                  Block block1 = Blocks.dirt;
                  if (block != block1 && block != Blocks.stone) {
                     break label50;
                  }
               }
            }

            int i1 = position.getY();
            if (i1 <= 3) {
               return false;
            }

            int j1 = this.Z;

            for (int i = 0; j1 >= 0 && i < 3; i++) {
               int j = j1 + rand.nextInt(2);
               int k = j1 + rand.nextInt(2);
               int l = j1 + rand.nextInt(2);
               float f = (float)(j + k + l) * 0.333F + 0.5F;

               for (BlockPos blockpos : BlockPos.getAllInBox(position.add(-j, -k, -l), position.add(j, k, l))) {
                  if (blockpos.distanceSq(position) <= (double)(f * f)) {
                     worldIn.setBlockState(blockpos, this.Q.getDefaultState(), 4);
                  }
               }

               position = position.add(-(j1 + 1) + rand.nextInt(2 + j1 * 2), 0 - rand.nextInt(2), -(j1 + 1) + rand.nextInt(2 + j1 * 2));
            }

            return true;
         }

         position = position.down();
      }
   }
}
