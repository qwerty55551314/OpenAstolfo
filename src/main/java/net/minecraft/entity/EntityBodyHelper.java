package net.minecraft.entity;

import net.minecraft.util.MathHelper;

public class EntityBodyHelper {
   private EntityLivingBase theLiving;
   private int rotationTickCounter;
   private float prevRenderYawHead;

   public EntityBodyHelper(EntityLivingBase entityLivingBase) {
      this.theLiving = entityLivingBase;
   }

   public void updateRenderAngles() {
      double d0 = this.theLiving.posX - this.theLiving.prevPosX;
      double d1 = this.theLiving.posZ - this.theLiving.prevPosZ;
      if (d0 * d0 + d1 * d1 > 2.5000003E-7F) {
         this.theLiving.renderYawOffset = this.theLiving.rotationYaw;
         this.theLiving.rotationYawHead = this.computeAngleWithBound(this.theLiving.renderYawOffset, this.theLiving.rotationYawHead, 75.0F);
         this.prevRenderYawHead = this.theLiving.rotationYawHead;
         this.rotationTickCounter = 0;
      } else {
         float f = 75.0F;
         float f1;
         int i = (f1 = Math.abs(this.theLiving.rotationYawHead - this.prevRenderYawHead) - 15.0F) == 0.0F ? 0 : (f1 < 0.0F ? -1 : 1);
         if (i > 0) {
            this.rotationTickCounter = 0;
            this.prevRenderYawHead = this.theLiving.rotationYawHead;
         } else {
            this.rotationTickCounter++;
            if (this.rotationTickCounter > 10) {
               f = Math.max(1.0F - (float)(this.rotationTickCounter - 10) / 10.0F, 0.0F) * 75.0F;
            }
         }

         this.theLiving.renderYawOffset = this.computeAngleWithBound(this.theLiving.rotationYawHead, this.theLiving.renderYawOffset, f);
      }
   }

   private float computeAngleWithBound(float f, float g, float h) {
      float f1 = MathHelper.wrapAngleTo180_float(f - g);
      if (f1 < -h) {
         f1 = -h;
      }

      if (f1 >= h) {
         f1 = h;
      }

      return f - f1;
   }
}
