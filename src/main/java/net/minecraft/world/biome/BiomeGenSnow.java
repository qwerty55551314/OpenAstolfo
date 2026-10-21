package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenIcePath;
import net.minecraft.world.gen.feature.WorldGenIceSpike;
import net.minecraft.world.gen.feature.WorldGenTaiga2;

public class BiomeGenSnow extends BiomeGenBase {
   private boolean field_150615_aC;
   private WorldGenIceSpike field_150616_aD = new WorldGenIceSpike();
   private WorldGenIcePath field_150617_aE = new WorldGenIcePath(4);
   private static final String fb = " Spikes";

   public BiomeGenSnow(int id, boolean bl) {
      super(id);
      this.field_150615_aC = bl;
      if (bl) {
         super.topBlock = Blocks.snow.getDefaultState();
      }

      super.spawnableCreatureList.clear();
   }

   @Override
   public void decorate(World worldIn, Random rand, BlockPos pos) {
      if (this.field_150615_aC) {
         for (int i = 0; i < 3; i++) {
            int j = rand.nextInt(16) + 8;
            int k = rand.nextInt(16) + 8;
            this.field_150616_aD.generate(worldIn, rand, worldIn.getHeight(pos.add(j, 0, k)));
         }

         for (int l = 0; l < 2; l++) {
            int i1 = rand.nextInt(16) + 8;
            int j1 = rand.nextInt(16) + 8;
            this.field_150617_aE.generate(worldIn, rand, worldIn.getHeight(pos.add(i1, 0, j1)));
         }
      }

      super.decorate(worldIn, rand, pos);
   }

   @Override
   public WorldGenAbstractTree genBigTreeChance(Random rand) {
      return new WorldGenTaiga2(false);
   }

   @Override
   public BiomeGenBase createMutatedBiome(int i) {
      BiomeGenBase biomegenbase = new BiomeGenSnow(i, true)
         .func_150557_a(13828095, true)
         .setBiomeName(super.biomeName + fb)
         .setEnableSnow()
         .setTemperatureRainfall(0.0F, 0.5F)
         .setHeight(new BiomeGenBase.Height(super.minHeight + 0.1F, super.maxHeight + 0.1F));
      biomegenbase.minHeight = super.minHeight + 0.3F;
      biomegenbase.maxHeight = super.maxHeight + 0.4F;
      return biomegenbase;
   }
}
