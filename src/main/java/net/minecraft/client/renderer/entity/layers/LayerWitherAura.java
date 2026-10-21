package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ModelWither;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderWither;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class LayerWitherAura implements LayerRenderer<EntityWither> {
   private static final ResourceLocation WITHER_ARMOR = new ResourceLocation("textures/entity/wither/wither_armor.png");
   private final RenderWither witherRenderer;
   private final ModelWither witherModel = new ModelWither(0.5F);

   public LayerWitherAura(RenderWither witherRendererIn) {
      this.witherRenderer = witherRendererIn;
   }

   public void doRenderLayer(EntityWither entitylivingbaseIn, float f, float g, float partialTicks, float h, float f2, float f3, float scale) {
      boolean flag = entitylivingbaseIn.isArmored();
      if (flag) {
         boolean flag1 = entitylivingbaseIn.isInvisible();
         GlStateManager.depthMask(!flag1);
         this.witherRenderer.bindTexture(WITHER_ARMOR);
         GlStateManager.matrixMode(5890);
         GlStateManager.loadIdentity();
         float f1 = (float)entitylivingbaseIn.ticksExisted + partialTicks;
         float f1x = MathHelper.cos(f1 * 0.02F) * 3.0F;
         float f21 = f1 * 0.01F;
         GlStateManager.translate(f1x, f21, 0.0F);
         GlStateManager.matrixMode(5888);
         GlStateManager.enableBlend();
         float f31 = 0.5F;
         GlStateManager.color(f31, f31, f31, 1.0F);
         GlStateManager.disableLighting();
         GlStateManager.blendFunc(1, 1);
         this.witherModel.setLivingAnimations(entitylivingbaseIn, f, g, partialTicks);
         this.witherModel.setModelAttributes(this.witherRenderer.getMainModel());
         this.witherModel.render(entitylivingbaseIn, f, g, h, f2, f3, scale);
         GlStateManager.matrixMode(5890);
         GlStateManager.loadIdentity();
         GlStateManager.matrixMode(5888);
         GlStateManager.enableLighting();
         GlStateManager.disableBlend();
      }
   }

   @Override
   public boolean shouldCombineTextures() {
      return false;
   }
}
