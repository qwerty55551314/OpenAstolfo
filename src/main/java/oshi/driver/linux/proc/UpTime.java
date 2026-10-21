package oshi.driver.linux.proc;

import oshi.annotation.concurrent.ThreadSafe;
import oshi.util.FileUtil;
import oshi.util.platform.linux.ProcPath;

@ThreadSafe
public final class UpTime {
   private static final long b = -8587029781063663584L;

   private UpTime() {
   }

   public static double getSystemUptimeSeconds() {
      String s = FileUtil.getStringFromFile(ProcPath.UPTIME);
      int i = s.indexOf((int)b);

      try {
         return i < 0 ? 0.0 : Double.parseDouble(s.substring(0, i));
      } catch (NumberFormatException numberformatexception) {
         return 0.0;
      }
   }
}
