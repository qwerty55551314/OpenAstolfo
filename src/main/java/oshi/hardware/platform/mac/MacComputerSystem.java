package oshi.hardware.platform.mac;

import com.sun.jna.platform.mac.IOKitUtil;
import com.sun.jna.platform.mac.IOKit.IOService;
import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;
import oshi.annotation.concurrent.Immutable;
import oshi.hardware.Baseboard;
import oshi.hardware.Firmware;
import oshi.hardware.common.AbstractComputerSystem;
import oshi.util.Memoizer;
import oshi.util.Util;
import oshi.util.tuples.Triplet;

@Immutable
public final class MacComputerSystem extends AbstractComputerSystem {
   private final Supplier<Triplet<String, String, String>> manufacturerModelSerial = Memoizer.memoize(MacComputerSystem::platformExpert);

   @Override
   public String getManufacturer() {
      return this.manufacturerModelSerial.get().getA();
   }

   @Override
   public String getModel() {
      return this.manufacturerModelSerial.get().getB();
   }

   @Override
   public String getSerialNumber() {
      return this.manufacturerModelSerial.get().getC();
   }

   @Override
   public Firmware createFirmware() {
      return new MacFirmware();
   }

   @Override
   public Baseboard createBaseboard() {
      return new MacBaseboard();
   }

   private static Triplet<String, String, String> platformExpert() {
      String s = null;
      String s1 = null;
      String s2 = null;
      IOService ioservice = IOKitUtil.getMatchingService("IOPlatformExpertDevice");
      if (ioservice != null) {
         byte[] abyte = ioservice.getByteArrayProperty("manufacturer");
         if (abyte != null) {
            s = new String(abyte, StandardCharsets.UTF_8).trim();
         }

         abyte = ioservice.getByteArrayProperty("model");
         if (abyte != null) {
            s1 = new String(abyte, StandardCharsets.UTF_8).trim();
         }

         s2 = ioservice.getStringProperty("IOPlatformSerialNumber");
         ioservice.release();
      }

      return new Triplet<>(Util.isBlank(s) ? "Apple Inc." : s, Util.isBlank(s1) ? "unknown" : s1, Util.isBlank(s2) ? "unknown" : s2);
   }
}
