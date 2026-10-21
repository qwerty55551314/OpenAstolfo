package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;

public class ItemSaddle extends Item {
   private static final String b = "mob.horse.leather";

   public ItemSaddle() {
      super.maxStackSize = 1;
      this.setCreativeTab(CreativeTabs.tabTransport);
   }

   @Override
   public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target) {
      if (target instanceof EntityPig) {
         EntityPig entitypig = (EntityPig)target;
         if (!entitypig.getSaddled() && !entitypig.isChild()) {
            entitypig.setSaddled(true);
            entitypig.worldObj.playSoundAtEntity(entitypig, b, 0.5F, 1.0F);
            stack.stackSize--;
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
      this.itemInteractionForEntity(stack, (EntityPlayer)null, target);
      return true;
   }
}
