package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;

public class GenLayerRiverMix extends GenLayer {
   private GenLayer biomePatternGeneratorChain;
   private GenLayer riverPatternGeneratorChain;
   private static final long g = 6570475552497467647L;

   public GenLayerRiverMix(long l, GenLayer genLayer, GenLayer genLayer2) {
      super(l);
      this.biomePatternGeneratorChain = genLayer;
      this.riverPatternGeneratorChain = genLayer2;
   }

   @Override
   public void initWorldGenSeed(long seed) {
      this.biomePatternGeneratorChain.initWorldGenSeed(seed);
      this.riverPatternGeneratorChain.initWorldGenSeed(seed);
      super.initWorldGenSeed(seed);
   }

   @Override
   public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
      int[] aint = this.biomePatternGeneratorChain.getInts(areaX, areaY, areaWidth, areaHeight);
      int[] aint1 = this.riverPatternGeneratorChain.getInts(areaX, areaY, areaWidth, areaHeight);
      int[] aint2 = IntCache.getIntCache(areaWidth * areaHeight);

      for (int i = 0; i < areaWidth * areaHeight; i++) {
         if (aint[i] == BiomeGenBase.ocean.biomeID || aint[i] == BiomeGenBase.deepOcean.biomeID) {
            aint2[i] = aint[i];
         } else if (aint1[i] == BiomeGenBase.river.biomeID) {
            if (aint[i] == BiomeGenBase.icePlains.biomeID) {
               aint2[i] = BiomeGenBase.frozenRiver.biomeID;
            } else if (aint[i] != BiomeGenBase.mushroomIsland.biomeID && aint[i] != BiomeGenBase.mushroomIslandShore.biomeID) {
               aint2[i] = aint1[i] & (int)g;
            } else {
               aint2[i] = BiomeGenBase.mushroomIslandShore.biomeID;
            }
         } else {
            aint2[i] = aint[i];
         }
      }

      return aint2;
   }
}
