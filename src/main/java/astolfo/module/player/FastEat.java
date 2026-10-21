package astolfo.module.player;

import astolfo.event.EventHandler;
import astolfo.event.EventPriority;
import astolfo.event.Listener;
import astolfo.event.network.PacketDirection;
import astolfo.event.network.PacketEvent;
import astolfo.module.Category;
import astolfo.module.Module;

public class FastEat extends Module {
   private int unusedIntB;
   private int unusedIntA;
   private boolean unusedFlagA;
   private boolean unusedFlagB;
   @EventHandler(value = PacketEvent.class, priority = EventPriority.HIGH)
   private Listener<PacketEvent> onPacket = packetEvent -> {
      if (packetEvent.getDirection() == PacketDirection.OUTGOING) {
      }
   };

   public FastEat() {
      super("FastEat", Category.PLAYER);
   }
}
