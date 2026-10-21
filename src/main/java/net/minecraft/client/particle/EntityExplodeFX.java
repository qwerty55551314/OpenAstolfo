package net.minecraft.client.particle;

import net.minecraft.world.World;

public class EntityExplodeFX extends EntityFX {
   public EntityExplodeFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
      super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
      super.motionX = xSpeedIn + (Math.random() * 2.0 - 1.0) * 0.05F;
      super.motionY = ySpeedIn + (Math.random() * 2.0 - 1.0) * 0.05F;
      super.motionZ = zSpeedIn + (Math.random() * 2.0 - 1.0) * 0.05F;
      super.particleRed = super.particleGreen = super.particleBlue = super.rand.nextFloat() * 0.3F + 0.7F;
      super.particleScale = super.rand.nextFloat() * super.rand.nextFloat() * 6.0F + 1.0F;
      super.particleMaxAge = (int)(16.0 / ((double)super.rand.nextFloat() * 0.8 + 0.2)) + 2;
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
      super.motionY += 0.004;
      this.moveEntity(super.motionX, super.motionY, super.motionZ);
      super.motionX *= 0.9F;
      super.motionY *= 0.9F;
      super.motionZ *= 0.9F;
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
         return new EntityExplodeFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
      }
   }
}
