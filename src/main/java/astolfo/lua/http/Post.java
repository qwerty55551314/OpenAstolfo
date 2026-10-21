package astolfo.lua.http;

import astolfo.auth.AuthUtil;
import astolfo.script.ScriptManager;
import astolfo.util.HttpRequest;
import astolfo.util.HttpUtil;
import java.util.ArrayList;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public final class Post extends OneArgFunction {
   @Override
   public LuaValue call(LuaValue luaValue) {
      boolean flag = ScriptManager.unsafeFunctions.isEnabled();
      if (!flag) {
         return valueOf(false);
      } else if (!luaValue.isstring()) {
         return valueOf(false);
      } else {
         String s = luaValue.checkjstring();
         HttpRequest httprequest = new HttpRequest().setUrl(s);
         httprequest.setHeaderKeys("Astolfo-User");
         httprequest.setHeaderValues(AuthUtil.getHwid());
         httprequest.setUserAgent("Astolfo-Client");
         httprequest.setAccept("");
         httprequest.setContentType("");
         ArrayList arraylist = new ArrayList();
         ArrayList arraylist1 = new ArrayList();
         String[] astring = s.split("\\?");
         int i = astring.length;
         if (i > 1) {
            String[] astring1 = astring[1].split("&");

            for (String s1 : astring1) {
               String[] astring2 = s1.split("=");
               if (astring2.length > 0) {
                  arraylist.add(astring2[0]);
                  arraylist1.add(astring2[1]);
               }
            }
         }

         String[] astring3 = new String[arraylist.size()];
         String[] astring4 = new String[arraylist1.size()];
         arraylist.toArray(astring3);
         arraylist1.toArray(astring4);
         httprequest.setParamKeys(astring3);
         httprequest.setParamValues(astring4);
         String s2 = HttpUtil.post(httprequest);
         return valueOf(s2);
      }
   }
}
