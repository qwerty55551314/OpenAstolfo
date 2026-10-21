package oshi.util.tuples;

import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class Pair<A, B> {
   private final A a;
   private final B b;
   private static int h;

   public Pair(A a, B b) {
      this.a = (A)a;
      this.b = (B)b;
   }

   public final A getA() {
      return this.a;
   }

   public final B getB() {
      return this.b;
   }

   public static int p() {
      return 0;
   }
}
