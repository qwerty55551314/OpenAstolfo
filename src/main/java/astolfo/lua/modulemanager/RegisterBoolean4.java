package astolfo.lua.modulemanager;

import astolfo.Astolfo;
import astolfo.ClientAccess;
import astolfo.lua.LuaApi;
import astolfo.module.ModuleManager;
import astolfo.setting.BooleanSetting;
import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ThreeArgFunction;

public final class RegisterBoolean4 extends ThreeArgFunction {
   @Override
   public LuaValue call(LuaValue luaValue, LuaValue luaValue2, LuaValue luaValue3) {
      String s = null;
      String s1 = null;
      boolean flag = false;
      if (luaValue.isstring()) {
         LuaString luastring = (LuaString)luaValue;
         s = luastring.checkjstring();
      }

      if (luaValue2.isstring()) {
         LuaString luastring1 = (LuaString)luaValue2;
         s1 = luastring1.checkjstring();
      }

      if (luaValue3.isboolean()) {
         LuaBoolean luaboolean = (LuaBoolean)luaValue3;
         flag = luaboolean.toboolean();
      }

      if (s != null && s1 != null) {
         BooleanSetting booleansetting = new BooleanSetting(s1, "script option", flag);
         LuaApi.settingsByName.put(s1, booleansetting);
         Astolfo astolfo = ClientAccess.client;
         ModuleManager modulemanager = astolfo.getModuleManager();
         modulemanager.getModule(s).addSettings(booleansetting);
         return valueOf(true);
      } else {
         return valueOf(false);
      }
   }
}
