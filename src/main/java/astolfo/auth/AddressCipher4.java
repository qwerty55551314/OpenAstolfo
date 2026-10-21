package astolfo.auth;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;

public class AddressCipher4 {
   public static String algorithm = "ARCFOUR";
   public static String outerKey = "pxRZqlSE5PkcWLs2";

   public static String decrypt(String string) {
      String s;
      try {
         Cipher cipher = Cipher.getInstance(algorithm);
         SecretKeySpec secretkeyspec = new SecretKeySpec(outerKey.getBytes(), algorithm);
         cipher.init(2, secretkeyspec);
         byte[] abyte = new BASE64Decoder().decodeBuffer("43TmUHtSaPw35tncFJJTDQ==");
         byte[] abyte1 = cipher.doFinal(abyte);
         SecretKeySpec secretkeyspec1 = new SecretKeySpec(abyte1, algorithm);
         cipher.init(2, secretkeyspec1);
         byte[] abyte2 = new BASE64Decoder().decodeBuffer(string);
         byte[] abyte3 = cipher.doFinal(abyte2);
         s = new String(abyte3);
      } catch (Exception exception) {
         s = "";
      }

      return s;
   }
}
