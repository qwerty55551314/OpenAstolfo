package astolfo.command;

import java.util.List;

public abstract class AbstractCommand implements CommandInfo {
   private String name;
   private String usage;
   private List<String> aliases;
   private static String[] b;

   public AbstractCommand(String string, String string2) {
      this.name = string;
      String[] astring = new String[0];
      this.usage = string2;
   }

   public abstract void runCommand(String[] var1);

   public void setAliases(List<String> list) {
      this.aliases = list;
   }

   public List<String> getAliases() {
      return this.aliases;
   }

   @Override
   public String getName() {
      return this.name;
   }

   @Override
   public String getUsage() {
      return this.usage;
   }
}
