package astolfo.module.other;

import astolfo.ClientAccess;
import astolfo.event.EventHandler;
import astolfo.event.Listener;
import astolfo.event.network.PacketDirection;
import astolfo.event.network.PacketEvent;
import astolfo.module.Category;
import astolfo.module.Module;
import astolfo.module.visuals.HUD;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;
import net.minecraft.util.EnumChatFormatting;

public final class ThunderNotifier extends Module {
   @EventHandler(PacketEvent.class)
   private final Listener<PacketEvent> onPacket = ThunderNotifier::onPacket;

   public ThunderNotifier(Category category) {
      super(category);
      this.setHidden(false);
   }

   private static void onPacket(PacketEvent packetEvent) {
      if (packetEvent.getDirection() == PacketDirection.RECEIVED) {
         Packet packet = packetEvent.getPacket();
         if (packet instanceof S2CPacketSpawnGlobalEntity) {
            S2CPacketSpawnGlobalEntity s2cpacketspawnglobalentity = (S2CPacketSpawnGlobalEntity)packetEvent.getPacket();
            int i = s2cpacketspawnglobalentity.func_149053_g();
            if (i == 1) {
               double d0 = (double)s2cpacketspawnglobalentity.func_149051_d() / 32.0;
               double d1 = (double)s2cpacketspawnglobalentity.func_149050_e() / 32.0;
               double d2 = (double)s2cpacketspawnglobalentity.func_149049_f() / 32.0;
               double d3 = ClientAccess.mc.thePlayer.getDistance(d0, d1, d2);
               if (HUD.isPowerBranding()) {
                  ClientAccess.chat
                     .createBuilder(false, false)
                     .append("[")
                     .color(EnumChatFormatting.DARK_GRAY)
                     .append("Power")
                     .color(EnumChatFormatting.RED)
                     .append("]")
                     .color(EnumChatFormatting.DARK_GRAY)
                     .append(" 距离 ")
                     .color(EnumChatFormatting.GREEN)
                     .append((int)d3 + "米")
                     .color(EnumChatFormatting.RED)
                     .append(" 检测到雷击! ")
                     .color(EnumChatFormatting.AQUA)
                     .append("坐标")
                     .color(EnumChatFormatting.GREEN)
                     .append("X: ")
                     .color(EnumChatFormatting.AQUA)
                     .append(String.valueOf((int)d0))
                     .color(EnumChatFormatting.WHITE)
                     .append(" Y: ")
                     .color(EnumChatFormatting.AQUA)
                     .append(String.valueOf((int)d1))
                     .color(EnumChatFormatting.WHITE)
                     .append(" Z: ")
                     .color(EnumChatFormatting.AQUA)
                     .append(String.valueOf((int)d2))
                     .color(EnumChatFormatting.WHITE)
                     .append(" [")
                     .color(EnumChatFormatting.BLUE)
                     .append("ClickHereTP")
                     .bold()
                     .color(EnumChatFormatting.YELLOW)
                     .runCommandOnClick(".tp " + d0 + " 128 " + d2)
                     .showTextOnHover(EnumChatFormatting.GREEN + "Click to TP~")
                     .append("]")
                     .color(EnumChatFormatting.BLUE)
                     .build()
                     .send();
               } else {
                  ClientAccess.chat
                     .createBuilder(false, false)
                     .append("> ")
                     .color(EnumChatFormatting.GRAY)
                     .append(String.format("Detected thunder at %s %s %s (%s blocks away)", (int)d0, (int)d1, (int)d2, (int)d3))
                     .color(EnumChatFormatting.WHITE)
                     .runCommandOnClick(".tp " + d0 + " 128 " + d2)
                     .showTextOnHover("Click for TP")
                     .build()
                     .send();
               }
            }
         }
      }
   }
}
