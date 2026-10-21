package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;

public class EntityAIOwnerHurtTarget extends EntityAITarget {
   public EntityTameable theEntityTameable;
   public EntityLivingBase theTarget;
   private int j;

   public EntityAIOwnerHurtTarget(EntityTameable theEntityTameableIn) {
      super(theEntityTameableIn, false);
      this.theEntityTameable = theEntityTameableIn;
      this.setMutexBits(1);
   }

   @Override
   public boolean shouldExecute() {
      EntityTameable entitytameable = this.theEntityTameable;
      if (!entitytameable.isTamed()) {
         return false;
      } else {
         EntityLivingBase entitylivingbase = this.theEntityTameable.getOwner();
         if (entitylivingbase == null) {
            return false;
         } else {
            this.theTarget = entitylivingbase.getLastAttacker();
            int i = entitylivingbase.getLastAttackerTime();
            if (i != this.j) {
               boolean flag = this.isSuitableTarget(this.theTarget, false);
               if (flag) {
                  boolean flag1 = this.theEntityTameable.shouldAttackEntity(this.theTarget, entitylivingbase);
                  if (flag1) {
                     return true;
                  }
               }
            }

            return false;
         }
      }
   }

   @Override
   public void startExecuting() {
      super.taskOwner.setAttackTarget(this.theTarget);
      EntityLivingBase entitylivingbase = this.theEntityTameable.getOwner();
      if (entitylivingbase != null) {
         this.j = entitylivingbase.getLastAttackerTime();
      }

      super.startExecuting();
   }
}
