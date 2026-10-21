package astolfo.lua.client;

import astolfo.ClientChat;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public final class Print extends OneArgFunction {
   @Override
   public LuaValue call(LuaValue luaValue) {
      ClientChat.CHAT.print(luaValue.tojstring());
      return valueOf(true);
   }
}
