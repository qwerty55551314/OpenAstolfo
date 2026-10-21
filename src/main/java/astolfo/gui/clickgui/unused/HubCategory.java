package astolfo.gui.clickgui.unused;

import astolfo.Astolfo;
import astolfo.gui.component.Container;
import astolfo.module.Category;
import astolfo.module.Module;
import astolfo.module.ModuleInfo;
import astolfo.module.ModuleManager;
import astolfo.render.RenderUtil;
import java.awt.Color;

public class HubCategory extends Container {
   private HubPanel hubPanel;
   private Category category;

   public HubCategory(HubPanel hubPanel, Category category, int i, int j, int k, int l) {
      super(category.getDisplayName(), (float)i, (float)j, k, l);
      this.hubPanel = hubPanel;
      this.category = category;
   }

   @Override
   public void render(int i, int j) {
      HubCategory hubcategory = this.hubPanel.getSelectedCategory();
      if (hubcategory != this) {
         if (this.isHovered(i, j)) {
            RenderUtil.drawRect(
               (double)this.getX(),
               (double)this.getYPos(),
               (double)(this.getX() + (float)this.getWidth()),
               (double)(this.getYPos() + (float)this.getHeight()),
               client.getColorSetting().getValue().getRGB()
            );
            RenderUtil.drawRect(
               (double)(this.getX() + 0.5F),
               (double)(this.getYPos() + 0.5F),
               (double)(this.getX() + (float)this.getWidth() - 0.5F),
               (double)(this.getYPos() + (float)this.getHeight() - 0.5F),
               new Color(32, 31, 32).getRGB()
            );
         }

         RenderUtil.setColor(this.isHovered(i, j) ? client.getColorSetting().getValue().getRGB() : new Color(143, 142, 143).getRGB());
         client.getCustomFontRenderer()
            .drawString(
               this.getName(),
               this.getX() + (float)(this.getWidth() / 2) - (float)(client.getCustomFontRenderer().getStringWidth(this.getName()) / 2),
               this.getYPos() + (float)this.getHeight() - 18.0F,
               -1
            );
      } else {
         this.getChildren().clear();
         Astolfo astolfo = client;
         ModuleManager modulemanager1 = astolfo.getModuleManager();
         Category categoryx = this.category;
         ModuleManager modulemanager = modulemanager1;

         for (ModuleInfo moduleinfo : modulemanager.getModules(categoryx)) {
            if (moduleinfo instanceof Module) {
               Module module = (Module)moduleinfo;
            }
         }
      }
   }

   @Override
   public void onClick(int i) {
   }

   public HubPanel getHubPanel() {
      return this.hubPanel;
   }

   public void setHubPanel(HubPanel hubPanel) {
      this.hubPanel = hubPanel;
   }

   public Category getCategory() {
      return this.category;
   }
}
