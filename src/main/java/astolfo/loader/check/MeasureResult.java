package astolfo.loader.check;

public final class MeasureResult {
   private MeasureStatus status;
   private String message;

   public MeasureResult(MeasureStatus measureStatus, String string) {
      String s = MeasureException.H();
      this.status = measureStatus;
      if (s != null) {
         this.message = string;
      } else {
         this.message = string;
         int i = 0;
         i++;
      }
   }

   public static final MeasureResult success() {
      return new MeasureResult(MeasureStatus.SUCCESS, "");
   }

   public static final MeasureResult failure(String string) {
      return new MeasureResult(MeasureStatus.FAILURE, string);
   }

   public static final MeasureResult interrupted(String string) {
      return new MeasureResult(MeasureStatus.INTERRUPTED, string);
   }

   public MeasureStatus getStatus() {
      return this.status;
   }

   public String getMessage() {
      return this.message;
   }
}
