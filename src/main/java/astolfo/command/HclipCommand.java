package astolfo.command;

import astolfo.ClientAccess;
import java.util.Collections;

public final class HclipCommand extends Command {
   public HclipCommand(String string, String string2) {
      super(string, string2);
      this.setAliases(Collections.singletonList("hc"));
   }

   @Override
   public void runCommand(String[] strings) {
      if (strings.length > 1) {
         double d0;
         try {
            d0 = Double.parseDouble(strings[1]);
         } catch (NumberFormatException numberformatexception) {
            ClientAccess.chat.printWarning("\"" + strings[1] + "\" is not a number.");
            return;
         }

         double d1 = Math.cos(Math.toRadians((double)(ClientAccess.mc.thePlayer.rotationYaw + 90.0F)));
         double d2 = Math.sin(Math.toRadians((double)(ClientAccess.mc.thePlayer.rotationYaw + 90.0F)));
         double d3 = 1.0 * d0 * d1 + 0.0 * d0 * d2;
         double d4 = 1.0 * d0 * d2 - 0.0 * d0 * d1;
         ClientAccess.mc.thePlayer.setPosition(ClientAccess.mc.thePlayer.posX + d3, ClientAccess.mc.thePlayer.posY, ClientAccess.mc.thePlayer.posZ + d4);
      } else {
         ClientAccess.chat.print("Hclip: " + this.getUsage());
      }
   }
}
