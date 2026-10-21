package net.minecraft.client.particle;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityDiggingFX extends EntityFX {
   private IBlockState sourceState;
   private BlockPos sourcePos;

   public EntityDiggingFX(
      World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, IBlockState state
   ) {
      super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
      this.sourceState = state;
      this.setParticleIcon(Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(state));
      super.particleGravity = state.getBlock().blockParticleGravity;
      super.particleRed = super.particleGreen = super.particleBlue = 0.6F;
      super.particleScale /= 2.0F;
   }

   public EntityDiggingFX setBlockPos(BlockPos pos) {
      this.sourcePos = pos;
      if (this.sourceState.getBlock() == Blocks.grass) {
         return this;
      } else {
         int i = this.sourceState.getBlock().colorMultiplier(super.worldObj, pos);
         super.particleRed *= (float)(i >> 16 & 0xFF) / 255.0F;
         super.particleGreen *= (float)(i >> 8 & 0xFF) / 255.0F;
         super.particleBlue *= (float)(i & 0xFF) / 255.0F;
         return this;
      }
   }

   public EntityDiggingFX func_174845_l() {
      int b0 = 70;
      this.sourcePos = new BlockPos(super.posX, super.posY, super.posZ);
      Block block = this.sourceState.getBlock();
      if (block == Blocks.grass) {
         return this;
      } else {
         int i = block.getRenderColor(this.sourceState);
         super.particleRed *= (float)(i >> 16 & 0xFF) / 255.0F;
         super.particleGreen *= (float)(i >> 8 & 0xFF) / 255.0F;
         super.particleBlue *= (float)(i & 0xFF) / 255.0F;
         return this;
      }
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

   @Override
   public int getBrightnessForRender(float partialTicks) {
      int i = super.getBrightnessForRender(partialTicks);
      int j = 0;
      boolean flag = super.worldObj.isBlockLoaded(this.sourcePos);
      if (flag) {
         j = super.worldObj.getCombinedLight(this.sourcePos, 0);
      }

      return i == 0 ? j : i;
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... ints
      ) {
         return new EntityDiggingFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, Block.getStateById(ints[0])).func_174845_l();
      }
   }
}
