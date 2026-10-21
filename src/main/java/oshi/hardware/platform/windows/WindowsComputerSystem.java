package oshi.hardware.platform.windows;

import com.sun.jna.platform.win32.COM.WbemcliUtil.WmiResult;
import java.util.function.Supplier;
import oshi.annotation.concurrent.Immutable;
import oshi.driver.windows.wmi.Win32Bios;
import oshi.driver.windows.wmi.Win32ComputerSystem;
import oshi.driver.windows.wmi.Win32ComputerSystemProduct;
import oshi.hardware.Baseboard;
import oshi.hardware.Firmware;
import oshi.hardware.common.AbstractComputerSystem;
import oshi.util.Memoizer;
import oshi.util.Util;
import oshi.util.platform.windows.WmiUtil;
import oshi.util.tuples.Pair;

@Immutable
public final class WindowsComputerSystem extends AbstractComputerSystem {
   private final Supplier<Pair<String, String>> manufacturerModel = Memoizer.memoize(WindowsComputerSystem::queryManufacturerModel);
   private final Supplier<String> serialNumber = Memoizer.memoize(WindowsComputerSystem::querySystemSerialNumber);

   @Override
   public String getManufacturer() {
      return this.manufacturerModel.get().getA();
   }

   @Override
   public String getModel() {
      return this.manufacturerModel.get().getB();
   }

   @Override
   public String getSerialNumber() {
      return this.serialNumber.get();
   }

   @Override
   public Firmware createFirmware() {
      return new WindowsFirmware();
   }

   @Override
   public Baseboard createBaseboard() {
      return new WindowsBaseboard();
   }

   private static Pair<String, String> queryManufacturerModel() {
      String s = null;
      String s1 = null;
      WmiResult wmiresult = Win32ComputerSystem.queryComputerSystem();
      if (wmiresult.getResultCount() > 0) {
         s = WmiUtil.getString(wmiresult, Win32ComputerSystem.ComputerSystemProperty.MANUFACTURER, 0);
         s1 = WmiUtil.getString(wmiresult, Win32ComputerSystem.ComputerSystemProperty.MODEL, 0);
      }

      return new Pair<>(Util.isBlank(s) ? "unknown" : s, Util.isBlank(s1) ? "unknown" : s1);
   }

   private static String querySystemSerialNumber() {
      String s;
      String s1 = s = querySerialFromBios();
      return (s1 != null || (s = querySerialFromCsProduct()) != null) && !Util.isBlank(s) ? s : "unknown";
   }

   private static String querySerialFromBios() {
      WmiResult wmiresult = Win32Bios.querySerialNumber();
      return wmiresult.getResultCount() > 0 ? WmiUtil.getString(wmiresult, Win32Bios.BiosSerialProperty.SERIALNUMBER, 0) : null;
   }

   private static String querySerialFromCsProduct() {
      WmiResult wmiresult = Win32ComputerSystemProduct.queryIdentifyingNumber();
      return wmiresult.getResultCount() > 0
         ? WmiUtil.getString(wmiresult, Win32ComputerSystemProduct.ComputerSystemProductProperty.IDENTIFYINGNUMBER, 0)
         : null;
   }
}
