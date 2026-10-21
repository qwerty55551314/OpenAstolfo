package net.minecraft.client.gui;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class Gui {
   public static final ResourceLocation optionsBackground = new ResourceLocation("textures/gui/options_background.png");
   public static final ResourceLocation statIcons = new ResourceLocation("textures/gui/container/stats_icons.png");
   public static final ResourceLocation icons = new ResourceLocation("textures/gui/icons.png");
   public float zLevel;
   private static boolean g;

   public static void drawThickVerticalLine(double d, double e, double d2, double d3, int i) {
      if (d3 < d2) {
         double d0 = d2;
         d2 = d3;
         d3 = d0;
      }

      drawDoubleRect(d, d2, e, d3, i);
   }

   public static void drawThickHorizontalLine(double d, double e, double d2, double d3, int i) {
      if (e < d) {
         double d0 = d;
         d = e;
         e = d0;
      }

      drawDoubleRect(d, d2, e + 1.0, d3, i);
   }

   public static void drawDoubleRect(double d, double e, double d2, double d3, int i) {
      double d21;
      int i1 = (d21 = d - d2) == 0.0 ? 0 : (d21 < 0.0 ? -1 : 1);
      if (i1 < 0) {
         double d0 = d;
         d = d2;
         d2 = d0;
      }

      double d31;
      int j = (d31 = e - d3) == 0.0 ? 0 : (d31 < 0.0 ? -1 : 1);
      if (j < 0) {
         double d1 = e;
         e = d3;
         d3 = d1;
      }

      float f3 = (float)(i >> 24 & 0xFF) / 255.0F;
      float f = (float)(i >> 16 & 0xFF) / 255.0F;
      float f1 = (float)(i >> 8 & 0xFF) / 255.0F;
      float f2 = (float)(i & 0xFF) / 255.0F;
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(f, f1, f2, f3);
      worldrenderer.begin(7, DefaultVertexFormats.POSITION);
      worldrenderer.pos(d, d3, 0.0).endVertex();
      worldrenderer.pos(d2, d3, 0.0).endVertex();
      worldrenderer.pos(d2, e, 0.0).endVertex();
      worldrenderer.pos(d, e, 0.0).endVertex();
      tessellator.draw();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
   }

   public static void drawHorizontalLine(int i, int startX, int endX, int y) {
      if (startX < i) {
         int i1 = i;
         i = startX;
         startX = i1;
      }

      drawRect(i, endX, startX + 1, endX + 1, y);
   }

   public static void drawVerticalLine(int i, int x, int startY, int endY) {
      if (startY < x) {
         int i1 = x;
         x = startY;
         startY = i1;
      }

      drawRect(i, x + 1, i + 1, startY, endY);
   }

   public static void drawRect(int left, int top, int right, int bottom, int color) {
      if (left < right) {
         int i = left;
         left = right;
         right = i;
      }

      if (top < bottom) {
         int j = top;
         top = bottom;
         bottom = j;
      }

      float f3 = (float)(color >> 24 & 0xFF) / 255.0F;
      float f = (float)(color >> 16 & 0xFF) / 255.0F;
      float f1 = (float)(color >> 8 & 0xFF) / 255.0F;
      float f2 = (float)(color & 0xFF) / 255.0F;
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(f, f1, f2, f3);
      worldrenderer.begin(7, DefaultVertexFormats.POSITION);
      worldrenderer.pos((double)left, (double)bottom, 0.0).endVertex();
      worldrenderer.pos((double)right, (double)bottom, 0.0).endVertex();
      worldrenderer.pos((double)right, (double)top, 0.0).endVertex();
      worldrenderer.pos((double)left, (double)top, 0.0).endVertex();
      tessellator.draw();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
   }

   public void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
      float f = (float)(startColor >> 24 & 0xFF) / 255.0F;
      float f1 = (float)(startColor >> 16 & 0xFF) / 255.0F;
      float f2 = (float)(startColor >> 8 & 0xFF) / 255.0F;
      float f3 = (float)(startColor & 0xFF) / 255.0F;
      float f4 = (float)(endColor >> 24 & 0xFF) / 255.0F;
      float f5 = (float)(endColor >> 16 & 0xFF) / 255.0F;
      float f6 = (float)(endColor >> 8 & 0xFF) / 255.0F;
      float f7 = (float)(endColor & 0xFF) / 255.0F;
      GlStateManager.disableTexture2D();
      GlStateManager.enableBlend();
      GlStateManager.disableAlpha();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.shadeModel(7425);
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
      worldrenderer.pos((double)right, (double)top, (double)this.zLevel).color(f1, f2, f3, f).endVertex();
      worldrenderer.pos((double)left, (double)top, (double)this.zLevel).color(f1, f2, f3, f).endVertex();
      worldrenderer.pos((double)left, (double)bottom, (double)this.zLevel).color(f5, f6, f7, f4).endVertex();
      worldrenderer.pos((double)right, (double)bottom, (double)this.zLevel).color(f5, f6, f7, f4).endVertex();
      tessellator.draw();
      GlStateManager.shadeModel(7424);
      GlStateManager.disableBlend();
      GlStateManager.enableAlpha();
      GlStateManager.enableTexture2D();
   }

   public void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
      fontRendererIn.drawStringWithShadow(text, (float)(x - fontRendererIn.getStringWidth(text) / 2), (float)y, color);
   }

   public void drawString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
      fontRendererIn.drawStringWithShadow(text, (float)x, (float)y, color);
   }

   public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
      float f = 0.00390625F;
      float f1 = 0.00390625F;
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
      worldrenderer.pos((double)(x + 0), (double)(y + height), (double)this.zLevel)
         .tex((double)((float)(textureX + 0) * f), (double)((float)(textureY + height) * f1))
         .endVertex();
      worldrenderer.pos((double)(x + width), (double)(y + height), (double)this.zLevel)
         .tex((double)((float)(textureX + width) * f), (double)((float)(textureY + height) * f1))
         .endVertex();
      worldrenderer.pos((double)(x + width), (double)(y + 0), (double)this.zLevel)
         .tex((double)((float)(textureX + width) * f), (double)((float)(textureY + 0) * f1))
         .endVertex();
      worldrenderer.pos((double)(x + 0), (double)(y + 0), (double)this.zLevel)
         .tex((double)((float)(textureX + 0) * f), (double)((float)(textureY + 0) * f1))
         .endVertex();
      tessellator.draw();
   }

   public void drawTexturedModalRect(float xCoord, float yCoord, int minU, int minV, int maxU, int maxV) {
      float f = 0.00390625F;
      float f1 = 0.00390625F;
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
      worldrenderer.pos((double)(xCoord + 0.0F), (double)(yCoord + (float)maxV), (double)this.zLevel)
         .tex((double)((float)(minU + 0) * f), (double)((float)(minV + maxV) * f1))
         .endVertex();
      worldrenderer.pos((double)(xCoord + (float)maxU), (double)(yCoord + (float)maxV), (double)this.zLevel)
         .tex((double)((float)(minU + maxU) * f), (double)((float)(minV + maxV) * f1))
         .endVertex();
      worldrenderer.pos((double)(xCoord + (float)maxU), (double)(yCoord + 0.0F), (double)this.zLevel)
         .tex((double)((float)(minU + maxU) * f), (double)((float)(minV + 0) * f1))
         .endVertex();
      worldrenderer.pos((double)(xCoord + 0.0F), (double)(yCoord + 0.0F), (double)this.zLevel)
         .tex((double)((float)(minU + 0) * f), (double)((float)(minV + 0) * f1))
         .endVertex();
      tessellator.draw();
   }

   public void drawTexturedModalRect(int xCoord, int yCoord, TextureAtlasSprite textureSprite, int widthIn, int heightIn) {
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
      worldrenderer.pos((double)(xCoord + 0), (double)(yCoord + heightIn), (double)this.zLevel)
         .tex((double)textureSprite.getMinU(), (double)textureSprite.getMaxV())
         .endVertex();
      worldrenderer.pos((double)(xCoord + widthIn), (double)(yCoord + heightIn), (double)this.zLevel)
         .tex((double)textureSprite.getMaxU(), (double)textureSprite.getMaxV())
         .endVertex();
      worldrenderer.pos((double)(xCoord + widthIn), (double)(yCoord + 0), (double)this.zLevel)
         .tex((double)textureSprite.getMaxU(), (double)textureSprite.getMinV())
         .endVertex();
      worldrenderer.pos((double)(xCoord + 0), (double)(yCoord + 0), (double)this.zLevel)
         .tex((double)textureSprite.getMinU(), (double)textureSprite.getMinV())
         .endVertex();
      tessellator.draw();
   }

   public static void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
      float f = 1.0F / textureWidth;
      float f1 = 1.0F / textureHeight;
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
      worldrenderer.pos((double)x, (double)(y + height), 0.0).tex((double)(u * f), (double)((v + (float)height) * f1)).endVertex();
      worldrenderer.pos((double)(x + width), (double)(y + height), 0.0).tex((double)((u + (float)width) * f), (double)((v + (float)height) * f1)).endVertex();
      worldrenderer.pos((double)(x + width), (double)y, 0.0).tex((double)((u + (float)width) * f), (double)(v * f1)).endVertex();
      worldrenderer.pos((double)x, (double)y, 0.0).tex((double)(u * f), (double)(v * f1)).endVertex();
      tessellator.draw();
   }

   public static void drawScaledCustomSizeModalRect(
      int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight
   ) {
      float f = 1.0F / tileWidth;
      float f1 = 1.0F / tileHeight;
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
      worldrenderer.pos((double)x, (double)(y + height), 0.0).tex((double)(u * f), (double)((v + (float)vHeight) * f1)).endVertex();
      worldrenderer.pos((double)(x + width), (double)(y + height), 0.0).tex((double)((u + (float)uWidth) * f), (double)((v + (float)vHeight) * f1)).endVertex();
      worldrenderer.pos((double)(x + width), (double)y, 0.0).tex((double)((u + (float)uWidth) * f), (double)(v * f1)).endVertex();
      worldrenderer.pos((double)x, (double)y, 0.0).tex((double)(u * f), (double)(v * f1)).endVertex();
      tessellator.draw();
   }

   static {
      B(true);
   }

   public static void B(boolean flag) {
      g = flag;
   }

   public static boolean H() {
      return g;
   }

   public static boolean I() {
      return false;
   }
}
