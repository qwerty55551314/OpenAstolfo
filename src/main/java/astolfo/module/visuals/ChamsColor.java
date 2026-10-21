package astolfo.module.visuals;

import astolfo.Astolfo;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

public enum ChamsColor {
   DEFAULT(Astolfo.INSTANCE.getColorSetting().getValue()),
   PINK(new Color(255, 127, 209)),
   PURPLE(new Color(167, 120, 255)),
   RED(new Color(231, 76, 60)),
   GREEN(new Color(46, 204, 113)),
   BLUE(new Color(85, 205, 252)),
   ORANGE(new Color(243, 156, 18)),
   RAINBOW(null),
   WHITE(Color.WHITE);

   private Color color;
   private static final ChamsColor[] VALUES = new ChamsColor[]{DEFAULT, PINK, PURPLE, RED, GREEN, BLUE, ORANGE, RAINBOW, WHITE};

   private ChamsColor(Color color) {
      this.color = color;
   }

   public void setColor(Color color) {
      this.color = color;
   }

   public Color getColor() {
      if (this.color == null) {
         return this.getRainbowColor(Minecraft.getSystemTime(), 1.0F);
      } else {
         return this == DEFAULT ? Astolfo.INSTANCE.getColorSetting().getValue() : this.color;
      }
   }

   private Color getRainbowColor(long l, float f) {
      float f1 = 1.0F - MathHelper.abs(MathHelper.sin((float)l % 4500.0F / 4500.0F * (float) Math.PI * 2.0F) * 0.5F);
      long i = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(f1, 0.5F, 1.0F)), 16);
      Color colorx = new Color((int)i);
      return new Color(
         (float)colorx.getRed() / 255.0F * f, (float)colorx.getGreen() / 255.0F * f, (float)colorx.getBlue() / 255.0F * f, (float)colorx.getAlpha() / 255.0F
      );
   }
}
