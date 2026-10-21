package net.minecraft.entity.item;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityXPOrb extends Entity {
   public int xpColor;
   public int xpOrbAge;
   public int delayBeforeCanPickup;
   private int xpOrbHealth = 5;
   private int xpValue;
   private EntityPlayer closestPlayer;
   private int xpTargetColor;

   public EntityXPOrb(World worldIn, double x, double y, double z, int expValue) {
      super(worldIn);
      this.setSize(0.5F, 0.5F);
      this.setPosition(x, y, z);
      super.rotationYaw = (float)(Math.random() * 360.0);
      super.motionX = (double)((float)(Math.random() * 0.2F - 0.1F) * 2.0F);
      super.motionY = (double)((float)(Math.random() * 0.2) * 2.0F);
      super.motionZ = (double)((float)(Math.random() * 0.2F - 0.1F) * 2.0F);
      this.xpValue = expValue;
   }

   @Override
   public boolean canTriggerWalking() {
      return false;
   }

   public EntityXPOrb(World worldIn) {
      super(worldIn);
      this.setSize(0.25F, 0.25F);
   }

   @Override
   public void entityInit() {
   }

   @Override
   public int getBrightnessForRender(float partialTicks) {
      float f = 0.5F;
      f = MathHelper.clamp_float(f, 0.0F, 1.0F);
      int i = super.getBrightnessForRender(partialTicks);
      int j = i & 0xFF;
      int k = i >> 16 & 0xFF;
      j += (int)(f * 15.0F * 16.0F);
      if (j > 240) {
         j = 240;
      }

      return j | k << 16;
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (this.delayBeforeCanPickup > 0) {
         this.delayBeforeCanPickup--;
      }

      super.prevPosX = super.posX;
      super.prevPosY = super.posY;
      super.prevPosZ = super.posZ;
      super.motionY -= 0.03F;
      if (super.worldObj.getBlockState(new BlockPos(this)).getBlock().getMaterial() == Material.lava) {
         super.motionY = 0.2F;
         super.motionX = (double)((super.rand.nextFloat() - super.rand.nextFloat()) * 0.2F);
         super.motionZ = (double)((super.rand.nextFloat() - super.rand.nextFloat()) * 0.2F);
         this.playSound("random.fizz", 0.4F, 2.0F + super.rand.nextFloat() * 0.4F);
      }

      this.pushOutOfBlocks(super.posX, (this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0, super.posZ);
      double d0 = 8.0;
      if (this.xpTargetColor < this.xpColor - 20 + this.getEntityId() % 100) {
         if (this.closestPlayer == null || this.closestPlayer.getDistanceSqToEntity(this) > d0 * d0) {
            this.closestPlayer = super.worldObj.getClosestPlayerToEntity(this, d0);
         }

         this.xpTargetColor = this.xpColor;
      }

      EntityPlayer entityplayer = this.closestPlayer;
      if (entityplayer != null && this.closestPlayer.isSpectator()) {
         this.closestPlayer = null;
      }

      if (this.closestPlayer != null) {
         double d1 = (this.closestPlayer.posX - super.posX) / d0;
         double d2 = (this.closestPlayer.posY + (double)this.closestPlayer.getEyeHeight() - super.posY) / d0;
         double d3 = (this.closestPlayer.posZ - super.posZ) / d0;
         double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
         double d5 = 1.0 - d4;
         if (d5 > 0.0) {
            d5 *= d5;
            super.motionX += d1 / d4 * d5 * 0.1;
            super.motionY += d2 / d4 * d5 * 0.1;
            super.motionZ += d3 / d4 * d5 * 0.1;
         }
      }

      this.moveEntity(super.motionX, super.motionY, super.motionZ);
      float f = 0.98F;
      boolean flag = super.onGround;
      if (flag) {
         f = super.worldObj
               .getBlockState(
                  new BlockPos(
                     MathHelper.floor_double(super.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY) - 1, MathHelper.floor_double(super.posZ)
                  )
               )
               .getBlock()
               .slipperiness
            * 0.98F;
      }

      super.motionX *= (double)f;
      super.motionY *= 0.98F;
      super.motionZ *= (double)f;
      boolean flag1 = super.onGround;
      if (flag1) {
         super.motionY *= -0.9F;
      }

      this.xpColor++;
      this.xpOrbAge++;
      if (this.xpOrbAge >= 6000) {
         this.setDead();
      }
   }

   @Override
   public boolean handleWaterMovement() {
      return super.worldObj.handleMaterialAcceleration(this.getEntityBoundingBox(), Material.water, this);
   }

   @Override
   public void dealFireDamage(int amount) {
      this.attackEntityFrom(DamageSource.inFire, (float)amount);
   }

   @Override
   public boolean attackEntityFrom(DamageSource source, float amount) {
      boolean flag = this.isEntityInvulnerable(source);
      if (flag) {
         return false;
      } else {
         this.setBeenAttacked();
         this.xpOrbHealth = (int)((float)this.xpOrbHealth - amount);
         int i = this.xpOrbHealth;
         if (i <= 0) {
            this.setDead();
         }

         return false;
      }
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound tagCompound) {
      tagCompound.setShort("Health", (short)((byte)this.xpOrbHealth));
      tagCompound.setShort("Age", (short)this.xpOrbAge);
      tagCompound.setShort("Value", (short)this.xpValue);
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound tagCompund) {
      this.xpOrbHealth = tagCompund.getShort("Health") & 255;
      this.xpOrbAge = tagCompund.getShort("Age");
      this.xpValue = tagCompund.getShort("Value");
   }

   @Override
   public void onCollideWithPlayer(EntityPlayer entityIn) {
      boolean flag = super.worldObj.isRemote;
      if (!flag) {
         int i = this.delayBeforeCanPickup;
         if (i == 0 && entityIn.xpCooldown == 0) {
            entityIn.xpCooldown = 2;
            super.worldObj.playSoundAtEntity(entityIn, "random.orb", 0.1F, 0.5F * ((super.rand.nextFloat() - super.rand.nextFloat()) * 0.7F + 1.8F));
            entityIn.onItemPickup(this, 1);
            entityIn.addExperience(this.xpValue);
            this.setDead();
         }
      }
   }

   public int getXpValue() {
      return this.xpValue;
   }

   public int getTextureByXP() {
      int i = this.xpValue;
      int j2;
      if (i >= 2477) {
         j2 = 10;
      } else {
         int j = this.xpValue;
         if (j >= 1237) {
            j2 = 9;
         } else {
            int k = this.xpValue;
            if (k >= 617) {
               j2 = 8;
            } else {
               int l = this.xpValue;
               if (l >= 307) {
                  j2 = 7;
               } else {
                  int i1 = this.xpValue;
                  if (i1 >= 149) {
                     j2 = 6;
                  } else {
                     int j1 = this.xpValue;
                     if (j1 >= 73) {
                        j2 = 5;
                     } else {
                        int k1 = this.xpValue;
                        if (k1 >= 37) {
                           j2 = 4;
                        } else {
                           int l1 = this.xpValue;
                           if (l1 >= 17) {
                              j2 = 3;
                           } else {
                              int i2 = this.xpValue;
                              j2 = i2 >= 7 ? 2 : (this.xpValue >= 3 ? 1 : 0);
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return j2;
   }

   public static int getXPSplit(int expValue) {
      return expValue >= 2477
         ? 2477
         : (
            expValue >= 1237
               ? 1237
               : (
                  expValue >= 617
                     ? 617
                     : (
                        expValue >= 307
                           ? 307
                           : (
                              expValue >= 149
                                 ? 149
                                 : (expValue >= 73 ? 73 : (expValue >= 37 ? 37 : (expValue >= 17 ? 17 : (expValue >= 7 ? 7 : (expValue >= 3 ? 3 : 1)))))
                           )
                     )
               )
         );
   }

   @Override
   public boolean canAttackWithItem() {
      return false;
   }
}
