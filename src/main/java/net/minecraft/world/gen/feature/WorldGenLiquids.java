package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenLiquids extends WorldGenerator {
   private Block block;

   public WorldGenLiquids(Block block) {
      this.block = block;
   }

   @Override
   public boolean generate(World worldIn, Random rand, BlockPos position) {
      Block block9 = worldIn.getBlockState(position.up()).getBlock();
      Block block1 = Blocks.stone;
      Block blockx = block9;
      if (blockx != block1) {
         return false;
      } else if (worldIn.getBlockState(position.down()).getBlock() != Blocks.stone) {
         return false;
      } else {
         Block block2 = worldIn.getBlockState(position).getBlock();
         if (block2.getMaterial() != Material.air && worldIn.getBlockState(position).getBlock() != Blocks.stone) {
            return false;
         } else {
            int i = 0;
            block9 = worldIn.getBlockState(position.west()).getBlock();
            Block block4 = Blocks.stone;
            Block block3 = block9;
            if (block3 == block4) {
               i++;
            }

            block9 = worldIn.getBlockState(position.east()).getBlock();
            Block block6 = Blocks.stone;
            Block block5 = block9;
            if (block5 == block6) {
               i++;
            }

            block9 = worldIn.getBlockState(position.north()).getBlock();
            Block block8 = Blocks.stone;
            Block block7 = block9;
            if (block7 == block8) {
               i++;
            }

            if (worldIn.getBlockState(position.south()).getBlock() == Blocks.stone) {
               i++;
            }

            int j = 0;
            boolean flag = worldIn.isAirBlock(position.west());
            if (flag) {
               j++;
            }

            boolean flag1 = worldIn.isAirBlock(position.east());
            if (flag1) {
               j++;
            }

            boolean flag2 = worldIn.isAirBlock(position.north());
            if (flag2) {
               j++;
            }

            boolean flag3 = worldIn.isAirBlock(position.south());
            if (flag3) {
               j++;
            }

            if (i == 3 && j == 1) {
               worldIn.setBlockState(position, this.block.getDefaultState(), 2);
               worldIn.forceBlockUpdateTick(this.block, position, rand);
            }

            return true;
         }
      }
   }
}
