package astolfo.event.network;

import astolfo.event.CancellableEvent;

public class ResourcePackEvent extends CancellableEvent {
   private String hash;

   public ResourcePackEvent(String string) {
      this.hash = string;
   }

   public String getHash() {
      return this.hash;
   }
}
