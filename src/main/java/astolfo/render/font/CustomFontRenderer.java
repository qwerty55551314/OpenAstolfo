package astolfo.render.font;

import astolfo.module.visuals.HUD;
import astolfo.render.RenderUtil;
import java.awt.Font;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

public final class CustomFontRenderer extends GlyphFontRenderer {
   private final GlyphPage boldPage = new GlyphPage();
   private final GlyphPage italicPage = new GlyphPage();
   private final GlyphPage boldItalicPage = new GlyphPage();
   private final int[] colorCodes = new int[32];
   private final String formatCodes = "0123456789abcdefklmnor";

   public CustomFontRenderer() {
      for (int i = 0; i < 32; i++) {
         int j = (i >> 3 & 1) * 85;
         int k = (i >> 2 & 1) * 170 + j;
         int l = (i >> 1 & 1) * 170 + j;
         int i1 = (i & 1) * 170 + j;
         if (i == 6) {
            k += 85;
         }

         if (i >= 16) {
            k /= 4;
            l /= 4;
            i1 /= 4;
         }

         this.colorCodes[i] = (k & 0xFF) << 16 | (l & 0xFF) << 8 | i1 & 0xFF;
      }
   }

   @Deprecated
   @Override
   public float drawStringWithPage(GlyphPage glyphPage, String string, float f, float g, int i) {
      return 0.0F;
   }

   @Override
   public float drawString(String string, float f, float g, int i) {
      return this.drawString(string, f, g, i, false);
   }

   public float drawStringWithShadow(String string, float f, float g, int i) {
      return HUD.cFont.getValue()
         ? Math.max(this.drawString(string, f + 0.5F, g + 0.5F, i, true), this.drawString(string, f, g, i, false))
         : (float)Minecraft.getMinecraft().fontRendererObj.drawString(string, f, g + 2.0F, i, true);
   }

   public void drawCenteredStringWithShadow(String string, float f, float g, int i) {
      this.drawStringWithShadow(string, f - (float)(this.getStringWidth(string) / 2), g - (float)(this.getStringHeight(string) / 2), i);
   }

   public void drawCenteredString(String string, float f, float g, int i) {
      this.drawString(string, f - (float)(this.getStringWidth(string) / 2), g - (float)(this.getStringHeight(string) / 2), i);
   }

   public float renderString(String string, float f, float g, int i, boolean bl, boolean bl2) {
      if (string == null) {
         return 0.0F;
      } else {
         if (i == 553648127) {
            i = 16777215;
         }

         int l = i & -67108864;
         if (l == 0) {
            i |= -16777216;
         }

         if (bl) {
            i = (i & 16579836) >> 2 | i & 0xFF000000;
         }

         GlyphPage glyphpage = super.glyphPage;
         float f1 = (float)(i >> 24 & 0xFF) / 255.0F;
         boolean flag = false;
         boolean flag1 = false;
         boolean flag2 = false;
         boolean flag3 = false;
         boolean flag4 = false;
         f *= 2.0F;
         g *= 2.0F;
         GlStateManager.pushMatrix();
         GlStateManager.scale(0.5F, 0.5F, 0.5F);
         GlStateManager.enableAlpha();
         GlStateManager.enableBlend();
         GlStateManager.blendFunc(770, 771);
         GlStateManager.color((float)(i >> 16 & 0xFF) / 255.0F, (float)(i >> 8 & 0xFF) / 255.0F, (float)(i & 0xFF) / 255.0F, f1);
         int i1 = string.length();
         glyphpage.bindTexture();

         for (int j = 0; j < i1; j++) {
            char c0 = string.charAt(j);
            if (c0 == 167 && j < i1 && j + 1 < i1) {
               int i1x = "0123456789abcdefklmnor".indexOf(string.charAt(j + 1));
               if (i1x < 16) {
                  flag1 = false;
                  flag2 = false;
                  flag = false;
                  flag4 = false;
                  flag3 = false;
                  glyphpage = super.glyphPage;
                  glyphpage.bindTexture();
                  if (i1x < 0 || i1x > 15) {
                     i1x = 15;
                  }

                  if (bl) {
                     i1x += 16;
                  }

                  int k = this.colorCodes[i1x];
                  GlStateManager.color((float)(k >> 16 & 0xFF) / 255.0F, (float)(k >> 8 & 0xFF) / 255.0F, (float)(k & 0xFF) / 255.0F, f1);
               } else if (i1x == 16) {
                  flag = true;
               } else if (i1x == 17) {
                  flag1 = true;
                  if (flag2) {
                     glyphpage = this.boldItalicPage;
                     glyphpage.bindTexture();
                  } else {
                     glyphpage = this.boldPage;
                     glyphpage.bindTexture();
                  }
               } else if (i1x == 18) {
                  flag3 = true;
               } else if (i1x == 19) {
                  flag4 = true;
               } else if (i1x == 20) {
                  flag2 = true;
                  if (flag1) {
                     glyphpage = this.boldItalicPage;
                     glyphpage.bindTexture();
                  } else {
                     glyphpage = this.italicPage;
                     glyphpage.bindTexture();
                  }
               } else if (i1x == 21) {
                  flag1 = false;
                  flag2 = false;
                  flag = false;
                  flag4 = false;
                  flag3 = false;
                  GlStateManager.color((float)(i >> 16 & 0xFF) / 255.0F, (float)(i >> 8 & 0xFF) / 255.0F, (float)(i & 0xFF) / 255.0F, f1);
                  glyphpage = super.glyphPage;
                  glyphpage.bindTexture();
               }

               j++;
            } else if (glyphpage.hasGlyph(c0)) {
               if (flag) {
                  char c1 = 0;

                  while (glyphpage.getGlyph(c1).width != glyphpage.getGlyph(c0).width) {
                     c1 = (char)((int)(Math.random() * 256.0));
                  }

                  c0 = c1;
               }

               GlyphPage.Glyph glyphpage$glyph = GlyphFontRenderer.drawGlyph(glyphpage, f, g, c0);
               if (flag3) {
                  RenderUtil.lineVertices(
                     f,
                     g + (float)(glyphpage$glyph.height / 4) + 2.0F,
                     f + (float)(glyphpage$glyph.width / 2),
                     g + (float)(glyphpage$glyph.height / 4) + 2.0F,
                     1.0F
                  );
               }

               if (flag4) {
                  RenderUtil.lineVertices(
                     f, g + (float)(glyphpage$glyph.height / 2), f + (float)(glyphpage$glyph.width / 2), g + (float)(glyphpage$glyph.height / 2), 1.0F
                  );
               }

               f += (float)(glyphpage$glyph.width + super.charSpacing);
            }
         }

         GlStateManager.color(1.0F, 1.0F, 1.0F);
         GlStateManager.popMatrix();
         return f;
      }
   }

