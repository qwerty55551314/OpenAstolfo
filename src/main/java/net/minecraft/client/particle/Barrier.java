package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class Barrier extends EntityFX {
   public Barrier(World worldIn, double d, double e, double d2, Item item) {
      super(worldIn, d, e, d2, 0.0, 0.0, 0.0);
      this.setParticleIcon(Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(item));
      super.particleRed = super.particleGreen = super.particleBlue = 1.0F;
      super.motionX = super.motionY = super.motionZ = 0.0;
      super.particleGravity = 0.0F;
      super.particleMaxAge = 80;
   }

   @Override
   public int getFXLayer() {
      return 1;
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
      float f = super.particleIcon.getMinU();
      float f1 = super.particleIcon.getMaxU();
      float f2 = super.particleIcon.getMinV();
      float f3 = super.particleIcon.getMaxV();
      float f4 = (float)(super.prevPosX + (super.posX - super.prevPosX) * (double)partialTicks - EntityFX.interpPosX);
      float f5 = (float)(super.prevPosY + (super.posY - super.prevPosY) * (double)partialTicks - EntityFX.interpPosY);
      float f6 = (float)(super.prevPosZ + (super.posZ - super.prevPosZ) * (double)partialTicks - EntityFX.interpPosZ);
      int i = this.getBrightnessForRender(partialTicks);
      int j = i >> 16 & 65535;
      int k = i & 65535;
      worldRendererIn.pos(
            (double)(f4 - rotationX * 0.5F - rotationXY * 0.5F), (double)(f5 - rotationZ * 0.5F), (double)(f6 - rotationYZ * 0.5F - rotationXZ * 0.5F)
         )
         .tex((double)f1, (double)f3)
         .color(super.particleRed, super.particleGreen, super.particleBlue, 1.0F)
         .lightmap(j, k)
         .endVertex();
      worldRendererIn.pos(
            (double)(f4 - rotationX * 0.5F + rotationXY * 0.5F), (double)(f5 + rotationZ * 0.5F), (double)(f6 - rotationYZ * 0.5F + rotationXZ * 0.5F)
         )
         .tex((double)f1, (double)f2)
         .color(super.particleRed, super.particleGreen, super.particleBlue, 1.0F)
         .lightmap(j, k)
         .endVertex();
      worldRendererIn.pos(
            (double)(f4 + rotationX * 0.5F + rotationXY * 0.5F), (double)(f5 + rotationZ * 0.5F), (double)(f6 + rotationYZ * 0.5F + rotationXZ * 0.5F)
         )
         .tex((double)f, (double)f2)
         .color(super.particleRed, super.particleGreen, super.particleBlue, 1.0F)
         .lightmap(j, k)
         .endVertex();
      worldRendererIn.pos(
            (double)(f4 + rotationX * 0.5F - rotationXY * 0.5F), (double)(f5 - rotationZ * 0.5F), (double)(f6 + rotationYZ * 0.5F - rotationXZ * 0.5F)
         )
         .tex((double)f, (double)f3)
         .color(super.particleRed, super.particleGreen, super.particleBlue, 1.0F)
         .lightmap(j, k)
         .endVertex();
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... ints
      ) {
         return new Barrier(worldIn, xCoordIn, yCoordIn, zCoordIn, Item.getItemFromBlock(Blocks.barrier));
      }
   }
}
