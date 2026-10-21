package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockCrops extends BlockBush implements IGrowable {
   public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);

   public BlockCrops() {
      this.setDefaultState(super.blockState.getBaseState().withProperty(AGE, 0));
      this.setTickRandomly(true);
      float f = 0.5F;
      this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
      this.setCreativeTab((CreativeTabs)null);
      this.setHardness(0.0F);
      this.setStepSound(Block.soundTypeGrass);
      this.disableStats();
   }

   @Override
   public boolean canPlaceBlockOn(Block ground) {
      return ground == Blocks.farmland;
   }

   @Override
   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
      super.updateTick(worldIn, pos, state, rand);
      int j = worldIn.getLightFromNeighbors(pos.up());
      if (j >= 9) {
         int i = state.getValue(AGE);
         if (i < 7) {
            float f = getGrowthChance(this, worldIn, pos);
            int k = rand.nextInt((int)(25.0F / f) + 1);
            if (k == 0) {
               worldIn.setBlockState(pos, state.withProperty(AGE, i + 1), 2);
            }
         }
      }
   }

   public void grow(World worldIn, BlockPos pos, IBlockState state) {
      int i = state.getValue(AGE) + MathHelper.getRandomIntegerInRange(worldIn.rand, 2, 5);
      if (i > 7) {
         i = 7;
      }

      worldIn.setBlockState(pos, state.withProperty(AGE, i), 2);
   }

   public static float getGrowthChance(Block blockIn, World worldIn, BlockPos pos) {
      float f = 1.0F;
      BlockPos blockpos = pos.down();

      for (int i = -1; i <= 1; i++) {
         for (int j = -1; j <= 1; j++) {
            float f1 = 0.0F;
            IBlockState iblockstate = worldIn.getBlockState(blockpos.add(i, 0, j));
            if (iblockstate.getBlock() == Blocks.farmland) {
               f1 = 1.0F;
               if (iblockstate.getValue(BlockFarmland.MOISTURE) > 0) {
                  f1 = 3.0F;
               }
            }

            if (i != 0 || j != 0) {
               f1 /= 4.0F;
            }

            f += f1;
         }
      }

      BlockPos blockpos1 = pos.north();
      BlockPos blockpos2 = pos.south();
      BlockPos blockpos3 = pos.west();
      BlockPos blockpos4 = pos.east();
      Block block = worldIn.getBlockState(blockpos3).getBlock();
      boolean flag = blockIn == block || blockIn == worldIn.getBlockState(blockpos4).getBlock();
      Block block1 = worldIn.getBlockState(blockpos1).getBlock();
      boolean flag1 = blockIn == block1 || blockIn == worldIn.getBlockState(blockpos2).getBlock();
      if (flag && flag1) {
         f /= 2.0F;
      } else {
         boolean flag3;
         label44: {
            Block block2 = worldIn.getBlockState(blockpos3.north()).getBlock();
            if (blockIn != block2) {
               Block block3 = worldIn.getBlockState(blockpos4.north()).getBlock();
               if (blockIn != block3) {
                  Block block4 = worldIn.getBlockState(blockpos4.south()).getBlock();
                  if (blockIn != block4 && blockIn != worldIn.getBlockState(blockpos3.south()).getBlock()) {
                     flag3 = false;
                     break label44;
                  }
               }
            }

            flag3 = true;
         }

         boolean flag2 = flag3;
         if (flag2) {
            f /= 2.0F;
         }
      }

      return f;
   }

   @Override
   public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
      int i = worldIn.getLight(pos);
      if (i >= 8 || worldIn.canSeeSky(pos)) {
         boolean flag = this.canPlaceBlockOn(worldIn.getBlockState(pos.down()).getBlock());
         if (flag) {
            return true;
         }
      }

      return false;
   }

   public Item getSeed() {
      return Items.wheat_seeds;
   }

   public Item getCrop() {
      return Items.wheat;
   }

   @Override
   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
      super.dropBlockAsItemWithChance(worldIn, pos, state, chance, 0);
      boolean flag = worldIn.isRemote;
      if (!flag) {
         int i = state.getValue(AGE);
         if (i >= 7) {
            int j = 3 + fortune;

            for (int k = 0; k < j; k++) {
               if (worldIn.rand.nextInt(15) <= i) {
                  spawnAsEntity(worldIn, pos, new ItemStack(this.getSeed(), 1, 0));
               }
            }
         }
      }
   }

   @Override
   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
      return state.getValue(AGE) == 7 ? this.getCrop() : this.getSeed();
   }

   @Override
   public Item getItem(World worldIn, BlockPos pos) {
      return this.getSeed();
   }

   @Override
   public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
      return state.getValue(AGE) < 7;
   }

   @Override
   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
      return true;
   }

   @Override
   public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
      this.grow(worldIn, pos, state);
   }

   @Override
   public IBlockState getStateFromMeta(int meta) {
      return this.getDefaultState().withProperty(AGE, meta);
   }

   @Override
   public int getMetaFromState(IBlockState state) {
      return state.getValue(AGE);
   }

   @Override
   public BlockState createBlockState() {
      return new BlockState(this, AGE);
   }
}
