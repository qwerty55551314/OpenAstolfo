package astolfo.auth.fingerprint;

import java.io.File;
import net.minecraft.util.Util;

public final class UserDirectoryValidator implements FingerprintValidator<String> {
   private static final String b = "C:/Users/";

   @Override
   public boolean test(FingerprintEntry fingerprintEntry) {
      boolean flag1 = Util.getOSType().equals(Util.EnumOS.WINDOWS);
      if (!flag1) {
         return true;
      } else {
         boolean flag = false;
         File file1 = new File(b);
         File[] afile = file1.listFiles();
         if (afile != null || afile.length != 0) {
            for (File file2 : afile) {
               if (file2.isDirectory() && ((String)fingerprintEntry.getValue()).contains(file2.getName())) {
                  flag = true;
                  break;
               }
            }
         }

         return flag;
      }
   }
}
