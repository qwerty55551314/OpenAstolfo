package astolfo.command;

import astolfo.Astolfo;
import astolfo.ClientAccess;
import astolfo.module.Module;
import astolfo.module.ModuleInfo;
import astolfo.module.ModuleManager;
import astolfo.module.hidden.GUI;
import java.util.Arrays;
import org.lwjgl.input.Keyboard;

public final class BindCommand extends Command {
   public BindCommand(String string, String string2) {
      super(string, string2);
      this.setAliases(Arrays.asList("b", "key"));
   }

   @Override
   public void runCommand(String[] strings) {
      int i = strings.length;
      if (i == 4 && strings[1].equalsIgnoreCase("add")) {
         Astolfo astolfo = ClientAccess.client;
         ModuleManager modulemanager2 = astolfo.getModuleManager();
         String s = strings[2];
         ModuleManager modulemanager = modulemanager2;
         Module module1 = modulemanager.getModule(s);
         if (module1 == null) {
            ClientAccess.chat.printWarning(String.format("Module %s not found.", strings[2]));
            return;
         }

         int k = Keyboard.getKeyIndex(strings[3].toUpperCase());
         module1.setKey(k);
         ClientAccess.chat.print(String.format("%s was bound to %s", module1.getName(), Keyboard.getKeyName(module1.getKey())));
      } else {
         int j = strings.length;
         if (j == 3 && strings[1].equalsIgnoreCase("del")) {
            Astolfo astolfo1 = ClientAccess.client;
            ModuleManager modulemanager3 = astolfo1.getModuleManager();
            String s1 = strings[2];
            ModuleManager modulemanager1 = modulemanager3;
            Module module = modulemanager1.getModule(s1);
            if (module == null) {
               ClientAccess.chat.printWarning(String.format("Module %s not found.", strings[2]));
               return;
            }

            module.setKey(0);
            ClientAccess.chat.print(String.format("%s keybind was deleted.", module.getName()));
         } else if (strings.length == 2 && strings[1].equalsIgnoreCase("clear")) {
            Astolfo astolfo2 = ClientAccess.client;

            for (ModuleInfo moduleinfo : astolfo2.getModuleManager().getList()) {
               if (moduleinfo instanceof Module && !(moduleinfo instanceof GUI)) {
                  ((Module)moduleinfo).setKey(0);
               }
            }

            ClientAccess.chat.print("Cleared all module keybinds");
         } else {
            ClientAccess.chat.print(this.getName() + ": " + this.getUsage());
         }
      }
   }
}
