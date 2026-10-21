package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;

public class BlockMushroom extends BlockBush implements IGrowable {
   public BlockMushroom() {
      float f = 0.2F;
      this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
      this.setTickRandomly(true);
   }

   @Override
   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
      int j = rand.nextInt(25);
      if (j == 0) {
         int i = 5;

         for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-4, -1, -4), pos.add(4, 1, 4))) {
            if (worldIn.getBlockState(blockpos).getBlock() == this) {
               if (--i <= 0) {
                  return;
               }
            }
         }

         BlockPos blockpos1 = pos.add(rand.nextInt(3) - 1, rand.nextInt(2) - rand.nextInt(2), rand.nextInt(3) - 1);

         for (int k = 0; k < 4; k++) {
            boolean flag = worldIn.isAirBlock(blockpos1);
            if (flag && this.canBlockStay(worldIn, blockpos1, this.getDefaultState())) {
               pos = blockpos1;
            }

            blockpos1 = pos.add(rand.nextInt(3) - 1, rand.nextInt(2) - rand.nextInt(2), rand.nextInt(3) - 1);
         }

         boolean flag1 = worldIn.isAirBlock(blockpos1);
         if (flag1) {
            boolean flag2 = this.canBlockStay(worldIn, blockpos1, this.getDefaultState());
            if (flag2) {
               worldIn.setBlockState(blockpos1, this.getDefaultState(), 2);
            }
         }
      }
   }

   @Override
   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
      boolean flag = super.canPlaceBlockAt(worldIn, pos);
      if (flag) {
         boolean flag1 = this.canBlockStay(worldIn, pos, this.getDefaultState());
         if (flag1) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean canPlaceBlockOn(Block ground) {
      return ground.isFullBlock();
   }

   @Override
   public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
      if (pos.getY() >= 0 && pos.getY() < 256) {
         IBlockState iblockstate = worldIn.getBlockState(pos.down());
         return iblockstate.getBlock() == Blocks.mycelium
            ? true
            : (
               iblockstate.getBlock() == Blocks.dirt && iblockstate.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.PODZOL
                  ? true
                  : worldIn.getLight(pos) < 13 && this.canPlaceBlockOn(iblockstate.getBlock())
            );
      } else {
         return false;
      }
   }

   public boolean generateBigMushroom(World worldIn, BlockPos pos, IBlockState state, Random rand) {
      worldIn.setBlockToAir(pos);
      WorldGenBigMushroom worldgenbigmushroom = null;
      BlockBush blockbush = Blocks.brown_mushroom;
      if (this == blockbush) {
         worldgenbigmushroom = new WorldGenBigMushroom(Blocks.brown_mushroom_block);
      } else if (this == Blocks.red_mushroom) {
         worldgenbigmushroom = new WorldGenBigMushroom(Blocks.red_mushroom_block);
      }

      if (worldgenbigmushroom != null) {
         boolean flag = worldgenbigmushroom.generate(worldIn, rand, pos);
         if (flag) {
            return true;
         }
      }

      worldIn.setBlockState(pos, state, 3);
      return false;
   }

   @Override
   public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
      return true;
   }

   @Override
   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
      return (double)rand.nextFloat() < 0.4;
   }

   @Override
   public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
      this.generateBigMushroom(worldIn, pos, state, rand);
   }
}
