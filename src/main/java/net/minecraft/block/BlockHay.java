package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockHay extends BlockRotatedPillar {
   public BlockHay() {
      super(Material.grass, MapColor.yellowColor);
      this.setDefaultState(super.blockState.getBaseState().withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.Y));
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   @Override
   public IBlockState getStateFromMeta(int meta) {
      EnumFacing.Axis enumfacing$axis = EnumFacing.Axis.Y;
      int i = meta & 12;
      if (i == 4) {
         enumfacing$axis = EnumFacing.Axis.X;
      } else if (i == 8) {
         enumfacing$axis = EnumFacing.Axis.Z;
      }

      return this.getDefaultState().withProperty(BlockRotatedPillar.AXIS, enumfacing$axis);
   }

   @Override
   public int getMetaFromState(IBlockState state) {
      int b0 = 0;
      EnumFacing.Axis enumfacing$axis = state.getValue(BlockRotatedPillar.AXIS);
      if (enumfacing$axis == EnumFacing.Axis.X) {
         b0 |= 4;
      } else if (enumfacing$axis == EnumFacing.Axis.Z) {
         b0 |= 8;
      }

      return b0;
   }

   @Override
   public BlockState createBlockState() {
      return new BlockState(this, BlockRotatedPillar.AXIS);
   }

   @Override
   public ItemStack createStackedBlock(IBlockState state) {
      return new ItemStack(Item.getItemFromBlock(this), 1, 0);
   }

   @Override
   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(BlockRotatedPillar.AXIS, facing.getAxis());
   }
}
