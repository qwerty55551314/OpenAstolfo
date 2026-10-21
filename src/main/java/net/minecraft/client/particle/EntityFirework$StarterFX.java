package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemDye;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFirework$StarterFX extends EntityFX {
   private int fireworkAge;
   private final EffectRenderer theEffectRenderer;
   private NBTTagList fireworkExplosions;
   public boolean twinkle;

   public EntityFirework$StarterFX(
      World world, double d, double e, double d2, double d3, double d4, double d5, EffectRenderer effectRenderer, NBTTagCompound nbtTagCompound
   ) {
      super(world, d, e, d2, 0.0, 0.0, 0.0);
      super.motionX = d3;
      super.motionY = d4;
      super.motionZ = d5;
      this.theEffectRenderer = effectRenderer;
      super.particleMaxAge = 8;
      if (nbtTagCompound != null) {
         this.fireworkExplosions = nbtTagCompound.getTagList("Explosions", 10);
         if (this.fireworkExplosions.tagCount() == 0) {
            this.fireworkExplosions = null;
         } else {
            super.particleMaxAge = this.fireworkExplosions.tagCount() * 2 - 1;

            for (int i = 0; i < this.fireworkExplosions.tagCount(); i++) {
               NBTTagCompound nbttagcompound = this.fireworkExplosions.getCompoundTagAt(i);
               if (nbttagcompound.getBoolean("Flicker")) {
                  this.twinkle = true;
                  super.particleMaxAge += 15;
                  break;
               }
            }
         }
      }
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
   }

   @Override
   public void onUpdate() {
      int k = this.fireworkAge;
      if (k == 0 && this.fireworkExplosions != null) {
         boolean flag = this.func_92037_i();
         boolean flag1 = false;
         if (this.fireworkExplosions.tagCount() >= 3) {
            flag1 = true;
         } else {
            for (int i = 0; i < this.fireworkExplosions.tagCount(); i++) {
               NBTTagCompound nbttagcompound = this.fireworkExplosions.getCompoundTagAt(i);
               if (nbttagcompound.getByte("Type") == 1) {
                  flag1 = true;
                  break;
               }
            }
         }

         StringBuilder stringbuilder = new StringBuilder().append("fireworks.").append(flag1 ? "largeBlast" : "blast");
         StringBuilder stringbuilder4;
         String s3;
         if (flag) {
            stringbuilder4 = stringbuilder;
            s3 = "_far";
         } else {
            stringbuilder4 = stringbuilder;
            s3 = "";
         }

         String s1 = stringbuilder4.append(s3).toString();
         super.worldObj.playSound(super.posX, super.posY, super.posZ, s1, 20.0F, 0.95F + super.rand.nextFloat() * 0.1F, true);
      }

      int l = this.fireworkAge % 2;
      if (l == 0 && this.fireworkExplosions != null && this.fireworkAge / 2 < this.fireworkExplosions.tagCount()) {
         int j1 = this.fireworkAge / 2;
         NBTTagCompound nbttagcompound1 = this.fireworkExplosions.getCompoundTagAt(j1);
         byte b0 = nbttagcompound1.getByte("Type");
         boolean flag4 = nbttagcompound1.getBoolean("Trail");
         boolean flag2 = nbttagcompound1.getBoolean("Flicker");
         int[] aint = nbttagcompound1.getIntArray("Colors");
         int[] aint1 = nbttagcompound1.getIntArray("FadeColors");
         if (aint.length == 0) {
            aint = new int[]{ItemDye.dyeColors[0]};
         }

         if (b0 == 1) {
            this.createBall(0.5, 4, aint, aint1, flag4, flag2);
         } else if (b0 == 2) {
            this.createShaped(
               0.5,
               new double[][]{
                  {0.0, 1.0},
                  {0.3455, 0.309},
                  {0.9511, 0.309},
                  {0.3795918367346939, -0.12653061224489795},
                  {0.6122448979591837, -0.8040816326530612},
                  {0.0, -0.35918367346938773}
               },
               aint,
               aint1,
               flag4,
               flag2,
               false
            );
         } else if (b0 == 3) {
            this.createShaped(
               0.5,
               new double[][]{
                  {0.0, 0.2},
                  {0.2, 0.2},
                  {0.2, 0.6},
                  {0.6, 0.6},
                  {0.6, 0.2},
                  {0.2, 0.2},
                  {0.2, 0.0},
                  {0.4, 0.0},
                  {0.4, -0.6},
                  {0.2, -0.6},
                  {0.2, -0.4},
                  {0.0, -0.4}
               },
               aint,
               aint1,
               flag4,
               flag2,
               true
            );
         } else if (b0 == 4) {
            this.createBurst(aint, aint1, flag4, flag2);
         } else {
            this.createBall(0.25, 2, aint, aint1, flag4, flag2);
         }

         int j = aint[0];
         float f = (float)((j & 0xFF0000) >> 16) / 255.0F;
         float f1 = (float)((j & 0xFF00) >> 8) / 255.0F;
         float f2 = (float)((j & 0xFF) >> 0) / 255.0F;
         EntityFirework$OverlayFX entityfirework$overlayfx = new EntityFirework$OverlayFX(super.worldObj, super.posX, super.posY, super.posZ);
         entityfirework$overlayfx.setRBGColorF(f, f1, f2);
         this.theEffectRenderer.addEffect(entityfirework$overlayfx);
      }

      this.fireworkAge++;
      int i1 = this.fireworkAge;
      if (i1 > super.particleMaxAge) {
         if (this.twinkle) {
            boolean flag3 = this.func_92037_i();
            StringBuilder stringbuilder1 = new StringBuilder();
            StringBuilder stringbuilder2 = stringbuilder1.append("fireworks.");
            StringBuilder stringbuilder3;
            String s2;
            if (flag3) {
               stringbuilder3 = stringbuilder2;
               s2 = "twinkle_far";
            } else {
               stringbuilder3 = stringbuilder2;
               s2 = "twinkle";
            }

            String s = stringbuilder3.append(s2).toString();
            super.worldObj.playSound(super.posX, super.posY, super.posZ, s, 20.0F, 0.9F + super.rand.nextFloat() * 0.15F, true);
         }

         this.setDead();
      }
   }

   private boolean func_92037_i() {
      Minecraft minecraft = Minecraft.getMinecraft();
      if (minecraft != null) {
         Entity entity = minecraft.getRenderViewEntity();
         if (entity != null) {
            double d0;
            int i = (d0 = minecraft.getRenderViewEntity().getDistanceSq(super.posX, super.posY, super.posZ) - 256.0) == 0.0 ? 0 : (d0 < 0.0 ? -1 : 1);
            if (i < 0) {
               return false;
            }
         }
      }

      return true;
   }

   private void createParticle(double d, double e, double d2, double d3, double d4, double d5, int[] ints, int[] ints2, boolean bl, boolean bl2) {
      int b0 = 70;
      EntityFirework$SparkFX entityfirework$sparkfx = new EntityFirework$SparkFX(super.worldObj, d, e, d2, d3, d4, d5, this.theEffectRenderer);
      entityfirework$sparkfx.setAlphaF(0.99F);
      entityfirework$sparkfx.setTrail(bl);
      entityfirework$sparkfx.setTwinkle(bl2);
      int i = super.rand.nextInt(ints.length);
      entityfirework$sparkfx.setColour(ints[i]);
      if (ints2 != null && ints2.length > 0) {
         entityfirework$sparkfx.setFadeColour(ints2[super.rand.nextInt(ints2.length)]);
      }

      this.theEffectRenderer.addEffect(entityfirework$sparkfx);
   }

   private void createBall(double speed, int size, int[] colours, int[] fadeColours, boolean trail, boolean twinkleIn) {
      double d0 = super.posX;
      double d1 = super.posY;
      double d2 = super.posZ;

      for (int i = -size; i <= size; i++) {
         for (int j = -size; j <= size; j++) {
            for (int k = -size; k <= size; k++) {
               double d3 = (double)j + (super.rand.nextDouble() - super.rand.nextDouble()) * 0.5;
               double d4 = (double)i + (super.rand.nextDouble() - super.rand.nextDouble()) * 0.5;
               double d5 = (double)k + (super.rand.nextDouble() - super.rand.nextDouble()) * 0.5;
               double d6 = (double)MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5) / speed + super.rand.nextGaussian() * 0.05;
               this.createParticle(d0, d1, d2, d3 / d6, d4 / d6, d5 / d6, colours, fadeColours, trail, twinkleIn);
               if (i != -size && i != size && j != -size && j != size) {
                  k += size * 2 - 1;
               }
            }
         }
      }
   }

   private void createShaped(double speed, double[][] shape, int[] colours, int[] fadeColours, boolean trail, boolean twinkleIn, boolean bl) {
      double d0 = shape[0][0];
      double d1 = shape[0][1];
      this.createParticle(super.posX, super.posY, super.posZ, d0 * speed, d1 * speed, 0.0, colours, fadeColours, trail, twinkleIn);
      float f = super.rand.nextFloat() * (float) Math.PI;
      double d2 = bl ? 0.034 : 0.34;

      for (int i = 0; i < 3; i++) {
         double d3 = (double)f + (double)((float)i * (float) Math.PI) * d2;
         double d4 = d0;
         double d5 = d1;

         for (int j = 1; j < shape.length; j++) {
            double d6 = shape[j][0];
            double d7 = shape[j][1];

            for (double d8 = 0.25; d8 <= 1.0; d8 += 0.25) {
               double d9 = (d4 + (d6 - d4) * d8) * speed;
               double d10 = (d5 + (d7 - d5) * d8) * speed;
               double d11 = d9 * Math.sin(d3);
               d9 *= Math.cos(d3);

               for (double d12 = -1.0; d12 <= 1.0; d12 += 2.0) {
                  this.createParticle(super.posX, super.posY, super.posZ, d9 * d12, d10, d11 * d12, colours, fadeColours, trail, twinkleIn);
               }
            }

            d4 = d6;
            d5 = d7;
         }
      }
   }

   private void createBurst(int[] colours, int[] fadeColours, boolean trail, boolean twinkleIn) {
      double d0 = super.rand.nextGaussian() * 0.05;
      double d1 = super.rand.nextGaussian() * 0.05;

      for (int i = 0; i < 70; i++) {
         double d2 = super.motionX * 0.5 + super.rand.nextGaussian() * 0.15 + d0;
         double d3 = super.motionZ * 0.5 + super.rand.nextGaussian() * 0.15 + d1;
         double d4 = super.motionY * 0.5 + super.rand.nextDouble() * 0.5;
         this.createParticle(super.posX, super.posY, super.posZ, d2, d4, d3, colours, fadeColours, trail, twinkleIn);
      }
   }

   @Override
   public int getFXLayer() {
      return 0;
   }
}
