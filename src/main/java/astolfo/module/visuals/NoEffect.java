package astolfo.module.visuals;

import astolfo.ClientAccess;
import astolfo.event.EventHandler;
import astolfo.event.Listener;
import astolfo.event.player.UpdateEvent;
import astolfo.event.render.HurtCameraEvent;
import astolfo.event.render.RenderOverlaysEvent;
import astolfo.module.Category;
import astolfo.module.Module;
import astolfo.setting.BooleanSetting;
import astolfo.setting.Setting;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.potion.Potion;

public final class NoEffect extends Module {
   private final BooleanSetting hurtCam = new BooleanSetting("Hurt-Cam", "Disables hurtcam effect", true);
   private final BooleanSetting overlay = new BooleanSetting("Overlay", "Disables pumpkin overlay and fire", false);
   private final BooleanSetting weather = new BooleanSetting("Weather", "Disables weather", true);
   private final BooleanSetting potions = new BooleanSetting("Potions", "Disables blindness", true);
   public static final BooleanSetting noFog = new BooleanSetting("No-Fog", "Disables fog", true);
   @EventHandler(HurtCameraEvent.class)
   private Listener<HurtCameraEvent> onHurtCamera = hurtCameraEvent -> {
      if (this.hurtCam.getValue()) {
         hurtCameraEvent.cancel();
      }
   };
   @EventHandler(UpdateEvent.class)
   private Listener<UpdateEvent> onUpdate = this::onUpdate;
   @EventHandler(RenderOverlaysEvent.class)
   private Listener<RenderOverlaysEvent> onRenderOverlays = renderOverlaysEvent -> {
      if (this.overlay.getValue()) {
         renderOverlaysEvent.cancel();
      }
   };

   public NoEffect(Category category) {
      super(category);
      this.setHidden(false);
      this.addSettings(new Setting[]{this.hurtCam, this.overlay, this.weather, this.potions, noFog});
   }

   private void onUpdate(UpdateEvent updateEvent) {
      WorldClient worldclient = ClientAccess.mc.theWorld;
      if (worldclient != null && this.weather.getValue()) {
         ClientAccess.mc.theWorld.setRainStrength(0.0F);
      }

      boolean flag = this.potions.getValue();
      if (flag && ClientAccess.mc.thePlayer.isPotionActive(Potion.blindness)) {
         ClientAccess.mc.thePlayer.removePotionEffect(Potion.blindness.id);
      }
   }
}
