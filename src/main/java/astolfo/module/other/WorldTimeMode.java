package astolfo.module.other;

public enum WorldTimeMode {
   DAY(1000),
   NOON(6000),
   SUNSET(12000),
   NIGHT(13000),
   MIDNIGHT(18000),
   SUNRISE(23000),
   LOOP(0);

   private long time;
   private static final WorldTimeMode[] VALUES = new WorldTimeMode[]{DAY, NOON, SUNSET, NIGHT, MIDNIGHT, SUNRISE, LOOP};

   private WorldTimeMode(int i2) {
      this.time = (long)i2;
   }

   public static long getTime(WorldTimeMode worldTimeMode) {
      return worldTimeMode.time;
   }
}
