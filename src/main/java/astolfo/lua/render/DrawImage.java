package astolfo.lua.render;

import astolfo.ClientAccess;
import astolfo.lua.RenderLibrary;
import astolfo.script.ScriptManager;
import net.minecraft.client.gui.Gui;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public final class DrawImage extends VarArgFunction {
   @Override
   public Varargs invoke(Varargs varargs) {
      if (!ScriptManager.unsafeFunctions.isEnabled()) {
         return valueOf(false);
      } else {
         String s = "";
         float f = 0.0F;
         float f1 = 0.0F;
         int i = 0;
         int j = 0;
         boolean flag = false;
         int k = 1;
         if (varargs.narg() < 5 && varargs.narg() > 6) {
            return valueOf(false);
         } else {
            for (int l = varargs.narg(); k <= l; k++) {
               LuaValue luavalue = varargs.checkvalue(k);
               switch (k) {
                  case 1:
                     if (luavalue.isstring()) {
                        s = luavalue.checkjstring();
                     }
                     break;
                  case 2:
                     if (luavalue.isnumber()) {
                        f = luavalue.tofloat();
                     }
                     break;
                  case 3:
                     if (luavalue.isnumber()) {
                        f1 = luavalue.tofloat();
                     }
                     break;
                  case 4:
                     if (luavalue.isnumber()) {
                        i = luavalue.toint();
                     }
                     break;
                  case 5:
                     if (luavalue.isnumber()) {
                        j = luavalue.toint();
                     }
                     break;
                  case 6:
                     if (luavalue.checkboolean()) {
                        flag = luavalue.checkboolean();
                     }
               }
            }

            ClientAccess.mc.getTextureManager().bindTexture(RenderLibrary.getOrDownloadImageTexture(s, flag));
            Gui.drawModalRectWithCustomSizedTexture((int)f, (int)f1, 0.0F, 0.0F, i, j, (float)i, (float)j);
            return valueOf(true);
         }
      }
   }
}
