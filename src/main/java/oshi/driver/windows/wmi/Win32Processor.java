package oshi.driver.windows.wmi;

import com.sun.jna.platform.win32.COM.WbemcliUtil.WmiQuery;
import com.sun.jna.platform.win32.COM.WbemcliUtil.WmiResult;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.util.platform.windows.WmiQueryHandler;

@ThreadSafe
public final class Win32Processor {
   private static final String P = "Win32_Processor";

   private Win32Processor() {
   }

   public static WmiResult<Win32Processor.VoltProperty> queryVoltage() {
      WmiQuery wmiquery = new WmiQuery<>("Win32_Processor", Win32Processor.VoltProperty.class);
      return WmiQueryHandler.createInstance().queryWMI(wmiquery);
   }

   public static WmiResult<Win32Processor.ProcessorIdProperty> queryProcessorId() {
      WmiQuery wmiquery = new WmiQuery<>("Win32_Processor", Win32Processor.ProcessorIdProperty.class);
      return WmiQueryHandler.createInstance().queryWMI(wmiquery);
   }

   public static WmiResult<Win32Processor.BitnessProperty> queryBitness() {
      WmiQuery wmiquery = new WmiQuery<>("Win32_Processor", Win32Processor.BitnessProperty.class);
      return WmiQueryHandler.createInstance().queryWMI(wmiquery);
   }

   public static enum BitnessProperty {
      ADDRESSWIDTH;
   }

   public static enum ProcessorIdProperty {
      PROCESSORID;
   }

   public static enum VoltProperty {
      CURRENTVOLTAGE,
      VOLTAGECAPS;
   }
}
