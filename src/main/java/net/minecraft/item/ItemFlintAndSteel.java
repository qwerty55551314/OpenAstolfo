package net.minecraft.item;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemFlintAndSteel extends Item {
   private static final String b = "fire.ignite";
   private static final long d = 6637680872873001024L;

   public ItemFlintAndSteel() {
      super.maxStackSize = 1;
      this.setMaxDamage((int)d);
      this.setCreativeTab(CreativeTabs.tabTools);
   }

   @Override
   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
      String[] astring = new String[0];
      pos = pos.offset(side);
      if (!playerIn.canPlayerEdit(pos, side, stack)) {
         return false;
      } else {
         if (worldIn.getBlockState(pos).getBlock().getMaterial() == Material.air) {
            worldIn.playSoundEffect(
               (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, b, 1.0F, Item.itemRand.nextFloat() * 0.4F + 0.8F
            );
            worldIn.setBlockState(pos, Blocks.fire.getDefaultState());
         }

         stack.damageItem(1, playerIn);
         return true;
      }
   }
}
