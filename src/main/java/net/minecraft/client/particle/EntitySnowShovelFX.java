package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntitySnowShovelFX extends EntityFX {
   public float snowDigParticleScale;

   public EntitySnowShovelFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
      this(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, 1.0F);
   }

   public EntitySnowShovelFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, float f) {
      super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
      super.motionX *= 0.1F;
      super.motionY *= 0.1F;
      super.motionZ *= 0.1F;
      super.motionX += xSpeedIn;
      super.motionY += ySpeedIn;
      super.motionZ += zSpeedIn;
      super.particleRed = super.particleGreen = super.particleBlue = 1.0F - (float)(Math.random() * 0.3F);
      super.particleScale *= 0.75F;
      super.particleScale *= f;
      this.snowDigParticleScale = super.particleScale;
      super.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
      super.particleMaxAge = (int)((float)super.particleMaxAge * f);
      super.noClip = false;
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
      float f = ((float)super.particleAge + partialTicks) / (float)super.particleMaxAge * 32.0F;
      f = MathHelper.clamp_float(f, 0.0F, 1.0F);
      super.particleScale = this.snowDigParticleScale * f;
      super.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
   }

   @Override
   public void onUpdate() {
      super.prevPosX = super.posX;
      super.prevPosY = super.posY;
      super.prevPosZ = super.posZ;
      if (super.particleAge++ >= super.particleMaxAge) {
         this.setDead();
      }

      this.setParticleTextureIndex(7 - super.particleAge * 8 / super.particleMaxAge);
      super.motionY -= 0.03;
      this.moveEntity(super.motionX, super.motionY, super.motionZ);
      super.motionX *= 0.99F;
      super.motionY *= 0.99F;
      super.motionZ *= 0.99F;
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
         return new EntitySnowShovelFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
      }
   }
}
