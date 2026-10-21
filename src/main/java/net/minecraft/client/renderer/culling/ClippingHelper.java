package net.minecraft.client.renderer.culling;

public class ClippingHelper {
   public float[][] frustum = new float[6][4];
   public float[] projectionMatrix = new float[16];
   public float[] modelviewMatrix = new float[16];
   public float[] clippingMatrix = new float[16];
   private static int X;

   private float dot(float[] floats, float f, float g, float h) {
      return floats[0] * f + floats[1] * g + floats[2] * h + floats[3];
   }

   public boolean isBoxInFrustum(double d, double e, double d2, double d3, double d4, double d5) {
      float f = (float)d;
      float f1 = (float)e;
      float f2 = (float)d2;
      float f3 = (float)d3;
      float f4 = (float)d4;
      float f5 = (float)d5;

      for (int i = 0; i < 6; i++) {
         float[] afloat = this.frustum[i];
         if (this.dot(afloat, f, f1, f2) <= 0.0F
            && this.dot(afloat, f3, f1, f2) <= 0.0F
            && this.dot(afloat, f, f4, f2) <= 0.0F
            && this.dot(afloat, f3, f4, f2) <= 0.0F
            && this.dot(afloat, f, f1, f5) <= 0.0F
            && this.dot(afloat, f3, f1, f5) <= 0.0F
            && this.dot(afloat, f, f4, f5) <= 0.0F
            && this.dot(afloat, f3, f4, f5) <= 0.0F) {
            return false;
         }
      }

      return true;
   }

   public static int i() {
      return 61;
   }
}
