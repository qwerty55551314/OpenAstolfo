package astolfo.event.game;

import astolfo.event.Event;
import astolfo.event.EventState;

public class TickEvent implements Event {
   private EventState state;

   public TickEvent(EventState eventState) {
      this.state = eventState;
   }

   public EventState getState() {
      return this.state;
   }
}
