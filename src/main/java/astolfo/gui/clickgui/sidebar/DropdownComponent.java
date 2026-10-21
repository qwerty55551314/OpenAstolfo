package astolfo.gui.clickgui.sidebar;

import astolfo.Astolfo;
import astolfo.setting.Setting;

public abstract class DropdownComponent extends SidebarSettingComponent {
   public DropdownComponent(ModuleButton moduleButton, Setting setting, float f, float g, int i, int j) {
      super(moduleButton, setting, f, g, i, j);
   }

   @Override
   public void onClick(int i) {
      if (i == 0 && this.isVisible()) {
         Astolfo astolfo = client;
         SidebarGui sidebargui = astolfo.getSidebarGui();
         Astolfo astolfo1 = client;
         sidebargui.setOpenDropdown(astolfo1.getSidebarGui().getOpenDropdown() != null ? null : this);
      }
   }

   public void renderDropdown(int i, int j) {
   }
}
