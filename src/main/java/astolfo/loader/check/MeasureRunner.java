package astolfo.loader.check;

public final class MeasureRunner {
   public static void run(MeasureRequest measureRequest) {
      boolean flag = false;

      try {
         MeasureRegistry.INSTANCE.runMeasures(measureRequest.getGroupId());
      } catch (MeasureException measureexception) {
         flag = true;
      }

      if (!flag) {
         measureRequest.setCompleted(true);
      }
   }
}
