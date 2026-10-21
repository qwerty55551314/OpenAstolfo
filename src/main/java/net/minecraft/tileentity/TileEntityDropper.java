package net.minecraft.tileentity;

public class TileEntityDropper extends TileEntityDispenser {
   @Override
   public String getName() {
      return this.hasCustomName() ? super.customName : "container.dropper";
   }

   @Override
   public String getGuiID() {
      return "minecraft:dropper";
   }
}
