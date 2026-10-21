package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BiomeGenPlains extends BiomeGenBase {
   public boolean field_150628_aC;
   private static final String fb = "Sunflower Plains";

   public BiomeGenPlains(int id) {
      super(id);
      this.setTemperatureRainfall(0.8F, 0.4F);
      this.setHeight(BiomeGenBase.height_LowPlains);
      super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityHorse.class, 5, 2, 6));
      super.theBiomeDecorator.treesPerChunk = -999;
      super.theBiomeDecorator.flowersPerChunk = 4;
      super.theBiomeDecorator.grassPerChunk = 10;
   }

   @Override
   public BlockFlower.EnumFlowerType pickRandomFlower(Random rand, BlockPos pos) {
      double d0 = BiomeGenBase.GRASS_COLOR_NOISE.func_151601_a((double)pos.getX() / 200.0, (double)pos.getZ() / 200.0);
      double d1;
      int j = (d1 = d0 - -0.8) == 0.0 ? 0 : (d1 < 0.0 ? -1 : 1);
      if (j < 0) {
         int l = rand.nextInt(4);
         switch (l) {
            case 0:
               return BlockFlower.EnumFlowerType.ORANGE_TULIP;
            case 1:
               return BlockFlower.EnumFlowerType.RED_TULIP;
            case 2:
               return BlockFlower.EnumFlowerType.PINK_TULIP;
            case 3:
            default:
               return BlockFlower.EnumFlowerType.WHITE_TULIP;
         }
      } else {
         int k = rand.nextInt(3);
         if (k > 0) {
            int i = rand.nextInt(3);
            return i == 0 ? BlockFlower.EnumFlowerType.POPPY : (i == 1 ? BlockFlower.EnumFlowerType.HOUSTONIA : BlockFlower.EnumFlowerType.OXEYE_DAISY);
         } else {
            return BlockFlower.EnumFlowerType.DANDELION;
         }
      }
   }

   @Override
   public void decorate(World worldIn, Random rand, BlockPos pos) {
      double d0 = BiomeGenBase.GRASS_COLOR_NOISE.func_151601_a((double)(pos.getX() + 8) / 200.0, (double)(pos.getZ() + 8) / 200.0);
      double d1;
      int i1 = (d1 = d0 - -0.8) == 0.0 ? 0 : (d1 < 0.0 ? -1 : 1);
      if (i1 < 0) {
         super.theBiomeDecorator.flowersPerChunk = 15;
         super.theBiomeDecorator.grassPerChunk = 5;
      } else {
         super.theBiomeDecorator.flowersPerChunk = 4;
         super.theBiomeDecorator.grassPerChunk = 10;
         BiomeGenBase.DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.GRASS);

         for (int i = 0; i < 7; i++) {
            int j = rand.nextInt(16) + 8;
            int k = rand.nextInt(16) + 8;
            int l = rand.nextInt(worldIn.getHeight(pos.add(j, 0, k)).getY() + 32);
            BiomeGenBase.DOUBLE_PLANT_GENERATOR.generate(worldIn, rand, pos.add(j, l, k));
         }
      }

      if (this.field_150628_aC) {
         BiomeGenBase.DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.SUNFLOWER);

         for (int j1 = 0; j1 < 10; j1++) {
            int k1 = rand.nextInt(16) + 8;
            int l1 = rand.nextInt(16) + 8;
            int i2 = rand.nextInt(worldIn.getHeight(pos.add(k1, 0, l1)).getY() + 32);
            BiomeGenBase.DOUBLE_PLANT_GENERATOR.generate(worldIn, rand, pos.add(k1, i2, l1));
         }
      }

      super.decorate(worldIn, rand, pos);
   }

   @Override
   public BiomeGenBase createMutatedBiome(int i) {
      BiomeGenPlains biomegenplains = new BiomeGenPlains(i);
      biomegenplains.setBiomeName(fb);
      biomegenplains.field_150628_aC = true;
      biomegenplains.setColor(9286496);
      biomegenplains.field_150609_ah = 14273354;
      return biomegenplains;
   }
}
