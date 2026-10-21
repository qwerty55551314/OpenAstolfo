package net.minecraft.world.gen.layer;

import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.ChunkProviderSettings;

public class GenLayerBiome extends GenLayer {
   private BiomeGenBase[] field_151623_c = new BiomeGenBase[]{
      BiomeGenBase.desert, BiomeGenBase.desert, BiomeGenBase.desert, BiomeGenBase.savanna, BiomeGenBase.savanna, BiomeGenBase.plains
   };
   private BiomeGenBase[] field_151621_d = new BiomeGenBase[]{
      BiomeGenBase.forest, BiomeGenBase.roofedForest, BiomeGenBase.extremeHills, BiomeGenBase.plains, BiomeGenBase.birchForest, BiomeGenBase.swampland
   };
   private BiomeGenBase[] field_151622_e = new BiomeGenBase[]{BiomeGenBase.forest, BiomeGenBase.extremeHills, BiomeGenBase.taiga, BiomeGenBase.plains};
   private BiomeGenBase[] field_151620_f = new BiomeGenBase[]{BiomeGenBase.icePlains, BiomeGenBase.icePlains, BiomeGenBase.icePlains, BiomeGenBase.coldTaiga};
   private final ChunkProviderSettings field_175973_g;

   public GenLayerBiome(long l, GenLayer genLayer, WorldType worldType, String string) {
      super(l);
      super.parent = genLayer;
      if (worldType == WorldType.DEFAULT_1_1) {
         this.field_151623_c = new BiomeGenBase[]{
            BiomeGenBase.desert, BiomeGenBase.forest, BiomeGenBase.extremeHills, BiomeGenBase.swampland, BiomeGenBase.plains, BiomeGenBase.taiga
         };
         this.field_175973_g = null;
      } else if (worldType == WorldType.CUSTOMIZED) {
         this.field_175973_g = ChunkProviderSettings.Factory.jsonToFactory(string).func_177864_b();
      } else {
         this.field_175973_g = null;
      }
   }

   @Override
   public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
      int[] aint = super.parent.getInts(areaX, areaY, areaWidth, areaHeight);
      int[] aint1 = IntCache.getIntCache(areaWidth * areaHeight);

      for (int i = 0; i < areaHeight; i++) {
         for (int j = 0; j < areaWidth; j++) {
            this.initChunkSeed((long)(j + areaX), (long)(i + areaY));
            int k = aint[j + i * areaWidth];
            int l = (k & 3840) >> 8;
            int i1 = k & -3841;
            if (this.field_175973_g != null && this.field_175973_g.fixedBiome >= 0) {
               aint1[j + i * areaWidth] = this.field_175973_g.fixedBiome;
            } else {
               boolean flag = isBiomeOceanic(i1);
               if (flag) {
                  aint1[j + i * areaWidth] = i1;
               } else {
                  int j1 = BiomeGenBase.mushroomIsland.biomeID;
                  if (i1 == j1) {
                     aint1[j + i * areaWidth] = i1;
                  } else if (i1 == 1) {
                     if (l > 0) {
                        if (this.nextInt(3) == 0) {
                           aint1[j + i * areaWidth] = BiomeGenBase.mesaPlateau.biomeID;
                        } else {
                           aint1[j + i * areaWidth] = BiomeGenBase.mesaPlateau_F.biomeID;
                        }
                     } else {
                        aint1[j + i * areaWidth] = this.field_151623_c[this.nextInt(this.field_151623_c.length)].biomeID;
                     }
                  } else if (i1 == 2) {
                     if (l > 0) {
                        aint1[j + i * areaWidth] = BiomeGenBase.jungle.biomeID;
                     } else {
                        aint1[j + i * areaWidth] = this.field_151621_d[this.nextInt(this.field_151621_d.length)].biomeID;
                     }
                  } else if (i1 == 3) {
                     if (l > 0) {
                        aint1[j + i * areaWidth] = BiomeGenBase.megaTaiga.biomeID;
                     } else {
                        aint1[j + i * areaWidth] = this.field_151622_e[this.nextInt(this.field_151622_e.length)].biomeID;
                     }
                  } else if (i1 == 4) {
                     aint1[j + i * areaWidth] = this.field_151620_f[this.nextInt(this.field_151620_f.length)].biomeID;
                  } else {
                     aint1[j + i * areaWidth] = BiomeGenBase.mushroomIsland.biomeID;
                  }
               }
            }
         }
      }

      return aint1;
   }
}
