package oshi.driver.windows.perfmon;

import java.util.Map;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.util.platform.windows.PerfCounterQuery;

@ThreadSafe
public final class PagingFile {
   private static final String L = "Paging File";
   private static final String U = "Win32_PerfRawData_PerfOS_PagingFile";

   private PagingFile() {
   }

   public static Map<PagingFile.PagingPercentProperty, Long> querySwapUsed() {
      PerfCounterQuery perfcounterquery = new PerfCounterQuery<>(PagingFile.PagingPercentProperty.class, "Paging File", "Win32_PerfRawData_PerfOS_PagingFile");
      return perfcounterquery.queryValues();
   }

   public static enum PagingPercentProperty implements PerfCounterQuery.PdhCounterProperty {
      PERCENTUSAGE("_Total", "% Usage");

      private final String instance;
      private final String counter;

      private PagingPercentProperty(String string2, String string3) {
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
