package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockRedstoneDiode extends BlockDirectional {
   public final boolean isRepeaterPowered;

   public BlockRedstoneDiode(boolean powered) {
      super(Material.circuits);
      this.isRepeaterPowered = powered;
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
   }

   @Override
   public boolean isFullCube() {
      return false;
   }

   @Override
   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
      return World.doesBlockHaveSolidTopSurface(worldIn, pos.down()) ? super.canPlaceBlockAt(worldIn, pos) : false;
   }

   public boolean canBlockStay(World worldIn, BlockPos pos) {
      return World.doesBlockHaveSolidTopSurface(worldIn, pos.down());
   }

   @Override
   public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random randomIn) {
   }

   @Override
   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
      boolean flag1 = this.isLocked(worldIn, pos, state);
      if (!flag1) {
         boolean flag = this.shouldBePowered(worldIn, pos, state);
         boolean flag2 = this.isRepeaterPowered;
         if (flag2 && !flag) {
            worldIn.setBlockState(pos, this.getUnpoweredState(state), 2);
         } else {
            boolean flag3 = this.isRepeaterPowered;
            if (!flag3) {
               worldIn.setBlockState(pos, this.getPoweredState(state), 2);
               if (!flag) {
                  worldIn.updateBlockTick(pos, this.getPoweredState(state).getBlock(), this.getTickDelay(state), -1);
               }
            }
         }
      }
   }

   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
      return side.getAxis() != EnumFacing.Axis.Y;
   }

   public boolean isPowered(IBlockState state) {
      return this.isRepeaterPowered;
   }

   @Override
   public int getStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
      return this.getWeakPower(worldIn, pos, state, side);
   }

   @Override
   public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
      return !this.isPowered(state) ? 0 : (state.getValue(BlockDirectional.FACING) == side ? this.getActiveSignal(worldIn, pos, state) : 0);
   }

   @Override
   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
      if (this.canBlockStay(worldIn, pos)) {
         this.updateState(worldIn, pos, state);
      } else {
         this.dropBlockAsItem(worldIn, pos, state, 0);
         worldIn.setBlockToAir(pos);

         for (EnumFacing enumfacing : EnumFacing.values()) {
            worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this);
         }
      }
   }

   public void updateState(World worldIn, BlockPos pos, IBlockState state) {
      boolean flag1 = this.isLocked(worldIn, pos, state);
      if (!flag1) {
         boolean flag = this.shouldBePowered(worldIn, pos, state);
         boolean flag2 = this.isRepeaterPowered;
         if (!flag2 || flag) {
            boolean flag3 = this.isRepeaterPowered;
            if (flag3 || !flag) {
               return;
            }
         }

         boolean flag4 = worldIn.isBlockTickPending(pos, this);
         if (!flag4) {
            int b0 = -1;
            boolean flag5 = this.isFacingTowardsRepeater(worldIn, pos, state);
            if (flag5) {
               b0 = -3;
            } else {
               boolean flag6 = this.isRepeaterPowered;
               if (flag6) {
                  b0 = -2;
               }
            }

            worldIn.updateBlockTick(pos, this, this.getDelay(state), b0);
         }
      }
   }

   public boolean isLocked(IBlockAccess worldIn, BlockPos pos, IBlockState state) {
      return false;
   }

   public boolean shouldBePowered(World worldIn, BlockPos pos, IBlockState state) {
      return this.calculateInputStrength(worldIn, pos, state) > 0;
   }

   public int calculateInputStrength(World worldIn, BlockPos pos, IBlockState state) {
      EnumFacing enumfacing = state.getValue(BlockDirectional.FACING);
      BlockPos blockpos = pos.offset(enumfacing);
      int i = worldIn.getRedstonePower(blockpos, enumfacing);
      if (i >= 15) {
         return i;
      } else {
         IBlockState iblockstate = worldIn.getBlockState(blockpos);
         return Math.max(i, iblockstate.getBlock() == Blocks.redstone_wire ? iblockstate.getValue(BlockRedstoneWire.POWER) : 0);
      }
   }

   public int getPowerOnSides(IBlockAccess worldIn, BlockPos pos, IBlockState state) {
      EnumFacing enumfacing = state.getValue(BlockDirectional.FACING);
      EnumFacing enumfacing1 = enumfacing.rotateY();
      EnumFacing enumfacing2 = enumfacing.rotateYCCW();
      return Math.max(this.getPowerOnSide(worldIn, pos.offset(enumfacing1), enumfacing1), this.getPowerOnSide(worldIn, pos.offset(enumfacing2), enumfacing2));
   }

   public int getPowerOnSide(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
      IBlockState iblockstate = worldIn.getBlockState(pos);
      Block block = iblockstate.getBlock();
      return this.canPowerSide(block) ? (block == Blocks.redstone_wire ? iblockstate.getValue(BlockRedstoneWire.POWER) : worldIn.getStrongPower(pos, side)) : 0;
   }

   @Override
   public boolean canProvidePower() {
      return true;
   }

   @Override
   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      return this.getDefaultState().withProperty(BlockDirectional.FACING, placer.getHorizontalFacing().getOpposite());
   }

   @Override
   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
      if (this.shouldBePowered(worldIn, pos, state)) {
         worldIn.scheduleUpdate(pos, this, 1);
      }
   }

   @Override
   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
      this.notifyNeighbors(worldIn, pos, state);
   }

   public void notifyNeighbors(World worldIn, BlockPos pos, IBlockState state) {
      EnumFacing enumfacing = state.getValue(BlockDirectional.FACING);
      BlockPos blockpos = pos.offset(enumfacing.getOpposite());
      worldIn.notifyBlockOfStateChange(blockpos, this);
      worldIn.notifyNeighborsOfStateExcept(blockpos, this, enumfacing);
   }

   @Override
   public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
      if (this.isRepeaterPowered) {
         for (EnumFacing enumfacing : EnumFacing.values()) {
            worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this);
         }
      }

      super.onBlockDestroyedByPlayer(worldIn, pos, state);
   }

   @Override
   public boolean isOpaqueCube() {
      return false;
   }

   public boolean canPowerSide(Block blockIn) {
      return blockIn.canProvidePower();
   }

   public int getActiveSignal(IBlockAccess worldIn, BlockPos pos, IBlockState state) {
      return 15;
   }

   public static boolean isRedstoneRepeaterBlockID(Block blockIn) {
      return Blocks.unpowered_repeater.isAssociated(blockIn) || Blocks.unpowered_comparator.isAssociated(blockIn);
   }

   public boolean isAssociated(Block other) {
      return other == this.getPoweredState(this.getDefaultState()).getBlock() || other == this.getUnpoweredState(this.getDefaultState()).getBlock();
   }

   public boolean isFacingTowardsRepeater(World worldIn, BlockPos pos, IBlockState state) {
      EnumFacing enumfacing = state.getValue(BlockDirectional.FACING).getOpposite();
      BlockPos blockpos = pos.offset(enumfacing);
      return isRedstoneRepeaterBlockID(worldIn.getBlockState(blockpos).getBlock())
         ? worldIn.getBlockState(blockpos).getValue(BlockDirectional.FACING) != enumfacing
         : false;
   }

   public int getTickDelay(IBlockState state) {
      return this.getDelay(state);
   }

   public abstract int getDelay(IBlockState var1);

   public abstract IBlockState getPoweredState(IBlockState var1);

   public abstract IBlockState getUnpoweredState(IBlockState var1);

   @Override
   public boolean isAssociatedBlock(Block other) {
      return this.isAssociated(other);
   }

   @Override
   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }
}
