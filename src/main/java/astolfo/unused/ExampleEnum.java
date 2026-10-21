package astolfo.unused;

public enum ExampleEnum {
   TEST_1,
   TEST_2;

   private static final ExampleEnum[] VALUES = new ExampleEnum[]{TEST_1, TEST_2};
   private static int v;

   static {
      u(97);
   }

   public static void u(int i) {
      v = i;
   }

   public static int V() {
      return v;
   }

   public static int d() {
      return 0;
   }
}
