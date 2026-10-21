package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class ItemNameTag extends Item {
   public ItemNameTag() {
      this.setCreativeTab(CreativeTabs.tabTools);
   }

   @Override
   public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target) {
      boolean flag = stack.hasDisplayName();
      if (!flag) {
         return false;
      } else {
         boolean flag1 = target instanceof EntityLiving;
         if (flag1) {
            EntityLiving entityliving = (EntityLiving)target;
            entityliving.setCustomNameTag(stack.getDisplayName());
            entityliving.enablePersistence();
            stack.stackSize--;
            return true;
         } else {
            return super.itemInteractionForEntity(stack, playerIn, target);
         }
      }
   }
}
