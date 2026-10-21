package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDoor extends Block {
   public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
   public static final PropertyBool OPEN = PropertyBool.create("open");
   public static final PropertyEnum<BlockDoor.EnumHingePosition> HINGE = PropertyEnum.create("hinge", BlockDoor.EnumHingePosition.class);
   public static final PropertyBool POWERED = PropertyBool.create("powered");
   public static final PropertyEnum<BlockDoor.EnumDoorHalf> HALF = PropertyEnum.create("half", BlockDoor.EnumDoorHalf.class);

   public BlockDoor(Material materialIn) {
      super(materialIn);
      this.setDefaultState(
         super.blockState
            .getBaseState()
            .withProperty(FACING, EnumFacing.NORTH)
            .withProperty(OPEN, false)
            .withProperty(HINGE, BlockDoor.EnumHingePosition.LEFT)
            .withProperty(POWERED, false)
            .withProperty(HALF, BlockDoor.EnumDoorHalf.LOWER)
      );
   }

   @Override
   public String getLocalizedName() {
      return StatCollector.translateToLocal((this.getUnlocalizedName() + ".name").replaceAll("tile", "item"));
   }

   @Override
   public boolean isOpaqueCube() {
      return false;
   }

   @Override
   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
      return isOpen(combineMetadata(worldIn, pos));
   }

   @Override
   public boolean isFullCube() {
      return false;
   }

   @Override
   public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
      this.setBlockBoundsBasedOnState(worldIn, pos);
      return super.getSelectedBoundingBox(worldIn, pos);
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
      this.setBlockBoundsBasedOnState(worldIn, pos);
      return super.getCollisionBoundingBox(worldIn, pos, state);
   }

   @Override
   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
      this.setBoundBasedOnMeta(combineMetadata(worldIn, pos));
   }

   private void setBoundBasedOnMeta(int combinedMeta) {
      float f = 0.1875F;
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
      EnumFacing enumfacing = getFacing(combinedMeta);
      boolean flag = isOpen(combinedMeta);
      boolean flag1 = isHingeLeft(combinedMeta);
      if (flag) {
         EnumFacing enumfacing1 = EnumFacing.EAST;
         if (enumfacing == enumfacing1) {
            if (!flag1) {
               this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
            } else {
               this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
            }
         } else {
            EnumFacing enumfacing2 = EnumFacing.SOUTH;
            if (enumfacing == enumfacing2) {
               if (!flag1) {
                  this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
               } else {
                  this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
               }
            } else {
               EnumFacing enumfacing3 = EnumFacing.WEST;
               if (enumfacing == enumfacing3) {
                  if (!flag1) {
                     this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
                  } else {
                     this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
                  }
               } else if (enumfacing == EnumFacing.NORTH) {
                  if (!flag1) {
                     this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
                  } else {
                     this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                  }
               }
            }
         }
      } else {
         EnumFacing enumfacing4 = EnumFacing.EAST;
         if (enumfacing == enumfacing4) {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
         } else {
            EnumFacing enumfacing5 = EnumFacing.SOUTH;
            if (enumfacing == enumfacing5) {
               this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
            } else {
               EnumFacing enumfacing6 = EnumFacing.WEST;
               if (enumfacing == enumfacing6) {
                  this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
               } else if (enumfacing == EnumFacing.NORTH) {
                  this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
               }
            }
         }
      }
   }

   @Override
   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
      if (super.blockMaterial == Material.iron) {
         return true;
      } else {
         BlockPos blockpos = state.getValue(HALF) == BlockDoor.EnumDoorHalf.LOWER ? pos : pos.down();
         IBlockState iblockstate = pos.equals(blockpos) ? state : worldIn.getBlockState(blockpos);
         if (iblockstate.getBlock() != this) {
            return false;
         } else {
            state = iblockstate.cycleProperty(OPEN);
            worldIn.setBlockState(blockpos, state, 2);
            worldIn.markBlockRangeForRenderUpdate(blockpos, pos);
            boolean flag = state.getValue(OPEN);
            worldIn.playAuxSFXAtEntity(playerIn, flag ? 1003 : 1006, pos, 0);
            return true;
         }
      }
   }

   public void toggleDoor(World worldIn, BlockPos pos, boolean open) {
      IBlockState iblockstate = worldIn.getBlockState(pos);
      if (iblockstate.getBlock() == this) {
         BlockPos blockpos = iblockstate.getValue(HALF) == BlockDoor.EnumDoorHalf.LOWER ? pos : pos.down();
         IBlockState iblockstate1 = pos == blockpos ? iblockstate : worldIn.getBlockState(blockpos);
         if (iblockstate1.getBlock() == this) {
            boolean flag = iblockstate1.getValue(OPEN);
            if (flag != open) {
               worldIn.setBlockState(blockpos, iblockstate1.withProperty(OPEN, open), 2);
               worldIn.markBlockRangeForRenderUpdate(blockpos, pos);
               EntityPlayer entityplayer = (EntityPlayer)null;
               worldIn.playAuxSFXAtEntity(entityplayer, open ? 1003 : 1006, pos, 0);
            }
         }
      }
   }

   @Override
   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
      if (state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER) {
         BlockPos blockpos = pos.down();
         IBlockState iblockstate = worldIn.getBlockState(blockpos);
         Block block = iblockstate.getBlock();
         if (block != this) {
            worldIn.setBlockToAir(pos);
         } else if (neighborBlock != this) {
            this.onNeighborBlockChange(worldIn, blockpos, iblockstate, neighborBlock);
         }
      } else {
         boolean flag4 = false;
         BlockPos blockpos1 = pos.up();
         IBlockState iblockstate1 = worldIn.getBlockState(blockpos1);
         if (iblockstate1.getBlock() != this) {
            worldIn.setBlockToAir(pos);
            flag4 = true;
         }

         boolean flag1 = World.doesBlockHaveSolidTopSurface(worldIn, pos.down());
         if (!flag1) {
            worldIn.setBlockToAir(pos);
            flag4 = true;
            if (iblockstate1.getBlock() == this) {
               worldIn.setBlockToAir(blockpos1);
            }
         }

         if (flag4) {
            if (!worldIn.isRemote) {
               this.dropBlockAsItem(worldIn, pos, state, 0);
            }
         } else {
            boolean flag2 = worldIn.isBlockPowered(pos);
            boolean flag = flag2 || worldIn.isBlockPowered(blockpos1);
            if ((flag || neighborBlock.canProvidePower()) && neighborBlock != this) {
               boolean flag3 = iblockstate1.getValue(POWERED);
               if (flag != flag3) {
                  worldIn.setBlockState(blockpos1, iblockstate1.withProperty(POWERED, flag), 2);
                  if (flag != state.getValue(OPEN)) {
                     worldIn.setBlockState(pos, state.withProperty(OPEN, flag), 2);
                     worldIn.markBlockRangeForRenderUpdate(pos, pos);
                     EntityPlayer entityplayer = (EntityPlayer)null;
                     worldIn.playAuxSFXAtEntity(entityplayer, flag ? 1003 : 1006, pos, 0);
                  }
               }
            }
         }
      }
   }

   @Override
   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
      return state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER ? null : this.getItem();
   }

   @Override
   public MovingObjectPosition collisionRayTrace(World worldIn, BlockPos pos, Vec3 startIn, Vec3 end) {
      this.setBlockBoundsBasedOnState(worldIn, pos);
      return super.collisionRayTrace(worldIn, pos, startIn, end);
   }

   @Override
   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
      int i = pos.getY();
      boolean flag3;
      if (i >= 255) {
         flag3 = false;
      } else {
         boolean flag = World.doesBlockHaveSolidTopSurface(worldIn, pos.down());
         if (flag) {
            boolean flag1 = super.canPlaceBlockAt(worldIn, pos);
            if (flag1) {
               boolean flag2 = super.canPlaceBlockAt(worldIn, pos.up());
               if (flag2) {
                  return true;
               }
            }
         }

         flag3 = false;
      }

      return flag3;
   }

   @Override
   public int getMobilityFlag() {
      return 1;
   }

   public static int combineMetadata(IBlockAccess worldIn, BlockPos pos) {
      IBlockState iblockstate = worldIn.getBlockState(pos);
      int i = iblockstate.getBlock().getMetaFromState(iblockstate);
      boolean flag = isTop(i);
      IBlockState iblockstate1 = worldIn.getBlockState(pos.down());
      int j = iblockstate1.getBlock().getMetaFromState(iblockstate1);
      int k = flag ? j : i;
      IBlockState iblockstate2 = worldIn.getBlockState(pos.up());
      int l = iblockstate2.getBlock().getMetaFromState(iblockstate2);
      int i1 = flag ? i : l;
      int j1 = i1 & 1;
      boolean flag1 = j1 != 0;
      int k1 = i1 & 2;
      boolean flag2 = k1 != 0;
      int l1 = removeHalfBit(k);
      int i2 = l1 | (flag ? 8 : 0);
      int j2 = i2 | (flag1 ? 16 : 0);
      return j2 | (flag2 ? 32 : 0);
   }

   @Override
   public Item getItem(World worldIn, BlockPos pos) {
      return this.getItem();
   }

   private Item getItem() {
      Block block = Blocks.iron_door;
      Item item;
      if (this == block) {
         item = Items.iron_door;
      } else {
         Block block1 = Blocks.spruce_door;
         if (this == block1) {
            item = Items.spruce_door;
         } else {
            Block block2 = Blocks.birch_door;
            if (this == block2) {
               item = Items.birch_door;
            } else {
               Block block3 = Blocks.jungle_door;
               if (this == block3) {
                  item = Items.jungle_door;
               } else {
                  Block block4 = Blocks.acacia_door;
                  item = this == block4 ? Items.acacia_door : (this == Blocks.dark_oak_door ? Items.dark_oak_door : Items.oak_door);
               }
            }
         }
      }

      return item;
   }

   @Override
   public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
      BlockPos blockpos = pos.down();
      if (player.capabilities.isCreativeMode && state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER && worldIn.getBlockState(blockpos).getBlock() == this) {
         worldIn.setBlockToAir(blockpos);
      }
   }

   @Override
   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   @Override
   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
      if (state.getValue(HALF) == BlockDoor.EnumDoorHalf.LOWER) {
         IBlockState iblockstate = worldIn.getBlockState(pos.up());
         if (iblockstate.getBlock() == this) {
            state = state.withProperty(HINGE, iblockstate.getValue(HINGE)).withProperty(POWERED, iblockstate.getValue(POWERED));
         }
      } else {
         IBlockState iblockstate1 = worldIn.getBlockState(pos.down());
         if (iblockstate1.getBlock() == this) {
            state = state.withProperty(FACING, iblockstate1.getValue(FACING)).withProperty(OPEN, iblockstate1.getValue(OPEN));
         }
      }

      return state;
   }

   @Override
   public IBlockState getStateFromMeta(int meta) {
      IBlockState iblockstate2;
      if ((meta & 8) > 0) {
         iblockstate2 = this.getDefaultState()
            .withProperty(HALF, BlockDoor.EnumDoorHalf.UPPER)
            .withProperty(HINGE, (meta & 1) > 0 ? BlockDoor.EnumHingePosition.RIGHT : BlockDoor.EnumHingePosition.LEFT);
         int i = meta & 2;
         PropertyBool propertybool = POWERED;
         IBlockState iblockstate = iblockstate2;
         iblockstate2 = iblockstate.withProperty(propertybool, i > 0);
      } else {
         iblockstate2 = this.getDefaultState()
            .withProperty(HALF, BlockDoor.EnumDoorHalf.LOWER)
            .withProperty(FACING, EnumFacing.getHorizontal(meta & 3).rotateYCCW());
         int j = meta & 4;
         PropertyBool propertybool1 = OPEN;
         IBlockState iblockstate1 = iblockstate2;
         iblockstate2 = iblockstate1.withProperty(propertybool1, j > 0);
      }

      return iblockstate2;
   }

   @Override
   public int getMetaFromState(IBlockState state) {
      int i = 0;
      Comparable comparable = state.getValue(HALF);
      if (comparable == BlockDoor.EnumDoorHalf.UPPER) {
         i |= 8;
         if (state.getValue(HINGE) == BlockDoor.EnumHingePosition.RIGHT) {
            i |= 1;
         }

         if (state.getValue(POWERED)) {
            i |= 2;
         }
      } else {
         i |= state.getValue(FACING).rotateY().getHorizontalIndex();
         boolean flag = state.getValue(OPEN);
         if (flag) {
            i |= 4;
         }
      }

      return i;
   }

   public static int removeHalfBit(int meta) {
      return meta & 7;
   }

   public static boolean isOpen(IBlockAccess worldIn, BlockPos pos) {
      return isOpen(combineMetadata(worldIn, pos));
   }

   public static EnumFacing getFacing(IBlockAccess worldIn, BlockPos pos) {
      return getFacing(combineMetadata(worldIn, pos));
   }

   public static EnumFacing getFacing(int combinedMeta) {
      return EnumFacing.getHorizontal(combinedMeta & 3).rotateYCCW();
   }

   public static boolean isOpen(int combinedMeta) {
      return (combinedMeta & 4) != 0;
   }

   public static boolean isTop(int meta) {
      return (meta & 8) != 0;
   }

   public static boolean isHingeLeft(int combinedMeta) {
      return (combinedMeta & 16) != 0;
   }

   @Override
   public BlockState createBlockState() {
      return new BlockState(this, HALF, FACING, OPEN, HINGE, POWERED);
   }

   public static enum EnumDoorHalf implements IStringSerializable {
      UPPER,
      LOWER;

      @Override
      public String toString() {
         return this.getName();
      }

      @Override
      public String getName() {
         return this == UPPER ? "upper" : "lower";
      }
   }

   public static enum EnumHingePosition implements IStringSerializable {
      LEFT,
      RIGHT;

      @Override
      public String toString() {
         return this.getName();
      }

      @Override
      public String getName() {
         return this == LEFT ? "left" : "right";
      }
   }
}
