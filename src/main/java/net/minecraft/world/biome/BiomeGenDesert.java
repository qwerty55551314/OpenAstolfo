package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenDesertWells;

public class BiomeGenDesert extends BiomeGenBase {
   public BiomeGenDesert(int id) {
      super(id);
      super.spawnableCreatureList.clear();
      super.topBlock = Blocks.sand.getDefaultState();
      super.fillerBlock = Blocks.sand.getDefaultState();
      super.theBiomeDecorator.treesPerChunk = -999;
      super.theBiomeDecorator.deadBushPerChunk = 2;
      super.theBiomeDecorator.reedsPerChunk = 50;
      super.theBiomeDecorator.cactiPerChunk = 10;
      super.spawnableCreatureList.clear();
   }

   @Override
   public void decorate(World worldIn, Random rand, BlockPos pos) {
      super.decorate(worldIn, rand, pos);
      if (rand.nextInt(1000) == 0) {
         int i = rand.nextInt(16) + 8;
         int j = rand.nextInt(16) + 8;
         BlockPos blockpos = worldIn.getHeight(pos.add(i, 0, j)).up();
         new WorldGenDesertWells().generate(worldIn, rand, blockpos);
      }
   }
}
