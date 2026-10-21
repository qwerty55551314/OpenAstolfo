package net.minecraft.world.gen.layer;

public class GenLayerVoronoiZoom extends GenLayer {
   public GenLayerVoronoiZoom(long l, GenLayer genLayer) {
      super(l);
      super.parent = genLayer;
   }

   @Override
   public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
      areaX -= 2;
      areaY -= 2;
      int i = areaX >> 2;
      int j = areaY >> 2;
      int k = (areaWidth >> 2) + 2;
      int l = (areaHeight >> 2) + 2;
      int[] aint = super.parent.getInts(i, j, k, l);
      int i1 = k - 1 << 2;
      int j1 = l - 1 << 2;
      int[] aint1 = IntCache.getIntCache(i1 * j1);

      for (int k1 = 0; k1 < l - 1; k1++) {
         int l1 = 0;
         int i2 = aint[l1 + 0 + (k1 + 0) * k];
         int l3 = aint[l1 + 0 + (k1 + 1) * k];

         for (int j2 = l3; l1 < k - 1; l1++) {
            this.initChunkSeed((long)(l1 + i << 2), (long)(k1 + j << 2));
            double d0 = ((double)this.nextInt(1024) / 1024.0 - 0.5) * 3.6;
            double d1 = ((double)this.nextInt(1024) / 1024.0 - 0.5) * 3.6;
            this.initChunkSeed((long)(l1 + i + 1 << 2), (long)(k1 + j << 2));
            double d2 = ((double)this.nextInt(1024) / 1024.0 - 0.5) * 3.6 + 4.0;
            double d3 = ((double)this.nextInt(1024) / 1024.0 - 0.5) * 3.6;
            this.initChunkSeed((long)(l1 + i << 2), (long)(k1 + j + 1 << 2));
            double d4 = ((double)this.nextInt(1024) / 1024.0 - 0.5) * 3.6;
            double d5 = ((double)this.nextInt(1024) / 1024.0 - 0.5) * 3.6 + 4.0;
            this.initChunkSeed((long)(l1 + i + 1 << 2), (long)(k1 + j + 1 << 2));
            double d6 = ((double)this.nextInt(1024) / 1024.0 - 0.5) * 3.6 + 4.0;
            double d7 = ((double)this.nextInt(1024) / 1024.0 - 0.5) * 3.6 + 4.0;
            int k2 = aint[l1 + 1 + (k1 + 0) * k] & 0xFF;
            int l2 = aint[l1 + 1 + (k1 + 1) * k] & 0xFF;

            for (int i3 = 0; i3 < 4; i3++) {
               int j3 = ((k1 << 2) + i3) * i1 + (l1 << 2);

               for (int k3 = 0; k3 < 4; k3++) {
                  double d8 = ((double)i3 - d1) * ((double)i3 - d1) + ((double)k3 - d0) * ((double)k3 - d0);
                  double d9 = ((double)i3 - d3) * ((double)i3 - d3) + ((double)k3 - d2) * ((double)k3 - d2);
                  double d10 = ((double)i3 - d5) * ((double)i3 - d5) + ((double)k3 - d4) * ((double)k3 - d4);
                  double d11 = ((double)i3 - d7) * ((double)i3 - d7) + ((double)k3 - d6) * ((double)k3 - d6);
                  if (d8 < d9 && d8 < d10 && d8 < d11) {
                     aint1[j3++] = i2;
                  } else if (d9 < d8 && d9 < d10 && d9 < d11) {
                     aint1[j3++] = k2;
                  } else if (d10 < d8 && d10 < d9 && d10 < d11) {
                     aint1[j3++] = j2;
                  } else {
                     aint1[j3++] = l2;
                  }
               }
            }

            i2 = k2;
            j2 = l2;
         }
      }

      int[] aint2 = IntCache.getIntCache(areaWidth * areaHeight);

      for (int i4 = 0; i4 < areaHeight; i4++) {
         System.arraycopy(aint1, (i4 + (areaY & 3)) * i1 + (areaX & 3), aint2, i4 * areaWidth, areaWidth);
      }

      return aint2;
   }
}
