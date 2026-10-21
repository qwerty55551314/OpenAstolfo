package astolfo.module.other;

import astolfo.ClientAccess;
import astolfo.event.EventHandler;
import astolfo.event.Listener;
import astolfo.event.network.PacketDirection;
import astolfo.event.network.PacketEvent;
import astolfo.module.Category;
import astolfo.module.Module;
import astolfo.setting.BooleanSetting;
import astolfo.setting.Setting;
import astolfo.util.Stopwatch;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S2DPacketOpenWindow;

public final class AntiFrozen extends Module {
   private final BooleanSetting spawn = new BooleanSetting("Spawn", "TPs you to spawn when you hit a frozen player", true);
   private Stopwatch messageTimer = new Stopwatch();
   @EventHandler(PacketEvent.class)
   private Listener<PacketEvent> onPacket = this::onPacket;

   public AntiFrozen(Category category) {
      super(category);
      this.setHidden(false);
      this.addSettings(new Setting[]{this.spawn});
   }

   private void onPacket(PacketEvent packetEvent) {
      if (packetEvent.getDirection() == PacketDirection.RECEIVED) {
         boolean flag = packetEvent.getPacket() instanceof S2DPacketOpenWindow;
         if (flag && ((S2DPacketOpenWindow)packetEvent.getPacket()).getWindowTitle().getUnformattedText().contains("You are frozen")) {
            packetEvent.cancel();
         }

         boolean flag1 = this.spawn.getValue();
         if (flag1 && packetEvent.getPacket() instanceof S02PacketChat) {
            S02PacketChat s02packetchat = (S02PacketChat)packetEvent.getPacket();
            boolean flag2 = s02packetchat.getChatComponent().getUnformattedText().contains("is currently frozen");
            if (flag2 && this.messageTimer.hasElapsed(1500.0F)) {
               ClientAccess.mc.thePlayer.setPosition(ClientAccess.mc.thePlayer.posX, -999.0, ClientAccess.mc.thePlayer.posY);
               this.messageTimer.reset();
            }
         }
      }
   }
}
