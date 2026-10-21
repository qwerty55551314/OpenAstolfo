package oshi.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.util.tuples.Pair;

@ThreadSafe
public final class ParseUtil {
   private static final String v = "{} didn't parse. Returning default. {}";
   private static final Pattern HERTZ_PATTERN = Pattern.compile("(\\d+(.\\d+)?) ?([kMGT]?Hz).*");
   private static final Pattern VALID_HEX = Pattern.compile("[0-9a-fA-F]+");
   private static final Pattern DHMS = Pattern.compile("(?:(\\d+)-)?(?:(\\d+):)??(?:(\\d+):)?(\\d+)(?:\\.(\\d+))?");
   private static final Pattern UUID_PATTERN = Pattern.compile(".*([0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}).*");
   private static final Pattern VENDOR_PRODUCT_ID = Pattern.compile(".*(?:VID|VEN)_(\\p{XDigit}{4})&(?:PID|DEV)_(\\p{XDigit}{4}).*");
   private static final Pattern LSPCI_MACHINE_READABLE = Pattern.compile("(.+)\\s\\[(.*?)\\]");
   private static final Pattern LSPCI_MEMORY_SIZE = Pattern.compile(".+\\s\\[size=(\\d+)([kKMGT])\\]");
   private static final String D = "Hz";
   private static final String C = "kHz";
   private static final String y = "MHz";
   private static final String M = "GHz";
   private static final String a = "THz";
   private static final String j = "PHz";
   private static final Map<String, Long> multipliers = new HashMap<>();
   private static final long F = 11644473600000L;
   private static final int TZ_OFFSET = TimeZone.getDefault().getOffset(System.currentTimeMillis());
   public static final Pattern whitespacesColonWhitespace = Pattern.compile("\\s+:\\s");
   public static final Pattern whitespaces = Pattern.compile("\\s+");
   public static final Pattern notDigits = Pattern.compile("[^0-9]+");
   public static final Pattern startWithNotDigits = Pattern.compile("^[^0-9]*");
   private static final long[] POWERS_OF_TEN = new long[]{
      1L,
      10L,
      100L,
      1000L,
      10000L,
      100000L,
      1000000L,
      10000000L,
      100000000L,
      1000000000L,
      10000000000L,
      100000000000L,
      1000000000000L,
      10000000000000L,
      100000000000000L,
      1000000000000000L,
      10000000000000000L,
      100000000000000000L,
      1000000000000000000L
   };
   private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
   private static final DateTimeFormatter CIM_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss.SSSSSSZZZZZ", Locale.US);
   private static int K;

   private ParseUtil() {
   }

   public static long parseHertz(String hertz) {
      Matcher matcher = HERTZ_PATTERN.matcher(hertz.trim());
      boolean flag = matcher.find();
      if (flag && matcher.groupCount() == 3) {
         double d0 = Double.valueOf(matcher.group(1)) * (double)multipliers.getOrDefault(matcher.group(3), -1L).longValue();
         if (d0 >= 0.0) {
            return (long)d0;
         }
      }

      return -1L;
   }

   public static int parseLastInt(String s, int i) {
      try {
         String s1 = parseLastString(s);
         return s1.toLowerCase().startsWith("0x") ? Integer.decode(s1) : Integer.parseInt(s1);
      } catch (NumberFormatException numberformatexception) {
         return i;
      }
   }

   public static long parseLastLong(String s, long li) {
      try {
         String s1 = parseLastString(s);
         return s1.toLowerCase().startsWith("0x") ? Long.decode(s1) : Long.parseLong(s1);
      } catch (NumberFormatException numberformatexception) {
         return li;
      }
   }

   public static double parseLastDouble(String s, double d) {
      try {
         return Double.parseDouble(parseLastString(s));
      } catch (NumberFormatException numberformatexception) {
         return d;
      }
   }

   public static String parseLastString(String s) {
      String[] astring = whitespaces.split(s);
      return astring.length < 1 ? s : astring[astring.length - 1];
   }

   public static String byteArrayToHexString(byte[] bytes) {
      char[] achar = new char[bytes.length * 2];

      for (int i = 0; i < bytes.length; i++) {
         int jx = bytes[i] & 255;
         achar[i * 2] = HEX_ARRAY[jx >>> 4];
         achar[i * 2 + 1] = HEX_ARRAY[jx & 15];
      }

      return new String(achar);
   }

