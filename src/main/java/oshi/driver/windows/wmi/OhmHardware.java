package oshi.driver.windows.wmi;

import com.sun.jna.platform.win32.COM.WbemcliUtil.WmiQuery;
import com.sun.jna.platform.win32.COM.WbemcliUtil.WmiResult;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.util.platform.windows.WmiQueryHandler;

@ThreadSafe
public final class OhmHardware {
   private OhmHardware() {
   }

   public static WmiResult<OhmHardware.IdentifierProperty> queryHwIdentifier(String typeToQuery, String typeName) {
      StringBuilder stringbuilder = new StringBuilder("Hardware");
      stringbuilder.append(" WHERE ").append(typeToQuery).append("Type=\"").append(typeName).append('"');
      WmiQuery wmiquery = new WmiQuery<>("ROOT\\OpenHardwareMonitor", stringbuilder.toString(), OhmHardware.IdentifierProperty.class);
      return WmiQueryHandler.createInstance().queryWMI(wmiquery);
   }

   public static enum IdentifierProperty {
      IDENTIFIER;
   }
}
