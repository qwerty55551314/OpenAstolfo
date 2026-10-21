package oshi.util.platform.mac;

import com.sun.jna.Memory;
import com.sun.jna.Structure;
import com.sun.jna.platform.mac.SystemB;
import com.sun.jna.ptr.IntByReference;
import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class SysctlUtil {
   private static String S;

   private SysctlUtil() {
   }

   public static int sysctl(String name, int def) {
      String s1 = d();
      IntByReference intbyreference = new IntByReference(SystemB.INT_SIZE);
      String s = s1;
      Memory memory = new Memory((long)intbyreference.getValue());
      int i = 0;
      if (s != null) {
         if (0 != SystemB.INSTANCE.sysctlbyname(name, memory, intbyreference, null, 0)) {
            return def;
         }

         i = memory.getInt(0L);
      }

      return i;
   }

   public static long sysctl(String name, long def) {
      IntByReference intbyreference = new IntByReference(SystemB.UINT64_SIZE);
      Memory memory = new Memory((long)intbyreference.getValue());
      return 0 != SystemB.INSTANCE.sysctlbyname(name, memory, intbyreference, null, 0) ? def : memory.getLong(0L);
   }

   public static String sysctl(String name, String def) {
      IntByReference intbyreference = new IntByReference();
      if (0 != SystemB.INSTANCE.sysctlbyname(name, null, intbyreference, null, 0)) {
         return def;
      } else {
         Memory memory = new Memory((long)intbyreference.getValue() + 1L);
         return 0 != SystemB.INSTANCE.sysctlbyname(name, memory, intbyreference, null, 0) ? def : memory.getString(0L);
      }
   }

   public static boolean sysctl(String name, Structure struct) {
      if (0 != SystemB.INSTANCE.sysctlbyname(name, struct.getPointer(), new IntByReference(struct.size()), null, 0)) {
         return false;
      } else {
         struct.read();
         return true;
      }
   }

   public static String d() {
      return S;
   }
}
