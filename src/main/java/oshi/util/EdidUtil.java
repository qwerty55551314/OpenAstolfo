package oshi.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class EdidUtil {
   private EdidUtil() {
   }

   public static String getManufacturerID(byte[] edid) {
      String s = String.format("%8s%8s", Integer.toBinaryString(edid[8] & 255), Integer.toBinaryString(edid[9] & 255)).replace(' ', '0');
      return String.format(
            "%s%s%s",
            (char)(64 + Integer.parseInt(s.substring(1, 6), 2)),
            (char)(64 + Integer.parseInt(s.substring(7, 11), 2)),
            (char)(64 + Integer.parseInt(s.substring(12, 16), 2))
         )
         .replace("@", "");
   }

   public static String getProductID(byte[] edid) {
      return Integer.toHexString(ByteBuffer.wrap(Arrays.copyOfRange(edid, 10, 12)).order(ByteOrder.LITTLE_ENDIAN).getShort() & '\uffff');
   }

   public static String getSerialNo(byte[] edid) {
      return String.format(
         "%s%s%s%s", getAlphaNumericOrHex(edid[15]), getAlphaNumericOrHex(edid[14]), getAlphaNumericOrHex(edid[13]), getAlphaNumericOrHex(edid[12])
      );
   }

   private static String getAlphaNumericOrHex(byte b) {
      return Character.isLetterOrDigit((char)b) ? String.format("%s", (char)b) : String.format("%02X", b);
   }

   public static byte getWeek(byte[] edid) {
      return edid[16];
   }

   public static int getYear(byte[] edid) {
      byte b0 = edid[17];
      return b0 + 1990;
   }

   public static String getVersion(byte[] edid) {
      return edid[18] + "." + edid[19];
   }

   public static boolean isDigital(byte[] edid) {
      return 1 == (edid[20] & 255) >> 7;
   }

   public static int getHcm(byte[] edid) {
      return edid[21];
   }

   public static int getVcm(byte[] edid) {
      return edid[22];
   }

   public static byte[][] getDescriptors(byte[] edid) {
      byte[][] abyte = new byte[4][18];

      for (int i = 0; i < abyte.length; i++) {
         System.arraycopy(edid, 54 + 18 * i, abyte[i], 0, 18);
      }

      return abyte;
   }

   public static int getDescriptorType(byte[] desc) {
      return ByteBuffer.wrap(Arrays.copyOfRange(desc, 0, 4)).getInt();
   }

   public static String getTimingDescriptor(byte[] desc) {
      int i = ByteBuffer.wrap(Arrays.copyOfRange(desc, 0, 2)).order(ByteOrder.LITTLE_ENDIAN).getShort() / 100;
      int j = (desc[2] & 255) + ((desc[4] & 240) << 4);
      int k = (desc[5] & 255) + ((desc[7] & 240) << 4);
      return String.format("Clock %dMHz, Active Pixels %dx%d ", i, j, k);
   }

   public static String getDescriptorRangeLimits(byte[] desc) {
      return String.format("Field Rate %d-%d Hz vertical, %d-%d Hz horizontal, Max clock: %d MHz", desc[5], desc[6], desc[7], desc[8], desc[9] * 10);
   }

   public static String getDescriptorText(byte[] desc) {
      return new String(Arrays.copyOfRange(desc, 4, 18), StandardCharsets.US_ASCII).trim();
   }

   public static String toString(byte[] edid) {
      StringBuilder stringbuilder = new StringBuilder();
      stringbuilder.append("  Manuf. ID=").append(getManufacturerID(edid));
      stringbuilder.append(", Product ID=").append(getProductID(edid));
      stringbuilder.append(", ").append(isDigital(edid) ? "Digital" : "Analog");
      stringbuilder.append(", Serial=").append(getSerialNo(edid));
      stringbuilder.append(", ManufDate=").append(getWeek(edid) * 12 / 52 + 1).append('/').append(getYear(edid));
      stringbuilder.append(", EDID v").append(getVersion(edid));
      int i = getHcm(edid);
      int j = getVcm(edid);
      stringbuilder.append(String.format("%n  %d x %d cm (%.1f x %.1f in)", i, j, (double)i / 2.54, (double)j / 2.54));
      byte[][] abyte = getDescriptors(edid);

      for (byte[] abyte1 : abyte) {
         int k = getDescriptorType(abyte1);
         switch (k) {
            case 250:
               stringbuilder.append("\n  Standard Timing ID: ").append(ParseUtil.byteArrayToHexString(abyte1));
               break;
            case 251:
               stringbuilder.append("\n  White Point Data: ").append(ParseUtil.byteArrayToHexString(abyte1));
               break;
            case 252:
               stringbuilder.append("\n  Monitor Name: ").append(getDescriptorText(abyte1));
               break;
            case 253:
               stringbuilder.append("\n  Range Limits: ").append(getDescriptorRangeLimits(abyte1));
               break;
            case 254:
               stringbuilder.append("\n  Unspecified Text: ").append(getDescriptorText(abyte1));
               break;
            case 255:
               stringbuilder.append("\n  Serial Number: ").append(getDescriptorText(abyte1));
               break;
            default:
               int l = getDescriptorType(abyte1);
               if (l <= 15 && getDescriptorType(abyte1) >= 0) {
                  stringbuilder.append("\n  Manufacturer Data: ").append(ParseUtil.byteArrayToHexString(abyte1));
               } else {
                  stringbuilder.append("\n  Preferred Timing: ").append(getTimingDescriptor(abyte1));
               }
         }
      }

      return stringbuilder.toString();
   }
}
