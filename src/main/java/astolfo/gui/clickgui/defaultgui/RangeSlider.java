package astolfo.gui.clickgui.defaultgui;

import astolfo.gui.clickgui.SettingComponent;
import astolfo.gui.component.Container;
import astolfo.module.Category;
import astolfo.module.visuals.HUD;
import astolfo.render.RenderUtil;
import astolfo.setting.NumberSetting;
import astolfo.setting.Setting;
import java.awt.Color;
import java.text.DecimalFormat;
import net.minecraft.client.gui.GuiMainMenu;
import org.lwjgl.input.Mouse;

public class RangeSlider extends SettingComponent {
   private final Category category;
   private Setting maxSetting;
   private boolean draggingMin;
   private boolean draggingMax;
   private int lastMouseX;
   private int lastMouseY;
   private static final DecimalFormat decimalFormat = new DecimalFormat("#.##");

   public RangeSlider(Category category, Container container, Setting setting, Setting setting2, int i, int j) {
      super(container, setting, i, j);
      this.category = category;
      this.maxSetting = setting2;
   }

   @Override
   public void onClick(int i) {
      if (i == 0) {
         boolean flag = this.getParentContainer().isExpanded();
         if (flag) {
            int i1 = this.updateAndGetHandlePos(this.lastMouseX, (int)this.getX(), this.getWidth(), this.getSetting());
            int j = this.updateAndGetHandlePos(this.lastMouseX, (int)this.getX(), this.getWidth(), this.maxSetting);
            boolean flag1 = this.isMouseInRect(
               (int)(this.getX() + (float)i1 - 5.0F), (int)this.getYPos(), 7, this.getHeight(), this.lastMouseX, this.lastMouseY
            );
            if (flag1) {
               this.draggingMin = true;
            } else if (this.isMouseInRect((int)(this.getX() + (float)j - 5.0F), (int)this.getYPos(), 7, this.getHeight(), this.lastMouseX, this.lastMouseY)) {
               this.draggingMax = true;
            }
         }
      }
   }

   @Override
   public void render(int i, int j) {
      boolean flag2 = HUD.gui.getSetting("Category-Colors").asBoolean();
      int i1 = flag2 ? this.category.getColor() : client.getColorSetting().getValue().getRGB();
      NumberSetting numbersetting = (NumberSetting)this.maxSetting;
      int j1 = this.updateAndGetHandlePos(i, (int)this.getX(), this.getWidth(), this.getSetting());
      int k = this.updateAndGetHandlePos(i, (int)this.getX(), this.getWidth(), this.maxSetting);
      Class<Integer> oclass1 = Integer.class;
      Class oclass = numbersetting.valueClass;
      if (oclass == oclass1) {
         NumberSetting numbersetting1 = this.getMinSetting();
         if (numbersetting.asInt() < numbersetting1.asInt()) {
            numbersetting.setValue(numbersetting1.asInt());
         }

         if (numbersetting1.asInt() > numbersetting.asInt()) {
            numbersetting1.setValue(numbersetting.asInt());
         }
      } else if (numbersetting.valueClass == Double.class) {
         NumberSetting numbersetting2 = this.getMinSetting();
         boolean flag = false;
         boolean flag1 = false;
         double d0;
         int l = (d0 = numbersetting.asDouble() - numbersetting2.asDouble()) == 0.0 ? 0 : (d0 < 0.0 ? -1 : 1);
         if (l < 0) {
            flag = true;
         }

         double d1;
         int i1x = (d1 = numbersetting2.asDouble() - numbersetting.asDouble()) == 0.0 ? 0 : (d1 < 0.0 ? -1 : 1);
         if (i1x > 0) {
            flag1 = true;
         }

         if (flag) {
            numbersetting.setValue(numbersetting2.asDouble());
         }

         if (flag1) {
            numbersetting2.setValue(numbersetting.asDouble());
         }
      } else {
         NumberSetting numbersetting3 = this.getMinSetting();
         float f;
         int j1x = (f = numbersetting.asFloat() - numbersetting3.asFloat()) == 0.0F ? 0 : (f < 0.0F ? -1 : 1);
         if (j1x < 0) {
            numbersetting.setValue((Number)numbersetting3.getValue());
         }

         if (numbersetting.asFloat() > numbersetting3.asFloat()) {
            numbersetting3.setValue(numbersetting.asFloat());
         }
      }

      String s = decimalFormat.format(this.getMinSetting().getValue()) + "-" + decimalFormat.format(this.maxSetting.getValue());
      s = s + this.getMinSetting().getSuffix();
      RenderUtil.drawRect(
         (double)(this.getX() + (float)j1 - 3.0F),
         (double)this.getYPos(),
         (double)(this.getX() + (float)j1),
         (double)(this.getYPos() + (float)this.getHeight()),
         new Color(i1).darker().getRGB()
      );
      RenderUtil.drawRect(
         (double)(this.getX() + (float)j1), (double)this.getYPos(), (double)(this.getX() + (float)k), (double)(this.getYPos() + (float)this.getHeight()), i1
      );
      if (k > j1) {
         RenderUtil.drawRect(
            (double)(this.getX() + (float)k - 3.0F),
            (double)this.getYPos(),
            (double)(this.getX() + (float)k),
            (double)(this.getYPos() + (float)this.getHeight()),
            new Color(i1).darker().getRGB()
         );
      }

      GuiMainMenu.latoBoldFont15.drawString(this.getMinSetting().getName().replace("Min-", ""), this.getX() + 3.0F, this.getYPos(), -1);
      GuiMainMenu.latoBoldFont15
         .drawString(s, this.getX() + (float)this.getWidth() - (float)GuiMainMenu.latoBoldFont15.getStringWidth(s, true), this.getYPos() + 1.0F, 13421772);
      this.lastMouseX = i;
      this.lastMouseY = j;
   }

   public int updateAndGetHandlePos(int i, int j, int k, Setting setting) {
      int i1 = Math.max(Math.min(i - j, k), 0);
      NumberSetting numbersetting = (NumberSetting)setting;
      boolean flag = this.draggingMin;
      if (flag && setting == this.getMinSetting()) {
         this.draggingMin = Mouse.isButtonDown(0);
         if (numbersetting.valueClass == Integer.class) {
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

      if (this.draggingMax && setting == this.maxSetting) {
         this.draggingMax = Mouse.isButtonDown(0);
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
            ((float)numbersetting.asInt() + 0.1F - (float)((Integer)numbersetting.getMin()).intValue() + 0.1F)
               / ((float)((Integer)numbersetting.getMax()).intValue() + 0.1F - (float)((Integer)numbersetting.getMin()).intValue() + 0.1F)
               * (float)k
         );
      } else {
         return numbersetting.valueClass == Double.class
            ? (int)((numbersetting.asDouble() - (Double)numbersetting.getMin()) / ((Double)numbersetting.getMax() - (Double)numbersetting.getMin()) * (double)k)
            : (int)((numbersetting.asFloat() - (Float)numbersetting.getMin()) / ((Float)numbersetting.getMax() - (Float)numbersetting.getMin()) * (float)k);
      }
   }

   public NumberSetting getMinSetting() {
      return (NumberSetting)this.getSetting();
   }
}
