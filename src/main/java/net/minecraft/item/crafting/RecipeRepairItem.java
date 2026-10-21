package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RecipeRepairItem implements IRecipe {
   private static final long b = -7843211187075415964L;

   @Override
   public boolean matches(InventoryCrafting inv, World worldIn) {
      ArrayList arraylist = Lists.newArrayList();

      for (int i = 0; i < inv.getSizeInventory(); i++) {
         ItemStack itemstack = inv.getStackInSlot(i);
         if (itemstack != null) {
            arraylist.add(itemstack);
            if (arraylist.size() > 1) {
               ItemStack itemstack1 = (ItemStack)arraylist.get(0);
               if (itemstack.getItem() != itemstack1.getItem() || itemstack1.stackSize != 1 || itemstack.stackSize != 1 || !itemstack1.getItem().isDamageable()
                  )
                {
                  return false;
               }
            }
         }
      }

      return arraylist.size() == 2;
   }

   @Override
   public ItemStack getCraftingResult(InventoryCrafting inv) {
      ArrayList arraylist = Lists.newArrayList();

      for (int i = 0; i < inv.getSizeInventory(); i++) {
         ItemStack itemstack = inv.getStackInSlot(i);
         if (itemstack != null) {
            arraylist.add(itemstack);
            if (arraylist.size() > 1) {
               ItemStack itemstack1 = (ItemStack)arraylist.get(0);
               if (itemstack.getItem() != itemstack1.getItem() || itemstack1.stackSize != 1 || itemstack.stackSize != 1 || !itemstack1.getItem().isDamageable()
                  )
                {
                  return null;
               }
            }
         }
      }

      if (arraylist.size() == 2) {
         ItemStack itemstack2 = (ItemStack)arraylist.get(0);
         ItemStack itemstack3 = (ItemStack)arraylist.get(1);
         if (itemstack2.getItem() == itemstack3.getItem()) {
            int j1 = itemstack2.stackSize;
            if (j1 == 1 && itemstack3.stackSize == 1) {
               Item item = itemstack2.getItem();
               if (item.isDamageable()) {
                  Item item1 = itemstack2.getItem();
                  int j = item1.getMaxDamage() - itemstack2.getItemDamage();
                  int k = item1.getMaxDamage() - itemstack3.getItemDamage();
                  int l = j + k + item1.getMaxDamage() * 5 / (int)b;
                  int i1 = item1.getMaxDamage() - l;
                  if (i1 < 0) {
                     i1 = 0;
                  }

                  return new ItemStack(itemstack2.getItem(), 1, i1);
               }
            }
         }
      }

      return null;
   }

   @Override
   public int getRecipeSize() {
      return 4;
   }

   @Override
   public ItemStack getRecipeOutput() {
      return null;
   }

   @Override
   public ItemStack[] getRemainingItems(InventoryCrafting inv) {
      ItemStack[] aitemstack = new ItemStack[inv.getSizeInventory()];

      for (int i = 0; i < aitemstack.length; i++) {
         ItemStack itemstack = inv.getStackInSlot(i);
         if (itemstack != null && itemstack.getItem().hasContainerItem()) {
            aitemstack[i] = new ItemStack(itemstack.getItem().getContainerItem());
         }
      }

      return aitemstack;
   }
}
