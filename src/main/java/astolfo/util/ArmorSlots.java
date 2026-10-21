package astolfo.util;

public final class ArmorSlots {
   public static ArmorSlot byArmorType(int i) {
      for (ArmorSlot armorslot : ArmorSlot.values()) {
         if (armorslot.armorType == i) {
            return armorslot;
         }
      }

      return null;
   }
}
