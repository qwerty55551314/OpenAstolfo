package astolfo.gui.clickgui.unused;

import astolfo.Astolfo;
import astolfo.gui.component.Container;
import astolfo.render.RenderUtil;
import java.awt.Color;

public class TitleBar extends Container {
   private static boolean B;
   private static final String e = "astolfo";

   public TitleBar(int i, int j, int k, int l) {
      super(e, (float)i, (float)j, k, l);
      this.setDraggable(true);
   }

   @Override
   public void onClick(int i) {
   }

   @Override
   public void render(int i, int j) {
      double d0 = (double)(this.getX() - 1.0F);
      double d1 = (double)(this.getYPos() - 1.0F);
      double d2 = (double)(this.getX() + (float)this.getWidth() + 1.0F);
      double d3 = (double)(this.getYPos() + (float)this.getHeight() + 1.0F);
      Astolfo astolfo = client;
      RenderUtil.drawRect(d0, d1, d2, d3, astolfo.getColor().getRGB());
      RenderUtil.drawRect(
         (double)this.getX(),
         (double)this.getYPos(),
         (double)(this.getX() + (float)this.getWidth()),
         (double)(this.getYPos() + (float)this.getHeight()),
         new Color(26, 26, 26).getRGB()
      );
      client.getCustomFontRenderer().drawString(this.getName(), this.getX() + 3.0F, this.getYPos() + 3.0F, -1);
      RenderUtil.drawRect(
         (double)(this.getX() + 85.0F),
         (double)this.getYPos(),
         (double)(this.getX() + (float)this.getWidth()),
         (double)(this.getYPos() + (float)this.getHeight()),
         new Color(37, 37, 37).getRGB()
      );
   }

   public static void c(boolean flag) {
      B = flag;
   }

   public static boolean W() {
      return B;
   }

   public static boolean v() {
      return false;
   }

   static {
      c(true);
   }
}
