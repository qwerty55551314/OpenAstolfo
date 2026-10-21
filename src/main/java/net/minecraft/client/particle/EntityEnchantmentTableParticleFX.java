package net.minecraft.client.particle;

import net.minecraft.world.World;

public class EntityEnchantmentTableParticleFX extends EntityFX {
   private float field_70565_a;
   private double coordX;
   private double coordY;
   private double coordZ;

   public EntityEnchantmentTableParticleFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
      super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
      super.motionX = xSpeedIn;
      super.motionY = ySpeedIn;
      super.motionZ = zSpeedIn;
      this.coordX = xCoordIn;
      this.coordY = yCoordIn;
      this.coordZ = zCoordIn;
      super.posX = super.prevPosX = xCoordIn + xSpeedIn;
      super.posY = super.prevPosY = yCoordIn + ySpeedIn;
      super.posZ = super.prevPosZ = zCoordIn + zSpeedIn;
      float f = super.rand.nextFloat() * 0.6F + 0.4F;
      this.field_70565_a = super.particleScale = super.rand.nextFloat() * 0.5F + 0.2F;
      super.particleRed = super.particleGreen = super.particleBlue = 1.0F * f;
      super.particleGreen *= 0.9F;
      super.particleRed *= 0.9F;
      super.particleMaxAge = (int)(Math.random() * 10.0) + 30;
      super.noClip = true;
      this.setParticleTextureIndex((int)(Math.random() * 26.0 + 1.0 + 224.0));
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
      f1 *= f1;
      f1 *= f1;
      return f * (1.0F - f1) + f1;
   }

   @Override
   public void onUpdate() {
      super.prevPosX = super.posX;
      super.prevPosY = super.posY;
      super.prevPosZ = super.posZ;
      float f = (float)super.particleAge / (float)super.particleMaxAge;
      f = 1.0F - f;
      float f1 = 1.0F - f;
      f1 *= f1;
      f1 *= f1;
      super.posX = this.coordX + super.motionX * (double)f;
      super.posY = this.coordY + super.motionY * (double)f - (double)(f1 * 1.2F);
      super.posZ = this.coordZ + super.motionZ * (double)f;
      if (super.particleAge++ >= super.particleMaxAge) {
         this.setDead();
      }
   }

   public static class EnchantmentTable implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... ints
      ) {
         return new EntityEnchantmentTableParticleFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
      }
   }
}
