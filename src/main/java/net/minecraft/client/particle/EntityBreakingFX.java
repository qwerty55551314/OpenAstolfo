package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntityBreakingFX extends EntityFX {
   public EntityBreakingFX(World worldIn, double posXIn, double posYIn, double posZIn, Item item) {
      this(worldIn, posXIn, posYIn, posZIn, item, 0);
   }

   public EntityBreakingFX(World worldIn, double posXIn, double posYIn, double posZIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, Item item, int i) {
      this(worldIn, posXIn, posYIn, posZIn, item, i);
      super.motionX *= 0.1F;
      super.motionY *= 0.1F;
      super.motionZ *= 0.1F;
      super.motionX += xSpeedIn;
      super.motionY += ySpeedIn;
      super.motionZ += zSpeedIn;
   }

   public EntityBreakingFX(World worldIn, double posXIn, double posYIn, double posZIn, Item item, int i) {
      super(worldIn, posXIn, posYIn, posZIn, 0.0, 0.0, 0.0);
      this.setParticleIcon(Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(item, i));
      super.particleRed = super.particleGreen = super.particleBlue = 1.0F;
      super.particleGravity = Blocks.snow.blockParticleGravity;
      super.particleScale /= 2.0F;
   }

   @Override
   public int getFXLayer() {
      return 1;
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
      float f = ((float)super.particleTextureIndexX + super.particleTextureJitterX / 4.0F) / 16.0F;
      float f1 = f + 0.015609375F;
      float f2 = ((float)super.particleTextureIndexY + super.particleTextureJitterY / 4.0F) / 16.0F;
      float f3 = f2 + 0.015609375F;
      float f4 = 0.1F * super.particleScale;
      if (super.particleIcon != null) {
         f = super.particleIcon.getInterpolatedU((double)(super.particleTextureJitterX / 4.0F * 16.0F));
         f1 = super.particleIcon.getInterpolatedU((double)((super.particleTextureJitterX + 1.0F) / 4.0F * 16.0F));
         f2 = super.particleIcon.getInterpolatedV((double)(super.particleTextureJitterY / 4.0F * 16.0F));
         f3 = super.particleIcon.getInterpolatedV((double)((super.particleTextureJitterY + 1.0F) / 4.0F * 16.0F));
      }

      float f5 = (float)(super.prevPosX + (super.posX - super.prevPosX) * (double)partialTicks - EntityFX.interpPosX);
      float f6 = (float)(super.prevPosY + (super.posY - super.prevPosY) * (double)partialTicks - EntityFX.interpPosY);
      float f7 = (float)(super.prevPosZ + (super.posZ - super.prevPosZ) * (double)partialTicks - EntityFX.interpPosZ);
      int i = this.getBrightnessForRender(partialTicks);
      int j = i >> 16 & 65535;
      int k = i & 65535;
      worldRendererIn.pos((double)(f5 - rotationX * f4 - rotationXY * f4), (double)(f6 - rotationZ * f4), (double)(f7 - rotationYZ * f4 - rotationXZ * f4))
         .tex((double)f, (double)f3)
         .color(super.particleRed, super.particleGreen, super.particleBlue, 1.0F)
         .lightmap(j, k)
         .endVertex();
      worldRendererIn.pos((double)(f5 - rotationX * f4 + rotationXY * f4), (double)(f6 + rotationZ * f4), (double)(f7 - rotationYZ * f4 + rotationXZ * f4))
         .tex((double)f, (double)f2)
         .color(super.particleRed, super.particleGreen, super.particleBlue, 1.0F)
         .lightmap(j, k)
         .endVertex();
      worldRendererIn.pos((double)(f5 + rotationX * f4 + rotationXY * f4), (double)(f6 + rotationZ * f4), (double)(f7 + rotationYZ * f4 + rotationXZ * f4))
         .tex((double)f1, (double)f2)
         .color(super.particleRed, super.particleGreen, super.particleBlue, 1.0F)
         .lightmap(j, k)
         .endVertex();
      worldRendererIn.pos((double)(f5 + rotationX * f4 - rotationXY * f4), (double)(f6 - rotationZ * f4), (double)(f7 + rotationYZ * f4 - rotationXZ * f4))
         .tex((double)f1, (double)f3)
         .color(super.particleRed, super.particleGreen, super.particleBlue, 1.0F)
         .lightmap(j, k)
         .endVertex();
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... ints
      ) {
         int i = ints.length > 1 ? ints[1] : 0;
         return new EntityBreakingFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, Item.getItemById(ints[0]), i);
      }
   }

   public static class SlimeFactory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... ints
      ) {
         return new EntityBreakingFX(worldIn, xCoordIn, yCoordIn, zCoordIn, Items.slime_ball);
      }
   }

   public static class SnowballFactory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... ints
      ) {
         return new EntityBreakingFX(worldIn, xCoordIn, yCoordIn, zCoordIn, Items.snowball);
      }
   }
}
