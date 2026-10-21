package astolfo.auth.fingerprint;

public final class NonEmptyValidator implements FingerprintValidator<String> {
   @Override
   public boolean test(FingerprintEntry fingerprintEntry) {
      if (fingerprintEntry instanceof EmptyFingerprintEntry) {
         return false;
      } else {
         Object object = fingerprintEntry.getValue();
         if (object instanceof String) {
            String s = (String)object;
            return !s.isEmpty();
         } else {
            return object != null;
         }
      }
   }
}
