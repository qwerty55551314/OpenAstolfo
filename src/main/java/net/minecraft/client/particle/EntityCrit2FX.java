package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityCrit2FX extends EntityFX {
   public float field_174839_a;
   private static final long cb = -994973854663180223L;

   public EntityCrit2FX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double d, double e, double d2) {
      this(worldIn, xCoordIn, yCoordIn, zCoordIn, d, e, d2, 1.0F);
   }

   public EntityCrit2FX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double d, double e, double d2, float f) {
      super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0, 0.0, 0.0);
      super.motionX *= 0.1F;
      super.motionY *= 0.1F;
      super.motionZ *= 0.1F;
      super.motionX += d * 0.4;
      super.motionY += e * 0.4;
      super.motionZ += d2 * 0.4;
      super.particleRed = super.particleGreen = super.particleBlue = (float)(Math.random() * 0.3F + 0.6F);
      super.particleScale *= 0.75F;
      super.particleScale *= f;
      this.field_174839_a = super.particleScale;
      super.particleMaxAge = (int)(6.0 / (Math.random() * 0.8 + 0.6));
      super.particleMaxAge = (int)((float)super.particleMaxAge * f);
      super.noClip = false;
      this.setParticleTextureIndex((int)cb);
      this.onUpdate();
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
      super.particleScale = this.field_174839_a * f;
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

      this.moveEntity(super.motionX, super.motionY, super.motionZ);
      super.particleGreen = (float)((double)super.particleGreen * 0.96);
      super.particleBlue = (float)((double)super.particleBlue * 0.9);
      super.motionX *= 0.7F;
      super.motionY *= 0.7F;
      super.motionZ *= 0.7F;
      super.motionY -= 0.02F;
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
         return new EntityCrit2FX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
      }
   }

   public static class MagicFactory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... ints
      ) {
         EntityCrit2FX entitycrit2fx = new EntityCrit2FX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
         entitycrit2fx.setRBGColorF(entitycrit2fx.getRedColorF() * 0.3F, entitycrit2fx.getGreenColorF() * 0.8F, entitycrit2fx.getBlueColorF());
         entitycrit2fx.nextTextureIndexX();
         return entitycrit2fx;
      }
   }
}
