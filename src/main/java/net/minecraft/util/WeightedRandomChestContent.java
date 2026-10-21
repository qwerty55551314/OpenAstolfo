package net.minecraft.util;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityDispenser;

public class WeightedRandomChestContent extends WeightedRandom.Item {
   private ItemStack theItemId;
   private int minStackSize;
   private int maxStackSize;

   public WeightedRandomChestContent(Item item, int i, int minimumChance, int maximumChance, int itemWeightIn) {
      super(itemWeightIn);
      this.theItemId = new ItemStack(item, 1, i);
      this.minStackSize = minimumChance;
      this.maxStackSize = maximumChance;
   }

   public WeightedRandomChestContent(ItemStack stack, int minimumChance, int maximumChance, int itemWeightIn) {
      super(itemWeightIn);
      this.theItemId = stack;
      this.minStackSize = minimumChance;
      this.maxStackSize = maximumChance;
   }

   public static void generateChestContents(Random randomIn, List<WeightedRandomChestContent> listIn, IInventory inv, int maxIn) {
      for (int i = 0; i < maxIn; i++) {
         WeightedRandomChestContent weightedrandomchestcontent = WeightedRandom.getRandomItem(randomIn, listIn);
         int j = weightedrandomchestcontent.minStackSize
            + randomIn.nextInt(weightedrandomchestcontent.maxStackSize - weightedrandomchestcontent.minStackSize + 1);
         if (weightedrandomchestcontent.theItemId.getMaxStackSize() >= j) {
            ItemStack itemstack1 = weightedrandomchestcontent.theItemId.copy();
            itemstack1.stackSize = j;
            inv.setInventorySlotContents(randomIn.nextInt(inv.getSizeInventory()), itemstack1);
         } else {
            for (int k = 0; k < j; k++) {
               ItemStack itemstack = weightedrandomchestcontent.theItemId.copy();
               itemstack.stackSize = 1;
               inv.setInventorySlotContents(randomIn.nextInt(inv.getSizeInventory()), itemstack);
            }
         }
      }
   }

   public static void generateDispenserContents(Random randomIn, List<WeightedRandomChestContent> listIn, TileEntityDispenser dispenser, int maxIn) {
      for (int i = 0; i < maxIn; i++) {
         WeightedRandomChestContent weightedrandomchestcontent = WeightedRandom.getRandomItem(randomIn, listIn);
         int j = weightedrandomchestcontent.minStackSize
            + randomIn.nextInt(weightedrandomchestcontent.maxStackSize - weightedrandomchestcontent.minStackSize + 1);
         if (weightedrandomchestcontent.theItemId.getMaxStackSize() >= j) {
            ItemStack itemstack1 = weightedrandomchestcontent.theItemId.copy();
            itemstack1.stackSize = j;
            dispenser.setInventorySlotContents(randomIn.nextInt(dispenser.getSizeInventory()), itemstack1);
         } else {
            for (int k = 0; k < j; k++) {
               ItemStack itemstack = weightedrandomchestcontent.theItemId.copy();
               itemstack.stackSize = 1;
               dispenser.setInventorySlotContents(randomIn.nextInt(dispenser.getSizeInventory()), itemstack);
            }
         }
      }
   }

   public static List<WeightedRandomChestContent> func_177629_a(
      List<WeightedRandomChestContent> list, WeightedRandomChestContent... weightedRandomChestContents
   ) {
      ArrayList arraylist = Lists.newArrayList(list);
      Collections.addAll(arraylist, weightedRandomChestContents);
      return arraylist;
   }
}
