package astolfo.gui.clickgui.unused;

import astolfo.gui.clickgui.SettingComponent;
import astolfo.gui.component.Container;
import astolfo.render.RenderUtil;
import astolfo.setting.Setting;
import java.awt.Color;
import org.lwjgl.opengl.GL11;

public class ColorWheel extends SettingComponent {
   public int selectedColor = Color.red.getRGB();
   private static final String d = "me gusta color";

   public ColorWheel(Container container, Setting setting, int i, int j) {
      super(container, setting, i, j);
   }

   @Override
   public void onClick(int i) {
   }

   public void updateHueFromMouse(int i, int j) {
      float f = this.getX();
      float f1 = this.getX() + 10.0F;
      float f2 = this.getYPos();
      float f3 = this.getYPos() + 17.0F;
      float f4 = (f + f1) / 2.0F;
      float f5 = (f2 + f3) / 2.0F;
      float[] afloat = Color.RGBtoHSB(this.selectedColor >> 16 & 0xFF, this.selectedColor >> 8 & 0xFF, this.selectedColor & 0xFF, new float[3]);
      double d0 = (double)((float)i - f4);
      double d1 = (double)((float)j - f5);
      double d2 = Math.atan2(d1, d0);
      if (d2 < 0.0) {
         d2 += (float) (Math.PI * 2);
      } else if (d2 > (float) (Math.PI * 2)) {
         d2 -= (float) (Math.PI * 2);
      }

      this.selectedColor = Color.HSBtoRGB((float)(d2 / (float) (Math.PI * 2)), afloat[1], afloat[2]);
   }

   @Override
   public void render(int i, int j) {
      Object object = null;
      RenderUtil.drawRect(
         (double)(this.getX() + 1.5F),
         (double)(this.getYPos() - 1.0F),
         (double)(this.getX() + (float)this.getWidth() + 0.5F),
         (double)(this.getYPos() + (float)this.getHeight() + 4.0F),
         new Color(1, 1, 1, 120).getRGB()
      );
      client.getCustomFontRenderer().drawStringWithShadow(d, this.getX() + 20.0F, this.getYPos(), Color.WHITE.getRGB());
      float f = this.getX() + 10.0F;
      float f1 = this.getYPos() + 6.0F;
      float f2 = 5.0F;
      int i1 = this.selectedColor;
      float[] afloat = Color.RGBtoHSB(i1 >> 16 & 0xFF, i1 >> 8 & 0xFF, i1 & 0xFF, new float[3]);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glBlendFunc(770, 771);
      GL11.glLineWidth(7.0F);
      GL11.glBegin(2);

      for (double d0 = 0.0; d0 < (float) (Math.PI * 2); d0 += 0.02F) {
         RenderUtil.setColor(Color.HSBtoRGB((float)(d0 / (float) (Math.PI * 2)), afloat[1], afloat[2]));
         GL11.glVertex2d((double)f + Math.cos(d0) * (double)f2, (double)f1 + Math.sin(d0) * (double)f2);
      }

      GL11.glEnd();
      double d1 = (double)(afloat[0] * (float) (Math.PI * 2));
      GL11.glBegin(1);
      RenderUtil.setColor(new Color(25, 25, 25, 255).getRGB());
      GL11.glVertex2d((double)f + Math.cos(d1) * (double)(f2 - 4.0F), (double)f1 + Math.sin(d1) * (double)(f2 - 4.0F));
      GL11.glVertex2d((double)f + Math.cos(d1) * (double)(f2 + 4.0F), (double)f1 + Math.sin(d1) * (double)(f2 + 4.0F));
      GL11.glEnd();
      GL11.glLineWidth(8.0F);
      GL11.glBegin(1);
      RenderUtil.setColor(new Color(200, 200, 200, 255).getRGB());
      GL11.glVertex2d((double)f + Math.cos(d1) * (double)(f2 - 3.5F), (double)f1 + Math.sin(d1) * (double)(f2 - 3.5F));
      GL11.glVertex2d((double)f + Math.cos(d1) * (double)(f2 + 3.5F), (double)f1 + Math.sin(d1) * (double)(f2 + 3.5F));
      GL11.glEnd();
      GL11.glLineWidth(1.0F);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
   }

   public static void drawBorderedRect(float f, float g, float h, float f2, float f3, int i, int j) {
      RenderUtil.drawRect((double)(f + f3), (double)(g + f3), (double)(h - f3), (double)(f2 - f3), j);
      RenderUtil.drawRect((double)f, (double)(g + f3), (double)(f + f3), (double)(f2 - f3), i);
      RenderUtil.drawRect((double)(h - f3), (double)(g + f3), (double)h, (double)(f2 - f3), i);
      RenderUtil.drawRect((double)f, (double)g, (double)h, (double)(g + f3), i);
      RenderUtil.drawRect((double)f, (double)(f2 - f3), (double)h, (double)f2, i);
   }

   public static void drawRectOutline(float f, float g, float h, float f2, float f3, int i) {
      RenderUtil.drawRect((double)f, (double)(g + f3), (double)(f + f3), (double)(f2 - f3), i);
      RenderUtil.drawRect((double)(h - f3), (double)(g + f3), (double)h, (double)(f2 - f3), i);
      RenderUtil.drawRect((double)f, (double)g, (double)h, (double)(g + f3), i);
      RenderUtil.drawRect((double)f, (double)(f2 - f3), (double)h, (double)f2, i);
   }
}