   public static byte[] hexStringToByteArray(String digits) {
      int i = digits.length();
      if (VALID_HEX.matcher(digits).matches() && (i & 1) == 0) {
         byte[] abyte = new byte[i / 2];

         for (int b0 = 0; b0 < i; b0 += 2) {
            abyte[b0 / 2] = (byte)(Character.digit(digits.charAt(b0), 16) << 4 | Character.digit(digits.charAt(b0 + 1), 16));
         }

         return abyte;
      } else {
         return new byte[0];
      }
   }

   public static byte[] asciiStringToByteArray(String text, int length) {
      return Arrays.copyOf(text.getBytes(StandardCharsets.US_ASCII), length);
   }

   public static byte[] longToByteArray(long value, int valueSize, int length) {
      long i = value;
      byte[] abyte = new byte[8];

      for (int jx = 7; jx >= 0 && i != 0L; jx--) {
         abyte[jx] = (byte)((int)i);
         i >>>= 8;
      }

      return Arrays.copyOfRange(abyte, 8 - valueSize, 8 + length - valueSize);
   }

   public static long strToLong(String str, int size) {
      return byteArrayToLong(str.getBytes(StandardCharsets.US_ASCII), size);
   }

   public static long byteArrayToLong(byte[] bytes, int size) {
      if (size > 8) {
         throw new IllegalArgumentException("Can't convert more than 8 bytes.");
      } else if (size > bytes.length) {
         throw new IllegalArgumentException("Size can't be larger than array length.");
      } else {
         long i = 0L;

         for (int jx = 0; jx < size; jx++) {
            long k = i << 8 | (long)(bytes[jx] & 255);
            i = k;
         }

         return i;
      }
   }

   public static float byteArrayToFloat(byte[] bytes, int size, int fpBits) {
      return (float)byteArrayToLong(bytes, size) / (float)(1 << fpBits);
   }

   public static long unsignedIntToLong(int unsignedValue) {
      long i = (long)unsignedValue;
      return i & 4294967295L;
   }

   public static long unsignedLongToSignedLong(long unsignedValue) {
      return unsignedValue & Long.MAX_VALUE;
   }

   public static String hexStringToString(String hexString) {
      if (hexString.length() % 2 > 0) {
         return hexString;
      } else {
         StringBuilder stringbuilder = new StringBuilder();

         try {
            for (int b0 = 0; b0 < hexString.length(); b0 += 2) {
               int i = Integer.parseInt(hexString.substring(b0, b0 + 2), 16);
               if (i < 32 || i > 127) {
                  return hexString;
               }

               stringbuilder.append((char)i);
            }
         } catch (NumberFormatException numberformatexception) {
            return hexString;
         }

         return stringbuilder.toString();
      }
   }

   public static int parseIntOrDefault(String s, int defaultInt) {
      try {
         return Integer.parseInt(s);
      } catch (NumberFormatException numberformatexception) {
         return defaultInt;
      }
   }

   public static long parseLongOrDefault(String s, long defaultLong) {
      try {
         return Long.parseLong(s);
      } catch (NumberFormatException numberformatexception) {
         return defaultLong;
      }
   }

   public static long parseUnsignedLongOrDefault(String s, long defaultLong) {
      try {
         return new BigInteger(s).longValue();
      } catch (NumberFormatException numberformatexception) {
         return defaultLong;
      }
   }

   public static double parseDoubleOrDefault(String s, double defaultDouble) {
      try {
         return Double.parseDouble(s);
      } catch (NumberFormatException numberformatexception) {
         return defaultDouble;
      }
   }

   public static long parseDHMSOrDefault(String s, long defaultLong) {
      Matcher matcher = DHMS.matcher(s);
      if (matcher.matches()) {
         long i = 0L;
         String s1 = matcher.group(1);
         if (s1 != null) {
            i += parseLongOrDefault(matcher.group(1), 0L) * 86400000L;
         }

         String s1x = matcher.group(2);
         if (s1x != null) {
            i += parseLongOrDefault(matcher.group(2), 0L) * 3600000L;
         }

         if (matcher.group(3) != null) {
            i += parseLongOrDefault(matcher.group(3), 0L) * 60000L;
         }

         i += parseLongOrDefault(matcher.group(4), 0L) * 1000L;
         return i + (long)(1000.0 * parseDoubleOrDefault("0." + matcher.group(5), 0.0));
      } else {
         return defaultLong;
      }
   }

