package astolfo.lua.modulemanager;

import astolfo.Astolfo;
import astolfo.ClientAccess;
import astolfo.lua.LuaApi;
import astolfo.lua.ModuleManagerLibrary4;
import astolfo.module.Module;
import astolfo.module.ModuleManager;
import astolfo.setting.MapSetting;
import astolfo.setting.Setting;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public final class SetOption extends VarArgFunction {
   @Override
   public Varargs onInvoke(Varargs varargs) {
      LuaValue luavalue = varargs.arg1();
      boolean flag = luavalue.isstring();
      if (!flag) {
         return NIL;
      } else {
         int i = varargs.narg();
         LuaValue luavalue1 = varargs.arg(2);
         LuaValue luavalue2 = varargs.arg(3);
         LuaValue luavalue3 = varargs.arg(4);
         switch (i) {
            case 3:
               if (luavalue1.isstring()) {
                  String s = luavalue.checkjstring() + luavalue1.checkjstring();
                  Setting setting = LuaApi.settingsByName.get(s);
                  if (setting == null) {
                     Astolfo astolfo = ClientAccess.client;
                     ModuleManager modulemanager2 = astolfo.getModuleManager();
                     String s2 = luavalue.checkjstring();
                     ModuleManager modulemanager = modulemanager2;
                     Module module1 = modulemanager.getModule(s2);
                     if (module1 != null) {
                        for (Setting setting4 : module1.getSettings()) {
                           if (setting4.getName().equals(luavalue1.checkjstring())) {
                              LuaApi.settingsByName.put(s, setting4);
                              setting = setting4;
                           }
                        }
                     }
                  }

                  if (setting != null) {
                     ModuleManagerLibrary4.accessApplyLuaValue(luavalue2, setting);
                  }
               }
               break;
            case 4:
               if (luavalue1.isstring() && luavalue2.isstring()) {
                  String s1 = luavalue.checkjstring() + luavalue1.checkjstring() + luavalue2.checkjstring();
                  Setting setting1 = LuaApi.settingsByName.get(s1);
                  if (setting1 == null) {
                     Astolfo astolfo1 = ClientAccess.client;
                     ModuleManager modulemanager3 = astolfo1.getModuleManager();
                     String s3 = luavalue.checkjstring();
                     ModuleManager modulemanager1 = modulemanager3;
                     Module module = modulemanager1.getModule(s3);
                     if (module != null) {
                        for (Setting setting2 : module.getSettings()) {
                           if (setting2 instanceof MapSetting) {
                              MapSetting mapsetting = (MapSetting)setting2;
                              Setting setting3 = mapsetting.getSettings().get(luavalue2.checkjstring());
                              if (setting3 != null) {
                                 LuaApi.settingsByName.put(s1, setting3);
                                 setting1 = setting3;
                              }
                           }
                        }
                     }
                  }

                  if (setting1 != null) {
                     ModuleManagerLibrary4.accessApplyLuaValue(luavalue3, setting1);
                  }
               }
         }

         return NIL;
      }
   }
}
