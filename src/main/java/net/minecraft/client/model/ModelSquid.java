package net.minecraft.client.model;

import net.minecraft.entity.Entity;

public class ModelSquid extends ModelBase {
   public ModelRenderer squidBody;
   public ModelRenderer[] squidTentacles = new ModelRenderer[8];

   public ModelSquid() {
      this.squidBody = new ModelRenderer(this, 0, 0);
      this.squidBody.addBox(-6.0F, -8.0F, -6.0F, 12, 16, 12);
      this.squidBody.rotationPointY += (float)(24 + -16);

      for (int i = 0; i < this.squidTentacles.length; i++) {
         this.squidTentacles[i] = new ModelRenderer(this, 48, 0);
         double d0 = (double)i * Math.PI * 2.0 / (double)this.squidTentacles.length;
         float f = (float)Math.cos(d0) * 5.0F;
         float f1 = (float)Math.sin(d0) * 5.0F;
         this.squidTentacles[i].addBox(-1.0F, 0.0F, -1.0F, 2, 18, 2);
         this.squidTentacles[i].rotationPointX = f;
         this.squidTentacles[i].rotationPointZ = f1;
         this.squidTentacles[i].rotationPointY = (float)(31 + -16);
         d0 = (double)i * Math.PI * -2.0 / (double)this.squidTentacles.length + (Math.PI / 2);
         this.squidTentacles[i].rotateAngleY = (float)d0;
      }
   }

   @Override
   public void setRotationAngles(
      float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn
   ) {
      for (ModelRenderer modelrenderer : this.squidTentacles) {
         modelrenderer.rotateAngleX = ageInTicks;
      }
   }

   @Override
   public void render(Entity entityIn, float f, float g, float h, float f2, float f3, float scale) {
      this.setRotationAngles(f, g, h, f2, f3, scale, entityIn);
      this.squidBody.render(scale);

      for (int i = 0; i < this.squidTentacles.length; i++) {
         this.squidTentacles[i].render(scale);
      }
   }
}
