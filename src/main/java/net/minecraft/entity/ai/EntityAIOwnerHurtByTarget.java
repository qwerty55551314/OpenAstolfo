package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;

public class EntityAIOwnerHurtByTarget extends EntityAITarget {
   public EntityTameable theDefendingTameable;
   public EntityLivingBase theOwnerAttacker;
   private int p;

   public EntityAIOwnerHurtByTarget(EntityTameable theDefendingTameableIn) {
      super(theDefendingTameableIn, false);
      this.theDefendingTameable = theDefendingTameableIn;
      this.setMutexBits(1);
   }

   @Override
   public boolean shouldExecute() {
      EntityTameable entitytameable = this.theDefendingTameable;
      if (!entitytameable.isTamed()) {
         return false;
      } else {
         EntityLivingBase entitylivingbase = this.theDefendingTameable.getOwner();
         if (entitylivingbase == null) {
            return false;
         } else {
            this.theOwnerAttacker = entitylivingbase.getAITarget();
            int i = entitylivingbase.getRevengeTimer();
            if (i != this.p) {
               boolean flag = this.isSuitableTarget(this.theOwnerAttacker, false);
               if (flag) {
                  boolean flag1 = this.theDefendingTameable.shouldAttackEntity(this.theOwnerAttacker, entitylivingbase);
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
      super.taskOwner.setAttackTarget(this.theOwnerAttacker);
      EntityLivingBase entitylivingbase = this.theDefendingTameable.getOwner();
      if (entitylivingbase != null) {
         this.p = entitylivingbase.getRevengeTimer();
      }

      super.startExecuting();
   }
}
