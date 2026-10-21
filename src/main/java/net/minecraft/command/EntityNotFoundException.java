package net.minecraft.command;

public class EntityNotFoundException extends CommandException {
   private static final String b = "commands.generic.entity.notFound";

   public EntityNotFoundException() {
      this(b);
   }

   public EntityNotFoundException(String string, Object... objects) {
      super(string, objects);
   }
}
