package oshi.hardware.platform.windows;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.Win32Exception;
import com.sun.jna.platform.win32.WinReg;
import java.util.ArrayList;
import java.util.List;
import oshi.annotation.concurrent.Immutable;
import oshi.hardware.common.AbstractSoundCard;

@Immutable
public final class WindowsSoundCard extends AbstractSoundCard {
   private static final String Z = "SYSTEM\\CurrentControlSet\\Control\\Class\\{4d36e96c-e325-11ce-bfc1-08002be10318}\\";

   public WindowsSoundCard(String kernelVersion, String name, String codec) {
      super(kernelVersion, name, codec);
   }

   public static List<WindowsSoundCard> getSoundCards() {
      Object object = null;
      ArrayList arraylist = new ArrayList();
      String[] astring = Advapi32Util.registryGetKeys(
         WinReg.HKEY_LOCAL_MACHINE, "SYSTEM\\CurrentControlSet\\Control\\Class\\{4d36e96c-e325-11ce-bfc1-08002be10318}\\"
      );

      for (String s : astring) {
         String s1 = "SYSTEM\\CurrentControlSet\\Control\\Class\\{4d36e96c-e325-11ce-bfc1-08002be10318}\\" + s;

         try {
            if (Advapi32Util.registryValueExists(WinReg.HKEY_LOCAL_MACHINE, s1, "Driver")) {
               arraylist.add(
                  new WindowsSoundCard(
                     Advapi32Util.registryGetStringValue(WinReg.HKEY_LOCAL_MACHINE, s1, "Driver")
                        + " "
                        + Advapi32Util.registryGetStringValue(WinReg.HKEY_LOCAL_MACHINE, s1, "DriverVersion"),
                     Advapi32Util.registryGetStringValue(WinReg.HKEY_LOCAL_MACHINE, s1, "ProviderName")
                        + " "
                        + Advapi32Util.registryGetStringValue(WinReg.HKEY_LOCAL_MACHINE, s1, "DriverDesc"),
                     Advapi32Util.registryGetStringValue(WinReg.HKEY_LOCAL_MACHINE, s1, "DriverDesc")
                  )
               );
            }
         } catch (Win32Exception win32exception) {
            if (win32exception.getErrorCode() != 5) {
               throw win32exception;
            }
         }
      }

      return arraylist;
   }
}
