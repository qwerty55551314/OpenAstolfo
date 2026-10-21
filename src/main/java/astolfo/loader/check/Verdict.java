package astolfo.loader.check;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public enum Verdict {
   K,
   H,
   N;

   private static final Verdict[] VALUES = new Verdict[]{K, H, N};
   private static int[] M;
   private static final String b = "input";

   public static Optional<Verdict> from(String string) throws NullPointerException {
      Objects.requireNonNull(string, b);
      return Arrays.stream(values()).filter(verdict -> verdict.name().equalsIgnoreCase(string)).findAny();
   }

   static {
      X(new int[2]);
   }

   public static void X(int[] iArray) {
      M = iArray;
   }

   public static int[] F() {
      return M;
   }
}
