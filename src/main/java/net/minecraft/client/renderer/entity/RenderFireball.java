package net.minecraft.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;

public class RenderFireball extends Render<EntityFireball> {
   private float scale;
   private static final long c = 7684220190791303175L;

   public RenderFireball(RenderManager renderManagerIn, float scaleIn) {
      super(renderManagerIn);
      this.scale = scaleIn;
   }

   public void doRender(EntityFireball entity, double x, double y, double z, float entityYaw, float partialTicks) {
      GlStateManager.pushMatrix();
      this.bindEntityTexture(entity);
      GlStateManager.translate((float)x, (float)y, (float)z);
      GlStateManager.enableRescaleNormal();
      GlStateManager.scale(this.scale, this.scale, this.scale);
      TextureAtlasSprite textureatlassprite = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(Items.fire_charge);
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      float f = textureatlassprite.getMinU();
      float f1 = textureatlassprite.getMaxU();
      float f2 = textureatlassprite.getMinV();
      float f3 = textureatlassprite.getMaxV();
      GlStateManager.rotate(180.0F - super.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(-super.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
      worldrenderer.begin((int)c, DefaultVertexFormats.POSITION_TEX_NORMAL);
      worldrenderer.pos(-0.5, -0.25, 0.0).tex((double)f, (double)f3).normal(0.0F, 1.0F, 0.0F).endVertex();
      worldrenderer.pos(0.5, -0.25, 0.0).tex((double)f1, (double)f3).normal(0.0F, 1.0F, 0.0F).endVertex();
      worldrenderer.pos(0.5, 0.75, 0.0).tex((double)f1, (double)f2).normal(0.0F, 1.0F, 0.0F).endVertex();
      worldrenderer.pos(-0.5, 0.75, 0.0).tex((double)f, (double)f2).normal(0.0F, 1.0F, 0.0F).endVertex();
      tessellator.draw();
      GlStateManager.disableRescaleNormal();
      GlStateManager.popMatrix();
      super.doRender(entity, x, y, z, entityYaw, partialTicks);
   }

   public ResourceLocation getEntityTexture(EntityFireball entity) {
      return TextureMap.locationBlocksTexture;
   }
}
