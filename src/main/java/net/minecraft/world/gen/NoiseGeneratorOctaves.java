package net.minecraft.world.gen;

import java.util.Random;
import net.minecraft.util.MathHelper;

public class NoiseGeneratorOctaves extends NoiseGenerator {
   private NoiseGeneratorImproved[] generatorCollection;
   private int octaves;
   private static final long c = -6920340848240492534L;

   public NoiseGeneratorOctaves(Random seed, int octavesIn) {
      this.octaves = octavesIn;
      this.generatorCollection = new NoiseGeneratorImproved[octavesIn];
      if (false) {
         int j = 0;
         if (j < octavesIn) {
            this.generatorCollection[j] = new NoiseGeneratorImproved(seed);
            j++;
         }
      } else {
         for (int i = 0; i < octavesIn; i++) {
            this.generatorCollection[i] = new NoiseGeneratorImproved(seed);
         }
      }
   }

   public double[] generateNoiseOctaves(
      double[] noiseArray, int xOffset, int yOffset, int zOffset, int xSize, int ySize, int zSize, double xScale, double yScale, double zScale
   ) {
      if (noiseArray == null) {
         noiseArray = new double[xSize * ySize * zSize];
      } else {
         for (int i = 0; i < noiseArray.length; i++) {
            noiseArray[i] = 0.0;
         }
      }

      double d3 = 1.0;

      for (int j = 0; j < this.octaves; j++) {
         double d0 = (double)xOffset * d3 * xScale;
         double d1 = (double)yOffset * d3 * yScale;
         double d2 = (double)zOffset * d3 * zScale;
         long k = MathHelper.floor_double_long(d0);
         long l = MathHelper.floor_double_long(d2);
         d0 -= (double)k;
         d2 -= (double)l;
         k %= 16777216L;
         l %= 16777216L;
         d0 += (double)k;
         d2 += (double)l;
         this.generatorCollection[j].populateNoiseArray(noiseArray, d0, d1, d2, xSize, ySize, zSize, xScale * d3, yScale * d3, zScale * d3, d3);
         d3 /= 2.0;
      }

      return noiseArray;
   }

   public double[] generateNoiseOctaves(double[] noiseArray, int xOffset, int zOffset, int xSize, int zSize, double xScale, double zScale, double d) {
      return this.generateNoiseOctaves(noiseArray, xOffset, (int)c, zOffset, xSize, 1, zSize, xScale, 1.0, zScale);
   }
}
