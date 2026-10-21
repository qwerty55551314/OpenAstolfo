package astolfo.lua.render;

import astolfo.gui.screen.LuaScreen;
import net.minecraft.client.Minecraft;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public final class ShowGui extends OneArgFunction {
   @Override
   public LuaValue call(LuaValue luaValue) {
      LuaTable luatable = null;
      if (luaValue.isfunction()) {
         LuaValue luavalue = luaValue.call();
         if (luavalue.istable()) {
            luatable = (LuaTable)luavalue.call();
         }
      } else if (luaValue.istable()) {
         luatable = (LuaTable)luaValue;
      }

      if (luatable != null) {
         Minecraft.getMinecraft().displayGuiScreen(new LuaScreen(luatable));
         return valueOf(true);
      } else {
         return valueOf(false);
      }
   }
}
