package astolfo.lua.player;

import astolfo.ClientAccess;
import astolfo.module.movement.LongJump;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public final class Damage extends OneArgFunction {
   @Override
   public LuaValue call(LuaValue luaValue) {
      if (!luaValue.isnumber()) {
         return valueOf(false);
      } else if (ClientAccess.mc.thePlayer != null) {
         LongJump.selfDamage(luaValue.checkdouble());
         return valueOf(true);
      } else {
         return valueOf(false);
      }
   }
}
