package astolfo.auth;

import com.sun.jna.Platform;
import java.util.Arrays;
import java.util.List;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.ComputerSystem;
import oshi.hardware.Display;
import oshi.hardware.GraphicsCard;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HWPartition;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;
import oshi.util.EdidUtil;
import oshi.util.tuples.Pair;

public class HwidBuilder {
   private final StringBuilder hwid = new StringBuilder();

   public HwidBuilder(HwidOptions hwidOptions) {
      SystemInfo systeminfo = new SystemInfo();
      OperatingSystem operatingsystem = systeminfo.getOperatingSystem();
      HardwareAbstractionLayer hardwareabstractionlayer = systeminfo.getHardware();
      ComputerSystem computersystem = hardwareabstractionlayer.getComputerSystem();
      CentralProcessor centralprocessor = hardwareabstractionlayer.getProcessor();
      if (HwidOptions.isUsernameEnabled(hwidOptions)) {
         this.hwid.append(System.getProperty("user.name")).append("$");
      }

      if (HwidOptions.isBaseboardEnabled(hwidOptions)) {
         String s = String.format("%08x", computersystem.getBaseboard().getSerialNumber().hashCode());
         this.hwid.append(s).append("$");
      }

      if (HwidOptions.isCpuEnabled(hwidOptions)) {
         String s2;
         try {
            s2 = centralprocessor.getProcessorIdentifier().getIdentifier();
         } catch (Exception exception) {
            s2 = "unk";
         }

         this.hwid.append(s2.hashCode()).append("$");
      }

      if (HwidOptions.isGpuEnabled(hwidOptions)) {
         GraphicsCard[] agraphicscard = hardwareabstractionlayer.getGraphicsCards();
         if (agraphicscard.length - 1 >= 0) {
            this.hwid.append(agraphicscard[agraphicscard.length - 1].getName().hashCode()).append("$");
         } else {
            this.hwid.append("unk").append("$");
         }
      }

      if (HwidOptions.isMemoryEnabled(hwidOptions)) {
         this.hwid.append((int)((double)hardwareabstractionlayer.getMemory().getTotal() / 1.0E9)).append("$");
      }

      if (HwidOptions.isDiskEnabled(hwidOptions)) {
         if (Platform.isWindows()) {
            Pair pair = findPartitionIndex(System.getProperty("user.home"), Arrays.asList(hardwareabstractionlayer.getDiskStores()));
            int i = (Integer)pair.getA();
            if (i != -1) {
               this.hwid.append(hardwareabstractionlayer.getDiskStores()[i].getSerial().hashCode());
            }
         } else {
            this.hwid.append(computersystem.getSerialNumber().hashCode());
         }

         this.hwid.append("$");
      }

      if (HwidOptions.isDisplaysEnabled(hwidOptions)) {
         Display[] adisplay = hardwareabstractionlayer.getDisplays();

         for (int j = 0; j < adisplay.length; j++) {
            byte[] abyte = adisplay[j].getEdid();
            byte[][] abyte1 = EdidUtil.getDescriptors(abyte);
            String s1 = "Display " + j;

            for (byte[] abyte2 : abyte1) {
               if (EdidUtil.getDescriptorType(abyte2) == 252) {
                  s1 = EdidUtil.getDescriptorText(abyte2);
               }
            }

            this.hwid.append(s1).append("-");
         }

         this.hwid.append("$");
      }
   }

   private static Pair<Integer, Integer> findPartitionIndex(String string, List<HWDiskStore> list) {
      for (int i = 0; i < list.size(); i++) {
         HWDiskStore hwdiskstore = (HWDiskStore)list.get(i);
         List list1 = Arrays.asList(hwdiskstore.getPartitions());

         for (int j = 0; j < list1.size(); j++) {
            String s = ((HWPartition)list1.get(j)).getMountPoint();
            if (!s.isEmpty() && string.substring(0, s.length()).equalsIgnoreCase(s)) {
               return new Pair<>(i, j);
            }
         }
      }

      return new Pair<>(-1, -1);
   }

   public String getRawHwid() {
      return this.hwid.toString();
   }
}
