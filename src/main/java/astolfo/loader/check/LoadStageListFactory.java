package astolfo.loader.check;

import astolfo.loader.LoadStage;
import astolfo.util.container.ListContainerOwner;

public class LoadStageListFactory {
   private final ListContainerOwner<LoadStage> stageContainer;

   public LoadStageListFactory() {
      LoadStagesList loadstageslist = new LoadStagesList();
      this.stageContainer = new ListContainerOwner<>(loadstageslist.getStages());
   }

   public ListContainerOwner<LoadStage> getStageContainer() {
      return this.stageContainer;
   }
}
