package astolfo.gui.clickgui.defaultgui;

import astolfo.gui.component.Container;
import astolfo.gui.component.Element;
import astolfo.module.Category;
import astolfo.module.visuals.HUD;
import astolfo.render.RenderUtil;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.util.ResourceLocation;

public class CategoryPanel extends Container {
   private Category category;
   private static final ResourceLocation eyeOpenIcon = new ResourceLocation("client/icon/eye_open.png");
   private static final ResourceLocation eyeClosedIcon = new ResourceLocation("client/icon/eye_close.png");

   public CategoryPanel(Category category, int i, int j, int k, int l) {
      super(category.getDisplayName(), (float)i, (float)j, k, l);
      this.category = category;
      this.setDraggable(true);
   }

   @Override
   public void onClick(int i) {
      if (i == 1) {
         this.setExpanded(!this.isExpanded());
      }
   }

   @Override
   public void render(int i, int j) {
      int i1 = this.getModulesHeight() + 2;
      boolean flag = HUD.gui.getSetting("Category-Colors").asBoolean();
      int j1 = flag ? this.category.getColor() : client.getColorSetting().getValue().getRGB();
      RenderUtil.drawRect(
         (double)(this.getX() - 0.5F),
         (double)(this.getYPos() - 0.5F),
         (double)(this.getX() + (float)this.getWidth() + 0.5F),
         (double)(this.getYPos() + (float)this.getHeight() + (float)i1 + 0.5F),
         j1
      );
      RenderUtil.drawRect(
         (double)this.getX(),
         (double)this.getYPos(),
         (double)(this.getX() + (float)this.getWidth()),
         (double)(this.getYPos() + (float)this.getHeight() + (float)i1),
         new Color(1710618).getRGB()
      );
      RenderUtil.drawRect(
         (double)(this.getX() + 2.0F),
         (double)(this.getYPos() + 17.0F),
         (double)(this.getX() + (float)this.getWidth() - 2.0F),
         (double)(this.getYPos() + (float)this.getHeight() + (float)i1 - 2.0F),
         new Color(37, 37, 37).getRGB()
      );
      boolean flag1 = this.isExpanded();
      RenderUtil.setColor(flag1 ? j1 : new Color(1710618).brighter().brighter().getRGB());
      Minecraft.getMinecraft().getTextureManager().bindTexture(this.isExpanded() ? eyeOpenIcon : eyeClosedIcon);
      Gui.drawModalRectWithCustomSizedTexture((int)(this.getX() + (float)this.getWidth() - 21.0F), (int)this.getYPos() + 4, 0.0F, 0.0F, 8, 8, 8.0F, 8.0F);
      RenderUtil.setColor(j1);
      Minecraft.getMinecraft()
         .getTextureManager()
         .bindTexture(new ResourceLocation(String.format("client/categories/%s.png", this.category.getDisplayName().toLowerCase())));
      Gui.drawModalRectWithCustomSizedTexture((int)(this.getX() + (float)this.getWidth() - 11.0F), (int)this.getYPos() + 4, 0.0F, 0.0F, 8, 8, 8.0F, 8.0F);
      GuiMainMenu.latoBoldFont15.drawStringWithShadow(this.category.getDisplayName().toLowerCase(), this.getX() + 4.0F, this.getYPos() + 3.5F, -1);
      boolean flag2 = this.isExpanded();
      if (flag2 || super.expandAnimation.isRunning()) {
         int k = 0;

         for (Element element : this.getChildren()) {
            if (element instanceof ModuleButton) {
               ModuleButton modulebutton = (ModuleButton)element;
               modulebutton.setPosition(this.getX() + 2.0F, this.getYPos() + (float)k + 17.0F);
               boolean flag3 = this.isExpanded();
               if (flag3) {
                  modulebutton.render(i, j);
               }

               boolean flag4 = modulebutton.isExpanded();
               int i1x = k;
               int j1x;
               if (!flag4) {
                  j1x = 15;
               } else {
                  j1x = modulebutton.getSettingsHeight();
                  boolean flag5 = this.hasExpandedMapComponent((ModuleButton)element);
                  int l = j1x;
                  i1x = k;
                  j1x = l + (flag5 ? this.getMapComponentsHeight((ModuleButton)element) + 2 : 0);
               }

               k = i1x + j1x;
            }
         }
      }
   }

   private int getModulesHeight() {
      int i = 0;
      if (this.isExpanded()) {
         for (Element element : this.getChildren()) {
            if (element instanceof ModuleButton) {
               if (((ModuleButton)element).isExpanded()) {
                  i += ((ModuleButton)element).getSettingsHeight()
                     + (this.hasExpandedMapComponent((ModuleButton)element) ? this.getMapComponentsHeight((ModuleButton)element) + 2 : 0);
               } else {
                  i += 15;
               }
            }
         }
      }

      return i;
   }

   private boolean hasExpandedMapComponent(ModuleButton moduleButton) {
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
