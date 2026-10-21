package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemDoor extends Item {
   private Block block;

   public ItemDoor(Block block) {
      this.block = block;
      this.setCreativeTab(CreativeTabs.tabRedstone);
   }

   @Override
   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
      if (side != EnumFacing.UP) {
         return false;
      } else {
         IBlockState iblockstate = worldIn.getBlockState(pos);
         Block blockx = iblockstate.getBlock();
         boolean flag = blockx.isReplaceable(worldIn, pos);
         if (!flag) {
            pos = pos.offset(side);
         }

         boolean flag1 = playerIn.canPlayerEdit(pos, side, stack);
         if (!flag1) {
            return false;
         } else {
            boolean flag2 = this.block.canPlaceBlockAt(worldIn, pos);
            if (!flag2) {
               return false;
            } else {
               placeDoor(worldIn, pos, EnumFacing.fromAngle((double)playerIn.rotationYaw), this.block);
               stack.stackSize--;
               return true;
            }
         }
      }
   }

   public static void placeDoor(World worldIn, BlockPos pos, EnumFacing facing, Block door) {
      String[] astring = new String[0];
      BlockPos blockpos = pos.offset(facing.rotateY());
      BlockPos blockpos1 = pos.offset(facing.rotateYCCW());
      boolean flag3 = worldIn.getBlockState(blockpos1).getBlock().isNormalCube();
      int i1 = flag3 ? 1 : 0;
      boolean flag4 = worldIn.getBlockState(blockpos1.up()).getBlock().isNormalCube();
      int k = i1;
      int i = k + (flag4 ? 1 : 0);
      boolean flag5 = worldIn.getBlockState(blockpos).getBlock().isNormalCube();
      int j1 = flag5 ? 1 : 0;
      boolean flag6 = worldIn.getBlockState(blockpos.up()).getBlock().isNormalCube();
      int l = j1;
      int j = l + (flag6 ? 1 : 0);
      Block blockx = worldIn.getBlockState(blockpos1).getBlock();
      boolean flag = blockx == door || worldIn.getBlockState(blockpos1.up()).getBlock() == door;
      Block block1 = worldIn.getBlockState(blockpos).getBlock();
      boolean flag1 = block1 == door || worldIn.getBlockState(blockpos.up()).getBlock() == door;
      boolean flag2 = false;
      if (flag && !flag1 || j > i) {
         flag2 = true;
      }

      BlockPos blockpos2 = pos.up();
      IBlockState iblockstate = door.getDefaultState()
         .withProperty(BlockDoor.FACING, facing)
         .withProperty(BlockDoor.HINGE, flag2 ? BlockDoor.EnumHingePosition.RIGHT : BlockDoor.EnumHingePosition.LEFT);
      worldIn.setBlockState(pos, iblockstate.withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER), 2);
      worldIn.setBlockState(blockpos2, iblockstate.withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.UPPER), 2);
      worldIn.notifyNeighborsOfStateChange(pos, door);
      worldIn.notifyNeighborsOfStateChange(blockpos2, door);
   }
}
