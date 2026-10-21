package net.minecraft.client.renderer.culling;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;

public class ClippingHelperImpl extends ClippingHelper {
   private static ClippingHelperImpl instance = new ClippingHelperImpl();
   private FloatBuffer projectionMatrixBuffer = GLAllocation.createDirectFloatBuffer(16);
   private FloatBuffer modelviewMatrixBuffer = GLAllocation.createDirectFloatBuffer(16);
   private FloatBuffer field_78564_h = GLAllocation.createDirectFloatBuffer(16);

   public static ClippingHelper getInstance() {
      instance.init();
      return instance;
   }

   private void normalize(float[] floats) {
      float f = MathHelper.sqrt_float(floats[0] * floats[0] + floats[1] * floats[1] + floats[2] * floats[2]);
      floats[0] /= f;
      floats[1] /= f;
      floats[2] /= f;
      floats[3] /= f;
   }

   public void init() {
      ((Buffer)this.projectionMatrixBuffer).clear();
      ((Buffer)this.modelviewMatrixBuffer).clear();
      ((Buffer)this.field_78564_h).clear();
      GlStateManager.getFloat(2983, this.projectionMatrixBuffer);
      GlStateManager.getFloat(2982, this.modelviewMatrixBuffer);
      float[] afloat = super.projectionMatrix;
      float[] afloat1 = super.modelviewMatrix;
      ((Buffer)this.projectionMatrixBuffer).flip().limit(16);
      this.projectionMatrixBuffer.get(afloat);
      ((Buffer)this.modelviewMatrixBuffer).flip().limit(16);
      this.modelviewMatrixBuffer.get(afloat1);
      super.clippingMatrix[0] = afloat1[0] * afloat[0] + afloat1[1] * afloat[4] + afloat1[2] * afloat[8] + afloat1[3] * afloat[12];
      super.clippingMatrix[1] = afloat1[0] * afloat[1] + afloat1[1] * afloat[5] + afloat1[2] * afloat[9] + afloat1[3] * afloat[13];
      super.clippingMatrix[2] = afloat1[0] * afloat[2] + afloat1[1] * afloat[6] + afloat1[2] * afloat[10] + afloat1[3] * afloat[14];
      super.clippingMatrix[3] = afloat1[0] * afloat[3] + afloat1[1] * afloat[7] + afloat1[2] * afloat[11] + afloat1[3] * afloat[15];
      super.clippingMatrix[4] = afloat1[4] * afloat[0] + afloat1[5] * afloat[4] + afloat1[6] * afloat[8] + afloat1[7] * afloat[12];
      super.clippingMatrix[5] = afloat1[4] * afloat[1] + afloat1[5] * afloat[5] + afloat1[6] * afloat[9] + afloat1[7] * afloat[13];
      super.clippingMatrix[6] = afloat1[4] * afloat[2] + afloat1[5] * afloat[6] + afloat1[6] * afloat[10] + afloat1[7] * afloat[14];
      super.clippingMatrix[7] = afloat1[4] * afloat[3] + afloat1[5] * afloat[7] + afloat1[6] * afloat[11] + afloat1[7] * afloat[15];
      super.clippingMatrix[8] = afloat1[8] * afloat[0] + afloat1[9] * afloat[4] + afloat1[10] * afloat[8] + afloat1[11] * afloat[12];
      super.clippingMatrix[9] = afloat1[8] * afloat[1] + afloat1[9] * afloat[5] + afloat1[10] * afloat[9] + afloat1[11] * afloat[13];
      super.clippingMatrix[10] = afloat1[8] * afloat[2] + afloat1[9] * afloat[6] + afloat1[10] * afloat[10] + afloat1[11] * afloat[14];
      super.clippingMatrix[11] = afloat1[8] * afloat[3] + afloat1[9] * afloat[7] + afloat1[10] * afloat[11] + afloat1[11] * afloat[15];
      super.clippingMatrix[12] = afloat1[12] * afloat[0] + afloat1[13] * afloat[4] + afloat1[14] * afloat[8] + afloat1[15] * afloat[12];
      super.clippingMatrix[13] = afloat1[12] * afloat[1] + afloat1[13] * afloat[5] + afloat1[14] * afloat[9] + afloat1[15] * afloat[13];
      super.clippingMatrix[14] = afloat1[12] * afloat[2] + afloat1[13] * afloat[6] + afloat1[14] * afloat[10] + afloat1[15] * afloat[14];
      super.clippingMatrix[15] = afloat1[12] * afloat[3] + afloat1[13] * afloat[7] + afloat1[14] * afloat[11] + afloat1[15] * afloat[15];
      float[] afloat2 = super.frustum[0];
      afloat2[0] = super.clippingMatrix[3] - super.clippingMatrix[0];
      afloat2[1] = super.clippingMatrix[7] - super.clippingMatrix[4];
      afloat2[2] = super.clippingMatrix[11] - super.clippingMatrix[8];
      afloat2[3] = super.clippingMatrix[15] - super.clippingMatrix[12];
      this.normalize(afloat2);
      float[] afloat3 = super.frustum[1];
      afloat3[0] = super.clippingMatrix[3] + super.clippingMatrix[0];
      afloat3[1] = super.clippingMatrix[7] + super.clippingMatrix[4];
      afloat3[2] = super.clippingMatrix[11] + super.clippingMatrix[8];
      afloat3[3] = super.clippingMatrix[15] + super.clippingMatrix[12];
      this.normalize(afloat3);
      float[] afloat4 = super.frustum[2];
      afloat4[0] = super.clippingMatrix[3] + super.clippingMatrix[1];
      afloat4[1] = super.clippingMatrix[7] + super.clippingMatrix[5];
      afloat4[2] = super.clippingMatrix[11] + super.clippingMatrix[9];
      afloat4[3] = super.clippingMatrix[15] + super.clippingMatrix[13];
      this.normalize(afloat4);
      float[] afloat5 = super.frustum[3];
      afloat5[0] = super.clippingMatrix[3] - super.clippingMatrix[1];
      afloat5[1] = super.clippingMatrix[7] - super.clippingMatrix[5];
      afloat5[2] = super.clippingMatrix[11] - super.clippingMatrix[9];
      afloat5[3] = super.clippingMatrix[15] - super.clippingMatrix[13];
      this.normalize(afloat5);
      float[] afloat6 = super.frustum[4];
      afloat6[0] = super.clippingMatrix[3] - super.clippingMatrix[2];
      afloat6[1] = super.clippingMatrix[7] - super.clippingMatrix[6];
      afloat6[2] = super.clippingMatrix[11] - super.clippingMatrix[10];
      afloat6[3] = super.clippingMatrix[15] - super.clippingMatrix[14];
      this.normalize(afloat6);
      float[] afloat7 = super.frustum[5];
      afloat7[0] = super.clippingMatrix[3] + super.clippingMatrix[2];
      afloat7[1] = super.clippingMatrix[7] + super.clippingMatrix[6];
      afloat7[2] = super.clippingMatrix[11] + super.clippingMatrix[10];
      afloat7[3] = super.clippingMatrix[15] + super.clippingMatrix[14];
      this.normalize(afloat7);
   }
}
