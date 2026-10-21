package astolfo.module.movement;

public enum SneakMode {
   WATCHDOG("Watchdog"),
   ALWAYS("Always");

   private String displayName;
   private static final SneakMode[] VALUES = new SneakMode[]{WATCHDOG, ALWAYS};

   private SneakMode(String string2) {
      this.displayName = string2;
   }

   public static String displayNameOf(SneakMode sneakMode) {
      return sneakMode.displayName;
   }
}
