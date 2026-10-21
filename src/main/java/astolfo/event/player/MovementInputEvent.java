package astolfo.event.player;

import astolfo.event.CancellableEvent;
import astolfo.event.Event;
import astolfo.event.EventState;

public class MovementInputEvent extends CancellableEvent implements Event {
   private EventState state;

   public MovementInputEvent(EventState eventState) {
      this.state = eventState;
   }

   public EventState getState() {
      return this.state;
   }
}
