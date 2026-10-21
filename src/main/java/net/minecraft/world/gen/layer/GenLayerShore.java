package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenJungle;
import net.minecraft.world.biome.BiomeGenMesa;

public class GenLayerShore extends GenLayer {
   public GenLayerShore(long l, GenLayer genLayer) {
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
            BiomeGenBase biomegenbase = BiomeGenBase.getBiome(k);
            if (k == BiomeGenBase.mushroomIsland.biomeID) {
               int l = aint[j + 1 + (i + 1 - 1) * (areaWidth + 2)];
               int i1 = aint[j + 1 + 1 + (i + 1) * (areaWidth + 2)];
               int j1 = aint[j + 1 - 1 + (i + 1) * (areaWidth + 2)];
               int k1 = aint[j + 1 + (i + 1 + 1) * (areaWidth + 2)];
               int l1 = BiomeGenBase.ocean.biomeID;
               if (l != l1) {
                  int i2 = BiomeGenBase.ocean.biomeID;
                  if (i1 != i2) {
                     int j2 = BiomeGenBase.ocean.biomeID;
                     if (j1 != j2 && k1 != BiomeGenBase.ocean.biomeID) {
                        aint1[j + i * areaWidth] = k;
                        continue;
                     }
                  }
               }

               aint1[j + i * areaWidth] = BiomeGenBase.mushroomIslandShore.biomeID;
            } else if (biomegenbase != null && biomegenbase.getBiomeClass() == BiomeGenJungle.class) {
               int k3 = aint[j + 1 + (i + 1 - 1) * (areaWidth + 2)];
               int j4 = aint[j + 1 + 1 + (i + 1) * (areaWidth + 2)];
               int i5 = aint[j + 1 - 1 + (i + 1) * (areaWidth + 2)];
               int l5 = aint[j + 1 + (i + 1 + 1) * (areaWidth + 2)];
               boolean flag = this.I(k3);
               if (flag) {
                  boolean flag1 = this.I(j4);
                  if (flag1) {
                     boolean flag2 = this.I(i5);
                     if (flag2) {
                        boolean flag3 = this.I(l5);
                        if (flag3) {
                           boolean flag4 = isBiomeOceanic(k3);
                           if (!flag4) {
                              boolean flag5 = isBiomeOceanic(j4);
                              if (!flag5) {
                                 boolean flag6 = isBiomeOceanic(i5);
                                 if (!flag6 && !isBiomeOceanic(l5)) {
                                    aint1[j + i * areaWidth] = k;
                                    continue;
                                 }
                              }
                           }

                           aint1[j + i * areaWidth] = BiomeGenBase.beach.biomeID;
                           continue;
                        }
                     }
                  }
               }

               aint1[j + i * areaWidth] = BiomeGenBase.jungleEdge.biomeID;
            } else {
               int k2 = BiomeGenBase.extremeHills.biomeID;
               if (k != k2) {
                  int l2 = BiomeGenBase.extremeHillsPlus.biomeID;
                  if (k != l2 && k != BiomeGenBase.extremeHillsEdge.biomeID) {
                     if (biomegenbase != null) {
                        boolean flag7 = biomegenbase.isSnowyBiome();
                        if (flag7) {
                           this.func_151632_a(aint, aint1, j, i, areaWidth, k, BiomeGenBase.coldBeach.biomeID);
                           continue;
                        }
                     }

                     if (k == BiomeGenBase.mesa.biomeID || k == BiomeGenBase.mesaPlateau_F.biomeID) {
                        int j3 = aint[j + 1 + (i + 1 - 1) * (areaWidth + 2)];
                        int i4 = aint[j + 1 + 1 + (i + 1) * (areaWidth + 2)];
                        int l4 = aint[j + 1 - 1 + (i + 1) * (areaWidth + 2)];
                        int k5 = aint[j + 1 + (i + 1 + 1) * (areaWidth + 2)];
                        boolean flag8 = isBiomeOceanic(j3);
                        if (!flag8) {
                           boolean flag9 = isBiomeOceanic(i4);
                           if (!flag9) {
                              boolean flag10 = isBiomeOceanic(l4);
                              if (!flag10) {
                                 boolean flag11 = isBiomeOceanic(k5);
                                 if (!flag11) {
                                    boolean flag12 = this.p(j3);
                                    if (flag12) {
                                       boolean flag13 = this.p(i4);
                                       if (flag13) {
                                          boolean flag14 = this.p(l4);
                                          if (flag14 && this.p(k5)) {
                                             aint1[j + i * areaWidth] = k;
                                             continue;
                                          }
                                       }
                                    }

                                    aint1[j + i * areaWidth] = BiomeGenBase.desert.biomeID;
                                    continue;
                                 }
                              }
                           }
                        }

                        aint1[j + i * areaWidth] = k;
                     } else if (k != BiomeGenBase.ocean.biomeID
                        && k != BiomeGenBase.deepOcean.biomeID
                        && k != BiomeGenBase.river.biomeID
                        && k != BiomeGenBase.swampland.biomeID) {
                        int i3 = aint[j + 1 + (i + 1 - 1) * (areaWidth + 2)];
                        int l3 = aint[j + 1 + 1 + (i + 1) * (areaWidth + 2)];
                        int k4 = aint[j + 1 - 1 + (i + 1) * (areaWidth + 2)];
                        int j5 = aint[j + 1 + (i + 1 + 1) * (areaWidth + 2)];
                        if (!isBiomeOceanic(i3) && !isBiomeOceanic(l3) && !isBiomeOceanic(k4) && !isBiomeOceanic(j5)) {
                           aint1[j + i * areaWidth] = k;
                        } else {
                           aint1[j + i * areaWidth] = BiomeGenBase.beach.biomeID;
                        }
                     } else {
                        aint1[j + i * areaWidth] = k;
                     }
                     continue;
                  }
               }

               this.func_151632_a(aint, aint1, j, i, areaWidth, k, BiomeGenBase.stoneBeach.biomeID);
            }
         }
      }

      return aint1;
   }

   private void func_151632_a(int[] ints, int[] ints2, int i, int j, int k, int l, int m) {
      boolean flag = isBiomeOceanic(l);
      if (flag) {
         ints2[i + j * k] = l;
      } else {
         int i1 = ints[i + 1 + (j + 1 - 1) * (k + 2)];
         int j1 = ints[i + 1 + 1 + (j + 1) * (k + 2)];
         int k1 = ints[i + 1 - 1 + (j + 1) * (k + 2)];
         int l1 = ints[i + 1 + (j + 1 + 1) * (k + 2)];
         boolean flag1 = isBiomeOceanic(i1);
         if (!flag1) {
            boolean flag2 = isBiomeOceanic(j1);
            if (!flag2) {
               boolean flag3 = isBiomeOceanic(k1);
               if (!flag3 && !isBiomeOceanic(l1)) {
                  ints2[i + j * k] = l;
                  return;
               }
            }
         }

         ints2[i + j * k] = m;
      }
   }

   private boolean I(int i) {
      return BiomeGenBase.getBiome(i) != null && BiomeGenBase.getBiome(i).getBiomeClass() == BiomeGenJungle.class
         ? true
         : i == BiomeGenBase.jungleEdge.biomeID
            || i == BiomeGenBase.jungle.biomeID
            || i == BiomeGenBase.jungleHills.biomeID
            || i == BiomeGenBase.forest.biomeID
            || i == BiomeGenBase.taiga.biomeID
            || isBiomeOceanic(i);
   }

   private boolean p(int i) {
      return BiomeGenBase.getBiome(i) instanceof BiomeGenMesa;
   }
}
