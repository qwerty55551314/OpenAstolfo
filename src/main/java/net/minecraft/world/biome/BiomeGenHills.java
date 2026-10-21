package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeGenHills extends BiomeGenBase {
   private WorldGenerator theWorldGenerator = new WorldGenMinable(
      Blocks.monster_egg.getDefaultState().withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.STONE), 9
   );
   private WorldGenTaiga2 field_150634_aD = new WorldGenTaiga2(false);
   private int field_150635_aE = 0;
   private int field_150636_aF = 1;
   private int field_150637_aG = 2;
   private int field_150638_aH = this.field_150635_aE;
   private static final String fb = " M";

   public BiomeGenHills(int id, boolean bl) {
      super(id);
      if (bl) {
         super.theBiomeDecorator.treesPerChunk = 3;
         this.field_150638_aH = this.field_150636_aF;
      }
   }

   @Override
   public WorldGenAbstractTree genBigTreeChance(Random rand) {
      return (WorldGenAbstractTree)(rand.nextInt(3) > 0 ? this.field_150634_aD : super.genBigTreeChance(rand));
   }

   @Override
   public void decorate(World worldIn, Random rand, BlockPos pos) {
      super.decorate(worldIn, rand, pos);
      int i = 3 + rand.nextInt(6);

      for (int j = 0; j < i; j++) {
         int k = rand.nextInt(16);
         int l = rand.nextInt(28) + 4;
         int i1 = rand.nextInt(16);
         BlockPos blockpos = pos.add(k, l, i1);
         if (worldIn.getBlockState(blockpos).getBlock() == Blocks.stone) {
            worldIn.setBlockState(blockpos, Blocks.emerald_ore.getDefaultState(), 2);
         }
      }

      for (int j1 = 0; j1 < 7; j1++) {
         int k1 = rand.nextInt(16);
         int l1 = rand.nextInt(64);
         int i2 = rand.nextInt(16);
         this.theWorldGenerator.generate(worldIn, rand, pos.add(k1, l1, i2));
      }
   }

   @Override
   public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
      label22: {
         super.topBlock = Blocks.grass.getDefaultState();
         super.fillerBlock = Blocks.dirt.getDefaultState();
         double d0;
         int i = (d0 = noiseVal - -1.0) == 0.0 ? 0 : (d0 < 0.0 ? -1 : 1);
         if (i < 0 || noiseVal > 2.0) {
            int j = this.field_150638_aH;
            if (j == this.field_150637_aG) {
               super.topBlock = Blocks.gravel.getDefaultState();
               super.fillerBlock = Blocks.gravel.getDefaultState();
               break label22;
            }
         }

         double d1;
         int k = (d1 = noiseVal - 1.0) == 0.0 ? 0 : (d1 < 0.0 ? -1 : 1);
         if (k > 0 && this.field_150638_aH != this.field_150636_aF) {
            super.topBlock = Blocks.stone.getDefaultState();
            super.fillerBlock = Blocks.stone.getDefaultState();
         }
      }

      this.generateBiomeTerrain(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
   }

   private BiomeGenHills mutateHills(BiomeGenBase biomeGenBase) {
      this.field_150638_aH = this.field_150637_aG;
      this.func_150557_a(biomeGenBase.color, true);
      this.setBiomeName(biomeGenBase.biomeName + fb);
      this.setHeight(new BiomeGenBase.Height(biomeGenBase.minHeight, biomeGenBase.maxHeight));
      this.setTemperatureRainfall(biomeGenBase.temperature, biomeGenBase.rainfall);
      return this;
   }

   @Override
   public BiomeGenBase createMutatedBiome(int i) {
      return new BiomeGenHills(i, false).mutateHills(this);
   }
}
