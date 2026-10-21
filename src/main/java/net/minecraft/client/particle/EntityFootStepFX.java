package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityFootStepFX extends EntityFX {
   private static final ResourceLocation FOOTPRINT_TEXTURE = new ResourceLocation("textures/particle/footprint.png");
   private int footstepAge;
   private int footstepMaxAge;
   private TextureManager currentFootSteps;

   public EntityFootStepFX(TextureManager currentFootStepsIn, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn) {
      super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0, 0.0, 0.0);
      this.currentFootSteps = currentFootStepsIn;
      super.motionX = super.motionY = super.motionZ = 0.0;
      this.footstepMaxAge = 200;
   }

   @Override
   public void renderParticle(
      WorldRenderer worldRendererIn,
      Entity entityIn,
      float partialTicks,
      float rotationX,
      float rotationZ,
      float rotationYZ,
      float rotationXY,
      float rotationXZ
   ) {
      float f = ((float)this.footstepAge + partialTicks) / (float)this.footstepMaxAge;
      f *= f;
      float f1 = 2.0F - f * 2.0F;
      if (f1 > 1.0F) {
         f1 = 1.0F;
      }

      f1 *= 0.2F;
      int short1 = 30164;
      GlStateManager.disableLighting();
      long i = 6464372214510650607L ^ 99707405585626L;
      float f2 = (float)(super.posX - EntityFX.interpPosX);
      float f3 = (float)(super.posY - EntityFX.interpPosY);
      float f4 = (float)(super.posZ - EntityFX.interpPosZ);
      float f5 = super.worldObj.getLightBrightness(new BlockPos(this));
      this.currentFootSteps.bindTexture(FOOTPRINT_TEXTURE);
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(770, 771);
      worldRendererIn.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
      worldRendererIn.pos((double)(f2 - 0.125F), (double)f3, (double)(f4 + 0.125F)).tex(0.0, 1.0).color(f5, f5, f5, f1).endVertex();
      worldRendererIn.pos((double)(f2 + 0.125F), (double)f3, (double)(f4 + 0.125F)).tex(1.0, 1.0).color(f5, f5, f5, f1).endVertex();
      worldRendererIn.pos((double)(f2 + 0.125F), (double)f3, (double)(f4 - 0.125F)).tex(1.0, 0.0).color(f5, f5, f5, f1).endVertex();
      worldRendererIn.pos((double)(f2 - 0.125F), (double)f3, (double)(f4 - 0.125F)).tex(0.0, 0.0).color(f5, f5, f5, f1).endVertex();
      Tessellator.getInstance().draw();
      GlStateManager.disableBlend();
      GlStateManager.enableLighting();
   }

   @Override
   public void onUpdate() {
      boolean flag = false;
      this.footstepAge++;
      if (this.footstepAge == this.footstepMaxAge) {
         this.setDead();
      }
   }

   @Override
   public int getFXLayer() {
      return 3;
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... ints
      ) {
         return new EntityFootStepFX(Minecraft.getMinecraft().getTextureManager(), worldIn, xCoordIn, yCoordIn, zCoordIn);
      }
   }
}
