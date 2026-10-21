package astolfo.lua.player;

import astolfo.lua.PlayerLibrary;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public final class ConvertSpeed extends TwoArgFunction {
   @Override
   public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
      if (luaValue != null && !luaValue.isnil() && luaValue.istable() && luaValue2 != null && !luaValue2.isnil() && luaValue2.isnumber()) {
         double[] adouble = PlayerLibrary.convertSpeed((double)luaValue2.tofloat());
         if (adouble != null) {
            LuaValue luavalue = luaValue.get("x");
            if (!luavalue.isnil() && luavalue.isnumber()) {
               luaValue.set("x", adouble[0]);
            }

            LuaValue luavalue1 = luaValue.get("z");
            if (!luavalue1.isnil() && luavalue1.isnumber()) {
               luaValue.set("z", adouble[1]);
            }

            return luaValue;
         }
      }

      return NIL;
   }
}
