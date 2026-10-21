package astolfo.command;

import astolfo.manager.Loadable;
import astolfo.manager.Manager;
import java.util.Iterator;
import java.util.List;

public class CommandManager extends Manager<CommandInfo> implements Loadable {
   private static String i;

   @Override
   public void load() {
   }

   @Override
   public void save() {
   }

   public CommandInfo getCommand(String string) {
      Iterator iterator = super.items.iterator();

      CommandInfo commandinfo;
      while (true) {
         if (!iterator.hasNext()) {
            return null;
         }

         commandinfo = (CommandInfo)iterator.next();
         boolean flag = commandinfo instanceof Command;
         if (flag) {
            if (commandinfo.getName().equalsIgnoreCase(string)) {
               break;
            }

            List list = ((Command)commandinfo).getAliases();
            if (list != null && ((Command)commandinfo).getAliases().contains(string)) {
               break;
            }
         }
      }

      return commandinfo;
   }
}
