package net.minecraft.world.gen.layer;

public class GenLayerZoom extends GenLayer {
   public GenLayerZoom(long l, GenLayer genLayer) {
      super(l);
      super.parent = genLayer;
   }

   @Override
   public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
      int i = areaX >> 1;
      int j = areaY >> 1;
      int k = (areaWidth >> 1) + 2;
      int l = (areaHeight >> 1) + 2;
      int[] aint = super.parent.getInts(i, j, k, l);
      int i1 = k - 1 << 1;
      int j1 = l - 1 << 1;
      int[] aint1 = IntCache.getIntCache(i1 * j1);

      for (int k1 = 0; k1 < l - 1; k1++) {
         int l1 = (k1 << 1) * i1;
         int i2 = 0;
         int j2 = aint[i2 + 0 + (k1 + 0) * k];
         int j3 = aint[i2 + 0 + (k1 + 1) * k];

         for (int k2 = j3; i2 < k - 1; i2++) {
            this.initChunkSeed((long)(i2 + i << 1), (long)(k1 + j << 1));
            int l2 = aint[i2 + 1 + (k1 + 0) * k];
            int i3 = aint[i2 + 1 + (k1 + 1) * k];
            aint1[l1] = j2;
            aint1[l1++ + i1] = this.selectRandom(new int[]{j2, k2});
            aint1[l1] = this.selectRandom(new int[]{j2, l2});
            aint1[l1++ + i1] = this.selectModeOrRandom(j2, l2, k2, i3);
            j2 = l2;
            k2 = i3;
         }
      }

      int[] aint2 = IntCache.getIntCache(areaWidth * areaHeight);

      for (int k3 = 0; k3 < areaHeight; k3++) {
         System.arraycopy(aint1, (k3 + (areaY & 1)) * i1 + (areaX & 1), aint2, k3 * areaWidth, areaWidth);
      }

      return aint2;
   }

   public static GenLayer magnify(long l, GenLayer genLayer, int i) {
      Object object = genLayer;

      for (int i1 = 0; i1 < i; i1++) {
         object = new GenLayerZoom(l + (long)i1, (GenLayer)object);
      }

      return (GenLayer)object;
   }
}
