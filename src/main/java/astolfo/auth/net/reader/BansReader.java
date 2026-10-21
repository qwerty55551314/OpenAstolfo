package astolfo.auth.net.reader;

import astolfo.auth.net.PacketReader;
import astolfo.auth.packet.BansPacket;
import astolfo.auth.packet.BansPacketType;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class BansReader implements PacketReader<BansPacket> {
   private BansPacket packet;

   @Override
   public void read(ByteBuffer byteBuffer) {
      byte[] bytes = new byte[byteBuffer.getInt()];
      byteBuffer.get(bytes);
      int i = byteBuffer.getInt();
      int j = byteBuffer.getInt();
      this.packet = new BansPacket(BansPacketType.values()[j], i, new String(bytes, StandardCharsets.UTF_8));
   }

   public BansPacket getPacket() {
      return this.packet;
   }
}
