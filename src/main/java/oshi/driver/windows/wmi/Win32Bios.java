package oshi.driver.windows.wmi;

import com.sun.jna.platform.win32.COM.WbemcliUtil.WmiQuery;
import com.sun.jna.platform.win32.COM.WbemcliUtil.WmiResult;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.util.platform.windows.WmiQueryHandler;

@ThreadSafe
public final class Win32Bios {
   private static final String i = "Win32_BIOS where PrimaryBIOS=true";

   private Win32Bios() {
   }

   public static WmiResult<Win32Bios.BiosSerialProperty> querySerialNumber() {
      WmiQuery wmiquery = new WmiQuery<>("Win32_BIOS where PrimaryBIOS=true", Win32Bios.BiosSerialProperty.class);
      return WmiQueryHandler.createInstance().queryWMI(wmiquery);
   }

   public static WmiResult<Win32Bios.BiosProperty> queryBiosInfo() {
      WmiQuery wmiquery = new WmiQuery<>("Win32_BIOS where PrimaryBIOS=true", Win32Bios.BiosProperty.class);
      return WmiQueryHandler.createInstance().queryWMI(wmiquery);
   }

   public static enum BiosProperty {
      MANUFACTURER,
      NAME,
      DESCRIPTION,
      VERSION,
      RELEASEDATE;
   }

   public static enum BiosSerialProperty {
      SERIALNUMBER;
   }
}
