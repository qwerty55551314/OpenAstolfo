package astolfo.lua.player;

import astolfo.ClientAccess;
import net.minecraft.client.Minecraft;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public final class UseItem extends ZeroArgFunction {
   @Override
   public LuaValue call() {
      Minecraft minecraft = ClientAccess.mc;
      if (minecraft.thePlayer != null) {
         Minecraft minecraft1 = ClientAccess.mc;
         if (minecraft1.theWorld != null) {
            Minecraft minecraft2 = ClientAccess.mc;
            if (minecraft2.thePlayer.getCurrentEquippedItem() != null) {
               ClientAccess.mc
                  .playerController
                  .sendUseItem(ClientAccess.mc.thePlayer, ClientAccess.mc.theWorld, ClientAccess.mc.thePlayer.getCurrentEquippedItem());
               return valueOf(true);
            }
         }
      }

      return valueOf(false);
   }
}
