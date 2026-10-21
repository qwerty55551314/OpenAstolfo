package shadersmod.client;

import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.util.MathHelper;

public class ClippingHelperShadow extends ClippingHelper {
   private static ClippingHelperShadow instance = new ClippingHelperShadow();
   public float[] frustumTest = new float[6];
   public float[][] shadowClipPlanes = new float[10][4];
   public int shadowClipPlaneCount;
   public float[] matInvMP = new float[16];
   public float[] vecIntersection = new float[4];

   @Override
   public boolean isBoxInFrustum(double d, double e, double d2, double d3, double d4, double d5) {
      for (int i = 0; i < this.shadowClipPlaneCount; i++) {
         float[] afloat = this.shadowClipPlanes[i];
         if (this.dot4(afloat, d, e, d2) <= 0.0
            && this.dot4(afloat, d3, e, d2) <= 0.0
            && this.dot4(afloat, d, d4, d2) <= 0.0
            && this.dot4(afloat, d3, d4, d2) <= 0.0
            && this.dot4(afloat, d, e, d5) <= 0.0
            && this.dot4(afloat, d3, e, d5) <= 0.0
            && this.dot4(afloat, d, d4, d5) <= 0.0
            && this.dot4(afloat, d3, d4, d5) <= 0.0) {
            return false;
         }
      }

      return true;
   }

   private double dot4(float[] plane, double x, double y, double z) {
      return (double)plane[0] * x + (double)plane[1] * y + (double)plane[2] * z + (double)plane[3];
   }

   private double dot3(float[] vecA, float[] vecB) {
      return (double)vecA[0] * (double)vecB[0] + (double)vecA[1] * (double)vecB[1] + (double)vecA[2] * (double)vecB[2];
   }

   public static ClippingHelper getInstance() {
      instance.init();
      return instance;
   }

   private void normalize3(float[] plane) {
      float f = MathHelper.sqrt_float(plane[0] * plane[0] + plane[1] * plane[1] + plane[2] * plane[2]);
      plane[0] /= f;
      plane[1] /= f;
      plane[2] /= f;
      plane[3] /= f;
   }

   private void i(float[] fArray) {
      float f = MathHelper.sqrt_float(fArray[0] * fArray[0] + fArray[1] * fArray[1] + fArray[2] * fArray[2]);
      if (f == 0.0F) {
         f = 1.0F;
      }

      fArray[0] /= f;
      fArray[1] /= f;
      fArray[2] /= f;
   }

   private void assignPlane(float[] plane, float a, float b, float c, float d) {
      float f = (float)Math.sqrt((double)(a * a + b * b + c * c));
      plane[0] = a / f;
      plane[1] = b / f;
      plane[2] = c / f;
      plane[3] = d / f;
   }

   private void copyPlane(float[] dst, float[] src) {
      dst[0] = src[0];
      dst[1] = src[1];
      dst[2] = src[2];
      dst[3] = src[3];
   }

   private void cross3(float[] out, float[] a, float[] b) {
      out[0] = a[1] * b[2] - a[2] * b[1];
      out[1] = a[2] * b[0] - a[0] * b[2];
      out[2] = a[0] * b[1] - a[1] * b[0];
   }

