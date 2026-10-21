package com.github.andrewoma.dexx.collection;

public class Pair<C1, C2> {
   private final C1 component1;
   private final C2 component2;
   private static boolean H;
   private static final String b = ", ";
   private static final long c = -2846369909045198817L;

   public Pair(C1 component1, C2 component2) {
      this.component1 = (C1)component1;
      this.component2 = (C2)component2;
   }

   public C1 component1() {
      return this.component1;
   }

   public C2 component2() {
      return this.component2;
   }

   @Override
   public String toString() {
      return "(" + this.component1 + b + this.component2 + ")";
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else {
         if (o != null) {
            Class oclass = this.getClass();
            if (oclass == o.getClass()) {
               Pair pair = (Pair)o;
               Object object = this.component1;
               if (object != null) {
                  if (!this.component1.equals(pair.component1)) {
                     return false;
                  }
               } else {
                  Object object1 = pair.component1;
                  if (object1 != null) {
                     return false;
                  }
               }

               Object object2 = this.component2;
               if (object2 != null ? this.component2.equals(pair.component2) : pair.component2 == null) {
                  return true;
               }

               return false;
            }
         }

         return false;
      }
   }

   @Override
   public int hashCode() {
      Object object = this.component1;
      int i = object != null ? this.component1.hashCode() : 0;
      int k = (int)c * i;
      Object object1 = this.component2;
      int j = k;
      return j + (object1 != null ? this.component2.hashCode() : 0);
   }

   public static void M(boolean flag) {
      H = flag;
   }

   public static boolean A() {
      return H;
   }

   public static boolean t() {
      return true;
   }
}
