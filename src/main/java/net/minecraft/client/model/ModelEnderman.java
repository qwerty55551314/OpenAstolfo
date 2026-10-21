package net.minecraft.client.model;

import net.minecraft.entity.Entity;

public class ModelEnderman extends ModelBiped {
   public boolean isCarrying;
   public boolean isAttacking;

   public ModelEnderman(float f) {
      super(0.0F, -14.0F, 64, 32);
      float f1 = -14.0F;
      super.bipedHeadwear = new ModelRenderer(this, 0, 16);
      super.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, f - 0.5F);
      super.bipedHeadwear.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
      super.bipedBody = new ModelRenderer(this, 32, 16);
      super.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, f);
      super.bipedBody.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
      super.bipedRightArm = new ModelRenderer(this, 56, 0);
      super.bipedRightArm.addBox(-1.0F, -2.0F, -1.0F, 2, 30, 2, f);
      super.bipedRightArm.setRotationPoint(-3.0F, 2.0F + f1, 0.0F);
      super.bipedLeftArm = new ModelRenderer(this, 56, 0);
      super.bipedLeftArm.mirror = true;
      super.bipedLeftArm.addBox(-1.0F, -2.0F, -1.0F, 2, 30, 2, f);
      super.bipedLeftArm.setRotationPoint(5.0F, 2.0F + f1, 0.0F);
      super.bipedRightLeg = new ModelRenderer(this, 56, 0);
      super.bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 30, 2, f);
      super.bipedRightLeg.setRotationPoint(-2.0F, 12.0F + f1, 0.0F);
      super.bipedLeftLeg = new ModelRenderer(this, 56, 0);
      super.bipedLeftLeg.mirror = true;
      super.bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 30, 2, f);
      super.bipedLeftLeg.setRotationPoint(2.0F, 12.0F + f1, 0.0F);
   }

   @Override
   public void setRotationAngles(
      float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn
   ) {
      super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
      super.bipedHead.showModel = true;
      float f = -14.0F;
      super.bipedBody.rotateAngleX = 0.0F;
      super.bipedBody.rotationPointY = f;
      super.bipedBody.rotationPointZ = -0.0F;
      super.bipedRightLeg.rotateAngleX -= 0.0F;
      super.bipedLeftLeg.rotateAngleX -= 0.0F;
      super.bipedRightArm.rotateAngleX = (float)((double)super.bipedRightArm.rotateAngleX * 0.5);
      super.bipedLeftArm.rotateAngleX = (float)((double)super.bipedLeftArm.rotateAngleX * 0.5);
      super.bipedRightLeg.rotateAngleX = (float)((double)super.bipedRightLeg.rotateAngleX * 0.5);
      super.bipedLeftLeg.rotateAngleX = (float)((double)super.bipedLeftLeg.rotateAngleX * 0.5);
      float f1 = 0.4F;
      float f3;
      int i = (f3 = super.bipedRightArm.rotateAngleX - f1) == 0.0F ? 0 : (f3 < 0.0F ? -1 : 1);
      if (i > 0) {
         super.bipedRightArm.rotateAngleX = f1;
      }

      float f4;
      int j = (f4 = super.bipedLeftArm.rotateAngleX - f1) == 0.0F ? 0 : (f4 < 0.0F ? -1 : 1);
      if (j > 0) {
         super.bipedLeftArm.rotateAngleX = f1;
      }

      float f5;
      int k = (f5 = super.bipedRightArm.rotateAngleX - -f1) == 0.0F ? 0 : (f5 < 0.0F ? -1 : 1);
      if (k < 0) {
         super.bipedRightArm.rotateAngleX = -f1;
      }

      float f6;
      int l = (f6 = super.bipedLeftArm.rotateAngleX - -f1) == 0.0F ? 0 : (f6 < 0.0F ? -1 : 1);
      if (l < 0) {
         super.bipedLeftArm.rotateAngleX = -f1;
      }

      float f7;
      int i1 = (f7 = super.bipedRightLeg.rotateAngleX - f1) == 0.0F ? 0 : (f7 < 0.0F ? -1 : 1);
      if (i1 > 0) {
         super.bipedRightLeg.rotateAngleX = f1;
      }

      float f8;
      int j1 = (f8 = super.bipedLeftLeg.rotateAngleX - f1) == 0.0F ? 0 : (f8 < 0.0F ? -1 : 1);
      if (j1 > 0) {
         super.bipedLeftLeg.rotateAngleX = f1;
      }

      float f9;
      int k1 = (f9 = super.bipedRightLeg.rotateAngleX - -f1) == 0.0F ? 0 : (f9 < 0.0F ? -1 : 1);
      if (k1 < 0) {
         super.bipedRightLeg.rotateAngleX = -f1;
      }

      float f10;
      int l1 = (f10 = super.bipedLeftLeg.rotateAngleX - -f1) == 0.0F ? 0 : (f10 < 0.0F ? -1 : 1);
      if (l1 < 0) {
         super.bipedLeftLeg.rotateAngleX = -f1;
      }

      boolean flag = this.isCarrying;
      if (flag) {
         super.bipedRightArm.rotateAngleX = -0.5F;
         super.bipedLeftArm.rotateAngleX = -0.5F;
         super.bipedRightArm.rotateAngleZ = 0.05F;
         super.bipedLeftArm.rotateAngleZ = -0.05F;
      }

      super.bipedRightArm.rotationPointZ = 0.0F;
      super.bipedLeftArm.rotationPointZ = 0.0F;
      super.bipedRightLeg.rotationPointZ = 0.0F;
      super.bipedLeftLeg.rotationPointZ = 0.0F;
      super.bipedRightLeg.rotationPointY = 9.0F + f;
      super.bipedLeftLeg.rotationPointY = 9.0F + f;
      super.bipedHead.rotationPointZ = -0.0F;
      super.bipedHead.rotationPointY = f + 1.0F;
      super.bipedHeadwear.rotationPointX = super.bipedHead.rotationPointX;
      super.bipedHeadwear.rotationPointY = super.bipedHead.rotationPointY;
      super.bipedHeadwear.rotationPointZ = super.bipedHead.rotationPointZ;
      super.bipedHeadwear.rotateAngleX = super.bipedHead.rotateAngleX;
      super.bipedHeadwear.rotateAngleY = super.bipedHead.rotateAngleY;
      super.bipedHeadwear.rotateAngleZ = super.bipedHead.rotateAngleZ;
      if (this.isAttacking) {
         float f2 = 1.0F;
         super.bipedHead.rotationPointY -= f2 * 5.0F;
      }
   }
}
