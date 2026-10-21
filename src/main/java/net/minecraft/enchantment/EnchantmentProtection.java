package net.minecraft.enchantment;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class EnchantmentProtection extends Enchantment {
   private static final String[] protectionName = new String[]{"all", "fire", "fall", "explosion", "projectile"};
   private static final int[] baseEnchantability = new int[]{1, 10, 5, 5, 3};
   private static final int[] levelEnchantability = new int[]{11, 8, 6, 8, 6};
   private static final int[] thresholdEnchantability = new int[]{20, 12, 10, 12, 15};
   public final int protectionType;

   public EnchantmentProtection(int i, ResourceLocation resourceLocation, int j, int k) {
      super(i, resourceLocation, j, EnumEnchantmentType.ARMOR);
      this.protectionType = k;
      if (k == 2) {
         super.type = EnumEnchantmentType.ARMOR_FEET;
      }
   }

   @Override
   public int getMinEnchantability(int enchantmentLevel) {
      return baseEnchantability[this.protectionType] + (enchantmentLevel - 1) * levelEnchantability[this.protectionType];
   }

   @Override
   public int getMaxEnchantability(int enchantmentLevel) {
      return this.getMinEnchantability(enchantmentLevel) + thresholdEnchantability[this.protectionType];
   }

   @Override
   public int getMaxLevel() {
      return 4;
   }

   @Override
   public int calcModifierDamage(int level, DamageSource source) {
      boolean flag = source.canHarmInCreative();
      if (flag) {
         return 0;
      } else {
         float f = (float)(6 + level * level) / 3.0F;
         int i = this.protectionType;
         int i1;
         if (i == 0) {
            i1 = MathHelper.floor_float(f * 0.75F);
         } else {
            int j = this.protectionType;
            if (j == 1 && source.isFireDamage()) {
               i1 = MathHelper.floor_float(f * 1.25F);
            } else {
               int k = this.protectionType;
               if (k == 2 && source == DamageSource.fall) {
                  i1 = MathHelper.floor_float(f * 2.5F);
               } else {
                  int l = this.protectionType;
                  if (l == 3 && source.isExplosion()) {
                     i1 = MathHelper.floor_float(f * 1.5F);
                  } else {
                     if (this.protectionType == 4) {
                        boolean flag1 = source.isProjectile();
                        if (flag1) {
                           return MathHelper.floor_float(f * 1.5F);
                        }
                     }

                     i1 = 0;
                  }
               }
            }
         }

         return i1;
      }
   }

   @Override
   public String getName() {
      return "enchantment.protect." + protectionName[this.protectionType];
   }

   @Override
   public boolean canApplyTogether(Enchantment ench) {
      if (!(ench instanceof EnchantmentProtection)) {
         return super.canApplyTogether(ench);
      } else {
         EnchantmentProtection enchantmentprotection = (EnchantmentProtection)ench;
         return enchantmentprotection.protectionType == this.protectionType ? false : this.protectionType == 2 || enchantmentprotection.protectionType == 2;
      }
   }

   public static int getFireTimeForEntity(Entity entity, int i) {
      int i1 = EnchantmentHelper.getMaxEnchantmentLevel(Enchantment.fireProtection.effectId, entity.getInventory());
      if (i1 > 0) {
         i -= MathHelper.floor_float((float)i * (float)i1 * 0.15F);
      }

      return i;
   }

   public static double func_92092_a(Entity entity, double d) {
      int i = EnchantmentHelper.getMaxEnchantmentLevel(Enchantment.blastProtection.effectId, entity.getInventory());
      if (i > 0) {
         d -= (double)MathHelper.floor_double(d * (double)((float)i * 0.15F));
      }

      return d;
   }
}
