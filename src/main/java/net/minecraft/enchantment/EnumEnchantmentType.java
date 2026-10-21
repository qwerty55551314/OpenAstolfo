package net.minecraft.enchantment;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

public enum EnumEnchantmentType {
   ALL,
   ARMOR,
   ARMOR_FEET,
   ARMOR_LEGS,
   ARMOR_TORSO,
   ARMOR_HEAD,
   WEAPON,
   DIGGER,
   FISHING_ROD,
   BREAKABLE,
   BOW;

   public boolean canEnchantItem(Item item) {
      EnumEnchantmentType enumenchantmenttype = ALL;
      if (this == enumenchantmenttype) {
         return true;
      } else {
         if (this == BREAKABLE) {
            boolean flag = item.isDamageable();
            if (flag) {
               return true;
            }
         }

         boolean flag1 = item instanceof ItemArmor;
         if (flag1) {
            if (this == ARMOR) {
               return true;
            } else {
               ItemArmor itemarmor = (ItemArmor)item;
               return itemarmor.armorType == 0
                  ? this == ARMOR_HEAD
                  : (
                     itemarmor.armorType == 2
                        ? this == ARMOR_LEGS
                        : (itemarmor.armorType == 1 ? this == ARMOR_TORSO : (itemarmor.armorType == 3 ? this == ARMOR_FEET : false))
                  );
            }
         } else {
            boolean flag2 = item instanceof ItemSword;
            boolean flag5;
            if (flag2) {
               flag5 = this == WEAPON;
            } else {
               boolean flag3 = item instanceof ItemTool;
               if (flag3) {
                  flag5 = this == DIGGER;
               } else {
                  boolean flag4 = item instanceof ItemBow;
                  flag5 = flag4 ? this == BOW : (item instanceof ItemFishingRod ? this == FISHING_ROD : false);
               }
            }

            return flag5;
         }
      }
   }
}
