package net.minecraft.world.gen;

import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;

public class MapGenBase {
   public int range = 8;
   public Random rand = new Random();
   public World worldObj;
   private static boolean l;

   public void generate(IChunkProvider chunkProviderIn, World worldIn, int x, int z, ChunkPrimer chunkPrimerIn) {
      int i = this.range;
      this.worldObj = worldIn;
      this.rand.setSeed(worldIn.getSeed());
      long j = this.rand.nextLong();
      long k = this.rand.nextLong();

      for (int lx = x - i; lx <= x + i; lx++) {
         for (int i1 = z - i; i1 <= z + i; i1++) {
            long j1 = (long)lx * j;
            long k1 = (long)i1 * k;
            this.rand.setSeed(j1 ^ k1 ^ worldIn.getSeed());
            this.recursiveGenerate(worldIn, lx, i1, x, z, chunkPrimerIn);
         }
      }
   }

   public void recursiveGenerate(World worldIn, int chunkX, int chunkZ, int i, int j, ChunkPrimer chunkPrimerIn) {
   }

   public static void A(boolean flag) {
      l = flag;
   }

   public static boolean s() {
      return l;
   }

   public static boolean w() {
      return true;
   }
}
