package astolfo.module.movement;

public enum DoubleJumpMode {
   VANILLA("Vanilla"),
   NCP("NCP");

   private String displayName;
   private static final DoubleJumpMode[] VALUES = new DoubleJumpMode[]{VANILLA, NCP};

   private DoubleJumpMode(String string2) {
      this.displayName = string2;
   }

   public static String displayNameOf(DoubleJumpMode doubleJumpMode) {
      return doubleJumpMode.displayName;
   }
}
