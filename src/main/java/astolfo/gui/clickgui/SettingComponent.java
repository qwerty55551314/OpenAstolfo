package astolfo.gui.clickgui;

import astolfo.gui.component.Component;
import astolfo.gui.component.Container;
import astolfo.setting.BooleanSetting;
import astolfo.setting.NumberSetting;
import astolfo.setting.Setting;

public abstract class SettingComponent extends Component {
   private Setting setting;
   private Container parentContainer;

   public SettingComponent(Container container, Setting setting, int i, int j) {
      super(setting.getName(), 0.0F, 0.0F, i, j);
      this.parentContainer = container;
      this.setting = setting;
   }

   public boolean isBooleanSetting() {
      return this.setting instanceof BooleanSetting;
   }

   public boolean isNumberSetting() {
      return this.setting instanceof NumberSetting;
   }

   public Setting getSetting() {
      return this.setting;
   }

   public Container getParentContainer() {
      return this.parentContainer;
   }
}
