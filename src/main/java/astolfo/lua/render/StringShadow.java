package astolfo.lua.render;

import astolfo.ClientAccess;
import java.awt.Color;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public final class StringShadow extends VarArgFunction {
   @Override
   public Varargs invoke(Varargs varargs) {
      String s = null;
      int i = 0;
      int j = 0;
      int k = 0;
      int l = 0;
      int i1 = 0;
      int j1 = 1;

      for (int k1 = varargs.narg(); j1 <= k1; j1++) {
         LuaValue luavalue = varargs.checkvalue(j1);
         switch (j1) {
            case 1:
               if (luavalue.isstring()) {
                  s = luavalue.checkjstring();
               }
               break;
            case 2:
               if (luavalue.isnumber()) {
                  i = luavalue.toint();
               }
               break;
            case 3:
               if (luavalue.isnumber()) {
                  j = luavalue.toint();
               }
               break;
            case 4:
               if (luavalue.isnumber()) {
                  k = luavalue.toint();
               }
               break;
            case 5:
               if (luavalue.isnumber()) {
                  l = luavalue.toint();
               }
               break;
            case 6:
               if (luavalue.isnumber()) {
                  i1 = luavalue.toint();
               }
         }
      }

      if (s != null) {
         ClientAccess.mc.fontRendererObj.drawStringWithShadow(s, (float)i, (float)j, new Color(k, l, i1).getRGB());
         return valueOf(false);
      } else {
         return valueOf(true);
      }
   }
}
