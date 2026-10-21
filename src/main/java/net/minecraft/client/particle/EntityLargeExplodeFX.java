package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityLargeExplodeFX extends EntityFX {
   private static final ResourceLocation EXPLOSION_TEXTURE = new ResourceLocation("textures/entity/explosion.png");
   private static final VertexFormat field_181549_az = new VertexFormat()
      .addElement(DefaultVertexFormats.POSITION_3F)
      .addElement(DefaultVertexFormats.TEX_2F)
      .addElement(DefaultVertexFormats.COLOR_4UB)
      .addElement(DefaultVertexFormats.TEX_2S)
      .addElement(DefaultVertexFormats.NORMAL_3B)
      .addElement(DefaultVertexFormats.PADDING_1B);
   private int field_70581_a;
   private int field_70584_aq;
   private TextureManager theRenderEngine;
   private float field_70582_as;

   public EntityLargeExplodeFX(TextureManager renderEngine, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double d, double e, double d2) {
      super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0, 0.0, 0.0);
      this.theRenderEngine = renderEngine;
      this.field_70584_aq = 6 + super.rand.nextInt(4);
      super.particleRed = super.particleGreen = super.particleBlue = super.rand.nextFloat() * 0.6F + 0.4F;
      this.field_70582_as = 1.0F - (float)d * 0.5F;
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
      int i = (int)(((float)this.field_70581_a + partialTicks) * 15.0F / (float)this.field_70584_aq);
      if (i <= 15) {
         this.theRenderEngine.bindTexture(EXPLOSION_TEXTURE);
         float f = (float)(i % 4) / 4.0F;
         float f1 = f + 0.24975F;
         float f2 = (float)(i / 4) / 4.0F;
         float f3 = f2 + 0.24975F;
         float f4 = 2.0F * this.field_70582_as;
         float f5 = (float)(super.prevPosX + (super.posX - super.prevPosX) * (double)partialTicks - EntityFX.interpPosX);
         float f6 = (float)(super.prevPosY + (super.posY - super.prevPosY) * (double)partialTicks - EntityFX.interpPosY);
         float f7 = (float)(super.prevPosZ + (super.posZ - super.prevPosZ) * (double)partialTicks - EntityFX.interpPosZ);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.disableLighting();
         RenderHelper.disableStandardItemLighting();
         worldRendererIn.begin(7, field_181549_az);
         worldRendererIn.pos((double)(f5 - rotationX * f4 - rotationXY * f4), (double)(f6 - rotationZ * f4), (double)(f7 - rotationYZ * f4 - rotationXZ * f4))
            .tex((double)f1, (double)f3)
            .color(super.particleRed, super.particleGreen, super.particleBlue, 1.0F)
            .lightmap(0, 240)
            .normal(0.0F, 1.0F, 0.0F)
            .endVertex();
         worldRendererIn.pos((double)(f5 - rotationX * f4 + rotationXY * f4), (double)(f6 + rotationZ * f4), (double)(f7 - rotationYZ * f4 + rotationXZ * f4))
            .tex((double)f1, (double)f2)
            .color(super.particleRed, super.particleGreen, super.particleBlue, 1.0F)
            .lightmap(0, 240)
            .normal(0.0F, 1.0F, 0.0F)
            .endVertex();
         worldRendererIn.pos((double)(f5 + rotationX * f4 + rotationXY * f4), (double)(f6 + rotationZ * f4), (double)(f7 + rotationYZ * f4 + rotationXZ * f4))
            .tex((double)f, (double)f2)
            .color(super.particleRed, super.particleGreen, super.particleBlue, 1.0F)
            .lightmap(0, 240)
            .normal(0.0F, 1.0F, 0.0F)
            .endVertex();
         worldRendererIn.pos((double)(f5 + rotationX * f4 - rotationXY * f4), (double)(f6 - rotationZ * f4), (double)(f7 + rotationYZ * f4 - rotationXZ * f4))
            .tex((double)f, (double)f3)
            .color(super.particleRed, super.particleGreen, super.particleBlue, 1.0F)
            .lightmap(0, 240)
            .normal(0.0F, 1.0F, 0.0F)
            .endVertex();
         Tessellator.getInstance().draw();
         GlStateManager.enableLighting();
      }
   }

   @Override
   public int getBrightnessForRender(float partialTicks) {
      return 61680;
   }

   @Override
   public void onUpdate() {
      super.prevPosX = super.posX;
      super.prevPosY = super.posY;
      boolean flag = false;
      super.prevPosZ = super.posZ;
      this.field_70581_a++;
      if (this.field_70581_a == this.field_70584_aq) {
         this.setDead();
      }
   }

   @Override
   public int getFXLayer() {
      return 3;
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... ints
      ) {
         return new EntityLargeExplodeFX(Minecraft.getMinecraft().getTextureManager(), worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
      }
   }
}
