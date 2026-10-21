package astolfo.auth.net.scramble;

import java.nio.ByteBuffer;

public final class FirstPairSwapScrambler implements Scrambler {
   private static void swapFirstPair(byte[] bytes, int i, int j) {
      int i1 = j / 2;
      if (i1 > 1) {
         swapFirstPair(bytes, i, i1);
      } else if (i1 == 1) {
         byte b0 = bytes[i + i1];
         bytes[i + i1] = bytes[i];
         bytes[i] = b0;
      }
   }

   @Override
   public void scramble(ByteBuffer byteBuffer) {
      byte[] abyte = byteBuffer.array();
      swapFirstPair(abyte, 0, abyte.length);
   }
}
