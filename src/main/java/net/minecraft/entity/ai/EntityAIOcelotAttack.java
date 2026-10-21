package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityAIOcelotAttack extends EntityAIBase {
   public World theWorld;
   public EntityLiving theEntity;
   public EntityLivingBase theVictim;
   public int attackCountdown;
   private static final long b = -6304892637681811436L;

   public EntityAIOcelotAttack(EntityLiving theEntityIn) {
      this.theEntity = theEntityIn;
      this.theWorld = theEntityIn.worldObj;
      this.setMutexBits(3);
   }

   @Override
   public boolean shouldExecute() {
      EntityLivingBase entitylivingbase = this.theEntity.getAttackTarget();
      if (entitylivingbase == null) {
         return false;
      } else {
         this.theVictim = entitylivingbase;
         return true;
      }
   }

   @Override
   public boolean continueExecuting() {
      boolean flag = this.theVictim.isEntityAlive();
      boolean flag2;
      if (!flag) {
         flag2 = false;
      } else {
         double d0;
         int i = (d0 = this.theEntity.getDistanceSqToEntity(this.theVictim) - 225.0) == 0.0 ? 0 : (d0 < 0.0 ? -1 : 1);
         if (i > 0) {
            flag2 = false;
         } else {
            boolean flag1 = this.theEntity.getNavigator().noPath();
            flag2 = !flag1 || this.shouldExecute();
         }
      }

      return flag2;
   }

   @Override
   public void resetTask() {
      this.theVictim = null;
      this.theEntity.getNavigator().clearPathEntity();
   }

   @Override
   public void updateTask() {
      this.theEntity.getLookHelper().setLookPositionWithEntity(this.theVictim, 30.0F, 30.0F);
      double d0 = (double)(this.theEntity.width * 2.0F * this.theEntity.width * 2.0F);
      double d1 = this.theEntity.getDistanceSq(this.theVictim.posX, this.theVictim.getEntityBoundingBox().minY, this.theVictim.posZ);
      double d2 = 0.8;
      double d3;
      int i = (d3 = d1 - d0) == 0.0 ? 0 : (d3 < 0.0 ? -1 : 1);
      if (i > 0 && d1 < 16.0) {
         d2 = 1.33;
      } else {
         double d4;
         int j = (d4 = d1 - 225.0) == 0.0 ? 0 : (d4 < 0.0 ? -1 : 1);
         if (j < 0) {
            d2 = 0.6;
         }
      }

      this.theEntity.getNavigator().tryMoveToEntityLiving(this.theVictim, d2);
      this.attackCountdown = Math.max(this.attackCountdown - 1, 0);
      double d5;
      int k = (d5 = d1 - d0) == 0.0 ? 0 : (d5 < 0.0 ? -1 : 1);
      if (k <= 0) {
         int l = this.attackCountdown;
         if (l <= 0) {
            this.attackCountdown = (int)b;
            this.theEntity.attackEntityAsMob(this.theVictim);
         }
      }
   }
}
