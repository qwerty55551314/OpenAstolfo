package astolfo.util;

import java.math.BigInteger;

public class FnvHash {
   private static final BigInteger FNV32_OFFSET_BASIS = new BigInteger("811c9dc5", 16);
   private static final BigInteger FNV64_OFFSET_BASIS = new BigInteger("cbf29ce484222325", 16);
   private static final BigInteger FNV32_PRIME = new BigInteger("01000193", 16);
   private static final BigInteger FNV64_PRIME = new BigInteger("100000001b3", 16);
   private static final BigInteger MOD_32 = new BigInteger("2").pow(32);
   private static final BigInteger MOD_64 = new BigInteger("2").pow(64);
   private static int Y;

   public BigInteger fnv1Hash32(byte[] bytes) {
      BigInteger biginteger = FNV32_OFFSET_BASIS;

      for (byte b0 : bytes) {
         biginteger = biginteger.multiply(FNV32_PRIME).mod(MOD_32);
         biginteger = biginteger.xor(BigInteger.valueOf((long)(b0 & 255)));
      }

      return biginteger;
   }

   public BigInteger fnv1Hash64(byte[] bytes) {
      BigInteger biginteger = FNV64_OFFSET_BASIS;

      for (byte b0 : bytes) {
         biginteger = biginteger.multiply(FNV64_PRIME).mod(MOD_64);
         biginteger = biginteger.xor(BigInteger.valueOf((long)(b0 & 255)));
      }

      return biginteger;
   }

   public BigInteger fnv1aHash32(byte[] bytes) {
      BigInteger biginteger = FNV32_OFFSET_BASIS;

      for (byte b0 : bytes) {
         biginteger = biginteger.xor(BigInteger.valueOf((long)(b0 & 255)));
         biginteger = biginteger.multiply(FNV32_PRIME).mod(MOD_32);
      }

      return biginteger;
   }

   public BigInteger fnv1aHash64(byte[] bytes) {
      BigInteger biginteger = FNV64_OFFSET_BASIS;
      int i = 0;

      for (int j = bytes.length; i < j; i++) {
         byte b0 = bytes[i];
         biginteger = biginteger.xor(BigInteger.valueOf((long)(b0 & 255)));
         biginteger = biginteger.multiply(FNV64_PRIME).mod(MOD_64);
      }

      return biginteger;
   }

   public static int p() {
      return 0;
   }
}
