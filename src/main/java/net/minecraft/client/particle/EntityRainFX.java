package net.minecraft.client.particle;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityRainFX extends EntityFX {
   private static final long hb = 7183974934530490387L;

   public EntityRainFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn) {
      super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0, 0.0, 0.0);
      super.motionX *= 0.3F;
      super.motionY = Math.random() * 0.2F + 0.1F;
      super.motionZ *= 0.3F;
      super.particleRed = 1.0F;
      super.particleGreen = 1.0F;
      super.particleBlue = 1.0F;
      this.setParticleTextureIndex((int)hb + super.rand.nextInt(4));
      this.setSize(0.01F, 0.01F);
      super.particleGravity = 0.06F;
      super.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
   }

   @Override
   public void onUpdate() {
      boolean flag2 = false;
      super.prevPosX = super.posX;
      super.prevPosY = super.posY;
      super.prevPosZ = super.posZ;
      super.motionY = super.motionY - (double)super.particleGravity;
      this.moveEntity(super.motionX, super.motionY, super.motionZ);
      super.motionX *= 0.98F;
      super.motionY *= 0.98F;
      super.motionZ *= 0.98F;
      int i = super.particleMaxAge--;
      if (i <= 0) {
         this.setDead();
      }

      boolean flag = super.onGround;
      if (flag) {
         if (Math.random() < 0.5) {
            this.setDead();
         }

         super.motionX *= 0.7F;
         super.motionZ *= 0.7F;
      }

      BlockPos blockpos = new BlockPos(this);
      IBlockState iblockstate = super.worldObj.getBlockState(blockpos);
      Block block = iblockstate.getBlock();
      block.setBlockBoundsBasedOnState(super.worldObj, blockpos);
      Material material = iblockstate.getBlock().getMaterial();
      boolean flag1 = material.isLiquid();
      if (flag1 || material.isSolid()) {
         Block block1 = iblockstate.getBlock();
         double d0;
         if (block1 instanceof BlockLiquid) {
            d0 = (double)(1.0F - BlockLiquid.getLiquidHeightPercent(iblockstate.getValue(BlockLiquid.LEVEL)));
         } else {
            d0 = block.getBlockBoundsMaxY();
         }

         double d1 = (double)MathHelper.floor_double(super.posY) + d0;
         if (super.posY < d1) {
            this.setDead();
         }
      }
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... ints
      ) {
         return new EntityRainFX(worldIn, xCoordIn, yCoordIn, zCoordIn);
      }
   }
}
