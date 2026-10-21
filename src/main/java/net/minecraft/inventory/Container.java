package net.minecraft.inventory;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

public abstract class Container {
   public List<ItemStack> inventoryItemStacks = Lists.newArrayList();
   public List<Slot> inventorySlots = Lists.newArrayList();
   public int windowId;
   private short transactionID;
   private int dragMode = -1;
   private int dragEvent;
   private final Set<Slot> dragSlots = Sets.newHashSet();
   public List<ICrafting> crafters = Lists.newArrayList();
   private Set<EntityPlayer> playerList = Sets.newHashSet();
   private static String S;
   private static final String h = "Listener already listening";

   public Slot addSlotToContainer(Slot slotIn) {
      slotIn.slotNumber = this.inventorySlots.size();
      this.inventorySlots.add(slotIn);
      this.inventoryItemStacks.add((ItemStack)null);
      return slotIn;
   }

   public void onCraftGuiOpened(ICrafting listener) {
      if (this.crafters.contains(listener)) {
         throw new IllegalArgumentException(h);
      } else {
         this.crafters.add(listener);
         listener.updateCraftingInventory(this, this.getInventory());
         this.detectAndSendChanges();
      }
   }

   public void removeCraftingFromCrafters(ICrafting listeners) {
      this.crafters.remove(listeners);
   }

   public List<ItemStack> getInventory() {
      ArrayList arraylist = Lists.newArrayList();

      for (int i = 0; i < this.inventorySlots.size(); i++) {
         arraylist.add(this.inventorySlots.get(i).getStack());
      }

      return arraylist;
   }

   public void detectAndSendChanges() {
      for (int i = 0; i < this.inventorySlots.size(); i++) {
         ItemStack itemstack = this.inventorySlots.get(i).getStack();
         ItemStack itemstack1 = this.inventoryItemStacks.get(i);
         if (!ItemStack.areItemStacksEqual(itemstack1, itemstack)) {
            itemstack1 = itemstack == null ? null : itemstack.copy();
            this.inventoryItemStacks.set(i, itemstack1);

            for (int j = 0; j < this.crafters.size(); j++) {
               this.crafters.get(j).sendSlotContents(this, i, itemstack1);
            }
         }
      }
   }

   public boolean enchantItem(EntityPlayer playerIn, int id) {
      return false;
   }

   public Slot getSlotFromInventory(IInventory inv, int slotIn) {
      for (int i = 0; i < this.inventorySlots.size(); i++) {
         Slot slot = this.inventorySlots.get(i);
         if (slot.isHere(inv, slotIn)) {
            return slot;
         }
      }

      return null;
   }

   public Slot getSlot(int slotId) {
      return this.inventorySlots.get(slotId);
   }

