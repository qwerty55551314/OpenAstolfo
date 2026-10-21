package astolfo.auth.net.reader;

import astolfo.auth.net.PacketReader;
import astolfo.auth.packet.NickPacket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class NickReader implements PacketReader<NickPacket> {
   private NickPacket packet;

   @Override
   public void read(ByteBuffer byteBuffer) {
      byte[] bytes = new byte[byteBuffer.getInt()];
      byteBuffer.get(bytes);
      byte[] bytes2 = new byte[byteBuffer.getInt()];
      byteBuffer.get(bytes2);
      this.packet = new NickPacket(new String(bytes, StandardCharsets.UTF_8), new String(bytes2, StandardCharsets.UTF_8));
   }

   public NickPacket getPacket() {
      return this.packet;
   }
}
