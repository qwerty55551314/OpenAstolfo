package astolfo.lua.modulemanager;

import astolfo.Astolfo;
import astolfo.ClientAccess;
import astolfo.lua.LuaApi;
import astolfo.module.Module;
import astolfo.module.ModuleManager;
import astolfo.setting.ColorSetting;
import astolfo.setting.MapSetting;
import astolfo.setting.Setting;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public final class Option3 extends VarArgFunction {
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
         switch (i) {
            case 2:
               if (luavalue1.isstring()) {
                  String s = luavalue.checkjstring() + luavalue1.checkjstring();
                  Setting setting = LuaApi.settingsByName.get(s);
                  if (setting != null) {
                     return CoerceJavaToLua.coerce(setting.getValue());
                  }

                  Astolfo astolfo = ClientAccess.client;
                  ModuleManager modulemanager2 = astolfo.getModuleManager();
                  String s2 = luavalue.checkjstring();
                  ModuleManager modulemanager = modulemanager2;
                  Module module1 = modulemanager.getModule(s2);
                  if (module1 != null) {
                     for (Setting setting4 : module1.getSettings()) {
                        if (setting4.getName().equals(luavalue1.checkjstring())) {
                           LuaApi.settingsByName.put(s, setting4);
                           return CoerceJavaToLua.coerce(setting4.getValue());
                        }
                     }
                  }
               }
               break;
            case 3:
               if (luavalue1.isstring() && luavalue2.isstring()) {
                  String s1 = luavalue.checkjstring() + luavalue1.checkjstring() + luavalue2.checkjstring();
                  Setting setting1 = LuaApi.settingsByName.get(s1);
                  if (setting1 != null) {
                     if (setting1 instanceof ColorSetting) {
                        ColorSetting colorsetting1 = (ColorSetting)setting1;
                        return new LuaTable(
                           varargsOf(
                              new LuaValue[]{
                                 valueOf(colorsetting1.getValue().getRed()),
                                 valueOf(colorsetting1.getValue().getGreen()),
                                 valueOf(colorsetting1.getValue().getBlue()),
                                 valueOf(colorsetting1.getValue().getAlpha())
                              }
                           )
                        );
                     }

                     return CoerceJavaToLua.coerce(setting1.getValue());
                  }

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
                              if (setting3 instanceof ColorSetting) {
                                 ColorSetting colorsetting = (ColorSetting)setting3;
                                 return new LuaTable(
                                    varargsOf(
                                       new LuaValue[]{
                                          valueOf(colorsetting.getValue().getRed()),
                                          valueOf(colorsetting.getValue().getGreen()),
                                          valueOf(colorsetting.getValue().getBlue()),
                                          valueOf(colorsetting.getValue().getAlpha())
                                       }
                                    )
                                 );
                              }

                              return CoerceJavaToLua.coerce(setting3.getValue());
                           }
                        }
                     }
                  }
               }
         }

         return NIL;
      }
   }
}
