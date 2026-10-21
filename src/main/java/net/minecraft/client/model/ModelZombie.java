package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelZombie extends ModelBiped {
   public ModelZombie() {
      this(0.0F, false);
   }

   public ModelZombie(float modelSize, float f, int textureWidthIn, int textureHeightIn) {
      super(modelSize, f, textureWidthIn, textureHeightIn);
   }

   public ModelZombie(float modelSize, boolean bl) {
      super(modelSize, 0.0F, 64, bl ? 32 : 64);
   }

   @Override
   public void setRotationAngles(
      float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn
   ) {
      super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
      float f = MathHelper.sin(super.swingProgress * (float) Math.PI);
      float f1 = MathHelper.sin((1.0F - (1.0F - super.swingProgress) * (1.0F - super.swingProgress)) * (float) Math.PI);
      super.bipedRightArm.rotateAngleZ = 0.0F;
      super.bipedLeftArm.rotateAngleZ = 0.0F;
      super.bipedRightArm.rotateAngleY = -(0.1F - f * 0.6F);
      super.bipedLeftArm.rotateAngleY = 0.1F - f * 0.6F;
      super.bipedRightArm.rotateAngleX = (float) (-Math.PI / 2);
      super.bipedLeftArm.rotateAngleX = (float) (-Math.PI / 2);
      super.bipedRightArm.rotateAngleX -= f * 1.2F - f1 * 0.4F;
      super.bipedLeftArm.rotateAngleX -= f * 1.2F - f1 * 0.4F;
      super.bipedRightArm.rotateAngleZ = super.bipedRightArm.rotateAngleZ + MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
      super.bipedLeftArm.rotateAngleZ = super.bipedLeftArm.rotateAngleZ - (MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F);
      super.bipedRightArm.rotateAngleX = super.bipedRightArm.rotateAngleX + MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
      super.bipedLeftArm.rotateAngleX = super.bipedLeftArm.rotateAngleX - MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
   }
}
