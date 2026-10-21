package astolfo.loader.check;

import astolfo.util.container.ListContainerOwner;

public class JvmArgumentsListOwner {
   private final ListContainerOwner<String> patternContainer;

   public JvmArgumentsListOwner() {
      JvmArgumentPatterns jvmargumentpatterns = new JvmArgumentPatterns();
      this.patternContainer = new ListContainerOwner<>(jvmargumentpatterns.getPatterns());
   }

   public ListContainerOwner<String> getPatternContainer() {
      return this.patternContainer;
   }
}
