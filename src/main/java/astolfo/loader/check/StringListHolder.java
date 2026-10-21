package astolfo.loader.check;

import astolfo.util.container.ListContainerOwner;

public class StringListHolder {
   private final ListContainerOwner<String> stringContainer;
   private static int z;

   public StringListHolder(ListContainerOwner<String> listContainerOwner) {
      this.stringContainer = listContainerOwner;
   }

   public ListContainerOwner<String> getStringContainer() {
      return this.stringContainer;
   }

   public static int M() {
      return 0;
   }
}
