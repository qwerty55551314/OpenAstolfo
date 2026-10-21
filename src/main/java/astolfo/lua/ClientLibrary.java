package astolfo.lua;

import astolfo.ClientAccess;
import astolfo.lua.client.ChestName;
import astolfo.lua.client.Config;
import astolfo.lua.client.Fps;
import astolfo.lua.client.GuiName;
import astolfo.lua.client.Print;
import astolfo.lua.client.Subtitle;
import astolfo.lua.client.Time;
import astolfo.lua.client.Title;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

public class ClientLibrary extends TwoArgFunction implements ClientAccess {
   public static final ZeroArgFunction guiNameFunction = new GuiName();
   public static final ZeroArgFunction chestNameFunction = new ChestName();
   public static final ZeroArgFunction timeFunction = new Time();
   public static final ZeroArgFunction configFunction = new Config();
   public static final ZeroArgFunction fpsFunction = new Fps();
   public static final ZeroArgFunction titleFunction = new Title();
   public static final ZeroArgFunction subtitleFunction = new Subtitle();
   public static final OneArgFunction printFunction = new Print();
   private static String z;

   @Override
   public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
      LuaTable luatable = LuaValue.tableOf();
      luatable.set("gui_name", guiNameFunction);
      luatable.set("chest_name", chestNameFunction);
      luatable.set("time", timeFunction);
      luatable.set("config", configFunction);
      luatable.set("print", printFunction);
      luatable.set("title", titleFunction);
      luatable.set("subtitle", subtitleFunction);
      luatable.set("fps", fpsFunction);
      luaValue2.set("client", luatable);
      return luatable;
   }
}
