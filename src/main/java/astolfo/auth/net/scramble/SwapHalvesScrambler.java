package astolfo.auth.net.scramble;

import java.nio.ByteBuffer;

public final class SwapHalvesScrambler implements Scrambler {
   @Override
   public void scramble(ByteBuffer byteBuffer) {
      byte[] abyte = byteBuffer.array();
      int i = abyte.length / 2;

      for (int j = 0; j < i; j++) {
         byte b0 = abyte[j + i];
         abyte[j + i] = abyte[j];
         abyte[j] = b0;
      }
   }
}
