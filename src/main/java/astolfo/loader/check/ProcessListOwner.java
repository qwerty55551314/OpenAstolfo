package astolfo.loader.check;

import astolfo.util.container.ListContainerOwner;

public class ProcessListOwner {
   private final ListContainerOwner<String> processContainer;

   public ProcessListOwner() {
      ProcessNames processnames = new ProcessNames();
      this.processContainer = new ListContainerOwner<>(processnames.getProcessNames());
   }

   public ListContainerOwner<String> getProcessContainer() {
      return this.processContainer;
   }
}
