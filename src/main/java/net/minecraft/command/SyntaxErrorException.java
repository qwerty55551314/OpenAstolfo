package net.minecraft.command;

public class SyntaxErrorException extends CommandException {
   private static final String b = "commands.generic.snytax";

   public SyntaxErrorException() {
      this(b);
   }

   public SyntaxErrorException(String message, Object... replacements) {
      super(message, replacements);
   }
}
