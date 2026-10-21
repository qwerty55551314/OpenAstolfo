package astolfo.lua.player;

import astolfo.ClientAccess;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public final class Angles extends VarArgFunction {
   @Override
   public Varargs onInvoke(Varargs varargs) {
      return LuaValue.varargsOf(
         new LuaValue[]{LuaValue.valueOf((double)ClientAccess.mc.thePlayer.rotationYaw), LuaValue.valueOf((double)ClientAccess.mc.thePlayer.rotationPitch)}
      );
   }
}