   private void addShadowClipPlane(float[] fArray) {
      this.copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], fArray);
   }

   private float length(float x, float y, float z) {
      return (float)Math.sqrt((double)(x * x + y * y + z * z));
   }

   private float distance(float x1, float y1, float z1, float x2, float y2, float z2) {
      return this.length(x1 - x2, y1 - y2, z1 - z2);
   }

   private void makeShadowPlane(float[] shadowPlane, float[] positivePlane, float[] negativePlane, float[] vecSun) {
      this.cross3(this.vecIntersection, positivePlane, negativePlane);
      this.cross3(shadowPlane, this.vecIntersection, vecSun);
      this.i(shadowPlane);
      float f = (float)this.dot3(positivePlane, negativePlane);
      float f1 = (float)this.dot3(shadowPlane, negativePlane);
      float f2 = this.distance(shadowPlane[0], shadowPlane[1], shadowPlane[2], negativePlane[0] * f1, negativePlane[1] * f1, negativePlane[2] * f1);
      float f3 = this.distance(positivePlane[0], positivePlane[1], positivePlane[2], negativePlane[0] * f, negativePlane[1] * f, negativePlane[2] * f);
      float f4 = f2 / f3;
      float f5 = (float)this.dot3(shadowPlane, positivePlane);
      float f6 = this.distance(shadowPlane[0], shadowPlane[1], shadowPlane[2], positivePlane[0] * f5, positivePlane[1] * f5, positivePlane[2] * f5);
      float f7 = this.distance(negativePlane[0], negativePlane[1], negativePlane[2], positivePlane[0] * f, positivePlane[1] * f, positivePlane[2] * f);
      float f8 = f6 / f7;
      shadowPlane[3] = positivePlane[3] * f4 + negativePlane[3] * f8;
   }

   public void init() {
      float[] afloat = super.projectionMatrix;
      float[] afloat1 = super.modelviewMatrix;
      float[] afloat2 = super.clippingMatrix;
      System.arraycopy(Shaders.faProjection, 0, afloat, 0, 16);
      System.arraycopy(Shaders.faModelView, 0, afloat1, 0, 16);
      SMath.multiplyMat4xMat4(afloat2, afloat1, afloat);
      this.assignPlane(super.frustum[0], afloat2[3] - afloat2[0], afloat2[7] - afloat2[4], afloat2[11] - afloat2[8], afloat2[15] - afloat2[12]);
      this.assignPlane(super.frustum[1], afloat2[3] + afloat2[0], afloat2[7] + afloat2[4], afloat2[11] + afloat2[8], afloat2[15] + afloat2[12]);
      this.assignPlane(super.frustum[2], afloat2[3] + afloat2[1], afloat2[7] + afloat2[5], afloat2[11] + afloat2[9], afloat2[15] + afloat2[13]);
      this.assignPlane(super.frustum[3], afloat2[3] - afloat2[1], afloat2[7] - afloat2[5], afloat2[11] - afloat2[9], afloat2[15] - afloat2[13]);
      this.assignPlane(super.frustum[4], afloat2[3] - afloat2[2], afloat2[7] - afloat2[6], afloat2[11] - afloat2[10], afloat2[15] - afloat2[14]);
      this.assignPlane(super.frustum[5], afloat2[3] + afloat2[2], afloat2[7] + afloat2[6], afloat2[11] + afloat2[10], afloat2[15] + afloat2[14]);
      float[] afloat3 = Shaders.shadowLightPositionVector;
      float f = (float)this.dot3(super.frustum[0], afloat3);
      float f1 = (float)this.dot3(super.frustum[1], afloat3);
      float f2 = (float)this.dot3(super.frustum[2], afloat3);
      float f3 = (float)this.dot3(super.frustum[3], afloat3);
      float f4 = (float)this.dot3(super.frustum[4], afloat3);
      float f5 = (float)this.dot3(super.frustum[5], afloat3);
      this.shadowClipPlaneCount = 0;
      float f6;
      int i = (f6 = f - 0.0F) == 0.0F ? 0 : (f6 < 0.0F ? -1 : 1);
      if (i >= 0) {
         this.copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], super.frustum[0]);
         if (f > 0.0F) {
            if (f2 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], super.frustum[0], super.frustum[2], afloat3);
            }

            if (f3 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], super.frustum[0], super.frustum[3], afloat3);
            }

            if (f4 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], super.frustum[0], super.frustum[4], afloat3);
            }

            if (f5 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], super.frustum[0], super.frustum[5], afloat3);
            }
         }
      }

      float f7;
      int j = (f7 = f1 - 0.0F) == 0.0F ? 0 : (f7 < 0.0F ? -1 : 1);
      if (j >= 0) {
         this.copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], super.frustum[1]);
         if (f1 > 0.0F) {
            if (f2 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], super.frustum[1], super.frustum[2], afloat3);
            }

            if (f3 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], super.frustum[1], super.frustum[3], afloat3);
            }

            if (f4 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], super.frustum[1], super.frustum[4], afloat3);
            }

            if (f5 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], super.frustum[1], super.frustum[5], afloat3);
            }
         }
      }

      float f8;
      int k = (f8 = f2 - 0.0F) == 0.0F ? 0 : (f8 < 0.0F ? -1 : 1);
      if (k >= 0) {
         this.copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], super.frustum[2]);
         if (f2 > 0.0F) {
            if (f < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], super.frustum[2], super.frustum[0], afloat3);
            }

            if (f1 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], super.frustum[2], super.frustum[1], afloat3);
            }

            if (f4 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], super.frustum[2], super.frustum[4], afloat3);
            }

            if (f5 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], super.frustum[2], super.frustum[5], afloat3);
            }
         }
      }

      float f9;
      int l = (f9 = f3 - 0.0F) == 0.0F ? 0 : (f9 < 0.0F ? -1 : 1);
      if (l >= 0) {
         this.copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], super.frustum[3]);
         if (f3 > 0.0F) {
            if (f < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], super.frustum[3], super.frustum[0], afloat3);
            }

            if (f1 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], super.frustum[3], super.frustum[1], afloat3);
            }

            if (f4 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], super.frustum[3], super.frustum[4], afloat3);
            }

            if (f5 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], super.frustum[3], super.frustum[5], afloat3);
            }
         }
      }

      float f10;
      int i1 = (f10 = f4 - 0.0F) == 0.0F ? 0 : (f10 < 0.0F ? -1 : 1);
      if (i1 >= 0) {
         this.copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], super.frustum[4]);
         if (f4 > 0.0F) {
            if (f < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], super.frustum[4], super.frustum[0], afloat3);
            }

            if (f1 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], super.frustum[4], super.frustum[1], afloat3);
            }

            if (f2 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], super.frustum[4], super.frustum[2], afloat3);
            }

            if (f3 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], super.frustum[4], super.frustum[3], afloat3);
            }
         }
      }

      float f11;
      int j1 = (f11 = f5 - 0.0F) == 0.0F ? 0 : (f11 < 0.0F ? -1 : 1);
      if (j1 >= 0) {
         this.copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], super.frustum[5]);
         float f12;
         int k1 = (f12 = f5 - 0.0F) == 0.0F ? 0 : (f12 < 0.0F ? -1 : 1);
         if (k1 > 0) {
            float f13;
            int l1 = (f13 = f - 0.0F) == 0.0F ? 0 : (f13 < 0.0F ? -1 : 1);
            if (l1 < 0) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], super.frustum[5], super.frustum[0], afloat3);
            }

            float f14;
            int i2 = (f14 = f1 - 0.0F) == 0.0F ? 0 : (f14 < 0.0F ? -1 : 1);
            if (i2 < 0) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], super.frustum[5], super.frustum[1], afloat3);
            }

            float f15;
            int j2 = (f15 = f2 - 0.0F) == 0.0F ? 0 : (f15 < 0.0F ? -1 : 1);
            if (j2 < 0) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], super.frustum[5], super.frustum[2], afloat3);
            }

            if (f3 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], super.frustum[5], super.frustum[3], afloat3);
            }
         }
      }
   }
}
