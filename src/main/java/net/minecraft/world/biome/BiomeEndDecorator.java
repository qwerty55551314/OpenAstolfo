package net.minecraft.world.biome;

import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenSpikes;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeEndDecorator extends BiomeDecorator {
   public WorldGenerator spikeGen = new WorldGenSpikes(Blocks.end_stone);

   @Override
   public void genDecorations(BiomeGenBase biomeGenBaseIn) {
      this.generateOres();
      int k = super.randomGenerator.nextInt(5);
      if (k == 0) {
         int i = super.randomGenerator.nextInt(16) + 8;
         int j = super.randomGenerator.nextInt(16) + 8;
         this.spikeGen.generate(super.currentWorld, super.randomGenerator, super.currentWorld.getTopSolidOrLiquidBlock(super.field_180294_c.add(i, 0, j)));
      }

      int l = super.field_180294_c.getX();
      if (l == 0 && super.field_180294_c.getZ() == 0) {
         EntityDragon entitydragon = new EntityDragon(super.currentWorld);
         entitydragon.setLocationAndAngles(0.0, 128.0, 0.0, super.randomGenerator.nextFloat() * 360.0F, 0.0F);
         super.currentWorld.spawnEntityInWorld(entitydragon);
      }
   }
}
