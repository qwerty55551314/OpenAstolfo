package astolfo.render.font;

import astolfo.render.GlHelper;
import astolfo.render.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;

public class GlyphFontRenderer implements TextRenderer {
   public int charSpacing = 0;
   public final GlyphPage glyphPage = new GlyphPage();

   @Override
   public float drawStringWithPage(GlyphPage glyphPage, String string, float f, float g, int i) {
      if (!glyphPage.isGenerated()) {
         return 0.0F;
      } else {
         GlStateManager.enableBlend();
         glyphPage.bindTexture();
         GlHelper.setColorHex(i);
         int i1 = string.length();

         for (int j = 0; j < i1; j++) {
            char c0 = string.charAt(j);
            if (glyphPage.hasGlyph(c0)) {
               GlyphPage.Glyph glyphpage$glyph = drawGlyph(glyphPage, f, g, c0);
               f += (float)(glyphpage$glyph.width + this.charSpacing);
            }
         }

         return f;
      }
   }

   public static GlyphPage.Glyph drawGlyph(GlyphPage glyphPage, float f, float g, char c) {
      GlyphPage.Glyph glyphpage$glyph = glyphPage.getGlyph(c);
      RenderUtil.drawTexturedRect(
         f,
         g,
         (float)glyphpage$glyph.width,
         (float)glyphpage$glyph.height,
         (float)glyphpage$glyph.x / (float)glyphPage.getTextureWidth(),
         (float)glyphpage$glyph.y / (float)glyphPage.getTextureHeight(),
         (float)(glyphpage$glyph.x + glyphpage$glyph.width) / (float)glyphPage.getTextureWidth(),
         (float)(glyphpage$glyph.y + glyphpage$glyph.height) / (float)glyphPage.getTextureHeight()
      );
      return glyphpage$glyph;
   }

   @Override
   public float drawString(String string, float f, float g, int i) {
      return this.drawStringWithPage(this.glyphPage, string, f, g, i);
   }

   public int getCharSpacing() {
      return this.charSpacing;
   }

   public void setCharSpacing(int i) {
      this.charSpacing = i;
   }

   @Override
   public GlyphPage getGlyphPage() {
      return this.glyphPage;
   }
}
