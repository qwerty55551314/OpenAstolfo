package astolfo.auth.net.writer;

import astolfo.auth.net.PacketBuffer;
import astolfo.auth.net.PacketWriter;
import astolfo.auth.packet.UserListPacket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public final class UserListWriter implements PacketWriter<UserListPacket> {
   private static boolean E;

   public void write(UserListPacket userListPacket, PacketBuffer packetBuffer) {
      byte[] abyte = userListPacket.users.getBytes(StandardCharsets.UTF_8);
      byte[] abyte1 = userListPacket.extra.getBytes(StandardCharsets.UTF_8);
      ByteBuffer bytebuffer = ByteBuffer.allocate(abyte.length + abyte1.length + 16);
      bytebuffer.putInt(abyte.length);
      bytebuffer.put(abyte);
      bytebuffer.putInt(abyte1.length);
      bytebuffer.put(abyte1);
      bytebuffer.putInt(userListPacket.type.ordinal());
      bytebuffer.putInt(userListPacket.sense ? 1 : 0);
      packetBuffer.addChunk(bytebuffer);
   }

   public static void X(boolean flag) {
      E = flag;
   }

   public static boolean w() {
      return E;
   }

   public static boolean K() {
      return true;
   }
}
