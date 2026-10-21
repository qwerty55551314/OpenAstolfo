package astolfo.account;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class StreamReader {
   private static final StreamReader INSTANCE = new StreamReader();
   private static final int BUFFER_SIZE = 8192;
   private static final int MAX_ARRAY_SIZE = 2147483639;
   private static int c;
   private static final String b = "Required array size too large";

   public byte[] readAllBytes(InputStream inputStream) throws IOException {
      byte[] abyte = new byte[8192];
      int i = abyte.length;
      int j = 0;

      while (true) {
         int k;
         for (; (k = inputStream.read(abyte, j, i - j)) <= 0; abyte = Arrays.copyOf(abyte, i)) {
            if (k < 0) {
               return i == j ? abyte : Arrays.copyOf(abyte, j);
            }

            int l = 2147483639 - i;
            if (i <= l) {
               i <<= 1;
            } else {
               if (i == 2147483639) {
                  throw new OutOfMemoryError(b);
               }

               i = 2147483639;
            }
         }

         j += k;
      }
   }

   public static StreamReader getInstance() {
      return INSTANCE;
   }

   public static void Y(int i) {
      c = i;
   }

   public static int J() {
      return c;
   }

   public static int d() {
      return 30;
   }
}
