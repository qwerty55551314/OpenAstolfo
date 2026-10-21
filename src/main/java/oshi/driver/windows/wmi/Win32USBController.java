package oshi.driver.windows.wmi;

import com.sun.jna.platform.win32.COM.WbemcliUtil.WmiQuery;
import com.sun.jna.platform.win32.COM.WbemcliUtil.WmiResult;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.util.platform.windows.WmiQueryHandler;

@ThreadSafe
public final class Win32USBController {
   private static final String y = "Win32_USBController";

   private Win32USBController() {
   }

   public static WmiResult<Win32USBController.USBControllerProperty> queryUSBControllers() {
      WmiQuery wmiquery = new WmiQuery<>("Win32_USBController", Win32USBController.USBControllerProperty.class);
      return WmiQueryHandler.createInstance().queryWMI(wmiquery);
   }

   public static enum USBControllerProperty {
      PNPDEVICEID;
   }
}
