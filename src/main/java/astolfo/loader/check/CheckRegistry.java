package astolfo.loader.check;

import java.util.List;

public interface CheckRegistry {
   Verdict resolve(CheckRequest var1);

   boolean addCheck(StageCheck var1);

   boolean removeCheck(StageCheck var1);

   List<StageCheck> getChecks();

   boolean addProcessor(VerdictProcessor var1);

   boolean removeProcessor(VerdictProcessor var1);

   List<VerdictProcessor> getProcessors();
}
