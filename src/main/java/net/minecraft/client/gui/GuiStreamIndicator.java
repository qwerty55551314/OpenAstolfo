package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class GuiStreamIndicator {
   private static final ResourceLocation locationStreamIndicator = new ResourceLocation("textures/gui/stream_indicator.png");
   private final Minecraft mc;
   private float streamAlpha = 1.0F;
   private int streamAlphaDelta = 1;

   public GuiStreamIndicator(Minecraft mcIn) {
      this.mc = mcIn;
   }

   public void render(int i, int j) {
   }

   private void render(int i, int i2, int i3, int i4) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 0.65F + 0.35000002F * this.streamAlpha);
      this.mc.getTextureManager().bindTexture(locationStreamIndicator);
      float f = 150.0F;
      float f1 = 0.0F;
      float f2 = (float)i3 * 0.015625F;
      float f3 = 1.0F;
      float f4 = (float)(i3 + 16) * 0.015625F;
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
      worldrenderer.pos((double)(i - 16 - i4), (double)(i2 + 16), (double)f).tex((double)f1, (double)f4).endVertex();
      worldrenderer.pos((double)(i - i4), (double)(i2 + 16), (double)f).tex((double)f3, (double)f4).endVertex();
      worldrenderer.pos((double)(i - i4), (double)(i2 + 0), (double)f).tex((double)f3, (double)f2).endVertex();
      worldrenderer.pos((double)(i - 16 - i4), (double)(i2 + 0), (double)f).tex((double)f1, (double)f2).endVertex();
      tessellator.draw();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private int func_152440_b() {
      return 0;
   }

   private int func_152438_c() {
      return 32;
   }

   public void updateStreamAlpha() {
      this.streamAlpha = 1.0F;
      this.streamAlphaDelta = 1;
   }
}
