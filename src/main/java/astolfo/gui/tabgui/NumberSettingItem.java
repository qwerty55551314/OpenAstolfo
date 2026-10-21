package astolfo.gui.tabgui;

import astolfo.gui.TabGui;
import astolfo.setting.NumberSetting;
import astolfo.util.MathUtil;
import java.text.DecimalFormat;
import net.minecraft.client.Minecraft;

public class NumberSettingItem extends TabGuiItem {
   private NumberSetting setting;
   private static boolean W;

   public NumberSettingItem(TabGui.Panel panel, NumberSetting numberSetting) {
      super(panel);
      this.setting = numberSetting;
   }

   @Override
   public void handleKeyPress(int i) {
      super.handleKeyPress(i);
      if (i == 78) {
         DecimalFormat decimalformat = new DecimalFormat("0.0");
         if (this.setting.valueClass == Integer.class) {
            int i1 = Integer.parseInt(String.valueOf(this.getIntValue() + (Minecraft.getMinecraft().gameSettings.keyBindSprint.isKeyDown() ? 10 : 1)));
            this.setting.setValue(i1);
         } else if (this.setting.valueClass == Double.class) {
            double d0 = Double.parseDouble(
               decimalformat.format(this.getDoubleValue() + 0.1 * (Minecraft.getMinecraft().gameSettings.keyBindSprint.isKeyDown() ? 10.0 : 1.0))
            );
            this.setting.setValue(d0);
         } else {
            float f = Float.parseFloat(
               decimalformat.format((double)(this.getFloatValue() + 0.1F * (Minecraft.getMinecraft().gameSettings.keyBindSprint.isKeyDown() ? 10.0F : 1.0F)))
            );
            this.setting.setValue(f);
         }
      } else if (i == 74) {
         DecimalFormat decimalformat1 = new DecimalFormat("0.0");
         Class<Integer> oclass1 = Integer.class;
         Class oclass = this.setting.valueClass;
         if (oclass == oclass1) {
            int j = Integer.parseInt(String.valueOf(this.getIntValue() - (Minecraft.getMinecraft().gameSettings.keyBindSprint.isKeyDown() ? 10 : 1)));
            this.setting.setValue(j);
         } else if (this.setting.valueClass == Double.class) {
            double d1 = Double.parseDouble(
               decimalformat1.format(this.getDoubleValue() - 0.1 * (Minecraft.getMinecraft().gameSettings.keyBindSprint.isKeyDown() ? 10.0 : 1.0))
            );
            this.setting.setValue(d1);
         } else {
            float f1 = Float.parseFloat(
               decimalformat1.format((double)(this.getFloatValue() - 0.1F * (Minecraft.getMinecraft().gameSettings.keyBindSprint.isKeyDown() ? 10.0F : 1.0F)))
            );
            this.setting.setValue(f1);
         }
      }
   }

   public int getIntValue() {
      String s = MathUtil.ONE_DECIMAL.format(this.setting.getValue()).replace(",", "");
      return Integer.valueOf(s);
   }

   public float getFloatValue() {
      String s = MathUtil.ONE_DECIMAL.format(this.setting.getValue()).replace(",", ".");
      return Float.parseFloat(s);
   }

   public double getDoubleValue() {
      String s = MathUtil.ONE_DECIMAL.format(this.setting.getValue()).replace(",", ".");
      return Double.parseDouble(s);
   }

   @Override
   public String getDisplayText() {
      return "§f" + this.setting.getName() + ": §7" + this.setting.getValue();
   }

   public static void U(boolean flag) {
      W = flag;
   }

   public static boolean h() {
      return W;
   }

   public static boolean r() {
      return false;
   }

   static {
      U(true);
   }
}
