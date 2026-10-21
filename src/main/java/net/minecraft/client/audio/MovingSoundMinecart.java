package net.minecraft.client.audio;

import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class MovingSoundMinecart extends MovingSound {
   private final EntityMinecart minecart;
   private float distance = 0.0F;
   private static final String b = "minecraft:minecart.base";

   public MovingSoundMinecart(EntityMinecart minecartIn) {
      super(new ResourceLocation(b));
      this.minecart = minecartIn;
      super.repeat = true;
      super.repeatDelay = 0;
   }

   @Override
   public void update() {
      if (this.minecart.isDead) {
         super.donePlaying = true;
      } else {
         super.xPosF = (float)this.minecart.posX;
         super.yPosF = (float)this.minecart.posY;
         super.zPosF = (float)this.minecart.posZ;
         float f = MathHelper.sqrt_double(this.minecart.motionX * this.minecart.motionX + this.minecart.motionZ * this.minecart.motionZ);
         if ((double)f >= 0.01) {
            this.distance = MathHelper.clamp_float(this.distance + 0.0025F, 0.0F, 1.0F);
            super.volume = 0.0F + MathHelper.clamp_float(f, 0.0F, 0.5F) * 0.7F;
         } else {
            this.distance = 0.0F;
            super.volume = 0.0F;
         }
      }
   }
}
