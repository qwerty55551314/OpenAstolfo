package astolfo.render;

import astolfo.util.MathUtil;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class DamageParticle extends EntityFX {
   public String text;
   public boolean throughWalls = true;
   public boolean growing = true;
   public boolean grow;
   public float extraScale = 1.0F;
   private final float damage;
   private final float baseScale;
   private final Color healColor;
   private final Color damageColor;
   private final boolean staticParticle;

   public DamageParticle(
      int i,
      World world,
      double d,
      double e,
      double d2,
      double d3,
      double d4,
      double d5,
      float f,
      float g,
      int j,
      boolean bl,
      boolean bl2,
      boolean bl3,
      Color color,
      Color color2
   ) {
      super(world, d, e, d2, d3, d4, d5);
      super.particleTextureJitterX = 0.0F;
      super.particleTextureJitterY = 0.0F;
      super.particleGravity = f;
      super.particleScale = g;
      this.baseScale = g;
      this.grow = bl;
      super.particleMaxAge = j;
      this.staticParticle = bl2;
      this.healColor = color;
      this.damageColor = color2;
      this.damage = bl3 ? (float)i / 2.0F : (float)i;
      this.text = MathUtil.ONE_DECIMAL.format((double)Math.abs(this.damage));
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
      float f = -Minecraft.getMinecraft().thePlayer.rotationYaw;
      float f1 = Minecraft.getMinecraft().thePlayer.rotationPitch;
      float f2 = (float)(super.prevPosX + (super.posX - super.prevPosX) * (double)partialTicks - EntityFX.interpPosX);
      float f3 = (float)(super.prevPosY + (super.posY - super.prevPosY) * (double)rotationX - EntityFX.interpPosY);
      float f4 = (float)(super.prevPosZ + (super.posZ - super.prevPosZ) * (double)rotationZ - EntityFX.interpPosZ);
      GL11.glPushMatrix();
      boolean flag = this.throughWalls;
      if (flag) {
         GL11.glDepthFunc(519);
      } else {
         GL11.glDepthFunc(515);
      }

      GL11.glTranslatef(f2, f3, f4);
      GL11.glRotatef(f, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(f1, 1.0F, 0.0F, 0.0F);
      GL11.glScalef(-1.0F, -1.0F, 1.0F);
      GL11.glScaled((double)super.particleScale * 0.008, (double)super.particleScale * 0.008, (double)super.particleScale * 0.008);
      GL11.glScaled((double)this.extraScale, (double)this.extraScale, (double)this.extraScale);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 0.003662109F);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDepthMask(true);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDisable(2896);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glEnable(3008);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      int i = this.damageColor.getRGB();
      float f5;
      int j = (f5 = this.damage - 0.0F) == 0.0F ? 0 : (f5 < 0.0F ? -1 : 1);
      if (j < 0) {
         i = this.healColor.getRGB();
      }

      FontRenderer fontrenderer = Minecraft.getMinecraft().fontRendererObj;
      fontrenderer.drawStringWithShadow(
         this.text,
         (float)(-MathHelper.floor_float((float)fontrenderer.getStringWidth(this.text) / 2.0F) + 1),
         (float)(-MathHelper.floor_float((float)fontrenderer.FONT_HEIGHT / 2.0F) + 1),
         i
      );
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glDepthFunc(515);
      GL11.glPopMatrix();
      boolean flag1 = this.grow;
      if (flag1) {
         if (this.growing) {
            super.particleScale *= 1.08F;
            if ((double)super.particleScale > (double)this.baseScale * 3.0) {
               this.growing = false;
            }
         } else {
            super.particleScale *= 0.96F;
         }
      }
   }

   @Override
   public void onUpdate() {
      if (this.staticParticle) {
         if (super.particleAge++ >= super.particleMaxAge) {
            this.setDead();
         }
      } else {
         super.onUpdate();
      }
   }

   @Override
   public int getFXLayer() {
      return 3;
   }
}
