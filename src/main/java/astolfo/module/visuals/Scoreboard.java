package astolfo.module.visuals;

import astolfo.module.Category;
import astolfo.module.Module;
import astolfo.setting.BooleanSetting;
import astolfo.setting.NumberSetting;
import astolfo.setting.Setting;

public final class Scoreboard extends Module {
   public static final NumberSetting<Float> position = new NumberSetting<>("Position", "Position of scoreboard", 150.0F, -200.0F, 200.0F);
   public static final BooleanSetting remove = new BooleanSetting("Remove", "Removes scoreboard", false);

   public Scoreboard(Category category) {
      super(category);
      this.addSettings(new Setting[]{position, remove});
      this.setHidden(false);
   }
}
