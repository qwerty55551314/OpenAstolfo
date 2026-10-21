package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class EntityPortalFX extends EntityFX {
   private float portalParticleScale;
   private double portalPosX;
   private double portalPosY;
   private double portalPosZ;

   public EntityPortalFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
      super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
      super.motionX = xSpeedIn;
      super.motionY = ySpeedIn;
      super.motionZ = zSpeedIn;
      this.portalPosX = super.posX = xCoordIn;
      this.portalPosY = super.posY = yCoordIn;
      this.portalPosZ = super.posZ = zCoordIn;
      float f = super.rand.nextFloat() * 0.6F + 0.4F;
      this.portalParticleScale = super.particleScale = super.rand.nextFloat() * 0.2F + 0.5F;
      super.particleRed = super.particleGreen = super.particleBlue = 1.0F * f;
      super.particleGreen *= 0.3F;
      super.particleRed *= 0.9F;
      super.particleMaxAge = (int)(Math.random() * 10.0) + 40;
      super.noClip = true;
      this.setParticleTextureIndex((int)(Math.random() * 8.0));
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
      float f = ((float)super.particleAge + partialTicks) / (float)super.particleMaxAge;
      f = 1.0F - f;
      f *= f;
      f = 1.0F - f;
      super.particleScale = this.portalParticleScale * f;
      super.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
   }

   @Override
   public int getBrightnessForRender(float partialTicks) {
      int i = super.getBrightnessForRender(partialTicks);
      float f = (float)super.particleAge / (float)super.particleMaxAge;
      f *= f;
      f *= f;
      int j = i & 0xFF;
      int k = i >> 16 & 0xFF;
      k += (int)(f * 15.0F * 16.0F);
      if (k > 240) {
         k = 240;
      }

      return j | k << 16;
   }

   @Override
   public float getBrightness(float partialTicks) {
      float f = super.getBrightness(partialTicks);
      float f1 = (float)super.particleAge / (float)super.particleMaxAge;
      f1 = f1 * f1 * f1 * f1;
      return f * (1.0F - f1) + f1;
   }

   @Override
   public void onUpdate() {
      super.prevPosX = super.posX;
      super.prevPosY = super.posY;
      super.prevPosZ = super.posZ;
      float f = (float)super.particleAge / (float)super.particleMaxAge;
      f = -f + f * f * 2.0F;
      f = 1.0F - f;
      super.posX = this.portalPosX + super.motionX * (double)f;
      super.posY = this.portalPosY + super.motionY * (double)f + (double)(1.0F - f);
      super.posZ = this.portalPosZ + super.motionZ * (double)f;
      if (super.particleAge++ >= super.particleMaxAge) {
         this.setDead();
      }
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... ints
      ) {
         return new EntityPortalFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
      }
   }
}
