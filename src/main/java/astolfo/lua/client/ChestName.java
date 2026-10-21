package astolfo.lua.client;

import astolfo.ClientAccess;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public final class ChestName extends ZeroArgFunction {
   @Override
   public LuaValue call() {
      GuiScreen guiscreen = ClientAccess.mc.currentScreen;
      if (guiscreen != null) {
         GuiScreen guiscreen1 = ClientAccess.mc.currentScreen;
         if (guiscreen1 instanceof GuiChest) {
            GuiChest guichest = (GuiChest)ClientAccess.mc.currentScreen;
            return valueOf(guichest.lowerChestInventory.getDisplayName().getUnformattedText());
         }
      }

      return valueOf("none");
   }
}
