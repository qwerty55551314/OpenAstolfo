package oshi.hardware.platform.unix.solaris;

import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.hardware.common.AbstractVirtualMemory;
import oshi.util.ExecutingCommand;
import oshi.util.Memoizer;
import oshi.util.ParseUtil;
import oshi.util.tuples.Pair;

@ThreadSafe
public final class SolarisVirtualMemory extends AbstractVirtualMemory {
   private static final Pattern SWAP_INFO = Pattern.compile(".+\\s(\\d+)K\\s+(\\d+)K$");
   private final Supplier<Pair<Long, Long>> usedTotal = Memoizer.memoize(SolarisVirtualMemory::querySwapInfo, Memoizer.defaultExpiration());
   private final Supplier<Long> pagesIn = Memoizer.memoize(SolarisVirtualMemory::queryPagesIn, Memoizer.defaultExpiration());
   private final Supplier<Long> pagesOut = Memoizer.memoize(SolarisVirtualMemory::queryPagesOut, Memoizer.defaultExpiration());

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
      return this.pagesIn.get();
   }

   @Override
   public long getSwapPagesOut() {
      return this.pagesOut.get();
   }

   private static long queryPagesIn() {
      long i = 0L;

      for (String s : ExecutingCommand.runNative("kstat -p cpu_stat:::pgpgin")) {
         i += ParseUtil.parseLastLong(s, 0L);
      }

      return i;
   }

   private static long queryPagesOut() {
      long i = 0L;

      for (String s : ExecutingCommand.runNative("kstat -p cpu_stat:::pgpgout")) {
         i += ParseUtil.parseLastLong(s, 0L);
      }

      return i;
   }

   private static Pair<Long, Long> querySwapInfo() {
      long i = 0L;
      long j = 0L;
      String s = ExecutingCommand.getAnswerAt("swap -lk", 1);
      Matcher matcher = SWAP_INFO.matcher(s);
      if (matcher.matches()) {
         i = ParseUtil.parseLongOrDefault(matcher.group(1), 0L) << 10;
         j = i - (ParseUtil.parseLongOrDefault(matcher.group(2), 0L) << 10);
      }

      return new Pair<>(j, i);
   }
}
