package astolfo.module.visuals;

import astolfo.module.Category;
import astolfo.module.Module;
import astolfo.setting.NumberSetting;
import astolfo.setting.Setting;

public class MoreParticles extends Module {
   public static final NumberSetting<Integer> enchantAmount = new NumberSetting<>("Enchant-Amount", "Amount of particles displayed", 1, 0, 20);
   public static final NumberSetting<Integer> criticalAmount = new NumberSetting<>("Critical-Amount", "Amount of particles displayed", 1, 0, 20);

   public MoreParticles(Category category) {
      super(category);
      this.addSettings(new Setting[]{enchantAmount, criticalAmount});
   }
}
