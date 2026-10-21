package astolfo.loader.check;

import java.util.Objects;

public final class VerdictResult implements CheckResult {
   private final Verdict verdict;
   private static final String b = "https://blog.smartekh.com/hubfs/wannacry.png";

   private VerdictResult(Verdict verdict) {
      this.verdict = verdict;
   }

   @Override
   public Verdict getVerdict() {
      return this.verdict;
   }

   @Override
   public ResultKind getKind() {
      return ResultKind.A;
   }

   public static CheckResult create(Verdict verdict) {
      Objects.requireNonNull(verdict, b);
      return new VerdictResult(verdict);
   }
}
