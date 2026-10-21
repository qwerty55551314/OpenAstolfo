package net.minecraft.entity.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.LockCode;
import net.minecraft.world.World;

public abstract class EntityMinecartContainer extends EntityMinecart implements ILockableContainer {
   private ItemStack[] minecartContainerItems = new ItemStack[36];
   private boolean dropContentsWhenDead = true;

   public EntityMinecartContainer(World worldIn) {
      super(worldIn);
   }

   public EntityMinecartContainer(World worldIn, double x, double y, double z) {
      super(worldIn, x, y, z);
   }

   @Override
   public void killMinecart(DamageSource source) {
      super.killMinecart(source);
      if (super.worldObj.getGameRules().getBoolean("doEntityDrops")) {
         InventoryHelper.dropInventoryItems(super.worldObj, this, this);
      }
   }

   @Override
   public ItemStack getStackInSlot(int index) {
      return this.minecartContainerItems[index];
   }

   @Override
   public ItemStack decrStackSize(int index, int count) {
      ItemStack itemstack1 = this.minecartContainerItems[index];
      if (itemstack1 != null) {
         ItemStack itemstack2 = this.minecartContainerItems[index];
         if (itemstack2.stackSize <= count) {
            ItemStack itemstack4 = this.minecartContainerItems[index];
            this.minecartContainerItems[index] = null;
            return itemstack4;
         } else {
            ItemStack itemstack = this.minecartContainerItems[index].splitStack(count);
            ItemStack itemstack3 = this.minecartContainerItems[index];
            if (itemstack3.stackSize == 0) {
               this.minecartContainerItems[index] = null;
            }

            return itemstack;
         }
      } else {
         return null;
      }
   }

   @Override
   public ItemStack removeStackFromSlot(int index) {
      if (this.minecartContainerItems[index] != null) {
         ItemStack itemstack = this.minecartContainerItems[index];
         this.minecartContainerItems[index] = null;
         return itemstack;
      } else {
         return null;
      }
   }

   @Override
   public void setInventorySlotContents(int index, ItemStack stack) {
      this.minecartContainerItems[index] = stack;
      if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
         stack.stackSize = this.getInventoryStackLimit();
      }
   }

   @Override
   public void markDirty() {
   }

   @Override
   public boolean isUseableByPlayer(EntityPlayer player) {
      boolean flag = super.isDead;
      boolean flag1;
      if (flag) {
         flag1 = false;
      } else {
         double d0;
         int i = (d0 = player.getDistanceSqToEntity(this) - 64.0) == 0.0 ? 0 : (d0 < 0.0 ? -1 : 1);
         flag1 = i <= 0;
      }

      return flag1;
   }

   @Override
   public void openInventory(EntityPlayer player) {
   }

   @Override
   public void closeInventory(EntityPlayer player) {
   }

   @Override
   public boolean isItemValidForSlot(int index, ItemStack stack) {
      return true;
   }

   @Override
   public String getName() {
      return this.hasCustomName() ? this.getCustomNameTag() : "container.minecart";
   }

   @Override
   public int getInventoryStackLimit() {
      return 64;
   }

   @Override
   public void travelToDimension(int dimensionId) {
      this.dropContentsWhenDead = false;
      super.travelToDimension(dimensionId);
   }

   @Override
   public void setDead() {
      if (this.dropContentsWhenDead) {
         InventoryHelper.dropInventoryItems(super.worldObj, this, this);
      }

      super.setDead();
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound tagCompound) {
      super.writeEntityToNBT(tagCompound);
      NBTTagList nbttaglist = new NBTTagList();

      for (int i = 0; i < this.minecartContainerItems.length; i++) {
         if (this.minecartContainerItems[i] != null) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setByte("Slot", (byte)i);
            this.minecartContainerItems[i].writeToNBT(nbttagcompound);
            nbttaglist.appendTag(nbttagcompound);
         }
      }

      tagCompound.setTag("Items", nbttaglist);
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound tagCompund) {
      super.readEntityFromNBT(tagCompund);
      NBTTagList nbttaglist = tagCompund.getTagList("Items", 10);
      this.minecartContainerItems = new ItemStack[this.getSizeInventory()];

      for (int i = 0; i < nbttaglist.tagCount(); i++) {
         NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
         int j = nbttagcompound.getByte("Slot") & 255;
         if (j >= 0 && j < this.minecartContainerItems.length) {
            this.minecartContainerItems[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
         }
      }
   }

   @Override
   public boolean interactFirst(EntityPlayer playerIn) {
      if (!super.worldObj.isRemote) {
         playerIn.displayGUIChest(this);
      }

      return true;
   }

   @Override
   public void applyDrag() {
      int i = 15 - Container.calcRedstoneFromInventory(this);
      float f = 0.98F + (float)i * 0.001F;
      super.motionX *= (double)f;
      super.motionY *= 0.0;
      super.motionZ *= (double)f;
   }

   @Override
   public int getField(int id) {
      return 0;
   }

   @Override
   public void setField(int id, int value) {
   }

   @Override
   public int getFieldCount() {
      return 0;
   }

   @Override
   public boolean isLocked() {
      return false;
   }

   @Override
   public void setLockCode(LockCode code) {
   }

   @Override
   public LockCode getLockCode() {
      return LockCode.EMPTY_CODE;
   }

   @Override
   public void clear() {
      for (int i = 0; i < this.minecartContainerItems.length; i++) {
         this.minecartContainerItems[i] = null;
      }
   }
}
