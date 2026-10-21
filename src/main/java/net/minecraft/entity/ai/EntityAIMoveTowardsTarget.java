package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;

public class EntityAIMoveTowardsTarget extends EntityAIBase {
   private EntityCreature theEntity;
   private EntityLivingBase targetEntity;
   private double movePosX;
   private double movePosY;
   private double movePosZ;
   private double speed;
   private float maxTargetDistance;

   public EntityAIMoveTowardsTarget(EntityCreature creature, double speedIn, float targetMaxDistance) {
      this.theEntity = creature;
      this.speed = speedIn;
      this.maxTargetDistance = targetMaxDistance;
      this.setMutexBits(1);
   }

   @Override
   public boolean shouldExecute() {
      this.targetEntity = this.theEntity.getAttackTarget();
      if (this.targetEntity == null) {
         return false;
      } else if (this.targetEntity.getDistanceSqToEntity(this.theEntity) > (double)(this.maxTargetDistance * this.maxTargetDistance)) {
         return false;
      } else {
         Vec3 vec3 = RandomPositionGenerator.findRandomTargetBlockTowards(
            this.theEntity, 16, 7, new Vec3(this.targetEntity.posX, this.targetEntity.posY, this.targetEntity.posZ)
         );
         if (vec3 == null) {
            return false;
         } else {
            this.movePosX = vec3.xCoord;
            this.movePosY = vec3.yCoord;
            this.movePosZ = vec3.zCoord;
            return true;
         }
      }
   }

   @Override
   public boolean continueExecuting() {
      boolean flag = this.theEntity.getNavigator().noPath();
      if (!flag) {
         boolean flag1 = this.targetEntity.isEntityAlive();
         if (flag1) {
            double d0;
            int i = (d0 = this.targetEntity.getDistanceSqToEntity(this.theEntity) - (double)(this.maxTargetDistance * this.maxTargetDistance)) == 0.0
               ? 0
               : (d0 < 0.0 ? -1 : 1);
            if (i < 0) {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public void resetTask() {
      this.targetEntity = null;
   }

   @Override
   public void startExecuting() {
      this.theEntity.getNavigator().tryMoveToXYZ(this.movePosX, this.movePosY, this.movePosZ, this.speed);
   }
}
