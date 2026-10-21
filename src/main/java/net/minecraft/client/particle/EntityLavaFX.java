package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityLavaFX extends EntityFX {
   private float lavaParticleScale;

   public EntityLavaFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn) {
      super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0, 0.0, 0.0);
      super.motionX *= 0.8F;
      super.motionY *= 0.8F;
      super.motionZ *= 0.8F;
      super.motionY = (double)(super.rand.nextFloat() * 0.4F + 0.05F);
      super.particleRed = super.particleGreen = super.particleBlue = 1.0F;
      super.particleScale = super.particleScale * (super.rand.nextFloat() * 2.0F + 0.2F);
      this.lavaParticleScale = super.particleScale;
      super.particleMaxAge = (int)(16.0 / (Math.random() * 0.8 + 0.2));
      super.noClip = false;
      this.setParticleTextureIndex(49);
   }

   @Override
   public int getBrightnessForRender(float partialTicks) {
      float f = ((float)super.particleAge + partialTicks) / (float)super.particleMaxAge;
      f = MathHelper.clamp_float(f, 0.0F, 1.0F);
      int i = super.getBrightnessForRender(partialTicks);
      int j = i >> 16 & 0xFF;
      return 240 | j << 16;
   }

   @Override
   public float getBrightness(float partialTicks) {
      return 1.0F;
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
      super.particleScale = this.lavaParticleScale * (1.0F - f * f);
      super.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
   }

   @Override
   public void onUpdate() {
      super.prevPosX = super.posX;
      super.prevPosY = super.posY;
      super.prevPosZ = super.posZ;
      int i = super.particleAge++;
      if (i >= super.particleMaxAge) {
         this.setDead();
      }

      float f = (float)super.particleAge / (float)super.particleMaxAge;
      float f1;
      int j = (f1 = super.rand.nextFloat() - f) == 0.0F ? 0 : (f1 < 0.0F ? -1 : 1);
      if (j > 0) {
         super.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, super.posX, super.posY, super.posZ, super.motionX, super.motionY, super.motionZ);
      }

      super.motionY -= 0.03;
      this.moveEntity(super.motionX, super.motionY, super.motionZ);
      super.motionX *= 0.999F;
      super.motionY *= 0.999F;
      super.motionZ *= 0.999F;
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
         return new EntityLavaFX(worldIn, xCoordIn, yCoordIn, zCoordIn);
      }
   }
}
