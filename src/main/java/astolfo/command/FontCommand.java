package astolfo.command;

import astolfo.ClientAccess;
import astolfo.font.FontManager;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

public class FontCommand extends Command {
   public FontCommand(String string, String string2) {
      super(string, string2);
   }

   @Override
   public void runCommand(String[] strings) {
      if (strings.length >= 2) {
         String s = StringUtils.join(Arrays.copyOfRange(strings, 1, strings.length), " ");
         FontManager.applyFont(s);
         ClientAccess.chat.print("Font has been set to " + s);
      }
   }
}
