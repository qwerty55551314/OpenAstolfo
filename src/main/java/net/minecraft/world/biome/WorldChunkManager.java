package net.minecraft.world.biome;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class WorldChunkManager {
   private GenLayer genBiomes;
   private GenLayer biomeIndexLayer;
   private BiomeCache biomeCache = new BiomeCache(this);
   private List<BiomeGenBase> biomesToSpawnIn;
   private String generatorOptions = "";
   private static boolean c;

   public WorldChunkManager() {
      this.biomesToSpawnIn = Lists.newArrayList();
      this.biomesToSpawnIn.add(BiomeGenBase.forest);
      this.biomesToSpawnIn.add(BiomeGenBase.plains);
      this.biomesToSpawnIn.add(BiomeGenBase.taiga);
      this.biomesToSpawnIn.add(BiomeGenBase.taigaHills);
      this.biomesToSpawnIn.add(BiomeGenBase.forestHills);
      this.biomesToSpawnIn.add(BiomeGenBase.jungle);
      this.biomesToSpawnIn.add(BiomeGenBase.jungleHills);
   }

   public WorldChunkManager(long seed, WorldType worldTypeIn, String options) {
      this();
      this.generatorOptions = options;
      GenLayer[] agenlayer = GenLayer.initializeAllBiomeGenerators(seed, worldTypeIn, options);
      this.genBiomes = agenlayer[0];
      this.biomeIndexLayer = agenlayer[1];
   }

   public WorldChunkManager(World worldIn) {
      this(worldIn.getSeed(), worldIn.getWorldInfo().getTerrainType(), worldIn.getWorldInfo().getGeneratorOptions());
   }

   public List<BiomeGenBase> getBiomesToSpawnIn() {
      return this.biomesToSpawnIn;
   }

   public BiomeGenBase getBiomeGenerator(BlockPos pos) {
      return this.getBiomeGenerator(pos, (BiomeGenBase)null);
   }

   public BiomeGenBase getBiomeGenerator(BlockPos pos, BiomeGenBase biomeGenBaseIn) {
      return this.biomeCache.func_180284_a(pos.getX(), pos.getZ(), biomeGenBaseIn);
   }

   public float[] getRainfall(float[] listToReuse, int x, int z, int width, int length) {
      IntCache.resetIntCache();
      if (listToReuse == null || listToReuse.length < width * length) {
         listToReuse = new float[width * length];
      }

      int[] aint = this.biomeIndexLayer.getInts(x, z, width, length);

      for (int i = 0; i < width * length; i++) {
         try {
            float f = (float)BiomeGenBase.getBiomeFromBiomeList(aint[i], BiomeGenBase.field_180279_ad).getIntRainfall() / 65536.0F;
            if (f > 1.0F) {
               f = 1.0F;
            }

            listToReuse[i] = f;
         } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("DownfallBlock");
            crashreportcategory.addCrashSection("biome id", i);
            crashreportcategory.addCrashSection("downfalls[] size", listToReuse.length);
            crashreportcategory.addCrashSection("x", x);
            crashreportcategory.addCrashSection("z", z);
            crashreportcategory.addCrashSection("w", width);
            crashreportcategory.addCrashSection("h", length);
            throw new ReportedException(crashreport);
         }
      }

      return listToReuse;
   }

   public float getTemperatureAtHeight(float f, int i) {
      return f;
   }

   public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] biomes, int x, int z, int width, int height) {
      IntCache.resetIntCache();
      if (biomes == null || biomes.length < width * height) {
         biomes = new BiomeGenBase[width * height];
      }

      int[] aint = this.genBiomes.getInts(x, z, width, height);

      try {
         for (int i = 0; i < width * height; i++) {
            biomes[i] = BiomeGenBase.getBiomeFromBiomeList(aint[i], BiomeGenBase.field_180279_ad);
         }

         return biomes;
      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
         CrashReportCategory crashreportcategory = crashreport.makeCategory("RawBiomeBlock");
         crashreportcategory.addCrashSection("biomes[] size", biomes.length);
         crashreportcategory.addCrashSection("x", x);
         crashreportcategory.addCrashSection("z", z);
         crashreportcategory.addCrashSection("w", width);
         crashreportcategory.addCrashSection("h", height);
         throw new ReportedException(crashreport);
      }
   }

   public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] oldBiomeList, int x, int z, int width, int depth) {
      return this.getBiomeGenAt(oldBiomeList, x, z, width, depth, true);
   }

   public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] listToReuse, int x, int z, int width, int length, boolean cacheFlag) {
      IntCache.resetIntCache();
      if (listToReuse == null || listToReuse.length < width * length) {
         listToReuse = new BiomeGenBase[width * length];
      }

      if (cacheFlag && width == 16 && length == 16) {
         int j = x & 15;
         if (j == 0 && (z & 15) == 0) {
            BiomeGenBase[] abiomegenbase = this.biomeCache.getCachedBiomes(x, z);
            System.arraycopy(abiomegenbase, 0, listToReuse, 0, width * length);
            return listToReuse;
         }
      }

      int[] aint = this.biomeIndexLayer.getInts(x, z, width, length);

      for (int i = 0; i < width * length; i++) {
         listToReuse[i] = BiomeGenBase.getBiomeFromBiomeList(aint[i], BiomeGenBase.field_180279_ad);
      }

      return listToReuse;
   }

   public boolean areBiomesViable(int i, int j, int k, List<BiomeGenBase> list) {
      IntCache.resetIntCache();
      int i1 = i - k >> 2;
      int j1 = j - k >> 2;
      int k1 = i + k >> 2;
      int l = j + k >> 2;
      int i1x = k1 - i1 + 1;
      int j1x = l - j1 + 1;
      int[] aint = this.genBiomes.getInts(i1, j1, i1x, j1x);

      try {
         for (int k1x = 0; k1x < i1x * j1x; k1x++) {
            BiomeGenBase biomegenbase = BiomeGenBase.getBiome(aint[k1x]);
            if (!list.contains(biomegenbase)) {
               return false;
            }
         }

         return true;
      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
         CrashReportCategory crashreportcategory = crashreport.makeCategory("Layer");
         crashreportcategory.addCrashSection("Layer", this.genBiomes.toString());
         crashreportcategory.addCrashSection("x", i);
         crashreportcategory.addCrashSection("z", j);
         crashreportcategory.addCrashSection("radius", k);
         crashreportcategory.addCrashSection("allowed", list);
         throw new ReportedException(crashreport);
      }
   }

   public BlockPos findBiomePosition(int x, int z, int range, List<BiomeGenBase> biomes, Random randomIn) {
      IntCache.resetIntCache();
      int i = x - range >> 2;
      int j = z - range >> 2;
      int k = x + range >> 2;
      int l = z + range >> 2;
      int i1 = k - i + 1;
      int j1 = l - j + 1;
      int[] aint = this.genBiomes.getInts(i, j, i1, j1);
      BlockPos blockpos = null;
      int k1 = 0;

      for (int l1 = 0; l1 < i1 * j1; l1++) {
         int i2 = i + l1 % i1 << 2;
         int j2 = j + l1 / i1 << 2;
         BiomeGenBase biomegenbase = BiomeGenBase.getBiome(aint[l1]);
         if (biomes.contains(biomegenbase) && (blockpos == null || randomIn.nextInt(k1 + 1) == 0)) {
            blockpos = new BlockPos(i2, 0, j2);
            k1++;
         }
      }

      return blockpos;
   }

   public void cleanupCache() {
      this.biomeCache.cleanupCache();
   }

   public static void I(boolean flag) {
      c = flag;
   }

   public static boolean i() {
      return c;
   }

   public static boolean A() {
      return true;
   }

   static {
      I(false);
   }
}
