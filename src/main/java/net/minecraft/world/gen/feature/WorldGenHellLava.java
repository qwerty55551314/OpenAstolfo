package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenHellLava extends WorldGenerator {
   private final Block field_150553_a;
   private final boolean field_94524_b;

   public WorldGenHellLava(Block block, boolean bl) {
      this.field_150553_a = block;
      this.field_94524_b = bl;
   }

   @Override
   public boolean generate(World worldIn, Random rand, BlockPos position) {
      Block block = worldIn.getBlockState(position.up()).getBlock();
      if (block != Blocks.netherrack) {
         return false;
      } else {
         Block block1 = worldIn.getBlockState(position).getBlock();
         if (block1.getMaterial() != Material.air && worldIn.getBlockState(position).getBlock() != Blocks.netherrack) {
            return false;
         } else {
            int i = 0;
            Block block10 = worldIn.getBlockState(position.west()).getBlock();
            Block block3 = Blocks.netherrack;
            Block block2 = block10;
            if (block2 == block3) {
               i++;
            }

            block10 = worldIn.getBlockState(position.east()).getBlock();
            Block block5 = Blocks.netherrack;
            Block block4 = block10;
            if (block4 == block5) {
               i++;
            }

            block10 = worldIn.getBlockState(position.north()).getBlock();
            Block block7 = Blocks.netherrack;
            Block block6 = block10;
            if (block6 == block7) {
               i++;
            }

            block10 = worldIn.getBlockState(position.south()).getBlock();
            Block block9 = Blocks.netherrack;
            Block block8 = block10;
            if (block8 == block9) {
               i++;
            }

            if (worldIn.getBlockState(position.down()).getBlock() == Blocks.netherrack) {
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

            boolean flag4 = worldIn.isAirBlock(position.down());
            if (flag4) {
               j++;
            }

            boolean flag5 = this.field_94524_b;
            if (!flag5 && i == 4 && j == 1 || i == 5) {
               worldIn.setBlockState(position, this.field_150553_a.getDefaultState(), 2);
               worldIn.forceBlockUpdateTick(this.field_150553_a, position, rand);
            }

            return true;
         }
      }
   }
}
