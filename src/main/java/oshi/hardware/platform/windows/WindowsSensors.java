package oshi.hardware.platform.windows;

import com.sun.jna.platform.win32.COM.WbemcliUtil.WmiResult;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.driver.windows.wmi.MSAcpiThermalZoneTemperature;
import oshi.driver.windows.wmi.OhmHardware;
import oshi.driver.windows.wmi.OhmSensor;
import oshi.driver.windows.wmi.Win32Fan;
import oshi.driver.windows.wmi.Win32Processor;
import oshi.hardware.common.AbstractSensors;
import oshi.util.platform.windows.WmiUtil;

@ThreadSafe
public final class WindowsSensors extends AbstractSensors {
   @Override
   public double queryCpuTemperature() {
      double d0 = getTempFromOHM();
      return d0 > 0.0 ? d0 : getTempFromWMI();
   }

   private static double getTempFromOHM() {
      WmiResult wmiresult = OhmHardware.queryHwIdentifier("Hardware", "CPU");
      if (wmiresult.getResultCount() > 0) {
         String s = WmiUtil.getString(wmiresult, OhmHardware.IdentifierProperty.IDENTIFIER, 0);
         if (s.length() > 0) {
            WmiResult wmiresult1 = OhmSensor.querySensorValue(s, "Temperature");
            if (wmiresult1.getResultCount() > 0) {
               double d0 = 0.0;

               for (int i = 0; i < wmiresult1.getResultCount(); i++) {
                  double d1 = d0 + (double)WmiUtil.getFloat(wmiresult1, OhmSensor.ValueProperty.VALUE, i);
                  d0 = d1;
               }

               return d0 / (double)wmiresult1.getResultCount();
            }
         }
      }

      return 0.0;
   }

   private static double getTempFromWMI() {
      double d0 = 0.0;
      long i = 0L;
      WmiResult wmiresult = MSAcpiThermalZoneTemperature.queryCurrentTemperature();
      int j = wmiresult.getResultCount();
      if (j > 0) {
         i = WmiUtil.getUint32asLong(wmiresult, MSAcpiThermalZoneTemperature.TemperatureProperty.CURRENTTEMPERATURE, 0);
      }

      long i1;
      int k = (i1 = i - 2732L) == 0L ? 0 : (i1 < 0L ? -1 : 1);
      if (k > 0) {
         d0 = (double)i / 10.0 - 273.15;
      } else {
         long j1;
         int l = (j1 = i - 274L) == 0L ? 0 : (j1 < 0L ? -1 : 1);
         if (l > 0) {
            d0 = (double)i - 273.0;
         }
      }

      return d0 < 0.0 ? 0.0 : d0;
   }

   @Override
   public int[] queryFanSpeeds() {
      int[] aint = getFansFromOHM();
      int i = aint.length;
      if (i > 0) {
         return aint;
      } else {
         aint = getFansFromWMI();
         int j = aint.length;
         return j > 0 ? aint : new int[0];
      }
   }

   private static int[] getFansFromOHM() {
      WmiResult wmiresult = OhmHardware.queryHwIdentifier("Hardware", "CPU");
      if (wmiresult.getResultCount() > 0) {
         String s = WmiUtil.getString(wmiresult, OhmHardware.IdentifierProperty.IDENTIFIER, 0);
         if (s.length() > 0) {
            WmiResult wmiresult1 = OhmSensor.querySensorValue(s, "Fan");
            if (wmiresult1.getResultCount() > 0) {
               int[] aint = new int[wmiresult1.getResultCount()];

               for (int i = 0; i < wmiresult1.getResultCount(); i++) {
                  aint[i] = (int)WmiUtil.getFloat(wmiresult1, OhmSensor.ValueProperty.VALUE, i);
               }

               return aint;
            }
         }
      }

      return new int[0];
   }

   private static int[] getFansFromWMI() {
      WmiResult wmiresult = Win32Fan.querySpeed();
      if (wmiresult.getResultCount() <= 1) {
         return new int[0];
      } else {
         int[] aint = new int[wmiresult.getResultCount()];

         for (int i = 0; i < wmiresult.getResultCount(); i++) {
            aint[i] = (int)WmiUtil.getUint64(wmiresult, Win32Fan.SpeedProperty.DESIREDSPEED, i);
         }

         return aint;
      }
   }

   @Override
   public double queryCpuVoltage() {
      double d0 = getVoltsFromOHM();
      return d0 > 0.0 ? d0 : getVoltsFromWMI();
   }

   private static double getVoltsFromOHM() {
      WmiResult wmiresult = OhmHardware.queryHwIdentifier("Sensor", "Voltage");
      if (wmiresult.getResultCount() > 0) {
         String s = null;

         for (int i = 0; i < wmiresult.getResultCount(); i++) {
            String s1 = WmiUtil.getString(wmiresult, OhmHardware.IdentifierProperty.IDENTIFIER, i);
            if (s1.toLowerCase().contains("cpu")) {
               s = s1;
               break;
            }
         }

         if (s == null) {
            s = WmiUtil.getString(wmiresult, OhmHardware.IdentifierProperty.IDENTIFIER, 0);
         }

         WmiResult wmiresult1 = OhmSensor.querySensorValue(s, "Voltage");
         if (wmiresult1.getResultCount() > 0) {
            return (double)WmiUtil.getFloat(wmiresult1, OhmSensor.ValueProperty.VALUE, 0);
         }
      }

      return 0.0;
   }

   private static double getVoltsFromWMI() {
      WmiResult wmiresult = Win32Processor.queryVoltage();
      int j = wmiresult.getResultCount();
      if (j > 1) {
         int i = WmiUtil.getUint16(wmiresult, Win32Processor.VoltProperty.CURRENTVOLTAGE, 0);
         if (i > 0) {
            int k = i & 128;
            if (k != 0) {
               return (double)(i & 127) / 10.0;
            }

            i = WmiUtil.getUint32(wmiresult, Win32Processor.VoltProperty.VOLTAGECAPS, 0);
            if ((i & 1) > 0) {
               return 5.0;
            }

            if ((i & 2) > 0) {
               return 3.3;
            }

            if ((i & 4) > 0) {
               return 2.9;
            }
         }
      }

      return 0.0;
   }
}
