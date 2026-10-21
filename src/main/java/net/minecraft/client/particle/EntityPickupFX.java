package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class EntityPickupFX extends EntityFX {
   private Entity field_174840_a;
   private Entity field_174843_ax;
   private int age;
   private int maxAge;
   private float field_174841_aA;
   private RenderManager field_174842_aB = Minecraft.getMinecraft().getRenderManager();

   public EntityPickupFX(World worldIn, Entity entity, Entity entity2, float f) {
      super(worldIn, entity.posX, entity.posY, entity.posZ, entity.motionX, entity.motionY, entity.motionZ);
      this.field_174840_a = entity;
      this.field_174843_ax = entity2;
      this.maxAge = 3;
      this.field_174841_aA = f;
   }

   @Override
   public void renderParticle(
      WorldRenderer worldRendererIn,
      Entity entityIn,
      float partialTicks,
      float rotationX,
      float rotationZ,
      float rotationYZ,
      float rotationXY,
      float rotationXZ
   ) {
      float f = ((float)this.age + partialTicks) / (float)this.maxAge;
      f *= f;
      double d0 = this.field_174840_a.posX;
      double d1 = this.field_174840_a.posY;
      double d2 = this.field_174840_a.posZ;
      double d3 = this.field_174843_ax.lastTickPosX + (this.field_174843_ax.posX - this.field_174843_ax.lastTickPosX) * (double)partialTicks;
      double d4 = this.field_174843_ax.lastTickPosY
         + (this.field_174843_ax.posY - this.field_174843_ax.lastTickPosY) * (double)partialTicks
         + (double)this.field_174841_aA;
      double d5 = this.field_174843_ax.lastTickPosZ + (this.field_174843_ax.posZ - this.field_174843_ax.lastTickPosZ) * (double)partialTicks;
      double d6 = d0 + (d3 - d0) * (double)f;
      double d7 = d1 + (d4 - d1) * (double)f;
      double d8 = d2 + (d5 - d2) * (double)f;
      int i = this.getBrightnessForRender(partialTicks);
      int j = i % 65536;
      int k = i / 65536;
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j / 1.0F, (float)k / 1.0F);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      d6 -= EntityFX.interpPosX;
      d7 -= EntityFX.interpPosY;
      d8 -= EntityFX.interpPosZ;
      this.field_174842_aB
         .renderEntityWithPosYaw(
            this.field_174840_a, (double)((float)d6), (double)((float)d7), (double)((float)d8), this.field_174840_a.rotationYaw, partialTicks
         );
   }

   @Override
   public void onUpdate() {
      int b0 = 70;
      this.age++;
      if (this.age == this.maxAge) {
         this.setDead();
      }
   }

   @Override
   public int getFXLayer() {
      return 3;
   }
}
