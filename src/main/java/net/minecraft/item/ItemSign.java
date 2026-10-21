package net.minecraft.item;

import net.minecraft.block.BlockStandingSign;
import net.minecraft.block.BlockWallSign;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemSign extends Item {
   public ItemSign() {
      super.maxStackSize = 16;
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   @Override
   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
      if (side == EnumFacing.DOWN) {
         return false;
      } else {
         boolean flag = worldIn.getBlockState(pos).getBlock().getMaterial().isSolid();
         if (!flag) {
            return false;
         } else {
            pos = pos.offset(side);
            boolean flag1 = playerIn.canPlayerEdit(pos, side, stack);
            if (!flag1) {
               return false;
            } else {
               boolean flag2 = Blocks.standing_sign.canPlaceBlockAt(worldIn, pos);
               if (!flag2) {
                  return false;
               } else {
                  boolean flag3 = worldIn.isRemote;
                  if (flag3) {
                     return true;
                  } else {
                     if (side == EnumFacing.UP) {
                        int i = MathHelper.floor_double((double)((playerIn.rotationYaw + 180.0F) * 16.0F / 360.0F) + 0.5) & 15;
                        worldIn.setBlockState(pos, Blocks.standing_sign.getDefaultState().withProperty(BlockStandingSign.ROTATION, i), 3);
                     } else {
                        worldIn.setBlockState(pos, Blocks.wall_sign.getDefaultState().withProperty(BlockWallSign.FACING, side), 3);
                     }

                     stack.stackSize--;
                     TileEntity tileentity = worldIn.getTileEntity(pos);
                     if (tileentity instanceof TileEntitySign && !ItemBlock.setTileEntityNBT(worldIn, playerIn, pos, stack)) {
                        playerIn.openEditSign((TileEntitySign)tileentity);
                     }

                     return true;
                  }
               }
            }
         }
      }
   }
}
