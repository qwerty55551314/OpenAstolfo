package astolfo.lua.player;

import astolfo.ClientAccess;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public final class HeldItem extends ZeroArgFunction {
   @Override
   public LuaValue call() {
      EntityPlayerSP entityplayersp = ClientAccess.mc.thePlayer;
      if (entityplayersp != null) {
         ItemStack itemstack = ClientAccess.mc.thePlayer.getCurrentEquippedItem();
         if (itemstack != null) {
            return valueOf(itemstack.getDisplayName());
         }
      }

      return NIL;
   }
}
