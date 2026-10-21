package astolfo.auth.handler;

import astolfo.auth.net.PacketOutput;
import astolfo.auth.packet.NickPacket;
import astolfo.auth.packet.PacketId;
import astolfo.hypixel.PlayerStatsChecker;
import astolfo.hypixel.PlayerStatsEntry;
import astolfo.module.exploits.Denicker;
import astolfo.module.hidden.PlayerListTracker;

public class NickHandler extends PacketHandler<NickPacket> {
   public NickHandler() {
      super(PacketId.NICK);
   }

   public void handle(NickPacket nickPacket, PacketOutput packetOutput) {
      System.out.println(nickPacket.nick + " is " + nickPacket.name);
      Denicker.nicks.put(nickPacket.nick, nickPacket.name);
      PlayerStatsChecker.UnresolvedNick unresolvedNick = PlayerStatsChecker.unresolvedNicks.get(nickPacket.nick);
      if (unresolvedNick != null) {
         PlayerStatsChecker playerStatsChecker = new PlayerStatsChecker(PlayerListTracker.hypixelApi);
         playerStatsChecker.initialize(unresolvedNick.playerInfo.getGameProfile().getId(), nickPacket.name, unresolvedNick.playerInfo, true);
         PlayerStatsEntry playerStatsEntry = new PlayerStatsEntry(nickPacket.name, unresolvedNick.playerInfo, playerStatsChecker);
         playerStatsEntry.realName = nickPacket.name;
         playerStatsEntry.name = nickPacket.nick;
         PlayerListTracker.playerStats.add(playerStatsEntry);
      }
   }
}
