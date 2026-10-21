package astolfo.util;

import java.nio.charset.StandardCharsets;

public class StringChecksum {
   private static final long b = 2433289489343316095L;

   public static int checksum(String string) {
      return sumChecksum(string.getBytes(StandardCharsets.UTF_8));
   }

   private static int sumChecksum(byte[] bytes) {
      int b0 = 0;

      for (byte b1 : bytes) {
         b0 += b1;
      }

      return b0 % (int)b;
   }

   public static byte[] xorEncode(int i, String string) {
      byte[] abyte = string.getBytes(StandardCharsets.UTF_8);

      for (int i1 = 0; i1 < abyte.length; i1++) {
         abyte[i1] = (byte)(abyte[i1] ^ i);
      }

      return abyte;
   }
}
