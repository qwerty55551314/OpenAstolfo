package net.minecraft.util;

public class IntegerCache {
   private static final int CACHE_SIZE = 4096;
   private static final Integer[] CACHE = createCache(4096);

   private static Integer[] createCache(int i) {
      Integer[] ainteger = new Integer[i];

      for (int i1 = 0; i1 < i; i1++) {
         ainteger[i1] = new Integer(i1);
      }

      return ainteger;
   }

   public static Integer getInteger(int value) {
      return value >= 0 && value < 4096 ? CACHE[value] : new Integer(value);
   }
}
