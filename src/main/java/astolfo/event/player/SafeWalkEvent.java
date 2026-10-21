package astolfo.event.player;

import astolfo.event.CancellableEvent;

public class SafeWalkEvent extends CancellableEvent {
   private boolean safe;

   public void setSafe(boolean bl) {
      this.safe = bl;
   }

   public boolean isSafe() {
      return this.safe;
   }
}
