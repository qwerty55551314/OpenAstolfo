package astolfo.lua.world;

import astolfo.lua.WorldLibrary;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public final class Inventory extends VarArgFunction {
   @Override
   public Varargs invoke(Varargs varargs) {
      if (varargs.arg1() != null && !varargs.arg1().isnil() && varargs.arg1().isnumber()) {
         Entity entity = WorldLibrary.entitiesById.get(varargs.arg1().checkint());
         if (entity != null && entity instanceof EntityLivingBase) {
            EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
            LuaTable luatable = new LuaTable();
            ItemStack itemstack = entitylivingbase.getHeldItem();
            luatable.set("hand", itemstack == null ? "none" : itemstack.getUnlocalizedName());
            ItemStack[] aitemstack = entitylivingbase.getInventory();
            if (aitemstack != null) {
               int i = 0;

               for (int j = aitemstack.length; i < j; i++) {
                  ItemStack itemstack1 = aitemstack[i];
                  if (itemstack1 != null) {
                     int k = EntityLiving.getArmorPosition(itemstack1);
                     switch (k) {
                        case 1:
                           luatable.set("boots", itemstack1.getUnlocalizedName());
                           break;
                        case 2:
                           luatable.set("leggings", itemstack1.getUnlocalizedName());
                           break;
                        case 3:
                           luatable.set("chestplate", itemstack1.getUnlocalizedName());
                           break;
                        case 4:
                           luatable.set("helmet", itemstack1.getUnlocalizedName());
                     }
                  }
               }

               return LuaValue.varargsOf(new LuaValue[]{luatable});
            }
         }
      }

      return NIL;
   }
}
