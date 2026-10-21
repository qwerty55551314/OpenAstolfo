package astolfo.event;

public enum EventPriority {
   HIGHEST(4),
   HIGH(3),
   NORMAL(2),
   LOW(1),
   LOWEST(0);

   public int level;

   private EventPriority(int i2) {
      this.level = i2;
   }

   public int getLevel() {
      return this.level;
   }
}
