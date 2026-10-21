package astolfo.event.player;

import astolfo.event.CancellableEvent;
import net.minecraft.util.Vec3;

public class LookEvent extends CancellableEvent {
   private Vec3 vec;

   public LookEvent(Vec3 vec3) {
      this.vec = vec3;
   }

   public Vec3 getVec() {
      return this.vec;
   }

   public void setVec(Vec3 vec3) {
      this.vec = vec3;
   }
}
