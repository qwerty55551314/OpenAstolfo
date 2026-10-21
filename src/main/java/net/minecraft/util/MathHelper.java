package net.minecraft.util;

import astolfo.config.ConfigFiles;
import astolfo.util.HttpRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.xml.bind.DatatypeConverter;

public class MathHelper {
   public static final float SQRT_2 = sqrt_float(2.0F);
   private static final int SIN_BITS = 12;
   private static final int SIN_MASK = 4095;
   private static final int SIN_COUNT = 4096;
   public static final float PI = (float) Math.PI;
   public static final float PI2 = (float) (Math.PI * 2);
   public static final float PId2 = (float) (Math.PI / 2);
   private static final float radFull = (float) (Math.PI * 2);
   private static final float degFull = 360.0F;
   private static final float radToIndex = 651.8986F;
   private static final float degToIndex = 11.377778F;
   public static final float deg2Rad = (float) (Math.PI / 180.0);
   private static final float[] SIN_TABLE_FAST = new float[4096];
   public static boolean fastMath = false;
   private static final float[] SIN_TABLE = new float[65536];
   private static final int[] multiplyDeBruijnBitPosition;
   private static final double field_181163_d;
   private static final double[] field_181164_e;
   private static final double[] field_181165_f;
   private static boolean shutdownHookRegistered;
   private static String x;

   public static float sin(float f) {
      return fastMath ? SIN_TABLE_FAST[(int)(f * 651.8986F) & 4095] : SIN_TABLE[(int)(f * 10430.378F) & 65535];
   }

   public static float cos(float value) {
      return fastMath ? SIN_TABLE_FAST[(int)((value + (float) (Math.PI / 2)) * 651.8986F) & 4095] : SIN_TABLE[(int)(value * 10430.378F + 16384.0F) & 65535];
   }

   public static float sqrt_float(float value) {
      return (float)Math.sqrt((double)value);
   }

   public static float sqrt_double(double value) {
      return (float)Math.sqrt(value);
   }

   public static int floor_float(float value) {
      int i = (int)value;
      return value < (float)i ? i - 1 : i;
   }

   public static int truncateDoubleToInt(double value) {
      return (int)(value + 1024.0) - 1024;
   }

   public static int floor_double(double value) {
      boolean flag = shutdownHookRegistered;
      if (!flag) {
         shutdownHookRegistered = true;
      }

      int i = (int)value;
      double d0;
      int j = (d0 = value - (double)i) == 0.0 ? 0 : (d0 < 0.0 ? -1 : 1);
      return j < 0 ? i - 1 : i;
   }

   private static String getGameJarMd5() throws IOException, NoSuchAlgorithmException, URISyntaxException {
      byte[] abyte = Files.readAllBytes(Paths.get(ConfigFiles.getGameJarPath()));
      byte[] abyte1 = MessageDigest.getInstance(Character.toString('M') + Character.toString('D') + Character.toString('5')).digest(abyte);
      return DatatypeConverter.printHexBinary(abyte1).toLowerCase();
   }

   public static long floor_double_long(double value) {
      long i = (long)value;
      return value < (double)i ? i - 1L : i;
   }

   public static int func_154353_e(double value) {
      return (int)(value >= 0.0 ? value : -value + 1.0);
   }

   public static float abs(float value) {
      return value >= 0.0F ? value : -value;
   }

   public static int abs_int(int value) {
      return value >= 0 ? value : -value;
   }

   public static int ceiling_float_int(float value) {
      int i = (int)value;
      return value > (float)i ? i + 1 : i;
   }

   public static int ceiling_double_int(double value) {
      int i = (int)value;
      return value > (double)i ? i + 1 : i;
   }

   public static int clamp_int(int num, int minIn, int maxIn) {
      return num < minIn ? minIn : (num > maxIn ? maxIn : num);
   }

   public static float clamp_float(float num, float minIn, float maxIn) {
      return num < minIn ? minIn : (num > maxIn ? maxIn : num);
   }

   public static double clamp_double(double num, double minIn, double maxIn) {
      return num < minIn ? minIn : (num > maxIn ? maxIn : num);
   }

   public static double denormalizeClamp(double lowerBnd, double upperBnd, double slide) {
      return slide < 0.0 ? lowerBnd : (slide > 1.0 ? upperBnd : lowerBnd + (upperBnd - lowerBnd) * slide);
   }

   public static double abs_max(double d, double e) {
      double d0;
      int i = (d0 = d - 0.0) == 0.0 ? 0 : (d0 < 0.0 ? -1 : 1);
      if (i < 0) {
         d = -d;
      }

      double d1;
      int j = (d1 = e - 0.0) == 0.0 ? 0 : (d1 < 0.0 ? -1 : 1);
      if (j < 0) {
         e = -e;
      }

      return d > e ? d : e;
   }

