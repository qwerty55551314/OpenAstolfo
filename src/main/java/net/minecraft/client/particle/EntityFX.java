package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFX extends Entity {
   public int particleTextureIndexX;
   public int particleTextureIndexY;
   public float particleTextureJitterX;
   public float particleTextureJitterY;
   public int particleAge;
   public int particleMaxAge;
   public float particleScale;
   public float particleGravity;
   public float particleRed;
   public float particleGreen;
   public float particleBlue;
   public float particleAlpha = 1.0F;
   public TextureAtlasSprite particleIcon;
   public static double interpPosX;
   public static double interpPosY;
   public static double interpPosZ;

   public EntityFX(World worldIn, double posXIn, double posYIn, double posZIn) {
      super(worldIn);
      this.setSize(0.2F, 0.2F);
      this.setPosition(posXIn, posYIn, posZIn);
      super.lastTickPosX = super.prevPosX = posXIn;
      super.lastTickPosY = super.prevPosY = posYIn;
      super.lastTickPosZ = super.prevPosZ = posZIn;
      this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
      this.particleTextureJitterX = super.rand.nextFloat() * 3.0F;
      this.particleTextureJitterY = super.rand.nextFloat() * 3.0F;
      this.particleScale = (super.rand.nextFloat() * 0.5F + 0.5F) * 2.0F;
      this.particleMaxAge = (int)(4.0F / (super.rand.nextFloat() * 0.9F + 0.1F));
      this.particleAge = 0;
   }

   public EntityFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
      this(worldIn, xCoordIn, yCoordIn, zCoordIn);
      super.motionX = xSpeedIn + (Math.random() * 2.0 - 1.0) * 0.4F;
      super.motionY = ySpeedIn + (Math.random() * 2.0 - 1.0) * 0.4F;
      super.motionZ = zSpeedIn + (Math.random() * 2.0 - 1.0) * 0.4F;
      float f = (float)(Math.random() + Math.random() + 1.0) * 0.15F;
      float f1 = MathHelper.sqrt_double(super.motionX * super.motionX + super.motionY * super.motionY + super.motionZ * super.motionZ);
      super.motionX = super.motionX / (double)f1 * (double)f * 0.4F;
      super.motionY = super.motionY / (double)f1 * (double)f * 0.4F + 0.1F;
      super.motionZ = super.motionZ / (double)f1 * (double)f * 0.4F;
   }

   public EntityFX multiplyVelocity(float multiplier) {
      super.motionX *= (double)multiplier;
      super.motionY = (super.motionY - 0.1F) * (double)multiplier + 0.1F;
      super.motionZ *= (double)multiplier;
      return this;
   }

   public EntityFX multipleParticleScaleBy(float scale) {
      this.setSize(0.2F * scale, 0.2F * scale);
      this.particleScale *= scale;
      return this;
   }

   public void setRBGColorF(float particleRedIn, float particleGreenIn, float particleBlueIn) {
      this.particleRed = particleRedIn;
      this.particleGreen = particleGreenIn;
      this.particleBlue = particleBlueIn;
   }

   public void setAlphaF(float alpha) {
      if (this.particleAlpha == 1.0F && alpha < 1.0F) {
         Minecraft.getMinecraft().effectRenderer.moveToAlphaLayer(this);
      } else if (this.particleAlpha < 1.0F && alpha == 1.0F) {
         Minecraft.getMinecraft().effectRenderer.moveToNoAlphaLayer(this);
      }

      this.particleAlpha = alpha;
   }

   public float getRedColorF() {
      return this.particleRed;
   }

   public float getGreenColorF() {
      return this.particleGreen;
   }

   public float getBlueColorF() {
      return this.particleBlue;
   }

   public float getAlpha() {
      return this.particleAlpha;
   }

   @Override
   public boolean canTriggerWalking() {
      return false;
   }

   @Override
   public void entityInit() {
   }

   @Override
   public void onUpdate() {
      super.prevPosX = super.posX;
      super.prevPosY = super.posY;
      super.prevPosZ = super.posZ;
      if (this.particleAge++ >= this.particleMaxAge) {
         this.setDead();
      }

      super.motionY = super.motionY - 0.04 * (double)this.particleGravity;
      this.moveEntity(super.motionX, super.motionY, super.motionZ);
      super.motionX *= 0.98F;
      super.motionY *= 0.98F;
      super.motionZ *= 0.98F;
      if (super.onGround) {
         super.motionX *= 0.7F;
         super.motionZ *= 0.7F;
      }
   }

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
      float f = (float)this.particleTextureIndexX / 16.0F;
      float f1 = f + 0.0624375F;
      float f2 = (float)this.particleTextureIndexY / 16.0F;
      float f3 = f2 + 0.0624375F;
      float f4 = 0.1F * this.particleScale;
      if (this.particleIcon != null) {
         f = this.particleIcon.getMinU();
         f1 = this.particleIcon.getMaxU();
         f2 = this.particleIcon.getMinV();
         f3 = this.particleIcon.getMaxV();
      }

      float f5 = (float)(super.prevPosX + (super.posX - super.prevPosX) * (double)partialTicks - interpPosX);
      float f6 = (float)(super.prevPosY + (super.posY - super.prevPosY) * (double)partialTicks - interpPosY);
      float f7 = (float)(super.prevPosZ + (super.posZ - super.prevPosZ) * (double)partialTicks - interpPosZ);
      int i = this.getBrightnessForRender(partialTicks);
      int j = i >> 16 & 65535;
      int k = i & 65535;
      worldRendererIn.pos((double)(f5 - rotationX * f4 - rotationXY * f4), (double)(f6 - rotationZ * f4), (double)(f7 - rotationYZ * f4 - rotationXZ * f4))
         .tex((double)f1, (double)f3)
         .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
         .lightmap(j, k)
         .endVertex();
      worldRendererIn.pos((double)(f5 - rotationX * f4 + rotationXY * f4), (double)(f6 + rotationZ * f4), (double)(f7 - rotationYZ * f4 + rotationXZ * f4))
         .tex((double)f1, (double)f2)
         .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
         .lightmap(j, k)
         .endVertex();
      worldRendererIn.pos((double)(f5 + rotationX * f4 + rotationXY * f4), (double)(f6 + rotationZ * f4), (double)(f7 + rotationYZ * f4 + rotationXZ * f4))
         .tex((double)f, (double)f2)
         .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
         .lightmap(j, k)
         .endVertex();
      worldRendererIn.pos((double)(f5 + rotationX * f4 - rotationXY * f4), (double)(f6 - rotationZ * f4), (double)(f7 + rotationYZ * f4 - rotationXZ * f4))
         .tex((double)f, (double)f3)
         .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
         .lightmap(j, k)
         .endVertex();
   }

   public int getFXLayer() {
      return 0;
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound tagCompound) {
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound tagCompund) {
   }

   public void setParticleIcon(TextureAtlasSprite icon) {
      int i = this.getFXLayer();
      if (i == 1) {
         this.particleIcon = icon;
      } else {
         throw new RuntimeException("Invalid call to Particle.setTex, use coordinate methods");
      }
   }

   public void setParticleTextureIndex(int particleTextureIndex) {
      if (this.getFXLayer() != 0) {
         throw new RuntimeException("Invalid call to Particle.setMiscTex");
      } else {
         this.particleTextureIndexX = particleTextureIndex % 16;
         this.particleTextureIndexY = particleTextureIndex / 16;
      }
   }

   public void nextTextureIndexX() {
      this.particleTextureIndexX++;
   }

   @Override
   public boolean canAttackWithItem() {
      return false;
   }

   @Override
   public String toString() {
      return this.getClass().getSimpleName()
         + ", Pos ("
         + super.posX
         + ","
         + super.posY
         + ","
         + super.posZ
         + "), RGBA ("
         + this.particleRed
         + ","
         + this.particleGreen
         + ","
         + this.particleBlue
         + ","
         + this.particleAlpha
         + "), Age "
         + this.particleAge;
   }
}
