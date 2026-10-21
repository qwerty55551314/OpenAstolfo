package astolfo.auth.net.scramble;

import java.nio.ByteBuffer;

public final class SwapPairsScrambler implements Scrambler {
   private static void swap(byte[] bytes, int i, int j) {
      byte b0 = bytes[i];
      bytes[i] = bytes[j];
      bytes[j] = b0;
   }

   @Override
   public void scramble(ByteBuffer byteBuffer) {
      byte[] abyte = byteBuffer.array();
      int i = 0;
      int j = abyte.length;
      int k = j - 1;

      for (int l = j - 2; i != k; i += i == l ? 1 : 2) {
         swap(abyte, i, i + 1);
      }
   }
}
