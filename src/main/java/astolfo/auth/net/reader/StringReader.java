package astolfo.auth.net.reader;

import astolfo.auth.net.PacketReader;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public final class StringReader implements PacketReader<String> {
   private String packet;

   @Override
   public void read(ByteBuffer byteBuffer) {
      byte[] bytes = new byte[byteBuffer.getInt()];
      byteBuffer.get(bytes);
      this.packet = new String(bytes, StandardCharsets.UTF_8);
   }

   public String getPacket() {
      return this.packet;
   }
}
