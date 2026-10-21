package astolfo.gui.tabgui;

import astolfo.gui.TabGui;
import astolfo.module.Module;
import astolfo.setting.BooleanSetting;
import astolfo.setting.EnumSetting;
import astolfo.setting.MapSetting;
import astolfo.setting.NumberSetting;
import astolfo.setting.Setting;
import net.minecraft.entity.Entity;

public class ModuleItem extends TabGuiItem {
   private final Module module;
   private static Entity[] A;

   public ModuleItem(TabGui.Panel panel, Module module) {
      super(panel);
      this.module = module;
   }

   public Module getModule() {
      return this.module;
   }

   @Override
   public String getDisplayText() {
      return (this.module.isEnabled() ? "§f" : "§7") + this.module.getName();
   }

   @Override
   public void onPress() {
      this.module.toggle();
   }

   @Override
   public void onExpand() {
      Object object = null;
      TabGui.Panel tabgui$panel = new TabGui.Panel(this.getPanel().getTabGui());
      this.module.getSettings().forEach(setting -> {
         boolean flag = setting instanceof BooleanSetting;
         if (flag) {
            BooleanSetting booleansetting = (BooleanSetting)setting;
            if (!setting.getName().equals("Hide")) {
               tabgui$panel.addItem(new BooleanSettingItem(tabgui$panel, booleansetting));
            }
         } else {
            boolean flag1 = setting instanceof NumberSetting;
            if (flag1) {
               NumberSetting numbersetting = (NumberSetting)setting;
               tabgui$panel.addItem(new NumberSettingItem(tabgui$panel, numbersetting));
            } else {
               boolean flag2 = setting instanceof MapSetting;
               if (flag2) {
                  MapSetting mapsetting = (MapSetting)setting;
                  tabgui$panel.addItem(new MapSettingItem(tabgui$panel, mapsetting));
               } else if (setting instanceof EnumSetting) {
                  tabgui$panel.addItem(new EnumSettingItem(tabgui$panel, (EnumSetting)setting));
               }
            }
         }
      });
      if (tabgui$panel.getItems().size() != 0) {
         tabgui$panel.getTabGui().addElement(tabgui$panel);
      }
   }

   public static void j(Entity[] entityArray) {
      A = entityArray;
   }

   public static Entity[] T() {
      return A;
   }

   static {
      j((Entity[])null);
   }
}