   public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
      Slot slot = this.inventorySlots.get(index);
      return slot != null ? slot.getStack() : null;
   }

   public ItemStack slotClick(int slotId, int clickedButton, int mode, EntityPlayer playerIn) {
      ItemStack itemstack = null;
      InventoryPlayer inventoryplayer = playerIn.inventory;
      if (mode == 5) {
         int i = this.dragEvent;
         this.dragEvent = getDragEvent(clickedButton);
         if ((i != 1 || this.dragEvent != 2) && i != this.dragEvent) {
            this.resetDrag();
         } else if (inventoryplayer.getItemStack() == null) {
            this.resetDrag();
         } else if (this.dragEvent == 0) {
            this.dragMode = extractDragMode(clickedButton);
            if (isValidDragMode(this.dragMode, playerIn)) {
               this.dragEvent = 1;
               this.dragSlots.clear();
            } else {
               this.resetDrag();
            }
         } else if (this.dragEvent == 1) {
            Slot slot = this.inventorySlots.get(slotId);
            if (slot != null
               && canAddItemToSlot(slot, inventoryplayer.getItemStack(), true)
               && slot.isItemValid(inventoryplayer.getItemStack())
               && inventoryplayer.getItemStack().stackSize > this.dragSlots.size()
               && this.canDragIntoSlot(slot)) {
               this.dragSlots.add(slot);
            }
         } else if (this.dragEvent == 2) {
            if (!this.dragSlots.isEmpty()) {
               ItemStack itemstack4 = inventoryplayer.getItemStack().copy();
               int j = inventoryplayer.getItemStack().stackSize;

               for (Slot slot1 : this.dragSlots) {
                  if (slot1 != null
                     && canAddItemToSlot(slot1, inventoryplayer.getItemStack(), true)
                     && slot1.isItemValid(inventoryplayer.getItemStack())
                     && inventoryplayer.getItemStack().stackSize >= this.dragSlots.size()
                     && this.canDragIntoSlot(slot1)) {
                     ItemStack itemstack1 = itemstack4.copy();
                     int k = slot1.getHasStack() ? slot1.getStack().stackSize : 0;
                     computeStackSize(this.dragSlots, this.dragMode, itemstack1, k);
                     if (itemstack1.stackSize > itemstack1.getMaxStackSize()) {
                        itemstack1.stackSize = itemstack1.getMaxStackSize();
                     }

                     if (itemstack1.stackSize > slot1.getItemStackLimit(itemstack1)) {
                        itemstack1.stackSize = slot1.getItemStackLimit(itemstack1);
                     }

                     j -= itemstack1.stackSize - k;
                     slot1.putStack(itemstack1);
                  }
               }

               itemstack4.stackSize = j;
               if (itemstack4.stackSize <= 0) {
                  itemstack4 = null;
               }

               inventoryplayer.setItemStack(itemstack4);
            }

            this.resetDrag();
         } else {
            this.resetDrag();
         }
      } else {
         int i1 = this.dragEvent;
         if (i1 != 0) {
            this.resetDrag();
         } else if ((mode == 0 || mode == 1) && (clickedButton == 0 || clickedButton == 1)) {
            if (slotId == -999) {
               if (inventoryplayer.getItemStack() != null) {
                  if (clickedButton == 0) {
                     playerIn.dropPlayerItemWithRandomChoice(inventoryplayer.getItemStack(), true);
                     inventoryplayer.setItemStack((ItemStack)null);
                  }

                  if (clickedButton == 1) {
                     playerIn.dropPlayerItemWithRandomChoice(inventoryplayer.getItemStack().splitStack(1), true);
                     if (inventoryplayer.getItemStack().stackSize == 0) {
                        inventoryplayer.setItemStack((ItemStack)null);
                     }
                  }
               }
            } else if (mode == 1) {
               if (slotId < 0) {
                  return null;
               }

               Slot slot6 = this.inventorySlots.get(slotId);
               if (slot6 != null && slot6.canTakeStack(playerIn)) {
                  ItemStack itemstack9 = this.transferStackInSlot(playerIn, slotId);
                  if (itemstack9 != null) {
                     Item item = itemstack9.getItem();
                     itemstack = itemstack9.copy();
                     ItemStack itemstack3 = slot6.getStack();
                     if (itemstack3 != null && slot6.getStack().getItem() == item) {
                        this.retrySlotClick(slotId, clickedButton, true, playerIn);
                     }
                  }
               }
            } else {
               if (slotId < 0) {
                  return null;
               }

               Slot slot7 = this.inventorySlots.get(slotId);
               if (slot7 != null) {
                  ItemStack itemstack10 = slot7.getStack();
                  ItemStack itemstack11 = inventoryplayer.getItemStack();
                  if (itemstack10 != null) {
                     itemstack = itemstack10.copy();
                  }

                  if (itemstack10 == null) {
                     if (itemstack11 != null && slot7.isItemValid(itemstack11)) {
                        int l2 = clickedButton == 0 ? itemstack11.stackSize : 1;
                        if (l2 > slot7.getItemStackLimit(itemstack11)) {
                           l2 = slot7.getItemStackLimit(itemstack11);
                        }

                        if (itemstack11.stackSize >= l2) {
                           slot7.putStack(itemstack11.splitStack(l2));
                        }

                        if (itemstack11.stackSize == 0) {
                           inventoryplayer.setItemStack((ItemStack)null);
                        }
                     }
                  } else if (slot7.canTakeStack(playerIn)) {
                     if (itemstack11 == null) {
                        int k2 = clickedButton == 0 ? itemstack10.stackSize : (itemstack10.stackSize + 1) / 2;
                        ItemStack itemstack13 = slot7.decrStackSize(k2);
                        inventoryplayer.setItemStack(itemstack13);
                        if (itemstack10.stackSize == 0) {
                           slot7.putStack((ItemStack)null);
                        }

                        slot7.onPickupFromSlot(playerIn, inventoryplayer.getItemStack());
                     } else if (slot7.isItemValid(itemstack11)) {
                        if (itemstack10.getItem() == itemstack11.getItem()
                           && itemstack10.getMetadata() == itemstack11.getMetadata()
                           && ItemStack.areItemStackTagsEqual(itemstack10, itemstack11)) {
                           int j2 = clickedButton == 0 ? itemstack11.stackSize : 1;
                           if (j2 > slot7.getItemStackLimit(itemstack11) - itemstack10.stackSize) {
                              j2 = slot7.getItemStackLimit(itemstack11) - itemstack10.stackSize;
                           }

                           if (j2 > itemstack11.getMaxStackSize() - itemstack10.stackSize) {
                              j2 = itemstack11.getMaxStackSize() - itemstack10.stackSize;
                           }

                           itemstack11.splitStack(j2);
                           if (itemstack11.stackSize == 0) {
                              inventoryplayer.setItemStack((ItemStack)null);
                           }

                           itemstack10.stackSize += j2;
                        } else if (itemstack11.stackSize <= slot7.getItemStackLimit(itemstack11)) {
                           slot7.putStack(itemstack11);
                           inventoryplayer.setItemStack(itemstack10);
                        }
                     } else if (itemstack10.getItem() == itemstack11.getItem()
                        && itemstack11.getMaxStackSize() > 1
                        && (!itemstack10.getHasSubtypes() || itemstack10.getMetadata() == itemstack11.getMetadata())
                        && ItemStack.areItemStackTagsEqual(itemstack10, itemstack11)) {
                        int i2 = itemstack10.stackSize;
                        if (i2 > 0 && i2 + itemstack11.stackSize <= itemstack11.getMaxStackSize()) {
                           itemstack11.stackSize += i2;
                           itemstack10 = slot7.decrStackSize(i2);
                           if (itemstack10.stackSize == 0) {
                              slot7.putStack((ItemStack)null);
                           }

                           slot7.onPickupFromSlot(playerIn, inventoryplayer.getItemStack());
                        }
                     }
                  }

                  slot7.onSlotChanged();
               }
            }
         } else if (mode == 2 && clickedButton >= 0 && clickedButton < 9) {
            Slot slot5 = this.inventorySlots.get(slotId);
            if (slot5.canTakeStack(playerIn)) {
               ItemStack itemstack8 = inventoryplayer.getStackInSlot(clickedButton);
               boolean flag = itemstack8 == null || slot5.inventory == inventoryplayer && slot5.isItemValid(itemstack8);
               int l1 = -1;
               if (!flag) {
                  l1 = inventoryplayer.getFirstEmptyStack();
                  flag |= l1 > -1;
               }

               if (slot5.getHasStack() && flag) {
                  ItemStack itemstack12 = slot5.getStack();
                  inventoryplayer.setInventorySlotContents(clickedButton, itemstack12.copy());
                  if ((slot5.inventory != inventoryplayer || !slot5.isItemValid(itemstack8)) && itemstack8 != null) {
                     if (l1 > -1) {
                        inventoryplayer.addItemStackToInventory(itemstack8);
                        slot5.decrStackSize(itemstack12.stackSize);
                        slot5.putStack((ItemStack)null);
                        slot5.onPickupFromSlot(playerIn, itemstack12);
                     }
                  } else {
                     slot5.decrStackSize(itemstack12.stackSize);
                     slot5.putStack(itemstack8);
                     slot5.onPickupFromSlot(playerIn, itemstack12);
                  }
               } else if (!slot5.getHasStack() && itemstack8 != null && slot5.isItemValid(itemstack8)) {
                  inventoryplayer.setInventorySlotContents(clickedButton, (ItemStack)null);
                  slot5.putStack(itemstack8);
               }
            }
         } else if (mode == 3 && playerIn.capabilities.isCreativeMode && inventoryplayer.getItemStack() == null && slotId >= 0) {
            Slot slot4 = this.inventorySlots.get(slotId);
            if (slot4 != null && slot4.getHasStack()) {
               ItemStack itemstack7 = slot4.getStack().copy();
               itemstack7.stackSize = itemstack7.getMaxStackSize();
               inventoryplayer.setItemStack(itemstack7);
            }
         } else if (mode == 4 && inventoryplayer.getItemStack() == null && slotId >= 0) {
            if (slotId >= this.inventorySlots.size()) {
               return itemstack;
            }

            Slot slot3 = this.inventorySlots.get(slotId);
            if (slot3 != null && slot3.getHasStack() && slot3.canTakeStack(playerIn)) {
               ItemStack itemstack6 = slot3.decrStackSize(clickedButton == 0 ? 1 : slot3.getStack().stackSize);
               slot3.onPickupFromSlot(playerIn, itemstack6);
               playerIn.dropPlayerItemWithRandomChoice(itemstack6, true);
            }
         } else if (mode == 6 && slotId >= 0) {
            Slot slot2 = this.inventorySlots.get(slotId);
            ItemStack itemstack5 = inventoryplayer.getItemStack();
            if (itemstack5 != null && (slot2 == null || !slot2.getHasStack() || !slot2.canTakeStack(playerIn))) {
               int j1 = clickedButton == 0 ? 0 : this.inventorySlots.size() - 1;
               int k1 = clickedButton == 0 ? 1 : -1;

               for (int i3 = 0; i3 < 2; i3++) {
                  for (int j3 = j1; j3 >= 0 && j3 < this.inventorySlots.size() && itemstack5.stackSize < itemstack5.getMaxStackSize(); j3 += k1) {
                     Slot slot8 = this.inventorySlots.get(j3);
                     if (slot8.getHasStack()
                        && canAddItemToSlot(slot8, itemstack5, true)
                        && slot8.canTakeStack(playerIn)
                        && this.canMergeSlot(itemstack5, slot8)
                        && (i3 != 0 || slot8.getStack().stackSize != slot8.getStack().getMaxStackSize())) {
                        int l = Math.min(itemstack5.getMaxStackSize() - itemstack5.stackSize, slot8.getStack().stackSize);
                        ItemStack itemstack2 = slot8.decrStackSize(l);
                        itemstack5.stackSize += l;
                        if (itemstack2.stackSize <= 0) {
                           slot8.putStack((ItemStack)null);
                        }

                        slot8.onPickupFromSlot(playerIn, itemstack2);
                     }
                  }
               }
            }

            this.detectAndSendChanges();
         }
      }

      return itemstack;
   }

   public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
      return true;
   }

   public void retrySlotClick(int slotId, int clickedButton, boolean mode, EntityPlayer playerIn) {
      this.slotClick(slotId, clickedButton, 1, playerIn);
   }

   public void onContainerClosed(EntityPlayer playerIn) {
      InventoryPlayer inventoryplayer = playerIn.inventory;
      if (inventoryplayer.getItemStack() != null) {
         playerIn.dropPlayerItemWithRandomChoice(inventoryplayer.getItemStack(), false);
         inventoryplayer.setItemStack((ItemStack)null);
      }
   }

   public void onCraftMatrixChanged(IInventory inventoryIn) {
      this.detectAndSendChanges();
   }

   public void putStackInSlot(int slotID, ItemStack stack) {
      this.getSlot(slotID).putStack(stack);
   }

   public void putStacksInSlots(ItemStack[] itemStacks) {
      for (int i = 0; i < itemStacks.length; i++) {
         this.getSlot(i).putStack(itemStacks[i]);
      }
   }

   public void updateProgressBar(int id, int data) {
   }

   public short getNextTransactionID(InventoryPlayer inventoryPlayer) {
      this.transactionID++;
      return this.transactionID;
   }

   public boolean getCanCraft(EntityPlayer entityPlayer) {
      return !this.playerList.contains(entityPlayer);
   }

   public void setCanCraft(EntityPlayer entityPlayer, boolean bl) {
      if (bl) {
         this.playerList.remove(entityPlayer);
      } else {
         this.playerList.add(entityPlayer);
      }
   }

   public abstract boolean canInteractWith(EntityPlayer var1);

   public boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
      boolean flag = false;
      int i = startIndex;
      if (reverseDirection) {
         i = endIndex - 1;
      }

      boolean flag1 = stack.isStackable();
      if (flag1) {
         while (stack.stackSize > 0 && (!reverseDirection && i < endIndex || reverseDirection && i >= startIndex)) {
            Slot slot = this.inventorySlots.get(i);
            ItemStack itemstack = slot.getStack();
            if (itemstack != null
               && itemstack.getItem() == stack.getItem()
               && (!stack.getHasSubtypes() || stack.getMetadata() == itemstack.getMetadata())
               && ItemStack.areItemStackTagsEqual(stack, itemstack)) {
               int j = itemstack.stackSize + stack.stackSize;
               if (j <= stack.getMaxStackSize()) {
                  stack.stackSize = 0;
                  itemstack.stackSize = j;
                  slot.onSlotChanged();
                  flag = true;
               } else if (itemstack.stackSize < stack.getMaxStackSize()) {
                  stack.stackSize = stack.stackSize - (stack.getMaxStackSize() - itemstack.stackSize);
                  itemstack.stackSize = stack.getMaxStackSize();
                  slot.onSlotChanged();
                  flag = true;
               }
            }

            if (reverseDirection) {
               i--;
            } else {
               i++;
            }
         }
      }

      int k = stack.stackSize;
      if (k > 0) {
         if (reverseDirection) {
            i = endIndex - 1;
         } else {
            i = startIndex;
         }

         while (!reverseDirection && i < endIndex || reverseDirection && i >= startIndex) {
            Slot slot1 = this.inventorySlots.get(i);
            ItemStack itemstack1 = slot1.getStack();
            if (itemstack1 == null) {
               slot1.putStack(stack.copy());
               slot1.onSlotChanged();
               stack.stackSize = 0;
               flag = true;
               break;
            }

            if (reverseDirection) {
               i--;
            } else {
               i++;
            }
         }
      }

      return flag;
   }

   public static int extractDragMode(int i) {
      return i >> 2 & 3;
   }

   public static int getDragEvent(int i) {
      return i & 3;
   }

   public static int func_94534_d(int i, int j) {
      return i & 3 | (j & 3) << 2;
   }

   public static boolean isValidDragMode(int dragModeIn, EntityPlayer player) {
      boolean flag1;
      if (dragModeIn == 0) {
         flag1 = true;
      } else if (dragModeIn == 1) {
         flag1 = true;
      } else {
         if (dragModeIn == 2) {
            boolean flag = player.capabilities.isCreativeMode;
            if (flag) {
               return true;
            }
         }

         flag1 = false;
      }

      return flag1;
   }

   public void resetDrag() {
      this.dragEvent = 0;
      this.dragSlots.clear();
   }

   public static boolean canAddItemToSlot(Slot slotIn, ItemStack stack, boolean stackSizeMatters) {
      boolean flag3;
      label34: {
         if (slotIn != null) {
            boolean flag1 = slotIn.getHasStack();
            if (flag1) {
               flag3 = false;
               break label34;
            }
         }

         flag3 = true;
      }

      boolean flag = flag3;
      if (slotIn != null) {
         boolean flag2 = slotIn.getHasStack();
         if (flag2 && stack != null && stack.isItemEqual(slotIn.getStack()) && ItemStack.areItemStackTagsEqual(slotIn.getStack(), stack)) {
            int i = stackSizeMatters ? 0 : stack.stackSize;
            flag |= slotIn.getStack().stackSize + i <= stack.getMaxStackSize();
         }
      }

      return flag;
   }

   public static void computeStackSize(Set<Slot> set, int i, ItemStack itemStack, int j) {
      switch (i) {
         case 0:
            itemStack.stackSize = MathHelper.floor_float((float)itemStack.stackSize / (float)set.size());
            break;
         case 1:
            itemStack.stackSize = 1;
            break;
         case 2:
            itemStack.stackSize = itemStack.getItem().getItemStackLimit();
      }

      itemStack.stackSize += j;
   }

   public boolean canDragIntoSlot(Slot slot) {
      return true;
   }

   public static int calcRedstone(TileEntity te) {
      return te instanceof IInventory ? calcRedstoneFromInventory((IInventory)te) : 0;
   }

   public static int calcRedstoneFromInventory(IInventory inv) {
      if (inv == null) {
         return 0;
      } else {
         int i = 0;
         float f = 0.0F;

         for (int j = 0; j < inv.getSizeInventory(); j++) {
            ItemStack itemstack = inv.getStackInSlot(j);
            if (itemstack != null) {
               f += (float)itemstack.stackSize / (float)Math.min(inv.getInventoryStackLimit(), itemstack.getMaxStackSize());
               i++;
            }
         }

         f /= (float)inv.getSizeInventory();
         return MathHelper.floor_float(f * 14.0F) + (i > 0 ? 1 : 0);
      }
   }
}
