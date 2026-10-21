package oshi.util.platform.windows;

import com.sun.jna.platform.win32.Pdh;
import com.sun.jna.platform.win32.VersionHelpers;
import com.sun.jna.platform.win32.BaseTSD.DWORD_PTR;
import com.sun.jna.platform.win32.Pdh.PDH_RAW_COUNTER;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinDef.DWORDByReference;
import com.sun.jna.platform.win32.WinDef.LONGLONGByReference;
import com.sun.jna.platform.win32.WinNT.HANDLEByReference;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.util.ParseUtil;
import oshi.util.Util;

@ThreadSafe
public final class PerfDataUtil {
   private static final DWORD_PTR PZERO = new DWORD_PTR(0L);
   private static final DWORDByReference PDH_FMT_RAW = new DWORDByReference(new DWORD(16L));
   private static final Pdh PDH = Pdh.INSTANCE;
   private static final boolean IS_VISTA_OR_GREATER = VersionHelpers.IsWindowsVistaOrGreater();
   private static final long b = 2723385511241582549L;

   private PerfDataUtil() {
   }

   public static PerfDataUtil.PerfCounter createCounter(String object, String instance, String counter) {
      return new PerfDataUtil.PerfCounter(object, instance, counter);
   }

   public static long updateQueryTimestamp(HANDLEByReference query) {
      int b0 = 85;
      LONGLONGByReference longlongbyreference = new LONGLONGByReference();
      boolean flag = IS_VISTA_OR_GREATER;
      int i = flag ? PDH.PdhCollectQueryDataWithTime(query.getValue(), longlongbyreference) : PDH.PdhCollectQueryData(query.getValue());

      for (int j = 0;
         i == (int)b;
         i = IS_VISTA_OR_GREATER ? PDH.PdhCollectQueryDataWithTime(query.getValue(), longlongbyreference) : PDH.PdhCollectQueryData(query.getValue())
      ) {
         int k = j++;
         if (k >= 3) {
            break;
         }

         Util.sleep((long)(1 << j));
      }

      if (i != 0) {
         return 0L;
      } else {
         return IS_VISTA_OR_GREATER ? ParseUtil.filetimeToUtcMs(longlongbyreference.getValue().longValue(), true) : System.currentTimeMillis();
      }
   }

   public static boolean openQuery(HANDLEByReference q) {
      int i = PDH.PdhOpenQuery(null, PZERO, q);
      return i == 0;
   }

   public static boolean closeQuery(HANDLEByReference q) {
      return 0 == PDH.PdhCloseQuery(q.getValue());
   }

   public static long queryCounter(HANDLEByReference counter) {
      int b0 = 85;
      PDH_RAW_COUNTER pdh_raw_counter = new PDH_RAW_COUNTER();
      int i = PDH.PdhGetRawCounterValue(counter.getValue(), PDH_FMT_RAW, pdh_raw_counter);
      return i != 0 ? (long)i : pdh_raw_counter.FirstValue;
   }

   public static boolean addCounter(HANDLEByReference query, String path, HANDLEByReference p) {
      boolean flag = IS_VISTA_OR_GREATER;
      int i = flag ? PDH.PdhAddEnglishCounter(query.getValue(), path, PZERO, p) : PDH.PdhAddCounter(query.getValue(), path, PZERO, p);
      return i == 0;
   }

   public static boolean removeCounter(HANDLEByReference p) {
      return 0 == PDH.PdhRemoveCounter(p.getValue());
   }

   public static class PerfCounter {
      private String object;
      private String instance;
      private String counter;

      public PerfCounter(String objectName, String instanceName, String counterName) {
         this.object = objectName;
         this.instance = instanceName;
         this.counter = counterName;
      }

      public String getObject() {
         return this.object;
      }

      public String getInstance() {
         return this.instance;
      }

      public String getCounter() {
         return this.counter;
      }

      public String getCounterPath() {
         int b0 = 85;
         StringBuilder stringbuilder = new StringBuilder();
         stringbuilder.append('\\').append(this.object);
         if (this.instance != null) {
            stringbuilder.append('(').append(this.instance).append(')');
         }

         stringbuilder.append('\\').append(this.counter);
         return stringbuilder.toString();
      }
   }
}
