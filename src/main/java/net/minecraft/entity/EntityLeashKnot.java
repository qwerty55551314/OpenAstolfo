package net.minecraft.entity;

import net.minecraft.block.BlockFence;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class EntityLeashKnot extends EntityHanging {
   public EntityLeashKnot(World worldIn) {
      super(worldIn);
   }

   public EntityLeashKnot(World worldIn, BlockPos hangingPositionIn) {
      super(worldIn, hangingPositionIn);
      this.setPosition((double)hangingPositionIn.getX() + 0.5, (double)hangingPositionIn.getY() + 0.5, (double)hangingPositionIn.getZ() + 0.5);
      this.setEntityBoundingBox(
         new AxisAlignedBB(
            super.posX - 0.1875, super.posY - 0.25 + 0.125, super.posZ - 0.1875, super.posX + 0.1875, super.posY + 0.25 + 0.125, super.posZ + 0.1875
         )
      );
   }

   @Override
   public void entityInit() {
      super.entityInit();
   }

   @Override
   public void updateFacingWithBoundingBox(EnumFacing facingDirectionIn) {
   }

   @Override
   public int getWidthPixels() {
      return 9;
   }

   @Override
   public int getHeightPixels() {
      return 9;
   }

   @Override
   public float getEyeHeight() {
      return -0.0625F;
   }

   @Override
   public boolean isInRangeToRenderDist(double distance) {
      return distance < 1024.0;
   }

   @Override
   public void onBroken(Entity brokenEntity) {
   }

   @Override
   public boolean writeToNBTOptional(NBTTagCompound tagCompund) {
      return false;
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound tagCompound) {
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound tagCompund) {
   }

   @Override
   public boolean interactFirst(EntityPlayer playerIn) {
      ItemStack itemstack = playerIn.getHeldItem();
      boolean flag = false;
      if (itemstack != null && itemstack.getItem() == Items.lead) {
         boolean flag1 = super.worldObj.isRemote;
         if (!flag1) {
            double d0 = 7.0;

            for (EntityLiving entityliving : super.worldObj
               .getEntitiesWithinAABB(
                  EntityLiving.class, new AxisAlignedBB(super.posX - d0, super.posY - d0, super.posZ - d0, super.posX + d0, super.posY + d0, super.posZ + d0)
               )) {
               if (entityliving.getLeashed() && entityliving.getLeashedToEntity() == playerIn) {
                  entityliving.setLeashedToEntity(this, true);
                  flag = true;
               }
            }
         }
      }

      boolean flag2 = super.worldObj.isRemote;
      if (!flag2 && !flag) {
         this.setDead();
         if (playerIn.capabilities.isCreativeMode) {
            double d1 = 7.0;

            for (EntityLiving entityliving1 : super.worldObj
               .getEntitiesWithinAABB(
                  EntityLiving.class, new AxisAlignedBB(super.posX - d1, super.posY - d1, super.posZ - d1, super.posX + d1, super.posY + d1, super.posZ + d1)
               )) {
               if (entityliving1.getLeashed() && entityliving1.getLeashedToEntity() == this) {
                  entityliving1.clearLeashed(true, false);
               }
            }
         }
      }

      return true;
   }

   @Override
   public boolean onValidSurface() {
      return super.worldObj.getBlockState(super.hangingPosition).getBlock() instanceof BlockFence;
   }

   public static EntityLeashKnot createKnot(World worldIn, BlockPos fence) {
      EntityLeashKnot entityleashknot = new EntityLeashKnot(worldIn, fence);
      entityleashknot.forceSpawn = true;
      worldIn.spawnEntityInWorld(entityleashknot);
      return entityleashknot;
   }

   public static EntityLeashKnot getKnotForPosition(World worldIn, BlockPos pos) {
      int i = pos.getX();
      int j = pos.getY();
      int k = pos.getZ();

      for (EntityLeashKnot entityleashknot : worldIn.getEntitiesWithinAABB(
         EntityLeashKnot.class, new AxisAlignedBB((double)i - 1.0, (double)j - 1.0, (double)k - 1.0, (double)i + 1.0, (double)j + 1.0, (double)k + 1.0)
      )) {
         if (entityleashknot.getHangingPosition().equals(pos)) {
            return entityleashknot;
         }
      }

      return null;
   }
}
