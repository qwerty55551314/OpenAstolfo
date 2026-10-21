package net.minecraft.util;

import java.util.Random;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

public class WeightedRandomFishable extends WeightedRandom.Item {
   private final ItemStack returnStack;
   private float maxDamagePercent;
   private boolean enchantable;
   private static final long b = -3692151808324534242L;

   public WeightedRandomFishable(ItemStack returnStackIn, int itemWeightIn) {
      super(itemWeightIn);
      this.returnStack = returnStackIn;
   }

   public ItemStack getItemStack(Random randomIn) {
      ItemStack itemstack = this.returnStack.copy();
      if (this.maxDamagePercent > 0.0F) {
         int i = (int)(this.maxDamagePercent * (float)this.returnStack.getMaxDamage());
         int j = itemstack.getMaxDamage() - randomIn.nextInt(randomIn.nextInt(i) + 1);
         if (j > i) {
            j = i;
         }

         if (j < 1) {
            j = 1;
         }

         itemstack.setItemDamage(j);
      }

      if (this.enchantable) {
         EnchantmentHelper.addRandomEnchantment(randomIn, itemstack, (int)b);
      }

      return itemstack;
   }

   public WeightedRandomFishable setMaxDamagePercent(float maxDamagePercentIn) {
      this.maxDamagePercent = maxDamagePercentIn;
      return this;
   }

   public WeightedRandomFishable setEnchantable() {
      this.enchantable = true;
      return this;
   }
}
