package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.util.MathHelper;

public class EntityAIArrowAttack extends EntityAIBase {
   private final EntityLiving entityHost;
   private final IRangedAttackMob rangedAttackEntityHost;
   private EntityLivingBase attackTarget;
   private int rangedAttackTime = -1;
   private double entityMoveSpeed;
   private int field_75318_f;
   private int field_96561_g;
   private int maxRangedAttackTime;
   private float field_96562_i;
   private float maxAttackDistance;
   private static final String c = "ArrowAttackGoal requires Mob implements RangedAttackMob";
   private static final long d = 2284995487067013140L;

   public EntityAIArrowAttack(IRangedAttackMob attacker, double movespeed, int i, float f) {
      this(attacker, movespeed, i, i, f);
   }

   public EntityAIArrowAttack(IRangedAttackMob attacker, double movespeed, int i, int maxAttackTime, float maxAttackDistanceIn) {
      if (!(attacker instanceof EntityLivingBase)) {
         throw new IllegalArgumentException(c);
      } else {
         this.rangedAttackEntityHost = attacker;
         this.entityHost = (EntityLiving)attacker;
         this.entityMoveSpeed = movespeed;
         this.field_96561_g = i;
         this.maxRangedAttackTime = maxAttackTime;
         this.field_96562_i = maxAttackDistanceIn;
         this.maxAttackDistance = maxAttackDistanceIn * maxAttackDistanceIn;
         this.setMutexBits(3);
      }
   }

   @Override
   public boolean shouldExecute() {
      EntityLivingBase entitylivingbase = this.entityHost.getAttackTarget();
      if (entitylivingbase == null) {
         return false;
      } else {
         this.attackTarget = entitylivingbase;
         return true;
      }
   }

   @Override
   public boolean continueExecuting() {
      return this.shouldExecute() || !this.entityHost.getNavigator().noPath();
   }

   @Override
   public void resetTask() {
      this.attackTarget = null;
      this.field_75318_f = 0;
      this.rangedAttackTime = -1;
   }

   @Override
   public void updateTask() {
      double d0 = this.entityHost.getDistanceSq(this.attackTarget.posX, this.attackTarget.getEntityBoundingBox().minY, this.attackTarget.posZ);
      boolean flag = this.entityHost.getEntitySenses().canSee(this.attackTarget);
      if (flag) {
         this.field_75318_f++;
      } else {
         this.field_75318_f = 0;
      }

      double d1;
      int i = (d1 = d0 - (double)this.maxAttackDistance) == 0.0 ? 0 : (d1 < 0.0 ? -1 : 1);
      if (i <= 0 && this.field_75318_f >= (int)d) {
         this.entityHost.getNavigator().clearPathEntity();
      } else {
         this.entityHost.getNavigator().tryMoveToEntityLiving(this.attackTarget, this.entityMoveSpeed);
      }

      this.entityHost.getLookHelper().setLookPositionWithEntity(this.attackTarget, 30.0F, 30.0F);
      int j = --this.rangedAttackTime;
      if (j == 0) {
         if (d0 > (double)this.maxAttackDistance || !flag) {
            return;
         }

         float f = MathHelper.sqrt_double(d0) / this.field_96562_i;
         float f1 = MathHelper.clamp_float(f, 0.1F, 1.0F);
         this.rangedAttackEntityHost.attackEntityWithRangedAttack(this.attackTarget, f1);
         this.rangedAttackTime = MathHelper.floor_float(f * (float)(this.maxRangedAttackTime - this.field_96561_g) + (float)this.field_96561_g);
      } else if (this.rangedAttackTime < 0) {
         float f2 = MathHelper.sqrt_double(d0) / this.field_96562_i;
         this.rangedAttackTime = MathHelper.floor_float(f2 * (float)(this.maxRangedAttackTime - this.field_96561_g) + (float)this.field_96561_g);
      }
   }
}
