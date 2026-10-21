package astolfo.event.world;

import astolfo.event.CancellableEvent;

public class AbortBreakingEvent extends CancellableEvent {
   public AbortBreakingEvent(boolean bl) {
      this.setCancelled(bl);
   }
}
