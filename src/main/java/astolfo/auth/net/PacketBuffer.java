package astolfo.auth.net;

import java.nio.ByteBuffer;

public final class PacketBuffer {
   private final OutgoingPacket packet;
   private static String X;

   public PacketBuffer(OutgoingPacket outgoingPacket) {
      this.packet = outgoingPacket;
   }

   public void addChunk(ByteBuffer byteBuffer) {
      this.packet.chunks.add(new PacketChunk(byteBuffer));
   }

   public static void C(String string) {
      X = string;
   }

   public static String D() {
      return X;
   }
}
