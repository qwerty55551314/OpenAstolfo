package net.minecraft.world.gen;

import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenFire;
import net.minecraft.world.gen.feature.WorldGenGlowStone1;
import net.minecraft.world.gen.feature.WorldGenGlowStone2;
import net.minecraft.world.gen.feature.WorldGenHellLava;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.MapGenNetherBridge;

public class ChunkProviderHell implements IChunkProvider {
   private final World worldObj;
   private final boolean field_177466_i;
   private final Random hellRNG;
   private double[] slowsandNoise = new double[256];
   private double[] gravelNoise = new double[256];
   private double[] netherrackExclusivityNoise = new double[256];
   private double[] noiseField;
   private final NoiseGeneratorOctaves netherNoiseGen1;
   private final NoiseGeneratorOctaves netherNoiseGen2;
   private final NoiseGeneratorOctaves netherNoiseGen3;
   private final NoiseGeneratorOctaves slowsandGravelNoiseGen;
   private final NoiseGeneratorOctaves netherrackExculsivityNoiseGen;
   public final NoiseGeneratorOctaves netherNoiseGen6;
   public final NoiseGeneratorOctaves netherNoiseGen7;
   private final WorldGenFire field_177470_t = new WorldGenFire();
   private final WorldGenGlowStone1 field_177469_u = new WorldGenGlowStone1();
   private final WorldGenGlowStone2 field_177468_v = new WorldGenGlowStone2();
   private final WorldGenerator field_177467_w = new WorldGenMinable(Blocks.quartz_ore.getDefaultState(), 14, BlockHelper.forBlock(Blocks.netherrack));
   private final WorldGenHellLava field_177473_x = new WorldGenHellLava(Blocks.flowing_lava, true);
   private final WorldGenHellLava field_177472_y = new WorldGenHellLava(Blocks.flowing_lava, false);
   private final GeneratorBushFeature field_177471_z = new GeneratorBushFeature(Blocks.brown_mushroom);
   private final GeneratorBushFeature field_177465_A = new GeneratorBushFeature(Blocks.red_mushroom);
   private final MapGenNetherBridge genNetherBridge = new MapGenNetherBridge();
   private final MapGenBase netherCaveGenerator = new MapGenCavesHell();
   public double[] noiseData1;
   public double[] noiseData2;
   public double[] noiseData3;
   public double[] noiseData4;
   public double[] noiseData5;
   private static final String k = "HellRandomLevelSource";

   public ChunkProviderHell(World worldIn, boolean bl, long seed) {
      this.worldObj = worldIn;
      this.field_177466_i = bl;
      this.hellRNG = new Random(seed);
      this.netherNoiseGen1 = new NoiseGeneratorOctaves(this.hellRNG, 16);
      this.netherNoiseGen2 = new NoiseGeneratorOctaves(this.hellRNG, 16);
      this.netherNoiseGen3 = new NoiseGeneratorOctaves(this.hellRNG, 8);
      this.slowsandGravelNoiseGen = new NoiseGeneratorOctaves(this.hellRNG, 4);
      this.netherrackExculsivityNoiseGen = new NoiseGeneratorOctaves(this.hellRNG, 4);
      this.netherNoiseGen6 = new NoiseGeneratorOctaves(this.hellRNG, 10);
      this.netherNoiseGen7 = new NoiseGeneratorOctaves(this.hellRNG, 16);
      worldIn.setSeaLevel(63);
   }

