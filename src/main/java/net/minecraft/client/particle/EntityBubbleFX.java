package net.minecraft.client.particle;

import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityBubbleFX extends EntityFX {
   private static final long cb = 3945149631444287520L;

   public EntityBubbleFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
      super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
      super.particleRed = 1.0F;
      super.particleGreen = 1.0F;
      super.particleBlue = 1.0F;
      this.setParticleTextureIndex((int)cb);
      this.setSize(0.02F, 0.02F);
      super.particleScale = super.particleScale * (super.rand.nextFloat() * 0.6F + 0.2F);
      super.motionX = xSpeedIn * 0.2F + (Math.random() * 2.0 - 1.0) * 0.02F;
      super.motionY = ySpeedIn * 0.2F + (Math.random() * 2.0 - 1.0) * 0.02F;
      super.motionZ = zSpeedIn * 0.2F + (Math.random() * 2.0 - 1.0) * 0.02F;
      super.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
   }

   @Override
   public void onUpdate() {
      super.prevPosX = super.posX;
      super.prevPosY = super.posY;
      super.prevPosZ = super.posZ;
      super.motionY += 0.002;
      this.moveEntity(super.motionX, super.motionY, super.motionZ);
      super.motionX *= 0.85F;
      super.motionY *= 0.85F;
      super.motionZ *= 0.85F;
      if (super.worldObj.getBlockState(new BlockPos(this)).getBlock().getMaterial() != Material.water) {
         this.setDead();
      }

      if (super.particleMaxAge-- <= 0) {
         this.setDead();
      }
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... ints
      ) {
         return new EntityBubbleFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
      }
   }
}
