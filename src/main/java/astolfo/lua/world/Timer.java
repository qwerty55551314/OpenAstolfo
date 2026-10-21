package astolfo.lua.world;

import astolfo.Astolfo;
import astolfo.ClientAccess;
import astolfo.module.ModuleManager;
import astolfo.module.player.Scaffold;
import astolfo.util.GameUtil;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public final class Timer extends ZeroArgFunction {
   @Override
   public LuaValue call() {
      Astolfo astolfo = ClientAccess.client;
      ModuleManager modulemanager1 = astolfo.getModuleManager();
      Class<Scaffold> oclass = Scaffold.class;
      ModuleManager modulemanager = modulemanager1;
      boolean flag = modulemanager.get(oclass).isEnabled();
      if (flag) {
         boolean flag1 = GameUtil.isOnHypixel();
         if (flag1) {
            boolean flag2 = Scaffold.keepSprint.getValue();
            if (flag2 && ClientAccess.mc.thePlayer.onGround) {
               return valueOf(1.0);
            }
         }
      }

      return valueOf((double)ClientAccess.mc.timer.timerSpeed);
   }
}
