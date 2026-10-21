package astolfo.auth.handler;

import astolfo.auth.net.PacketOutput;
import astolfo.auth.packet.PacketId;
import astolfo.auth.packet.ServerCommand;
import astolfo.auth.packet.ServerCommandType;
import astolfo.module.hidden.TitleAnnouncer;

public class ServerCommandHandler extends PacketHandler<ServerCommand> {
   public ServerCommandHandler() {
      super(PacketId.SERVER_COMMAND);
   }

   public void handle(ServerCommand serverCommand, PacketOutput packetOutput) {
      if (serverCommand.type == ServerCommandType.A) {
         TitleAnnouncer.pendingTitle = serverCommand.text;
         TitleAnnouncer.titleTimer.reset();
      } else if (serverCommand.type == ServerCommandType.C) {
         if (mc.thePlayer != null) {
            mc.thePlayer.playSound("mob.ghast.scream", 4.0F, 1.0F);
         }
      } else if (serverCommand.type == ServerCommandType.D) {
         mc.shutdown();
      }
   }
}
