package net.minecraft.client.renderer.texture;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Config;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import shadersmod.client.ShadersTex;

public class TextureCompass extends TextureAtlasSprite {
   public double currentAngle;
   public double angleDelta;
   public static String locationSprite;

   public TextureCompass(String iconName) {
      super(iconName);
      locationSprite = iconName;
   }

   @Override
   public void updateAnimation() {
      Minecraft minecraft = Minecraft.getMinecraft();
      if (minecraft.theWorld != null && minecraft.thePlayer != null) {
         this.updateCompass(minecraft.theWorld, minecraft.thePlayer.posX, minecraft.thePlayer.posZ, (double)minecraft.thePlayer.rotationYaw, false, false);
      } else {
         this.updateCompass((World)null, 0.0, 0.0, 0.0, true, false);
      }
   }

   public void updateCompass(World worldIn, double d, double e, double d2, boolean bl, boolean bl2) {
      if (!super.framesTextureData.isEmpty()) {
         double d0 = 0.0;
         if (worldIn != null && !bl) {
            BlockPos blockpos = worldIn.getSpawnPoint();
            double d1 = (double)blockpos.getX() - d;
            double d21 = (double)blockpos.getZ() - e;
            d2 %= 360.0;
            d0 = -((d2 - 90.0) * Math.PI / 180.0 - Math.atan2(d21, d1));
            if (!worldIn.provider.isSurfaceWorld()) {
               d0 = Math.random() * Math.PI * 2.0;
            }
         }

         if (bl2) {
            this.currentAngle = d0;
         } else {
            double d3 = d0 - this.currentAngle;

            while (d3 < -Math.PI) {
               d3 += Math.PI * 2;
            }

            while (d3 >= Math.PI) {
               d3 -= Math.PI * 2;
            }

            d3 = MathHelper.clamp_double(d3, -1.0, 1.0);
            this.angleDelta += d3 * 0.1;
            this.angleDelta *= 0.8;
            this.currentAngle = this.currentAngle + this.angleDelta;
         }

         int i = (int)((this.currentAngle / (Math.PI * 2) + 1.0) * (double)super.framesTextureData.size()) % super.framesTextureData.size();

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
