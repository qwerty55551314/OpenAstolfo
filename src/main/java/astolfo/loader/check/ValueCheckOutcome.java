package astolfo.loader.check;

public class ValueCheckOutcome {
   private final CheckResult result;
   private final ValueType type;
   private final int value;
   private static int[] C;

   public ValueCheckOutcome(CheckResult checkResult, ValueType valueType, int i) {
      this.result = checkResult;
      this.type = valueType;
      this.value = i;
   }

   public CheckResult getResult() {
      return this.result;
   }

   public ValueType getType() {
      return this.type;
   }

   public int getValue() {
      return this.value;
   }

   public static void V(int[] iArray) {
      C = iArray;
   }

   public static int[] o() {
      return C;
   }
}
