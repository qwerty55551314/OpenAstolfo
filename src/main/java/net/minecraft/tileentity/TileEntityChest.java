package net.minecraft.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

public class TileEntityChest extends TileEntityLockable implements ITickable, IInventory {
   private ItemStack[] chestContents = new ItemStack[27];
   public boolean adjacentChestChecked;
   public TileEntityChest adjacentChestZNeg;
   public TileEntityChest adjacentChestXPos;
   public TileEntityChest adjacentChestXNeg;
   public TileEntityChest adjacentChestZPos;
   public float lidAngle;
   public float prevLidAngle;
   public int numPlayersUsing;
   private int ticksSinceSync;
   private int cachedChestType;
   private String customName;

   public TileEntityChest() {
      this.cachedChestType = -1;
   }

   public TileEntityChest(int chestType) {
      this.cachedChestType = chestType;
   }

   @Override
   public int getSizeInventory() {
      return 27;
   }

   @Override
   public ItemStack getStackInSlot(int index) {
      return this.chestContents[index];
   }

   @Override
   public ItemStack decrStackSize(int index, int count) {
      ItemStack itemstack1 = this.chestContents[index];
      if (itemstack1 != null) {
         ItemStack itemstack2 = this.chestContents[index];
         if (itemstack2.stackSize <= count) {
            ItemStack itemstack4 = this.chestContents[index];
            this.chestContents[index] = null;
            this.markDirty();
            return itemstack4;
         } else {
            ItemStack itemstack = this.chestContents[index].splitStack(count);
            ItemStack itemstack3 = this.chestContents[index];
            if (itemstack3.stackSize == 0) {
               this.chestContents[index] = null;
            }

            this.markDirty();
            return itemstack;
         }
      } else {
         return null;
      }
   }

   @Override
   public ItemStack removeStackFromSlot(int index) {
      if (this.chestContents[index] != null) {
         ItemStack itemstack = this.chestContents[index];
         this.chestContents[index] = null;
         return itemstack;
      } else {
         return null;
      }
   }

   @Override
   public void setInventorySlotContents(int index, ItemStack stack) {
      this.chestContents[index] = stack;
      if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
         stack.stackSize = this.getInventoryStackLimit();
      }

