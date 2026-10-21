package astolfo.loader.check;

public enum UndecidedResult implements CheckResult {
   INSTANCE;

   @Override
   public ResultKind getKind() {
      return ResultKind.C;
   }
}
