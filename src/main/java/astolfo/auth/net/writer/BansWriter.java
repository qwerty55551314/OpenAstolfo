package astolfo.auth.net.writer;

import astolfo.auth.net.PacketBuffer;
import astolfo.auth.net.PacketWriter;
import astolfo.auth.packet.BansPacket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class BansWriter implements PacketWriter<BansPacket> {
   private static String Z;

   public void write(BansPacket bansPacket, PacketBuffer packetBuffer) {
      byte[] abyte = bansPacket.bans.getBytes(StandardCharsets.UTF_8);
      ByteBuffer bytebuffer = ByteBuffer.allocate(abyte.length + 12);
      bytebuffer.putInt(abyte.length);
      bytebuffer.put(abyte);
      bytebuffer.putInt(bansPacket.mode);
      bytebuffer.putInt(bansPacket.type.ordinal());
      packetBuffer.addChunk(bytebuffer);
   }

   public static void I(String string) {
      Z = string;
   }

   public static String g() {
      return Z;
   }
}
