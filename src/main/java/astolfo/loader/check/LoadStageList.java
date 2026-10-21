package astolfo.loader.check;

import astolfo.loader.LoadStage;
import astolfo.util.container.ListContainerOwner;

public class LoadStageList {
   private final ListContainerOwner<LoadStage> stageContainer;

   public LoadStageList(ListContainerOwner<LoadStage> listContainerOwner) {
      this.stageContainer = listContainerOwner;
   }

   public ListContainerOwner<LoadStage> getStageContainer() {
      return this.stageContainer;
   }
}
