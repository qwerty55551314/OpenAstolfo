package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RendererLivingEntity;

public class LayerBipedArmor extends LayerArmorBase<ModelBiped> {
   public LayerBipedArmor(RendererLivingEntity<?> rendererIn) {
      super(rendererIn);
   }

   @Override
   public void initArmor() {
      super.modelLeggings = new ModelBiped(0.5F);
      super.modelArmor = new ModelBiped(1.0F);
   }

   public void setModelPartVisible(ModelBiped model, int armorSlot) {
      String[] astring = new String[0];
      this.setModelVisible(model);
      switch (armorSlot) {
         case 1:
            model.bipedRightLeg.showModel = true;
            model.bipedLeftLeg.showModel = true;
            break;
         case 2:
            model.bipedBody.showModel = true;
            model.bipedRightLeg.showModel = true;
            model.bipedLeftLeg.showModel = true;
            break;
         case 3:
            model.bipedBody.showModel = true;
            model.bipedRightArm.showModel = true;
            model.bipedLeftArm.showModel = true;
            break;
         case 4:
            model.bipedHead.showModel = true;
            model.bipedHeadwear.showModel = true;
      }
   }

   public void setModelVisible(ModelBiped model) {
      model.setInvisible(false);
   }
}