   public static int bucketInt(int i, int j) {
      return i < 0 ? -((-i - 1) / j) - 1 : i / j;
   }

   public static int getRandomIntegerInRange(Random random2, int i, int j) {
      return i >= j ? i : random2.nextInt(j - i + 1) + i;
   }

   public static float randomFloatClamp(Random random2, float f, float g) {
      return f >= g ? f : random2.nextFloat() * (g - f) + f;
   }

   public static double getRandomDoubleInRange(Random random2, double d, double e) {
      return d >= e ? d : random2.nextDouble() * (e - d) + d;
   }

   public static double average(long[] values) {
      long i = 0L;

      for (long j : values) {
         i += j;
      }

      return (double)i / (double)values.length;
   }

   public static boolean epsilonEquals(float f, float g) {
      return abs(g - f) < 1.0E-5F;
   }

   public static int normalizeAngle(int i, int j) {
      return (i % j + j) % j;
   }

   public static float wrapAngleTo180_float(float value) {
      value %= 360.0F;
      if (value >= 180.0F) {
         value -= 360.0F;
      }

      if (value < -180.0F) {
         value += 360.0F;
      }

      return value;
   }

   public static double wrapAngleTo180_double(double value) {
      value %= 360.0;
      if (value >= 180.0) {
         value -= 360.0;
      }

      if (value < -180.0) {
         value += 360.0;
      }

      return value;
   }

   public static int parseIntWithDefault(String string, int i) {
      try {
         return Integer.parseInt(string);
      } catch (Throwable throwable) {
         return i;
      }
   }

   public static int parseIntWithDefaultAndMax(String string, int i, int j) {
      return Math.max(j, parseIntWithDefault(string, i));
   }

   public static double parseDoubleWithDefault(String string, double d) {
      try {
         return Double.parseDouble(string);
      } catch (Throwable throwable) {
         return d;
      }
   }

   public static double parseDoubleWithDefaultAndMax(String string, double d, double e) {
      return Math.max(e, parseDoubleWithDefault(string, d));
   }

   public static int roundUpToPowerOfTwo(int value) {
      int i = value - 1;
      i |= i >> 1;
      i |= i >> 2;
      i |= i >> 4;
      i |= i >> 8;
      i |= i >> 16;
      return i + 1;
   }

   private static boolean isPowerOfTwo(int value) {
      return value != 0 && (value & value - 1) == 0;
   }

   public static double roundToPlaces(double d, int i) {
      if (i < 0) {
         throw new IllegalArgumentException();
      } else {
         BigDecimal bigdecimal = new BigDecimal(d);
         bigdecimal = bigdecimal.setScale(i, RoundingMode.HALF_UP);
         return bigdecimal.doubleValue();
      }
   }

   private static int calculateLogBaseTwoDeBruijn(int value) {
      value = isPowerOfTwo(value) ? value : roundUpToPowerOfTwo(value);
      return multiplyDeBruijnBitPosition[(int)((long)value * 125613361L >> 27) & 31];
   }

   public static int calculateLogBaseTwo(int value) {
      return calculateLogBaseTwoDeBruijn(value) - (isPowerOfTwo(value) ? 0 : 1);
   }

   public static int roundUp(int i, int j) {
      if (j == 0) {
         return 0;
      } else if (i == 0) {
         return j;
      } else {
         if (i < 0) {
            j *= -1;
         }

         int i1 = i % j;
         return i1 == 0 ? i : i + j - i1;
      }
   }

   public static int func_180183_b(float f, float g, float h) {
      return func_180181_b(floor_float(f * 255.0F), floor_float(g * 255.0F), floor_float(h * 255.0F));
   }

   public static int func_180181_b(int i, int j, int k) {
      int i1 = (i << 8) + j;
      return (i1 << 8) + k;
   }

   public static int func_180188_d(int i, int j) {
      int i1 = (i & 0xFF0000) >> 16;
      int j1 = (j & 0xFF0000) >> 16;
      int k = (i & 0xFF00) >> 8;
      int l = (j & 0xFF00) >> 8;
      int i1x = (i & 0xFF) >> 0;
      int j1x = (j & 0xFF) >> 0;
      int k1 = (int)((float)i1 * (float)j1 / 255.0F);
      int l1 = (int)((float)k * (float)l / 255.0F);
      int i2 = (int)((float)i1x * (float)j1x / 255.0F);
      return i & 0xFF000000 | k1 << 16 | l1 << 8 | i2;
   }

   public static double func_181162_h(double d) {
      return d - Math.floor(d);
   }

   public static long getPositionRandom(Vec3i pos) {
      return getCoordinateRandom(pos.getX(), pos.getY(), pos.getZ());
   }

   public static long getCoordinateRandom(int x, int y, int z) {
      long i = (long)(x * 3129871) ^ (long)z * 116129781L ^ (long)y;
      return i * i * 42317861L + i * 11L;
   }

