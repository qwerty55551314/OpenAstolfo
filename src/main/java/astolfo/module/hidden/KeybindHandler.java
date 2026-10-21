package astolfo.module.hidden;

import astolfo.Astolfo;
import astolfo.ClientAccess;
import astolfo.event.EventHandler;
import astolfo.event.Listener;
import astolfo.event.game.KeyPressEvent;
import astolfo.module.HiddenModule;
import astolfo.module.Module;
import astolfo.module.ModuleInfo;
import java.util.List;

public final class KeybindHandler extends HiddenModule {
   @EventHandler(KeyPressEvent.class)
   private Listener<KeyPressEvent> onKeyPress = keyPressEvent -> {
      Astolfo astolfo = ClientAccess.client;
      List list = astolfo.getModuleManager().getList();
      int i = 0;

      for (int j = list.size(); i < j; i++) {
         ModuleInfo moduleinfo = (ModuleInfo)list.get(i);
         if (moduleinfo instanceof Module) {
            Module module = (Module)moduleinfo;
            if (module.getKey() == keyPressEvent.getKey()) {
               module.toggle();
            }
         }
      }
   };

   public KeybindHandler() {
      ClientAccess.client.getEventBus().subscribe(this);
   }
}
