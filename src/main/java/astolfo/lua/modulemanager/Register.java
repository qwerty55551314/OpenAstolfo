package astolfo.lua.modulemanager;

import astolfo.Astolfo;
import astolfo.ClientAccess;
import astolfo.module.ModuleInfo;
import astolfo.module.scripts.ScriptModule;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public final class Register extends TwoArgFunction {
   @Override
   public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
      String s = null;
      LuaTable luatable = null;
      if (luaValue.isstring()) {
         LuaString luastring = (LuaString)luaValue;
         s = luastring.checkjstring();
      }

      boolean flag = luaValue2.isfunction();
      if (flag) {
         LuaValue luavalue = luaValue2.call();
         if (luavalue.istable()) {
            luatable = (LuaTable)luavalue.call();
         }
      } else if (luaValue2.istable()) {
         luatable = (LuaTable)luaValue2;
      }

      if (s != null && luatable != null) {
         Astolfo astolfo = ClientAccess.client;
         astolfo.getModuleManager().add(new ModuleInfo[]{new ScriptModule(s, luatable)});
         return valueOf(true);
      } else {
         return valueOf(false);
      }
   }
}
