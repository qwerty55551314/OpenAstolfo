package astolfo.command;

import astolfo.ClientAccess;
import astolfo.module.visuals.Waypoints;
import java.util.Arrays;

public final class WaypointsCommand extends Command {
   public WaypointsCommand(String string, String string2) {
      super(string, string2);
      this.setAliases(Arrays.asList("wp", "waypoint"));
   }

   @Override
   public void runCommand(String[] strings) {
      try {
         if (strings.length > 1) {
            if (strings[1].equalsIgnoreCase("add")) {
               switch (strings.length) {
                  case 3:
                     Waypoints.addWaypoint(strings[2], ClientAccess.mc.thePlayer.posX, ClientAccess.mc.thePlayer.posY, ClientAccess.mc.thePlayer.posZ);
                     ClientAccess.chat
                        .printSuccess(
                           String.format(
                              "Successfully added %s as waypoint (%s, %s, %s)",
                              strings[2],
                              (int)ClientAccess.mc.thePlayer.posX,
                              (int)ClientAccess.mc.thePlayer.posY,
                              (int)ClientAccess.mc.thePlayer.posZ
                           )
                        );
                     break;
                  case 6:
                     double d0 = Double.parseDouble(strings[3]);
                     double d1 = Double.parseDouble(strings[4]);
                     double d2 = Double.parseDouble(strings[5]);
                     Waypoints.addWaypoint(strings[2], d0, d1, d2);
                     ClientAccess.chat.printSuccess(String.format("Successfully added %s as waypoint (%s, %s, %s)", strings[2], (int)d0, (int)d1, (int)d2));
               }
            }

            if (strings[1].equalsIgnoreCase("remove")) {
               switch (strings.length) {
                  case 3:
                     ClientAccess.chat.printSuccess(String.format("Successfully removed %s waypoint", strings[2]));
                     Waypoints.removeWaypoint(strings[2]);
               }
            }
         }

         if (strings.length == 1) {
            ClientAccess.chat.print(String.format("%s:%s", this.getName(), this.getUsage()));
         }
      } catch (Exception exception) {
         ClientAccess.chat.print(String.format("%s:%s", this.getName(), this.getUsage()));
      }
   }
}
