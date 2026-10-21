package astolfo.auth.net.writer;

import astolfo.auth.net.PacketBuffer;
import astolfo.auth.net.PacketWriter;
import astolfo.auth.packet.IrcMessage;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public final class IrcMessageWriter implements PacketWriter<IrcMessage> {
   private static String G;

   public void write(IrcMessage ircMessage, PacketBuffer packetBuffer) {
      byte[] abyte = ircMessage.sender.getBytes(StandardCharsets.UTF_8);
      byte[] abyte1 = ircMessage.message.getBytes(StandardCharsets.UTF_8);
      String s1 = O();
      ByteBuffer bytebuffer = ByteBuffer.allocate(abyte.length + abyte1.length + 12);
      String s = s1;
      bytebuffer.putInt(abyte.length);
      bytebuffer.put(abyte);
      bytebuffer.putInt(abyte1.length);
      bytebuffer.put(abyte1);
      bytebuffer.putInt(ircMessage.type.ordinal());
      packetBuffer.addChunk(bytebuffer);
      if (s == null) {
         int i = 0;
         i++;
      }
   }

   public static String O() {
      return G;
   }
}
