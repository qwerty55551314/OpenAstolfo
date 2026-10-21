package net.minecraft.src;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;

public class RandomMobsRule {
   private ResourceLocation baseResLoc = null;
   private int[] skins = null;
   private ResourceLocation[] resourceLocations = null;
   private int[] weights = null;
   private BiomeGenBase[] biomes = null;
   private RangeListInt heights = null;
   public int[] sumWeights = null;
   public int sumAllWeights = 1;

   public RandomMobsRule(ResourceLocation resourceLocation, int[] ints, int[] ints2, BiomeGenBase[] biomeGenBases, RangeListInt rangeListInt) {
      this.baseResLoc = resourceLocation;
      this.skins = ints;
      this.weights = ints2;
      this.biomes = biomeGenBases;
      this.heights = rangeListInt;
   }

   public boolean isValid(String path) {
      this.resourceLocations = new ResourceLocation[this.skins.length];
      ResourceLocation resourcelocation = RandomMobs.getMcpatcherLocation(this.baseResLoc);
      if (resourcelocation == null) {
         Config.warn("Invalid path: " + this.baseResLoc.getResourcePath());
         return false;
      } else {
         for (int i = 0; i < this.resourceLocations.length; i++) {
            int j = this.skins[i];
            if (j <= 1) {
               this.resourceLocations[i] = this.baseResLoc;
            } else {
               ResourceLocation resourcelocation1 = RandomMobs.getLocationIndexed(resourcelocation, j);
               if (resourcelocation1 == null) {
                  Config.warn("Invalid path: " + this.baseResLoc.getResourcePath());
                  return false;
               }

               if (!Config.hasResource(resourcelocation1)) {
                  Config.warn("Texture not found: " + resourcelocation1.getResourcePath());
                  return false;
               }

               this.resourceLocations[i] = resourcelocation1;
            }
         }

         if (this.weights != null) {
            if (this.weights.length > this.resourceLocations.length) {
               Config.warn("More weights defined than skins, trimming weights: " + path);
               int[] aint = new int[this.resourceLocations.length];
               System.arraycopy(this.weights, 0, aint, 0, aint.length);
               this.weights = aint;
            }

            if (this.weights.length < this.resourceLocations.length) {
               Config.warn("Less weights defined than skins, expanding weights: " + path);
               int[] aint1 = new int[this.resourceLocations.length];
               System.arraycopy(this.weights, 0, aint1, 0, this.weights.length);
               int l = MathUtils.getAverage(this.weights);

               for (int j1 = this.weights.length; j1 < aint1.length; j1++) {
                  aint1[j1] = l;
               }

               this.weights = aint1;
            }

            this.sumWeights = new int[this.weights.length];
            int k = 0;

            for (int i1 = 0; i1 < this.weights.length; i1++) {
               if (this.weights[i1] < 0) {
                  Config.warn("Invalid weight: " + this.weights[i1]);
                  return false;
               }

               k += this.weights[i1];
               this.sumWeights[i1] = k;
            }

            this.sumAllWeights = k;
            if (this.sumAllWeights <= 0) {
               Config.warn("Invalid sum of all weights: " + k);
               this.sumAllWeights = 1;
            }
         }

         return true;
      }
   }

   public boolean matches(EntityLiving el) {
      return !Matches.biome(el.spawnBiome, this.biomes)
         ? false
         : (this.heights != null && el.spawnPosition != null ? this.heights.isInRange(el.spawnPosition.getY()) : true);
   }

   public ResourceLocation getTextureLocation(ResourceLocation loc, int randomId) {
      int i = 0;
      if (this.weights == null) {
         i = randomId % this.resourceLocations.length;
      } else {
         int j = randomId % this.sumAllWeights;

         for (int k = 0; k < this.sumWeights.length; k++) {
            if (this.sumWeights[k] > j) {
               i = k;
               break;
            }
         }
      }

      return this.resourceLocations[i];
   }
}
