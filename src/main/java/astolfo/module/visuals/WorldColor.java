package astolfo.module.visuals;

import astolfo.module.Category;
import astolfo.module.Module;
import astolfo.setting.ColorSetting;
import astolfo.setting.Setting;
import java.awt.Color;

public class WorldColor extends Module {
   public static final ColorSetting color = new ColorSetting("Color", "Color", Color.GRAY);

   public WorldColor(Category category) {
      super(category);
      this.addSettings(new Setting[]{color});
      this.setHidden(false);
   }
}
