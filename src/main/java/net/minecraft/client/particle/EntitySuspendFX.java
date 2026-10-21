package net.minecraft.client.particle;

import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntitySuspendFX extends EntityFX {
   public EntitySuspendFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
      super(worldIn, xCoordIn, yCoordIn - 0.125, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
      super.particleRed = 0.4F;
      super.particleGreen = 0.4F;
      super.particleBlue = 0.7F;
      this.setParticleTextureIndex(0);
      this.setSize(0.01F, 0.01F);
      super.particleScale = super.particleScale * (super.rand.nextFloat() * 0.6F + 0.2F);
      super.motionX = xSpeedIn * 0.0;
      super.motionY = ySpeedIn * 0.0;
      super.motionZ = zSpeedIn * 0.0;
      super.particleMaxAge = (int)(16.0 / (Math.random() * 0.8 + 0.2));
   }

   @Override
   public void onUpdate() {
      super.prevPosX = super.posX;
      super.prevPosY = super.posY;
      super.prevPosZ = super.posZ;
      this.moveEntity(super.motionX, super.motionY, super.motionZ);
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
         return new EntitySuspendFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
      }
   }
}
