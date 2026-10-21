package astolfo.lua.client;

import astolfo.Astolfo;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public final class Config extends ZeroArgFunction {
   @Override
   public LuaValue call() {
      return valueOf(Astolfo.INSTANCE.getConfigName());
   }
}
