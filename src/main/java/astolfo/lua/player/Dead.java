package astolfo.lua.player;

import astolfo.ClientAccess;
import net.minecraft.client.entity.EntityPlayerSP;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public final class Dead extends ZeroArgFunction {
   @Override
   public LuaValue call() {
      EntityPlayerSP entityplayersp = ClientAccess.mc.thePlayer;
      if (entityplayersp != null) {
         boolean flag = ClientAccess.mc.thePlayer.isEntityAlive();
         return valueOf(!flag);
      } else {
         return valueOf(false);
      }
   }
}
