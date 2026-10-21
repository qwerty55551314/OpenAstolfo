package astolfo.event.player;

import astolfo.event.Event;

public class RotationYawEvent implements Event {
   private float yaw;

   public RotationYawEvent(float f) {
      this.yaw = f;
   }

   public float getYaw() {
      return this.yaw;
   }

   public void setYaw(float f) {
      this.yaw = f;
   }
}
