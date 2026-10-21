package net.minecraft.item;

import net.minecraft.util.EnumChatFormatting;

public enum EnumRarity {
   COMMON(EnumChatFormatting.WHITE, "Common"),
   UNCOMMON(EnumChatFormatting.YELLOW, "Uncommon"),
   RARE(EnumChatFormatting.AQUA, "Rare"),
   EPIC(EnumChatFormatting.LIGHT_PURPLE, "Epic");

   public final EnumChatFormatting rarityColor;
   public final String rarityName;

   private EnumRarity(EnumChatFormatting enumChatFormatting, String string2) {
      this.rarityColor = enumChatFormatting;
      this.rarityName = string2;
   }
}
