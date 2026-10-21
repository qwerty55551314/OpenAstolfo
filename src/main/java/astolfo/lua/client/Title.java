package astolfo.lua.client;

import astolfo.ClientAccess;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public final class Title extends ZeroArgFunction {
   @Override
   public LuaValue call() {
      return ClientAccess.mc.ingameGUI != null ? valueOf(ClientAccess.mc.ingameGUI.displayedTitle) : valueOf("");
   }
}
