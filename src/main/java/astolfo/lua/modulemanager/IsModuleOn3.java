package astolfo.lua.modulemanager;

import astolfo.Astolfo;
import astolfo.ClientAccess;
import astolfo.module.Module;
import astolfo.module.ModuleManager;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public final class IsModuleOn3 extends OneArgFunction {
   @Override
   public LuaValue call(LuaValue luaValue) {
      if (luaValue.isstring()) {
         LuaString luastring = (LuaString)luaValue;
         Astolfo astolfo = ClientAccess.client;
         ModuleManager modulemanager1 = astolfo.getModuleManager();
         String s = luastring.checkjstring();
         ModuleManager modulemanager = modulemanager1;
         Module module = modulemanager.getModule(s);
         if (module != null) {
            return valueOf(module.isEnabled());
         }
      }

      return valueOf(false);
   }
}
