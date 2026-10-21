package astolfo.lua.player;

import astolfo.ClientAccess;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public final class HurtTime extends ZeroArgFunction {
   @Override
   public LuaValue call() {
      return (LuaValue)(ClientAccess.mc.thePlayer != null ? valueOf(ClientAccess.mc.thePlayer.hurtTime) : NIL);
   }
}
