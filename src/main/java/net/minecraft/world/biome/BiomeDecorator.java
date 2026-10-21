package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkProviderSettings;
import net.minecraft.world.gen.GeneratorBushFeature;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import net.minecraft.world.gen.feature.WorldGenCactus;
import net.minecraft.world.gen.feature.WorldGenClay;
import net.minecraft.world.gen.feature.WorldGenDeadBush;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenLiquids;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenPumpkin;
import net.minecraft.world.gen.feature.WorldGenReed;
import net.minecraft.world.gen.feature.WorldGenSand;
import net.minecraft.world.gen.feature.WorldGenWaterlily;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeDecorator {
   public World currentWorld;
   public Random randomGenerator;
   public BlockPos field_180294_c;
   public ChunkProviderSettings chunkProviderSettings;
   public WorldGenerator clayGen = new WorldGenClay(4);
   public WorldGenerator sandGen = new WorldGenSand(Blocks.sand, 7);
   public WorldGenerator gravelAsSandGen = new WorldGenSand(Blocks.gravel, 6);
   public WorldGenerator dirtGen;
   public WorldGenerator gravelGen;
   public WorldGenerator graniteGen;
   public WorldGenerator dioriteGen;
   public WorldGenerator andesiteGen;
   public WorldGenerator coalGen;
   public WorldGenerator ironGen;
   public WorldGenerator goldGen;
   public WorldGenerator redstoneGen;
   public WorldGenerator diamondGen;
   public WorldGenerator lapisGen;
   public WorldGenFlowers yellowFlowerGen = new WorldGenFlowers(Blocks.yellow_flower, BlockFlower.EnumFlowerType.DANDELION);
   public WorldGenerator mushroomBrownGen = new GeneratorBushFeature(Blocks.brown_mushroom);
   public WorldGenerator mushroomRedGen = new GeneratorBushFeature(Blocks.red_mushroom);
   public WorldGenerator bigMushroomGen = new WorldGenBigMushroom();
   public WorldGenerator reedGen = new WorldGenReed();
   public WorldGenerator cactusGen = new WorldGenCactus();
   public WorldGenerator waterlilyGen = new WorldGenWaterlily();
   public int waterlilyPerChunk;
   public int treesPerChunk;
   public int flowersPerChunk = 2;
   public int grassPerChunk = 1;
   public int deadBushPerChunk;
   public int mushroomsPerChunk;
   public int reedsPerChunk;
   public int cactiPerChunk;
   public int sandPerChunk = 1;
   public int sandPerChunk2 = 3;
   public int clayPerChunk = 1;
   public int bigMushroomsPerChunk;
   public boolean generateLakes = true;
   private static final String i = "Already decorating";

   public void decorate(World worldIn, Random randomIn, BiomeGenBase biome, BlockPos blockPos) {
      if (this.currentWorld != null) {
         throw new RuntimeException(i);
      } else {
         this.currentWorld = worldIn;
         String s = worldIn.getWorldInfo().getGeneratorOptions();
         if (s != null) {
            this.chunkProviderSettings = ChunkProviderSettings.Factory.jsonToFactory(s).func_177864_b();
         } else {
            this.chunkProviderSettings = ChunkProviderSettings.Factory.jsonToFactory("").func_177864_b();
         }

         this.randomGenerator = randomIn;
         this.field_180294_c = blockPos;
         this.dirtGen = new WorldGenMinable(Blocks.dirt.getDefaultState(), this.chunkProviderSettings.dirtSize);
         this.gravelGen = new WorldGenMinable(Blocks.gravel.getDefaultState(), this.chunkProviderSettings.gravelSize);
         this.graniteGen = new WorldGenMinable(
            Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE), this.chunkProviderSettings.graniteSize
         );
         this.dioriteGen = new WorldGenMinable(
            Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE), this.chunkProviderSettings.dioriteSize
         );
         this.andesiteGen = new WorldGenMinable(
            Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE), this.chunkProviderSettings.andesiteSize
         );
         this.coalGen = new WorldGenMinable(Blocks.coal_ore.getDefaultState(), this.chunkProviderSettings.coalSize);
         this.ironGen = new WorldGenMinable(Blocks.iron_ore.getDefaultState(), this.chunkProviderSettings.ironSize);
         this.goldGen = new WorldGenMinable(Blocks.gold_ore.getDefaultState(), this.chunkProviderSettings.goldSize);
         this.redstoneGen = new WorldGenMinable(Blocks.redstone_ore.getDefaultState(), this.chunkProviderSettings.redstoneSize);
         this.diamondGen = new WorldGenMinable(Blocks.diamond_ore.getDefaultState(), this.chunkProviderSettings.diamondSize);
         this.lapisGen = new WorldGenMinable(Blocks.lapis_ore.getDefaultState(), this.chunkProviderSettings.lapisSize);
         this.genDecorations(biome);
         this.currentWorld = null;
         this.randomGenerator = null;
      }
   }

   public void genDecorations(BiomeGenBase biomeGenBaseIn) {
      this.generateOres();

      for (int ix = 0; ix < this.sandPerChunk2; ix++) {
         int j = this.randomGenerator.nextInt(16) + 8;
         int k = this.randomGenerator.nextInt(16) + 8;
         this.sandGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(this.field_180294_c.add(j, 0, k)));
      }

      for (int j2 = 0; j2 < this.clayPerChunk; j2++) {
         int i3 = this.randomGenerator.nextInt(16) + 8;
         int j7 = this.randomGenerator.nextInt(16) + 8;
         this.clayGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(this.field_180294_c.add(i3, 0, j7)));
      }

      for (int k2 = 0; k2 < this.sandPerChunk; k2++) {
         int j3 = this.randomGenerator.nextInt(16) + 8;
         int k7 = this.randomGenerator.nextInt(16) + 8;
         this.gravelAsSandGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(this.field_180294_c.add(j3, 0, k7)));
      }

      int l2 = this.treesPerChunk;
      int i1 = this.randomGenerator.nextInt(10);
      if (i1 == 0) {
         l2++;
      }

      for (int k3 = 0; k3 < l2; k3++) {
         int l7 = this.randomGenerator.nextInt(16) + 8;
         int l = this.randomGenerator.nextInt(16) + 8;
         WorldGenAbstractTree worldgenabstracttree = biomeGenBaseIn.genBigTreeChance(this.randomGenerator);
         worldgenabstracttree.func_175904_e();
         BlockPos blockpos = this.currentWorld.getHeight(this.field_180294_c.add(l7, 0, l));
         if (worldgenabstracttree.generate(this.currentWorld, this.randomGenerator, blockpos)) {
            worldgenabstracttree.func_180711_a(this.currentWorld, this.randomGenerator, blockpos);
         }
      }

      for (int l3 = 0; l3 < this.bigMushroomsPerChunk; l3++) {
         int i8 = this.randomGenerator.nextInt(16) + 8;
         int l11 = this.randomGenerator.nextInt(16) + 8;
         this.bigMushroomGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getHeight(this.field_180294_c.add(i8, 0, l11)));
      }

      for (int i4 = 0; i4 < this.flowersPerChunk; i4++) {
         int j8 = this.randomGenerator.nextInt(16) + 8;
         int i12 = this.randomGenerator.nextInt(16) + 8;
         int k15 = this.currentWorld.getHeight(this.field_180294_c.add(j8, 0, i12)).getY() + 32;
         if (k15 > 0) {
            int l18 = this.randomGenerator.nextInt(k15);
            BlockPos blockpos1 = this.field_180294_c.add(j8, l18, i12);
            BlockFlower.EnumFlowerType blockflower$enumflowertype = biomeGenBaseIn.pickRandomFlower(this.randomGenerator, blockpos1);
            BlockFlower blockflower = blockflower$enumflowertype.getBlockType().getBlock();
            if (blockflower.getMaterial() != Material.air) {
               this.yellowFlowerGen.setGeneratedBlock(blockflower, blockflower$enumflowertype);
               this.yellowFlowerGen.generate(this.currentWorld, this.randomGenerator, blockpos1);
            }
         }
      }

      for (int j4 = 0; j4 < this.grassPerChunk; j4++) {
         int k8 = this.randomGenerator.nextInt(16) + 8;
         int j12 = this.randomGenerator.nextInt(16) + 8;
         int l15 = this.currentWorld.getHeight(this.field_180294_c.add(k8, 0, j12)).getY() * 2;
         if (l15 > 0) {
            int i19 = this.randomGenerator.nextInt(l15);
            biomeGenBaseIn.getRandomWorldGenForGrass(this.randomGenerator)
               .generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(k8, i19, j12));
         }
      }

      for (int k4 = 0; k4 < this.deadBushPerChunk; k4++) {
         int l8 = this.randomGenerator.nextInt(16) + 8;
         int k12 = this.randomGenerator.nextInt(16) + 8;
         int i16 = this.currentWorld.getHeight(this.field_180294_c.add(l8, 0, k12)).getY() * 2;
         if (i16 > 0) {
            int j19 = this.randomGenerator.nextInt(i16);
            new WorldGenDeadBush().generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(l8, j19, k12));
         }
      }

      for (int l4 = 0; l4 < this.waterlilyPerChunk; l4++) {
         int i9 = this.randomGenerator.nextInt(16) + 8;
         int l12 = this.randomGenerator.nextInt(16) + 8;
         int j16 = this.currentWorld.getHeight(this.field_180294_c.add(i9, 0, l12)).getY() * 2;
         if (j16 > 0) {
            int k19 = this.randomGenerator.nextInt(j16);
            BlockPos blockpos4 = this.field_180294_c.add(i9, k19, l12);

            while (blockpos4.getY() > 0) {
               BlockPos blockpos7 = blockpos4.down();
               if (!this.currentWorld.isAirBlock(blockpos7)) {
                  break;
               }

               blockpos4 = blockpos7;
            }

            this.waterlilyGen.generate(this.currentWorld, this.randomGenerator, blockpos4);
         }
      }

      for (int i5 = 0; i5 < this.mushroomsPerChunk; i5++) {
         int j1 = this.randomGenerator.nextInt(4);
         if (j1 == 0) {
            int j9 = this.randomGenerator.nextInt(16) + 8;
            int i13 = this.randomGenerator.nextInt(16) + 8;
            BlockPos blockpos2 = this.currentWorld.getHeight(this.field_180294_c.add(j9, 0, i13));
            this.mushroomBrownGen.generate(this.currentWorld, this.randomGenerator, blockpos2);
         }

         if (this.randomGenerator.nextInt(8) == 0) {
            int k9 = this.randomGenerator.nextInt(16) + 8;
            int j13 = this.randomGenerator.nextInt(16) + 8;
            int k16 = this.currentWorld.getHeight(this.field_180294_c.add(k9, 0, j13)).getY() * 2;
            if (k16 > 0) {
               int l19 = this.randomGenerator.nextInt(k16);
               BlockPos blockpos5 = this.field_180294_c.add(k9, l19, j13);
               this.mushroomRedGen.generate(this.currentWorld, this.randomGenerator, blockpos5);
            }
         }
      }

      int k1 = this.randomGenerator.nextInt(4);
      if (k1 == 0) {
         int j5 = this.randomGenerator.nextInt(16) + 8;
         int l9 = this.randomGenerator.nextInt(16) + 8;
         int k13 = this.currentWorld.getHeight(this.field_180294_c.add(j5, 0, l9)).getY() * 2;
         if (k13 > 0) {
            int l16 = this.randomGenerator.nextInt(k13);
            this.mushroomBrownGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(j5, l16, l9));
         }
      }

      int l1 = this.randomGenerator.nextInt(8);
      if (l1 == 0) {
         int k5 = this.randomGenerator.nextInt(16) + 8;
         int i10 = this.randomGenerator.nextInt(16) + 8;
         int l13 = this.currentWorld.getHeight(this.field_180294_c.add(k5, 0, i10)).getY() * 2;
         if (l13 > 0) {
            int i17 = this.randomGenerator.nextInt(l13);
            this.mushroomRedGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(k5, i17, i10));
         }
      }

      for (int l5 = 0; l5 < this.reedsPerChunk; l5++) {
         int j10 = this.randomGenerator.nextInt(16) + 8;
         int i14 = this.randomGenerator.nextInt(16) + 8;
         int j17 = this.currentWorld.getHeight(this.field_180294_c.add(j10, 0, i14)).getY() * 2;
         if (j17 > 0) {
            int i20 = this.randomGenerator.nextInt(j17);
            this.reedGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(j10, i20, i14));
         }
      }

      for (int i6 = 0; i6 < 10; i6++) {
         int k10 = this.randomGenerator.nextInt(16) + 8;
         int j14 = this.randomGenerator.nextInt(16) + 8;
         int k17 = this.currentWorld.getHeight(this.field_180294_c.add(k10, 0, j14)).getY() * 2;
         if (k17 > 0) {
            int j20 = this.randomGenerator.nextInt(k17);
            this.reedGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(k10, j20, j14));
         }
      }

      int i2 = this.randomGenerator.nextInt(32);
      if (i2 == 0) {
         int j6 = this.randomGenerator.nextInt(16) + 8;
         int l10 = this.randomGenerator.nextInt(16) + 8;
         int k14 = this.currentWorld.getHeight(this.field_180294_c.add(j6, 0, l10)).getY() * 2;
         if (k14 > 0) {
            int l17 = this.randomGenerator.nextInt(k14);
            new WorldGenPumpkin().generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(j6, l17, l10));
         }
      }

      for (int k6 = 0; k6 < this.cactiPerChunk; k6++) {
         int i11 = this.randomGenerator.nextInt(16) + 8;
         int l14 = this.randomGenerator.nextInt(16) + 8;
         int i18 = this.currentWorld.getHeight(this.field_180294_c.add(i11, 0, l14)).getY() * 2;
         if (i18 > 0) {
            int k20 = this.randomGenerator.nextInt(i18);
            this.cactusGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(i11, k20, l14));
         }
      }

      boolean flag = this.generateLakes;
      if (flag) {
         for (int l6 = 0; l6 < 50; l6++) {
            int j11 = this.randomGenerator.nextInt(16) + 8;
            int i15 = this.randomGenerator.nextInt(16) + 8;
            int j18 = this.randomGenerator.nextInt(248) + 8;
            if (j18 > 0) {
               int l20 = this.randomGenerator.nextInt(j18);
               BlockPos blockpos6 = this.field_180294_c.add(j11, l20, i15);
               new WorldGenLiquids(Blocks.flowing_water).generate(this.currentWorld, this.randomGenerator, blockpos6);
            }
         }

         for (int i7 = 0; i7 < 20; i7++) {
            int k11 = this.randomGenerator.nextInt(16) + 8;
            int j15 = this.randomGenerator.nextInt(16) + 8;
            int k18 = this.randomGenerator.nextInt(this.randomGenerator.nextInt(this.randomGenerator.nextInt(240) + 8) + 8);
            BlockPos blockpos3 = this.field_180294_c.add(k11, k18, j15);
            new WorldGenLiquids(Blocks.flowing_lava).generate(this.currentWorld, this.randomGenerator, blockpos3);
         }
      }
   }

   public void genStandardOre1(int blockCount, WorldGenerator generator, int minHeight, int maxHeight) {
      if (maxHeight < minHeight) {
         int ix = minHeight;
         minHeight = maxHeight;
         maxHeight = ix;
      } else if (maxHeight == minHeight) {
         if (minHeight < 255) {
            maxHeight++;
         } else {
            minHeight--;
         }
      }

      for (int j = 0; j < blockCount; j++) {
         BlockPos blockpos = this.field_180294_c
            .add(this.randomGenerator.nextInt(16), this.randomGenerator.nextInt(maxHeight - minHeight) + minHeight, this.randomGenerator.nextInt(16));
         generator.generate(this.currentWorld, this.randomGenerator, blockpos);
      }
   }

   public void genStandardOre2(int blockCount, WorldGenerator generator, int centerHeight, int spread) {
      for (int ix = 0; ix < blockCount; ix++) {
         BlockPos blockpos = this.field_180294_c
            .add(
               this.randomGenerator.nextInt(16),
               this.randomGenerator.nextInt(spread) + this.randomGenerator.nextInt(spread) + centerHeight - spread,
               this.randomGenerator.nextInt(16)
            );
         generator.generate(this.currentWorld, this.randomGenerator, blockpos);
      }
   }

   public void generateOres() {
      this.genStandardOre1(
         this.chunkProviderSettings.dirtCount, this.dirtGen, this.chunkProviderSettings.dirtMinHeight, this.chunkProviderSettings.dirtMaxHeight
      );
      this.genStandardOre1(
         this.chunkProviderSettings.gravelCount, this.gravelGen, this.chunkProviderSettings.gravelMinHeight, this.chunkProviderSettings.gravelMaxHeight
      );
      this.genStandardOre1(
         this.chunkProviderSettings.dioriteCount, this.dioriteGen, this.chunkProviderSettings.dioriteMinHeight, this.chunkProviderSettings.dioriteMaxHeight
      );
      this.genStandardOre1(
         this.chunkProviderSettings.graniteCount, this.graniteGen, this.chunkProviderSettings.graniteMinHeight, this.chunkProviderSettings.graniteMaxHeight
      );
      this.genStandardOre1(
         this.chunkProviderSettings.andesiteCount, this.andesiteGen, this.chunkProviderSettings.andesiteMinHeight, this.chunkProviderSettings.andesiteMaxHeight
      );
      this.genStandardOre1(
         this.chunkProviderSettings.coalCount, this.coalGen, this.chunkProviderSettings.coalMinHeight, this.chunkProviderSettings.coalMaxHeight
      );
      this.genStandardOre1(
         this.chunkProviderSettings.ironCount, this.ironGen, this.chunkProviderSettings.ironMinHeight, this.chunkProviderSettings.ironMaxHeight
      );
      this.genStandardOre1(
         this.chunkProviderSettings.goldCount, this.goldGen, this.chunkProviderSettings.goldMinHeight, this.chunkProviderSettings.goldMaxHeight
      );
      this.genStandardOre1(
         this.chunkProviderSettings.redstoneCount, this.redstoneGen, this.chunkProviderSettings.redstoneMinHeight, this.chunkProviderSettings.redstoneMaxHeight
      );
      this.genStandardOre1(
         this.chunkProviderSettings.diamondCount, this.diamondGen, this.chunkProviderSettings.diamondMinHeight, this.chunkProviderSettings.diamondMaxHeight
      );
      this.genStandardOre2(
         this.chunkProviderSettings.lapisCount, this.lapisGen, this.chunkProviderSettings.lapisCenterHeight, this.chunkProviderSettings.lapisSpread
      );
   }
}
