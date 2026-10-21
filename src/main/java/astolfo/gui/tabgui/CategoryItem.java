package astolfo.gui.tabgui;

import astolfo.Astolfo;
import astolfo.gui.TabGui;
import astolfo.module.Category;
import astolfo.module.Module;
import astolfo.module.ModuleInfo;

public class CategoryItem extends TabGuiItem {
   private final Category category;

   public CategoryItem(TabGui.Panel panel, Category category) {
      super(panel);
      this.category = category;
   }

   public Category getCategory() {
      return this.category;
   }

   @Override
   public String getDisplayText() {
      return this.category.getDisplayName();
   }

   @Override
   public void onPress() {
      Object object = null;
      TabGui.Panel tabgui$panel = new TabGui.Panel(this.getPanel().getTabGui());
      Astolfo astolfo = Astolfo.INSTANCE;
      astolfo.getModuleManager().getList().forEach(moduleInfo -> {
         if (moduleInfo instanceof Module && moduleInfo.getCategory() == this.category) {
            tabgui$panel.addItem(new ModuleItem(tabgui$panel, (Module)moduleInfo));
         }
      });
      if (tabgui$panel.getItems().size() != 0) {
         this.getPanel().getTabGui().addElement(tabgui$panel);
      }
   }

   @Override
   public void onExpand() {
      this.onPress();
   }
}
