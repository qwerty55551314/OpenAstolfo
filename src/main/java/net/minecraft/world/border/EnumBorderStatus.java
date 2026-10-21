package net.minecraft.world.border;

public enum EnumBorderStatus {
   GROWING(4259712),
   SHRINKING(16724016),
   STATIONARY(2138367);

   private final int id;

   private EnumBorderStatus(int i2) {
      this.id = i2;
   }

   public int getID() {
      return this.id;
   }
}
