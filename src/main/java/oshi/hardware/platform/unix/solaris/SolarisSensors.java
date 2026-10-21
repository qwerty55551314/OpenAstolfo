package oshi.hardware.platform.unix.solaris;

import java.util.ArrayList;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.hardware.common.AbstractSensors;
import oshi.util.ExecutingCommand;
import oshi.util.ParseUtil;

@ThreadSafe
public final class SolarisSensors extends AbstractSensors {
   @Override
   public double queryCpuTemperature() {
      double d0 = 0.0;

      for (String s : ExecutingCommand.runNative("/usr/sbin/prtpicl -v -c temperature-sensor")) {
         boolean flag = s.trim().startsWith("Temperature:");
         if (flag) {
            int i = ParseUtil.parseLastInt(s, 0);
            if ((double)i > d0) {
               d0 = (double)i;
            }
         }
      }

      if (d0 > 1000.0) {
         d0 /= 1000.0;
      }

      return d0;
   }

   @Override
   public int[] queryFanSpeeds() {
      ArrayList arraylist = new ArrayList();

      for (String s : ExecutingCommand.runNative("/usr/sbin/prtpicl -v -c fan")) {
         boolean flag = s.trim().startsWith("Speed:");
         if (flag) {
            arraylist.add(ParseUtil.parseLastInt(s, 0));
         }
      }

      int[] aint = new int[arraylist.size()];

      for (int i = 0; i < arraylist.size(); i++) {
         aint[i] = (Integer)arraylist.get(i);
      }

      return aint;
   }

   @Override
   public double queryCpuVoltage() {
      double d0 = 0.0;

      for (String s : ExecutingCommand.runNative("/usr/sbin/prtpicl -v -c voltage-sensor")) {
         if (s.trim().startsWith("Voltage:")) {
            d0 = ParseUtil.parseDoubleOrDefault(s.replace("Voltage:", "").trim(), 0.0);
            break;
         }
      }

      return d0;
   }
}