   public static String parseUuidOrDefault(String s, String defaultStr) {
      Matcher matcher = UUID_PATTERN.matcher(s.toLowerCase());
      return matcher.matches() ? matcher.group(1) : defaultStr;
   }

   public static String getSingleQuoteStringValue(String line) {
      return getStringBetween(line, '\'');
   }

   public static String getDoubleQuoteStringValue(String line) {
      return getStringBetween(line, '"');
   }

   public static String getStringBetween(String line, char c) {
      int i = line.indexOf(c);
      return i < 0 ? "" : line.substring(i + 1, line.lastIndexOf(c)).trim();
   }

   public static int getFirstIntValue(String line) {
      return getNthIntValue(line, 1);
   }

   public static int getNthIntValue(String line, int n) {
      String[] astring = notDigits.split(startWithNotDigits.matcher(line).replaceFirst(""));
      return astring.length >= n ? parseIntOrDefault(astring[n - 1], 0) : 0;
   }

   public static String removeMatchingString(String original, String toRemove) {
      if (original != null && !original.isEmpty() && toRemove != null && !toRemove.isEmpty()) {
         int i = original.indexOf(toRemove, 0);
         if (i == -1) {
            return original;
         } else {
            StringBuilder stringbuilder = new StringBuilder(original.length() - toRemove.length());
            int jx = 0;

            do {
               stringbuilder.append(original.substring(jx, i));
               jx = i + toRemove.length();
               i = original.indexOf(toRemove, jx);
            } while (i != -1);

            stringbuilder.append(original.substring(jx));
            return stringbuilder.toString();
         }
      } else {
         return original;
      }
   }

   public static long[] parseStringToLongArray(String s, int[] indices, int length, char delimiter) {
      long[] along = new long[indices.length];
      int i = s.length();
      int jx = indices.length - 1;
      int k = length - 1;
      int l = 0;
      boolean flag = false;
      boolean flag1 = true;
      boolean flag2 = false;
      boolean flag3 = false;

      while (--i > 0 && jx >= 0) {
         char c0 = s.charAt(i);
         if (c0 == delimiter) {
            if (!flag2 && flag1) {
               flag2 = true;
            }

            if (!flag) {
               if (flag2 && indices[jx] == k--) {
                  jx--;
               }

               flag = true;
               l = 0;
               flag3 = false;
               flag1 = true;
            }
         } else if (indices[jx] != k || c0 == '+' || !flag1) {
            flag = false;
         } else if (c0 >= '0' && c0 <= '9' && !flag3) {
            if (l <= 18 && (l != 17 || c0 != '9' || along[jx] <= 223372036854775807L)) {
               along[jx] += (long)(c0 - '0') * POWERS_OF_TEN[l++];
            } else {
               along[jx] = Long.MAX_VALUE;
            }

            flag = false;
         } else if (c0 == '-') {
            along[jx] *= -1L;
            flag = false;
            flag3 = true;
         } else {
            if (flag2) {
               return new long[indices.length];
            }

            along[jx] = 0L;
            flag1 = false;
         }
      }

      return jx > 0 ? new long[indices.length] : along;
   }

   public static int countStringToLongArray(String s, char delimiter) {
      int i = s.length();
      int jx = 0;
      boolean flag = false;
      boolean flag1 = true;
      boolean flag2 = false;

      while (--i > 0) {
         char c0 = s.charAt(i);
         if (c0 == delimiter) {
            if (!flag) {
               if (flag1) {
                  jx++;
               }

               flag = true;
               flag2 = false;
               flag1 = true;
            }
         } else if (c0 == '+' || !flag1) {
            flag = false;
         } else if (c0 >= '0' && c0 <= '9' && !flag2) {
            flag = false;
         } else if (c0 == '-') {
            flag = false;
            flag2 = true;
         } else {
            if (jx > 0) {
               return jx;
            }

            flag1 = false;
         }
      }

      return jx + 1;
   }

