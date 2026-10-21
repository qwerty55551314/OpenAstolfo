package net.minecraft.world.gen;

import java.util.Random;

public class NoiseGeneratorPerlin extends NoiseGenerator {
   private NoiseGeneratorSimplex[] field_151603_a;
   private int field_151602_b;

   public NoiseGeneratorPerlin(Random random2, int i) {
      this.field_151602_b = i;
      this.field_151603_a = new NoiseGeneratorSimplex[i];

      for (int i1 = 0; i1 < i; i1++) {
         this.field_151603_a[i1] = new NoiseGeneratorSimplex(random2);
      }
   }

   public double func_151601_a(double d, double e) {
      double d0 = 0.0;
      double d1 = 1.0;

      for (int i = 0; i < this.field_151602_b; i++) {
         d0 += this.field_151603_a[i].func_151605_a(d * d1, e * d1) / d1;
         d1 /= 2.0;
      }

      return d0;
   }

   public double[] func_151599_a(double[] doubles, double d, double e, int i, int j, double d2, double d3, double d4) {
      return this.func_151600_a(doubles, d, e, i, j, d2, d3, d4, 0.5);
   }

   public double[] func_151600_a(double[] doubles, double d, double e, int i, int j, double d2, double d3, double d4, double d5) {
      if (doubles != null && doubles.length >= i * j) {
         for (int i1 = 0; i1 < doubles.length; i1++) {
            doubles[i1] = 0.0;
         }
      } else {
         doubles = new double[i * j];
      }

      double d1 = 1.0;
      double d0 = 1.0;

      for (int j1 = 0; j1 < this.field_151602_b; j1++) {
         this.field_151603_a[j1].func_151606_a(doubles, d, e, i, j, d2 * d0 * d1, d3 * d0 * d1, 0.55 / d1);
         d0 *= d4;
         d1 *= d5;
      }

      return doubles;
   }
}
