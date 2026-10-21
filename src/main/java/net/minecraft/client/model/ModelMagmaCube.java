package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMagmaCube;

public class ModelMagmaCube extends ModelBase {
   public ModelRenderer[] segments = new ModelRenderer[8];
   public ModelRenderer core;

   public ModelMagmaCube() {
      for (int i = 0; i < this.segments.length; i++) {
         int b0 = 0;
         int j = i;
         if (i == 2) {
            b0 = 24;
            j = 10;
         } else if (i == 3) {
            b0 = 24;
            j = 19;
         }

         this.segments[i] = new ModelRenderer(this, b0, j);
         this.segments[i].addBox(-4.0F, (float)(16 + i), -4.0F, 8, 1, 8);
      }

      this.core = new ModelRenderer(this, 0, 16);
      this.core.addBox(-2.0F, 18.0F, -2.0F, 4, 4, 4);
   }

   @Override
   public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float f, float g, float partialTickTime) {
      EntityMagmaCube entitymagmacube = (EntityMagmaCube)entitylivingbaseIn;
      float f1 = entitymagmacube.prevSquishFactor + (entitymagmacube.squishFactor - entitymagmacube.prevSquishFactor) * partialTickTime;
      if (f1 < 0.0F) {
         f1 = 0.0F;
      }

      for (int i = 0; i < this.segments.length; i++) {
         this.segments[i].rotationPointY = (float)(-(4 - i)) * f1 * 1.7F;
      }
   }

   @Override
   public void render(Entity entityIn, float f, float g, float h, float f2, float f3, float scale) {
      this.setRotationAngles(f, g, h, f2, f3, scale, entityIn);
      this.core.render(scale);

      for (int i = 0; i < this.segments.length; i++) {
         this.segments[i].render(scale);
      }
   }
}
