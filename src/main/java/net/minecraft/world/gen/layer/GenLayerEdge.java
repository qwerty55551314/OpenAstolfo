package net.minecraft.world.gen.layer;

public class GenLayerEdge extends GenLayer {
   private final GenLayerEdge.Mode field_151627_c;

   public GenLayerEdge(long l, GenLayer genLayer, GenLayerEdge.Mode mode) {
      super(l);
      super.parent = genLayer;
      this.field_151627_c = mode;
   }

   @Override
   public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
      switch (this.field_151627_c) {
         case COOL_WARM:
         default:
            return this.getIntsCoolWarm(areaX, areaY, areaWidth, areaHeight);
         case HEAT_ICE:
            return this.getIntsHeatIce(areaX, areaY, areaWidth, areaHeight);
         case SPECIAL:
            return this.getIntsSpecial(areaX, areaY, areaWidth, areaHeight);
      }
   }

   private int[] getIntsCoolWarm(int i, int j, int k, int l) {
      int i1 = i - 1;
      int j1 = j - 1;
      int k1 = 1 + k + 1;
      int l1 = 1 + l + 1;
      int[] aint = super.parent.getInts(i1, j1, k1, l1);
      int[] aint1 = IntCache.getIntCache(k * l);

      for (int i1x = 0; i1x < l; i1x++) {
         for (int j1x = 0; j1x < k; j1x++) {
            this.initChunkSeed((long)(j1x + i), (long)(i1x + j));
            int k1x = aint[j1x + 1 + (i1x + 1) * k1];
            if (k1x == 1) {
               int l1x = aint[j1x + 1 + (i1x + 1 - 1) * k1];
               int i2 = aint[j1x + 1 + 1 + (i1x + 1) * k1];
               int j2 = aint[j1x + 1 - 1 + (i1x + 1) * k1];
               int k2 = aint[j1x + 1 + (i1x + 1 + 1) * k1];
               boolean flag = l1x == 3 || i2 == 3 || j2 == 3 || k2 == 3;
               boolean flag1 = l1x == 4 || i2 == 4 || j2 == 4 || k2 == 4;
               if (flag || flag1) {
                  k1x = 2;
               }
            }

            aint1[j1x + i1x * k] = k1x;
         }
      }

      return aint1;
   }

   private int[] getIntsHeatIce(int i, int j, int k, int l) {
      int i1 = i - 1;
      int j1 = j - 1;
      int k1 = 1 + k + 1;
      int l1 = 1 + l + 1;
      int[] aint = super.parent.getInts(i1, j1, k1, l1);
      int[] aint1 = IntCache.getIntCache(k * l);

      for (int i1x = 0; i1x < l; i1x++) {
         for (int j1x = 0; j1x < k; j1x++) {
            int k1x = aint[j1x + 1 + (i1x + 1) * k1];
            if (k1x == 4) {
               int l1x = aint[j1x + 1 + (i1x + 1 - 1) * k1];
               int i2 = aint[j1x + 1 + 1 + (i1x + 1) * k1];
               int j2 = aint[j1x + 1 - 1 + (i1x + 1) * k1];
               int k2 = aint[j1x + 1 + (i1x + 1 + 1) * k1];
               boolean flag = l1x == 2 || i2 == 2 || j2 == 2 || k2 == 2;
               boolean flag1 = l1x == 1 || i2 == 1 || j2 == 1 || k2 == 1;
               if (flag1 || flag) {
                  k1x = 3;
               }
            }

            aint1[j1x + i1x * k] = k1x;
         }
      }

      return aint1;
   }

   private int[] getIntsSpecial(int i, int j, int k, int l) {
      int[] aint = super.parent.getInts(i, j, k, l);
      int[] aint1 = IntCache.getIntCache(k * l);

      for (int i1 = 0; i1 < l; i1++) {
         for (int j1 = 0; j1 < k; j1++) {
            this.initChunkSeed((long)(j1 + i), (long)(i1 + j));
            int k1 = aint[j1 + i1 * k];
            if (k1 != 0 && this.nextInt(13) == 0) {
               k1 |= 1 + this.nextInt(15) << 8 & 3840;
            }

            aint1[j1 + i1 * k] = k1;
         }
      }

      return aint1;
   }

   public static enum Mode {
      COOL_WARM,
      HEAT_ICE,
      SPECIAL;
   }
}
