package astolfo.event.render;

import astolfo.event.Event;

public class RenderHotbarEvent implements Event {
   private int slot;

   public RenderHotbarEvent(int i) {
      this.slot = i;
   }

   public int getSlot() {
      return this.slot;
   }

   public void setSlot(int i) {
      this.slot = i;
   }
}
