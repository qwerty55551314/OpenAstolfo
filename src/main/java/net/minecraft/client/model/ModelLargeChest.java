package net.minecraft.client.model;

public class ModelLargeChest extends ModelChest {
   public ModelLargeChest() {
      super.chestLid = new ModelRenderer(this, 0, 0).setTextureSize(128, 64);
      super.chestLid.addBox(0.0F, -5.0F, -14.0F, 30, 5, 14, 0.0F);
      super.chestLid.rotationPointX = 1.0F;
      super.chestLid.rotationPointY = 7.0F;
      super.chestLid.rotationPointZ = 15.0F;
      super.chestKnob = new ModelRenderer(this, 0, 0).setTextureSize(128, 64);
      super.chestKnob.addBox(-1.0F, -2.0F, -15.0F, 2, 4, 1, 0.0F);
      super.chestKnob.rotationPointX = 16.0F;
      super.chestKnob.rotationPointY = 7.0F;
      super.chestKnob.rotationPointZ = 15.0F;
      super.chestBelow = new ModelRenderer(this, 0, 19).setTextureSize(128, 64);
      super.chestBelow.addBox(0.0F, 0.0F, 0.0F, 30, 10, 14, 0.0F);
      super.chestBelow.rotationPointX = 1.0F;
      super.chestBelow.rotationPointY = 6.0F;
      super.chestBelow.rotationPointZ = 1.0F;
   }
}
