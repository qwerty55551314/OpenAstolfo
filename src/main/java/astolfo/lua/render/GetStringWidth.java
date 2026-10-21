package astolfo.lua.render;

import astolfo.ClientAccess;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public final class GetStringWidth extends OneArgFunction {
   @Override
   public LuaValue call(LuaValue luaValue) {
      return luaValue.isstring() ? valueOf(ClientAccess.mc.fontRendererObj.getStringWidth(luaValue.checkjstring())) : valueOf(0);
   }
}
