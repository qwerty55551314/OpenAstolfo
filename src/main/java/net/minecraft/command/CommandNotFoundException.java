package net.minecraft.command;

public class CommandNotFoundException extends CommandException {
   private static final String b = "commands.generic.notFound";

   public CommandNotFoundException() {
      this(b);
   }

   public CommandNotFoundException(String string, Object... objects) {
      super(string, objects);
   }
}
