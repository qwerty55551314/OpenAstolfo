package astolfo.event.render;

import astolfo.event.Event;
import astolfo.event.EventState;

public class RenderArmEvent implements Event {
   private EventState state;

   public RenderArmEvent(EventState eventState) {
      this.state = eventState;
   }

   public EventState getState() {
      return this.state;
   }
}
