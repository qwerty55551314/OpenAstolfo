package astolfo.util;

import java.text.DecimalFormat;
import net.minecraft.util.Vec3;

public final class MathUtil {
   public static final DecimalFormat ONE_DECIMAL = new DecimalFormat("#.#");
   private static String[] E;

   public static double interpolate(double d, double e, float f, double d2) {
      return d + (e - d) * (double)f - d2;
   }

   public static double randomInRange(double d, double e) {
      return Math.random() * (e - d) + d;
   }

   public static final Vec3 rotate(Vec3 vec3, float f, float g) {
      double d0 = Math.toRadians((double)(-1.0F * (f + 90.0F)));
      double d1 = Math.toRadians((double)(-g));
      double d2 = Math.cos(d0);
      double d3 = Math.cos(d1);
      double d4 = Math.sin(d0);
      double d5 = Math.sin(d1);
      double d6 = vec3.xCoord;
      double d7 = vec3.yCoord;
      double d9 = d6 * d3 - d7 * d5;
      double d10 = d6 * d5 + d7 * d3;
      double d8 = vec3.zCoord;
      double d11 = d8 * d2 - d9 * d4;
      d9 = d8 * d4 + d9 * d2;
      return new Vec3(d9, d10, d11);
   }
}
