package astolfo.event.player;

import astolfo.event.Event;
import astolfo.event.EventState;

public class UpdateEvent implements Event {
   private EventState state;

   public UpdateEvent(EventState eventState) {
      this.state = eventState;
   }

   public EventState getState() {
      return this.state;
   }
}
