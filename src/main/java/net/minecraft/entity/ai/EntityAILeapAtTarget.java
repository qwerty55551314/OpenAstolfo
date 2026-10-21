package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class EntityAILeapAtTarget extends EntityAIBase {
   public EntityLiving leaper;
   public EntityLivingBase leapTarget;
   public float leapMotionY;

   public EntityAILeapAtTarget(EntityLiving leapingEntity, float leapMotionYIn) {
      this.leaper = leapingEntity;
      this.leapMotionY = leapMotionYIn;
      this.setMutexBits(5);
   }

   @Override
   public boolean shouldExecute() {
      this.leapTarget = this.leaper.getAttackTarget();
      if (this.leapTarget == null) {
         return false;
      } else {
         double d0 = this.leaper.getDistanceSqToEntity(this.leapTarget);
         double d1;
         int i = (d1 = d0 - 4.0) == 0.0 ? 0 : (d1 < 0.0 ? -1 : 1);
         if (i >= 0) {
            double d2;
            int j = (d2 = d0 - 16.0) == 0.0 ? 0 : (d2 < 0.0 ? -1 : 1);
            if (j <= 0) {
               boolean flag = this.leaper.onGround;
               boolean flag1;
               if (!flag) {
                  flag1 = false;
               } else {
                  int k = this.leaper.getRNG().nextInt(5);
                  flag1 = k == 0;
               }

               return flag1;
            }
         }

         return false;
      }
   }

   @Override
   public boolean continueExecuting() {
      return !this.leaper.onGround;
   }

   @Override
   public void startExecuting() {
      double d0 = this.leapTarget.posX - this.leaper.posX;
      double d1 = this.leapTarget.posZ - this.leaper.posZ;
      float f = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
      this.leaper.motionX = this.leaper.motionX + d0 / (double)f * 0.5 * 0.8F + this.leaper.motionX * 0.2F;
      this.leaper.motionZ = this.leaper.motionZ + d1 / (double)f * 0.5 * 0.8F + this.leaper.motionZ * 0.2F;
      this.leaper.motionY = (double)this.leapMotionY;
   }
}
