package astolfo.auth.handler;

import astolfo.ClientAccess;
import astolfo.auth.net.PacketOutput;
import astolfo.auth.packet.PacketId;

public abstract class PacketHandler<T> implements ClientAccess {
   private final PacketId packetId;

   public PacketHandler(PacketId packetId) {
      this.packetId = packetId;
   }

   public abstract void handle(T object, PacketOutput packetOutput);

   public PacketId getPacketId() {
      return this.packetId;
   }
}
