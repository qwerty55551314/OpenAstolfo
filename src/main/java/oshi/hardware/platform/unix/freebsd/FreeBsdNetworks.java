package oshi.hardware.platform.unix.freebsd;

import oshi.annotation.concurrent.ThreadSafe;
import oshi.hardware.NetworkIF;
import oshi.hardware.common.Networks;
import oshi.util.ExecutingCommand;
import oshi.util.ParseUtil;

@ThreadSafe
public final class FreeBsdNetworks extends Networks {
   private static final String c = "netstat -bI ";

   public static boolean updateNetworkStats(NetworkIF netIF) {
      String s = ExecutingCommand.getAnswerAt(c + netIF.getName(), 1);
      netIF.setTimeStamp(System.currentTimeMillis());
      String[] astring = ParseUtil.whitespaces.split(s);
      if (astring.length < 12) {
         return false;
      } else {
         netIF.setBytesSent(ParseUtil.parseUnsignedLongOrDefault(astring[10], 0L));
         netIF.setBytesRecv(ParseUtil.parseUnsignedLongOrDefault(astring[7], 0L));
         netIF.setPacketsSent(ParseUtil.parseUnsignedLongOrDefault(astring[8], 0L));
         netIF.setPacketsRecv(ParseUtil.parseUnsignedLongOrDefault(astring[4], 0L));
         netIF.setOutErrors(ParseUtil.parseUnsignedLongOrDefault(astring[9], 0L));
         netIF.setInErrors(ParseUtil.parseUnsignedLongOrDefault(astring[5], 0L));
         netIF.setCollisions(ParseUtil.parseUnsignedLongOrDefault(astring[11], 0L));
         netIF.setInDrops(ParseUtil.parseUnsignedLongOrDefault(astring[6], 0L));
         return true;
      }
   }
}
