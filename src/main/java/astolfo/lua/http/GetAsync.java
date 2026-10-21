package astolfo.lua.http;

import astolfo.auth.AuthUtil;
import astolfo.lua.HttpLibrary;
import astolfo.script.ScriptManager;
import astolfo.util.HttpRequest;
import astolfo.util.HttpUtil;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public final class GetAsync extends TwoArgFunction {
   @Override
   public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
      boolean flag = ScriptManager.unsafeFunctions.isEnabled();
      if (!flag) {
         return valueOf(false);
      } else {
         boolean flag1 = luaValue.isstring();
         if (!flag1) {
            return valueOf(false);
         } else {
            boolean flag2 = luaValue2.istable();
            if (!flag2) {
               return valueOf(false);
            } else {
               HttpRequest httprequest = new HttpRequest().setUrl(luaValue.checkjstring());
               httprequest.setHeaderKeys("Astolfo-User");
               httprequest.setHeaderValues(AuthUtil.getHwid());
               httprequest.setUserAgent("Astolfo-Client");
               httprequest.setAccept("");
               httprequest.setContentType("");
               HttpLibrary.asyncExecutor.submit(() -> {
                  String s = HttpUtil.get(httprequest);
                  LuaValue luavalue = luaValue2.get("run");
                  luavalue.call(s);
               });
               return FALSE;
            }
         }
      }
   }
}
