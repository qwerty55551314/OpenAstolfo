package astolfo.lua.player;

import astolfo.ClientAccess;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public final class HeldItemSlot extends ZeroArgFunction {
   @Override
   public LuaValue call() {
      return (LuaValue)(ClientAccess.mc.thePlayer != null ? valueOf(ClientAccess.mc.thePlayer.inventory.currentItem + 1) : NIL);
   }
}
