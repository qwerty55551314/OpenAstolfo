package oshi.driver.linux.proc;

import java.util.List;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.hardware.CentralProcessor;
import oshi.util.FileUtil;
import oshi.util.ParseUtil;
import oshi.util.platform.linux.ProcPath;

@ThreadSafe
public final class CpuStat {
   private CpuStat() {
   }

   public static long[] getSystemCpuLoadTicks() {
      long[] along = new long[CentralProcessor.TickType.values().length];
      List list = FileUtil.readFile(ProcPath.STAT);
      if (list.isEmpty()) {
         return along;
      } else {
         String s = (String)list.get(0);
         String[] astring = ParseUtil.whitespaces.split(s);
         if (astring.length <= CentralProcessor.TickType.IDLE.getIndex()) {
            return along;
         } else {
            for (int i = 0; i < CentralProcessor.TickType.values().length; i++) {
               along[i] = ParseUtil.parseLongOrDefault(astring[i + 1], 0L);
            }

            return along;
         }
      }
   }

   public static long[][] getProcessorCpuLoadTicks(int logicalProcessorCount) {
      long[][] along = new long[logicalProcessorCount][CentralProcessor.TickType.values().length];
      int i = 0;

      for (String s : FileUtil.readFile(ProcPath.STAT)) {
         boolean flag = s.startsWith("cpu");
         if (flag && !s.startsWith("cpu ")) {
            String[] astring = ParseUtil.whitespaces.split(s);
            int k = astring.length;
            if (k <= CentralProcessor.TickType.IDLE.getIndex()) {
               return along;
            }

            for (int j = 0; j < CentralProcessor.TickType.values().length; j++) {
               along[i][j] = ParseUtil.parseLongOrDefault(astring[j + 1], 0L);
            }

            if (++i >= logicalProcessorCount) {
               break;
            }
         }
      }

      return along;
   }

   public static long getContextSwitches() {
      for (String s : FileUtil.readFile(ProcPath.STAT)) {
         if (s.startsWith("ctxt ")) {
            String[] astring = ParseUtil.whitespaces.split(s);
            if (astring.length == 2) {
               return ParseUtil.parseLongOrDefault(astring[1], 0L);
            }
         }
      }

      return -1L;
   }

   public static long getInterrupts() {
      for (String s : FileUtil.readFile(ProcPath.STAT)) {
         if (s.startsWith("intr ")) {
            String[] astring = ParseUtil.whitespaces.split(s);
            if (astring.length > 2) {
               return ParseUtil.parseLongOrDefault(astring[1], 0L);
            }
         }
      }

      return -1L;
   }

   public static long getBootTime() {
      for (String s : FileUtil.readFile(ProcPath.STAT)) {
         if (s.startsWith("btime")) {
            String[] astring = ParseUtil.whitespaces.split(s);
            return ParseUtil.parseLongOrDefault(astring[1], 0L);
         }
      }

      return 0L;
   }
}
