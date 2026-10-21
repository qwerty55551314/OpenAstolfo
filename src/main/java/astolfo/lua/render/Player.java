package astolfo.lua.render;

import astolfo.lua.WorldLibrary;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public final class Player extends VarArgFunction {
   @Override
   public Varargs invoke(Varargs varargs) {
      int i = 0;
      int j = 0;
      int k = 8;
      int l = -1;
      float f = 0.0F;
      float f1 = 0.0F;
      int i1 = 1;
      int k1 = varargs.narg();
      if (k1 < 6) {
         return valueOf(false);
      } else {
         for (int j1 = varargs.narg(); i1 <= j1; i1++) {
            LuaValue luavalue = varargs.checkvalue(i1);
            switch (i1) {
               case 1:
                  if (luavalue.isnumber()) {
                     i = luavalue.checkint();
                  }
                  break;
               case 2:
                  if (luavalue.isnumber()) {
                     j = luavalue.checkint();
                  }
                  break;
               case 3:
                  if (luavalue.isnumber()) {
                     k = luavalue.checkint();
                  }
                  break;
               case 4:
                  if (luavalue.isnumber()) {
                     f = luavalue.tofloat();
                  }
                  break;
               case 5:
                  if (luavalue.isnumber()) {
                     f1 = luavalue.tofloat();
                  }
                  break;
               case 6:
                  if (luavalue.isnumber()) {
                     l = luavalue.checkint();
                  }
            }
         }

         Entity entity = WorldLibrary.entitiesById.get(l);
         boolean flag = entity instanceof EntityLivingBase;
         if (flag) {
            GuiInventory.drawEntityWithRotation(i, j, f, f1, k, (EntityLivingBase)entity);
         }

         return valueOf(false);
      }
   }
}
