package astolfo.lua.render;

import astolfo.render.RenderUtil;
import java.awt.Color;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public final class Circle extends VarArgFunction {
   @Override
   public Varargs invoke(Varargs varargs) {
      double d0 = 0.0;
      double d1 = 0.0;
      int i = 0;
      int j = 0;
      double d2 = 0.0;
      int k = 0;
      int l = 0;
      int i1 = 0;
      int j1 = 0;
      int k1 = 1;
      if (varargs.narg() < 8) {
         return valueOf(false);
      } else {
         for (int l1 = varargs.narg(); k1 <= l1; k1++) {
            LuaValue luavalue = varargs.checkvalue(k1);
            switch (k1) {
               case 1:
                  if (luavalue.isnumber()) {
                     d0 = luavalue.checkdouble();
                  }
                  break;
               case 2:
                  if (luavalue.isnumber()) {
                     d1 = luavalue.checkdouble();
                  }
                  break;
               case 3:
                  if (luavalue.isnumber()) {
                     i = luavalue.toint();
                  }
                  break;
               case 4:
                  if (luavalue.isnumber()) {
                     j = luavalue.toint();
                  }
                  break;
               case 5:
                  if (luavalue.isnumber()) {
                     d2 = luavalue.checkdouble();
                  }
                  break;
               case 6:
                  if (luavalue.isnumber()) {
                     k = luavalue.toint();
                  }
                  break;
               case 7:
                  if (luavalue.isnumber()) {
                     l = luavalue.toint();
                  }
                  break;
               case 8:
                  if (luavalue.isnumber()) {
                     i1 = luavalue.toint();
                  }
                  break;
               case 9:
                  if (luavalue.isnumber()) {
                     j1 = luavalue.toint();
                  }
            }
         }

         RenderUtil.drawArc(d0, d1, i, j, d2, new Color(k, l, i1, j1).getRGB());
         return valueOf(true);
      }
   }
}
