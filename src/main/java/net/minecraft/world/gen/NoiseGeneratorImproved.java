package net.minecraft.world.gen;

import java.util.Random;

public class NoiseGeneratorImproved extends NoiseGenerator {
   private int[] permutations = new int[512];
   public double xCoord;
   public double yCoord;
   public double zCoord;
   private static final double[] field_152381_e = new double[]{1.0, -1.0, 1.0, -1.0, 1.0, -1.0, 1.0, -1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, -1.0, 0.0};
   private static final double[] field_152382_f = new double[]{1.0, 1.0, -1.0, -1.0, 0.0, 0.0, 0.0, 0.0, 1.0, -1.0, 1.0, -1.0, 1.0, -1.0, 1.0, -1.0};
   private static final double[] field_152383_g = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 1.0, -1.0, -1.0, 1.0, 1.0, -1.0, -1.0, 0.0, 1.0, 0.0, -1.0};
   private static final double[] field_152384_h = new double[]{1.0, -1.0, 1.0, -1.0, 1.0, -1.0, 1.0, -1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, -1.0, 0.0};
   private static final double[] field_152385_i = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 1.0, -1.0, -1.0, 1.0, 1.0, -1.0, -1.0, 0.0, 1.0, 0.0, -1.0};

   public NoiseGeneratorImproved() {
      this(new Random());
   }

   public NoiseGeneratorImproved(Random random2) {
      this.xCoord = random2.nextDouble() * 256.0;
      this.yCoord = random2.nextDouble() * 256.0;
      this.zCoord = random2.nextDouble() * 256.0;
      int i = 0;

      while (i < 256) {
         this.permutations[i] = i++;
      }

      for (int l = 0; l < 256; l++) {
         int j = random2.nextInt(256 - l) + l;
         int k = this.permutations[l];
         this.permutations[l] = this.permutations[j];
         this.permutations[j] = k;
         this.permutations[l + 256] = this.permutations[l];
      }
   }

   public final double lerp(double d, double e, double d2) {
      return e + d * (d2 - e);
   }

   public final double func_76309_a(int i, double d, double e) {
      int i1 = i & 15;
      return field_152384_h[i1] * d + field_152385_i[i1] * e;
   }

   public final double grad(int i, double d, double e, double d2) {
      int i1 = i & 15;
      return field_152381_e[i1] * d + field_152382_f[i1] * e + field_152383_g[i1] * d2;
   }

   public void populateNoiseArray(
      double[] noiseArray,
      double xOffset,
      double yOffset,
      double zOffset,
      int xSize,
      int ySize,
      int zSize,
      double xScale,
      double yScale,
      double zScale,
      double noiseScale
   ) {
      if (ySize == 1) {
         int k1 = 0;
         double d2 = 1.0 / noiseScale;

         for (int l1 = 0; l1 < xSize; l1++) {
            double d4 = xOffset + (double)l1 * xScale + this.xCoord;
            int i2 = (int)d4;
            if (d4 < (double)i2) {
               i2--;
            }

            int j2 = i2 & 0xFF;
            d4 -= (double)i2;
            double d5 = d4 * d4 * d4 * (d4 * (d4 * 6.0 - 15.0) + 10.0);

            for (int k2 = 0; k2 < zSize; k2++) {
               double d6 = zOffset + (double)k2 * zScale + this.zCoord;
               int l2 = (int)d6;
               if (d6 < (double)l2) {
                  l2--;
               }

               int i3 = l2 & 0xFF;
               d6 -= (double)l2;
               double d7 = d6 * d6 * d6 * (d6 * (d6 * 6.0 - 15.0) + 10.0);
               int i = this.permutations[j2] + 0;
               int j = this.permutations[i] + i3;
               int k = this.permutations[j2 + 1] + 0;
               int l = this.permutations[k] + i3;
               double d0 = this.lerp(d5, this.func_76309_a(this.permutations[j], d4, d6), this.grad(this.permutations[l], d4 - 1.0, 0.0, d6));
               double d1 = this.lerp(d5, this.grad(this.permutations[j + 1], d4, 0.0, d6 - 1.0), this.grad(this.permutations[l + 1], d4 - 1.0, 0.0, d6 - 1.0));
               double d8 = this.lerp(d7, d0, d1);
               int j3 = k1++;
               noiseArray[j3] += d8 * d2;
            }
         }
      } else {
         int k5 = 0;
         double d16 = 1.0 / noiseScale;
         int l5 = -1;
         double d3 = 0.0;
         double d17 = 0.0;
         double d18 = 0.0;
         double d19 = 0.0;

         for (int i7 = 0; i7 < xSize; i7++) {
            double d20 = xOffset + (double)i7 * xScale + this.xCoord;
            int j7 = (int)d20;
            double d22;
            int i5 = (d22 = d20 - (double)j7) == 0.0 ? 0 : (d22 < 0.0 ? -1 : 1);
            if (i5 < 0) {
               j7--;
            }

            int k7 = j7 & 0xFF;
            d20 -= (double)j7;
            double d21 = d20 * d20 * d20 * (d20 * (d20 * 6.0 - 15.0) + 10.0);

            for (int l7 = 0; l7 < zSize; l7++) {
               double d9 = zOffset + (double)l7 * zScale + this.zCoord;
               int k3 = (int)d9;
               double d23;
               int j5 = (d23 = d9 - (double)k3) == 0.0 ? 0 : (d23 < 0.0 ? -1 : 1);
               if (j5 < 0) {
                  k3--;
               }

               int l3 = k3 & 0xFF;
               d9 -= (double)k3;
               double d10 = d9 * d9 * d9 * (d9 * (d9 * 6.0 - 15.0) + 10.0);

               for (int i4 = 0; i4 < ySize; i4++) {
                  double d11 = yOffset + (double)i4 * yScale + this.yCoord;
                  int j4 = (int)d11;
                  if (d11 < (double)j4) {
                     j4--;
                  }

                  int k4 = j4 & 0xFF;
                  d11 -= (double)j4;
                  double d12 = d11 * d11 * d11 * (d11 * (d11 * 6.0 - 15.0) + 10.0);
                  if (i4 == 0 || k4 != l5) {
                     l5 = k4;
                     int i6 = this.permutations[k7] + k4;
                     int i1 = this.permutations[i6] + l3;
                     int j6 = this.permutations[i6 + 1] + l3;
                     int j1 = this.permutations[k7 + 1] + k4;
                     int k6 = this.permutations[j1] + l3;
                     int l6 = this.permutations[j1 + 1] + l3;
                     d3 = this.lerp(d21, this.grad(this.permutations[i1], d20, d11, d9), this.grad(this.permutations[k6], d20 - 1.0, d11, d9));
                     d17 = this.lerp(d21, this.grad(this.permutations[j6], d20, d11 - 1.0, d9), this.grad(this.permutations[l6], d20 - 1.0, d11 - 1.0, d9));
                     d18 = this.lerp(
                        d21, this.grad(this.permutations[i1 + 1], d20, d11, d9 - 1.0), this.grad(this.permutations[k6 + 1], d20 - 1.0, d11, d9 - 1.0)
                     );
                     d19 = this.lerp(
                        d21,
                        this.grad(this.permutations[j6 + 1], d20, d11 - 1.0, d9 - 1.0),
                        this.grad(this.permutations[l6 + 1], d20 - 1.0, d11 - 1.0, d9 - 1.0)
                     );
                  }

                  double d13 = this.lerp(d12, d3, d17);
                  double d14 = this.lerp(d12, d18, d19);
                  double d15 = this.lerp(d10, d13, d14);
                  int l4 = k5++;
                  noiseArray[l4] += d15 * d16;
               }
            }
         }
      }
   }
}
