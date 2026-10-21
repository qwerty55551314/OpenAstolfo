package astolfo.module.other;

public enum InfiniteReachMode {
   LINEAR("Linear"),
   PATH_FIND("Path Find");

   private String label;
   private static final InfiniteReachMode[] VALUES = new InfiniteReachMode[]{LINEAR, PATH_FIND};

   private InfiniteReachMode(String string2) {
      this.label = string2;
   }
}
