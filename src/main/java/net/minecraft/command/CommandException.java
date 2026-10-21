package net.minecraft.command;

public class CommandException extends Exception {
   private final Object[] errorObjects;
   private static boolean f;

   public CommandException(String message, Object... objects) {
      super(message);
      this.errorObjects = objects;
   }

   public Object[] getErrorObjects() {
      return this.errorObjects;
   }

   public static void Y(boolean flag) {
      f = flag;
   }

   public static boolean L() {
      return f;
   }

   public static boolean g() {
      return true;
   }
}
