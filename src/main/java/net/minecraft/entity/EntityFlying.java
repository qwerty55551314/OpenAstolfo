package net.minecraft.entity;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class EntityFlying extends EntityLiving {
   public EntityFlying(World worldIn) {
      super(worldIn);
   }

   @Override
   public void fall(float distance, float damageMultiplier) {
   }

   @Override
   public void updateFallState(double y, boolean onGroundIn, Block blockIn, BlockPos pos) {
   }

   @Override
   public void moveEntityWithHeading(float strafe, float forward) {
      boolean flag = this.isInWater();
      if (flag) {
         this.moveFlying(strafe, forward, 0.02F);
         this.moveEntity(super.motionX, super.motionY, super.motionZ);
         super.motionX *= 0.8F;
         super.motionY *= 0.8F;
         super.motionZ *= 0.8F;
      } else if (this.isInLava()) {
         this.moveFlying(strafe, forward, 0.02F);
         this.moveEntity(super.motionX, super.motionY, super.motionZ);
         super.motionX *= 0.5;
         super.motionY *= 0.5;
         super.motionZ *= 0.5;
      } else {
         float f = 0.91F;
         if (super.onGround) {
            f = super.worldObj
                  .getBlockState(
                     new BlockPos(
                        MathHelper.floor_double(super.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY) - 1, MathHelper.floor_double(super.posZ)
                     )
                  )
                  .getBlock()
                  .slipperiness
               * 0.91F;
         }

         float f1 = 0.16277136F / (f * f * f);
         this.moveFlying(strafe, forward, super.onGround ? 0.1F * f1 : 0.02F);
         f = 0.91F;
         if (super.onGround) {
            f = super.worldObj
                  .getBlockState(
                     new BlockPos(
                        MathHelper.floor_double(super.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY) - 1, MathHelper.floor_double(super.posZ)
                     )
                  )
                  .getBlock()
                  .slipperiness
               * 0.91F;
         }

         this.moveEntity(super.motionX, super.motionY, super.motionZ);
         super.motionX *= (double)f;
         super.motionY *= (double)f;
         super.motionZ *= (double)f;
      }

      super.prevLimbSwingAmount = super.limbSwingAmount;
      double d1 = super.posX - super.prevPosX;
      double d0 = super.posZ - super.prevPosZ;
      float f2 = MathHelper.sqrt_double(d1 * d1 + d0 * d0) * 4.0F;
      if (f2 > 1.0F) {
         f2 = 1.0F;
      }

      super.limbSwingAmount = super.limbSwingAmount + (f2 - super.limbSwingAmount) * 0.4F;
      super.limbSwing = super.limbSwing + super.limbSwingAmount;
   }

   @Override
   public boolean isOnLadder() {
      return false;
   }
}
