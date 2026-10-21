package astolfo.lua.render;

import astolfo.module.hidden.PlayerListTracker;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public final class WorldToScreen extends VarArgFunction {
   @Override
   public Varargs invoke(Varargs varargs) {
      float f = 0.0F;
      float f1 = 0.0F;
      float f2 = 0.0F;
      if (varargs.narg() < 3) {
         return valueOf(false);
      } else {
         int i = 1;

         for (int j = varargs.narg(); i <= j; i++) {
            LuaValue luavalue = varargs.checkvalue(i);
            switch (i) {
               case 1:
                  if (luavalue.isnumber()) {
                     f = luavalue.tofloat();
                  }
                  break;
               case 2:
                  if (luavalue.isnumber()) {
                     f1 = luavalue.tofloat();
                  }
                  break;
               case 3:
                  if (luavalue.isnumber()) {
                     f2 = luavalue.tofloat();
                  }
            }
         }

         float[] afloat = PlayerListTracker.project(f, f1, f2);
         return (Varargs)(afloat != null
            ? LuaValue.varargsOf(new LuaValue[]{LuaValue.valueOf((double)afloat[0]), LuaValue.valueOf((double)afloat[1]), LuaValue.valueOf((double)afloat[2])})
            : NIL);
      }
   }
}
