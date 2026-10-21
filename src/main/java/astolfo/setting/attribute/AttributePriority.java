package astolfo.setting.attribute;

public enum AttributePriority {
   HIGHEST(0),
   HIGH(1),
   NORMAL(2),
   LOW(3),
   LOWEST(4);

   public int level;

   private AttributePriority(int i2) {
      this.level = i2;
   }

   public int getLevel() {
      return this.level;
   }
}