   public float drawString(String string, float f, float g, int i, boolean bl) {
      return this.renderString(string, f, g, i, bl, true);
   }

   public int getStringHeight(String string) {
      if (string == null) {
         return 0;
      } else {
         int i = 0;
         GlyphPage glyphpage = super.glyphPage;
         boolean flag = false;
         boolean flag1 = false;
         int j = string.length();

         for (int k = 0; k < j; k++) {
            char c0 = string.charAt(k);
            if (c0 == 167 && k < j) {
               int l = "0123456789abcdefklmnor".indexOf(c0);
               if (l < 16) {
                  flag = false;
                  flag1 = false;
               } else if (l == 17) {
                  flag = true;
                  if (flag1) {
                     glyphpage = this.boldItalicPage;
                  } else {
                     glyphpage = this.boldPage;
                  }
               } else if (l == 20) {
                  flag1 = true;
                  if (flag) {
                     glyphpage = this.boldItalicPage;
                  } else {
                     glyphpage = this.italicPage;
                  }
               } else if (l == 21) {
                  flag = false;
                  flag1 = false;
                  glyphpage = super.glyphPage;
               }

               k++;
            } else {
               boolean flag2 = glyphpage.hasGlyph(c0);
               if (flag2) {
                  int i1 = glyphpage.getGlyph(c0).height;
                  if (i1 > i) {
                     i = glyphpage.getGlyph(c0).height;
                  }
               }
            }
         }

         return i / 2;
      }
   }

   public int getStringWidth(String string) {
      return this.getStringWidth(string, false);
   }

   public int getStringWidth(String string, boolean bl) {
      if (string == null) {
         return 0;
      } else {
         boolean flag2 = HUD.cFont.getValue();
         if (!flag2 && !bl) {
            return Minecraft.getMinecraft().fontRendererObj.getStringWidth(string);
         } else {
            int i = 0;
            GlyphPage glyphpage = super.glyphPage;
            boolean flag = false;
            boolean flag1 = false;
            int j = string.length();

            for (int k = 0; k < j; k++) {
               char c0 = string.charAt(k);
               if (c0 == 167 && k < j) {
                  int l = "0123456789abcdefklmnor".indexOf(c0);
                  if (l < 16) {
                     flag = false;
                     flag1 = false;
                  } else if (l == 17) {
                     flag = true;
                     if (flag1) {
                        glyphpage = this.boldItalicPage;
                     } else {
                        glyphpage = this.boldPage;
                     }
                  } else if (l == 20) {
                     flag1 = true;
                     if (flag) {
                        glyphpage = this.boldItalicPage;
                     } else {
                        glyphpage = this.italicPage;
                     }
                  } else if (l == 21) {
                     flag = false;
                     flag1 = false;
                     glyphpage = super.glyphPage;
                  }

                  k++;
               } else {
                  boolean flag3 = glyphpage.hasGlyph(c0);
                  if (flag3) {
                     i += glyphpage.getGlyph(c0).width + super.charSpacing;
                  }
               }
            }

            return i / 2;
         }
      }
   }

   public void loadFont(Font font, boolean bl) {
      super.glyphPage.generate(font, bl);
      this.boldPage.generate(font.deriveFont(1), bl);
      this.italicPage.generate(font.deriveFont(2), bl);
      this.boldItalicPage.generate(font.deriveFont(3), bl);
   }
}
