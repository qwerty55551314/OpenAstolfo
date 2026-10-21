package astolfo.loader.check;

public final class MeasureException extends RuntimeException {
   private static String y;

   public MeasureException(String string) {
      super(string);
   }

   public MeasureException(String string, Throwable throwable) {
      super(string, throwable);
   }

   public static void Y(String string) {
      y = string;
   }

   public static String H() {
      return y;
   }

   static {
      Y("kQwA2");
   }
}
