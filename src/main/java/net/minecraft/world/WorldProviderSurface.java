package net.minecraft.world;

public class WorldProviderSurface extends WorldProvider {
   private static final String c = "Overworld";

   @Override
   public String getDimensionName() {
      return c;
   }

   @Override
   public String getInternalNameSuffix() {
      return "";
   }
}
