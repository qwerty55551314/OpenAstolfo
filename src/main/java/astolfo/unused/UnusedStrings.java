package astolfo.unused;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;

public class UnusedStrings {
   public static String cipherAlgorithm = "ARCFOUR";
   public static String rc4Key = "o2vtyP4UjkjWWCQc";

   public static String decrypt(String string) {
      String s;
      try {
         Cipher cipher = Cipher.getInstance(cipherAlgorithm);
         SecretKeySpec secretkeyspec = new SecretKeySpec(rc4Key.getBytes(), cipherAlgorithm);
         cipher.init(2, secretkeyspec);
         byte[] abyte = new BASE64Decoder().decodeBuffer("VoBvIjDOl3xXzxlUfETSfQ==");
         byte[] abyte1 = cipher.doFinal(abyte);
         SecretKeySpec secretkeyspec1 = new SecretKeySpec(abyte1, cipherAlgorithm);
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
