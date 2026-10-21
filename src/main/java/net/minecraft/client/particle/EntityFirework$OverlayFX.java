package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFirework$OverlayFX extends EntityFX {
   public EntityFirework$OverlayFX(World world, double d, double e, double d2) {
      super(world, d, e, d2);
      super.particleMaxAge = 4;
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
      float f = 7.1F * MathHelper.sin(((float)super.particleAge + partialTicks - 1.0F) * 0.25F * (float) Math.PI);
      super.particleAlpha = 0.6F - ((float)super.particleAge + partialTicks - 1.0F) * 0.25F * 0.5F;
      float f1 = (float)(super.prevPosX + (super.posX - super.prevPosX) * (double)partialTicks - EntityFX.interpPosX);
      float f2 = (float)(super.prevPosY + (super.posY - super.prevPosY) * (double)partialTicks - EntityFX.interpPosY);
      float f3 = (float)(super.prevPosZ + (super.posZ - super.prevPosZ) * (double)partialTicks - EntityFX.interpPosZ);
      int i = this.getBrightnessForRender(partialTicks);
      int j = i >> 16 & 65535;
      int k = i & 65535;
      worldRendererIn.pos((double)(f1 - rotationX * f - rotationXY * f), (double)(f2 - rotationZ * f), (double)(f3 - rotationYZ * f - rotationXZ * f))
         .tex(0.5, 0.375)
         .color(super.particleRed, super.particleGreen, super.particleBlue, super.particleAlpha)
         .lightmap(j, k)
         .endVertex();
      worldRendererIn.pos((double)(f1 - rotationX * f + rotationXY * f), (double)(f2 + rotationZ * f), (double)(f3 - rotationYZ * f + rotationXZ * f))
         .tex(0.5, 0.125)
         .color(super.particleRed, super.particleGreen, super.particleBlue, super.particleAlpha)
         .lightmap(j, k)
         .endVertex();
      worldRendererIn.pos((double)(f1 + rotationX * f + rotationXY * f), (double)(f2 + rotationZ * f), (double)(f3 + rotationYZ * f + rotationXZ * f))
         .tex(0.25, 0.125)
         .color(super.particleRed, super.particleGreen, super.particleBlue, super.particleAlpha)
         .lightmap(j, k)
         .endVertex();
      worldRendererIn.pos((double)(f1 + rotationX * f - rotationXY * f), (double)(f2 - rotationZ * f), (double)(f3 + rotationYZ * f - rotationXZ * f))
         .tex(0.25, 0.375)
         .color(super.particleRed, super.particleGreen, super.particleBlue, super.particleAlpha)
         .lightmap(j, k)
         .endVertex();
   }
}
