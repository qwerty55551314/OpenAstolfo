package net.minecraft.world.gen;

import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;

public class ChunkProviderEnd implements IChunkProvider {
   private Random endRNG;
   private NoiseGeneratorOctaves noiseGen1;
   private NoiseGeneratorOctaves noiseGen2;
   private NoiseGeneratorOctaves noiseGen3;
   public NoiseGeneratorOctaves noiseGen4;
   public NoiseGeneratorOctaves noiseGen5;
   private World endWorld;
   private double[] densities;
   private BiomeGenBase[] biomesForGeneration;
   public double[] noiseData1;
   public double[] noiseData2;
   public double[] noiseData3;
   public double[] noiseData4;
   public double[] noiseData5;
   private static final String b = "RandomLevelSource";

   public ChunkProviderEnd(World worldIn, long seed) {
      this.endWorld = worldIn;
      this.endRNG = new Random(seed);
      this.noiseGen1 = new NoiseGeneratorOctaves(this.endRNG, 16);
      this.noiseGen2 = new NoiseGeneratorOctaves(this.endRNG, 16);
      this.noiseGen3 = new NoiseGeneratorOctaves(this.endRNG, 8);
      this.noiseGen4 = new NoiseGeneratorOctaves(this.endRNG, 10);
      this.noiseGen5 = new NoiseGeneratorOctaves(this.endRNG, 16);
   }

   public void func_180520_a(int i, int j, ChunkPrimer chunkPrimer) {
      int i1 = 2 + 1;
      if (false) {
         int l2 = 2 + 1;
         this.densities = this.initializeNoiseField(this.densities, i * 2, 0, j * 2, i1, 33, l2);
         int i3 = 0;

         for (int j3 = i3; j3 < 2; j3 = 0) {
            int b0 = 0;
            if (b0 >= 2) {
               i3++;
               break;
            }
         }
      } else {
         int j1 = 2 + 1;
         this.densities = this.initializeNoiseField(this.densities, i * 2, 0, j * 2, i1, 33, j1);

         for (int k = 0; k < 2; k++) {
            for (int l = 0; l < 2; l++) {
               for (int i1x = 0; i1x < 32; i1x++) {
                  double d0 = 0.25;
                  double d1 = this.densities[((k + 0) * j1 + l + 0) * 33 + i1x + 0];
                  double d2 = this.densities[((k + 0) * j1 + l + 1) * 33 + i1x + 0];
                  double d3 = this.densities[((k + 1) * j1 + l + 0) * 33 + i1x + 0];
                  double d4 = this.densities[((k + 1) * j1 + l + 1) * 33 + i1x + 0];
                  double d5 = (this.densities[((k + 0) * j1 + l + 0) * 33 + i1x + 1] - d1) * d0;
                  double d6 = (this.densities[((k + 0) * j1 + l + 1) * 33 + i1x + 1] - d2) * d0;
                  double d7 = (this.densities[((k + 1) * j1 + l + 0) * 33 + i1x + 1] - d3) * d0;
                  double d8 = (this.densities[((k + 1) * j1 + l + 1) * 33 + i1x + 1] - d4) * d0;

                  for (int j1x = 0; j1x < 4; j1x++) {
                     double d9 = 0.125;
                     double d10 = d1;
                     double d11 = d2;
                     double d12 = (d3 - d1) * d9;
                     double d13 = (d4 - d2) * d9;

                     for (int k1 = 0; k1 < 8; k1++) {
                        double d14 = 0.125;
                        double d15 = d10;
                        double d16 = (d11 - d10) * d14;

                        for (int l1 = 0; l1 < 8; l1++) {
                           IBlockState iblockstate = null;
                           if (d15 > 0.0) {
                              iblockstate = Blocks.end_stone.getDefaultState();
                           }

                           int i2 = k1 + k * 8;
                           int j2 = j1x + i1x * 4;
                           int k2 = l1 + l * 8;
                           chunkPrimer.setBlockState(i2, j2, k2, iblockstate);
                           d15 += d16;
                        }

                        d10 += d12;
                        d11 += d13;
                     }

                     d1 += d5;
                     d2 += d6;
                     d3 += d7;
                     d4 += d8;
                  }
               }
            }
         }
      }
   }

   public void func_180519_a(ChunkPrimer chunkPrimer) {
      for (int i = 0; i < 16; i++) {
         for (int j = 0; j < 16; j++) {
            int k = -1;
            IBlockState iblockstate = Blocks.end_stone.getDefaultState();
            IBlockState iblockstate1 = Blocks.end_stone.getDefaultState();

            for (int l = 127; l >= 0; l--) {
               IBlockState iblockstate2 = chunkPrimer.getBlockState(i, l, j);
               if (iblockstate2.getBlock().getMaterial() == Material.air) {
                  k = -1;
               } else if (iblockstate2.getBlock() == Blocks.stone) {
                  if (k == -1) {
                     k = 1;
                     if (l >= 0) {
                        chunkPrimer.setBlockState(i, l, j, iblockstate);
                     } else {
                        chunkPrimer.setBlockState(i, l, j, iblockstate1);
                     }
                  } else if (k > 0) {
                     k--;
                     chunkPrimer.setBlockState(i, l, j, iblockstate1);
                  }
               }
            }
         }
      }
   }

