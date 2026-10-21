package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;

public class ModelSkeleton extends ModelZombie {
   public ModelSkeleton() {
      this(0.0F, false);
   }

   public ModelSkeleton(float f, boolean bl) {
      super(f, 0.0F, 64, 32);
      if (!bl) {
         super.bipedRightArm = new ModelRenderer(this, 40, 16);
         super.bipedRightArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, f);
         super.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
         super.bipedLeftArm = new ModelRenderer(this, 40, 16);
         super.bipedLeftArm.mirror = true;
         super.bipedLeftArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, f);
         super.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
         super.bipedRightLeg = new ModelRenderer(this, 0, 16);
         super.bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, f);
         super.bipedRightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
         super.bipedLeftLeg = new ModelRenderer(this, 0, 16);
         super.bipedLeftLeg.mirror = true;
         super.bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, f);
         super.bipedLeftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
      }
   }

   @Override
   public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float f, float g, float partialTickTime) {
      super.aimedBow = ((EntitySkeleton)entitylivingbaseIn).getSkeletonType() == 1;
      super.setLivingAnimations(entitylivingbaseIn, f, g, partialTickTime);
   }

   @Override
   public void setRotationAngles(
      float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn
   ) {
      super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
   }
}
