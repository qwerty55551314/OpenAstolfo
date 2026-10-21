package net.minecraft.client.particle;

import net.minecraft.world.World;

public class EntityFishWakeFX extends EntityFX {
   public EntityFishWakeFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double d, double e, double d2) {
      super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0, 0.0, 0.0);
      super.motionX *= 0.3F;
      super.motionY = Math.random() * 0.2F + 0.1F;
      super.motionZ *= 0.3F;
      super.particleRed = 1.0F;
      super.particleGreen = 1.0F;
      super.particleBlue = 1.0F;
      this.setParticleTextureIndex(19);
      this.setSize(0.01F, 0.01F);
      super.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
      super.particleGravity = 0.0F;
      super.motionX = d;
      super.motionY = e;
      super.motionZ = d2;
   }

   @Override
   public void onUpdate() {
      super.prevPosX = super.posX;
      boolean flag = false;
      super.prevPosY = super.posY;
      super.prevPosZ = super.posZ;
      super.motionY = super.motionY - (double)super.particleGravity;
      this.moveEntity(super.motionX, super.motionY, super.motionZ);
      super.motionX *= 0.98F;
      super.motionY *= 0.98F;
      super.motionZ *= 0.98F;
      int i = 60 - super.particleMaxAge;
      float f = (float)i * 0.001F;
      this.setSize(f, f);
      this.setParticleTextureIndex(19 + i % 4);
      if (super.particleMaxAge-- <= 0) {
         this.setDead();
      }
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... ints
      ) {
         return new EntityFishWakeFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
      }
   }
}
