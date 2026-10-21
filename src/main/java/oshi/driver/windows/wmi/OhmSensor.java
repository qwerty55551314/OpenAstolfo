package oshi.driver.windows.wmi;

import com.sun.jna.platform.win32.COM.WbemcliUtil.WmiQuery;
import com.sun.jna.platform.win32.COM.WbemcliUtil.WmiResult;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.util.platform.windows.WmiQueryHandler;

@ThreadSafe
public final class OhmSensor {
   private OhmSensor() {
   }

   public static WmiResult<OhmSensor.ValueProperty> querySensorValue(String identifier, String sensorType) {
      Object object = null;
      StringBuilder stringbuilder = new StringBuilder("Sensor");
      stringbuilder.append(" WHERE Parent = \"").append(identifier);
      stringbuilder.append("\" AND SensorType=\"").append(sensorType).append('"');
      WmiQuery wmiquery = new WmiQuery<>("ROOT\\OpenHardwareMonitor", stringbuilder.toString(), OhmSensor.ValueProperty.class);
      return WmiQueryHandler.createInstance().queryWMI(wmiquery);
   }

   public static enum ValueProperty {
      VALUE;
   }
}
