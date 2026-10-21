package astolfo.auth.fingerprint;

public final class EmptyFingerprintEntry<T> implements FingerprintEntry<T> {
   private static final Object NULL_VALUE = null;
   private static final String b = "NULL";

   @Override
   public String getName() {
      return b;
   }

   @Override
   public int getHash() {
      return -1;
   }

   @Override
   public T getValue() {
      return (T)NULL_VALUE;
   }

   @Override
   public void refresh() {
   }
}
