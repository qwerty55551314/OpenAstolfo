package astolfo.auth.fingerprint;

public interface FingerprintRegistry {
   int VALID = 1;
   int INVALID = -1;
   int NOT_VALIDATED = 0;

   void init();

   int countValidatedEntries();

   int getEntryHash(String var1);

   int getCombinedHash();

   void register(String var1, FingerprintEntry<?> var2);

   void registerSupplied(String var1, FingerprintEntrySupplier var2);

   int validate(String var1);

   int validateAll();

   boolean remove(String var1);

   void invalidateAll();
}
