package astolfo.command;

import astolfo.ClientAccess;
import astolfo.hypixel.FinalKillTracker;
import astolfo.module.hidden.ScoreboardTracker;
import java.util.Collections;
import java.util.Map.Entry;
import net.minecraft.util.EnumChatFormatting;

public class FinalsCommand extends Command {
   public FinalsCommand(String string, String string2) {
      super(string, string2);
      this.setAliases(Collections.singletonList("final"));
   }

   @Override
   public void runCommand(String[] strings) {
      boolean flag = ScoreboardTracker.isInGame();
      if (!flag) {
         ClientAccess.toasts.danger("You must be in a Mega Walls game for this");
      } else {
         int i = strings.length;
         if (i < 2) {
            this.printTeamFinals(EnumChatFormatting.RED, "Red", String.valueOf(FinalKillTracker.getTeamFinals(0)));
            this.printTeamFinals(EnumChatFormatting.GREEN, "Green", String.valueOf(FinalKillTracker.getTeamFinals(1)));
            this.printTeamFinals(EnumChatFormatting.YELLOW, "Yellow", String.valueOf(FinalKillTracker.getTeamFinals(2)));
            this.printTeamFinals(EnumChatFormatting.BLUE, "Blue", String.valueOf(FinalKillTracker.getTeamFinals(3)));
         } else if (strings.length == 2) {
         }
      }
   }

   private void U(EnumChatFormatting enumChatFormatting) {
      for (Entry entry : FinalKillTracker.playerFinals.entrySet()) {
         if (((String)entry.getKey()).startsWith(enumChatFormatting.toString())) {
            this.printTeamFinals(enumChatFormatting, (String)entry.getKey(), String.valueOf(entry.getValue()));
         }
      }
   }

   private void printTeamFinals(EnumChatFormatting enumChatFormatting, String string, String string2) {
      ClientAccess.chat
         .createBuilder(false, false)
         .append(".")
         .append(string)
         .color(enumChatFormatting)
         .append(" - ")
         .append(String.valueOf(string2))
         .build()
         .send();
   }
}
