package astolfo.loader.check;

import java.util.Comparator;

public abstract class AbstractStageCheck implements StageCheck {
   private static final Comparator<StageCheck> priorityComparator = Comparator.comparing(StageCheck::getPriority);
   private static int[] d;

   @Override
   public final int compareTo(StageCheck stageCheck) {
      return priorityComparator.compare(this, stageCheck);
   }

   static {
      int[] aint = new int[3];
      R(aint);
   }

   public static void R(int[] iArray) {
      d = iArray;
   }

   public static int[] F() {
      return d;
   }
}
