package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.MathHelper;

public class EntityMoveHelper {
   public EntityLiving entity;
   public double posX;
   public double posY;
   public double posZ;
   public double speed;
   public boolean update;

   public EntityMoveHelper(EntityLiving entitylivingIn) {
      this.entity = entitylivingIn;
      this.posX = entitylivingIn.posX;
      this.posY = entitylivingIn.posY;
      this.posZ = entitylivingIn.posZ;
   }

   public boolean isUpdating() {
      return this.update;
   }

   public double getSpeed() {
      return this.speed;
   }

   public void setMoveTo(double x, double y, double z, double speedIn) {
      this.posX = x;
      this.posY = y;
      this.posZ = z;
      this.speed = speedIn;
      this.update = true;
   }

   public void onUpdateMoveHelper() {
      this.entity.setMoveForward(0.0F);
      boolean flag = this.update;
      if (flag) {
         this.update = false;
         int i = MathHelper.floor_double(this.entity.getEntityBoundingBox().minY + 0.5);
         double d0 = this.posX - this.entity.posX;
         double d1 = this.posZ - this.entity.posZ;
         double d2 = this.posY - (double)i;
         double d3 = d0 * d0 + d2 * d2 + d1 * d1;
         double d4 = 2.5000003E-7F;
         if (d3 >= d4) {
            float f = (float)(MathHelper.atan2(d1, d0) * 180.0 / Math.PI) - 90.0F;
            this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, f, 30.0F);
            this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()));
            double d5;
            int j = (d5 = d2 - 0.0) == 0.0 ? 0 : (d5 < 0.0 ? -1 : 1);
            if (j > 0 && d0 * d0 + d1 * d1 < 1.0) {
               this.entity.getJumpHelper().setJumping();
            }
         }
      }
   }

   public float limitAngle(float f, float g, float h) {
      float f1 = MathHelper.wrapAngleTo180_float(g - f);
      float f2;
      int i = (f2 = f1 - h) == 0.0F ? 0 : (f2 < 0.0F ? -1 : 1);
      if (i > 0) {
         f1 = h;
      }

      if (f1 < -h) {
         f1 = -h;
      }

      float f1x = f + f1;
      float f3;
      int j = (f3 = f1x - 0.0F) == 0.0F ? 0 : (f3 < 0.0F ? -1 : 1);
      if (j < 0) {
         f1x += 360.0F;
      } else if (f1x > 360.0F) {
         f1x -= 360.0F;
      }

      return f1x;
   }

   public double getX() {
      return this.posX;
   }

   public double getY() {
      return this.posY;
   }

   public double getZ() {
      return this.posZ;
   }
}
