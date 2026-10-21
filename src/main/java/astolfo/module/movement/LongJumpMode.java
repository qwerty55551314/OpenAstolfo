package astolfo.module.movement;

public enum LongJumpMode {
   NORMAL("Normal"),
   SAFE("Safe"),
   WATCHDOG("Watchdog");

   private String displayName;
   private static final LongJumpMode[] VALUES = new LongJumpMode[]{NORMAL, SAFE, WATCHDOG};

   private LongJumpMode(String string2) {
      this.displayName = string2;
   }
}
