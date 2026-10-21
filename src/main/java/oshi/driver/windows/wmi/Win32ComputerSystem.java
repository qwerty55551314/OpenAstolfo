package oshi.driver.windows.wmi;

import com.sun.jna.platform.win32.COM.WbemcliUtil.WmiQuery;
import com.sun.jna.platform.win32.COM.WbemcliUtil.WmiResult;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.util.platform.windows.WmiQueryHandler;

@ThreadSafe
public final class Win32ComputerSystem {
   private static final String k = "Win32_ComputerSystem";

   private Win32ComputerSystem() {
   }

   public static WmiResult<Win32ComputerSystem.ComputerSystemProperty> queryComputerSystem() {
      WmiQuery wmiquery = new WmiQuery<>("Win32_ComputerSystem", Win32ComputerSystem.ComputerSystemProperty.class);
      return WmiQueryHandler.createInstance().queryWMI(wmiquery);
   }

   public static enum ComputerSystemProperty {
      MANUFACTURER,
      MODEL;
   }
}
