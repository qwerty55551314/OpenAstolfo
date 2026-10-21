package astolfo.lua.player;

import astolfo.util.GameUtil;
import net.minecraft.util.MovingObjectPosition;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public final class RayCast extends VarArgFunction {
   @Override
   public Varargs onInvoke(Varargs varargs) {
      if (varargs.narg() < 2) {
         return LuaValue.varargsOf(new LuaValue[]{NIL});
      } else {
         float f = 0.0F;
         float f1 = 0.0F;
         double d0 = 4.5;
         int i = 1;

         for (int j = varargs.narg(); i <= j; i++) {
            LuaValue luavalue = varargs.checkvalue(i);
            switch (i) {
               case 1:
                  if (luavalue.isnumber()) {
                     f = luavalue.tofloat();
                  }
                  break;
               case 2:
                  if (luavalue.isnumber()) {
                     f1 = luavalue.tofloat();
                  }
                  break;
               case 3:
                  if (luavalue.isnumber()) {
                     d0 = luavalue.checkdouble();
                  }
            }
         }

         MovingObjectPosition movingobjectposition = GameUtil.rayTrace(f, f1, d0);
         if (movingobjectposition != null) {
            switch (MovingObjectTypeSwitch.$SwitchMap$net$minecraft$util$MovingObjectPosition$MovingObjectType[movingobjectposition.typeOfHit.ordinal()]) {
               case 1:
                  return LuaValue.varargsOf(
                     new LuaValue[]{valueOf(movingobjectposition.typeOfHit.ordinal() + 1), valueOf(movingobjectposition.entityHit.getEntityId())}
                  );
               case 2:
                  return LuaValue.varargsOf(new LuaValue[]{valueOf(movingobjectposition.typeOfHit.ordinal() + 1)});
               case 3:
                  return LuaValue.varargsOf(
                     new LuaValue[]{
                        valueOf(movingobjectposition.typeOfHit.ordinal() + 1),
                        valueOf(movingobjectposition.sideHit.ordinal() + 1),
                        valueOf(movingobjectposition.getBlockPos().getX()),
                        valueOf(movingobjectposition.getBlockPos().getY()),
                        valueOf(movingobjectposition.getBlockPos().getZ()),
                        valueOf(movingobjectposition.hitVec.xCoord),
                        valueOf(movingobjectposition.hitVec.yCoord),
                        valueOf(movingobjectposition.hitVec.zCoord)
                     }
                  );
               default:
                  return LuaValue.varargsOf(new LuaValue[]{valueOf(movingobjectposition.typeOfHit.ordinal() + 1)});
            }
         } else {
            return LuaValue.varargsOf(new LuaValue[]{NIL});
         }
      }
   }
}
