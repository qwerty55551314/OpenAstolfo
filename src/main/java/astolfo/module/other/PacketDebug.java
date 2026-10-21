package astolfo.module.other;

import astolfo.event.EventHandler;
import astolfo.event.Listener;
import astolfo.event.network.PacketDirection;
import astolfo.event.network.PacketEvent;
import astolfo.module.Category;
import astolfo.module.Module;

public final class PacketDebug extends Module {
   @EventHandler(PacketEvent.class)
   private Listener<PacketEvent> onPacket = packetEvent -> {
      if (packetEvent.getDirection() == PacketDirection.OUTGOING) {
      }
   };

   public PacketDebug() {
      super("PacketDebug", Category.OTHER);
      this.setHidden(false);
   }
}
