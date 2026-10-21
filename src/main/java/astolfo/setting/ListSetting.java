package astolfo.setting;

import java.util.ArrayList;
import java.util.List;

public final class ListSetting extends Setting<List<Setting<?>>> {
   private static final long serialVersionUID = 109246710484L;

   public ListSetting(String string, String string2, List<Setting<?>> list) {
      super(string, string2, list, SettingType.LIST);
   }

   public ListSetting(String string, String string2) {
      super(string, string2, new ArrayList<>(), SettingType.LIST);
   }

   public List<Setting<?>> getSettings() {
      return this.getValue();
   }

   public Setting<?> getSetting(int i) {
      return this.getValue().get(i);
   }

   public Setting<?> find(Setting<?> setting) {
      for (Setting setting1 : this.getValue()) {
         if (setting1.equals(setting)) {
            return setting1;
         }
      }

      return null;
   }
}
