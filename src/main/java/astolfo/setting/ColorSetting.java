package astolfo.setting;

import astolfo.gui.clickgui.defaultgui.ColorPicker;
import astolfo.module.hidden.HypixelPacketHandler;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

public final class ColorSetting extends Setting<Color> {
   public ColorMode mode = ColorMode.NONE;

   public ColorSetting(String string, String string2, Color color) {
      super(string, string2, color, SettingType.COLOR);
   }

   public Color getValue() {
      Color color = (Color)super.getValue();
      switch (this.mode) {
         case PULSATE:
            float f = 1.0F - MathHelper.abs(MathHelper.sin((float)Minecraft.getSystemTime() % 4500.0F / 4500.0F * (float) Math.PI * 2.0F) * 0.6F);
            return new Color(color.getRed(), color.getGreen(), color.getBlue(), MathHelper.clamp_int((int)((float)color.getAlpha() * f), 0, 255));
         case RAINBOW:
            return ColorPicker.getHueColor(HypixelPacketHandler.tickCycle);
         default:
            return color;
      }
   }

   public void setValue(Color color) {
      super.setValue(color);
   }

   @Override
   public void parse(String string) {
      String[] astring = string.split(" ");
      int i = astring.length;
      if (i == 3) {
         this.setValue(new Color(Integer.parseInt(astring[0]), Integer.parseInt(astring[1]), Integer.parseInt(astring[2])));
      } else {
         int j = astring.length;
         if (j == 4) {
            this.setValue(new Color(Integer.parseInt(astring[0]), Integer.parseInt(astring[1]), Integer.parseInt(astring[2]), Integer.parseInt(astring[3])));
         } else if (astring.length == 5) {
            this.mode = ColorMode.values()[Integer.parseInt(astring[4])];
            this.setValue(new Color(Integer.parseInt(astring[0]), Integer.parseInt(astring[1]), Integer.parseInt(astring[2]), Integer.parseInt(astring[3])));
         }
      }
   }

   @Override
   public String asString() {
      return ((Color)super.value).getRed()
         + " "
         + ((Color)super.value).getGreen()
         + " "
         + ((Color)super.value).getBlue()
         + " "
         + ((Color)super.value).getAlpha()
         + " "
         + this.mode.index;
   }
}
