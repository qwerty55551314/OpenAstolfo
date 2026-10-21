package oshi.hardware.platform.windows;

import com.sun.jna.platform.win32.COM.WbemcliUtil.WmiResult;
import java.util.function.Supplier;
import oshi.annotation.concurrent.Immutable;
import oshi.driver.windows.wmi.Win32BaseBoard;
import oshi.hardware.common.AbstractBaseboard;
import oshi.util.Memoizer;
import oshi.util.Util;
import oshi.util.platform.windows.WmiUtil;
import oshi.util.tuples.Quartet;

@Immutable
public final class WindowsBaseboard extends AbstractBaseboard {
   private final Supplier<Quartet<String, String, String, String>> manufModelVersSerial = Memoizer.memoize(WindowsBaseboard::queryManufModelVersSerial);

   @Override
   public String getManufacturer() {
      return this.manufModelVersSerial.get().getA();
   }

   @Override
   public String getModel() {
      return this.manufModelVersSerial.get().getB();
   }

   @Override
   public String getVersion() {
      return this.manufModelVersSerial.get().getC();
   }

   @Override
   public String getSerialNumber() {
      return this.manufModelVersSerial.get().getD();
   }

   private static Quartet<String, String, String, String> queryManufModelVersSerial() {
      String s = null;
      String s1 = null;
      String s2 = null;
      String s3 = null;
      WmiResult wmiresult = Win32BaseBoard.queryBaseboardInfo();
      if (wmiresult.getResultCount() > 0) {
         s = WmiUtil.getString(wmiresult, Win32BaseBoard.BaseBoardProperty.MANUFACTURER, 0);
         s1 = WmiUtil.getString(wmiresult, Win32BaseBoard.BaseBoardProperty.MODEL, 0);
         s2 = WmiUtil.getString(wmiresult, Win32BaseBoard.BaseBoardProperty.VERSION, 0);
         s3 = WmiUtil.getString(wmiresult, Win32BaseBoard.BaseBoardProperty.SERIALNUMBER, 0);
      }

      return new Quartet<>(
         Util.isBlank(s) ? "unknown" : s, Util.isBlank(s1) ? "unknown" : s1, Util.isBlank(s2) ? "unknown" : s2, Util.isBlank(s3) ? "unknown" : s3
      );
   }
}
