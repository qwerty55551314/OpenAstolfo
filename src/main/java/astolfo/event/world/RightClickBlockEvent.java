package astolfo.event.world;

import astolfo.event.CancellableEvent;
import astolfo.event.EventState;

public class RightClickBlockEvent extends CancellableEvent {
   private EventState state;

   public RightClickBlockEvent(EventState eventState) {
      this.state = eventState;
   }
}
