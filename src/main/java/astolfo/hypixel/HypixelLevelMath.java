package astolfo.hypixel;

public interface HypixelLevelMath {
   double BASE = 10000.0;
   double GROWTH = 2500.0;
   double HALF_GROWTH = 1250.0;
   double REVERSE_PQ_PREFIX = -3.5;
   double REVERSE_CONST = 12.25;
   double GROWTH_DIVIDES_2 = 8.0E-4;

   static double getLevel(double d) {
      return d < 0.0 ? 1.0 : Math.floor(-2.5 + Math.sqrt(12.25 + 8.0E-4 * d));
   }

   static double getExactLevel(double d) {
      return getLevel(d) + getPercentageToNextLevel(d);
   }

   static double getExpFromLevelToNext(double d) {
      return d < 1.0 ? 10000.0 : 2500.0 * (d - 1.0) + 10000.0;
   }

   static double getTotalExpToLevel(double d) {
      double d0 = Math.floor(d);
      double d1 = getTotalExpToFullLevel(d0);
      return d == d0 ? d1 : (getTotalExpToFullLevel(d0 + 1.0) - d1) * (d % 1.0) + d1;
   }

   static double getTotalExpToFullLevel(double d) {
      return (1250.0 * (d - 2.0) + 10000.0) * (d - 1.0);
   }

   static double getPercentageToNextLevel(double d) {
      double d0 = getLevel(d);
      double d1 = getTotalExpToLevel(d0);
      return (d - d1) / (getTotalExpToLevel(d0 + 1.0) - d1);
   }
}
