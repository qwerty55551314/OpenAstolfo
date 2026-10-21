package net.minecraft.client.particle;

import net.minecraft.world.World;

public class EntityAuraFX extends EntityFX {
   public EntityAuraFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double speedIn) {
      super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, speedIn);
      float f = super.rand.nextFloat() * 0.1F + 0.2F;
      super.particleRed = f;
      super.particleGreen = f;
      super.particleBlue = f;
      this.setParticleTextureIndex(0);
      this.setSize(0.02F, 0.02F);
      super.particleScale = super.particleScale * (super.rand.nextFloat() * 0.6F + 0.5F);
      super.motionX *= 0.02F;
      super.motionY *= 0.02F;
      super.motionZ *= 0.02F;
      super.particleMaxAge = (int)(20.0 / (Math.random() * 0.8 + 0.2));
      super.noClip = true;
   }

   @Override
   public void onUpdate() {
      super.prevPosX = super.posX;
      super.prevPosY = super.posY;
      super.prevPosZ = super.posZ;
      this.moveEntity(super.motionX, super.motionY, super.motionZ);
      super.motionX *= 0.99;
      super.motionY *= 0.99;
      super.motionZ *= 0.99;
      if (super.particleMaxAge-- <= 0) {
         this.setDead();
      }
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... ints
      ) {
         return new EntityAuraFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
      }
   }

   public static class HappyVillagerFactory implements IParticleFactory {
      private static final long b = 1490818423008002130L;

      @Override
      public EntityFX getEntityFX(
         int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... ints
      ) {
         EntityAuraFX entityaurafx = new EntityAuraFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
         entityaurafx.setParticleTextureIndex((int)b);
         entityaurafx.setRBGColorF(1.0F, 1.0F, 1.0F);
         return entityaurafx;
      }
   }
}
