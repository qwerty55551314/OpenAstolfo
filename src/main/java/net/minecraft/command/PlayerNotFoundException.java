package net.minecraft.command;

public class PlayerNotFoundException extends CommandException {
   private static final String b = "commands.generic.player.notFound";

   public PlayerNotFoundException() {
      this(b);
   }

   public PlayerNotFoundException(String message, Object... replacements) {
      super(message, replacements);
   }
}
