package astolfo.lua.modulemanager;

import astolfo.Astolfo;
import astolfo.ClientAccess;
import astolfo.lua.LuaApi;
import astolfo.lua.ModuleManagerLibrary2;
import astolfo.module.ModuleManager;
import astolfo.setting.NumberSetting;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public final class RegisterNumber extends VarArgFunction {
   @Override
   public Varargs invoke(Varargs varargs) {
      String s = null;
      String s1 = null;
      Object object = null;
      Object object1 = null;
      Object object2 = null;
      int i = 1;

      for (int j = varargs.narg(); i <= j; i++) {
         LuaValue luavalue = varargs.checkvalue(i);
         switch (i) {
            case 1:
               if (luavalue.isstring()) {
                  s = luavalue.checkjstring();
               }
               break;
            case 2:
               if (luavalue.isstring()) {
                  s1 = luavalue.checkjstring();
               }
               break;
            case 3:
               if (luavalue.isnumber()) {
                  Double d2 = null;
                  Integer integer2 = null;
                  boolean flag2 = false;
                  if (luavalue.isint()) {
                     try {
                        integer2 = luavalue.checkint();
                        flag2 = true;
                     } catch (LuaError luaerror5) {
                     }
                  }

                  if (integer2 == null) {
                     try {
                        d2 = luavalue.checkdouble();
                     } catch (LuaError luaerror4) {
                     }
                  }

                  if (d2 != null) {
                     object = d2;
                  } else if (integer2 != null && flag2) {
                     object = integer2;
                  }
               }
               break;
            case 4:
               if (luavalue.isnumber()) {
                  Double d1 = null;
                  Integer integer1 = null;
                  boolean flag1 = false;
                  if (luavalue.isint()) {
                     try {
                        integer1 = luavalue.checkint();
                        flag1 = true;
                     } catch (LuaError luaerror3) {
                     }
                  }

                  if (integer1 == null) {
                     try {
                        d1 = luavalue.checkdouble();
                     } catch (LuaError luaerror2) {
                     }
                  }

                  if (d1 != null) {
                     object1 = d1;
                  } else if (integer1 != null && flag1) {
                     object1 = integer1;
                  }
               }
               break;
            case 5:
               if (luavalue.isnumber()) {
                  Double d0 = null;
                  Integer integer = null;
                  boolean flag = false;
                  if (luavalue.isint()) {
                     try {
                        integer = luavalue.checkint();
                        flag = true;
                     } catch (LuaError luaerror) {
                     }
                  }

                  if (integer == null) {
                     try {
                        d0 = luavalue.checkdouble();
                     } catch (LuaError luaerror1) {
                     }
                  }

                  if (d0 != null) {
                     object2 = d0;
                  } else if (integer != null && flag) {
                     object2 = integer;
                  }
               }
         }
      }

      if (s != null && s1 != null && object != null && object1 != null && object2 != null) {
         NumberSetting numbersetting = new NumberSetting(s1, "script option", (Number)object2, (Number)object, (Number)object1);
         LuaApi.settingsByName.put(s1, numbersetting);
         Astolfo astolfo = ClientAccess.client;
         ModuleManager modulemanager = astolfo.getModuleManager();
         modulemanager.getModule(s).addSettings(numbersetting);
         return ModuleManagerLibrary2.varargsOf(new LuaValue[]{valueOf(true)});
      } else {
         return ModuleManagerLibrary2.varargsOf(new LuaValue[]{valueOf(false)});
      }
   }
}
