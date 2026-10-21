package astolfo.module.other;

import astolfo.event.EventHandler;
import astolfo.event.EventState;
import astolfo.event.Listener;
import astolfo.event.player.UpdateEvent;
import astolfo.module.Category;
import astolfo.module.Module;
import astolfo.setting.BooleanSetting;
import astolfo.setting.Setting;
import org.lwjgl.input.Keyboard;

public class FreeLook extends Module {
   public static final BooleanSetting mouseWheel = new BooleanSetting("Mouse-Wheel", "Works when clicking mouse-wheel", false);
   @EventHandler(UpdateEvent.class)
   private Listener<UpdateEvent> onUpdate = this::onUpdate;

   public FreeLook(Category category) {
      super(category);
      this.setHidden(false);
      this.addSettings(new Setting[]{mouseWheel});
   }

   private void onUpdate(UpdateEvent updateEvent) {
      if (updateEvent.getState() == EventState.PRE && !Keyboard.isKeyDown(this.getKey()) && !mouseWheel.getValue()) {
         this.setEnabled(false);
      }
   }
}
