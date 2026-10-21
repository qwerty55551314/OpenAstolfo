package net.minecraft.client.audio;

import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class MovingSoundMinecartRiding extends MovingSound {
   private final EntityPlayer player;
   private final EntityMinecart minecart;
   private static final String b = "minecraft:minecart.inside";

   public MovingSoundMinecartRiding(EntityPlayer playerRiding, EntityMinecart minecart) {
      super(new ResourceLocation(b));
      this.player = playerRiding;
      this.minecart = minecart;
      super.attenuationType = ISound.AttenuationType.NONE;
      super.repeat = true;
      super.repeatDelay = 0;
   }

   @Override
   public void update() {
      if (!this.minecart.isDead && this.player.isRiding() && this.player.ridingEntity == this.minecart) {
         float f = MathHelper.sqrt_double(this.minecart.motionX * this.minecart.motionX + this.minecart.motionZ * this.minecart.motionZ);
         if ((double)f >= 0.01) {
            super.volume = 0.0F + MathHelper.clamp_float(f, 0.0F, 1.0F) * 0.75F;
         } else {
            super.volume = 0.0F;
         }
      } else {
         super.donePlaying = true;
      }
   }
}
