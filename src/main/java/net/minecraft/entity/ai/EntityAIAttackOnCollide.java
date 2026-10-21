package net.minecraft.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityAIAttackOnCollide extends EntityAIBase {
   public World worldObj;
   public EntityCreature attacker;
   public int attackTick;
   public double speedTowardsTarget;
   public boolean longMemory;
   public PathEntity entityPathEntity;
   public Class<? extends Entity> classTarget;
   private int delayCounter;
   private double targetX;
   private double targetY;
   private double targetZ;

   public EntityAIAttackOnCollide(EntityCreature creature, Class<? extends Entity> targetClass, double speedIn, boolean useLongMemory) {
      this(creature, speedIn, useLongMemory);
      this.classTarget = targetClass;
   }

   public EntityAIAttackOnCollide(EntityCreature creature, double speedIn, boolean useLongMemory) {
      this.attacker = creature;
      this.worldObj = creature.worldObj;
      this.speedTowardsTarget = speedIn;
      this.longMemory = useLongMemory;
      this.setMutexBits(3);
   }

   @Override
   public boolean shouldExecute() {
      EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
      if (entitylivingbase == null) {
         return false;
      } else {
         boolean flag = entitylivingbase.isEntityAlive();
         if (!flag) {
            return false;
         } else if (this.classTarget != null && !this.classTarget.isAssignableFrom(entitylivingbase.getClass())) {
            return false;
         } else {
            this.entityPathEntity = this.attacker.getNavigator().getPathToEntityLiving(entitylivingbase);
            return this.entityPathEntity != null;
         }
      }
   }

   @Override
   public boolean continueExecuting() {
      EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
      boolean flag2;
      if (entitylivingbase == null) {
         flag2 = false;
      } else {
         boolean flag = entitylivingbase.isEntityAlive();
         if (!flag) {
            flag2 = false;
         } else if (!this.longMemory) {
            boolean flag1 = this.attacker.getNavigator().noPath();
            flag2 = !flag1;
         } else {
            flag2 = this.attacker.isWithinHomeDistanceFromPosition(new BlockPos(entitylivingbase));
         }
      }

      return flag2;
   }

   @Override
   public void startExecuting() {
      this.attacker.getNavigator().setPath(this.entityPathEntity, this.speedTowardsTarget);
      this.delayCounter = 0;
   }

   @Override
   public void resetTask() {
      this.attacker.getNavigator().clearPathEntity();
   }

   @Override
   public void updateTask() {
      EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
      this.attacker.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
      double d0 = this.attacker.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
      double d1 = this.func_179512_a(entitylivingbase);
      this.delayCounter--;
      boolean flag = this.longMemory;
      if (flag || this.attacker.getEntitySenses().canSee(entitylivingbase)) {
         int i = this.delayCounter;
         if (i <= 0
            && (
               this.targetX == 0.0 && this.targetY == 0.0 && this.targetZ == 0.0
                  || entitylivingbase.getDistanceSq(this.targetX, this.targetY, this.targetZ) >= 1.0
                  || this.attacker.getRNG().nextFloat() < 0.05F
            )) {
            this.targetX = entitylivingbase.posX;
            this.targetY = entitylivingbase.getEntityBoundingBox().minY;
            this.targetZ = entitylivingbase.posZ;
            this.delayCounter = 4 + this.attacker.getRNG().nextInt(7);
            if (d0 > 1024.0) {
               this.delayCounter += 10;
            } else if (d0 > 256.0) {
               this.delayCounter += 5;
            }

            if (!this.attacker.getNavigator().tryMoveToEntityLiving(entitylivingbase, this.speedTowardsTarget)) {
               this.delayCounter += 15;
            }
         }
      }

      this.attackTick = Math.max(this.attackTick - 1, 0);
      double d2;
      int j = (d2 = d0 - d1) == 0.0 ? 0 : (d2 < 0.0 ? -1 : 1);
      if (j <= 0 && this.attackTick <= 0) {
         this.attackTick = 20;
         EntityCreature entitycreature = this.attacker;
         if (entitycreature.getHeldItem() != null) {
            this.attacker.swingItem();
         }

         this.attacker.attackEntityAsMob(entitylivingbase);
      }
   }

   public double func_179512_a(EntityLivingBase attackTarget) {
      return (double)(this.attacker.width * 2.0F * this.attacker.width * 2.0F + attackTarget.width);
   }
}
