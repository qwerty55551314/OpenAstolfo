package astolfo.command;

import astolfo.Astolfo;
import astolfo.ClientAccess;
import astolfo.module.exploits.Denicker;
import net.minecraft.util.EnumChatFormatting;

public final class HelpCommand extends Command {
   public HelpCommand(String string, String string2) {
      super(string, string2);
   }

   @Override
   public void runCommand(String[] strings) {
      Denicker.nicks.clear();
      Astolfo astolfo = ClientAccess.client;

      for (CommandInfo commandinfo : astolfo.getCommandManager().getList()) {
         if (commandinfo instanceof Command) {
            ClientAccess.chat
               .createBuilder(false, false)
               .append(".")
               .append(commandinfo.getName())
               .color(EnumChatFormatting.DARK_PURPLE)
               .append(" - ")
               .color(EnumChatFormatting.WHITE)
               .append(commandinfo.getUsage())
               .build()
               .send();
         }
      }
   }
}
