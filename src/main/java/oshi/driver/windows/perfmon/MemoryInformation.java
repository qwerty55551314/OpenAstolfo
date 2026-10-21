package oshi.driver.windows.perfmon;

import java.util.Map;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.util.platform.windows.PerfCounterQuery;

@ThreadSafe
public final class MemoryInformation {
   private static final String M = "Memory";
   private static final String a = "Win32_PerfRawData_PerfOS_Memory";

   private MemoryInformation() {
   }

   public static Map<MemoryInformation.PageSwapProperty, Long> queryPageSwaps() {
      PerfCounterQuery perfcounterquery = new PerfCounterQuery<>(MemoryInformation.PageSwapProperty.class, "Memory", "Win32_PerfRawData_PerfOS_Memory");
      return perfcounterquery.queryValues();
   }

   public static enum PageSwapProperty implements PerfCounterQuery.PdhCounterProperty {
      PAGESINPUTPERSEC(null, "Pages Input/sec"),
      PAGESOUTPUTPERSEC(null, "Pages Output/sec");

      private final String instance;
      private final String counter;

      private PageSwapProperty(String string2, String string3) {
         this.instance = string2;
         this.counter = string3;
      }

      @Override
      public String getInstance() {
         return this.instance;
      }

      @Override
      public String getCounter() {
         return this.counter;
      }
   }
}
