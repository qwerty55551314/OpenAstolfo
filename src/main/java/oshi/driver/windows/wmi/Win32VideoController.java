package oshi.driver.windows.wmi;

import com.sun.jna.platform.win32.COM.WbemcliUtil.WmiQuery;
import com.sun.jna.platform.win32.COM.WbemcliUtil.WmiResult;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.util.platform.windows.WmiQueryHandler;

@ThreadSafe
public final class Win32VideoController {
   private static final String T = "Win32_VideoController";

   private Win32VideoController() {
   }

   public static WmiResult<Win32VideoController.VideoControllerProperty> queryVideoController() {
      WmiQuery wmiquery = new WmiQuery<>("Win32_VideoController", Win32VideoController.VideoControllerProperty.class);
      return WmiQueryHandler.createInstance().queryWMI(wmiquery);
   }

   public static enum VideoControllerProperty {
      ADAPTERCOMPATIBILITY,
      ADAPTERRAM,
      DRIVERVERSION,
      NAME,
      PNPDEVICEID;
   }
}
