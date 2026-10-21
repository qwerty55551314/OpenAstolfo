package net.minecraft.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class RenderFish extends Render<EntityFishHook> {
   private static final ResourceLocation FISH_PARTICLES = new ResourceLocation("textures/particle/particles.png");

   public RenderFish(RenderManager renderManagerIn) {
      super(renderManagerIn);
   }

   public void doRender(EntityFishHook entity, double x, double y, double z, float entityYaw, float partialTicks) {
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x, (float)y, (float)z);
      GlStateManager.enableRescaleNormal();
      GlStateManager.scale(0.5F, 0.5F, 0.5F);
      this.bindEntityTexture(entity);
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      GlStateManager.rotate(180.0F - super.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(-super.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
      worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
      worldrenderer.pos(-0.5, -0.5, 0.0).tex(0.0625, 0.1875).normal(0.0F, 1.0F, 0.0F).endVertex();
      worldrenderer.pos(0.5, -0.5, 0.0).tex(0.125, 0.1875).normal(0.0F, 1.0F, 0.0F).endVertex();
      worldrenderer.pos(0.5, 0.5, 0.0).tex(0.125, 0.125).normal(0.0F, 1.0F, 0.0F).endVertex();
      worldrenderer.pos(-0.5, 0.5, 0.0).tex(0.0625, 0.125).normal(0.0F, 1.0F, 0.0F).endVertex();
      tessellator.draw();
      GlStateManager.disableRescaleNormal();
      GlStateManager.popMatrix();
      EntityPlayer entityplayer = entity.angler;
      if (entityplayer != null) {
         float f = entity.angler.getSwingProgress(partialTicks);
         float f1 = MathHelper.sin(MathHelper.sqrt_float(f) * (float) Math.PI);
         Vec3 vec3 = new Vec3(-0.36, 0.03, 0.35);
         vec3 = vec3.rotatePitch(
            -(entity.angler.prevRotationPitch + (entity.angler.rotationPitch - entity.angler.prevRotationPitch) * partialTicks) * (float) Math.PI / 180.0F
         );
         vec3 = vec3.rotateYaw(
            -(entity.angler.prevRotationYaw + (entity.angler.rotationYaw - entity.angler.prevRotationYaw) * partialTicks) * (float) Math.PI / 180.0F
         );
         vec3 = vec3.rotateYaw(f1 * 0.5F);
         vec3 = vec3.rotatePitch(-f1 * 0.7F);
         double d0 = entity.angler.prevPosX + (entity.angler.posX - entity.angler.prevPosX) * (double)partialTicks + vec3.xCoord;
         double d1 = entity.angler.prevPosY + (entity.angler.posY - entity.angler.prevPosY) * (double)partialTicks + vec3.yCoord;
         double d2 = entity.angler.prevPosZ + (entity.angler.posZ - entity.angler.prevPosZ) * (double)partialTicks + vec3.zCoord;
         double d3 = (double)entity.angler.getEyeHeight();
         GameSettings gamesettings = super.renderManager.options;
         if (gamesettings != null && super.renderManager.options.thirdPersonView > 0 || entity.angler != Minecraft.getMinecraft().thePlayer) {
            float f2 = (entity.angler.prevRenderYawOffset + (entity.angler.renderYawOffset - entity.angler.prevRenderYawOffset) * partialTicks)
               * (float) Math.PI
               / 180.0F;
            double d4 = (double)MathHelper.sin(f2);
            double d6 = (double)MathHelper.cos(f2);
            d0 = entity.angler.prevPosX + (entity.angler.posX - entity.angler.prevPosX) * (double)partialTicks - d6 * 0.35 - d4 * 0.8;
            d1 = entity.angler.prevPosY + d3 + (entity.angler.posY - entity.angler.prevPosY) * (double)partialTicks - 0.45;
            double d11 = entity.angler.prevPosZ + (entity.angler.posZ - entity.angler.prevPosZ) * (double)partialTicks - d4 * 0.35 + d6 * 0.8;
            d2 = d11;
            d3 = entity.angler.isSneaking() ? -0.1875 : 0.0;
         }

         double d12 = entity.prevPosX + (entity.posX - entity.prevPosX) * (double)partialTicks;
         double d5 = entity.prevPosY + (entity.posY - entity.prevPosY) * (double)partialTicks + 0.25;
         double d7 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double)partialTicks;
         double d8 = (double)((float)(d0 - d12));
         double d9 = (double)((float)(d1 - d5)) + d3;
         double d10 = (double)((float)(d2 - d7));
         GlStateManager.disableTexture2D();
         GlStateManager.disableLighting();
         worldrenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);

         for (int i = 0; i <= 16; i++) {
            float f3 = (float)i / 16.0F;
            worldrenderer.pos(x + d8 * (double)f3, y + d9 * (double)(f3 * f3 + f3) * 0.5 + 0.25, z + d10 * (double)f3).color(0, 0, 0, 255).endVertex();
         }

         tessellator.draw();
         GlStateManager.enableLighting();
         GlStateManager.enableTexture2D();
         super.doRender(entity, x, y, z, entityYaw, partialTicks);
      }
   }

   public ResourceLocation getEntityTexture(EntityFishHook entity) {
      return FISH_PARTICLES;
   }
}
