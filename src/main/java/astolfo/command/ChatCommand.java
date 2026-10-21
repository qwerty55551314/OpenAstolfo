package astolfo.command;

import astolfo.ClientAccess;
import astolfo.chat.ChatManager;

public class ChatCommand extends Command {
   public ChatCommand(String string, String string2) {
      super(string, string2);
   }

   @Override
   public void runCommand(String[] strings) {
      int i = strings.length;
      switch (i) {
         case 2:
            boolean flag = strings[1].equalsIgnoreCase("enabled");
            if (flag) {
               ChatManager.setChatEnabled(!ChatManager.isChatEnabled());
               ClientAccess.chat.print("Astolfo Chat has been set to " + ChatManager.isChatEnabled());
            } else {
               boolean flag1 = strings[1].equals("pings");
               if (flag1) {
                  boolean flag2 = ChatManager.isPingsEnabled();
                  ChatManager.setPingsEnabled(!flag2);
                  ClientAccess.chat.print("Astolfo Chat pings has been set to " + ChatManager.isPingsEnabled());
               }
            }
            break;
         default:
            ClientAccess.chat.print(this.getName() + ": " + this.getUsage());
      }
   }
}
