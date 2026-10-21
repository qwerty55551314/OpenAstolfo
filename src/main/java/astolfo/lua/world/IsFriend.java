package astolfo.lua.world;

import astolfo.Astolfo;
import astolfo.ClientAccess;
import astolfo.lua.WorldLibrary;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public final class IsFriend extends OneArgFunction {
   @Override
   public LuaValue call(LuaValue luaValue) {
      if (!luaValue.isnil() && luaValue.isnumber() && luaValue.isint()) {
         Entity entity = WorldLibrary.entitiesById.get(luaValue.checkint());
         if (entity instanceof EntityLivingBase) {
            Astolfo astolfo = ClientAccess.client;
            return valueOf(astolfo.getRelationManager().isFriend(entity));
         }
      }

      return valueOf(false);
   }
}
