package astolfo.loader.check;

import astolfo.util.container.ListContainerOwner;

public class JvmArgumentsPropertyListOwner {
   private final ListContainerOwner<String> patternContainer;

   public JvmArgumentsPropertyListOwner() {
      JvmArgumentPropertyPatterns jvmargumentpropertypatterns = new JvmArgumentPropertyPatterns();
      this.patternContainer = new ListContainerOwner<>(jvmargumentpropertypatterns.getPatterns());
   }

   public ListContainerOwner<String> getPatternContainer() {
      return this.patternContainer;
   }
}
