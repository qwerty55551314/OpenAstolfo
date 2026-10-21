package net.minecraft.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import org.apache.commons.lang3.Validate;

public abstract class EntityHanging extends Entity {
   private int tickCounter1;
   public BlockPos hangingPosition;
   public EnumFacing facingDirection;

   public EntityHanging(World worldIn) {
      super(worldIn);
      this.setSize(0.5F, 0.5F);
   }

   public EntityHanging(World worldIn, BlockPos hangingPositionIn) {
      this(worldIn);
      this.hangingPosition = hangingPositionIn;
   }

   @Override
   public void entityInit() {
   }

   public void updateFacingWithBoundingBox(EnumFacing facingDirectionIn) {
      Validate.notNull(facingDirectionIn);
      Validate.isTrue(facingDirectionIn.getAxis().isHorizontal());
      this.facingDirection = facingDirectionIn;
      super.prevRotationYaw = super.rotationYaw = (float)(this.facingDirection.getHorizontalIndex() * 90);
      this.updateBoundingBox();
   }

   private void updateBoundingBox() {
      if (this.facingDirection != null) {
         double d0 = (double)this.hangingPosition.getX() + 0.5;
         double d1 = (double)this.hangingPosition.getY() + 0.5;
         double d2 = (double)this.hangingPosition.getZ() + 0.5;
         double d3 = this.func_174858_a(this.getWidthPixels());
         double d4 = this.func_174858_a(this.getHeightPixels());
         d0 -= (double)this.facingDirection.getFrontOffsetX() * 0.46875;
         d2 -= (double)this.facingDirection.getFrontOffsetZ() * 0.46875;
         d1 += d4;
         EnumFacing enumfacing = this.facingDirection.rotateYCCW();
         d0 += d3 * (double)enumfacing.getFrontOffsetX();
         d2 += d3 * (double)enumfacing.getFrontOffsetZ();
         super.posX = d0;
         super.posY = d1;
         super.posZ = d2;
         double d5 = (double)this.getWidthPixels();
         double d6 = (double)this.getHeightPixels();
         double d7 = (double)this.getWidthPixels();
         if (this.facingDirection.getAxis() == EnumFacing.Axis.Z) {
            d7 = 1.0;
         } else {
            d5 = 1.0;
         }

         d5 /= 32.0;
         d6 /= 32.0;
         d7 /= 32.0;
         this.setEntityBoundingBox(new AxisAlignedBB(d0 - d5, d1 - d6, d2 - d7, d0 + d5, d1 + d6, d2 + d7));
      }
   }

   private double func_174858_a(int i) {
      return i % 32 == 0 ? 0.5 : 0.0;
   }

   @Override
   public void onUpdate() {
      super.prevPosX = super.posX;
      super.prevPosY = super.posY;
      super.prevPosZ = super.posZ;
      int i = this.tickCounter1++;
      if (i == 100) {
         boolean flag = super.worldObj.isRemote;
         if (!flag) {
            this.tickCounter1 = 0;
            boolean flag1 = super.isDead;
            if (!flag1 && !this.onValidSurface()) {
               this.setDead();
               this.onBroken((Entity)null);
            }
         }
      }
   }

   public boolean onValidSurface() {
      boolean flag = super.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty();
      if (!flag) {
         return false;
      } else {
         int i = Math.max(1, this.getWidthPixels() / 16);
         int j = Math.max(1, this.getHeightPixels() / 16);
         BlockPos blockpos = this.hangingPosition.offset(this.facingDirection.getOpposite());
         EnumFacing enumfacing = this.facingDirection.rotateYCCW();

         for (int k = 0; k < i; k++) {
            for (int l = 0; l < j; l++) {
               BlockPos blockpos1 = blockpos.offset(enumfacing, k).up(l);
               Block block = super.worldObj.getBlockState(blockpos1).getBlock();
               if (!block.getMaterial().isSolid() && !BlockRedstoneDiode.isRedstoneRepeaterBlockID(block)) {
                  return false;
               }
            }
         }

         for (Entity entity : super.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox())) {
            boolean flag1 = entity instanceof EntityHanging;
            if (flag1) {
               return false;
            }
         }

         return true;
      }
   }

   @Override
   public boolean canBeCollidedWith() {
      return true;
   }

   @Override
   public boolean hitByEntity(Entity entityIn) {
      return entityIn instanceof EntityPlayer ? this.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)entityIn), 0.0F) : false;
   }

   @Override
   public EnumFacing getHorizontalFacing() {
      return this.facingDirection;
   }

   @Override
   public boolean attackEntityFrom(DamageSource source, float amount) {
      boolean flag = this.isEntityInvulnerable(source);
      if (flag) {
         return false;
      } else {
         boolean flag1 = super.isDead;
         if (!flag1 && !super.worldObj.isRemote) {
            this.setDead();
            this.setBeenAttacked();
            this.onBroken(source.getEntity());
         }

         return true;
      }
   }

   @Override
   public void moveEntity(double x, double y, double z) {
      boolean flag = super.worldObj.isRemote;
      if (!flag) {
         boolean flag1 = super.isDead;
         if (!flag1 && x * x + y * y + z * z > 0.0) {
            this.setDead();
            this.onBroken((Entity)null);
         }
      }
   }

   @Override
   public void addVelocity(double x, double y, double z) {
      boolean flag = super.worldObj.isRemote;
      if (!flag) {
         boolean flag1 = super.isDead;
         if (!flag1 && x * x + y * y + z * z > 0.0) {
            this.setDead();
            this.onBroken((Entity)null);
         }
      }
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound tagCompound) {
      tagCompound.setByte("Facing", (byte)this.facingDirection.getHorizontalIndex());
      tagCompound.setInteger("TileX", this.getHangingPosition().getX());
      tagCompound.setInteger("TileY", this.getHangingPosition().getY());
      tagCompound.setInteger("TileZ", this.getHangingPosition().getZ());
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound tagCompund) {
      this.hangingPosition = new BlockPos(tagCompund.getInteger("TileX"), tagCompund.getInteger("TileY"), tagCompund.getInteger("TileZ"));
      boolean flag = tagCompund.hasKey("Direction", 99);
      EnumFacing enumfacing;
      if (flag) {
         enumfacing = EnumFacing.getHorizontal(tagCompund.getByte("Direction"));
         this.hangingPosition = this.hangingPosition.offset(enumfacing);
      } else {
         boolean flag1 = tagCompund.hasKey("Facing", 99);
         if (flag1) {
            enumfacing = EnumFacing.getHorizontal(tagCompund.getByte("Facing"));
         } else {
            enumfacing = EnumFacing.getHorizontal(tagCompund.getByte("Dir"));
         }
      }

      this.updateFacingWithBoundingBox(enumfacing);
   }

   public abstract int getWidthPixels();

   public abstract int getHeightPixels();

   public abstract void onBroken(Entity var1);

   @Override
   public boolean shouldSetPosAfterLoading() {
      return false;
   }

   @Override
   public void setPosition(double x, double y, double z) {
      super.posX = x;
      super.posY = y;
      super.posZ = z;
      BlockPos blockpos = this.hangingPosition;
      this.hangingPosition = new BlockPos(x, y, z);
      if (!this.hangingPosition.equals(blockpos)) {
         this.updateBoundingBox();
         super.isAirBorne = true;
      }
   }

   public BlockPos getHangingPosition() {
      return this.hangingPosition;
   }
}
