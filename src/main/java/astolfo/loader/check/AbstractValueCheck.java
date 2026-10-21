package astolfo.loader.check;

public abstract class AbstractValueCheck implements ValueCheckView {
   private final Object value;
   private static boolean Z;

   public AbstractValueCheck(Object object) {
      this.value = object;
   }

   public Object getValue() {
      return this.value;
   }

   public static void M(boolean flag) {
      Z = flag;
   }

   public static boolean M() {
      return Z;
   }

   public static boolean V() {
      return false;
   }

   static {
      M(true);
   }
}
