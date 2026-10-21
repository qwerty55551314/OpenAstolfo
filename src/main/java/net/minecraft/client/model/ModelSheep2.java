package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;

public class ModelSheep2 extends ModelQuadruped {
   private float headRotationAngleX;

   public ModelSheep2() {
      super(12, 0.0F);
      super.head = new ModelRenderer(this, 0, 0);
      super.head.addBox(-3.0F, -4.0F, -6.0F, 6, 6, 8, 0.0F);
      super.head.setRotationPoint(0.0F, 6.0F, -8.0F);
      super.body = new ModelRenderer(this, 28, 8);
      super.body.addBox(-4.0F, -10.0F, -7.0F, 8, 16, 6, 0.0F);
      super.body.setRotationPoint(0.0F, 5.0F, 2.0F);
   }

   @Override
   public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float f, float g, float partialTickTime) {
      super.setLivingAnimations(entitylivingbaseIn, f, g, partialTickTime);
      super.head.rotationPointY = 6.0F + ((EntitySheep)entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 9.0F;
      this.headRotationAngleX = ((EntitySheep)entitylivingbaseIn).getHeadRotationAngleX(partialTickTime);
   }

   @Override
   public void setRotationAngles(
      float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn
   ) {
      super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
      super.head.rotateAngleX = this.headRotationAngleX;
   }
}
