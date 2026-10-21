package astolfo.module.movement;

import astolfo.ClientAccess;
import astolfo.event.EventHandler;
import astolfo.event.EventState;
import astolfo.event.Listener;
import astolfo.event.player.MotionEvent;
import astolfo.module.Category;
import astolfo.module.Module;
import astolfo.util.GameUtil;

public final class Water extends Module {
   private int swimTicks;
   @EventHandler(MotionEvent.class)
   private Listener<MotionEvent> onMotion = this::onMotion;

   public Water(Category category) {
      super(category);
      this.setHidden(false);
   }

   private double[] getMoveDirection(float f) {
      float f1 = ClientAccess.mc.thePlayer.rotationYaw + f;
      float f4;
      int i = (f4 = ClientAccess.mc.thePlayer.moveForward - 0.0F) == 0.0F ? 0 : (f4 < 0.0F ? -1 : 1);
      if (i < 0) {
         f1 += 180.0F;
      }

      float f5;
      int j = (f5 = ClientAccess.mc.thePlayer.moveStrafing - 0.0F) == 0.0F ? 0 : (f5 < 0.0F ? -1 : 1);
      if (j > 0) {
         f1 -= 90.0F * (ClientAccess.mc.thePlayer.moveForward < 0.0F ? -0.5F : (ClientAccess.mc.thePlayer.moveForward > 0.0F ? 0.5F : 1.0F));
      }

      if (ClientAccess.mc.thePlayer.moveStrafing < 0.0F) {
         float f6;
         int k = (f6 = ClientAccess.mc.thePlayer.moveForward - 0.0F) == 0.0F ? 0 : (f6 < 0.0F ? -1 : 1);
         float f3 = 90.0F;
         f1 += f3 * (k < 0 ? -0.5F : (ClientAccess.mc.thePlayer.moveForward > 0.0F ? 0.5F : 1.0F));
      }

      float f1x = (float)Math.cos((double)(f1 + 90.0F) * Math.PI / 180.0);
      float f2 = (float)Math.sin((double)(f1 + 90.0F) * Math.PI / 180.0);
      return new double[]{(double)f1x, (double)f2};
   }

   private void onMotion(MotionEvent motionEvent) {
      if (motionEvent.getState() == EventState.PRE && ClientAccess.mc.thePlayer.isInWater() && GameUtil.isMoving(ClientAccess.mc.thePlayer)) {
         this.swimTicks++;
         double d0 = this.getMoveDirection(0.0F)[0];
         double d1 = this.getMoveDirection(0.0F)[1];
         if (this.swimTicks >= 3) {
            ClientAccess.mc.thePlayer.motionX = d0 * 0.06F;
            ClientAccess.mc.thePlayer.motionZ = d1 * 0.06F;
            this.swimTicks = 0;
         } else {
            ClientAccess.mc.thePlayer.motionX = d0 * 0.27F;
            ClientAccess.mc.thePlayer.motionZ = d1 * 0.27F;
         }
      }
   }
}
