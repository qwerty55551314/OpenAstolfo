package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;

public class LayerCreeperCharge implements LayerRenderer<EntityCreeper> {
   private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
   private final RenderCreeper creeperRenderer;
   private final ModelCreeper creeperModel = new ModelCreeper(2.0F);

   public LayerCreeperCharge(RenderCreeper creeperRendererIn) {
      this.creeperRenderer = creeperRendererIn;
   }

   public void doRenderLayer(EntityCreeper entitylivingbaseIn, float f, float g, float partialTicks, float h, float f2, float f3, float scale) {
      boolean flag1 = entitylivingbaseIn.getPowered();
      if (flag1) {
         boolean flag = entitylivingbaseIn.isInvisible();
         GlStateManager.depthMask(!flag);
         this.creeperRenderer.bindTexture(LIGHTNING_TEXTURE);
         GlStateManager.matrixMode(5890);
         GlStateManager.loadIdentity();
         float f1 = (float)entitylivingbaseIn.ticksExisted + partialTicks;
         GlStateManager.translate(f1 * 0.01F, f1 * 0.01F, 0.0F);
         GlStateManager.matrixMode(5888);
         GlStateManager.enableBlend();
         float f1x = 0.5F;
         GlStateManager.color(f1x, f1x, f1x, 1.0F);
         GlStateManager.disableLighting();
         GlStateManager.blendFunc(1, 1);
         this.creeperModel.setModelAttributes(this.creeperRenderer.getMainModel());
         this.creeperModel.render(entitylivingbaseIn, f, g, h, f2, f3, scale);
         GlStateManager.matrixMode(5890);
         GlStateManager.loadIdentity();
         GlStateManager.matrixMode(5888);
         GlStateManager.enableLighting();
         GlStateManager.disableBlend();
         GlStateManager.depthMask(flag);
      }
   }

   @Override
   public boolean shouldCombineTextures() {
      return false;
   }
}
