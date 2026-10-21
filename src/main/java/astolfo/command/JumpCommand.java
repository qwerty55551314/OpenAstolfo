package astolfo.command;

import astolfo.ClientAccess;
import astolfo.event.EventHandler;
import astolfo.event.Listener;
import astolfo.event.player.MoveEvent;
import java.util.Arrays;
import net.minecraft.client.entity.EntityPlayerSP;

public final class JumpCommand extends Command {
   private boolean leftGround;
   @EventHandler(MoveEvent.class)
   private Listener<MoveEvent> onMove = moveEvent -> {
      switch (moveEvent.getState()) {
         case PRE:
            float f12;
            int i = (f12 = ClientAccess.mc.thePlayer.moveForward - 0.0F) == 0.0F ? 0 : (f12 < 0.0F ? -1 : 1);
            float f3 = ClientAccess.mc.thePlayer.rotationYaw;
            float f15 = f3 + (float)(i < 0 ? 180 : 0);
            float f6 = 0.0F;
            float f5 = ClientAccess.mc.thePlayer.moveStrafing;
            float f4 = f15;
            f15 = f4;
            float f16;
            if (f5 > f6) {
               float f13;
               int j = (f13 = ClientAccess.mc.thePlayer.moveForward - 0.0F) == 0.0F ? 0 : (f13 < 0.0F ? -1 : 1);
               float f7 = -90.0F;
               f15 = f4;
               f16 = f7 * (j > 0 ? 0.5F : (ClientAccess.mc.thePlayer.moveForward < 0.0F ? -0.5F : 1.0F));
            } else {
               f16 = 0.0F;
            }

            f15 += f16;
            float f10 = 0.0F;
            float f9 = ClientAccess.mc.thePlayer.moveStrafing;
            float f8 = f15;
            f15 = f8;
            if (f9 < f10) {
               float f14;
               int k = (f14 = ClientAccess.mc.thePlayer.moveForward - 0.0F) == 0.0F ? 0 : (f14 < 0.0F ? -1 : 1);
               float f11 = -90.0F;
               f15 = f8;
               f16 = f11 * (k > 0 ? 0.5F : (ClientAccess.mc.thePlayer.moveForward < 0.0F ? -0.5F : 1.0F));
            } else {
               f16 = 0.0F;
            }

            float f = f15 - f16;
            float f1 = (float)Math.cos((double)(f + 90.0F) * Math.PI / 180.0);
            float f2 = (float)Math.sin((double)(f + 90.0F) * Math.PI / 180.0);
            ClientAccess.mc.thePlayer.setSprinting(true);
            ClientAccess.mc.thePlayer.onGround = true;
            EntityPlayerSP entityplayersp = ClientAccess.mc.thePlayer;
            if (entityplayersp.isCollidedVertically) {
               ClientAccess.mc.thePlayer.motionY = 0.42F;
            } else {
               this.leftGround = true;
               ClientAccess.mc.thePlayer.motionX = (double)f1 * 0.36;
               ClientAccess.mc.thePlayer.motionZ = (double)f2 * 0.36;
            }

            boolean flag = this.leftGround;
            if (flag && ClientAccess.mc.thePlayer.isCollidedVertically) {
               ClientAccess.mc.thePlayer.setVelocity(0.0, 0.0, 0.0);
               ClientAccess.client.getEventBus().unsubscribe(this);
            }
      }
   };

   public JumpCommand(String string, String string2) {
      super(string, string2);
      this.setAliases(Arrays.asList("jump", "bounce"));
   }

   @Override
   public void runCommand(String[] strings) {
      ClientAccess.client.getEventBus().subscribe(this);
      this.leftGround = false;
   }
}
