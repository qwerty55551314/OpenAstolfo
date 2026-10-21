package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockVine extends Block {
   public static final PropertyBool UP = PropertyBool.create("up");
   public static final PropertyBool NORTH = PropertyBool.create("north");
   public static final PropertyBool EAST = PropertyBool.create("east");
   public static final PropertyBool SOUTH = PropertyBool.create("south");
   public static final PropertyBool WEST = PropertyBool.create("west");
   public static final PropertyBool[] ALL_FACES = new PropertyBool[]{UP, NORTH, SOUTH, WEST, EAST};

   public BlockVine() {
      super(Material.vine);
      this.setDefaultState(
         super.blockState
            .getBaseState()
            .withProperty(UP, false)
            .withProperty(NORTH, false)
            .withProperty(EAST, false)
            .withProperty(SOUTH, false)
            .withProperty(WEST, false)
      );
      this.setTickRandomly(true);
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   @Override
   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
      return state.withProperty(UP, worldIn.getBlockState(pos.up()).getBlock().isBlockNormalCube());
   }

   @Override
   public void setBlockBoundsForItemRender() {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
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
   public boolean isReplaceable(World worldIn, BlockPos pos) {
      return true;
   }

   @Override
   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
      float f = 1.0F;
      float f1 = 1.0F;
      float f2 = 1.0F;
      float f3 = 0.0F;
      float f4 = 0.0F;
      float f5 = 0.0F;
      boolean flag = false;
      boolean flag1 = worldIn.getBlockState(pos).getValue(WEST);
      if (flag1) {
         f3 = Math.max(f3, 0.0625F);
         f = 0.0F;
         f1 = 0.0F;
         f4 = 1.0F;
         f2 = 0.0F;
         f5 = 1.0F;
         flag = true;
      }

      boolean flag2 = worldIn.getBlockState(pos).getValue(EAST);
      if (flag2) {
         f = Math.min(f, 0.9375F);
         f3 = 1.0F;
         f1 = 0.0F;
         f4 = 1.0F;
         f2 = 0.0F;
         f5 = 1.0F;
         flag = true;
      }

      boolean flag3 = worldIn.getBlockState(pos).getValue(NORTH);
      if (flag3) {
         f5 = Math.max(f5, 0.0625F);
         f2 = 0.0F;
         f = 0.0F;
         f3 = 1.0F;
         f1 = 0.0F;
         f4 = 1.0F;
         flag = true;
      }

      boolean flag4 = worldIn.getBlockState(pos).getValue(SOUTH);
      if (flag4) {
         f2 = Math.min(f2, 0.9375F);
         f5 = 1.0F;
         f = 0.0F;
         f3 = 1.0F;
         f1 = 0.0F;
         f4 = 1.0F;
         flag = true;
      }

      if (!flag && this.canPlaceOn(worldIn.getBlockState(pos.up()).getBlock())) {
         f1 = Math.min(f1, 0.9375F);
         f4 = 1.0F;
         f = 0.0F;
         f3 = 1.0F;
         f2 = 0.0F;
         f5 = 1.0F;
      }

      this.setBlockBounds(f, f1, f2, f3, f4, f5);
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
      return null;
   }

   @Override
   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
      switch (side) {
         case UP:
            return this.canPlaceOn(worldIn.getBlockState(pos.up()).getBlock());
         case NORTH:
         case SOUTH:
         case EAST:
         case WEST:
            return this.canPlaceOn(worldIn.getBlockState(pos.offset(side.getOpposite())).getBlock());
         default:
            return false;
      }
   }

   private boolean canPlaceOn(Block blockIn) {
      boolean flag = blockIn.isFullCube();
      if (flag) {
         boolean flag1 = blockIn.blockMaterial.blocksMovement();
         if (flag1) {
            return true;
         }
      }

      return false;
   }

   private boolean recheckGrownSides(World worldIn, BlockPos pos, IBlockState state) {
      IBlockState iblockstate = state;

      for (Object object : EnumFacing.Plane.HORIZONTAL) {
         PropertyBool propertybool = getPropertyFor((EnumFacing)object);
         boolean flag = state.getValue(propertybool);
         if (flag && !this.canPlaceOn(worldIn.getBlockState(pos.offset((EnumFacing)object)).getBlock())) {
            IBlockState iblockstate1 = worldIn.getBlockState(pos.up());
            if (iblockstate1.getBlock() != this || !iblockstate1.getValue(propertybool)) {
               state = state.withProperty(propertybool, false);
            }
         }
      }

      if (getNumGrownFaces(state) == 0) {
         return false;
      } else {
         if (iblockstate != state) {
            worldIn.setBlockState(pos, state, 2);
         }

         return true;
      }
   }

   @Override
   public int getBlockColor() {
      return 4764952;
   }

   @Override
   public int getRenderColor(IBlockState state) {
      return 4764952;
   }

   @Override
   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
      return worldIn.getBiomeGenForCoords(pos).getFoliageColorAtPos(pos);
   }

   @Override
   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
      boolean flag = worldIn.isRemote;
      if (!flag) {
         boolean flag1 = this.recheckGrownSides(worldIn, pos, state);
         if (!flag1) {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
         }
      }
   }

   @Override
   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
      boolean flag3 = worldIn.isRemote;
      if (!flag3) {
         int i1 = worldIn.rand.nextInt(4);
         if (i1 == 0) {
            int i = 5;
            boolean flag = false;

            label182:
            for (int j = -4; j <= 4; j++) {
               for (int k = -4; k <= 4; k++) {
                  for (int l = -1; l <= 1; l++) {
                     if (worldIn.getBlockState(pos.add(j, l, k)).getBlock() == this) {
                        if (--i <= 0) {
                           flag = true;
                           break label182;
                        }
                     }
                  }
               }
            }

            EnumFacing enumfacing1 = EnumFacing.random(rand);
            BlockPos blockpos2 = pos.up();
            if (enumfacing1 != EnumFacing.UP || pos.getY() >= 255 || !worldIn.isAirBlock(blockpos2)) {
               boolean flag4 = enumfacing1.getAxis().isHorizontal();
               if (!flag4 || state.getValue(getPropertyFor(enumfacing1))) {
                  if (pos.getY() > 1) {
                     BlockPos blockpos4 = pos.down();
                     IBlockState iblockstate1 = worldIn.getBlockState(blockpos4);
                     Block block1 = iblockstate1.getBlock();
                     if (block1.blockMaterial == Material.air) {
                        IBlockState iblockstate2 = state;

                        for (Object object1 : EnumFacing.Plane.HORIZONTAL) {
                           if (rand.nextBoolean()) {
                              iblockstate2 = iblockstate2.withProperty(getPropertyFor((EnumFacing)object1), false);
                           }
                        }

                        if (iblockstate2.getValue(NORTH) || iblockstate2.getValue(EAST) || iblockstate2.getValue(SOUTH) || iblockstate2.getValue(WEST)) {
                           worldIn.setBlockState(blockpos4, iblockstate2, 2);
                        }
                     } else if (block1 == this) {
                        IBlockState iblockstate3 = iblockstate1;

                        for (Object object2 : EnumFacing.Plane.HORIZONTAL) {
                           PropertyBool propertybool = getPropertyFor((EnumFacing)object2);
                           boolean flag5 = rand.nextBoolean();
                           if (flag5 && state.getValue(propertybool)) {
                              iblockstate3 = iblockstate3.withProperty(propertybool, true);
                           }
                        }

                        boolean flag6 = iblockstate3.getValue(NORTH);
                        if (flag6 || iblockstate3.getValue(EAST) || iblockstate3.getValue(SOUTH) || iblockstate3.getValue(WEST)) {
                           worldIn.setBlockState(blockpos4, iblockstate3, 2);
                        }
                     }
                  }
               } else if (!flag) {
                  BlockPos blockpos3 = pos.offset(enumfacing1);
                  Block block = worldIn.getBlockState(blockpos3).getBlock();
                  if (block.blockMaterial == Material.air) {
                     EnumFacing enumfacing2 = enumfacing1.rotateY();
                     EnumFacing enumfacing = enumfacing1.rotateYCCW();
                     boolean flag1 = state.getValue(getPropertyFor(enumfacing2));
                     boolean flag2 = state.getValue(getPropertyFor(enumfacing));
                     BlockPos blockpos = blockpos3.offset(enumfacing2);
                     BlockPos blockpos1 = blockpos3.offset(enumfacing);
                     if (flag1 && this.canPlaceOn(worldIn.getBlockState(blockpos).getBlock())) {
                        worldIn.setBlockState(blockpos3, this.getDefaultState().withProperty(getPropertyFor(enumfacing2), true), 2);
                     } else if (flag2 && this.canPlaceOn(worldIn.getBlockState(blockpos1).getBlock())) {
                        worldIn.setBlockState(blockpos3, this.getDefaultState().withProperty(getPropertyFor(enumfacing), true), 2);
                     } else if (flag1 && worldIn.isAirBlock(blockpos) && this.canPlaceOn(worldIn.getBlockState(pos.offset(enumfacing2)).getBlock())) {
                        worldIn.setBlockState(blockpos, this.getDefaultState().withProperty(getPropertyFor(enumfacing1.getOpposite()), true), 2);
                     } else if (flag2 && worldIn.isAirBlock(blockpos1) && this.canPlaceOn(worldIn.getBlockState(pos.offset(enumfacing)).getBlock())) {
                        worldIn.setBlockState(blockpos1, this.getDefaultState().withProperty(getPropertyFor(enumfacing1.getOpposite()), true), 2);
                     } else if (this.canPlaceOn(worldIn.getBlockState(blockpos3.up()).getBlock())) {
                        worldIn.setBlockState(blockpos3, this.getDefaultState(), 2);
                     }
                  } else if (block.blockMaterial.isOpaque() && block.isFullCube()) {
                     worldIn.setBlockState(pos, state.withProperty(getPropertyFor(enumfacing1), true), 2);
                  }
               }
            } else if (!flag) {
               IBlockState iblockstate = state;

               for (Object object : EnumFacing.Plane.HORIZONTAL) {
                  if (rand.nextBoolean() || !this.canPlaceOn(worldIn.getBlockState(blockpos2.offset((EnumFacing)object)).getBlock())) {
                     iblockstate = iblockstate.withProperty(getPropertyFor((EnumFacing)object), false);
                  }
               }

               if (iblockstate.getValue(NORTH) || iblockstate.getValue(EAST) || iblockstate.getValue(SOUTH) || iblockstate.getValue(WEST)) {
                  worldIn.setBlockState(blockpos2, iblockstate, 2);
               }
            }
         }
      }
   }

   @Override
   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      IBlockState iblockstate = this.getDefaultState()
         .withProperty(UP, false)
         .withProperty(NORTH, false)
         .withProperty(EAST, false)
         .withProperty(SOUTH, false)
         .withProperty(WEST, false);
      return facing.getAxis().isHorizontal() ? iblockstate.withProperty(getPropertyFor(facing.getOpposite()), true) : iblockstate;
   }

   @Override
   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
      return null;
   }

   @Override
   public int quantityDropped(Random randomIn) {
      return 0;
   }

   @Override
   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te) {
      if (!worldIn.isRemote && player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == Items.shears) {
         player.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
         spawnAsEntity(worldIn, pos, new ItemStack(Blocks.vine, 1, 0));
      } else {
         super.harvestBlock(worldIn, player, pos, state, te);
      }
   }

   @Override
   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   @Override
   public IBlockState getStateFromMeta(int meta) {
      IBlockState iblockstate4 = this.getDefaultState();
      int i = meta & 1;
      PropertyBool propertybool = SOUTH;
      IBlockState iblockstate = iblockstate4;
      iblockstate4 = iblockstate.withProperty(propertybool, i > 0);
      int j = meta & 2;
      PropertyBool propertybool1 = WEST;
      IBlockState iblockstate1 = iblockstate4;
      iblockstate4 = iblockstate1.withProperty(propertybool1, j > 0);
      int k = meta & 4;
      PropertyBool propertybool2 = NORTH;
      IBlockState iblockstate2 = iblockstate4;
      iblockstate4 = iblockstate2.withProperty(propertybool2, k > 0);
      int l = meta & 8;
      PropertyBool propertybool3 = EAST;
      IBlockState iblockstate3 = iblockstate4;
      return iblockstate3.withProperty(propertybool3, l > 0);
   }

   @Override
   public int getMetaFromState(IBlockState state) {
      int b0 = 0;
      boolean flag = state.getValue(SOUTH);
      if (flag) {
         b0 |= 1;
      }

      boolean flag1 = state.getValue(WEST);
      if (flag1) {
         b0 |= 2;
      }

      boolean flag2 = state.getValue(NORTH);
      if (flag2) {
         b0 |= 4;
      }

      boolean flag3 = state.getValue(EAST);
      if (flag3) {
         b0 |= 8;
      }

      return b0;
   }

   @Override
   public BlockState createBlockState() {
      return new BlockState(this, UP, NORTH, EAST, SOUTH, WEST);
   }

   public static PropertyBool getPropertyFor(EnumFacing side) {
      switch (side) {
         case UP:
            return UP;
         case NORTH:
            return NORTH;
         case SOUTH:
            return SOUTH;
         case EAST:
            return EAST;
         case WEST:
            return WEST;
         default:
            throw new IllegalArgumentException(side + " is an invalid choice");
      }
   }

   public static int getNumGrownFaces(IBlockState state) {
      int i = 0;

      for (PropertyBool propertybool : ALL_FACES) {
         if (state.getValue(propertybool)) {
            i++;
         }
      }

      return i;
   }
}
