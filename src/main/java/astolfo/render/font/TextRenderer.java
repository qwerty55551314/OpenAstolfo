package astolfo.render.font;

public interface TextRenderer {
   float drawStringWithPage(GlyphPage var1, String var2, float var3, float var4, int var5);

   float drawString(String var1, float var2, float var3, int var4);

   GlyphPage getGlyphPage();
}
