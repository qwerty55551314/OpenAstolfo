package astolfo.module.other;

import astolfo.ClientAccess;
import astolfo.event.EventHandler;
import astolfo.event.Listener;
import astolfo.event.world.DestroyBlockEvent;
import astolfo.event.world.RightClickBlockEvent;
import astolfo.module.Category;
import astolfo.module.Module;
import astolfo.setting.BooleanSetting;
import astolfo.setting.Setting;

public final class AntiDesync extends Module {
   private final BooleanSetting breakSetting = new BooleanSetting("Break", "Prevents ghost blocks when breaking blocks", true);
   private final BooleanSetting place = new BooleanSetting("Place", "Prevents ghost blocks when placing blocks", false);
   @EventHandler(DestroyBlockEvent.class)
   private final Listener<DestroyBlockEvent> onDestroyBlock = destroyBlockEvent -> {
      if (this.breakSetting.getValue()) {
         destroyBlockEvent.cancel();
      }
   };
   @EventHandler(RightClickBlockEvent.class)
   private final Listener<RightClickBlockEvent> onRightClickBlock = this::onHeldItemSync;

   public AntiDesync(Category category) {
      super(category);
      this.addSettings(new Setting[]{this.breakSetting, this.place});
      this.setHidden(false);
   }

   private void onHeldItemSync(RightClickBlockEvent rightClickBlockEvent) {
      if (this.place.getValue()) {
         ClientAccess.mc.thePlayer.swingItem();
         rightClickBlockEvent.cancel();
      }
   }
}
