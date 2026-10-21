package astolfo.gui.tabgui;

import astolfo.gui.TabGui;
import astolfo.setting.BooleanSetting;
import astolfo.setting.EnumSetting;
import astolfo.setting.MapSetting;
import astolfo.setting.NumberSetting;
import astolfo.setting.Setting;

public class MapSettingItem extends TabGuiItem {
   private final MapSetting setting;

   public MapSettingItem(TabGui.Panel panel, MapSetting mapSetting) {
      super(panel);
      this.setting = mapSetting;
   }

   @Override
   public String getDisplayText() {
      return "§f" + this.setting.getName() + "...";
   }

   @Override
   public void onPress() {
      boolean flag = false;
      TabGui.Panel tabgui$panel = new TabGui.Panel(this.getPanel().getTabGui());
      this.setting.getValue().forEach((string, setting) -> {
         boolean flag1 = setting instanceof BooleanSetting;
         if (flag1) {
            if (!setting.getName().equals("Hide")) {
               tabgui$panel.addItem(new BooleanSettingItem(tabgui$panel, (BooleanSetting)setting));
            }
         } else {
            boolean flag2 = setting instanceof NumberSetting;
            if (flag2) {
               tabgui$panel.addItem(new NumberSettingItem(tabgui$panel, (NumberSetting)setting));
            } else if (setting instanceof EnumSetting) {
               tabgui$panel.addItem(new EnumSettingItem(tabgui$panel, (EnumSetting)setting));
            }
         }
      });
      this.getPanel().getTabGui().addElement(tabgui$panel);
   }

   @Override
   public void onExpand() {
      this.onPress();
   }
}
