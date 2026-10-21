package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityEnderEye extends Entity {
   private double targetX;
   private double targetY;
   private double targetZ;
   private int despawnTimer;
   private boolean shatterOrDrop;

   public EntityEnderEye(World worldIn) {
      super(worldIn);
      this.setSize(0.25F, 0.25F);
   }

   @Override
   public void entityInit() {
   }

   @Override
   public boolean isInRangeToRenderDist(double distance) {
      double d0 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0;
      boolean flag = Double.isNaN(d0);
      if (flag) {
         d0 = 4.0;
      }

      d0 *= 64.0;
      double d1;
      int i = (d1 = distance - d0 * d0) == 0.0 ? 0 : (d1 < 0.0 ? -1 : 1);
      return i < 0;
   }

   public EntityEnderEye(World worldIn, double x, double y, double z) {
      super(worldIn);
      this.despawnTimer = 0;
      this.setSize(0.25F, 0.25F);
      this.setPosition(x, y, z);
   }

   public void moveTowards(BlockPos blockPos) {
      double d0 = (double)blockPos.getX();
      int i = blockPos.getY();
      double d1 = (double)blockPos.getZ();
      double d2 = d0 - super.posX;
      double d3 = d1 - super.posZ;
      float f = MathHelper.sqrt_double(d2 * d2 + d3 * d3);
      if (f > 12.0F) {
         this.targetX = super.posX + d2 / (double)f * 12.0;
         this.targetZ = super.posZ + d3 / (double)f * 12.0;
         this.targetY = super.posY + 8.0;
      } else {
         this.targetX = d0;
         this.targetY = (double)i;
         this.targetZ = d1;
      }

      this.despawnTimer = 0;
      this.shatterOrDrop = super.rand.nextInt(5) > 0;
   }

   @Override
   public void setVelocity(double x, double y, double z) {
      super.motionX = x;
      super.motionY = y;
      super.motionZ = z;
      if (super.prevRotationPitch == 0.0F && super.prevRotationYaw == 0.0F) {
         float f = MathHelper.sqrt_double(x * x + z * z);
         super.prevRotationYaw = super.rotationYaw = (float)(MathHelper.atan2(x, z) * 180.0 / Math.PI);
         super.prevRotationPitch = super.rotationPitch = (float)(MathHelper.atan2(y, (double)f) * 180.0 / Math.PI);
      }
   }

   @Override
   public void onUpdate() {
      super.lastTickPosX = super.posX;
      super.lastTickPosY = super.posY;
      super.lastTickPosZ = super.posZ;
      super.onUpdate();
      super.posX = super.posX + super.motionX;
      super.posY = super.posY + super.motionY;
      super.posZ = super.posZ + super.motionZ;
      float f = MathHelper.sqrt_double(super.motionX * super.motionX + super.motionZ * super.motionZ);
      super.rotationYaw = (float)(MathHelper.atan2(super.motionX, super.motionZ) * 180.0 / Math.PI);
      super.rotationPitch = (float)(MathHelper.atan2(super.motionY, (double)f) * 180.0 / Math.PI);

      while (super.rotationPitch - super.prevRotationPitch < -180.0F) {
         super.prevRotationPitch -= 360.0F;
      }

      while (super.rotationPitch - super.prevRotationPitch >= 180.0F) {
         super.prevRotationPitch += 360.0F;
      }

      while (super.rotationYaw - super.prevRotationYaw < -180.0F) {
         super.prevRotationYaw -= 360.0F;
      }

      while (super.rotationYaw - super.prevRotationYaw >= 180.0F) {
         super.prevRotationYaw += 360.0F;
      }

      super.rotationPitch = super.prevRotationPitch + (super.rotationPitch - super.prevRotationPitch) * 0.2F;
      super.rotationYaw = super.prevRotationYaw + (super.rotationYaw - super.prevRotationYaw) * 0.2F;
      if (!super.worldObj.isRemote) {
         double d0 = this.targetX - super.posX;
         double d1 = this.targetZ - super.posZ;
         float f1 = (float)Math.sqrt(d0 * d0 + d1 * d1);
         float f2 = (float)MathHelper.atan2(d1, d0);
         double d2 = (double)f + (double)(f1 - f) * 0.0025;
         float f4;
         int j = (f4 = f1 - 1.0F) == 0.0F ? 0 : (f4 < 0.0F ? -1 : 1);
         if (j < 0) {
            d2 *= 0.8;
            super.motionY *= 0.8;
         }

         super.motionX = Math.cos((double)f2) * d2;
         super.motionZ = Math.sin((double)f2) * d2;
         if (super.posY < this.targetY) {
            super.motionY = super.motionY + (1.0 - super.motionY) * 0.015F;
         } else {
            super.motionY = super.motionY + (-1.0 - super.motionY) * 0.015F;
         }
      }

      float f3 = 0.25F;
      if (this.isInWater()) {
         for (int i = 0; i < 4; i++) {
            super.worldObj
               .spawnParticle(
                  EnumParticleTypes.WATER_BUBBLE,
                  super.posX - super.motionX * (double)f3,
                  super.posY - super.motionY * (double)f3,
                  super.posZ - super.motionZ * (double)f3,
                  super.motionX,
                  super.motionY,
                  super.motionZ
               );
         }
      } else {
         super.worldObj
            .spawnParticle(
               EnumParticleTypes.PORTAL,
               super.posX - super.motionX * (double)f3 + super.rand.nextDouble() * 0.6 - 0.3,
               super.posY - super.motionY * (double)f3 - 0.5,
               super.posZ - super.motionZ * (double)f3 + super.rand.nextDouble() * 0.6 - 0.3,
               super.motionX,
               super.motionY,
               super.motionZ
            );
      }

      boolean flag = super.worldObj.isRemote;
      if (!flag) {
         this.setPosition(super.posX, super.posY, super.posZ);
         this.despawnTimer++;
         int k = this.despawnTimer;
         if (k > 80) {
            boolean flag1 = super.worldObj.isRemote;
            if (!flag1) {
               this.setDead();
               if (this.shatterOrDrop) {
                  super.worldObj.spawnEntityInWorld(new EntityItem(super.worldObj, super.posX, super.posY, super.posZ, new ItemStack(Items.ender_eye)));
               } else {
                  super.worldObj.playAuxSFX(2003, new BlockPos(this), 0);
               }
            }
         }
      }
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound tagCompound) {
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound tagCompund) {
   }

   @Override
   public float getBrightness(float partialTicks) {
      return 1.0F;
   }

   @Override
   public int getBrightnessForRender(float partialTicks) {
      return 15728880;
   }

   @Override
   public boolean canAttackWithItem() {
      return false;
   }
}
