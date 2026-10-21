package net.minecraft.enchantment;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

public class EnchantmentThorns extends Enchantment {
   private static final String p = "damage.thorns";

   public EnchantmentThorns(int i, ResourceLocation resourceLocation, int j) {
      super(i, resourceLocation, j, EnumEnchantmentType.ARMOR_TORSO);
      this.setName("thorns");
   }

   @Override
   public int getMinEnchantability(int enchantmentLevel) {
      return 10 + 20 * (enchantmentLevel - 1);
   }

   @Override
   public int getMaxEnchantability(int enchantmentLevel) {
      return super.getMinEnchantability(enchantmentLevel) + 50;
   }

   @Override
   public int getMaxLevel() {
      return 3;
   }

   @Override
   public boolean canApply(ItemStack stack) {
      return stack.getItem() instanceof ItemArmor ? true : super.canApply(stack);
   }

   @Override
   public void onUserHurt(EntityLivingBase user, Entity attacker, int level) {
      Random random = user.getRNG();
      ItemStack itemstack = EnchantmentHelper.getEnchantedItem(Enchantment.thorns, user);
      if (func_92094_a(level, random)) {
         if (attacker != null) {
            attacker.attackEntityFrom(DamageSource.causeThornsDamage(user), (float)func_92095_b(level, random));
            attacker.playSound(p, 0.5F, 1.0F);
         }

         if (itemstack != null) {
            itemstack.damageItem(3, user);
         }
      } else if (itemstack != null) {
         itemstack.damageItem(1, user);
      }
   }

   public static boolean func_92094_a(int i, Random random2) {
      boolean flag;
      if (i <= 0) {
         flag = false;
      } else {
         float f;
         int i1 = (f = random2.nextFloat() - 0.15F * (float)i) == 0.0F ? 0 : (f < 0.0F ? -1 : 1);
         flag = i1 < 0;
      }

      return flag;
   }

   public static int func_92095_b(int i, Random random2) {
      return i > 10 ? i - 10 : 1 + random2.nextInt(4);
   }
}
