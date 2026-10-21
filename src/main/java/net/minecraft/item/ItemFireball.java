package net.minecraft.item;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemFireball extends Item {
   private static final String b = "item.fireCharge.use";

   public ItemFireball() {
      this.setCreativeTab(CreativeTabs.tabMisc);
   }

   @Override
   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
      boolean flag = worldIn.isRemote;
      if (flag) {
         return true;
      } else {
         pos = pos.offset(side);
         boolean flag1 = playerIn.canPlayerEdit(pos, side, stack);
         if (!flag1) {
            return false;
         } else {
            if (worldIn.getBlockState(pos).getBlock().getMaterial() == Material.air) {
               worldIn.playSoundEffect(
                  (double)pos.getX() + 0.5,
                  (double)pos.getY() + 0.5,
                  (double)pos.getZ() + 0.5,
                  b,
                  1.0F,
                  (Item.itemRand.nextFloat() - Item.itemRand.nextFloat()) * 0.2F + 1.0F
               );
               worldIn.setBlockState(pos, Blocks.fire.getDefaultState());
            }

            if (!playerIn.capabilities.isCreativeMode) {
               stack.stackSize--;
            }

            return true;
         }
      }
   }
}
