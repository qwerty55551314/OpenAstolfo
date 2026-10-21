package astolfo.auth.fingerprint;

import java.util.UUID;

public final class FingerprintUuid {
   public static final UUID NIL_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");
   private static final long Q = Long.MIN_VALUE;
   private final FingerprintRegistry registry;
   private UUID forcedUuid;
   private static int[] H;

   public FingerprintUuid(FingerprintRegistry fingerprintRegistry) {
      this.registry = fingerprintRegistry;
      int i = this.registry.validateAll();
      if (i != this.registry.countValidatedEntries()) {
         this.forcedUuid = NIL_UUID;
         this.registry.invalidateAll();
      }
   }

   public UUID getUuid() {
      if (this.forcedUuid != null) {
         return this.forcedUuid;
      } else {
         long i = (long)this.registry.getCombinedHash();
         long j = Long.MIN_VALUE | i << 31 << 11;
         return UUID.nameUUIDFromBytes(Long.toBinaryString(j).getBytes());
      }
   }

   public static void R(int[] iArray) {
      H = iArray;
   }

   public static int[] D() {
      return H;
   }
}