   public static String getTextBetweenStrings(String text, String before, String after) {
      String s = "";
      if (text.indexOf(before) >= 0 && text.indexOf(after) >= 0) {
         s = text.substring(text.indexOf(before) + before.length(), text.length());
         s = s.substring(0, s.indexOf(after));
      }

      return s;
   }

   public static long filetimeToUtcMs(long filetime, boolean local) {
      return filetime / 10000L - 11644473600000L - (local ? (long)TZ_OFFSET : 0L);
   }

   public static String parseMmDdYyyyToYyyyMmDD(String dateString) {
      try {
         return String.format("%s-%s-%s", dateString.substring(6, 10), dateString.substring(0, 2), dateString.substring(3, 5));
      } catch (StringIndexOutOfBoundsException stringindexoutofboundsexception) {
         return dateString;
      }
   }

   public static OffsetDateTime parseCimDateTimeToOffset(String cimDateTime) {
      try {
         int i = Integer.parseInt(cimDateTime.substring(22));
         LocalTime localtime = LocalTime.MIDNIGHT.plusMinutes((long)i);
         return OffsetDateTime.parse(cimDateTime.substring(0, 22) + localtime.format(DateTimeFormatter.ISO_LOCAL_TIME), CIM_FORMAT);
      } catch (NumberFormatException | DateTimeParseException | IndexOutOfBoundsException indexoutofboundsexception) {
         return Constants.UNIX_EPOCH;
      }
   }

   public static boolean filePathStartsWith(List<String> prefixList, String path) {
      for (String s : prefixList) {
         if (path.equals(s) || path.startsWith(s + "/")) {
            return true;
         }
      }

      return false;
   }

   public static long parseDecimalMemorySizeToBinary(String size) {
      String[] astring = whitespaces.split(size);
      long i = parseLongOrDefault(astring[0], 0L);
      if (astring.length == 2 && astring[1].length() > 1) {
         switch (astring[1].charAt(0)) {
            case 'G':
               i <<= 30;
               break;
            case 'K':
            case 'k':
               i <<= 10;
               break;
            case 'M':
               i <<= 20;
               break;
            case 'T':
               i <<= 40;
         }
      }

      return i;
   }

   public static Pair<String, String> parsePnPDeviceIdToVendorProductId(String pnpDeviceId) {
      Matcher matcher = VENDOR_PRODUCT_ID.matcher(pnpDeviceId);
      if (matcher.matches()) {
         String s = "0x" + matcher.group(1).toLowerCase();
         String s1 = "0x" + matcher.group(2).toLowerCase();
         return new Pair<>(s, s1);
      } else {
         return null;
      }
   }

   public static long parseLshwResourceString(String resources) {
      long i = 0L;
      String[] astring = whitespaces.split(resources);

      for (String s : astring) {
         if (s.startsWith("memory:")) {
            String[] astring1 = s.substring(7).split("-");
            if (astring1.length == 2) {
               try {
                  i += Long.parseLong(astring1[1], 16) - Long.parseLong(astring1[0], 16) + 1L;
               } catch (NumberFormatException numberformatexception) {
               }
            }
         }
      }

      return i;
   }

   public static Pair<String, String> parseLspciMachineReadable(String line) {
      Matcher matcher = LSPCI_MACHINE_READABLE.matcher(line);
      return matcher.matches() ? new Pair<>(matcher.group(1), matcher.group(2)) : null;
   }

   public static long parseLspciMemorySize(String line) {
      Matcher matcher = LSPCI_MEMORY_SIZE.matcher(line);
      return matcher.matches() ? parseDecimalMemorySizeToBinary(matcher.group(1) + " " + matcher.group(2) + "B") : 0L;
   }

   static {
      int short1 = 30570;
      long i = 1324656337635860659L ^ 138425294819973L;
      multipliers.put("Hz", 1L);
      multipliers.put("kHz", 1000L);
      multipliers.put("MHz", 1000000L);
      multipliers.put("GHz", 1000000000L);
      multipliers.put("THz", 1000000000000L);
      multipliers.put("PHz", 1000000000000000L);
   }

   public static int i() {
      return 54;
   }
}
