package astolfo.lua.world;

import astolfo.lua.WorldLibrary;
import astolfo.module.hidden.HypixelGameTracker;
import astolfo.module.hidden.PlayerPotionEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public final class IsPotionActive extends TwoArgFunction {
   @Override
   public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
      if (!luaValue.isnil() && luaValue.isnumber() && luaValue.isint() && !luaValue2.isnil() && luaValue2.isnumber() && luaValue2.isint()) {
         Entity entity = WorldLibrary.entitiesById.get(luaValue.checkint());
         if (entity instanceof EntityPlayer) {
            PlayerPotionEffects playerpotioneffects = HypixelGameTracker.playerEffects.get((EntityPlayer)entity);
            if (playerpotioneffects != null) {
               return valueOf(playerpotioneffects.effects.get(luaValue2.checkint()) != null);
            }
         }
      }

      return NIL;
   }
}
