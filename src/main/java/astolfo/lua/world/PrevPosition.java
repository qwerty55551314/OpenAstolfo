package astolfo.lua.world;

import astolfo.lua.WorldLibrary;
import net.minecraft.entity.Entity;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public final class PrevPosition extends VarArgFunction {
   @Override
   public Varargs onInvoke(Varargs varargs) {
      LuaValue luavalue = varargs.arg1();
      if (luavalue != null && !luavalue.isnil() && luavalue.isnumber()) {
         Entity entity = WorldLibrary.entitiesById.get(luavalue.checkint());
         if (entity != null) {
            return LuaValue.varargsOf(
               new LuaValue[]{LuaValue.valueOf(entity.lastTickPosX), LuaValue.valueOf(entity.lastTickPosY), LuaValue.valueOf(entity.lastTickPosZ)}
            );
         }
      }

      return NIL;
   }
}
