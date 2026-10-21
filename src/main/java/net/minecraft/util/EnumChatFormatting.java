package net.minecraft.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

public enum EnumChatFormatting {
   BLACK("BLACK", '0', 0),
   DARK_BLUE("DARK_BLUE", '1', 1),
   DARK_GREEN("DARK_GREEN", '2', 2),
   DARK_AQUA("DARK_AQUA", '3', 3),
   DARK_RED("DARK_RED", '4', 4),
   DARK_PURPLE("DARK_PURPLE", '5', 5),
   GOLD("GOLD", '6', 6),
   GRAY("GRAY", '7', 7),
   DARK_GRAY("DARK_GRAY", '8', 8),
   BLUE("BLUE", '9', 9),
   GREEN("GREEN", 'a', 10),
   AQUA("AQUA", 'b', 11),
   RED("RED", 'c', 12),
   LIGHT_PURPLE("LIGHT_PURPLE", 'd', 13),
   YELLOW("YELLOW", 'e', 14),
   WHITE("WHITE", 'f', 15),
   OBFUSCATED("OBFUSCATED", 'k', true),
   BOLD("BOLD", 'l', true),
   STRIKETHROUGH("STRIKETHROUGH", 'm', true),
   UNDERLINE("UNDERLINE", 'n', true),
   ITALIC("ITALIC", 'o', true),
   RESET("RESET", 'r', -1);

   private static final Map<String, EnumChatFormatting> nameMapping = Maps.newHashMap();
   private static final Pattern formattingCodePattern = Pattern.compile("(?i)" + String.valueOf('§') + "[0-9A-FK-OR]");
   private final String name;
   private final char formattingCode;
   private final boolean fancyStyling;
   private final String controlString;
   private final int colorIndex;

   private static String func_175745_c(String string) {
      return string.toLowerCase().replaceAll("[^a-z]", "");
   }

   private EnumChatFormatting(String string2, char c, int i2) {
      this(string2, c, false, i2);
   }

   private EnumChatFormatting(String string2, char c, boolean flag) {
      this(string2, c, flag, -1);
   }

   private EnumChatFormatting(String string2, char c, boolean flag, int i2) {
      this.name = string2;
      this.formattingCode = c;
      this.fancyStyling = flag;
      this.colorIndex = i2;
      this.controlString = "§" + c;
   }

   public int getColorIndex() {
      return this.colorIndex;
   }

   public boolean isFancyStyling() {
      return this.fancyStyling;
   }

   public boolean isColor() {
      return !this.fancyStyling && this != RESET;
   }

   public String getFriendlyName() {
      return this.name().toLowerCase();
   }

   @Override
   public String toString() {
      return this.controlString;
   }

   public static String getTextWithoutFormattingCodes(String text) {
      return text == null ? null : formattingCodePattern.matcher(text).replaceAll("");
   }

   public static EnumChatFormatting getValueByName(String friendlyName) {
      return friendlyName == null ? null : nameMapping.get(func_175745_c(friendlyName));
   }

   public static EnumChatFormatting func_175744_a(int i) {
      if (i < 0) {
         return RESET;
      } else {
         for (EnumChatFormatting enumchatformatting : values()) {
            if (enumchatformatting.getColorIndex() == i) {
               return enumchatformatting;
            }
         }

         return null;
      }
   }

   public static Collection<String> getValidValues(boolean bl, boolean bl2) {
      ArrayList arraylist = Lists.newArrayList();

      for (EnumChatFormatting enumchatformatting : values()) {
         boolean flag = enumchatformatting.isColor();
         if (!flag || bl) {
            boolean flag1 = enumchatformatting.isFancyStyling();
            if (!flag1 || bl2) {
               arraylist.add(enumchatformatting.getFriendlyName());
            }
         }
      }

      return arraylist;
   }

   static {
      for (EnumChatFormatting enumchatformatting : values()) {
         nameMapping.put(func_175745_c(enumchatformatting.name), enumchatformatting);
      }
   }
}
