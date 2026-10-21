package net.minecraft.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class EntityDamageSourceIndirect extends EntityDamageSource {
   private Entity indirectEntity;

   public EntityDamageSourceIndirect(String damageTypeIn, Entity source, Entity indirectEntityIn) {
      super(damageTypeIn, source);
      this.indirectEntity = indirectEntityIn;
   }

   @Override
   public Entity getSourceOfDamage() {
      return super.damageSourceEntity;
   }

   @Override
   public Entity getEntity() {
      return this.indirectEntity;
   }

   @Override
   public IChatComponent getDeathMessage(EntityLivingBase entityLivingBaseIn) {
      Entity entity = this.indirectEntity;
      IChatComponent ichatcomponent = entity == null ? super.damageSourceEntity.getDisplayName() : this.indirectEntity.getDisplayName();
      Entity entity1 = this.indirectEntity;
      ItemStack itemstack = entity1 instanceof EntityLivingBase ? ((EntityLivingBase)this.indirectEntity).getHeldItem() : null;
      String s = "death.attack." + super.damageType;
      String s1 = s + ".item";
      if (itemstack != null) {
         boolean flag = itemstack.hasDisplayName();
         if (flag && StatCollector.canTranslate(s1)) {
            return new ChatComponentTranslation(s1, entityLivingBaseIn.getDisplayName(), ichatcomponent, itemstack.getChatComponent());
         }
      }

      return new ChatComponentTranslation(s, entityLivingBaseIn.getDisplayName(), ichatcomponent);
   }
}
