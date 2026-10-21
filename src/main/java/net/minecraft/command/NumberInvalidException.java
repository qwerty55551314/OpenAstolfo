package net.minecraft.command;

public class NumberInvalidException extends CommandException {
   private static final String b = "commands.generic.num.invalid";

   public NumberInvalidException() {
      this(b);
   }

   public NumberInvalidException(String message, Object... replacements) {
      super(message, replacements);
   }
}