   @Override
   public Chunk provideChunk(int x, int z) {
      boolean flag = false;
      this.endRNG.setSeed((long)x * 341873128712L + (long)z * 132897987541L);
      ChunkPrimer chunkprimer = new ChunkPrimer();
      this.biomesForGeneration = this.endWorld.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, x * 16, z * 16, 16, 16);
      this.func_180520_a(x, z, chunkprimer);
      this.func_180519_a(chunkprimer);
      Chunk chunk = new Chunk(this.endWorld, chunkprimer, x, z);
      byte[] abyte = chunk.getBiomeArray();

      for (int i = 0; i < abyte.length; i++) {
         abyte[i] = (byte)this.biomesForGeneration[i].biomeID;
      }

      chunk.generateSkylightMap();
      return chunk;
   }

   private double[] initializeNoiseField(double[] doubles, int i, int j, int k, int l, int m, int n) {
      if (doubles == null) {
         doubles = new double[l * m * n];
      }

      double d0 = 684.412;
      double d1 = 684.412;
      this.noiseData4 = this.noiseGen4.generateNoiseOctaves(this.noiseData4, i, k, l, n, 1.121, 1.121, 0.5);
      this.noiseData5 = this.noiseGen5.generateNoiseOctaves(this.noiseData5, i, k, l, n, 200.0, 200.0, 0.5);
      d0 *= 2.0;
      this.noiseData1 = this.noiseGen3.generateNoiseOctaves(this.noiseData1, i, j, k, l, m, n, d0 / 80.0, d1 / 160.0, d0 / 80.0);
      this.noiseData2 = this.noiseGen1.generateNoiseOctaves(this.noiseData2, i, j, k, l, m, n, d0, d1, d0);
      this.noiseData3 = this.noiseGen2.generateNoiseOctaves(this.noiseData3, i, j, k, l, m, n, d0, d1, d0);
      int i1 = 0;

      for (int j1 = 0; j1 < l; j1++) {
         for (int k1 = 0; k1 < n; k1++) {
            float f = (float)(j1 + i) / 1.0F;
            float f1 = (float)(k1 + k) / 1.0F;
            float f2 = 100.0F - MathHelper.sqrt_float(f * f + f1 * f1) * 8.0F;
            float f3;
            int i1x = (f3 = f2 - 80.0F) == 0.0F ? 0 : (f3 < 0.0F ? -1 : 1);
            if (i1x > 0) {
               f2 = 80.0F;
            }

            if (f2 < -100.0F) {
               f2 = -100.0F;
            }

            for (int l1 = 0; l1 < m; l1++) {
               double d3 = this.noiseData2[i1] / 512.0;
               double d4 = this.noiseData3[i1] / 512.0;
               double d5 = (this.noiseData1[i1] / 10.0 + 1.0) / 2.0;
               double d2;
               if (d5 < 0.0) {
                  d2 = d3;
               } else if (d5 > 1.0) {
                  d2 = d4;
               } else {
                  d2 = d3 + (d4 - d3) * d5;
               }

               d2 -= 8.0;
               d2 += (double)f2;
               int b0 = 2;
               if (l1 > m / 2 - b0) {
                  double d6 = (double)((float)(l1 - (m / 2 - b0)) / 64.0F);
                  d6 = MathHelper.clamp_double(d6, 0.0, 1.0);
                  d2 = d2 * (1.0 - d6) + -3000.0 * d6;
               }

               b0 = 8;
               if (l1 < b0) {
                  double d7 = (double)((float)(b0 - l1) / ((float)b0 - 1.0F));
                  d2 = d2 * (1.0 - d7) + -30.0 * d7;
               }

               doubles[i1] = d2;
               i1++;
            }
         }
      }

      return doubles;
   }

   @Override
   public boolean chunkExists(int x, int z) {
      return true;
   }

   @Override
   public void populate(IChunkProvider chunkProvider, int x, int z) {
      BlockFalling.fallInstantly = true;
      BlockPos blockpos = new BlockPos(x * 16, 0, z * 16);
      this.endWorld.getBiomeGenForCoords(blockpos.add(16, 0, 16)).decorate(this.endWorld, this.endWorld.rand, blockpos);
      BlockFalling.fallInstantly = false;
   }

   @Override
   public boolean populateChunk(IChunkProvider chunkProvider, Chunk chunkIn, int x, int z) {
      return false;
   }

   @Override
   public boolean saveChunks(boolean saveAllChunks, IProgressUpdate progressCallback) {
      return true;
   }

   @Override
   public void saveExtraData() {
   }

   @Override
   public boolean unloadQueuedChunks() {
      return false;
   }

   @Override
   public boolean canSave() {
      return true;
   }

   @Override
   public String makeString() {
      return b;
   }

   @Override
   public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
      return this.endWorld.getBiomeGenForCoords(pos).getSpawnableList(creatureType);
   }

   @Override
   public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position) {
      return null;
   }

   @Override
   public int getLoadedChunkCount() {
      return 0;
   }

   @Override
   public void recreateStructures(Chunk chunkIn, int x, int z) {
   }

   @Override
   public Chunk provideChunk(BlockPos blockPosIn) {
      return this.provideChunk(blockPosIn.getX() >> 4, blockPosIn.getZ() >> 4);
   }
}
