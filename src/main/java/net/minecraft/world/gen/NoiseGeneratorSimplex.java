package net.minecraft.world.gen;

import java.util.Random;

public class NoiseGeneratorSimplex {
   private static int[][] field_151611_e = new int[][]{
      {1, 1, 0}, {-1, 1, 0}, {1, -1, 0}, {-1, -1, 0}, {1, 0, 1}, {-1, 0, 1}, {1, 0, -1}, {-1, 0, -1}, {0, 1, 1}, {0, -1, 1}, {0, 1, -1}, {0, -1, -1}
   };
   public static final double field_151614_a = Math.sqrt(3.0);
   private int[] field_151608_f = new int[512];
   public double field_151612_b;
   public double field_151613_c;
   public double field_151610_d;
   private static final double field_151609_g = 0.5 * (field_151614_a - 1.0);
   private static final double field_151615_h = (3.0 - field_151614_a) / 6.0;

   public NoiseGeneratorSimplex() {
      this(new Random());
   }

   public NoiseGeneratorSimplex(Random random2) {
      this.field_151612_b = random2.nextDouble() * 256.0;
      this.field_151613_c = random2.nextDouble() * 256.0;
      this.field_151610_d = random2.nextDouble() * 256.0;
      int i = 0;

      while (i < 256) {
         this.field_151608_f[i] = i++;
      }

      for (int l = 0; l < 256; l++) {
         int j = random2.nextInt(256 - l) + l;
         int k = this.field_151608_f[l];
         this.field_151608_f[l] = this.field_151608_f[j];
         this.field_151608_f[j] = k;
         this.field_151608_f[l + 256] = this.field_151608_f[l];
      }
   }

   private static int func_151607_a(double d) {
      return d > 0.0 ? (int)d : (int)d - 1;
   }

   private static double func_151604_a(int[] ints, double d, double e) {
      return (double)ints[0] * d + (double)ints[1] * e;
   }

   public double func_151605_a(double d, double e) {
      double d0 = 0.5 * (field_151614_a - 1.0);
      double d1 = (d + e) * d0;
      int i = func_151607_a(d + d1);
      int j = func_151607_a(e + d1);
      double d2 = (3.0 - field_151614_a) / 6.0;
      double d3 = (double)(i + j) * d2;
      double d4 = (double)i - d3;
      double d5 = (double)j - d3;
      double d6 = d - d4;
      double d7 = e - d5;
      double d21;
      int l1 = (d21 = d6 - d7) == 0.0 ? 0 : (d21 < 0.0 ? -1 : 1);
      int b0;
      int b1;
      if (l1 > 0) {
         b0 = 1;
         b1 = 0;
      } else {
         b0 = 0;
         b1 = 1;
      }

      double d8 = d6 - (double)b0 + d2;
      double d9 = d7 - (double)b1 + d2;
      double d10 = d6 - 1.0 + 2.0 * d2;
      double d11 = d7 - 1.0 + 2.0 * d2;
      int k = i & 0xFF;
      int l = j & 0xFF;
      int i1 = this.field_151608_f[k + this.field_151608_f[l]] % 12;
      int j1 = this.field_151608_f[k + b0 + this.field_151608_f[l + b1]] % 12;
      int k1 = this.field_151608_f[k + 1 + this.field_151608_f[l + 1]] % 12;
      double d12 = 0.5 - d6 * d6 - d7 * d7;
      double d18 = 0.0;
      double d13;
      if (d12 < d18) {
         d13 = 0.0;
      } else {
         d12 *= d12;
         d13 = d12 * d12 * func_151604_a(field_151611_e[i1], d6, d7);
      }

      double d14 = 0.5 - d8 * d8 - d9 * d9;
      double d19 = 0.0;
      double d15;
      if (d14 < d19) {
         d15 = 0.0;
      } else {
         d14 *= d14;
         d15 = d14 * d14 * func_151604_a(field_151611_e[j1], d8, d9);
      }

      double d16 = 0.5 - d10 * d10 - d11 * d11;
      double d20 = 0.0;
      double d17;
      if (d16 < d20) {
         d17 = 0.0;
      } else {
         d16 *= d16;
         d17 = d16 * d16 * func_151604_a(field_151611_e[k1], d10, d11);
      }

      return 70.0 * (d13 + d15 + d17);
   }

   public void func_151606_a(double[] doubles, double d, double e, int i, int j, double d2, double d3, double d4) {
      int i1 = 0;

      for (int j1 = 0; j1 < j; j1++) {
         double d0 = (e + (double)j1) * d3 + this.field_151613_c;

         for (int k = 0; k < i; k++) {
            double d1 = (d + (double)k) * d2 + this.field_151612_b;
            double d21 = (d1 + d0) * field_151609_g;
            int l = func_151607_a(d1 + d21);
            int i1x = func_151607_a(d0 + d21);
            double d31 = (double)(l + i1x) * field_151615_h;
            double d41 = (double)l - d31;
            double d5 = (double)i1x - d31;
            double d6 = d1 - d41;
            double d7 = d0 - d5;
            int b0;
            int b1;
            if (d6 > d7) {
               b0 = 1;
               b1 = 0;
            } else {
               b0 = 0;
               b1 = 1;
            }

            double d8 = d6 - (double)b0 + field_151615_h;
            double d9 = d7 - (double)b1 + field_151615_h;
            double d10 = d6 - 1.0 + 2.0 * field_151615_h;
            double d11 = d7 - 1.0 + 2.0 * field_151615_h;
            int j1x = l & 0xFF;
            int k1 = i1x & 0xFF;
            int l1 = this.field_151608_f[j1x + this.field_151608_f[k1]] % 12;
            int i2 = this.field_151608_f[j1x + b0 + this.field_151608_f[k1 + b1]] % 12;
            int j2 = this.field_151608_f[j1x + 1 + this.field_151608_f[k1 + 1]] % 12;
            double d12 = 0.5 - d6 * d6 - d7 * d7;
            double d13;
            if (d12 < 0.0) {
               d13 = 0.0;
            } else {
               d12 *= d12;
               d13 = d12 * d12 * func_151604_a(field_151611_e[l1], d6, d7);
            }

            double d14 = 0.5 - d8 * d8 - d9 * d9;
            double d15;
            if (d14 < 0.0) {
               d15 = 0.0;
            } else {
               d14 *= d14;
               d15 = d14 * d14 * func_151604_a(field_151611_e[i2], d8, d9);
            }

            double d16 = 0.5 - d10 * d10 - d11 * d11;
            double d17;
            if (d16 < 0.0) {
               d17 = 0.0;
            } else {
               d16 *= d16;
               d17 = d16 * d16 * func_151604_a(field_151611_e[j2], d10, d11);
            }

            int k2 = i1++;
            doubles[k2] += 70.0 * (d13 + d15 + d17) * d4;
         }
      }
   }
}
