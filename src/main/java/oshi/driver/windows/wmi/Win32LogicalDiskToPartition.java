package oshi.driver.windows.wmi;

import com.sun.jna.platform.win32.COM.WbemcliUtil.WmiQuery;
import com.sun.jna.platform.win32.COM.WbemcliUtil.WmiResult;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.util.platform.windows.WmiQueryHandler;

@ThreadSafe
public final class Win32LogicalDiskToPartition {
   private static final String N = "Win32_LogicalDiskToPartition";

   private Win32LogicalDiskToPartition() {
   }

   public static WmiResult<Win32LogicalDiskToPartition.DiskToPartitionProperty> queryDiskToPartition() {
      WmiQuery wmiquery = new WmiQuery<>("Win32_LogicalDiskToPartition", Win32LogicalDiskToPartition.DiskToPartitionProperty.class);
      return WmiQueryHandler.createInstance().queryWMI(wmiquery);
   }

   public static enum DiskToPartitionProperty {
      ANTECEDENT,
      DEPENDENT;
   }
}
