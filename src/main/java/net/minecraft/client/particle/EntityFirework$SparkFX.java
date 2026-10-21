package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityFirework$SparkFX extends EntityFX {
   private int baseTextureIndex = 160;
   private boolean trail;
   private boolean twinkle;
   private final EffectRenderer field_92047_az;
   private float fadeColourRed;
   private float fadeColourGreen;
   private float fadeColourBlue;
   private boolean hasFadeColour;

   public EntityFirework$SparkFX(World world, double d, double e, double d2, double d3, double d4, double d5, EffectRenderer effectRenderer) {
      super(world, d, e, d2);
      super.motionX = d3;
      super.motionY = d4;
      super.motionZ = d5;
      this.field_92047_az = effectRenderer;
      super.particleScale *= 0.75F;
      super.particleMaxAge = 48 + super.rand.nextInt(12);
      super.noClip = false;
   }

   public void setTrail(boolean trailIn) {
      this.trail = trailIn;
   }

   public void setTwinkle(boolean twinkleIn) {
      this.twinkle = twinkleIn;
   }

   public void setColour(int colour) {
      float f = (float)((colour & 0xFF0000) >> 16) / 255.0F;
      float f1 = (float)((colour & 0xFF00) >> 8) / 255.0F;
      float f2 = (float)((colour & 0xFF) >> 0) / 255.0F;
      float f3 = 1.0F;
      this.setRBGColorF(f * f3, f1 * f3, f2 * f3);
   }

   public void setFadeColour(int faceColour) {
      this.fadeColourRed = (float)((faceColour & 0xFF0000) >> 16) / 255.0F;
      this.fadeColourGreen = (float)((faceColour & 0xFF00) >> 8) / 255.0F;
      this.fadeColourBlue = (float)((faceColour & 0xFF) >> 0) / 255.0F;
      this.hasFadeColour = true;
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox() {
      return null;
   }

   @Override
   public boolean canBePushed() {
      return false;
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
      if (!this.twinkle || super.particleAge < super.particleMaxAge / 3 || (super.particleAge + super.particleMaxAge) / 3 % 2 == 0) {
         super.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
      }
   }

   @Override
   public void onUpdate() {
      super.prevPosX = super.posX;
      super.prevPosY = super.posY;
      super.prevPosZ = super.posZ;
      int i1 = super.particleAge++;
      int j = super.particleMaxAge;
      int i = i1;
      if (i >= j) {
         this.setDead();
      }

      if (super.particleAge > super.particleMaxAge / 2) {
         this.setAlphaF(1.0F - ((float)super.particleAge - (float)(super.particleMaxAge / 2)) / (float)super.particleMaxAge);
         boolean flag = this.hasFadeColour;
         if (flag) {
            super.particleRed = super.particleRed + (this.fadeColourRed - super.particleRed) * 0.2F;
            super.particleGreen = super.particleGreen + (this.fadeColourGreen - super.particleGreen) * 0.2F;
            super.particleBlue = super.particleBlue + (this.fadeColourBlue - super.particleBlue) * 0.2F;
         }
      }

      this.setParticleTextureIndex(this.baseTextureIndex + (7 - super.particleAge * 8 / super.particleMaxAge));
      super.motionY -= 0.004;
      this.moveEntity(super.motionX, super.motionY, super.motionZ);
      super.motionX *= 0.91F;
      super.motionY *= 0.91F;
      super.motionZ *= 0.91F;
      boolean flag1 = super.onGround;
      if (flag1) {
         super.motionX *= 0.7F;
         super.motionZ *= 0.7F;
      }

      boolean flag2 = this.trail;
      if (flag2) {
         int l = super.particleMaxAge / 2;
         int k = super.particleAge;
         if (k < l && (super.particleAge + super.particleMaxAge) % 2 == 0) {
            EntityFirework$SparkFX entityfirework$sparkfx = new EntityFirework$SparkFX(
               super.worldObj, super.posX, super.posY, super.posZ, 0.0, 0.0, 0.0, this.field_92047_az
            );
            entityfirework$sparkfx.setAlphaF(0.99F);
            entityfirework$sparkfx.setRBGColorF(super.particleRed, super.particleGreen, super.particleBlue);
            entityfirework$sparkfx.particleAge = entityfirework$sparkfx.particleMaxAge / 2;
            if (this.hasFadeColour) {
               entityfirework$sparkfx.hasFadeColour = true;
               entityfirework$sparkfx.fadeColourRed = this.fadeColourRed;
               entityfirework$sparkfx.fadeColourGreen = this.fadeColourGreen;
               entityfirework$sparkfx.fadeColourBlue = this.fadeColourBlue;
            }

            entityfirework$sparkfx.twinkle = this.twinkle;
            this.field_92047_az.addEffect(entityfirework$sparkfx);
         }
      }
   }

   @Override
   public int getBrightnessForRender(float partialTicks) {
      return 15728880;
   }

   @Override
   public float getBrightness(float partialTicks) {
      return 1.0F;
   }
}
