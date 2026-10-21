package astolfo.loader;

import astolfo.loader.check.GameJarHashList;
import astolfo.loader.check.StringListHolder;

public class IntegrityCheckHolder {
   private final GameJarHashList gameJarHashList;
   private final StringListHolder stringListHolder;
   private final IntegrityCheck integrityCheck;
   private static int C;

   public IntegrityCheckHolder() {
      boolean flag = false;
      this.gameJarHashList = new GameJarHashList();
      this.stringListHolder = new StringListHolder(this.gameJarHashList.getHashContainer());
      this.integrityCheck = new IntegrityCheck(this.stringListHolder);
   }

   public IntegrityCheck getIntegrityCheck() {
      return this.integrityCheck;
   }

   public static void m(int i) {
      C = i;
   }

   public static int G() {
      return C;
   }

   public static int O() {
      return 0;
   }

   static {
      m(41);
   }
}
