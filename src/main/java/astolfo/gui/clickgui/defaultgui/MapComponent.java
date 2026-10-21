package astolfo.gui.clickgui.defaultgui;

import astolfo.gui.clickgui.SettingComponent;
import astolfo.gui.clickgui.SettingContainer;
import astolfo.gui.component.Component;
import astolfo.gui.component.Container;
import astolfo.gui.component.Element;
import astolfo.render.RenderUtil;
import astolfo.setting.Setting;
import java.awt.Color;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.entity.Entity;

public class MapComponent extends SettingContainer {
   private static Entity[] e;

   public MapComponent(Container container, Setting setting, int i, int j) {
      super(container, setting, i, j);
   }

   @Override
   public void onClick(int i) {
      if (i == 0) {
         this.setExpanded(!this.isExpanded());
      }
   }

   @Override
   public void render(int i, int j) {
      int i1 = this.getChildrenHeight();
      boolean flag = this.isExpanded();
      if (flag) {
         RenderUtil.drawRect(
            (double)this.getX(),
            (double)this.getYPos(),
            (double)(this.getX() + (float)this.getWidth()),
            (double)(this.getYPos() + (float)this.getHeight() + (float)i1),
            new Color(18, 18, 18).getRGB()
         );
      }

      GuiMainMenu.latoBoldFont15
         .drawString(
            this.getMapSetting().getName() + "...",
            this.getX()
               + (float)(this.getWidth() / 2)
               - (float)(GuiMainMenu.latoBoldFont15.getStringWidth(this.getSetting().getName() + "...", true) / 2)
               + 3.0F,
            this.getYPos(),
            Color.WHITE.getRGB()
         );
      boolean flag1 = this.isExpanded();
      if (flag1) {
         int j1 = 0;

         for (Element element : this.getChildren()) {
            boolean flag2 = element instanceof SettingContainer;
            if (flag2) {
               ((SettingContainer)element).setPosition(this.getX() + 3.0F, this.getYPos() + (float)j1 + 10.0F);
               element.render(i, j);
               j1 += ((SettingContainer)element).getHeight() + (((SettingContainer)element).isExpanded() ? 3 : 0);
            }

            if (element instanceof SettingComponent) {
               ((SettingComponent)element).setPosition(this.getX() + 3.0F, this.getYPos() + (float)j1 + 10.0F);
               element.render(i, j);
               j1 += 10;
            }
         }
      }
   }

   public int getChildrenHeight() {
      int i = 0;
      boolean flag = this.isExpanded();
      if (flag) {
         for (Element element : this.getChildren()) {
            if (element instanceof BooleanComponent
               || element instanceof ColorPicker
               || element instanceof StringComponent
               || element instanceof NumberSlider
               || element instanceof RangeSlider
               || element instanceof EnumComponent) {
               i += ((Component)element).getHeight();
            }
         }
      }

      boolean flag1 = this.isExpanded();
      return i + (flag1 ? 3 : 0);
   }

   public static void K(Entity[] entityArray) {
      e = entityArray;
   }

   public static Entity[] w() {
      return e;
   }

   static {
      K((Entity[])null);
   }
}