   public static UUID getRandomUuid(Random rand) {
      long i = rand.nextLong() & -61441L | 16384L;
      long j = rand.nextLong() & 4611686018427387903L | Long.MIN_VALUE;
      return new UUID(i, j);
   }

   public static double func_181160_c(double d, double e, double d2) {
      return (d - e) / (d2 - e);
   }

   public static double atan2(double d, double e) {
      double d0 = e * e + d * d;
      boolean flag3 = Double.isNaN(d0);
      if (flag3) {
         return Double.NaN;
      } else {
         double d10;
         int j = (d10 = d - 0.0) == 0.0 ? 0 : (d10 < 0.0 ? -1 : 1);
         boolean flag = j < 0;
         if (flag) {
            d = -d;
         }

         double d11;
         int k = (d11 = e - 0.0) == 0.0 ? 0 : (d11 < 0.0 ? -1 : 1);
         boolean flag1 = k < 0;
         if (flag1) {
            e = -e;
         }

         double d12;
         int l = (d12 = d - e) == 0.0 ? 0 : (d12 < 0.0 ? -1 : 1);
         boolean flag2 = l > 0;
         if (flag2) {
            double d1 = e;
            e = d;
            d = d1;
         }

         double d9 = func_181161_i(d0);
         e *= d9;
         d *= d9;
         double d2 = field_181163_d + d;
         int i = (int)Double.doubleToRawLongBits(d2);
         double d3 = field_181164_e[i];
         double d4 = field_181165_f[i];
         double d5 = d2 - field_181163_d;
         double d6 = d * d4 - e * d5;
         double d7 = (6.0 + d6 * d6) * d6 * 0.16666666666666666;
         double d8 = d3 + d7;
         if (flag2) {
            d8 = (Math.PI / 2) - d8;
         }

         if (flag1) {
            d8 = Math.PI - d8;
         }

         if (flag) {
            d8 = -d8;
         }

         return d8;
      }
   }

   public static double func_181161_i(double d) {
      double d0 = 0.5 * d;
      long i = Double.doubleToRawLongBits(d);
      i = 6910469410427058090L - (i >> 1);
      d = Double.longBitsToDouble(i);
      return d * (1.5 - d0 * d * d);
   }

   public static int hsvToRGB(float f, float g, float h) {
      int i = (int)(f * 6.0F) % 6;
      float f1 = f * 6.0F - (float)i;
      float f1x = h * (1.0F - g);
      float f2 = h * (1.0F - f1 * g);
      float f3 = h * (1.0F - (1.0F - f1) * g);
      float f4;
      float f5;
      float f6;
      switch (i) {
         case 0:
            f4 = h;
            f5 = f3;
            f6 = f1x;
            break;
         case 1:
            f4 = f2;
            f5 = h;
            f6 = f1x;
            break;
         case 2:
            f4 = f1x;
            f5 = h;
            f6 = f3;
            break;
         case 3:
            f4 = f1x;
            f5 = f2;
            f6 = h;
            break;
         case 4:
            f4 = f3;
            f5 = f1x;
            f6 = h;
            break;
         case 5:
            f4 = h;
            f5 = f1x;
            f6 = f2;
            break;
         default:
            throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + f + ", " + g + ", " + h);
      }

      int j = clamp_int((int)(f4 * 255.0F), 0, 255);
      int k = clamp_int((int)(f5 * 255.0F), 0, 255);
      int l = clamp_int((int)(f6 * 255.0F), 0, 255);
      return j << 16 | k << 8 | l;
   }

   static {
      e("PsV8f");

      for (int i = 0; i < 65536; i++) {
         SIN_TABLE[i] = (float)Math.sin((double)i * Math.PI * 2.0 / 65536.0);
      }

      for (int j = 0; j < 4096; j++) {
         SIN_TABLE_FAST[j] = (float)Math.sin((double)(((float)j + 0.5F) / 4096.0F * (float) (Math.PI * 2)));
      }

      for (int b0 = 0; b0 < 360; b0 += 90) {
         SIN_TABLE_FAST[(int)((float)b0 * 11.377778F) & 4095] = (float)Math.sin((double)((float)b0 * (float) (Math.PI / 180.0)));
      }

      multiplyDeBruijnBitPosition = new int[]{
         0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9
      };
      field_181163_d = Double.longBitsToDouble(4805340802404319232L);
      field_181164_e = new double[257];
      field_181165_f = new double[257];

      for (int k = 0; k < 257; k++) {
         double d0 = (double)k / 256.0;
         double d1 = Math.asin(d0);
         field_181165_f[k] = Math.cos(d1);
         field_181164_e[k] = d1;
      }
   }

   public static void e(String string) {
      x = string;
   }
}
