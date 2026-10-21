package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;

public class GenLayerBiomeEdge extends GenLayer {
   public GenLayerBiomeEdge(long l, GenLayer genLayer) {
      super(l);
      super.parent = genLayer;
   }

   @Override
   public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
      int[] aint = super.parent.getInts(areaX - 1, areaY - 1, areaWidth + 2, areaHeight + 2);
      int[] aint1 = IntCache.getIntCache(areaWidth * areaHeight);

      for (int i = 0; i < areaHeight; i++) {
         for (int j = 0; j < areaWidth; j++) {
            this.initChunkSeed((long)(j + areaX), (long)(i + areaY));
            int k = aint[j + 1 + (i + 1) * (areaWidth + 2)];
            if (!this.replaceBiomeEdgeIfNecessary(aint, aint1, j, i, areaWidth, k, BiomeGenBase.extremeHills.biomeID, BiomeGenBase.extremeHillsEdge.biomeID)
               && !this.replaceBiomeEdge(aint, aint1, j, i, areaWidth, k, BiomeGenBase.mesaPlateau_F.biomeID, BiomeGenBase.mesa.biomeID)
               && !this.replaceBiomeEdge(aint, aint1, j, i, areaWidth, k, BiomeGenBase.mesaPlateau.biomeID, BiomeGenBase.mesa.biomeID)
               && !this.replaceBiomeEdge(aint, aint1, j, i, areaWidth, k, BiomeGenBase.megaTaiga.biomeID, BiomeGenBase.taiga.biomeID)) {
               if (k == BiomeGenBase.desert.biomeID) {
                  int l = aint[j + 1 + (i + 1 - 1) * (areaWidth + 2)];
                  int i1 = aint[j + 1 + 1 + (i + 1) * (areaWidth + 2)];
                  int j1 = aint[j + 1 - 1 + (i + 1) * (areaWidth + 2)];
                  int k1 = aint[j + 1 + (i + 1 + 1) * (areaWidth + 2)];
                  if (l != BiomeGenBase.icePlains.biomeID
                     && i1 != BiomeGenBase.icePlains.biomeID
                     && j1 != BiomeGenBase.icePlains.biomeID
                     && k1 != BiomeGenBase.icePlains.biomeID) {
                     aint1[j + i * areaWidth] = k;
                  } else {
                     aint1[j + i * areaWidth] = BiomeGenBase.extremeHillsPlus.biomeID;
                  }
               } else if (k == BiomeGenBase.swampland.biomeID) {
                  int l1 = aint[j + 1 + (i + 1 - 1) * (areaWidth + 2)];
                  int i2 = aint[j + 1 + 1 + (i + 1) * (areaWidth + 2)];
                  int j2 = aint[j + 1 - 1 + (i + 1) * (areaWidth + 2)];
                  int k2 = aint[j + 1 + (i + 1 + 1) * (areaWidth + 2)];
                  if (l1 == BiomeGenBase.desert.biomeID
                     || i2 == BiomeGenBase.desert.biomeID
                     || j2 == BiomeGenBase.desert.biomeID
                     || k2 == BiomeGenBase.desert.biomeID
                     || l1 == BiomeGenBase.coldTaiga.biomeID
                     || i2 == BiomeGenBase.coldTaiga.biomeID
                     || j2 == BiomeGenBase.coldTaiga.biomeID
                     || k2 == BiomeGenBase.coldTaiga.biomeID
                     || l1 == BiomeGenBase.icePlains.biomeID
                     || i2 == BiomeGenBase.icePlains.biomeID
                     || j2 == BiomeGenBase.icePlains.biomeID
                     || k2 == BiomeGenBase.icePlains.biomeID) {
                     aint1[j + i * areaWidth] = BiomeGenBase.plains.biomeID;
                  } else if (l1 != BiomeGenBase.jungle.biomeID
                     && k2 != BiomeGenBase.jungle.biomeID
                     && i2 != BiomeGenBase.jungle.biomeID
                     && j2 != BiomeGenBase.jungle.biomeID) {
                     aint1[j + i * areaWidth] = k;
                  } else {
                     aint1[j + i * areaWidth] = BiomeGenBase.jungleEdge.biomeID;
                  }
               } else {
                  aint1[j + i * areaWidth] = k;
               }
            }
         }
      }

      return aint1;
   }

   private boolean replaceBiomeEdgeIfNecessary(int[] ints, int[] ints2, int i, int j, int k, int l, int m, int n) {
      boolean flag = biomesEqualOrMesaPlateau(l, m);
      if (!flag) {
         return false;
      } else {
         int i1 = ints[i + 1 + (j + 1 - 1) * (k + 2)];
         int j1 = ints[i + 1 + 1 + (j + 1) * (k + 2)];
         int k1 = ints[i + 1 - 1 + (j + 1) * (k + 2)];
         int l1 = ints[i + 1 + (j + 1 + 1) * (k + 2)];
         boolean flag1 = this.canBiomesBeNeighbors(i1, m);
         if (flag1) {
            boolean flag2 = this.canBiomesBeNeighbors(j1, m);
            if (flag2) {
               boolean flag3 = this.canBiomesBeNeighbors(k1, m);
               if (flag3 && this.canBiomesBeNeighbors(l1, m)) {
                  ints2[i + j * k] = l;
                  return true;
               }
            }
         }

         ints2[i + j * k] = n;
         return true;
      }
   }

   private boolean replaceBiomeEdge(int[] ints, int[] ints2, int i, int j, int k, int l, int m, int n) {
      if (l != m) {
         return false;
      } else {
         int i1 = ints[i + 1 + (j + 1 - 1) * (k + 2)];
         int j1 = ints[i + 1 + 1 + (j + 1) * (k + 2)];
         int k1 = ints[i + 1 - 1 + (j + 1) * (k + 2)];
         int l1 = ints[i + 1 + (j + 1 + 1) * (k + 2)];
         boolean flag = biomesEqualOrMesaPlateau(i1, m);
         if (flag) {
            boolean flag1 = biomesEqualOrMesaPlateau(j1, m);
            if (flag1) {
               boolean flag2 = biomesEqualOrMesaPlateau(k1, m);
               if (flag2 && biomesEqualOrMesaPlateau(l1, m)) {
                  ints2[i + j * k] = l;
                  return true;
               }
            }
         }

         ints2[i + j * k] = n;
         return true;
      }
   }

   private boolean canBiomesBeNeighbors(int i, int j) {
      boolean flag = biomesEqualOrMesaPlateau(i, j);
      if (flag) {
         return true;
      } else {
         BiomeGenBase biomegenbase = BiomeGenBase.getBiome(i);
         BiomeGenBase biomegenbase1 = BiomeGenBase.getBiome(j);
         if (biomegenbase != null && biomegenbase1 != null) {
            BiomeGenBase.TempCategory biomegenbase$tempcategory = biomegenbase.getTempCategory();
            BiomeGenBase.TempCategory biomegenbase$tempcategory1 = biomegenbase1.getTempCategory();
            if (biomegenbase$tempcategory != biomegenbase$tempcategory1) {
               BiomeGenBase.TempCategory biomegenbase$tempcategory2 = BiomeGenBase.TempCategory.MEDIUM;
               if (biomegenbase$tempcategory != biomegenbase$tempcategory2 && biomegenbase$tempcategory1 != BiomeGenBase.TempCategory.MEDIUM) {
                  return false;
               }
            }

            return true;
         } else {
            return false;
         }
      }
   }
}
