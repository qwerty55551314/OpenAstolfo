package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class CloudRenderer {
   private Minecraft mc;
   private boolean updated = false;
   private boolean renderFancy = false;
   public int cloudTickCounter;
   public float partialTicks;
   private int updateCloudTickCounter = -1;
   private int glListClouds = 0;
   private double updatePlayerX = 0.0;
   private double updatePlayerY = 0.0;
   private double updatePlayerZ = 0.0;

   public CloudRenderer(Minecraft mc) {
      this.mc = mc;
      this.updateCloudTickCounter = GLAllocation.generateDisplayLists(1);
   }

   public void prepareToRender(boolean bl, int i, float f) {
      if (this.renderFancy != bl) {
         this.updated = false;
      }

      this.renderFancy = bl;
      this.cloudTickCounter = i;
      this.partialTicks = f;
   }

   public boolean shouldUpdateGlList() {
      boolean flag2 = this.updated;
      if (!flag2) {
         return true;
      } else if (this.cloudTickCounter >= this.glListClouds + 20) {
         return true;
      } else {
         Entity entity = this.mc.getRenderViewEntity();
         double d0;
         int i = (d0 = this.updatePlayerY + (double)entity.getEyeHeight() - (128.0 + (double)(this.mc.gameSettings.ofCloudsHeight * 128.0F))) == 0.0
            ? 0
            : (d0 < 0.0 ? -1 : 1);
         boolean flag = i < 0;
         double d1;
         int j = (d1 = entity.prevPosY + (double)entity.getEyeHeight() - (128.0 + (double)(this.mc.gameSettings.ofCloudsHeight * 128.0F))) == 0.0
            ? 0
            : (d1 < 0.0 ? -1 : 1);
         boolean flag1 = j < 0;
         return flag1 != flag;
      }
   }

   public void startUpdateGlList() {
      GL11.glNewList(this.updateCloudTickCounter, 4864);
   }

   public void endUpdateGlList() {
      GL11.glEndList();
      this.glListClouds = this.cloudTickCounter;
      this.updatePlayerX = this.mc.getRenderViewEntity().prevPosX;
      this.updatePlayerY = this.mc.getRenderViewEntity().prevPosY;
      this.updatePlayerZ = this.mc.getRenderViewEntity().prevPosZ;
      this.updated = true;
      GlStateManager.resetColor();
   }

   public void renderGlList() {
      Entity entity = this.mc.getRenderViewEntity();
      double d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * (double)this.partialTicks;
      double d1 = entity.prevPosY + (entity.posY - entity.prevPosY) * (double)this.partialTicks;
      double d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double)this.partialTicks;
      double d3 = (double)((float)(this.cloudTickCounter - this.glListClouds) + this.partialTicks);
      float f = (float)(d0 - this.updatePlayerX + d3 * 0.03);
      float f1 = (float)(d1 - this.updatePlayerY);
      float f2 = (float)(d2 - this.updatePlayerZ);
      GlStateManager.pushMatrix();
      if (this.renderFancy) {
         GlStateManager.translate(-f / 12.0F, -f1, -f2 / 12.0F);
      } else {
         GlStateManager.translate(-f, -f1, -f2);
      }

      GlStateManager.callList(this.updateCloudTickCounter);
      GlStateManager.popMatrix();
      GlStateManager.resetColor();
   }

   public void reset() {
      this.updated = false;
   }
}
