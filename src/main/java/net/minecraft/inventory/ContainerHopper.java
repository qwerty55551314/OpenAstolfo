package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class ContainerHopper extends Container {
   private final IInventory hopperInventory;

   public ContainerHopper(InventoryPlayer playerInventory, IInventory hopperInventoryIn, EntityPlayer player) {
      this.hopperInventory = hopperInventoryIn;
      hopperInventoryIn.openInventory(player);

      for (int i = 0; i < hopperInventoryIn.getSizeInventory(); i++) {
         this.addSlotToContainer(new Slot(hopperInventoryIn, i, 44 + i * 18, 20));
      }

      for (int k = 0; k < 3; k++) {
         for (int j = 0; j < 9; j++) {
            this.addSlotToContainer(new Slot(playerInventory, j + k * 9 + 9, 8 + j * 18, k * 18 + 51));
         }
      }

      for (int l = 0; l < 9; l++) {
         this.addSlotToContainer(new Slot(playerInventory, l, 8 + l * 18, 58 + 51));
      }
   }

   @Override
   public boolean canInteractWith(EntityPlayer playerIn) {
      return this.hopperInventory.isUseableByPlayer(playerIn);
   }

   @Override
   public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
      ItemStack itemstack = null;
      Slot slot = super.inventorySlots.get(index);
      if (slot != null && slot.getHasStack()) {
         ItemStack itemstack1 = slot.getStack();
         itemstack = itemstack1.copy();
         if (index < this.hopperInventory.getSizeInventory()) {
            if (!this.mergeItemStack(itemstack1, this.hopperInventory.getSizeInventory(), super.inventorySlots.size(), true)) {
               return null;
            }
         } else {
            boolean flag = this.mergeItemStack(itemstack1, 0, this.hopperInventory.getSizeInventory(), false);
            if (!flag) {
               return null;
            }
         }

         if (itemstack1.stackSize == 0) {
            slot.putStack((ItemStack)null);
         } else {
            slot.onSlotChanged();
         }
      }

      return itemstack;
   }

   @Override
   public void onContainerClosed(EntityPlayer playerIn) {
      super.onContainerClosed(playerIn);
      this.hopperInventory.closeInventory(playerIn);
   }
}
