package astolfo.loader;

import astolfo.ClientAccess;

public abstract class AbstractLoadStage implements LoadStage, ClientAccess {
   public int state;
   private static int[] K;

   public abstract StageAction getAction();

   public static void q(int[] iArray) {
      K = iArray;
   }

   public static int[] v() {
      return K;
   }
}
