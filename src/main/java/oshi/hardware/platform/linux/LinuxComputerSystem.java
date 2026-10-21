package oshi.hardware.platform.linux;

import java.util.function.Supplier;
import oshi.annotation.concurrent.Immutable;
import oshi.hardware.Baseboard;
import oshi.hardware.Firmware;
import oshi.hardware.common.AbstractComputerSystem;
import oshi.util.ExecutingCommand;
import oshi.util.FileUtil;
import oshi.util.Memoizer;
import oshi.util.ParseUtil;
import oshi.util.platform.linux.ProcPath;

@Immutable
public final class LinuxComputerSystem extends AbstractComputerSystem {
   private final Supplier<String> manufacturer = Memoizer.memoize(LinuxComputerSystem::y);
   private final Supplier<String> model = Memoizer.memoize(LinuxComputerSystem::queryModel);
   private final Supplier<String> serialNumber = Memoizer.memoize(LinuxComputerSystem::querySerialNumber);

   @Override
   public String getManufacturer() {
      return this.manufacturer.get();
   }

   @Override
   public String getModel() {
      return this.model.get();
   }

   @Override
   public String getSerialNumber() {
      return this.serialNumber.get();
   }

   @Override
   public Firmware createFirmware() {
      return new LinuxFirmware();
   }

   @Override
   public Baseboard createBaseboard() {
      return new LinuxBaseboard();
   }

   private static String y() {
      String s;
      return (s = queryManufacturerFromSysfs()) == null && (s = queryManufacturerFromProcCpu()) == null ? "unknown" : s;
   }

   private static String queryModel() {
      String s;
      return (s = queryModelFromSysfs()) == null && (s = queryModelFromDeviceTree()) == null && (s = queryModelFromLshw()) == null ? "unknown" : s;
   }

   private static String querySerialNumber() {
      String s;
      return (s = querySerialFromSysfs()) == null
            && (s = querySerialFromDmiDecode()) == null
            && (s = querySerialFromLshal()) == null
            && (s = querySerialFromLshw()) == null
         ? "unknown"
         : s;
   }

   private static String queryManufacturerFromSysfs() {
      String s = FileUtil.getStringFromFile("/sys/devices/virtual/dmi/id/sys_vendor").trim();
      return !s.isEmpty() ? s : null;
   }

   private static String queryManufacturerFromProcCpu() {
      for (String s : FileUtil.readFile(ProcPath.CPUINFO)) {
         if (s.startsWith("CPU implementer")) {
            int i = ParseUtil.parseLastInt(s, 0);
            switch (i) {
               case 65:
                  return "ARM";
               case 66:
                  return "Broadcom";
               case 67:
                  return "Cavium";
               case 68:
                  return "DEC";
               case 69:
               case 70:
               case 71:
               case 72:
               case 73:
               case 74:
               case 75:
               case 76:
               case 77:
               case 79:
               case 82:
               case 84:
               case 85:
               case 87:
               case 88:
               case 89:
               case 90:
               case 91:
               case 92:
               case 93:
               case 94:
               case 95:
               case 96:
               case 97:
               case 98:
               case 99:
               case 100:
               case 101:
               case 103:
               case 104:
               default:
                  return null;
               case 78:
                  return "Nvidia";
               case 80:
                  return "APM";
               case 81:
                  return "Qualcomm";
               case 83:
                  return "Samsung";
               case 86:
                  return "Marvell";
               case 102:
                  return "Faraday";
               case 105:
                  return "Intel";
            }
         }
      }

      return null;
   }

   private static String queryModelFromSysfs() {
      String s = FileUtil.getStringFromFile("/sys/devices/virtual/dmi/id/product_name").trim();
      String s1 = FileUtil.getStringFromFile("/sys/devices/virtual/dmi/id/product_version").trim();
      boolean flag = s.isEmpty();
      if (flag) {
         return !s1.isEmpty() ? s1 : null;
      } else {
         return !s1.isEmpty() && !"None".equals(s1) ? s + " (version: " + s1 + ")" : s;
      }
   }

   private static String queryModelFromDeviceTree() {
      String s = FileUtil.getStringFromFile("/sys/firmware/devicetree/base/model");
      return !s.isEmpty() ? s.replace("Machine: ", "") : null;
   }

   private static String queryModelFromLshw() {
      for (String s : ExecutingCommand.runNative("lshw -C system")) {
         if (s.contains("product:")) {
            return s.split("product:")[1].trim();
         }
      }

      return null;
   }

   private static String querySerialFromSysfs() {
      String s = FileUtil.getStringFromFile("/sys/devices/virtual/dmi/id/product_serial");
      boolean flag = s.isEmpty();
      if (!flag && !"None".equals(s)) {
         return null;
      } else {
         s = FileUtil.getStringFromFile("/sys/devices/virtual/dmi/id/board_serial");
         boolean flag1 = s.isEmpty();
         return !flag1 && !"None".equals(s) ? s : null;
      }
   }

   private static String querySerialFromDmiDecode() {
      for (String s : ExecutingCommand.runNative("dmidecode -t system")) {
         if (s.contains("Serial Number:")) {
            return s.split("Serial Number:")[1].trim();
         }
      }

      return null;
   }

   private static String querySerialFromLshal() {
      for (String s : ExecutingCommand.runNative("lshal")) {
         if (s.contains("system.hardware.serial =")) {
            return ParseUtil.getSingleQuoteStringValue(s);
         }
      }

      return null;
   }

   private static String querySerialFromLshw() {
      for (String s : ExecutingCommand.runNative("lshw -C system")) {
         if (s.contains("serial:")) {
            return s.split("serial:")[1].trim();
         }
      }

      return null;
   }
}
