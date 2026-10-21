package net.minecraft.entity.item;

import net.minecraft.block.BlockFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityMinecartFurnace extends EntityMinecart {
   private int fuel;
   public double pushX;
   public double pushZ;

   public EntityMinecartFurnace(World worldIn) {
      super(worldIn);
   }

   public EntityMinecartFurnace(World worldIn, double x, double y, double z) {
      super(worldIn, x, y, z);
   }

   @Override
   public EntityMinecart.EnumMinecartType getMinecartType() {
      return EntityMinecart.EnumMinecartType.FURNACE;
   }

   @Override
   public void entityInit() {
      super.entityInit();
      super.dataWatcher.addObject(16, new Byte((byte)0));
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      int i = this.fuel;
      if (i > 0) {
         this.fuel--;
      }

      if (this.fuel <= 0) {
         this.pushX = this.pushZ = 0.0;
      }

      int j = this.fuel;
      this.setMinecartPowered(j > 0);
      boolean flag = this.isMinecartPowered();
      if (flag && super.rand.nextInt(4) == 0) {
         super.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, super.posX, super.posY + 0.8, super.posZ, 0.0, 0.0, 0.0);
      }
   }

   @Override
   public double getMaximumSpeed() {
      return 0.2;
   }

   @Override
   public void killMinecart(DamageSource source) {
      super.killMinecart(source);
      if (!source.isExplosion() && super.worldObj.getGameRules().getBoolean("doEntityDrops")) {
         this.entityDropItem(new ItemStack(Blocks.furnace, 1), 0.0F);
      }
   }

   @Override
   public void func_180460_a(BlockPos blockPos, IBlockState iBlockState) {
      super.func_180460_a(blockPos, iBlockState);
      double d0 = this.pushX * this.pushX + this.pushZ * this.pushZ;
      double d2;
      int i = (d2 = d0 - 1.0E-4) == 0.0 ? 0 : (d2 < 0.0 ? -1 : 1);
      if (i > 0) {
         double d3;
         int j = (d3 = super.motionX * super.motionX + super.motionZ * super.motionZ - 0.001) == 0.0 ? 0 : (d3 < 0.0 ? -1 : 1);
         if (j > 0) {
            d0 = (double)MathHelper.sqrt_double(d0);
            this.pushX /= d0;
            this.pushZ /= d0;
            if (this.pushX * super.motionX + this.pushZ * super.motionZ < 0.0) {
               this.pushX = 0.0;
               this.pushZ = 0.0;
            } else {
               double d1 = d0 / this.getMaximumSpeed();
               this.pushX *= d1;
               this.pushZ *= d1;
            }
         }
      }
   }

   @Override
   public void applyDrag() {
      double d0 = this.pushX * this.pushX + this.pushZ * this.pushZ;
      if (d0 > 1.0E-4) {
         d0 = (double)MathHelper.sqrt_double(d0);
         this.pushX /= d0;
         this.pushZ /= d0;
         double d1 = 1.0;
         super.motionX *= 0.8F;
         super.motionY *= 0.0;
         super.motionZ *= 0.8F;
         super.motionX = super.motionX + this.pushX * d1;
         super.motionZ = super.motionZ + this.pushZ * d1;
      } else {
         super.motionX *= 0.98F;
         super.motionY *= 0.0;
         super.motionZ *= 0.98F;
      }

      super.applyDrag();
   }

   @Override
   public boolean interactFirst(EntityPlayer playerIn) {
      ItemStack itemstack = playerIn.inventory.getCurrentItem();
      if (itemstack != null && itemstack.getItem() == Items.coal) {
         if (!playerIn.capabilities.isCreativeMode && --itemstack.stackSize == 0) {
            playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, (ItemStack)null);
         }

         this.fuel += 3600;
      }

      this.pushX = super.posX - playerIn.posX;
      this.pushZ = super.posZ - playerIn.posZ;
      return true;
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound tagCompound) {
      super.writeEntityToNBT(tagCompound);
      tagCompound.setDouble("PushX", this.pushX);
      tagCompound.setDouble("PushZ", this.pushZ);
      tagCompound.setShort("Fuel", (short)this.fuel);
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound tagCompund) {
      super.readEntityFromNBT(tagCompund);
      this.pushX = tagCompund.getDouble("PushX");
      this.pushZ = tagCompund.getDouble("PushZ");
      this.fuel = tagCompund.getShort("Fuel");
   }

   public boolean isMinecartPowered() {
      return (super.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
   }

   public void setMinecartPowered(boolean bl) {
      if (bl) {
         super.dataWatcher.updateObject(16, (byte)(super.dataWatcher.getWatchableObjectByte(16) | 1));
      } else {
         super.dataWatcher.updateObject(16, (byte)(super.dataWatcher.getWatchableObjectByte(16) & -2));
      }
   }

   @Override
   public IBlockState getDefaultDisplayTile() {
      return (this.isMinecartPowered() ? Blocks.lit_furnace : Blocks.furnace).getDefaultState().withProperty(BlockFurnace.FACING, EnumFacing.NORTH);
   }
}
