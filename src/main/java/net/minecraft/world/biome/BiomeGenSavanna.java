package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenSavannaTree;

public class BiomeGenSavanna extends BiomeGenBase {
   private static final WorldGenSavannaTree field_150627_aC = new WorldGenSavannaTree(false);

   public BiomeGenSavanna(int id) {
      super(id);
      super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityHorse.class, 1, 2, 6));
      super.theBiomeDecorator.treesPerChunk = 1;
      super.theBiomeDecorator.flowersPerChunk = 4;
      super.theBiomeDecorator.grassPerChunk = 20;
   }

   @Override
   public WorldGenAbstractTree genBigTreeChance(Random rand) {
      return (WorldGenAbstractTree)(rand.nextInt(5) > 0 ? field_150627_aC : super.worldGeneratorTrees);
   }

   @Override
   public BiomeGenBase createMutatedBiome(int i) {
      BiomeGenSavanna.Mutated biomegensavanna$mutated = new BiomeGenSavanna.Mutated(i, this);
      biomegensavanna$mutated.temperature = (super.temperature + 1.0F) * 0.5F;
      biomegensavanna$mutated.minHeight = super.minHeight * 0.5F + 0.3F;
      biomegensavanna$mutated.maxHeight = super.maxHeight * 0.5F + 1.2F;
      return biomegensavanna$mutated;
   }

   @Override
   public void decorate(World worldIn, Random rand, BlockPos pos) {
      BiomeGenBase.DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.GRASS);

      for (int i = 0; i < 7; i++) {
         int j = rand.nextInt(16) + 8;
         int k = rand.nextInt(16) + 8;
         int l = rand.nextInt(worldIn.getHeight(pos.add(j, 0, k)).getY() + 32);
         BiomeGenBase.DOUBLE_PLANT_GENERATOR.generate(worldIn, rand, pos.add(j, l, k));
      }

      super.decorate(worldIn, rand, pos);
   }

   public static class Mutated extends BiomeGenMutated {
      public Mutated(int i, BiomeGenBase biomeGenBase) {
         super(i, biomeGenBase);
         super.theBiomeDecorator.treesPerChunk = 2;
         super.theBiomeDecorator.flowersPerChunk = 2;
         super.theBiomeDecorator.grassPerChunk = 5;
      }

      @Override
      public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
         super.topBlock = Blocks.grass.getDefaultState();
         super.fillerBlock = Blocks.dirt.getDefaultState();
         if (noiseVal > 1.75) {
            super.topBlock = Blocks.stone.getDefaultState();
            super.fillerBlock = Blocks.stone.getDefaultState();
         } else if (noiseVal > -0.5) {
            super.topBlock = Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT);
         }

         this.generateBiomeTerrain(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
      }

      @Override
      public void decorate(World worldIn, Random rand, BlockPos pos) {
         super.theBiomeDecorator.decorate(worldIn, rand, this, pos);
      }
   }
}
