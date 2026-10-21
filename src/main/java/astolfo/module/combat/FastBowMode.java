package astolfo.module.combat;

public enum FastBowMode {
   VANILLA("Vanilla");

   private String displayName;
   private static final FastBowMode[] VALUES = new FastBowMode[]{VANILLA};

   private FastBowMode(String string2) {
      this.displayName = string2;
   }

   public static String displayNameOf(FastBowMode fastBowMode) {
      return fastBowMode.displayName;
   }
}
