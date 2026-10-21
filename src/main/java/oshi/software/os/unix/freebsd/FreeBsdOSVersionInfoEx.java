package oshi.software.os.unix.freebsd;

import oshi.software.common.AbstractOSVersionInfoEx;
import oshi.util.platform.unix.freebsd.BsdSysctlUtil;

@Deprecated
public class FreeBsdOSVersionInfoEx extends AbstractOSVersionInfoEx {
   public FreeBsdOSVersionInfoEx() {
      this.setVersion(BsdSysctlUtil.sysctl("kern.osrelease", ""));
      String s = BsdSysctlUtil.sysctl("kern.version", "");
      String s1 = BsdSysctlUtil.sysctl("kern.ostype", "FreeBSD");
      this.setBuildNumber(s.split(":")[0].replace(s1, "").replace(this.getVersion(), "").trim());
      this.setCodeName("");
   }
}
