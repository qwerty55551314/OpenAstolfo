package oshi.hardware.platform.mac;

import com.sun.jna.platform.mac.SystemB;
import com.sun.jna.platform.mac.SystemB.VMStatistics;
import com.sun.jna.platform.mac.SystemB.XswUsage;
import com.sun.jna.ptr.IntByReference;
import java.util.function.Supplier;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.hardware.common.AbstractVirtualMemory;
import oshi.util.Memoizer;
import oshi.util.ParseUtil;
import oshi.util.platform.mac.SysctlUtil;
import oshi.util.tuples.Pair;

@ThreadSafe
public final class MacVirtualMemory extends AbstractVirtualMemory {
   private final Supplier<Pair<Long, Long>> usedTotal = Memoizer.memoize(MacVirtualMemory::querySwapUsage, Memoizer.defaultExpiration());
   private final Supplier<Pair<Long, Long>> inOut = Memoizer.memoize(MacVirtualMemory::queryVmStat, Memoizer.defaultExpiration());
   private static final String b = "vm.swapusage";

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

   private static Pair<Long, Long> querySwapUsage() {
      long i = 0L;
      long j = 0L;
      XswUsage xswusage = new XswUsage();
      if (SysctlUtil.sysctl(b, xswusage)) {
         i = xswusage.xsu_used;
         j = xswusage.xsu_total;
      }

      return new Pair<>(i, j);
   }

   private static Pair<Long, Long> queryVmStat() {
      long i = 0L;
      long j = 0L;
      VMStatistics vmstatistics = new VMStatistics();
      if (0 == SystemB.INSTANCE.host_statistics(SystemB.INSTANCE.mach_host_self(), 2, vmstatistics, new IntByReference(vmstatistics.size() / SystemB.INT_SIZE))
         )
       {
         i = ParseUtil.unsignedIntToLong(vmstatistics.pageins);
         j = ParseUtil.unsignedIntToLong(vmstatistics.pageouts);
      }

      return new Pair<>(i, j);
   }
}
