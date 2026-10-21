package astolfo.gui.clickgui.transparent;

import astolfo.gui.clickgui.SettingComponent;
import astolfo.gui.clickgui.SettingContainer;
import astolfo.gui.component.Container;
import astolfo.gui.component.Element;
import astolfo.render.RenderUtil;
import astolfo.setting.Setting;
import java.awt.Color;

public class MapComponent extends SettingContainer {
   private static final Color backgroundColor = new Color(23, 23, 23);
   private static int[] R;

   public MapComponent(Container container, Setting setting, int i, int j) {
      super(container, setting, i, j);
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
      client.getCustomFontRenderer()
         .drawStringWithShadow(
            this.getMapSetting().getName() + "...",
            this.getX() + (float)(this.getWidth() / 2) - (float)(client.getCustomFontRenderer().getStringWidth(this.getSetting().getName() + "...") / 2) + 3.0F,
            this.getYPos(),
            Color.WHITE.getRGB()
         );
      boolean flag = this.isExpanded();
      if (flag) {
         int i1 = 0;

         for (Element element : this.getChildren()) {
            boolean flag1 = element instanceof SettingComponent;
            if (flag1) {
               ((SettingComponent)element).setPosition(this.getX() + 1.5F, this.getYPos() + (float)i1 + 15.0F);
               element.render(i, j);
               i1 += 15;
               i1 = (int)((double)i1 - super.expandAnimation.getValue(0.0, (double)i1));
            }
         }
      }
   }

   @Override
   public void onClick(int i) {
      if (i == 0) {
         boolean flag = this.isExpanded();
         this.setExpanded(!flag);
         if (this.isExpanded()) {
            super.expandAnimation.backwards().rewind().start();
         } else {
            super.expandAnimation.forwards().rewind().start();
         }
      }
   }

   public int getChildrenHeight() {
      int b0 = 0;
      if (this.isExpanded()) {
         for (Element element : this.getChildren()) {
            if (element instanceof BooleanComponent
               || element instanceof StringComponent
               || element instanceof NumberSlider
               || element instanceof EnumComponent) {
               b0 += 15;
            }
         }
      }

      return b0;
   }

   static {
      E((int[])null);
   }

   public static void E(int[] iArray) {
      R = iArray;
   }

   public static int[] A() {
      return R;
   }
}
