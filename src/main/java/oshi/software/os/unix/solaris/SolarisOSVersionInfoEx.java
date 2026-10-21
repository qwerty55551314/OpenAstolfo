package oshi.software.os.unix.solaris;

import oshi.software.common.AbstractOSVersionInfoEx;
import oshi.util.ExecutingCommand;
import oshi.util.ParseUtil;

@Deprecated
public class SolarisOSVersionInfoEx extends AbstractOSVersionInfoEx {
   public SolarisOSVersionInfoEx() {
      String s = ExecutingCommand.getFirstAnswer("uname -rv");
      String[] astring = ParseUtil.whitespaces.split(s);
      this.setVersion(astring[0]);
      if (astring.length > 1) {
         this.setBuildNumber(astring[1]);
      }

      this.setCodeName("Solaris");
   }
}
