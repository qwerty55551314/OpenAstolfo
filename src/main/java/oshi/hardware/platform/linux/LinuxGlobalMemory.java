package oshi.hardware.platform.linux;

import java.util.function.Supplier;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.hardware.VirtualMemory;
import oshi.hardware.common.AbstractGlobalMemory;
import oshi.util.ExecutingCommand;
import oshi.util.FileUtil;
import oshi.util.Memoizer;
import oshi.util.ParseUtil;
import oshi.util.platform.linux.ProcPath;
import oshi.util.tuples.Pair;

@ThreadSafe
public final class LinuxGlobalMemory extends AbstractGlobalMemory {
   private final Supplier<Pair<Long, Long>> availTotal = Memoizer.memoize(LinuxGlobalMemory::readMemInfo, Memoizer.defaultExpiration());
   private final Supplier<Long> pageSize = Memoizer.memoize(LinuxGlobalMemory::queryPageSize);
   private final Supplier<VirtualMemory> vm = Memoizer.memoize(LinuxGlobalMemory::createVirtualMemory);

   @Override
   public long getAvailable() {
      return this.availTotal.get().getA();
   }

   @Override
   public long getTotal() {
      return this.availTotal.get().getB();
   }

   @Override
   public long getPageSize() {
      return this.pageSize.get();
   }

   @Override
   public VirtualMemory getVirtualMemory() {
      return this.vm.get();
   }

   private static long queryPageSize() {
      return ParseUtil.parseLongOrDefault(ExecutingCommand.getFirstAnswer("getconf PAGE_SIZE"), 4096L);
   }

   private static Pair<Long, Long> readMemInfo() {
      long i = 0L;
      long j = 0L;
      long k = 0L;
      long l = 0L;
      long i1 = 0L;

      for (String s : FileUtil.readFile(ProcPath.MEMINFO)) {
         String[] astring = ParseUtil.whitespaces.split(s);
         if (astring.length > 1) {
            String s1 = astring[0];
            int b0 = -1;
            int k1 = s1.hashCode();
            switch (k1) {
               case -1340290165:
                  if (s1.equals("MemTotal:")) {
                     b0 = 0;
                  }
                  break;
               case -610278759:
                  if (s1.equals("MemFree:")) {
                     b0 = 2;
                  }
                  break;
               case -77960470:
                  if (s1.equals("Inactive(file):")) {
                     b0 = 4;
                  }
                  break;
               case 1453390374:
                  if (s1.equals("MemAvailable:")) {
                     b0 = 1;
                  }
                  break;
               case 1562889674:
                  if (s1.equals("SReclaimable:")) {
                     b0 = 5;
                  }
                  break;
               case 2115742703:
                  if (s1.equals("Active(file):")) {
                     b0 = 3;
                  }
            }

            switch (b0) {
               case 0:
                  i1 = parseMeminfo(astring);
                  break;
               case 1:
                  long j1 = parseMeminfo(astring);
                  return new Pair<>(j1, i1);
               case 2:
                  i = parseMeminfo(astring);
                  break;
               case 3:
                  j = parseMeminfo(astring);
                  break;
               case 4:
                  k = parseMeminfo(astring);
                  break;
               case 5:
                  l = parseMeminfo(astring);
            }
         }
      }

      return new Pair<>(i + j + k + l, i1);
   }

   private static long parseMeminfo(String[] memorySplit) {
      if (memorySplit.length < 2) {
         return 0L;
      } else {
         long i = ParseUtil.parseLongOrDefault(memorySplit[1], 0L);
         int j = memorySplit.length;
         if (j > 2 && "kB".equals(memorySplit[2])) {
            i *= 1024L;
         }

         return i;
      }
   }

   private static VirtualMemory createVirtualMemory() {
      return new LinuxVirtualMemory();
   }
}
