package net.minecraft.client.audio;

import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.util.ResourceLocation;

public class GuardianSound extends MovingSound {
   private final EntityGuardian guardian;
   private static final String b = "minecraft:mob.guardian.attack";

   public GuardianSound(EntityGuardian guardian) {
      super(new ResourceLocation(b));
      this.guardian = guardian;
      super.attenuationType = ISound.AttenuationType.NONE;
      super.repeat = true;
      super.repeatDelay = 0;
   }

   @Override
   public void update() {
      if (!this.guardian.isDead && this.guardian.hasTargetedEntity()) {
         super.xPosF = (float)this.guardian.posX;
         super.yPosF = (float)this.guardian.posY;
         super.zPosF = (float)this.guardian.posZ;
         float f = this.guardian.func_175477_p(0.0F);
         super.volume = 0.0F + 1.0F * f * f;
         super.pitch = 0.7F + 0.5F * f;
      } else {
         super.donePlaying = true;
      }
   }
}
