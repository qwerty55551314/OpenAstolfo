package oshi.hardware.platform.mac;

import com.sun.jna.platform.mac.IOKitUtil;
import com.sun.jna.platform.mac.IOKit.IOIterator;
import com.sun.jna.platform.mac.IOKit.IORegistryEntry;
import com.sun.jna.platform.mac.IOKit.IOService;
import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;
import oshi.annotation.concurrent.Immutable;
import oshi.hardware.common.AbstractFirmware;
import oshi.util.Memoizer;
import oshi.util.Util;
import oshi.util.tuples.Quintet;

@Immutable
public final class MacFirmware extends AbstractFirmware {
   private final Supplier<Quintet<String, String, String, String, String>> manufNameDescVersRelease = Memoizer.memoize(MacFirmware::queryEfi);

   @Override
   public String getManufacturer() {
      return this.manufNameDescVersRelease.get().y();
   }

   @Override
   public String getName() {
      return this.manufNameDescVersRelease.get().getB();
   }

   @Override
   public String getDescription() {
      return this.manufNameDescVersRelease.get().getC();
   }

   @Override
   public String getVersion() {
      return this.manufNameDescVersRelease.get().getD();
   }

   @Override
   public String getReleaseDate() {
      return this.manufNameDescVersRelease.get().getE();
   }

   private static Quintet<String, String, String, String, String> queryEfi() {
      String s = null;
      String s1 = null;
      String s2 = null;
      String s3 = null;
      String s4 = null;
      IOService ioservice = IOKitUtil.getMatchingService("IOPlatformExpertDevice");
      if (ioservice != null) {
         IOIterator ioiterator = ioservice.getChildIterator("IODeviceTree");
         if (ioiterator != null) {
            for (IORegistryEntry ioregistryentry = ioiterator.next(); ioregistryentry != null; ioregistryentry = ioiterator.next()) {
               String s5 = ioregistryentry.getName();
               int b0 = -1;
               int i = s5.hashCode();
               switch (i) {
                  case -1361214606:
                     if (s5.equals("chosen")) {
                        b0 = 1;
                     }
                     break;
                  case 100328:
                     if (s5.equals("efi")) {
                        b0 = 2;
                     }
                     break;
                  case 113104:
                     if (s5.equals("rom")) {
                        b0 = 0;
                     }
               }

               switch (b0) {
                  case 0:
                     byte[] abyte2 = ioregistryentry.getByteArrayProperty("vendor");
                     if (abyte2 != null) {
                        s = new String(abyte2, StandardCharsets.UTF_8).trim();
                     }

                     abyte2 = ioregistryentry.getByteArrayProperty("version");
                     if (abyte2 != null) {
                        s3 = new String(abyte2, StandardCharsets.UTF_8).trim();
                     }

                     abyte2 = ioregistryentry.getByteArrayProperty("release-date");
                     if (abyte2 != null) {
                        s4 = new String(abyte2, StandardCharsets.UTF_8).trim();
                     }
                     break;
                  case 1:
                     byte[] abyte1 = ioregistryentry.getByteArrayProperty("booter-name");
                     if (abyte1 != null) {
                        s1 = new String(abyte1, StandardCharsets.UTF_8).trim();
                     }
                     break;
                  case 2:
                     byte[] abyte = ioregistryentry.getByteArrayProperty("firmware-abi");
                     if (abyte != null) {
                        s2 = new String(abyte, StandardCharsets.UTF_8).trim();
                     }
               }

               ioregistryentry.release();
            }

            ioiterator.release();
         }

         ioservice.release();
      }

      return new Quintet<>(
         Util.isBlank(s) ? "unknown" : s,
         Util.isBlank(s1) ? "unknown" : s1,
         Util.isBlank(s2) ? "unknown" : s2,
         Util.isBlank(s3) ? "unknown" : s3,
         Util.isBlank(s4) ? "unknown" : s4
      );
   }
}
