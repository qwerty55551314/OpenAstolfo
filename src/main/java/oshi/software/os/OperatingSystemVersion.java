package oshi.software.os;

import oshi.annotation.concurrent.NotThreadSafe;

@Deprecated
@NotThreadSafe
public interface OperatingSystemVersion {
   String getVersion();

   void setVersion(String var1);

   String getCodeName();

   void setCodeName(String var1);

   String getBuildNumber();

   void setBuildNumber(String var1);
}
