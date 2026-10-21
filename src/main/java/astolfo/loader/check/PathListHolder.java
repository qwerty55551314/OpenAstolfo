package astolfo.loader.check;

import astolfo.util.container.ListContainerOwner;
import java.nio.file.Path;

public class PathListHolder {
   private final ListContainerOwner<Path> pathContainer;
   private static String[] C;

   public PathListHolder(ListContainerOwner<Path> listContainerOwner) {
      this.pathContainer = listContainerOwner;
   }

   public ListContainerOwner<Path> getPathContainer() {
      return this.pathContainer;
   }

   public static void K(String[] stringArray) {
      C = stringArray;
   }

   public static String[] M() {
      return C;
   }

   static {
      K(new String[1]);
   }
}
