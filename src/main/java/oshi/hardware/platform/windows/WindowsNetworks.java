package oshi.hardware.platform.windows;

import com.sun.jna.platform.win32.IPHlpAPI;
import com.sun.jna.platform.win32.VersionHelpers;
import com.sun.jna.platform.win32.IPHlpAPI.MIB_IFROW;
import com.sun.jna.platform.win32.IPHlpAPI.MIB_IF_ROW2;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.hardware.NetworkIF;
import oshi.hardware.common.Networks;
import oshi.util.ParseUtil;

@ThreadSafe
public final class WindowsNetworks extends Networks {
   private static final boolean IS_VISTA_OR_GREATER = VersionHelpers.IsWindowsVistaOrGreater();
   private static final byte CONNECTOR_PRESENT_BIT = 32;

   public static boolean updateNetworkStats(NetworkIF netIF) {
      if (IS_VISTA_OR_GREATER) {
         MIB_IF_ROW2 mib_if_row2 = new MIB_IF_ROW2();
         mib_if_row2.InterfaceIndex = netIF.queryNetworkInterface().getIndex();
         if (0 != IPHlpAPI.INSTANCE.GetIfEntry2(mib_if_row2)) {
            return false;
         }

         netIF.setIfType(mib_if_row2.Type);
         netIF.setNdisPhysicalMediumType(mib_if_row2.PhysicalMediumType);
         netIF.setConnectorPresent((mib_if_row2.InterfaceAndOperStatusFlags & 32) > 0);
         netIF.setBytesSent(mib_if_row2.OutOctets);
         netIF.setBytesRecv(mib_if_row2.InOctets);
         netIF.setPacketsSent(mib_if_row2.OutUcastPkts);
         netIF.setPacketsRecv(mib_if_row2.InUcastPkts);
         netIF.setOutErrors(mib_if_row2.OutErrors);
         netIF.setInErrors(mib_if_row2.InErrors);
         netIF.setCollisions(mib_if_row2.OutDiscards);
         netIF.setInDrops(mib_if_row2.InDiscards);
         netIF.setSpeed(mib_if_row2.ReceiveLinkSpeed);
      } else {
         MIB_IFROW mib_ifrow = new MIB_IFROW();
         mib_ifrow.dwIndex = netIF.queryNetworkInterface().getIndex();
         if (0 != IPHlpAPI.INSTANCE.GetIfEntry(mib_ifrow)) {
            return false;
         }

         netIF.setIfType(mib_ifrow.dwType);
         netIF.setBytesSent(ParseUtil.unsignedIntToLong(mib_ifrow.dwOutOctets));
         netIF.setBytesRecv(ParseUtil.unsignedIntToLong(mib_ifrow.dwInOctets));
         netIF.setPacketsSent(ParseUtil.unsignedIntToLong(mib_ifrow.dwOutUcastPkts));
         netIF.setPacketsRecv(ParseUtil.unsignedIntToLong(mib_ifrow.dwInUcastPkts));
         netIF.setOutErrors(ParseUtil.unsignedIntToLong(mib_ifrow.dwOutErrors));
         netIF.setInErrors(ParseUtil.unsignedIntToLong(mib_ifrow.dwInErrors));
         netIF.setCollisions(ParseUtil.unsignedIntToLong(mib_ifrow.dwOutDiscards));
         netIF.setInDrops(ParseUtil.unsignedIntToLong(mib_ifrow.dwInDiscards));
         netIF.setSpeed(ParseUtil.unsignedIntToLong(mib_ifrow.dwSpeed));
      }

      netIF.setTimeStamp(System.currentTimeMillis());
      return true;
   }
}
