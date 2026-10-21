package astolfo.loader.check;

import astolfo.loader.LoadStage;
import java.util.List;
import java.util.Objects;

public interface CheckRequest {
   LoadStage getStage();

   CheckContext createContext(List<StageCheck> var1, List<VerdictProcessor> var2);

   static CheckRequest forStage(LoadStage loadStage) {
      Objects.requireNonNull(loadStage, "");
      return new StageCheckRequest(loadStage);
   }
}
