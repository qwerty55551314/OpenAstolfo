package astolfo.render.font;

import astolfo.render.GlHelper;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.image.BufferedImage;
import net.minecraft.client.renderer.GlStateManager;

public final class GlyphPage {
   private final GlyphPage.Glyph[] glyphs = new GlyphPage.Glyph[256];
   private int textureId = -1;
   private int maxGlyphHeight = 0;
   private int textureWidth;
   private int textureHeight;

   public GlyphPage generate(Font font, boolean bl) {
      return this.generateAtlas(font, bl, bl, 16, 2);
   }

   private GlyphPage generateAtlas(Font font, boolean bl, boolean bl2, int i, int j) {
      if (this.textureId == -1) {
         this.textureId = GlHelper.genTexture();
      }

      FontMetrics fontmetrics = new Canvas().getFontMetrics(font);
      int i1 = 0;
      int j1 = 0;
      int k = 0;

      for (int l = 0; l < this.glyphs.length; l++) {
         char c0 = (char)l;
         int i1x = fontmetrics.getHeight();
         int j1x = fontmetrics.charWidth(c0);
         if (l != 0 && l % i == 0) {
            j1 = j;
            k += i1 + j;
            i1 = 0;
         }

         if (i1x > i1) {
            i1 = i1x;
            if (i1x > this.maxGlyphHeight) {
               this.maxGlyphHeight = i1x;
            }
         }

         this.glyphs[l] = new GlyphPage.Glyph(j1, k, j1x, i1x);
         j1 += j1x + j;
         if (j1 + j1x + j > this.textureWidth) {
            this.textureWidth = j1 + j1x + j;
         }

         if (k + i1x + j > this.textureHeight) {
            this.textureHeight = k + i1x + j;
         }
      }

      BufferedImage bufferedimage = new BufferedImage(this.textureWidth, this.textureHeight, 2);
      Graphics2D graphics2d = (Graphics2D)bufferedimage.getGraphics();
      graphics2d.setFont(font);
      fontmetrics = graphics2d.getFontMetrics(font);
      graphics2d.setColor(new Color(255, 255, 255, 0));
      graphics2d.fillRect(0, 0, this.textureWidth, this.textureHeight);
      graphics2d.setColor(Color.WHITE);
      graphics2d.setRenderingHint(
         RenderingHints.KEY_FRACTIONALMETRICS, bl2 ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF
      );
      Key key = RenderingHints.KEY_TEXT_ANTIALIASING;
      Graphics2D graphics2d1;
      Key key2;
      Object object;
      if (bl) {
         graphics2d1 = graphics2d;
         key2 = key;
         object = RenderingHints.VALUE_TEXT_ANTIALIAS_GASP;
      } else {
         graphics2d1 = graphics2d;
         key2 = key;
         object = RenderingHints.VALUE_TEXT_ANTIALIAS_OFF;
      }

      graphics2d1.setRenderingHint(key2, object);
      Key key1 = RenderingHints.KEY_ALPHA_INTERPOLATION;
      if (bl) {
         graphics2d1 = graphics2d;
         key2 = key1;
         object = RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY;
      } else {
         graphics2d1 = graphics2d;
         key2 = key1;
         object = RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED;
      }

      graphics2d1.setRenderingHint(key2, object);

      for (int l1 = 0; l1 < this.glyphs.length; l1++) {
         graphics2d.drawString(String.valueOf((char)l1), this.glyphs[l1].x, this.glyphs[l1].y + fontmetrics.getAscent());
      }

      int k1 = this.textureId;
      int i2;
      BufferedImage bufferedimage1;
      int short1;
      if (bl) {
         i2 = k1;
         bufferedimage1 = bufferedimage;
         short1 = 9729;
      } else {
         i2 = k1;
         bufferedimage1 = bufferedimage;
         short1 = 9728;
      }

      GlHelper.uploadImage(i2, bufferedimage1, short1, 10497);
      return this;
   }

   public void bindTexture() {
      GlStateManager.bindTexture(this.textureId);
   }

   public GlyphPage.Glyph getGlyph(char c) {
      return this.glyphs[c];
   }

   public int getStringWidth(String string) {
      int i = 0;

      for (char c0 : string.toCharArray()) {
         i += this.glyphs[c0].width;
      }

      return i;
   }

   public boolean hasGlyph(char c) {
      return c >= 0 && c < 256;
   }

   public boolean isGenerated() {
      return this.textureId != -1;
   }

   public int getMaxGlyphHeight() {
      return this.maxGlyphHeight;
   }

   public int getTextureWidth() {
      return this.textureWidth;
   }

   public int getTextureHeight() {
      return this.textureHeight;
   }

   public class Glyph {
      public final int x;
      public final int y;
      public final int width;
      public final int height;

      public Glyph(int i, int j, int k, int l) {
         this.x = i;
         this.y = j;
         this.width = k;
         this.height = l;
      }
   }
}
