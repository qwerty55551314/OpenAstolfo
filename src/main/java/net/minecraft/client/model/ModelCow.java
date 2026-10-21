package net.minecraft.client.model;

public class ModelCow extends ModelQuadruped {
   public ModelCow() {
      super(12, 0.0F);
      super.head = new ModelRenderer(this, 0, 0);
      super.head.addBox(-4.0F, -4.0F, -6.0F, 8, 8, 6, 0.0F);
      super.head.setRotationPoint(0.0F, 4.0F, -8.0F);
      super.head.setTextureOffset(22, 0).addBox(-5.0F, -5.0F, -4.0F, 1, 3, 1, 0.0F);
      super.head.setTextureOffset(22, 0).addBox(4.0F, -5.0F, -4.0F, 1, 3, 1, 0.0F);
      super.body = new ModelRenderer(this, 18, 4);
      super.body.addBox(-6.0F, -10.0F, -7.0F, 12, 18, 10, 0.0F);
      super.body.setRotationPoint(0.0F, 5.0F, 2.0F);
      super.body.setTextureOffset(52, 0).addBox(-2.0F, 2.0F, -8.0F, 4, 6, 1);
      super.leg1.rotationPointX--;
      super.leg2.rotationPointX++;
      super.leg1.rotationPointZ += 0.0F;
      super.leg2.rotationPointZ += 0.0F;
      super.leg3.rotationPointX--;
      super.leg4.rotationPointX++;
      super.leg3.rotationPointZ--;
      super.leg4.rotationPointZ--;
      super.childZOffset += 2.0F;
   }
}
