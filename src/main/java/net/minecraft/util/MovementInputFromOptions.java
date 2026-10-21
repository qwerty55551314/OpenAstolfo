package net.minecraft.util;

import astolfo.Astolfo;
import astolfo.event.EventState;
import astolfo.event.player.MovementInputEvent;
import astolfo.module.ModuleManager;
import astolfo.module.movement.Sneak;
import astolfo.module.player.Scaffold;
import net.minecraft.client.settings.GameSettings;

public class MovementInputFromOptions extends MovementInput {
   private final GameSettings gameSettings;

   public MovementInputFromOptions(GameSettings gameSettingsIn) {
      this.gameSettings = gameSettingsIn;
   }

   @Override
   public void updatePlayerMoveState() {
      MovementInputEvent movementinputevent = new MovementInputEvent(EventState.PRE);
      Astolfo.INSTANCE.getEventBus().post(movementinputevent);
      super.moveStrafe = 0.0F;
      super.moveForward = 0.0F;
      if (!movementinputevent.isCancelled()) {
         if (this.gameSettings.keyBindForward.isKeyDown()) {
            super.moveForward++;
         }

         if (this.gameSettings.keyBindBack.isKeyDown()) {
            super.moveForward--;
         }

         if (this.gameSettings.keyBindLeft.isKeyDown()) {
            super.moveStrafe++;
         }

         if (this.gameSettings.keyBindRight.isKeyDown()) {
            super.moveStrafe--;
         }
      }

      boolean flag3;
      MovementInputFromOptions movementinputfromoptions;
      label50: {
         super.jump = this.gameSettings.keyBindJump.isKeyDown();
         Astolfo astolfo = Astolfo.INSTANCE;
         ModuleManager modulemanager3 = astolfo.getModuleManager();
         Class<Scaffold> oclass = Scaffold.class;
         ModuleManager modulemanager = modulemanager3;
         boolean flag = modulemanager.get(oclass).isEnabled();
         movementinputfromoptions = this;
         if (!flag || !Scaffold.down.getValue()) {
            boolean flag1 = this.gameSettings.keyBindSneak.isKeyDown();
            movementinputfromoptions = this;
            if (flag1) {
               flag3 = true;
               break label50;
            }
         }

         flag3 = false;
      }

      movementinputfromoptions.sneak = flag3;
      boolean flag2 = super.sneak;
      if (flag2) {
         Astolfo astolfo1 = Astolfo.INSTANCE;
         ModuleManager modulemanager2 = astolfo1.getModuleManager();
         Class<Sneak> oclass1 = Sneak.class;
         ModuleManager modulemanager1 = modulemanager2;
         if (modulemanager1.get(oclass1).isEnabled() && Sneak.hold.getValue()) {
            super.sneak = false;
         }
      }

      if (super.sneak) {
         super.moveStrafe = (float)((double)super.moveStrafe * 0.3);
         super.moveForward = (float)((double)super.moveForward * 0.3);
      }

      Astolfo.INSTANCE.getEventBus().post(new MovementInputEvent(EventState.POST));
   }
}
