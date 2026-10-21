package oshi.util.tuples;

import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class Quintet<A, B, C, D, E> {
   private final A a;
   private final B b;
   private final C c;
   private final D d;
   private final E e;

   public Quintet(A a, B b, C c, D d, E e) {
      this.a = (A)a;
      this.b = (B)b;
      this.c = (C)c;
      this.d = (D)d;
      this.e = (E)e;
   }

   public final A y() {
      return this.a;
   }

   public final B getB() {
      return this.b;
   }

   public final C getC() {
      return this.c;
   }

   public final D getD() {
      return this.d;
   }

   public final E getE() {
      return this.e;
   }
}
