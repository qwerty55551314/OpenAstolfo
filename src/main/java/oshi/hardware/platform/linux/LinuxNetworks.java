package oshi.hardware.platform.linux;

import java.io.File;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.hardware.NetworkIF;
import oshi.hardware.common.Networks;
import oshi.util.FileUtil;

@ThreadSafe
public final class LinuxNetworks extends Networks {
   public static boolean updateNetworkStats(NetworkIF netIF) {
      try {
         File file1 = new File(String.format("/sys/class/net/%s/statistics", netIF.getName()));
         if (!file1.isDirectory()) {
            return false;
         }
      } catch (SecurityException securityexception) {
         return false;
      }

      String s10 = String.format("/sys/class/net/%s/type", netIF.getName());
      String s = String.format("/sys/class/net/%s/carrier", netIF.getName());
      String s1 = String.format("/sys/class/net/%s/statistics/tx_bytes", netIF.getName());
      String s2 = String.format("/sys/class/net/%s/statistics/rx_bytes", netIF.getName());
      String s3 = String.format("/sys/class/net/%s/statistics/tx_packets", netIF.getName());
      String s4 = String.format("/sys/class/net/%s/statistics/rx_packets", netIF.getName());
      String s5 = String.format("/sys/class/net/%s/statistics/tx_errors", netIF.getName());
      String s6 = String.format("/sys/class/net/%s/statistics/rx_errors", netIF.getName());
      String s7 = String.format("/sys/class/net/%s/statistics/collisions", netIF.getName());
      String s8 = String.format("/sys/class/net/%s/statistics/rx_dropped", netIF.getName());
      String s9 = String.format("/sys/class/net/%s/speed", netIF.getName());
      netIF.setTimeStamp(System.currentTimeMillis());
      netIF.setIfType(FileUtil.getIntFromFile(s10));
      netIF.setConnectorPresent(FileUtil.getIntFromFile(s) > 0);
      netIF.setBytesSent(FileUtil.getUnsignedLongFromFile(s1));
      netIF.setBytesRecv(FileUtil.getUnsignedLongFromFile(s2));
      netIF.setPacketsSent(FileUtil.getUnsignedLongFromFile(s3));
      netIF.setPacketsRecv(FileUtil.getUnsignedLongFromFile(s4));
      netIF.setOutErrors(FileUtil.getUnsignedLongFromFile(s5));
      netIF.setInErrors(FileUtil.getUnsignedLongFromFile(s6));
      netIF.setCollisions(FileUtil.getUnsignedLongFromFile(s7));
      netIF.setInDrops(FileUtil.getUnsignedLongFromFile(s8));
      long i = FileUtil.getUnsignedLongFromFile(s9) * 1024L * 1024L;
      netIF.setSpeed(i < 0L ? 0L : i);
      return true;
   }
}
