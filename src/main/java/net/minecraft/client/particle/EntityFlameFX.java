package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFlameFX extends EntityFX {
   private float flameScale;

   public EntityFlameFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
      super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
      super.motionX = super.motionX * 0.01F + xSpeedIn;
      super.motionY = super.motionY * 0.01F + ySpeedIn;
      super.motionZ = super.motionZ * 0.01F + zSpeedIn;
      super.posX = super.posX + (double)((super.rand.nextFloat() - super.rand.nextFloat()) * 0.05F);
      super.posY = super.posY + (double)((super.rand.nextFloat() - super.rand.nextFloat()) * 0.05F);
      super.posZ = super.posZ + (double)((super.rand.nextFloat() - super.rand.nextFloat()) * 0.05F);
      this.flameScale = super.particleScale;
      super.particleRed = super.particleGreen = super.particleBlue = 1.0F;
      super.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2)) + 4;
      super.noClip = true;
      this.setParticleTextureIndex(48);
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
      super.particleScale = this.flameScale * (1.0F - f * f * 0.5F);
      super.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
   }

   @Override
   public int getBrightnessForRender(float partialTicks) {
      float f = ((float)super.particleAge + partialTicks) / (float)super.particleMaxAge;
      f = MathHelper.clamp_float(f, 0.0F, 1.0F);
      int i = super.getBrightnessForRender(partialTicks);
      int j = i & 0xFF;
      int k = i >> 16 & 0xFF;
      j += (int)(f * 15.0F * 16.0F);
      if (j > 240) {
         j = 240;
      }

      return j | k << 16;
   }

   @Override
   public float getBrightness(float partialTicks) {
      float f = ((float)super.particleAge + partialTicks) / (float)super.particleMaxAge;
      f = MathHelper.clamp_float(f, 0.0F, 1.0F);
      float f1 = super.getBrightness(partialTicks);
      return f1 * f + (1.0F - f);
   }

   @Override
   public void onUpdate() {
      super.prevPosX = super.posX;
      super.prevPosY = super.posY;
      super.prevPosZ = super.posZ;
      if (super.particleAge++ >= super.particleMaxAge) {
         this.setDead();
      }

      this.moveEntity(super.motionX, super.motionY, super.motionZ);
      super.motionX *= 0.96F;
      super.motionY *= 0.96F;
      super.motionZ *= 0.96F;
      if (super.onGround) {
         super.motionX *= 0.7F;
         super.motionZ *= 0.7F;
      }
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... ints
      ) {
         return new EntityFlameFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
      }
   }
}
