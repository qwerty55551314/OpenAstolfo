package astolfo.module.movement;

import astolfo.ClientAccess;
import astolfo.event.EventHandler;
import astolfo.event.Listener;
import astolfo.event.player.SafeWalkEvent;
import astolfo.module.Category;
import astolfo.module.Module;
import astolfo.setting.BooleanSetting;
import astolfo.setting.Setting;

public final class SafeWalk extends Module {
   private final BooleanSetting air = new BooleanSetting("Air", "Safe walks on air", false);
   @EventHandler(SafeWalkEvent.class)
   private Listener<SafeWalkEvent> onSafeWalk = safeWalkEvent -> {
      if (ClientAccess.mc.thePlayer != null) {
         safeWalkEvent.setSafe(!this.air.getValue());
         safeWalkEvent.cancel();
      }
   };

   public SafeWalk(Category category) {
      super(category);
      this.addSettings(new Setting[]{this.air});
   }
}
