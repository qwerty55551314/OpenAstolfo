package oshi.hardware.platform.windows;

import com.sun.jna.platform.win32.COM.WbemcliUtil.WmiResult;
import java.util.function.Supplier;
import oshi.annotation.concurrent.Immutable;
import oshi.driver.windows.wmi.Win32Bios;
import oshi.hardware.common.AbstractFirmware;
import oshi.util.Memoizer;
import oshi.util.Util;
import oshi.util.platform.windows.WmiUtil;
import oshi.util.tuples.Quintet;

@Immutable
public final class WindowsFirmware extends AbstractFirmware {
   private final Supplier<Quintet<String, String, String, String, String>> manufNameDescVersRelease = Memoizer.memoize(
      WindowsFirmware::queryManufNameDescVersRelease
   );

   @Override
   public String getManufacturer() {
      return this.manufNameDescVersRelease.get().y();
   }

   @Override
   public String getName() {
      return this.manufNameDescVersRelease.get().getB();
   }

   @Override
   public String getDescription() {
      return this.manufNameDescVersRelease.get().getC();
   }

   @Override
   public String getVersion() {
      return this.manufNameDescVersRelease.get().getD();
   }

   @Override
   public String getReleaseDate() {
      return this.manufNameDescVersRelease.get().getE();
   }

   private static Quintet<String, String, String, String, String> queryManufNameDescVersRelease() {
      String s = null;
      String s1 = null;
      String s2 = null;
      String s3 = null;
      String s4 = null;
      WmiResult wmiresult = Win32Bios.queryBiosInfo();
      if (wmiresult.getResultCount() > 0) {
         s = WmiUtil.getString(wmiresult, Win32Bios.BiosProperty.MANUFACTURER, 0);
         s1 = WmiUtil.getString(wmiresult, Win32Bios.BiosProperty.NAME, 0);
         s2 = WmiUtil.getString(wmiresult, Win32Bios.BiosProperty.DESCRIPTION, 0);
         s3 = WmiUtil.getString(wmiresult, Win32Bios.BiosProperty.VERSION, 0);
         s4 = WmiUtil.getDateString(wmiresult, Win32Bios.BiosProperty.RELEASEDATE, 0);
      }

      return new Quintet<>(
         Util.isBlank(s) ? "unknown" : s,
         Util.isBlank(s1) ? "unknown" : s1,
         Util.isBlank(s2) ? "unknown" : s2,
         Util.isBlank(s3) ? "unknown" : s3,
         Util.isBlank(s4) ? "unknown" : s4
      );
   }
}
