package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFenceGate extends BlockDirectional {
   public static final PropertyBool OPEN = PropertyBool.create("open");
   public static final PropertyBool POWERED = PropertyBool.create("powered");
   public static final PropertyBool IN_WALL = PropertyBool.create("in_wall");

   public BlockFenceGate(BlockPlanks.EnumType enumType) {
      super(Material.wood, enumType.getMapColor());
      this.setDefaultState(super.blockState.getBaseState().withProperty(OPEN, false).withProperty(POWERED, false).withProperty(IN_WALL, false));
      this.setCreativeTab(CreativeTabs.tabRedstone);
   }

   @Override
   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
      EnumFacing.Axis enumfacing$axis = state.getValue(BlockDirectional.FACING).getAxis();
      EnumFacing.Axis enumfacing$axis1 = EnumFacing.Axis.Z;
      if (enumfacing$axis != enumfacing$axis1
         || worldIn.getBlockState(pos.west()).getBlock() != Blocks.cobblestone_wall && worldIn.getBlockState(pos.east()).getBlock() != Blocks.cobblestone_wall) {
         if (enumfacing$axis != EnumFacing.Axis.X) {
            return state;
         }

         IBlockState iblockstate = worldIn.getBlockState(pos.north());
         if (iblockstate.getBlock() != Blocks.cobblestone_wall && worldIn.getBlockState(pos.south()).getBlock() != Blocks.cobblestone_wall) {
            return state;
         }
      }

      return state.withProperty(IN_WALL, true);
   }

   @Override
   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
      return worldIn.getBlockState(pos.down()).getBlock().getMaterial().isSolid() ? super.canPlaceBlockAt(worldIn, pos) : false;
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
      if (state.getValue(OPEN)) {
         return null;
      } else {
         EnumFacing.Axis enumfacing$axis = state.getValue(BlockDirectional.FACING).getAxis();
         return enumfacing$axis == EnumFacing.Axis.Z
            ? new AxisAlignedBB(
               (double)pos.getX(),
               (double)pos.getY(),
               (double)((float)pos.getZ() + 0.375F),
               (double)(pos.getX() + 1),
               (double)((float)pos.getY() + 1.5F),
               (double)((float)pos.getZ() + 0.625F)
            )
            : new AxisAlignedBB(
               (double)((float)pos.getX() + 0.375F),
               (double)pos.getY(),
               (double)pos.getZ(),
               (double)((float)pos.getX() + 0.625F),
               (double)((float)pos.getY() + 1.5F),
               (double)(pos.getZ() + 1)
            );
      }
   }

   @Override
   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
      EnumFacing.Axis enumfacing$axis = worldIn.getBlockState(pos).getValue(BlockDirectional.FACING).getAxis();
      if (enumfacing$axis == EnumFacing.Axis.Z) {
         this.setBlockBounds(0.0F, 0.0F, 0.375F, 1.0F, 1.0F, 0.625F);
      } else {
         this.setBlockBounds(0.375F, 0.0F, 0.0F, 0.625F, 1.0F, 1.0F);
      }
   }

   @Override
   public boolean isOpaqueCube() {
      return false;
   }

   @Override
   public boolean isFullCube() {
      return false;
   }

   @Override
   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
      return worldIn.getBlockState(pos).getValue(OPEN);
   }

   @Override
   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      return this.getDefaultState()
         .withProperty(BlockDirectional.FACING, placer.getHorizontalFacing())
         .withProperty(OPEN, false)
         .withProperty(POWERED, false)
         .withProperty(IN_WALL, false);
   }

   @Override
   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
      boolean flag = state.getValue(OPEN);
      if (flag) {
         state = state.withProperty(OPEN, false);
         worldIn.setBlockState(pos, state, 2);
      } else {
         EnumFacing enumfacing = EnumFacing.fromAngle((double)playerIn.rotationYaw);
         if (state.getValue(BlockDirectional.FACING) == enumfacing.getOpposite()) {
            state = state.withProperty(BlockDirectional.FACING, enumfacing);
         }

         state = state.withProperty(OPEN, true);
         worldIn.setBlockState(pos, state, 2);
      }

      boolean flag1 = state.getValue(OPEN);
      worldIn.playAuxSFXAtEntity(playerIn, flag1 ? 1003 : 1006, pos, 0);
      return true;
   }

   @Override
   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
      boolean flag1 = worldIn.isRemote;
      if (!flag1) {
         boolean flag = worldIn.isBlockPowered(pos);
         if (flag || neighborBlock.canProvidePower()) {
            if (flag && !state.getValue(OPEN) && !state.getValue(POWERED)) {
               worldIn.setBlockState(pos, state.withProperty(OPEN, true).withProperty(POWERED, true), 2);
               worldIn.playAuxSFXAtEntity((EntityPlayer)null, 1003, pos, 0);
            } else if (!flag && state.getValue(OPEN) && state.getValue(POWERED)) {
               worldIn.setBlockState(pos, state.withProperty(OPEN, false).withProperty(POWERED, false), 2);
               worldIn.playAuxSFXAtEntity((EntityPlayer)null, 1006, pos, 0);
            } else if (flag != state.getValue(POWERED)) {
               worldIn.setBlockState(pos, state.withProperty(POWERED, flag), 2);
            }
         }
      }
   }

   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
      return true;
   }

   @Override
   public IBlockState getStateFromMeta(int meta) {
      IBlockState iblockstate2 = this.getDefaultState().withProperty(BlockDirectional.FACING, EnumFacing.getHorizontal(meta));
      int i = meta & 4;
      PropertyBool propertybool = OPEN;
      IBlockState iblockstate = iblockstate2;
      iblockstate2 = iblockstate.withProperty(propertybool, i != 0);
      int j = meta & 8;
      PropertyBool propertybool1 = POWERED;
      IBlockState iblockstate1 = iblockstate2;
      return iblockstate1.withProperty(propertybool1, j != 0);
   }

   @Override
   public int getMetaFromState(IBlockState state) {
      int i = 0;
      i |= state.getValue(BlockDirectional.FACING).getHorizontalIndex();
      boolean flag = state.getValue(POWERED);
      if (flag) {
         i |= 8;
      }

      boolean flag1 = state.getValue(OPEN);
      if (flag1) {
         i |= 4;
      }

      return i;
   }

   @Override
   public BlockState createBlockState() {
      return new BlockState(this, BlockDirectional.FACING, OPEN, POWERED, IN_WALL);
   }
}
