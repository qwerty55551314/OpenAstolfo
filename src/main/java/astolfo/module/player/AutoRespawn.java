package astolfo.module.player;

import astolfo.ClientAccess;
import astolfo.event.EventHandler;
import astolfo.event.Listener;
import astolfo.event.player.UpdateEvent;
import astolfo.module.Category;
import astolfo.module.Module;
import astolfo.setting.BooleanSetting;
import astolfo.setting.Setting;
import net.minecraft.client.gui.GuiGameOver;

public final class AutoRespawn extends Module {
   private final BooleanSetting instant = new BooleanSetting("Instant", "Instantly respawns", false);
   @EventHandler(UpdateEvent.class)
   private Listener<UpdateEvent> onUpdate = this::onUpdate;

   public AutoRespawn(Category category) {
      super(category);
      this.setHidden(false);
      this.addSettings(new Setting[]{this.instant});
   }

   private void onUpdate(UpdateEvent updateEvent) {
      boolean flag = this.instant.asBoolean();
      if (!flag || ClientAccess.mc.thePlayer.getHealth() != 0.0F) {
         boolean flag1 = ClientAccess.mc.currentScreen instanceof GuiGameOver;
         if (!flag1 || ((GuiGameOver)ClientAccess.mc.currentScreen).enableButtonsTimer < 20) {
            return;
         }
      }

      ClientAccess.mc.thePlayer.respawnPlayer();
      ClientAccess.mc.displayGuiScreen(null);
   }
}
