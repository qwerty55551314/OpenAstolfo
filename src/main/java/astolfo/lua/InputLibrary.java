package astolfo.lua;

import astolfo.ClientAccess;
import astolfo.lua.input.GetKeyName;
import astolfo.lua.input.GetKeyNumber;
import astolfo.lua.input.GetScroll;
import astolfo.lua.input.IsKeyDown;
import astolfo.lua.input.IsMouseDown;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

public class InputLibrary extends TwoArgFunction implements ClientAccess {
   public static final OneArgFunction isKeyDownFunction = new IsKeyDown();
   public static final OneArgFunction getKeyNameFunction = new GetKeyName();
   public static final OneArgFunction getKeyNumberFunction = new GetKeyNumber();
   public static final ZeroArgFunction getScrollFunction = new GetScroll();
   public static final OneArgFunction isMouseDownFunction = new IsMouseDown();
   private static String E;

   @Override
   public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
      LuaTable luatable = LuaValue.tableOf();
      luatable.set("is_key_down", isKeyDownFunction);
      luatable.set("is_mouse_down", isMouseDownFunction);
      luatable.set("get_key_name", getKeyNameFunction);
      luatable.set("get_key_number", getKeyNumberFunction);
      luatable.set("get_scroll", getScrollFunction);
      luaValue2.set("input", luatable);
      return luatable;
   }
}
