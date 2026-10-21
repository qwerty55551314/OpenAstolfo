package astolfo.auth.net.writer;

import astolfo.auth.net.PacketBuffer;
import astolfo.auth.net.PacketWriter;
import java.nio.ByteBuffer;

public class IntWriter implements PacketWriter<Integer> {
   private static int[] A;

   public void write(Integer integer, PacketBuffer packetBuffer) {
      ByteBuffer bytebuffer = ByteBuffer.allocate(4);
      bytebuffer.putInt(integer);
      packetBuffer.addChunk(bytebuffer);
   }

   public static void Z(int[] iArray) {
      A = iArray;
   }

   public static int[] m() {
      return A;
   }

   static {
      Z(new int[3]);
   }
}
