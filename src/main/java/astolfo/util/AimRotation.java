package astolfo.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

public class AimRotation {
   private float yaw;
   private float pitch;
   private boolean gcdFixEnabled;
   private static String[] V;

   public AimRotation(float f, float g) {
      this.yaw = f;
      this.pitch = g;
   }

   public void applyTo(EntityPlayer entityPlayer) {
      boolean flag = Float.isNaN(this.yaw);
      if (!flag) {
         boolean flag1 = Float.isNaN(this.pitch);
         if (!flag1) {
            if (this.gcdFixEnabled) {
               this.applyGcdFix(Minecraft.getMinecraft().gameSettings.mouseSensitivity);
            }

            float f = MathHelper.wrapAngleTo180_float(this.yaw - entityPlayer.rotationYaw);
            this.yaw = entityPlayer.rotationYaw + f;
            entityPlayer.rotationYaw = this.yaw;
            entityPlayer.rotationPitch = this.pitch;
            return;
         }
      }
   }

   public void setGcdFixEnabled(boolean bl) {
      this.gcdFixEnabled = bl;
   }

   public void applyGcdFix(float f) {
      float f1 = f * 0.6F + 0.2F;
      float f1x = f1 * f1 * f1 * 1.2F;
      this.yaw = this.yaw - this.yaw % f1x;
      this.pitch = this.pitch - this.pitch % f1x;
   }

   public float getPitch() {
      return this.pitch;
   }

   public float getYaw() {
      return this.yaw;
   }

   public static void j(String[] stringArray) {
      V = stringArray;
   }

   public static String[] E() {
      return V;
   }

   static {
      j(new String[5]);
   }
}
