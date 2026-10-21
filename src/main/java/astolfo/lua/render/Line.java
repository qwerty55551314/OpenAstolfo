package astolfo.lua.render;

import astolfo.render.RenderUtil;
import java.awt.Color;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public final class Line extends VarArgFunction {
   @Override
   public Varargs invoke(Varargs varargs) {
      float f = 0.0F;
      float f1 = 0.0F;
      float f2 = 0.0F;
      float f3 = 0.0F;
      int i = 0;
      int j = 0;
      int k = 0;
      int l = 0;
      float f4 = 1.0F;
      int i1 = 1;
      if (varargs.narg() < 9) {
         return valueOf(false);
      } else {
         for (int j1 = varargs.narg(); i1 <= j1; i1++) {
            LuaValue luavalue = varargs.checkvalue(i1);
            switch (i1) {
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
                  break;
               case 4:
                  if (luavalue.isnumber()) {
                     f3 = luavalue.tofloat();
                  }
                  break;
               case 5:
                  if (luavalue.isnumber()) {
                     f4 = luavalue.tofloat();
                  }
                  break;
               case 6:
                  if (luavalue.isnumber()) {
                     i = luavalue.toint();
                  }
                  break;
               case 7:
                  if (luavalue.isnumber()) {
                     j = luavalue.toint();
                  }
                  break;
               case 8:
                  if (luavalue.isnumber()) {
                     k = luavalue.toint();
                  }
                  break;
               case 9:
                  if (luavalue.isnumber()) {
                     l = luavalue.toint();
                  }
            }
         }

         RenderUtil.drawLine(f4, f, f1, f2, f3, new Color(i, j, k, l).getRGB());
         return valueOf(true);
      }
   }
}
