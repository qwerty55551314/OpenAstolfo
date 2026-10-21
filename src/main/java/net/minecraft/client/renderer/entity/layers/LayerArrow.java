package net.minecraft.client.renderer.entity.layers;

import java.util.Random;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.MathHelper;

public class LayerArrow implements LayerRenderer<EntityLivingBase> {
   private final RendererLivingEntity field_177168_a;

   public LayerArrow(RendererLivingEntity rendererLivingEntity) {
      this.field_177168_a = rendererLivingEntity;
   }

   @Override
   public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float f, float g, float partialTicks, float h, float f2, float f3, float scale) {
      String[] astring = new String[0];
      int i = entitylivingbaseIn.getArrowCountInEntity();
      if (i > 0) {
         EntityArrow entityarrow = new EntityArrow(entitylivingbaseIn.worldObj, entitylivingbaseIn.posX, entitylivingbaseIn.posY, entitylivingbaseIn.posZ);
         Random random = new Random((long)entitylivingbaseIn.getEntityId());
         RenderHelper.disableStandardItemLighting();

         for (int j = 0; j < i; j++) {
            GlStateManager.pushMatrix();
            ModelRenderer modelrenderer = this.field_177168_a.getMainModel().getRandomModelBox(random);
            ModelBox modelbox = (ModelBox)modelrenderer.cubeList.get(random.nextInt(modelrenderer.cubeList.size()));
            modelrenderer.postRender(0.0625F);
            float f1 = random.nextFloat();
            float f1x = random.nextFloat();
            float f21 = random.nextFloat();
            float f31 = (modelbox.posX1 + (modelbox.posX2 - modelbox.posX1) * f1) / 16.0F;
            float f4 = (modelbox.posY1 + (modelbox.posY2 - modelbox.posY1) * f1x) / 16.0F;
            float f5 = (modelbox.posZ1 + (modelbox.posZ2 - modelbox.posZ1) * f21) / 16.0F;
            GlStateManager.translate(f31, f4, f5);
            f1 = f1 * 2.0F - 1.0F;
            f1x = f1x * 2.0F - 1.0F;
            f21 = f21 * 2.0F - 1.0F;
            f1 *= -1.0F;
            f1x *= -1.0F;
            f21 *= -1.0F;
            float f6 = MathHelper.sqrt_float(f1 * f1 + f21 * f21);
            entityarrow.prevRotationYaw = entityarrow.rotationYaw = (float)(Math.atan2((double)f1, (double)f21) * 180.0 / Math.PI);
            entityarrow.prevRotationPitch = entityarrow.rotationPitch = (float)(Math.atan2((double)f1x, (double)f6) * 180.0 / Math.PI);
            double d0 = 0.0;
            double d1 = 0.0;
            double d2 = 0.0;
            this.field_177168_a.getRenderManager().renderEntityWithPosYaw(entityarrow, d0, d1, d2, 0.0F, partialTicks);
            GlStateManager.popMatrix();
         }

         RenderHelper.enableStandardItemLighting();
      }
   }

   @Override
   public boolean shouldCombineTextures() {
      return false;
   }
}
