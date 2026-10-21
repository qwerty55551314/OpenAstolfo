package net.minecraft.potion;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.util.ResourceLocation;

public class PotionAttackDamage extends Potion {
   public PotionAttackDamage(int potionID, ResourceLocation location, boolean badEffect, int potionColor) {
      super(potionID, location, badEffect, potionColor);
   }

   @Override
   public double getAttributeModifierAmount(int i, AttributeModifier modifier) {
      return super.id == Potion.weakness.id ? (double)(-0.5F * (float)(i + 1)) : 1.3 * (double)(i + 1);
   }
}
