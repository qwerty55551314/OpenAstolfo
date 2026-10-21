package net.minecraft.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class EntityDamageSource extends DamageSource {
   public Entity damageSourceEntity;
   private boolean isThornsDamage = false;

   public EntityDamageSource(String damageTypeIn, Entity damageSourceEntityIn) {
      super(damageTypeIn);
      this.damageSourceEntity = damageSourceEntityIn;
   }

   public EntityDamageSource setIsThornsDamage() {
      this.isThornsDamage = true;
      return this;
   }

   public boolean getIsThornsDamage() {
      return this.isThornsDamage;
   }

   @Override
   public Entity getEntity() {
      return this.damageSourceEntity;
   }

   @Override
   public IChatComponent getDeathMessage(EntityLivingBase entityLivingBaseIn) {
      Entity entity = this.damageSourceEntity;
      ItemStack itemstack = entity instanceof EntityLivingBase ? ((EntityLivingBase)this.damageSourceEntity).getHeldItem() : null;
      String s = "death.attack." + super.damageType;
      String s1 = s + ".item";
      if (itemstack != null) {
         boolean flag = itemstack.hasDisplayName();
         if (flag && StatCollector.canTranslate(s1)) {
            return new ChatComponentTranslation(s1, entityLivingBaseIn.getDisplayName(), this.damageSourceEntity.getDisplayName(), itemstack.getChatComponent());
         }
      }

      return new ChatComponentTranslation(s, entityLivingBaseIn.getDisplayName(), this.damageSourceEntity.getDisplayName());
   }

   @Override
   public boolean isDifficultyScaled() {
      Entity entity = this.damageSourceEntity;
      if (entity != null) {
         boolean flag = this.damageSourceEntity instanceof EntityLivingBase;
         if (flag) {
            boolean flag1 = this.damageSourceEntity instanceof EntityPlayer;
            if (!flag1) {
               return true;
            }
         }
      }

      return false;
   }
}
