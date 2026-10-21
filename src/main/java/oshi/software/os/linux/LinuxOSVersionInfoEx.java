package oshi.software.os.linux;

import java.io.File;
import java.util.List;
import oshi.software.common.AbstractOSVersionInfoEx;
import oshi.util.ExecutingCommand;
import oshi.util.FileUtil;
import oshi.util.ParseUtil;
import oshi.util.platform.linux.ProcPath;

@Deprecated
public class LinuxOSVersionInfoEx extends AbstractOSVersionInfoEx {
   public LinuxOSVersionInfoEx() {
      this(null, null);
   }

   public LinuxOSVersionInfoEx(String versionId, String codeName) {
      this.setVersion(versionId);
      this.setCodeName(codeName);
      if (this.getVersion() == null) {
         this.setVersionFromReleaseFiles();
      }

      if (this.getCodeName() == null) {
         this.setCodeName("");
      }

      List list = FileUtil.readFile(ProcPath.VERSION);
      if (!list.isEmpty()) {
         String[] astring = ParseUtil.whitespaces.split((CharSequence)list.get(0));

         for (String s : astring) {
            if (!"Linux".equals(s) && !"version".equals(s)) {
               this.setBuildNumber(s);
               return;
            }
         }
      }

      this.setBuildNumber("");
   }

   private void setVersionFromReleaseFiles() {
      boolean flag = this.readDistribRelease("/etc/system-release");
      if (!flag) {
         boolean flag1 = this.readOsRelease();
         if (!flag1) {
            boolean flag2 = this.execLsbRelease();
            if (!flag2) {
               if (!this.readLsbRelease()) {
                  String s = LinuxOperatingSystem.getReleaseFilename();
                  if (!this.readDistribRelease(s)) {
                     if (this.getVersion() == null) {
                        this.setVersion(System.getProperty("os.version"));
                     }
                  }
               }
            }
         }
      }
   }

   private boolean readOsRelease() {
      if (new File("/etc/os-release").exists()) {
         for (String s : FileUtil.readFile("/etc/os-release")) {
            if (s.startsWith("VERSION=")) {
               s = ParseUtil.getDoubleQuoteStringValue(s).trim();
               String[] astring = s.split("[()]");
               if (astring.length <= 1) {
                  astring = s.split(", ");
               }

               if (astring.length > 0) {
                  super.version = astring[0].trim();
               }

               if (astring.length > 1) {
                  super.codeName = astring[1].trim();
               }
            } else if (s.startsWith("VERSION_ID=") && super.version == null) {
               super.version = ParseUtil.getDoubleQuoteStringValue(s).trim();
            }
         }
      }

      return super.version != null;
   }

   private boolean execLsbRelease() {
      for (String s : ExecutingCommand.runNative("lsb_release -a")) {
         if (s.startsWith("Description:")) {
            s = s.replace("Description:", "").trim();
            if (s.contains(" release ")) {
               super.version = this.parseRelease(s, " release ");
            }
         } else if (s.startsWith("Release:") && super.version == null) {
            super.version = s.replace("Release:", "").trim();
         } else if (s.startsWith("Codename:") && super.codeName == null) {
            super.codeName = s.replace("Codename:", "").trim();
         }
      }

      return super.version != null;
   }

   private boolean readLsbRelease() {
      if (new File("/etc/lsb-release").exists()) {
         for (String s : FileUtil.readFile("/etc/lsb-release")) {
            if (s.startsWith("DISTRIB_DESCRIPTION=")) {
               s = ParseUtil.getDoubleQuoteStringValue(s).trim();
               if (s.contains(" release ")) {
                  super.version = this.parseRelease(s, " release ");
               }
            } else if (s.startsWith("DISTRIB_RELEASE=") && super.version == null) {
               super.version = ParseUtil.getDoubleQuoteStringValue(s).trim();
            } else if (s.startsWith("DISTRIB_CODENAME=") && super.codeName == null) {
               super.codeName = ParseUtil.getDoubleQuoteStringValue(s).trim();
            }
         }
      }

      return super.version != null;
   }

   private boolean readDistribRelease(String filename) {
      if (new File(filename).exists()) {
         for (String s : FileUtil.readFile(filename)) {
            if (s.contains(" release ")) {
               super.version = this.parseRelease(s, " release ");
               break;
            }

            if (s.contains(" VERSION ")) {
               super.version = this.parseRelease(s, " VERSION ");
               break;
            }
         }
      }

      return super.version != null;
   }

   private String parseRelease(String line, String splitLine) {
      String[] astring = line.split(splitLine);
      int i = astring.length;
      if (i > 1) {
         astring = astring[1].split("[()]");
         int j = astring.length;
         if (j > 0) {
            super.version = astring[0].trim();
         }

         if (astring.length > 1) {
            super.codeName = astring[1].trim();
         }
      }

      return super.version;
   }
}
