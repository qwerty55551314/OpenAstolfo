package astolfo.lua.player;

import astolfo.ClientAccess;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.ServerData;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public final class Ping extends ZeroArgFunction {
   @Override
   public LuaValue call() {
      EntityPlayerSP entityplayersp = ClientAccess.mc.thePlayer;
      if (entityplayersp == null) {
         return valueOf(false);
      } else {
         int l;
         label26: {
            if (ClientAccess.mc.thePlayer.getPlayerInfo() != null) {
               Minecraft minecraft = ClientAccess.mc;
               if (minecraft.thePlayer.getPlayerInfo().getResponseTime() > 1) {
                  l = ClientAccess.mc.thePlayer.getPlayerInfo().getResponseTime();
                  break label26;
               }
            }

            ServerData serverdata = ClientAccess.mc.getCurrentServerData();
            if (serverdata != null) {
               long k;
               int j = (k = ClientAccess.mc.getCurrentServerData().pingToServer - 0L) == 0L ? 0 : (k < 0L ? -1 : 1);
               if (j > 0) {
                  l = (int)ClientAccess.mc.getCurrentServerData().pingToServer;
                  break label26;
               }
            }

            l = 0;
         }

         int i = l;
         return valueOf(Math.min(i, 999));
      }
   }
}
