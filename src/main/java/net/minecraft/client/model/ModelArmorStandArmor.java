package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;

public class ModelArmorStandArmor extends ModelBiped {
   public ModelArmorStandArmor() {
      this(0.0F);
   }

   public ModelArmorStandArmor(float modelSize) {
      this(modelSize, 64, 32);
   }

   public ModelArmorStandArmor(float modelSize, int textureWidthIn, int textureHeightIn) {
      super(modelSize, 0.0F, textureWidthIn, textureHeightIn);
   }

   @Override
   public void setRotationAngles(
      float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn
   ) {
      if (entityIn instanceof EntityArmorStand) {
         EntityArmorStand entityarmorstand = (EntityArmorStand)entityIn;
         super.bipedHead.rotateAngleX = (float) (Math.PI / 180.0) * entityarmorstand.getHeadRotation().getX();
         super.bipedHead.rotateAngleY = (float) (Math.PI / 180.0) * entityarmorstand.getHeadRotation().getY();
         super.bipedHead.rotateAngleZ = (float) (Math.PI / 180.0) * entityarmorstand.getHeadRotation().getZ();
         super.bipedHead.setRotationPoint(0.0F, 1.0F, 0.0F);
         super.bipedBody.rotateAngleX = (float) (Math.PI / 180.0) * entityarmorstand.getBodyRotation().getX();
         super.bipedBody.rotateAngleY = (float) (Math.PI / 180.0) * entityarmorstand.getBodyRotation().getY();
         super.bipedBody.rotateAngleZ = (float) (Math.PI / 180.0) * entityarmorstand.getBodyRotation().getZ();
         super.bipedLeftArm.rotateAngleX = (float) (Math.PI / 180.0) * entityarmorstand.getLeftArmRotation().getX();
         super.bipedLeftArm.rotateAngleY = (float) (Math.PI / 180.0) * entityarmorstand.getLeftArmRotation().getY();
         super.bipedLeftArm.rotateAngleZ = (float) (Math.PI / 180.0) * entityarmorstand.getLeftArmRotation().getZ();
         super.bipedRightArm.rotateAngleX = (float) (Math.PI / 180.0) * entityarmorstand.getRightArmRotation().getX();
         super.bipedRightArm.rotateAngleY = (float) (Math.PI / 180.0) * entityarmorstand.getRightArmRotation().getY();
         super.bipedRightArm.rotateAngleZ = (float) (Math.PI / 180.0) * entityarmorstand.getRightArmRotation().getZ();
         super.bipedLeftLeg.rotateAngleX = (float) (Math.PI / 180.0) * entityarmorstand.getLeftLegRotation().getX();
         super.bipedLeftLeg.rotateAngleY = (float) (Math.PI / 180.0) * entityarmorstand.getLeftLegRotation().getY();
         super.bipedLeftLeg.rotateAngleZ = (float) (Math.PI / 180.0) * entityarmorstand.getLeftLegRotation().getZ();
         super.bipedLeftLeg.setRotationPoint(1.9F, 11.0F, 0.0F);
         super.bipedRightLeg.rotateAngleX = (float) (Math.PI / 180.0) * entityarmorstand.getRightLegRotation().getX();
         super.bipedRightLeg.rotateAngleY = (float) (Math.PI / 180.0) * entityarmorstand.getRightLegRotation().getY();
         super.bipedRightLeg.rotateAngleZ = (float) (Math.PI / 180.0) * entityarmorstand.getRightLegRotation().getZ();
         super.bipedRightLeg.setRotationPoint(-1.9F, 11.0F, 0.0F);
         copyModelAngles(super.bipedHead, super.bipedHeadwear);
      }
   }
}
