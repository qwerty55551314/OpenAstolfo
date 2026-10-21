package astolfo.gui.clickgui.transparent;

import astolfo.Astolfo;
import astolfo.gui.clickgui.SettingComponent;
import astolfo.gui.component.Container;
import astolfo.module.visuals.HUD;
import astolfo.render.RenderUtil;
import astolfo.setting.NumberSetting;
import astolfo.setting.Setting;
import astolfo.util.MathUtil;
import java.awt.Color;
import java.text.DecimalFormat;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Mouse;

public class NumberSlider extends SettingComponent {
   private final DecimalFormat decimalFormat = new DecimalFormat("#");
   private static final Color darkColor = new Color(22, 22, 22);

   public NumberSlider(Container container, Setting setting, int i, int j) {
      super(container, setting, i, j);
   }

   @Override
   public void render(int i, int j) {
      float f = this.getNumberSetting().getMax().floatValue() - this.getNumberSetting().getMin().floatValue();
      int i1 = this.getNumberSetting().getMax().intValue() - this.getNumberSetting().getMin().intValue();
      float f3 = this.getNumberSetting().getMin().floatValue();
      float f2 = this.getRoundedFloat();
      float f1 = (float)Math.round((f2 - f3) / (this.getNumberSetting().valueClass == Integer.class ? (float)i1 : f) * (float)this.getWidth());
      RenderUtil.drawRect(
         (double)(this.getX() + 1.5F),
         (double)(this.getYPos() - 1.0F),
         (double)(this.getX() + (float)this.getWidth() + 0.5F),
         (double)(this.getYPos() + (float)this.getHeight() + 4.0F),
         new Color(1, 1, 1, 120).getRGB()
      );
      double d0 = (double)(this.getX() + 1.5F);
      double d1 = (double)(this.getYPos() + 10.0F);
      double d2 = (double)(this.getX() + f1);
      double d3 = (double)(this.getYPos() + (float)this.getHeight() + 2.0F);
      Astolfo astolfo = client;
      RenderUtil.drawRect(d0, d1, d2, d3, astolfo.getColor().getRGB());
      boolean flag = this.getNumberSetting().valueClass.equals(Integer.class);
      String s;
      if (flag) {
         s = String.format("%s %s", this.getNumberSetting().getName().replace("-", " "), this.getRoundedInt());
      } else {
         boolean flag1 = this.getNumberSetting().valueClass.equals(Float.class);
         if (flag1) {
            s = String.format("%s %s", this.getNumberSetting().getName().replace("-", " "), this.getRoundedFloat());
         } else if (this.getNumberSetting().valueClass.equals(Double.class)) {
            s = String.format("%s %s", this.getNumberSetting().getName().replace("-", " "), this.getRoundedDouble());
         } else {
            s = String.format("%s %s", this.getNumberSetting().getName().replace("-", " "), this.getRoundedInt());
         }
      }

      s = s + this.getNumberSetting().getSuffix();
      client.getCustomFontRenderer().drawStringWithShadow(EnumChatFormatting.GRAY + s, this.getX() + 4.0F, this.getYPos() - 1.0F, Color.WHITE.getRGB());
      boolean flag2 = this.isHovered(i, j);
      if (flag2 && HUD.toolTips.getValue()) {
         client.getCustomFontRenderer().drawStringWithShadow(this.getNumberSetting().getDescription(), (float)(i + 6), (float)(j - 4), -1);
      }

      boolean flag3 = this.isHovered(i, j);
      if (flag3) {
         boolean flag4 = Mouse.isButtonDown(0);
         if (flag4 && this.getParentContainer().isExpanded()) {
            Class<Integer> oclass1 = Integer.class;
            Class oclass = this.getNumberSetting().valueClass;
            if (oclass == oclass1) {
               this.getNumberSetting()
                  .setValue(
                     (i - (int)this.getX()) * this.getNumberSetting().getMax().intValue() / this.getWidth() + this.getNumberSetting().getMin().intValue()
                  );
            } else if (this.getNumberSetting().valueClass == Double.class) {
               this.getNumberSetting()
                  .setValue(
                     Double.valueOf(
                        MathUtil.ONE_DECIMAL
                           .format((double)(((float)i - this.getX()) * f / (float)this.getWidth()) + this.getNumberSetting().getMin().doubleValue())
                           .replace(",", ".")
                     )
                  );
            } else {
               this.getNumberSetting()
                  .setValue(
                     Float.valueOf(
                        MathUtil.ONE_DECIMAL
                           .format((double)(((float)i - this.getX()) * f / (float)this.getWidth() + this.getNumberSetting().getMin().floatValue()))
                           .replace(",", ".")
                     )
                  );
            }
         }
      }
   }

   public int getRoundedInt() {
      String s = MathUtil.ONE_DECIMAL.format(this.getNumberSetting().getValue()).replace(",", "");
      return Integer.valueOf(s);
   }

   public float getRoundedFloat() {
      String s = MathUtil.ONE_DECIMAL.format(this.getNumberSetting().getValue()).replace(",", ".");
      return Float.parseFloat(s);
   }

   public double getRoundedDouble() {
      String s = MathUtil.ONE_DECIMAL.format(this.getNumberSetting().getValue()).replace(",", ".");
      return Double.parseDouble(s);
   }

   private NumberSetting getNumberSetting() {
      return (NumberSetting)this.getSetting();
   }

   @Override
   public void onClick(int i) {
   }
}
