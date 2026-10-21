package oshi.hardware.platform.linux;

import java.util.function.Supplier;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.hardware.common.AbstractVirtualMemory;
import oshi.util.FileUtil;
import oshi.util.Memoizer;
import oshi.util.ParseUtil;
import oshi.util.platform.linux.ProcPath;
import oshi.util.tuples.Pair;

@ThreadSafe
public final class LinuxVirtualMemory extends AbstractVirtualMemory {
   private final Supplier<Pair<Long, Long>> usedTotal = Memoizer.memoize(LinuxVirtualMemory::queryMemInfo, Memoizer.defaultExpiration());
   private final Supplier<Pair<Long, Long>> inOut = Memoizer.memoize(LinuxVirtualMemory::queryVmStat, Memoizer.defaultExpiration());

   @Override
   public long getSwapUsed() {
      return this.usedTotal.get().getA();
   }

   @Override
   public long getSwapTotal() {
      return this.usedTotal.get().getB();
   }

   @Override
   public long getSwapPagesIn() {
      return this.inOut.get().getA();
   }

   @Override
   public long getSwapPagesOut() {
      return this.inOut.get().getB();
   }

   private static Pair<Long, Long> queryMemInfo() {
      long i = 0L;
      long j = 0L;

      for (String s : FileUtil.readFile(ProcPath.MEMINFO)) {
         String[] astring = ParseUtil.whitespaces.split(s);
         if (astring.length > 1) {
            String s1 = astring[0];
            int b0 = -1;
            int k = s1.hashCode();
            switch (k) {
               case 709286235:
                  if (s1.equals("SwapFree:")) {
                     b0 = 1;
                  }
                  break;
               case 911518985:
                  if (s1.equals("SwapTotal:")) {
                     b0 = 0;
                  }
            }

            switch (b0) {
               case 0:
                  j = parseMeminfo(astring);
                  break;
               case 1:
                  i = parseMeminfo(astring);
            }
         }
      }

      return new Pair<>(j - i, j);
   }

   private static Pair<Long, Long> queryVmStat() {
      long i = 0L;
      long j = 0L;

      for (String s : FileUtil.readFile(ProcPath.VMSTAT)) {
         String[] astring = ParseUtil.whitespaces.split(s);
         if (astring.length > 1) {
            String s1 = astring[0];
            int b0 = -1;
            int k = s1.hashCode();
            switch (k) {
               case -989940781:
                  if (s1.equals("pgpgin")) {
                     b0 = 0;
                  }
                  break;
               case -623387040:
                  if (s1.equals("pgpgout")) {
                     b0 = 1;
                  }
            }

            switch (b0) {
               case 0:
                  i = ParseUtil.parseLongOrDefault(astring[1], 0L);
                  break;
               case 1:
                  j = ParseUtil.parseLongOrDefault(astring[1], 0L);
            }
         }
      }

      return new Pair<>(i, j);
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
}
