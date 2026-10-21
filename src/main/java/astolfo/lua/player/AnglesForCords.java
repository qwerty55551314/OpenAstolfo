package astolfo.lua.player;

import astolfo.ClientAccess;
import astolfo.lua.PlayerLibrary;
import astolfo.util.Rotation;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vector3d;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public final class AnglesForCords extends VarArgFunction {
   @Override
   public Varargs onInvoke(Varargs varargs) {
      if (varargs.narg() == 3) {
         Vector3d vector3d = new Vector3d(
            ClientAccess.mc.thePlayer.posX, ClientAccess.mc.thePlayer.posY + (double)ClientAccess.mc.thePlayer.getEyeHeight(), ClientAccess.mc.thePlayer.posZ
         );
         double d0 = 0.0;
         double d1 = 0.0;
         double d2 = 0.0;
         int i = 1;

         for (int j = varargs.narg(); i <= j; i++) {
            LuaValue luavalue = varargs.checkvalue(i);
            switch (i) {
               case 1:
                  if (luavalue.isnumber()) {
                     d0 = luavalue.checkdouble();
                  }
                  break;
               case 2:
                  if (luavalue.isnumber()) {
                     d1 = luavalue.checkdouble();
                  }
                  break;
               case 3:
                  if (luavalue.isnumber()) {
                     d2 = luavalue.checkdouble();
                  }
            }
         }

         Vector3d vector3d1 = new Vector3d(d0, d1, d2);
         Rotation rotation = PlayerLibrary.getRotator().rotationTo(vector3d1, vector3d);
         float f = MathHelper.wrapAngleTo180_float(rotation.getYaw() - ClientAccess.mc.thePlayer.rotationYaw);
         float f1 = ClientAccess.mc.thePlayer.rotationYaw + f;
         return LuaValue.varargsOf(new LuaValue[]{LuaValue.valueOf((double)f1), LuaValue.valueOf((double)rotation.getPitch())});
      } else {
         return NIL;
      }
   }
}
