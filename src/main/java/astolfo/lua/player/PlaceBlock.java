package astolfo.lua.player;

import astolfo.ClientAccess;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public final class PlaceBlock extends VarArgFunction {
   @Override
   public Varargs onInvoke(Varargs varargs) {
      if (varargs.narg() < 8) {
         return LuaValue.varargsOf(new LuaValue[]{NIL});
      } else {
         int i = 0;
         int j = 0;
         double d0 = 0.0;
         double d1 = 0.0;
         double d2 = 0.0;
         double d3 = 0.0;
         double d4 = 0.0;
         double d5 = 0.0;
         int k = 1;

         for (int l = varargs.narg(); k <= l; k++) {
            LuaValue luavalue = varargs.checkvalue(k);
            switch (k) {
               case 1:
                  if (luavalue.isnumber()) {
                     i = luavalue.checkint();
                  }
                  break;
               case 2:
                  if (luavalue.isnumber()) {
                     d0 = luavalue.checkdouble();
                  }
                  break;
               case 3:
                  if (luavalue.isnumber()) {
                     d1 = luavalue.checkdouble();
                  }
                  break;
               case 4:
                  if (luavalue.isnumber()) {
                     d2 = luavalue.checkdouble();
                  }
                  break;
               case 5:
                  if (luavalue.isnumber()) {
                     j = luavalue.checkint();
                  }
                  break;
               case 6:
                  if (luavalue.isnumber()) {
                     d3 = (double)luavalue.checkint();
                  }
                  break;
               case 7:
                  if (luavalue.isnumber()) {
                     d4 = (double)luavalue.checkint();
                  }
                  break;
               case 8:
                  if (luavalue.isnumber()) {
                     d5 = (double)luavalue.checkint();
                  }
            }
         }

         boolean flag = ClientAccess.mc
            .playerController
            .onPlayerRightClick(
               ClientAccess.mc.thePlayer,
               ClientAccess.mc.theWorld,
               ClientAccess.mc.thePlayer.inventory.getStackInSlot(i - 1),
               new BlockPos(d0, d1, d2),
               EnumFacing.values()[j - 1],
               new Vec3(d3, d4, d5)
            );
         return valueOf(flag);
      }
   }
}
