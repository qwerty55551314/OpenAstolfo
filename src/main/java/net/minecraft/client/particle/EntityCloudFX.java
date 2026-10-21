package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityCloudFX extends EntityFX {
   public float field_70569_a;

   public EntityCloudFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double d, double e, double d2) {
      super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0, 0.0, 0.0);
      float f = 2.5F;
      super.motionX *= 0.1F;
      super.motionY *= 0.1F;
      super.motionZ *= 0.1F;
      super.motionX += d;
      super.motionY += e;
      super.motionZ += d2;
      super.particleRed = super.particleGreen = super.particleBlue = 1.0F - (float)(Math.random() * 0.3F);
      super.particleScale *= 0.75F;
      super.particleScale *= f;
      this.field_70569_a = super.particleScale;
      super.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.3));
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
      super.particleScale = this.field_70569_a * f;
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

      this.setParticleTextureIndex(7 - super.particleAge * 8 / super.particleMaxAge);
      this.moveEntity(super.motionX, super.motionY, super.motionZ);
      super.motionX *= 0.96F;
      super.motionY *= 0.96F;
      super.motionZ *= 0.96F;
      EntityPlayer entityplayer = super.worldObj.getClosestPlayerToEntity(this, 2.0);
      if (entityplayer != null) {
         double d0;
         int j = (d0 = super.posY - entityplayer.getEntityBoundingBox().minY) == 0.0 ? 0 : (d0 < 0.0 ? -1 : 1);
         if (j > 0) {
            super.posY = super.posY + (entityplayer.getEntityBoundingBox().minY - super.posY) * 0.2;
            super.motionY = super.motionY + (entityplayer.motionY - super.motionY) * 0.2;
            this.setPosition(super.posX, super.posY, super.posZ);
         }
      }

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
         return new EntityCloudFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
      }
   }
}