      this.markDirty();
   }

   @Override
   public String getName() {
      return this.hasCustomName() ? this.customName : "container.chest";
   }

   @Override
   public boolean hasCustomName() {
      String s = this.customName;
      if (s != null) {
         int i = this.customName.length();
         if (i > 0) {
            return true;
         }
      }

      return false;
   }

   public void setCustomName(String name) {
      this.customName = name;
   }

   @Override
   public void readFromNBT(NBTTagCompound compound) {
      super.readFromNBT(compound);
      NBTTagList nbttaglist = compound.getTagList("Items", 10);
      this.chestContents = new ItemStack[this.getSizeInventory()];
      if (compound.hasKey("CustomName", 8)) {
         this.customName = compound.getString("CustomName");
      }

      for (int i = 0; i < nbttaglist.tagCount(); i++) {
         NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
         int j = nbttagcompound.getByte("Slot") & 255;
         if (j >= 0 && j < this.chestContents.length) {
            this.chestContents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
         }
      }
   }

   @Override
   public void writeToNBT(NBTTagCompound compound) {
      super.writeToNBT(compound);
      NBTTagList nbttaglist = new NBTTagList();

      for (int i = 0; i < this.chestContents.length; i++) {
         if (this.chestContents[i] != null) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setByte("Slot", (byte)i);
            this.chestContents[i].writeToNBT(nbttagcompound);
            nbttaglist.appendTag(nbttagcompound);
         }
      }

      compound.setTag("Items", nbttaglist);
      if (this.hasCustomName()) {
         compound.setString("CustomName", this.customName);
      }
   }

   @Override
   public int getInventoryStackLimit() {
      return 64;
   }

   @Override
   public boolean isUseableByPlayer(EntityPlayer player) {
      return super.worldObj.getTileEntity(super.pos) != this
         ? false
         : player.getDistanceSq((double)super.pos.getX() + 0.5, (double)super.pos.getY() + 0.5, (double)super.pos.getZ() + 0.5) <= 64.0;
   }

   @Override
   public void updateContainingBlockInfo() {
      super.updateContainingBlockInfo();
      this.adjacentChestChecked = false;
   }

   private void func_174910_a(TileEntityChest chestTe, EnumFacing side) {
      boolean flag = chestTe.isInvalid();
      if (flag) {
         this.adjacentChestChecked = false;
      } else {
         boolean flag1 = this.adjacentChestChecked;
         if (flag1) {
            switch (side) {
               case NORTH:
                  TileEntityChest tileentitychest = this.adjacentChestZNeg;
                  if (tileentitychest != chestTe) {
                     this.adjacentChestChecked = false;
                  }
                  break;
               case SOUTH:
                  TileEntityChest tileentitychest1 = this.adjacentChestZPos;
                  if (tileentitychest1 != chestTe) {
                     this.adjacentChestChecked = false;
                  }
                  break;
               case EAST:
                  TileEntityChest tileentitychest2 = this.adjacentChestXPos;
                  if (tileentitychest2 != chestTe) {
                     this.adjacentChestChecked = false;
                  }
                  break;
               case WEST:
                  TileEntityChest tileentitychest3 = this.adjacentChestXNeg;
                  if (tileentitychest3 != chestTe) {
                     this.adjacentChestChecked = false;
                  }
            }
         }
      }
   }

   public void checkForAdjacentChests() {
      if (!this.adjacentChestChecked) {
         this.adjacentChestChecked = true;
         this.adjacentChestXNeg = this.getAdjacentChest(EnumFacing.WEST);
         this.adjacentChestXPos = this.getAdjacentChest(EnumFacing.EAST);
         this.adjacentChestZNeg = this.getAdjacentChest(EnumFacing.NORTH);
         this.adjacentChestZPos = this.getAdjacentChest(EnumFacing.SOUTH);
      }
   }

   public TileEntityChest getAdjacentChest(EnumFacing side) {
      BlockPos blockpos = super.pos.offset(side);
      if (this.isChestAt(blockpos)) {
         TileEntity tileentity = super.worldObj.getTileEntity(blockpos);
         if (tileentity instanceof TileEntityChest) {
            TileEntityChest tileentitychest = (TileEntityChest)tileentity;
            tileentitychest.func_174910_a(this, side.getOpposite());
            return tileentitychest;
         }
      }

      return null;
   }

   private boolean isChestAt(BlockPos posIn) {
      World world = super.worldObj;
      if (world == null) {
         return false;
      } else {
         Block block = super.worldObj.getBlockState(posIn).getBlock();
         boolean flag = block instanceof BlockChest;
         if (flag) {
            int i = ((BlockChest)block).chestType;
            if (i == this.getChestType()) {
               return true;
            }
         }

         return false;
      }
   }

   @Override
   public void update() {
      this.checkForAdjacentChests();
      Object object = null;
      int i = super.pos.getX();
      int j = super.pos.getY();
      int k = super.pos.getZ();
      this.ticksSinceSync++;
      if (!super.worldObj.isRemote && this.numPlayersUsing != 0 && (this.ticksSinceSync + i + j + k) % 200 == 0) {
         this.numPlayersUsing = 0;
         float f = 5.0F;

         for (EntityPlayer entityplayer : super.worldObj
            .getEntitiesWithinAABB(
               EntityPlayer.class,
               new AxisAlignedBB(
                  (double)((float)i - f),
                  (double)((float)j - f),
                  (double)((float)k - f),
                  (double)((float)(i + 1) + f),
                  (double)((float)(j + 1) + f),
                  (double)((float)(k + 1) + f)
               )
            )) {
            if (entityplayer.openContainer instanceof ContainerChest) {
               IInventory iinventory = ((ContainerChest)entityplayer.openContainer).getLowerChestInventory();
               if (iinventory == this || iinventory instanceof InventoryLargeChest && ((InventoryLargeChest)iinventory).isPartOfLargeChest(this)) {
                  this.numPlayersUsing++;
               }
            }
         }
      }

      this.prevLidAngle = this.lidAngle;
      float f2 = 0.1F;
      int l = this.numPlayersUsing;
      if (l > 0 && this.lidAngle == 0.0F && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null) {
         double d1 = (double)i + 0.5;
         double d2 = (double)k + 0.5;
         if (this.adjacentChestZPos != null) {
            d2 += 0.5;
         }

         if (this.adjacentChestXPos != null) {
            d1 += 0.5;
         }

         super.worldObj.playSoundEffect(d1, (double)j + 0.5, d2, "random.chestopen", 0.5F, super.worldObj.rand.nextFloat() * 0.1F + 0.9F);
      }

      int i1 = this.numPlayersUsing;
      if (i1 != 0 || !(this.lidAngle > 0.0F)) {
         int j1 = this.numPlayersUsing;
         if (j1 <= 0 || !(this.lidAngle < 1.0F)) {
            return;
         }
      }

      float f3 = this.lidAngle;
      if (this.numPlayersUsing > 0) {
         this.lidAngle += f2;
      } else {
         this.lidAngle -= f2;
      }

      float f1 = this.lidAngle;
      if (f1 > 1.0F) {
         this.lidAngle = 1.0F;
      }

      float f4 = 0.5F;
      float f5;
      int k1 = (f5 = this.lidAngle - f4) == 0.0F ? 0 : (f5 < 0.0F ? -1 : 1);
      if (k1 < 0 && f3 >= f4 && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null) {
         double d3 = (double)i + 0.5;
         double d0 = (double)k + 0.5;
         if (this.adjacentChestZPos != null) {
            d0 += 0.5;
         }

         if (this.adjacentChestXPos != null) {
            d3 += 0.5;
         }

         super.worldObj.playSoundEffect(d3, (double)j + 0.5, d0, "random.chestclosed", 0.5F, super.worldObj.rand.nextFloat() * 0.1F + 0.9F);
      }

      if (this.lidAngle < 0.0F) {
         this.lidAngle = 0.0F;
      }
   }

   @Override
   public boolean receiveClientEvent(int id, int typeIn) {
      if (id == 1) {
         this.numPlayersUsing = typeIn;
         return true;
      } else {
         return super.receiveClientEvent(id, typeIn);
      }
   }

   @Override
   public void openInventory(EntityPlayer player) {
      if (!player.isSpectator()) {
         if (this.numPlayersUsing < 0) {
            this.numPlayersUsing = 0;
         }

         this.numPlayersUsing++;
         super.worldObj.addBlockEvent(super.pos, this.getBlockType(), 1, this.numPlayersUsing);
         super.worldObj.notifyNeighborsOfStateChange(super.pos, this.getBlockType());
         super.worldObj.notifyNeighborsOfStateChange(super.pos.down(), this.getBlockType());
      }
   }

   @Override
   public void closeInventory(EntityPlayer player) {
      if (!player.isSpectator() && this.getBlockType() instanceof BlockChest) {
         this.numPlayersUsing--;
         super.worldObj.addBlockEvent(super.pos, this.getBlockType(), 1, this.numPlayersUsing);
         super.worldObj.notifyNeighborsOfStateChange(super.pos, this.getBlockType());
         super.worldObj.notifyNeighborsOfStateChange(super.pos.down(), this.getBlockType());
      }
   }

   @Override
   public boolean isItemValidForSlot(int index, ItemStack stack) {
      return true;
   }

   @Override
   public void invalidate() {
      super.invalidate();
      this.updateContainingBlockInfo();
      this.checkForAdjacentChests();
   }

   public int getChestType() {
      if (this.cachedChestType == -1) {
         if (super.worldObj == null || !(this.getBlockType() instanceof BlockChest)) {
            return 0;
         }

         this.cachedChestType = ((BlockChest)this.getBlockType()).chestType;
      }

      return this.cachedChestType;
   }

   @Override
   public String getGuiID() {
      return "minecraft:chest";
   }

   @Override
   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
      return new ContainerChest(playerInventory, this, playerIn);
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
   public void clear() {
      for (int i = 0; i < this.chestContents.length; i++) {
         this.chestContents[i] = null;
      }
   }
}
