package astolfo.command;

import astolfo.ClientAccess;
import java.util.Collections;

public final class ClearchatCommand extends Command {
   public ClearchatCommand(String string, String string2) {
      super(string, string2);
      this.setAliases(Collections.singletonList("clear"));
   }

   @Override
   public void runCommand(String[] strings) {
      ClientAccess.mc.ingameGUI.getChatGUI().clearChatMessages();
   }
}