   public void func_180515_a(int i, int j, ChunkPrimer chunkPrimer) {
      int i1 = this.worldObj.getSeaLevel() / 2 + 1;
      int j1 = 4 + 1;
      int kx = 4 + 1;
      this.noiseField = this.initializeNoiseField(this.noiseField, i * 4, 0, j * 4, j1, 17, kx);

      for (int l = 0; l < 4; l++) {
         for (int i1x = 0; i1x < 4; i1x++) {
            for (int j1x = 0; j1x < 16; j1x++) {
               double d0 = 0.125;
               double d1 = this.noiseField[((l + 0) * kx + i1x + 0) * 17 + j1x + 0];
               double d2 = this.noiseField[((l + 0) * kx + i1x + 1) * 17 + j1x + 0];
               double d3 = this.noiseField[((l + 1) * kx + i1x + 0) * 17 + j1x + 0];
               double d4 = this.noiseField[((l + 1) * kx + i1x + 1) * 17 + j1x + 0];
               double d5 = (this.noiseField[((l + 0) * kx + i1x + 0) * 17 + j1x + 1] - d1) * d0;
               double d6 = (this.noiseField[((l + 0) * kx + i1x + 1) * 17 + j1x + 1] - d2) * d0;
               double d7 = (this.noiseField[((l + 1) * kx + i1x + 0) * 17 + j1x + 1] - d3) * d0;
               double d8 = (this.noiseField[((l + 1) * kx + i1x + 1) * 17 + j1x + 1] - d4) * d0;

               for (int k1 = 0; k1 < 8; k1++) {
                  double d9 = 0.25;
                  double d10 = d1;
                  double d11 = d2;
                  double d12 = (d3 - d1) * d9;
                  double d13 = (d4 - d2) * d9;

                  for (int l1 = 0; l1 < 4; l1++) {
                     double d14 = 0.25;
                     double d15 = d10;
                     double d16 = (d11 - d10) * d14;

                     for (int i2 = 0; i2 < 4; i2++) {
                        IBlockState iblockstate = null;
                        if (j1x * 8 + k1 < i1) {
                           iblockstate = Blocks.lava.getDefaultState();
                        }

                        if (d15 > 0.0) {
                           iblockstate = Blocks.netherrack.getDefaultState();
                        }

                        int j2 = l1 + l * 4;
                        int k2 = k1 + j1x * 8;
                        int l2 = i2 + i1x * 4;
                        chunkPrimer.setBlockState(j2, k2, l2, iblockstate);
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

   public void func_180516_b(int i, int j, ChunkPrimer chunkPrimer) {
      int i1 = this.worldObj.getSeaLevel() + 1;
      double d0 = 0.03125;
      this.slowsandNoise = this.slowsandGravelNoiseGen.generateNoiseOctaves(this.slowsandNoise, i * 16, j * 16, 0, 16, 16, 1, d0, d0, 1.0);
      this.gravelNoise = this.slowsandGravelNoiseGen.generateNoiseOctaves(this.gravelNoise, i * 16, 109, j * 16, 16, 1, 16, d0, 1.0, d0);
      this.netherrackExclusivityNoise = this.netherrackExculsivityNoiseGen
         .generateNoiseOctaves(this.netherrackExclusivityNoise, i * 16, j * 16, 0, 16, 16, 1, d0 * 2.0, d0 * 2.0, d0 * 2.0);

      for (int j1 = 0; j1 < 16; j1++) {
         for (int kx = 0; kx < 16; kx++) {
            boolean flag = this.slowsandNoise[j1 + kx * 16] + this.hellRNG.nextDouble() * 0.2 > 0.0;
            boolean flag1 = this.gravelNoise[j1 + kx * 16] + this.hellRNG.nextDouble() * 0.2 > 0.0;
            int l = (int)(this.netherrackExclusivityNoise[j1 + kx * 16] / 3.0 + 3.0 + this.hellRNG.nextDouble() * 0.25);
            int i1x = -1;
            IBlockState iblockstate = Blocks.netherrack.getDefaultState();
            IBlockState iblockstate1 = Blocks.netherrack.getDefaultState();

            for (int j1x = 127; j1x >= 0; j1x--) {
               if (j1x < 127 - this.hellRNG.nextInt(5) && j1x > this.hellRNG.nextInt(5)) {
                  IBlockState iblockstate2 = chunkPrimer.getBlockState(kx, j1x, j1);
                  if (iblockstate2.getBlock() == null || iblockstate2.getBlock().getMaterial() == Material.air) {
                     i1x = -1;
                  } else if (iblockstate2.getBlock() == Blocks.netherrack) {
                     if (i1x == -1) {
                        if (l <= 0) {
                           iblockstate = null;
                           iblockstate1 = Blocks.netherrack.getDefaultState();
                        } else if (j1x >= i1 - 4 && j1x <= i1 + 1) {
                           iblockstate = Blocks.netherrack.getDefaultState();
                           iblockstate1 = Blocks.netherrack.getDefaultState();
                           if (flag1) {
                              iblockstate = Blocks.gravel.getDefaultState();
                              iblockstate1 = Blocks.netherrack.getDefaultState();
                           }

                           if (flag) {
                              iblockstate = Blocks.soul_sand.getDefaultState();
                              iblockstate1 = Blocks.soul_sand.getDefaultState();
                           }
                        }

                        if (j1x < i1 && (iblockstate == null || iblockstate.getBlock().getMaterial() == Material.air)) {
                           iblockstate = Blocks.lava.getDefaultState();
                        }

                        i1x = l;
                        if (j1x >= i1 - 1) {
                           chunkPrimer.setBlockState(kx, j1x, j1, iblockstate);
                        } else {
                           chunkPrimer.setBlockState(kx, j1x, j1, iblockstate1);
                        }
                     } else if (i1x > 0) {
                        i1x--;
                        chunkPrimer.setBlockState(kx, j1x, j1, iblockstate1);
                     }
                  }
               } else {
                  chunkPrimer.setBlockState(kx, j1x, j1, Blocks.bedrock.getDefaultState());
               }
            }
         }
      }
   }

   @Override
   public Chunk provideChunk(int x, int z) {
      this.hellRNG.setSeed((long)x * 341873128712L + (long)z * 132897987541L);
      boolean flag = true;
      ChunkPrimer chunkprimer = new ChunkPrimer();
      this.func_180515_a(x, z, chunkprimer);
      this.func_180516_b(x, z, chunkprimer);
      this.netherCaveGenerator.generate(this, this.worldObj, x, z, chunkprimer);
      if (this.field_177466_i) {
         this.genNetherBridge.generate(this, this.worldObj, x, z, chunkprimer);
      }

      Chunk chunk = new Chunk(this.worldObj, chunkprimer, x, z);
      BiomeGenBase[] abiomegenbase = this.worldObj.getWorldChunkManager().loadBlockGeneratorData((BiomeGenBase[])null, x * 16, z * 16, 16, 16);
      byte[] abyte = chunk.getBiomeArray();

      for (int i = 0; i < abyte.length; i++) {
         abyte[i] = (byte)abiomegenbase[i].biomeID;
      }

      chunk.resetRelightChecks();
      return chunk;
   }

   private double[] initializeNoiseField(double[] doubles, int i, int j, int k, int l, int m, int n) {
      if (doubles == null) {
         doubles = new double[l * m * n];
      }

      double d0 = 684.412;
      double d1 = 2053.236;
      this.noiseData4 = this.netherNoiseGen6.generateNoiseOctaves(this.noiseData4, i, j, k, l, 1, n, 1.0, 0.0, 1.0);
      this.noiseData5 = this.netherNoiseGen7.generateNoiseOctaves(this.noiseData5, i, j, k, l, 1, n, 100.0, 0.0, 100.0);
      this.noiseData1 = this.netherNoiseGen3.generateNoiseOctaves(this.noiseData1, i, j, k, l, m, n, d0 / 80.0, d1 / 60.0, d0 / 80.0);
      this.noiseData2 = this.netherNoiseGen1.generateNoiseOctaves(this.noiseData2, i, j, k, l, m, n, d0, d1, d0);
      this.noiseData3 = this.netherNoiseGen2.generateNoiseOctaves(this.noiseData3, i, j, k, l, m, n, d0, d1, d0);
      int i1 = 0;
      double[] adouble = new double[m];

      for (int j1 = 0; j1 < m; j1++) {
         adouble[j1] = Math.cos((double)j1 * Math.PI * 6.0 / (double)m) * 2.0;
         double d2 = (double)j1;
         if (j1 > m / 2) {
            d2 = (double)(m - 1 - j1);
         }

         if (d2 < 4.0) {
            d2 = 4.0 - d2;
            adouble[j1] -= d2 * d2 * d2 * 10.0;
         }
      }

      for (int l1 = 0; l1 < l; l1++) {
         for (int i1x = 0; i1x < n; i1x++) {
            double d3 = 0.0;

            for (int kx = 0; kx < m; kx++) {
               double d5 = adouble[kx];
               double d6 = this.noiseData2[i1] / 512.0;
               double d7 = this.noiseData3[i1] / 512.0;
               double d8 = (this.noiseData1[i1] / 10.0 + 1.0) / 2.0;
               double d4;
               if (d8 < 0.0) {
                  d4 = d6;
               } else if (d8 > 1.0) {
                  d4 = d7;
               } else {
                  d4 = d6 + (d7 - d6) * d8;
               }

               d4 -= d5;
               if (kx > m - 4) {
                  double d9 = (double)((float)(kx - (m - 4)) / 3.0F);
                  d4 = d4 * (1.0 - d9) + -10.0 * d9;
               }

               if ((double)kx < d3) {
                  double d10 = (d3 - (double)kx) / 4.0;
                  d10 = MathHelper.clamp_double(d10, 0.0, 1.0);
                  d4 = d4 * (1.0 - d10) + -10.0 * d10;
               }

               doubles[i1] = d4;
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
      ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(x, z);
      this.genNetherBridge.generateStructure(this.worldObj, this.hellRNG, chunkcoordintpair);

      for (int i = 0; i < 8; i++) {
         this.field_177472_y
            .generate(this.worldObj, this.hellRNG, blockpos.add(this.hellRNG.nextInt(16) + 8, this.hellRNG.nextInt(120) + 4, this.hellRNG.nextInt(16) + 8));
      }

      for (int j = 0; j < this.hellRNG.nextInt(this.hellRNG.nextInt(10) + 1) + 1; j++) {
         this.field_177470_t
            .generate(this.worldObj, this.hellRNG, blockpos.add(this.hellRNG.nextInt(16) + 8, this.hellRNG.nextInt(120) + 4, this.hellRNG.nextInt(16) + 8));
      }

      for (int kx = 0; kx < this.hellRNG.nextInt(this.hellRNG.nextInt(10) + 1); kx++) {
         this.field_177469_u
            .generate(this.worldObj, this.hellRNG, blockpos.add(this.hellRNG.nextInt(16) + 8, this.hellRNG.nextInt(120) + 4, this.hellRNG.nextInt(16) + 8));
      }

      for (int l = 0; l < 10; l++) {
         this.field_177468_v
            .generate(this.worldObj, this.hellRNG, blockpos.add(this.hellRNG.nextInt(16) + 8, this.hellRNG.nextInt(128), this.hellRNG.nextInt(16) + 8));
      }

      boolean flag = this.hellRNG.nextBoolean();
      if (flag) {
         this.field_177471_z
            .generate(this.worldObj, this.hellRNG, blockpos.add(this.hellRNG.nextInt(16) + 8, this.hellRNG.nextInt(128), this.hellRNG.nextInt(16) + 8));
      }

      boolean flag1 = this.hellRNG.nextBoolean();
      if (flag1) {
         this.field_177465_A
            .generate(this.worldObj, this.hellRNG, blockpos.add(this.hellRNG.nextInt(16) + 8, this.hellRNG.nextInt(128), this.hellRNG.nextInt(16) + 8));
      }

      for (int i1 = 0; i1 < 16; i1++) {
         this.field_177467_w
            .generate(this.worldObj, this.hellRNG, blockpos.add(this.hellRNG.nextInt(16), this.hellRNG.nextInt(108) + 10, this.hellRNG.nextInt(16)));
      }

      for (int j1 = 0; j1 < 16; j1++) {
         this.field_177473_x
            .generate(this.worldObj, this.hellRNG, blockpos.add(this.hellRNG.nextInt(16), this.hellRNG.nextInt(108) + 10, this.hellRNG.nextInt(16)));
      }

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
      return k;
   }

   @Override
   public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
      if (creatureType == EnumCreatureType.MONSTER) {
         boolean flag = this.genNetherBridge.func_175795_b(pos);
         if (flag) {
            return this.genNetherBridge.getSpawnList();
         }

         if (this.genNetherBridge.isPositionInStructure(this.worldObj, pos)) {
            World world1 = this.worldObj;
            BlockPos blockpos = pos.down();
            World world = world1;
            if (world.getBlockState(blockpos).getBlock() == Blocks.nether_brick) {
               return this.genNetherBridge.getSpawnList();
            }
         }
      }

      BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(pos);
      return biomegenbase.getSpawnableList(creatureType);
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
      this.genNetherBridge.generate(this, this.worldObj, x, z, (ChunkPrimer)null);
   }

   @Override
   public Chunk provideChunk(BlockPos blockPosIn) {
      return this.provideChunk(blockPosIn.getX() >> 4, blockPosIn.getZ() >> 4);
   }
}
