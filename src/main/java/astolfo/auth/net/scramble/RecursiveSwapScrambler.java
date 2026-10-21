package astolfo.auth.net.scramble;

import java.nio.ByteBuffer;

public final class RecursiveSwapScrambler implements Scrambler {
   private static void swapRecursive(byte[] bytes, int i, int j) {
      int i1 = j / 2;
      if (i1 > 1) {
         swapRecursive(bytes, i, i1);
         swapRecursive(bytes, i + i1, j - i1);
      } else if (i1 == 1) {
         byte b0 = bytes[i + i1];
         bytes[i + i1] = bytes[i];
         bytes[i] = b0;
      }
   }

   @Override
   public void scramble(ByteBuffer byteBuffer) {
      byte[] abyte = byteBuffer.array();
      swapRecursive(abyte, 0, abyte.length);
   }
}
