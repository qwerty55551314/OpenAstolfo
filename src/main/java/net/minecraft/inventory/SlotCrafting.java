package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.stats.AchievementList;

public class SlotCrafting extends Slot {
   private final InventoryCrafting craftMatrix;
   private final EntityPlayer thePlayer;
   private int amountCrafted;

   public SlotCrafting(EntityPlayer player, InventoryCrafting craftingInventory, IInventory iInventory, int slotIndex, int xPosition, int yPosition) {
      super(iInventory, slotIndex, xPosition, yPosition);
      this.thePlayer = player;
      this.craftMatrix = craftingInventory;
   }

   @Override
   public boolean isItemValid(ItemStack stack) {
      return false;
   }

   @Override
   public ItemStack decrStackSize(int amount) {
      if (this.getHasStack()) {
         this.amountCrafted = this.amountCrafted + Math.min(amount, this.getStack().stackSize);
      }

      return super.decrStackSize(amount);
   }

   @Override
   public void onCrafting(ItemStack stack, int amount) {
      this.amountCrafted += amount;
      this.onCrafting(stack);
   }

   @Override
   public void onCrafting(ItemStack stack) {
      if (this.amountCrafted > 0) {
         stack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.amountCrafted);
      }

      this.amountCrafted = 0;
      Item item = stack.getItem();
      if (item == Item.getItemFromBlock(Blocks.crafting_table)) {
         this.thePlayer.triggerAchievement(AchievementList.buildWorkBench);
      }

      Item item1 = stack.getItem();
      if (item1 instanceof ItemPickaxe) {
         this.thePlayer.triggerAchievement(AchievementList.buildPickaxe);
      }

      Item item2 = stack.getItem();
      if (item2 == Item.getItemFromBlock(Blocks.furnace)) {
         this.thePlayer.triggerAchievement(AchievementList.buildFurnace);
      }

      Item item3 = stack.getItem();
      if (item3 instanceof ItemHoe) {
         this.thePlayer.triggerAchievement(AchievementList.buildHoe);
      }

      Item item10 = stack.getItem();
      Item item5 = Items.bread;
      Item item4 = item10;
      if (item4 == item5) {
         this.thePlayer.triggerAchievement(AchievementList.makeBread);
      }

      if (stack.getItem() == Items.cake) {
         this.thePlayer.triggerAchievement(AchievementList.bakeCake);
      }

      boolean flag = stack.getItem() instanceof ItemPickaxe;
      if (flag && ((ItemPickaxe)stack.getItem()).getToolMaterial() != Item.ToolMaterial.WOOD) {
         this.thePlayer.triggerAchievement(AchievementList.buildBetterPickaxe);
      }

      if (stack.getItem() instanceof ItemSword) {
         this.thePlayer.triggerAchievement(AchievementList.buildSword);
      }

      item10 = stack.getItem();
      Item item7 = Item.getItemFromBlock(Blocks.enchanting_table);
      Item item6 = item10;
      if (item6 == item7) {
         this.thePlayer.triggerAchievement(AchievementList.enchantments);
      }

      item10 = stack.getItem();
      Item item9 = Item.getItemFromBlock(Blocks.bookshelf);
      Item item8 = item10;
      if (item8 == item9) {
         this.thePlayer.triggerAchievement(AchievementList.bookcase);
      }

      if (stack.getItem() == Items.golden_apple && stack.getMetadata() == 1) {
         this.thePlayer.triggerAchievement(AchievementList.overpowered);
      }
   }

   @Override
   public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack) {
      this.onCrafting(stack);
      ItemStack[] aitemstack = CraftingManager.getInstance().func_180303_b(this.craftMatrix, playerIn.worldObj);

      for (int i = 0; i < aitemstack.length; i++) {
         ItemStack itemstack = this.craftMatrix.getStackInSlot(i);
         ItemStack itemstack1 = aitemstack[i];
         if (itemstack != null) {
            this.craftMatrix.decrStackSize(i, 1);
         }

         if (itemstack1 != null) {
            if (this.craftMatrix.getStackInSlot(i) == null) {
               this.craftMatrix.setInventorySlotContents(i, itemstack1);
            } else {
               EntityPlayer entityplayer = this.thePlayer;
               if (!entityplayer.inventory.addItemStackToInventory(itemstack1)) {
                  this.thePlayer.dropPlayerItemWithRandomChoice(itemstack1, false);
               }
            }
         }
      }
   }
}
