package oshi.software.os.windows;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinBase.SYSTEM_INFO;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.ptr.IntByReference;

public class WindowsOSSystemInfo {
   private SYSTEM_INFO z = null;

   public WindowsOSSystemInfo() {
      this.init();
   }

   public WindowsOSSystemInfo(SYSTEM_INFO si) {
      this.z = si;
   }

   private void init() {
      SYSTEM_INFO system_info = new SYSTEM_INFO();
      Kernel32.INSTANCE.GetSystemInfo(system_info);

      try {
         IntByReference intbyreference = new IntByReference();
         HANDLE handle = Kernel32.INSTANCE.GetCurrentProcess();
         if (Kernel32.INSTANCE.IsWow64Process(handle, intbyreference) && intbyreference.getValue() > 0) {
            Kernel32.INSTANCE.GetNativeSystemInfo(system_info);
         }
      } catch (UnsatisfiedLinkError unsatisfiedlinkerror) {
      }

      this.z = system_info;
   }

   public int getNumberOfProcessors() {
      return this.z.dwNumberOfProcessors.intValue();
   }
}
