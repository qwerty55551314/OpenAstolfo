package astolfo.loader.check;

import astolfo.loader.LoadStage;
import java.util.List;
import java.util.Objects;

public final class StageCheckRequest implements CheckRequest {
   private final LoadStage stage;
   private static String[] s;
   private static final String b = "https://i.imgur.com/UDP1Ycm.png";

   public StageCheckRequest(LoadStage loadStage) {
      this.stage = loadStage;
   }

   @Override
   public LoadStage getStage() {
      return this.stage;
   }

   @Override
   public CheckContext createContext(List<StageCheck> list, List<VerdictProcessor> list2) {
      String[] astring = new String[0];
      Objects.requireNonNull(list, b);
      return new DefaultCheckContext(this, list, list2);
   }
}
