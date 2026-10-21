package net.minecraft.client.renderer.entity.layers;

import java.util.Random;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.boss.EntityDragon;

public class LayerEnderDragonDeath implements LayerRenderer<EntityDragon> {
   public void doRenderLayer(EntityDragon entitylivingbaseIn, float f, float g, float partialTicks, float h, float f2, float f3, float scale) {
      if (entitylivingbaseIn.deathTicks > 0) {
         Tessellator tessellator = Tessellator.getInstance();
         WorldRenderer worldrenderer = tessellator.getWorldRenderer();
         RenderHelper.disableStandardItemLighting();
         float f1 = ((float)entitylivingbaseIn.deathTicks + partialTicks) / 200.0F;
         float f1x = 0.0F;
         if (f1 > 0.8F) {
            f1x = (f1 - 0.8F) / 0.2F;
         }

         Random random = new Random(432L);
         GlStateManager.disableTexture2D();
         GlStateManager.shadeModel(7425);
         GlStateManager.enableBlend();
         GlStateManager.blendFunc(770, 1);
         GlStateManager.disableAlpha();
         GlStateManager.enableCull();
         GlStateManager.depthMask(false);
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, -1.0F, -2.0F);

         for (int i = 0; (float)i < (f1 + f1 * f1) / 2.0F * 60.0F; i++) {
            GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(random.nextFloat() * 360.0F + f1 * 90.0F, 0.0F, 0.0F, 1.0F);
            float f21 = random.nextFloat() * 20.0F + 5.0F + f1x * 10.0F;
            float f31 = random.nextFloat() * 2.0F + 1.0F + f1x * 2.0F;
            worldrenderer.begin(6, DefaultVertexFormats.POSITION_COLOR);
            worldrenderer.pos(0.0, 0.0, 0.0).color(255, 255, 255, (int)(255.0F * (1.0F - f1x))).endVertex();
            worldrenderer.pos(-0.866 * (double)f31, (double)f21, (double)(-0.5F * f31)).color(255, 0, 255, 0).endVertex();
            worldrenderer.pos(0.866 * (double)f31, (double)f21, (double)(-0.5F * f31)).color(255, 0, 255, 0).endVertex();
            worldrenderer.pos(0.0, (double)f21, (double)(1.0F * f31)).color(255, 0, 255, 0).endVertex();
            worldrenderer.pos(-0.866 * (double)f31, (double)f21, (double)(-0.5F * f31)).color(255, 0, 255, 0).endVertex();
            tessellator.draw();
         }

         GlStateManager.popMatrix();
         GlStateManager.depthMask(true);
         GlStateManager.disableCull();
         GlStateManager.disableBlend();
         GlStateManager.shadeModel(7424);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.enableTexture2D();
         GlStateManager.enableAlpha();
         RenderHelper.enableStandardItemLighting();
      }
   }

   @Override
   public boolean shouldCombineTextures() {
      return false;
   }
}
