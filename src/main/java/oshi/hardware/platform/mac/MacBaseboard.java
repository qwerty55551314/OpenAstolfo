package oshi.hardware.platform.mac;

import com.sun.jna.platform.mac.IOKitUtil;
import com.sun.jna.platform.mac.IOKit.IOService;
import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;
import oshi.annotation.concurrent.Immutable;
import oshi.hardware.common.AbstractBaseboard;
import oshi.util.Memoizer;
import oshi.util.Util;
import oshi.util.tuples.Quartet;

@Immutable
public final class MacBaseboard extends AbstractBaseboard {
   private final Supplier<Quartet<String, String, String, String>> manufModelVersSerial = Memoizer.memoize(MacBaseboard::queryPlatform);

   @Override
   public String getManufacturer() {
      return this.manufModelVersSerial.get().getA();
   }

   @Override
   public String getModel() {
      return this.manufModelVersSerial.get().getB();
   }

   @Override
   public String getVersion() {
      return this.manufModelVersSerial.get().getC();
   }

   @Override
   public String getSerialNumber() {
      return this.manufModelVersSerial.get().getD();
   }

   private static Quartet<String, String, String, String> queryPlatform() {
      String s = null;
      String s1 = null;
      String s2 = null;
      String s3 = null;
      IOService ioservice = IOKitUtil.getMatchingService("IOPlatformExpertDevice");
      if (ioservice != null) {
         byte[] abyte = ioservice.getByteArrayProperty("manufacturer");
         if (abyte != null) {
            s = new String(abyte, StandardCharsets.UTF_8).trim();
         }

         abyte = ioservice.getByteArrayProperty("board-id");
         if (abyte != null) {
            s1 = new String(abyte, StandardCharsets.UTF_8).trim();
         }

         abyte = ioservice.getByteArrayProperty("version");
         if (abyte != null) {
            s2 = new String(abyte, StandardCharsets.UTF_8).trim();
         }

         s3 = ioservice.getStringProperty("IOPlatformSerialNumber");
         ioservice.release();
      }

      return new Quartet<>(
         Util.isBlank(s) ? "Apple Inc." : s, Util.isBlank(s1) ? "unknown" : s1, Util.isBlank(s2) ? "unknown" : s2, Util.isBlank(s3) ? "unknown" : s3
      );
   }
}
