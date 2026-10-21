package astolfo.gui.clickgui.transparent;

import astolfo.Astolfo;
import astolfo.gui.component.Container;
import astolfo.gui.component.Element;
import astolfo.module.Category;
import astolfo.render.RenderUtil;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public class CategoryPanel extends Container {
   private Category category;
   private static final Color lightColor = new Color(38, 37, 38);
   private static final Color darkColor = new Color(26, 26, 26);

   public CategoryPanel(Category category, int i, int j, int k, int l) {
      super(category.getDisplayName(), (float)i, (float)j, k, l);
      this.category = category;
      this.setDraggable(true);
   }

   @Override
   public void render(int i, int j) {
      int i1 = this.getModulesHeight() + 4;
      double d0 = (double)this.getX();
      double d1 = (double)this.getYPos();
      double d2 = (double)(this.getX() + (float)this.getWidth());
      double d3 = (double)(this.getYPos() + (float)this.getHeight() + 6.0F);
      Astolfo astolfo = client;
      RenderUtil.drawRect(d0, d1, d2, d3, astolfo.getColor().getRGB());
      client.getCustomFontRenderer().drawStringWithShadow(this.category.getDisplayName(), this.getX() + 2.0F, this.getYPos() + 2.0F, Color.WHITE.getRGB());
      boolean flag = this.isExpanded();
      int l;
      if (flag) {
         Astolfo astolfo1 = client;
         l = astolfo1.getColor().brighter().getRGB();
      } else {
         l = new Color(55, 55, 55).getRGB();
      }

      RenderUtil.setColor(l);
      Minecraft.getMinecraft()
         .getTextureManager()
         .bindTexture(new ResourceLocation(String.format("client/categories/%s.png", this.category.getDisplayName().toLowerCase())));
      Gui.drawModalRectWithCustomSizedTexture((int)(this.getX() + (float)this.getWidth() - 10.0F), (int)this.getYPos() + 4, 0.0F, 0.0F, 8, 8, 8.0F, 8.0F);
      boolean flag1 = this.isExpanded();
      if (flag1 || super.expandAnimation.isRunning()) {
         int j1 = 0;

         for (Element element : this.getChildren()) {
            if (element instanceof ModuleButton) {
               ModuleButton modulebutton = (ModuleButton)element;
               modulebutton.setPosition(this.getX(), this.getYPos() + (float)j1 + 17.0F);
               boolean flag2 = this.isExpanded();
               if (flag2) {
                  modulebutton.render(i, j);
               }

               boolean flag3 = modulebutton.isExpanded();
               int i1x = j1;
               int j1x;
               if (!flag3) {
                  j1x = 15;
               } else {
                  int k1 = modulebutton.getExpandedHeight();
                  boolean flag4 = this.hasExpandedMap((ModuleButton)element);
                  int k = k1;
                  i1x = j1;
                  j1x = k + (flag4 ? this.getMapComponentsHeight((ModuleButton)element) : 0);
               }

               j1 = i1x + j1x;
               j1 = (int)((double)j1 - super.expandAnimation.getValue(0.0, (double)j1));
            }
         }
      }
   }

   @Override
   public void onClick(int i) {
      if (i == 1) {
         boolean flag = this.isExpanded();
         this.setExpanded(!flag);
         if (this.isExpanded()) {
            super.expandAnimation.backwards().rewind().start();
         } else {
            super.expandAnimation.forwards().rewind().start();
         }
      }
   }

   private int getModulesHeight() {
      int i = 0;
      if (this.isExpanded()) {
         for (Element element : this.getChildren()) {
            if (element instanceof ModuleButton) {
               if (((ModuleButton)element).isExpanded()) {
                  i += ((ModuleButton)element).getExpandedHeight()
                     + (this.hasExpandedMap((ModuleButton)element) ? this.getMapComponentsHeight((ModuleButton)element) : 0);
               } else {
                  i += 15;
               }
            }
         }
      }

      return i;
   }

   private boolean hasExpandedMap(ModuleButton moduleButton) {
      if (!moduleButton.getChildren().isEmpty()) {
         for (Element element : moduleButton.getChildren()) {
            if (element instanceof MapComponent && ((MapComponent)element).isExpanded()) {
               return true;
            }
         }
      }

      return false;
   }

   private int getMapComponentsHeight(ModuleButton moduleButton) {
      int i = 0;
      if (!moduleButton.getChildren().isEmpty()) {
         for (Element element : moduleButton.getChildren()) {
            if (element instanceof MapComponent) {
               i += ((MapComponent)element).getChildrenHeight();
            }
         }
      }

      return i;
   }

   public Category getCategory() {
      return this.category;
   }
}
