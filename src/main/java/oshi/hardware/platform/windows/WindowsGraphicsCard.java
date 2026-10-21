package oshi.hardware.platform.windows;

import com.sun.jna.platform.win32.VersionHelpers;
import com.sun.jna.platform.win32.COM.WbemcliUtil.WmiResult;
import java.util.ArrayList;
import java.util.List;
import oshi.annotation.concurrent.Immutable;
import oshi.driver.windows.wmi.Win32VideoController;
import oshi.hardware.common.AbstractGraphicsCard;
import oshi.util.ParseUtil;
import oshi.util.Util;
import oshi.util.platform.windows.WmiUtil;
import oshi.util.tuples.Pair;

@Immutable
public final class WindowsGraphicsCard extends AbstractGraphicsCard {
   private static final boolean IS_VISTA_OR_GREATER = VersionHelpers.IsWindowsVistaOrGreater();

   public WindowsGraphicsCard(String name, String deviceId, String vendor, String versionInfo, long vram) {
      super(name, deviceId, vendor, versionInfo, vram);
   }

   public static List<WindowsGraphicsCard> getGraphicsCards() {
      Object object = null;
      ArrayList arraylist = new ArrayList();
      if (IS_VISTA_OR_GREATER) {
         WmiResult wmiresult = Win32VideoController.queryVideoController();

         for (int i = 0; i < wmiresult.getResultCount(); i++) {
            String s = WmiUtil.getString(wmiresult, Win32VideoController.VideoControllerProperty.NAME, i);
            Pair pair = ParseUtil.parsePnPDeviceIdToVendorProductId(WmiUtil.getString(wmiresult, Win32VideoController.VideoControllerProperty.PNPDEVICEID, i));
            String s1 = pair == null ? "unknown" : (String)pair.getB();
            String s2 = WmiUtil.getString(wmiresult, Win32VideoController.VideoControllerProperty.ADAPTERCOMPATIBILITY, i);
            if (pair != null) {
               if (Util.isBlank(s2)) {
                  s1 = (String)pair.getA();
               } else {
                  s2 = s2 + " (" + (String)pair.getA() + ")";
               }
            }

            String s3 = WmiUtil.getString(wmiresult, Win32VideoController.VideoControllerProperty.DRIVERVERSION, i);
            if (!Util.isBlank(s3)) {
               s3 = "DriverVersion=" + s3;
            } else {
               s3 = "unknown";
            }

            long j = WmiUtil.getUint32asLong(wmiresult, Win32VideoController.VideoControllerProperty.ADAPTERRAM, i);
            arraylist.add(new WindowsGraphicsCard(Util.isBlank(s) ? "unknown" : s, s1, Util.isBlank(s2) ? "unknown" : s2, s3, j));
         }
      }

      return arraylist;
   }
}
