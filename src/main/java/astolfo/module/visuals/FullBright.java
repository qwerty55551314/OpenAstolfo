package astolfo.module.visuals;

import astolfo.ClientAccess;
import astolfo.event.EventHandler;
import astolfo.event.Listener;
import astolfo.event.player.UpdateEvent;
import astolfo.module.Category;
import astolfo.module.Module;
import astolfo.setting.EnumSetting;
import astolfo.setting.NumberSetting;
import astolfo.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public final class FullBright extends Module {
   private final EnumSetting<FullBrightMode> mode = new EnumSetting<>("Mode", "Set your animation mode", FullBrightMode.WORLD);
   private final NumberSetting<Float> bright = new NumberSetting<>("Bright", "Bright level", 1.0F, 0.1F, 1.0F);
   private boolean nightVisionApplied;
   @EventHandler(UpdateEvent.class)
   private final Listener<UpdateEvent> onUpdate = this::onUpdate;

   public FullBright(Category category) {
      super(category);
      this.setHidden(false);
      this.addSettings(new Setting[]{this.mode, this.bright});
   }

   @Override
   public void onDisable() {
      Minecraft minecraft = ClientAccess.mc;
      if (minecraft.theWorld != null) {
         ClientAccess.mc.theWorld.provider.generateLightBrightnessTable();
      }

      if (ClientAccess.mc.thePlayer != null && this.nightVisionApplied) {
         ClientAccess.mc.thePlayer.removePotionEffect(Potion.nightVision.id);
      }

      this.nightVisionApplied = false;
      super.onDisable();
   }

   private void onUpdate(UpdateEvent updateEvent) {
      if (ClientAccess.mc.theWorld != null) {
         if (this.mode.getValue() == FullBrightMode.WORLD) {
            boolean flag = this.nightVisionApplied;
            if (flag) {
               ClientAccess.mc.thePlayer.removePotionEffect(Potion.nightVision.id);
               this.nightVisionApplied = false;
            }

            for (int i = 0; i < 16; i++) {
               if (ClientAccess.mc.theWorld.provider.lightBrightnessTable[i] != this.bright.getValue()) {
                  ClientAccess.mc.theWorld.provider.lightBrightnessTable[i] = this.bright.getValue();
               }
            }
         } else {
            PotionEffect potioneffect = new PotionEffect(Potion.nightVision.id, 7000, 1);
            potioneffect.setPotionDurationMax(true);
            ClientAccess.mc.thePlayer.addPotionEffect(potioneffect);
            this.nightVisionApplied = true;
         }
      }
   }
}
