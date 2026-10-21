package oshi.hardware.platform.mac;

import com.sun.jna.platform.mac.IOKit.IOConnect;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.hardware.common.AbstractSensors;
import oshi.util.platform.mac.SmcUtil;

@ThreadSafe
public final class MacSensors extends AbstractSensors {
   private int numFans = 0;

   @Override
   public double queryCpuTemperature() {
      IOConnect ioconnect = SmcUtil.smcOpen();
      double d0 = SmcUtil.smcGetFloat(ioconnect, "TC0P");
      SmcUtil.smcClose(ioconnect);
      return d0 > 0.0 ? d0 : 0.0;
   }

   @Override
   public int[] queryFanSpeeds() {
      IOConnect ioconnect = SmcUtil.smcOpen();
      if (this.numFans == 0) {
         this.numFans = (int)SmcUtil.smcGetLong(ioconnect, "FNum");
      }

      int[] aint = new int[this.numFans];

      for (int i = 0; i < this.numFans; i++) {
         aint[i] = (int)SmcUtil.smcGetFloat(ioconnect, String.format("F%dAc", i));
      }

      SmcUtil.smcClose(ioconnect);
      return aint;
   }

   @Override
   public double queryCpuVoltage() {
      IOConnect ioconnect = SmcUtil.smcOpen();
      double d0 = SmcUtil.smcGetFloat(ioconnect, "VC0C") / 1000.0;
      SmcUtil.smcClose(ioconnect);
      return d0;
   }
}
