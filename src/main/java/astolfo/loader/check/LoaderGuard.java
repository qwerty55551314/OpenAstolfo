package astolfo.loader.check;

import astolfo.manager.Loadable;

public class LoaderGuard implements Loadable {
   private final LoadStageList stageList;
   private final StageVerification verification;

   public LoaderGuard() {
      LoadStageListFactory loadstagelistfactory = new LoadStageListFactory();
      this.stageList = new LoadStageList(loadstagelistfactory.getStageContainer());
      this.verification = new StageVerification(this.stageList);
   }

   public LoadStageList getStageList() {
      return this.stageList;
   }

   public StageVerification getVerification() {
      return this.verification;
   }

   @Override
   public void load() {
      this.runTask(() -> {
         ThreadDeath threaddeath = this.verification.getVerifier().verifyStages();
         if (threaddeath != null) {
            throw threaddeath;
         }
      });
   }

   @Override
   public void save() {
   }

   public void runTask(Task task) {
      task.run();
   }
}
