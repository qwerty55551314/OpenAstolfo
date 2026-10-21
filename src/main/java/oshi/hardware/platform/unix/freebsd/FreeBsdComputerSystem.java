package oshi.hardware.platform.unix.freebsd;

import java.util.function.Supplier;
import oshi.annotation.concurrent.Immutable;
import oshi.hardware.Baseboard;
import oshi.hardware.Firmware;
import oshi.hardware.common.AbstractComputerSystem;
import oshi.util.ExecutingCommand;
import oshi.util.Memoizer;
import oshi.util.ParseUtil;
import oshi.util.Util;
import oshi.util.tuples.Quartet;

@Immutable
public final class FreeBsdComputerSystem extends AbstractComputerSystem {
   private final Supplier<Quartet<String, String, String, String>> manufModelSerialVers = Memoizer.memoize(FreeBsdComputerSystem::readDmiDecode);

   @Override
   public String getManufacturer() {
      return this.manufModelSerialVers.get().getA();
   }

   @Override
   public String getModel() {
      return this.manufModelSerialVers.get().getB();
   }

   @Override
   public String getSerialNumber() {
      return this.manufModelSerialVers.get().getC();
   }

   @Override
   public Firmware createFirmware() {
      return new FreeBsdFirmware();
   }

   @Override
   public Baseboard createBaseboard() {
      return new FreeBsdBaseboard(
         this.manufModelSerialVers.get().getA(),
         this.manufModelSerialVers.get().getB(),
         this.manufModelSerialVers.get().getC(),
         this.manufModelSerialVers.get().getD()
      );
   }

   private static Quartet<String, String, String, String> readDmiDecode() {
      String s = null;
      String s1 = null;
      String s2 = null;
      String s3 = null;

      for (String s4 : ExecutingCommand.runNative("dmidecode -t system")) {
         boolean flag = s4.contains("Manufacturer:");
         if (flag) {
            s = s4.split("Manufacturer:")[1].trim();
         } else if (s4.contains("Product Name:")) {
            s1 = s4.split("Product Name:")[1].trim();
         } else if (s4.contains("Serial Number:")) {
            s2 = s4.split("Serial Number:")[1].trim();
         } else if (s4.contains("Version:")) {
            s3 = s4.split("Version:")[1].trim();
         }
      }

      if (Util.isBlank(s2)) {
         s2 = querySystemSerialNumber();
      }

      return new Quartet<>(
         Util.isBlank(s) ? "unknown" : s, Util.isBlank(s1) ? "unknown" : s1, Util.isBlank(s2) ? "unknown" : s2, Util.isBlank(s3) ? "unknown" : s3
      );
   }

   private static String querySystemSerialNumber() {
      for (String s : ExecutingCommand.runNative("lshal")) {
         if (s.contains("system.hardware.serial =")) {
            return ParseUtil.getSingleQuoteStringValue(s);
         }
      }

      return "unknown";
   }
}
