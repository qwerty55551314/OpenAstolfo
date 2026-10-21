package astolfo.gui.animation;

import java.util.function.Function;

public enum Easing {
   LINEAR(doubleValue -> doubleValue),
   CURVE(doubleValue -> doubleValue * doubleValue),
   STEEP_CURVE(doubleValue -> doubleValue * doubleValue * doubleValue),
   BEZIER_CURVE(doubleValue -> {
      double d0 = -1.0 + Math.sqrt(-doubleValue + 1.0);
      return d0 * d0;
   }),
   INVERSE_CURVE(doubleValue -> -(doubleValue - 1.0) * (doubleValue - 1.0) + 1.0),
   INVERSE_STEEP_CURVE(doubleValue -> Math.pow(doubleValue - 1.0, 3.0) + 1.0),
   RUBBER(doubleValue -> -Math.sin(10.0 * doubleValue) / (10.0 * doubleValue) + 1.0),
   INERTIA(doubleValue -> Math.cbrt(doubleValue - 0.5) / 1.5874010519681994),
   INSTANT(doubleValue -> (double)Math.round(doubleValue));

   private final Function<Double, Double> function;
   private static final Easing[] VALUES = new Easing[]{LINEAR, CURVE, STEEP_CURVE, BEZIER_CURVE, INVERSE_CURVE, INVERSE_STEEP_CURVE, RUBBER, INERTIA, INSTANT};

   private Easing(Function<Double, Double> function) {
      this.function = function;
   }

   public double apply(double d) {
      return this.function.apply(d);
   }
}
