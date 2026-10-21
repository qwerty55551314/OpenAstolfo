package astolfo.command;

import astolfo.ClientAccess;
import astolfo.auth.DisconnectHandler;
import astolfo.util.Stopwatch;

public class BansCommand extends Command {
   public static int hours;
   private final Stopwatch cooldown = new Stopwatch();

   public BansCommand(String string, String string2) {
      super(string, string2);
   }

   @Override
   public void runCommand(String[] strings) {
      boolean flag = DisconnectHandler.disconnected;
      if (flag) {
         ClientAccess.chat.printError("You are disconnected");
      } else {
         int j = strings.length;
         if (j == 3) {
            boolean flag1 = this.cooldown.hasElapsed(5000.0F);
            if (!flag1) {
               ClientAccess.chat.print("Wait " + Math.round(5.0F - (float)this.cooldown.getElapsed() / 1000.0F) + "s before using this command again");
               return;
            }

            int i = getModeId(strings[1].toLowerCase());
            boolean flag2 = strings[1].equalsIgnoreCase("all");
            if (!flag2 && i == 0) {
               ClientAccess.chat.print("Gamemode is invalid, defaulting to all");
            }

            try {
               hours = Integer.parseInt(strings[2]);
               if (hours <= 0 || hours > 12) {
                  ClientAccess.chat.printError("Invalid hour");
                  return;
               }

               this.cooldown.reset();
            } catch (NumberFormatException numberformatexception) {
               ClientAccess.chat.printError("Invalid hour");
            }
         } else {
            ClientAccess.chat.print(this.getName() + ": " + this.getUsage());
         }
      }
   }

   public static int getModeId(String string) {
      int b0 = -1;
      int i = string.hashCode();
      switch (i) {
         case 3157:
            if (string.equals("bw")) {
               b0 = 1;
            }
            break;
         case 3498:
            if (string.equals("mw")) {
               b0 = 3;
            }
            break;
         case 3684:
            if (string.equals("sw")) {
               b0 = 0;
            }
            break;
         case 111003:
            if (string.equals("pit")) {
               b0 = 4;
            }
            break;
         case 115760:
            if (string.equals("uhc")) {
               b0 = 2;
            }
      }

      switch (b0) {
         case 0:
            return 1;
         case 1:
            return 2;
         case 2:
            return 3;
         case 3:
            return 4;
         case 4:
            return 5;
         default:
            return 0;
      }
   }

   public static String getModeName(int i) {
      switch (i) {
         case 1:
            return "sw";
         case 2:
            return "bw";
         case 3:
            return "uhc";
         case 4:
            return "mw";
         case 5:
            return "pit";
         default:
            return "all";
      }
   }
}
