package astolfo.module.visuals;

import astolfo.ClientAccess;
import astolfo.event.EventHandler;
import astolfo.event.EventState;
import astolfo.event.Listener;
import astolfo.event.game.TickEvent;
import astolfo.event.world.WorldLoadEvent;
import astolfo.module.Category;
import astolfo.module.Module;
import astolfo.setting.BooleanSetting;
import astolfo.setting.Setting;
import astolfo.setting.StringSetting;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;

public final class StreamerMode extends Module {
   public static final BooleanSetting scrambleNames = new BooleanSetting("Scramble-Names", "Scrambles other people usernames", true);
   public static final BooleanSetting hideSkins = new BooleanSetting("Hide-Skins", "Hides skins", true);
   public static final StringSetting spoofName = new StringSetting("Spoof-Name", "Name you're spoofing", "You");
   public static final ArrayList<String> seenPlayerNames = new ArrayList<>();
   @EventHandler(WorldLoadEvent.class)
   private Listener<WorldLoadEvent> onWorldLoad = StreamerMode::onWorldLoad;
   @EventHandler(TickEvent.class)
   private Listener<TickEvent> onTick = StreamerMode::onTick;

   public StreamerMode(Category category) {
      super(category);
      this.addSettings(new Setting[]{scrambleNames, hideSkins, spoofName});
   }

   public static String getPlayerListName(NetworkPlayerInfo networkPlayerInfo) {
      return networkPlayerInfo.getDisplayName() != null
         ? networkPlayerInfo.getDisplayName().getFormattedText()
         : ScorePlayerTeam.formatPlayerName(networkPlayerInfo.getPlayerTeam(), networkPlayerInfo.getGameProfile().getName());
   }

   private static void onTick(TickEvent tickEvent) {
      if (tickEvent.getState() == EventState.PRE) {
         Minecraft minecraft = ClientAccess.mc;
         if (minecraft.thePlayer != null) {
            WorldClient worldclient = ClientAccess.mc.theWorld;
            if (worldclient != null) {
               for (EntityPlayer entityplayer : ClientAccess.mc.theWorld.playerEntities) {
                  if (entityplayer != ClientAccess.mc.thePlayer) {
                     boolean flag = seenPlayerNames.contains(entityplayer.getName());
                     if (!flag) {
                        seenPlayerNames.add(entityplayer.getName());
                     }
                  }
               }
            }
         }
      }
   }

   private static void onWorldLoad(WorldLoadEvent worldLoadEvent) {
      seenPlayerNames.clear();
   }
}
