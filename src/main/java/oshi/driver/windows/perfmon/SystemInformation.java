package oshi.driver.windows.perfmon;

import java.util.Map;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.util.platform.windows.PerfCounterQuery;

@ThreadSafe
public final class SystemInformation {
   private static final String j = "System";
   private static final String v = "Win32_PerfRawData_PerfOS_System";

   private SystemInformation() {
   }

   public static Map<SystemInformation.ContextSwitchProperty, Long> queryContextSwitchCounters() {
      PerfCounterQuery perfcounterquery = new PerfCounterQuery<>(SystemInformation.ContextSwitchProperty.class, "System", "Win32_PerfRawData_PerfOS_System");
      return perfcounterquery.queryValues();
   }

   public static enum ContextSwitchProperty implements PerfCounterQuery.PdhCounterProperty {
      CONTEXTSWITCHESPERSEC(null, "Context Switches/sec");

      private final String instance;
      private final String counter;

      private ContextSwitchProperty(String string2, String string3) {
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
