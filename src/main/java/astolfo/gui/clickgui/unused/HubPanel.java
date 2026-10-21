package astolfo.gui.clickgui.unused;

import astolfo.gui.component.Component;
import astolfo.gui.component.Container;
import astolfo.gui.component.Element;
import astolfo.render.RenderUtil;
import java.awt.Color;
import org.lwjgl.input.Mouse;

public class HubPanel extends Container {
   private HubCategory selectedCategory;
   private static int[] p;

   public HubPanel(int i, int j, int k, int l) {
      super("Hub", (float)i, (float)j, k, l);
      this.setDraggable(true);
   }

   @Override
   public void render(int i, int j) {
      RenderUtil.drawRect(
         (double)(this.getX() - 4.0F),
         (double)(this.getYPos() - 4.0F),
         (double)(this.getX() + (float)this.getWidth() + 4.0F),
         (double)(this.getYPos() + (float)this.getHeight() + 4.0F),
         new Color(67, 63, 65).getRGB()
      );
      RenderUtil.drawRect(
         (double)this.getX(),
         (double)this.getYPos(),
         (double)(this.getX() + (float)this.getWidth()),
         (double)(this.getYPos() + (float)this.getHeight()),
         new Color(32, 31, 32).getRGB()
      );
      if (this.selectedCategory != null) {
         this.selectedCategory.setHubPanel(this);
         client.getCustomFontRenderer().drawString(this.selectedCategory.getName(), this.getX() + 50.0F, this.getYPos() + 5.0F, Color.WHITE.getRGB());
         this.selectedCategory.render(i, j);
      } else {
         client.getCustomFontRenderer()
            .drawString(
               this.getName(),
               this.getX() + (float)(this.getWidth() / 2) - (float)(client.getCustomFontRenderer().getStringWidth("Hub") / 2),
               this.getYPos() + 5.0F,
               Color.WHITE.getRGB()
            );
         int i1 = 0;
         int j1 = 0;

         for (Element element : this.getChildren()) {
            if (element instanceof HubCategory) {
               Component component = (Component)element;
               float f = this.getX() + (float)(this.getWidth() / 3 * j1);
               int k = i1 >= 3 ? 110 : 20;
               component.setPosition(f, this.getYPos() + (float)k);
               component.render(i, j);
               ((HubCategory)component).setHubPanel(this);
               i1++;
               if (++j1 >= 3) {
                  j1 = 0;
               }
            }
         }

         if (this.selectedCategory == null) {
            for (Element element1 : this.getChildren()) {
               if (element1 instanceof HubCategory) {
                  Component component1 = (Component)element1;
                  boolean flag = this.isHovered(i, j);
                  if (flag && Mouse.isButtonDown(0)) {
                     this.selectedCategory = (HubCategory)component1;
                  }
               }
            }
         }
      }
   }

   @Override
   public void onClick(int i) {
   }

   public HubCategory getSelectedCategory() {
      return this.selectedCategory;
   }

   public static void n(int[] iArray) {
      p = iArray;
   }

   public static int[] R() {
      return p;
   }

   static {
      n(new int[2]);
   }
}
