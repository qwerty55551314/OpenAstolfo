package net.minecraft.world.biome;

import java.util.Arrays;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeGenMesa extends BiomeGenBase {
   private IBlockState[] field_150621_aC;
   private long field_150622_aD;
   private NoiseGeneratorPerlin field_150623_aE;
   private NoiseGeneratorPerlin field_150624_aF;
   private NoiseGeneratorPerlin field_150625_aG;
   private boolean field_150626_aH;
   private boolean field_150620_aI;

   public BiomeGenMesa(int id, boolean bl, boolean bl2) {
      super(id);
      this.field_150626_aH = bl;
      this.field_150620_aI = bl2;
      this.setDisableRain();
      this.setTemperatureRainfall(2.0F, 0.0F);
      super.spawnableCreatureList.clear();
      super.topBlock = Blocks.sand.getDefaultState().withProperty(BlockSand.VARIANT, BlockSand.EnumType.RED_SAND);
      super.fillerBlock = Blocks.stained_hardened_clay.getDefaultState();
      super.theBiomeDecorator.treesPerChunk = -999;
      super.theBiomeDecorator.deadBushPerChunk = 20;
      super.theBiomeDecorator.reedsPerChunk = 3;
      super.theBiomeDecorator.cactiPerChunk = 5;
      super.theBiomeDecorator.flowersPerChunk = 0;
      super.spawnableCreatureList.clear();
      if (bl2) {
         super.theBiomeDecorator.treesPerChunk = 5;
      }
   }

   @Override
   public WorldGenAbstractTree genBigTreeChance(Random rand) {
      return super.worldGeneratorTrees;
   }

   @Override
   public int getFoliageColorAtPos(BlockPos pos) {
      return 10387789;
   }

   @Override
   public int getGrassColorAtPos(BlockPos pos) {
      return 9470285;
   }

   @Override
   public void decorate(World worldIn, Random rand, BlockPos pos) {
      super.decorate(worldIn, rand, pos);
   }

   @Override
   public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
      if (this.field_150621_aC == null || this.field_150622_aD != worldIn.getSeed()) {
         this.func_150619_a(worldIn.getSeed());
      }

      NoiseGeneratorPerlin noisegeneratorperlin = this.field_150623_aE;
      if (noisegeneratorperlin == null || this.field_150624_aF == null || this.field_150622_aD != worldIn.getSeed()) {
         Random random = new Random(this.field_150622_aD);
         this.field_150623_aE = new NoiseGeneratorPerlin(random, 4);
         this.field_150624_aF = new NoiseGeneratorPerlin(random, 1);
      }

      this.field_150622_aD = worldIn.getSeed();
      double d4 = 0.0;
      boolean flag = this.field_150626_aH;
      if (flag) {
         int i = (x & -16) + (z & 15);
         int j = (z & -16) + (x & 15);
         double d0 = Math.min(Math.abs(noiseVal), this.field_150623_aE.func_151601_a((double)i * 0.25, (double)j * 0.25));
         if (d0 > 0.0) {
            double d1 = 0.001953125;
            double d2 = Math.abs(this.field_150624_aF.func_151601_a((double)i * d1, (double)j * d1));
            d4 = d0 * d0 * 2.5;
            double d3 = Math.ceil(d2 * 50.0) + 14.0;
            if (d4 > d3) {
               d4 = d3;
            }

            d4 += 64.0;
         }
      }

      int l1 = x & 15;
      int i2 = z & 15;
      int j2 = worldIn.getSeaLevel();
      IBlockState iblockstate = Blocks.stained_hardened_clay.getDefaultState();
      IBlockState iblockstate3 = super.fillerBlock;
      int k = (int)(noiseVal / 3.0 + 3.0 + rand.nextDouble() * 0.25);
      double d5;
      int j1 = (d5 = Math.cos(noiseVal / 3.0 * Math.PI) - 0.0) == 0.0 ? 0 : (d5 < 0.0 ? -1 : 1);
      boolean flag1 = j1 > 0;
      int l = -1;
      boolean flag2 = false;

      for (int i1 = 255; i1 >= 0; i1--) {
         if (chunkPrimerIn.getBlockState(i2, i1, l1).getBlock().getMaterial() == Material.air) {
            int k1 = (int)d4;
            if (i1 < k1) {
               chunkPrimerIn.setBlockState(i2, i1, l1, Blocks.stone.getDefaultState());
            }
         }

         if (i1 <= rand.nextInt(5)) {
            chunkPrimerIn.setBlockState(i2, i1, l1, Blocks.bedrock.getDefaultState());
         } else {
            IBlockState iblockstate1 = chunkPrimerIn.getBlockState(i2, i1, l1);
            Block block = iblockstate1.getBlock();
            if (block.getMaterial() == Material.air) {
               l = -1;
            } else if (iblockstate1.getBlock() == Blocks.stone) {
               if (l == -1) {
                  flag2 = false;
                  if (k <= 0) {
                     iblockstate = null;
                     iblockstate3 = Blocks.stone.getDefaultState();
                  } else if (i1 >= j2 - 4 && i1 <= j2 + 1) {
                     iblockstate = Blocks.stained_hardened_clay.getDefaultState();
                     iblockstate3 = super.fillerBlock;
                  }

                  if (i1 < j2 && (iblockstate == null || iblockstate.getBlock().getMaterial() == Material.air)) {
                     iblockstate = Blocks.water.getDefaultState();
                  }

                  l = k + Math.max(0, i1 - j2);
                  if (i1 < j2 - 1) {
                     chunkPrimerIn.setBlockState(i2, i1, l1, iblockstate3);
                     if (iblockstate3.getBlock() == Blocks.stained_hardened_clay) {
                        chunkPrimerIn.setBlockState(i2, i1, l1, iblockstate3.getBlock().getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.ORANGE));
                     }
                  } else if (!this.field_150620_aI || i1 <= 86 + k * 2) {
                     if (i1 <= j2 + 3 + k) {
                        chunkPrimerIn.setBlockState(i2, i1, l1, super.topBlock);
                        flag2 = true;
                     } else {
                        IBlockState iblockstate2;
                        if (i1 < 64 || i1 > 127) {
                           iblockstate2 = Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.ORANGE);
                        } else if (flag1) {
                           iblockstate2 = Blocks.hardened_clay.getDefaultState();
                        } else {
                           iblockstate2 = this.func_180629_a(x, i1, z);
                        }

                        chunkPrimerIn.setBlockState(i2, i1, l1, iblockstate2);
                     }
                  } else if (flag1) {
                     chunkPrimerIn.setBlockState(i2, i1, l1, Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT));
                  } else {
                     chunkPrimerIn.setBlockState(i2, i1, l1, Blocks.grass.getDefaultState());
                  }
               } else if (l > 0) {
                  l--;
                  if (flag2) {
                     chunkPrimerIn.setBlockState(
                        i2, i1, l1, Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.ORANGE)
                     );
                  } else {
                     IBlockState iblockstate4 = this.func_180629_a(x, i1, z);
                     chunkPrimerIn.setBlockState(i2, i1, l1, iblockstate4);
                  }
               }
            }
         }
      }
   }

   private void func_150619_a(long l) {
      boolean flag = true;
      this.field_150621_aC = new IBlockState[64];
      Arrays.fill(this.field_150621_aC, Blocks.hardened_clay.getDefaultState());
      Random random = new Random(l);
      this.field_150625_aG = new NoiseGeneratorPerlin(random, 1);

      for (int l1 = 0; l1 < 64; l1++) {
         l1 += random.nextInt(5) + 1;
         if (l1 < 64) {
            this.field_150621_aC[l1] = Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.ORANGE);
         }
      }

      int i2 = random.nextInt(4) + 2;

      for (int i = 0; i < i2; i++) {
         int j = random.nextInt(3) + 1;
         int k = random.nextInt(64);

         for (int l1x = 0; k + l1x < 64 && l1x < j; l1x++) {
            this.field_150621_aC[k + l1x] = Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.YELLOW);
         }
      }

      int j2 = random.nextInt(4) + 2;

      for (int k2 = 0; k2 < j2; k2++) {
         int i3 = random.nextInt(3) + 2;
         int l3 = random.nextInt(64);

         for (int i1 = 0; l3 + i1 < 64 && i1 < i3; i1++) {
            this.field_150621_aC[l3 + i1] = Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.BROWN);
         }
      }

      int l2 = random.nextInt(4) + 2;

      for (int j3 = 0; j3 < l2; j3++) {
         int i4 = random.nextInt(3) + 1;
         int k4 = random.nextInt(64);

         for (int j1 = 0; k4 + j1 < 64 && j1 < i4; j1++) {
            this.field_150621_aC[k4 + j1] = Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.RED);
         }
      }

      int k3 = random.nextInt(3) + 3;
      int j4 = 0;

      for (int l4 = 0; l4 < k3; l4++) {
         int b0 = 1;
         j4 += random.nextInt(16) + 4;

         for (int k1 = 0; j4 + k1 < 64 && k1 < b0; k1++) {
            this.field_150621_aC[j4 + k1] = Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.WHITE);
            if (j4 + k1 > 1 && random.nextBoolean()) {
               this.field_150621_aC[j4 + k1 - 1] = Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.SILVER);
            }

            if (j4 + k1 < 63 && random.nextBoolean()) {
               this.field_150621_aC[j4 + k1 + 1] = Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.SILVER);
            }
         }
      }
   }

   private IBlockState func_180629_a(int i, int j, int k) {
      int i1 = (int)Math.round(this.field_150625_aG.func_151601_a((double)i * 1.0 / 512.0, (double)i * 1.0 / 512.0) * 2.0);
      return this.field_150621_aC[(j + i1 + 64) % 64];
   }

   @Override
   public BiomeGenBase createMutatedBiome(int i) {
      boolean flag = super.biomeID == BiomeGenBase.mesa.biomeID;
      BiomeGenMesa biomegenmesa = new BiomeGenMesa(i, flag, this.field_150620_aI);
      if (!flag) {
         biomegenmesa.setHeight(BiomeGenBase.height_LowHills);
         biomegenmesa.setBiomeName(super.biomeName + " M");
      } else {
         biomegenmesa.setBiomeName(super.biomeName + " (Bryce)");
      }

      biomegenmesa.func_150557_a(super.color, true);
      return biomegenmesa;
   }
}
