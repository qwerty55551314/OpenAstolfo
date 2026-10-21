package oshi.hardware.platform.unix.freebsd;

import java.util.function.Supplier;
import oshi.annotation.concurrent.Immutable;
import oshi.hardware.common.AbstractFirmware;
import oshi.util.ExecutingCommand;
import oshi.util.Memoizer;
import oshi.util.ParseUtil;
import oshi.util.Util;
import oshi.util.tuples.Triplet;

@Immutable
public final class FreeBsdFirmware extends AbstractFirmware {
   private final Supplier<Triplet<String, String, String>> manufVersRelease = Memoizer.memoize(FreeBsdFirmware::readDmiDecode);

   @Override
   public String getManufacturer() {
      return this.manufVersRelease.get().getA();
   }

   @Override
   public String getVersion() {
      return this.manufVersRelease.get().getB();
   }

   @Override
   public String getReleaseDate() {
      return this.manufVersRelease.get().getC();
   }

   private static Triplet<String, String, String> readDmiDecode() {
      String s = null;
      String s1 = null;
      String s2 = "";

      for (String s3 : ExecutingCommand.runNative("dmidecode -t bios")) {
         if (s3.contains("Vendor:")) {
            s = s3.split("Vendor:")[1].trim();
         } else if (s3.contains("Version:")) {
            s1 = s3.split("Version:")[1].trim();
         } else if (s3.contains("Release Date:")) {
            s2 = s3.split("Release Date:")[1].trim();
         }
      }

      s2 = ParseUtil.parseMmDdYyyyToYyyyMmDD(s2);
      return new Triplet<>(Util.isBlank(s) ? "unknown" : s, Util.isBlank(s1) ? "unknown" : s1, Util.isBlank(s2) ? "unknown" : s2);
   }
}
