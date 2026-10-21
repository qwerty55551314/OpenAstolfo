package astolfo.loader.check;

import astolfo.loader.LoadStage;
import astolfo.loader.LoadStages;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoadStagesList {
   private final List<LoadStage> stages = new ArrayList<>(
      Arrays.asList(LoadStages.createDecoyStage1(), LoadStages.createDirectoryStage(), LoadStages.createManagersStage(), LoadStages.createModulesStage())
   );

   public LoadStagesList() {
      if (false) {
         int i = 0;
         i++;
      }
   }

   public List<LoadStage> getStages() {
      return this.stages;
   }
}
