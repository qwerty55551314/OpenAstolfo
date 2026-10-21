package oshi.software.os.windows;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.COM.WbemcliUtil.WmiResult;
import java.util.ArrayList;
import oshi.driver.windows.wmi.Win32OperatingSystem;
import oshi.software.common.AbstractOSVersionInfoEx;
import oshi.util.ParseUtil;
import oshi.util.platform.windows.WmiUtil;

@Deprecated
public class WindowsOSVersionInfoEx extends AbstractOSVersionInfoEx {
   public WindowsOSVersionInfoEx() {
      WmiResult wmiresult = Win32OperatingSystem.queryOsVersion();
      if (wmiresult.getResultCount() < 1) {
         this.handleNoVersionInfo();
      } else {
         int i = WmiUtil.getUint32(wmiresult, Win32OperatingSystem.OSVersionProperty.SUITEMASK, 0);
         this.setBuildNumber(WmiUtil.getString(wmiresult, Win32OperatingSystem.OSVersionProperty.BUILDNUMBER, 0));
         this.setVersion(this.parseVersion(wmiresult, i));
         this.setCodeName(this.parseCodeName(i));
      }
   }

   public void handleNoVersionInfo() {
      this.setVersion(System.getProperty("os.version"));
      this.setCodeName("");
      this.setBuildNumber("");
   }

   private String parseVersion(WmiResult<Win32OperatingSystem.OSVersionProperty> versionInfo, int suiteMask) {
      String s = System.getProperty("os.version");
      String[] astring = WmiUtil.getString(versionInfo, Win32OperatingSystem.OSVersionProperty.VERSION, 0).split("\\D");
      int k = astring.length;
      int i = k > 0 ? ParseUtil.parseIntOrDefault(astring[0], 0) : 0;
      int l = astring.length;
      int j = l > 1 ? ParseUtil.parseIntOrDefault(astring[1], 0) : 0;
      int i1 = WmiUtil.getUint32(versionInfo, Win32OperatingSystem.OSVersionProperty.PRODUCTTYPE, 0);
      boolean flag = i1 == 1;
      switch (i) {
         case 5:
            if (j == 2) {
               if ((suiteMask & 32768) != 0) {
                  s = "Home Server";
               } else if (flag) {
                  s = "XP";
               } else {
                  s = User32.INSTANCE.GetSystemMetrics(89) != 0 ? "Server 2003" : "Server 2003 R2";
               }
            } else if (j == 1) {
               s = "XP";
            } else if (j == 0) {
               s = "2000";
            }
            break;
         case 6:
            if (j == 3) {
               s = flag ? "8.1" : "Server 2012 R2";
            } else if (j == 2) {
               s = flag ? "8" : "Server 2012";
            } else if (j == 1) {
               s = flag ? "7" : "Server 2008 R2";
            } else if (j == 0) {
               s = flag ? "Vista" : "Server 2008";
            }
            break;
         case 10:
            if (j == 0) {
               if (flag) {
                  s = "10";
               } else {
                  s = ParseUtil.parseLongOrDefault(this.getBuildNumber(), 0L) > 17762L ? "Server 2019" : "Server 2016";
               }
            }
      }

      String s1 = WmiUtil.getString(versionInfo, Win32OperatingSystem.OSVersionProperty.CSDVERSION, 0);
      if (!s1.isEmpty() && !"unknown".equals(s1)) {
         s = s + " " + s1.replace("Service Pack ", "SP");
      }

      return s;
   }

   private String parseCodeName(int suiteMask) {
      Object object = null;
      ArrayList arraylist = new ArrayList();
      int i = suiteMask & 2;
      if (i != 0) {
         arraylist.add("Enterprise");
      }

      int j = suiteMask & 4;
      if (j != 0) {
         arraylist.add("BackOffice");
      }

      int k = suiteMask & 8;
      if (k != 0) {
         arraylist.add("Communication Server");
      }

      int l = suiteMask & 128;
      if (l != 0) {
         arraylist.add("Datacenter");
      }

      int i1 = suiteMask & 512;
      if (i1 != 0) {
         arraylist.add("Home");
      }

      int j1 = suiteMask & 1024;
      if (j1 != 0) {
         arraylist.add("Web Server");
      }

      int k1 = suiteMask & 8192;
      if (k1 != 0) {
         arraylist.add("Storage Server");
      }

      int l1 = suiteMask & 16384;
      if (l1 != 0) {
         arraylist.add("Compute Cluster");
      }

      return String.join(",", arraylist);
   }
}
