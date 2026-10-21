package astolfo.lua;

import astolfo.setting.BooleanSetting;
import astolfo.setting.NumberSetting;
import astolfo.setting.Setting;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public final class GetSettingFunction extends OneArgFunction {
   @Override
   public LuaValue call(LuaValue luaValue) {
      if (luaValue.isstring()) {
         LuaString luastring = (LuaString)luaValue;
         Setting setting = LuaApi.settingsByName.get(luastring.checkjstring());
         if (setting instanceof BooleanSetting) {
            return valueOf(setting.asBoolean());
         } else if (setting instanceof NumberSetting) {
            NumberSetting numbersetting = (NumberSetting)setting;
            return CoerceJavaToLua.coerce(numbersetting.getValue());
         } else {
            return valueOf(true);
         }
      } else {
         return valueOf(false);
      }
   }
}
