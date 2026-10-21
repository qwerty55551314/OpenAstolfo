package net.minecraft.world.chunk.storage;

public class NibbleArrayReader {
   public final byte[] data;
   private final int depthBits;
   private final int depthBitsPlusFour;

   public NibbleArrayReader(byte[] dataIn, int depthBitsIn) {
      this.data = dataIn;
      this.depthBits = depthBitsIn;
      this.depthBitsPlusFour = depthBitsIn + 4;
   }

   public int get(int i, int j, int k) {
      int i1 = i << this.depthBitsPlusFour | k << this.depthBits | j;
      int j1 = i1 >> 1;
      int k1 = i1 & 1;
      return k1 == 0 ? this.data[j1] & 15 : this.data[j1] >> 4 & 15;
   }
}
