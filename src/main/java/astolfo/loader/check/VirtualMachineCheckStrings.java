package astolfo.loader.check;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;

public class VirtualMachineCheckStrings {
   public static String algorithm = "ARCFOUR";
   public static String key = "jD7e4x99jnJFYWPX";

   public static String decrypt(String string) {
      String s;
      try {
         Cipher cipher = Cipher.getInstance(algorithm);
         SecretKeySpec secretkeyspec = new SecretKeySpec(key.getBytes(), algorithm);
         cipher.init(2, secretkeyspec);
         byte[] abyte = new BASE64Decoder().decodeBuffer("kkHBHcY3ZSiczPqdmPftdw==");
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
