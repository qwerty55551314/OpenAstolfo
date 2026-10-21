package net.minecraft.client.renderer.texture;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Config;
import net.minecraft.util.MathHelper;
import shadersmod.client.ShadersTex;

public class TextureClock extends TextureAtlasSprite {
   private double currentAngle;
   private double angleDelta;

   public TextureClock(String iconName) {
      super(iconName);
   }

   @Override
   public void updateAnimation() {
      if (!super.framesTextureData.isEmpty()) {
         Minecraft minecraft = Minecraft.getMinecraft();
         double d0 = 0.0;
         if (minecraft.theWorld != null && minecraft.thePlayer != null) {
            d0 = (double)minecraft.theWorld.getCelestialAngle(1.0F);
            if (!minecraft.theWorld.provider.isSurfaceWorld()) {
               d0 = Math.random();
            }
         }

         double d1 = d0 - this.currentAngle;

         while (d1 < -0.5) {
            d1++;
         }

         while (d1 >= 0.5) {
            d1--;
         }

         d1 = MathHelper.clamp_double(d1, -1.0, 1.0);
         this.angleDelta += d1 * 0.1;
         this.angleDelta *= 0.8;
         this.currentAngle = this.currentAngle + this.angleDelta;
         int i = (int)((this.currentAngle + 1.0) * (double)super.framesTextureData.size()) % super.framesTextureData.size();

         while (i < 0) {
            i = (i + super.framesTextureData.size()) % super.framesTextureData.size();
         }

         if (i != super.frameCounter) {
            super.frameCounter = i;
            if (Config.isShaders()) {
               ShadersTex.uploadTexSub(
                  (int[][])super.framesTextureData.get(super.frameCounter), super.width, super.height, super.originX, super.originY, false, false
               );
            } else {
               TextureUtil.uploadTextureMipmap(
                  (int[][])super.framesTextureData.get(super.frameCounter), super.width, super.height, super.originX, super.originY, false, false
               );
            }
         }
      }
   }
}
