package astolfo.gui.clickgui.sidebar;

import astolfo.Astolfo;
import astolfo.render.RenderUtil;
import astolfo.setting.NumberSetting;
import astolfo.setting.Setting;
import java.awt.Color;
import net.minecraft.client.gui.GuiMainMenu;
import org.lwjgl.input.Mouse;

public class NumberSlider extends SidebarSettingComponent {
   private boolean sliderDragging;
   private static final String j = "%.1f";

   public NumberSlider(ModuleButton moduleButton, Setting setting, float f, float g, int i, int j) {
      super(moduleButton, setting, f, g, i, j);
   }

   @Override
   public void onClick(int i) {
      if (i == 0 && this.isVisible()) {
         this.sliderDragging = true;
      }
   }

   @Override
   public void render(int i, int j) {
      float f = this.getX() + (float)this.getWidth() / 1.7F;
      int i1 = this.updateAndGetFillWidth(i, (int)f, 60);
      String s = String.format(NumberSlider.j, Double.valueOf(this.getNumberSetting().asString()));
      double d4 = (double)(f - 0.5F);
      double d5 = (double)(this.getYPos() + 0.5F);
      double d6 = (double)(f + 60.0F);
      double d7 = (double)(this.getYPos() + 4.5F);
      Color color = new Color(34, 36, 43, super.fadeAlpha);
      double d3 = d7;
      double d2 = d6;
      double d1 = d5;
      double d0 = d4;
      RenderUtil.drawRect(d0, d1, d2, d3, color.getRGB());
      d4 = (double)f;
      d5 = (double)(this.getYPos() + 1.0F);
      d6 = (double)(f + (float)i1);
      d7 = (double)(this.getYPos() + 5.0F);
      int k;
      if (this.sliderDragging) {
         Astolfo astolfo = Astolfo.INSTANCE;
         k = astolfo.getSidebarGui().getPulseColor();
      } else {
         k = new Color(255, 255, 255, super.fadeAlpha).getRGB();
      }

      RenderUtil.drawRect(d4, d5, d6, d7, k);
      int jx = super.fadeAlpha;
      if (jx > 40) {
         GuiMainMenu.robotoRegularFont10.drawString(s, f + (float)i1, this.getYPos() + 4.0F, new Color(255, 255, 255, super.fadeAlpha).getRGB());
      }

      if (super.fadeAlpha > 40) {
         client.getCustomFontRenderer()
            .drawString(this.getNumberSetting().getName(), this.getX(), this.getYPos(), new Color(255, 255, 255, super.fadeAlpha).getRGB());
      }
   }

   public int updateAndGetFillWidth(int i, int j, int k) {
      int i1 = Math.max(Math.min(i - j, k), 0);
      NumberSetting numbersetting = this.getNumberSetting();
      if (this.sliderDragging) {
         this.sliderDragging = Mouse.isButtonDown(0);
         Class<Integer> oclass1 = Integer.class;
         Class oclass = numbersetting.valueClass;
         if (oclass == oclass1) {
            numbersetting.setValue(
               (int)(
                  (float)((Integer)numbersetting.getMin()).intValue()
                     + 0.1F
                     + (float)i1
                        * (((float)((Integer)numbersetting.getMax()).intValue() + 0.1F - (float)((Integer)numbersetting.getMin()).intValue() + 0.1F) / (float)k)
               )
            );
         } else if (numbersetting.valueClass == Double.class) {
            numbersetting.setValue(
               (Double)numbersetting.getMin() + (double)i1 * (((Double)numbersetting.getMax() - (Double)numbersetting.getMin()) / (double)k)
            );
         } else {
            numbersetting.setValue((Float)numbersetting.getMin() + (float)i1 * (((Float)numbersetting.getMax() - (Float)numbersetting.getMin()) / (float)k));
         }
      }

      Class<Integer> oclass3 = Integer.class;
      Class oclass2 = numbersetting.valueClass;
      if (oclass2 == oclass3) {
         return (int)(
            ((float)((Integer)numbersetting.getValue()).intValue() + 0.1F - (float)((Integer)numbersetting.getMin()).intValue() + 0.1F)
               / ((float)((Integer)numbersetting.getMax()).intValue() + 0.1F - (float)((Integer)numbersetting.getMin()).intValue() + 0.1F)
               * (float)k
         );
      } else {
         return numbersetting.valueClass == Double.class
            ? (int)(
               ((Double)numbersetting.getValue() - (Double)numbersetting.getMin())
                  / ((Double)numbersetting.getMax() - (Double)numbersetting.getMin())
                  * (double)k
            )
            : (int)(
               ((Float)numbersetting.getValue() - (Float)numbersetting.getMin()) / ((Float)numbersetting.getMax() - (Float)numbersetting.getMin()) * (float)k
            );
      }
   }

   public NumberSetting getNumberSetting() {
      return (NumberSetting)this.getSetting();
   }
}
