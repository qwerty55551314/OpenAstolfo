package astolfo.lua.render;

import astolfo.render.RenderUtil;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public final class Rect extends VarArgFunction {
   @Override
   public Varargs invoke(Varargs varargs) {
      double d0 = 0.0;
      double d1 = 0.0;
      double d2 = 0.0;
      double d3 = 0.0;
      int i = 0;
      int j = 0;
      int k = 0;
      int l = 0;
      int i1 = 1;
      if (varargs.narg() < 8) {
         return valueOf(false);
      } else {
         for (int j1 = varargs.narg(); i1 <= j1; i1++) {
            LuaValue luavalue = varargs.checkvalue(i1);
            switch (i1) {
               case 1:
                  if (luavalue.isnumber()) {
                     d0 = luavalue.todouble();
                  }
                  break;
               case 2:
                  if (luavalue.isnumber()) {
                     d1 = luavalue.todouble();
                  }
                  break;
               case 3:
                  if (luavalue.isnumber()) {
                     d2 = luavalue.todouble();
                  }
                  break;
               case 4:
                  if (luavalue.isnumber()) {
                     d3 = luavalue.todouble();
                  }
                  break;
               case 5:
                  if (luavalue.isnumber()) {
                     i = luavalue.toint();
                  }
                  break;
               case 6:
                  if (luavalue.isnumber()) {
                     j = luavalue.toint();
                  }
                  break;
               case 7:
                  if (luavalue.isnumber()) {
                     k = luavalue.toint();
                  }
                  break;
               case 8:
                  if (luavalue.isnumber()) {
                     l = luavalue.toint();
                  }
            }
         }

         RenderUtil.drawRectRgba(d0, d1, d2, d3, i, j, k, l);
         return valueOf(true);
      }
   }
}
