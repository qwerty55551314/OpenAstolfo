package astolfo.module.other;

import astolfo.event.EventHandler;
import astolfo.event.EventState;
import astolfo.event.Listener;
import astolfo.event.player.UpdateEvent;
import astolfo.event.world.WorldLoadEvent;
import astolfo.module.Category;
import astolfo.module.Module;
import astolfo.setting.BooleanSetting;
import astolfo.util.RandomUtils;
import astolfo.util.Stopwatch;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;

public final class Announcer extends Module {
   private final BooleanSetting skip = new BooleanSetting("Skip", "Skip players if delay not good", true);
   private List<String> joinMessages = Arrays.asList("Hello {user}", "Welcome {user}", "Nice to see you, {user}");
   private List<String> leaveMessages = Arrays.asList("Bye {user}", "See you later, {user}", "Good bye {user}", "Hasta la vista, {user}");
   private final BooleanSetting join = new BooleanSetting("Join", "Welcomes players that join", true);
   private final BooleanSetting leave = new BooleanSetting("Leave", "Welcomes players that leave", true);
   private Stopwatch messageTimer = new Stopwatch();
   private List<NetworkPlayerInfo> knownPlayers = new CopyOnWriteArrayList<>();
   private int ticksSinceJoin;
   @EventHandler(WorldLoadEvent.class)
   private Listener<WorldLoadEvent> onWorldLoad = worldLoadEvent -> {
      this.ticksSinceJoin = 0;
      this.knownPlayers.clear();
   };
   @EventHandler(UpdateEvent.class)
   private Listener<UpdateEvent> onUpdate = updateEvent -> {
      if (updateEvent.getState() == EventState.PRE) {
         List list = GuiPlayerTabOverlay.field_175252_a.sortedCopy(mc.thePlayer.sendQueue.getPlayerInfoMap());
         list.remove(mc.thePlayer.sendQueue.getPlayerInfo(mc.session.getUsername()));

         for (Object object : list) {
            NetworkPlayerInfo networkPlayerInfo = (NetworkPlayerInfo)object;
            String string = this.getPlayerDisplayName(networkPlayerInfo);
            if (this.ticksSinceJoin < 150) {
               if (!this.knownPlayers.contains(networkPlayerInfo)) {
                  this.knownPlayers.add(networkPlayerInfo);
               }
            } else if (this.join.getValue() && !this.knownPlayers.contains(networkPlayerInfo)) {
               if (this.messageTimer.hasElapsed(1000.0F)) {
                  String string2 = this.joinMessages.get(RandomUtils.RANDOM.nextInt(this.joinMessages.size())).replace("{user}", string);
                  mc.thePlayer.sendChatMessage(string2);
                  if (!this.skip.getValue()) {
                     this.knownPlayers.add(networkPlayerInfo);
                  }

                  this.messageTimer.reset();
               }

               if (this.skip.getValue()) {
                  this.knownPlayers.add(networkPlayerInfo);
               }
            }
         }

         if (this.leave.getValue()) {
            for (NetworkPlayerInfo networkPlayerInfo2 : this.knownPlayers) {
               String string3 = this.getPlayerDisplayName(networkPlayerInfo2);
               if (!list.contains(networkPlayerInfo2) && this.ticksSinceJoin > 150) {
                  if (this.messageTimer.hasElapsed(1000.0F)) {
                     String string4 = this.leaveMessages.get(RandomUtils.RANDOM.nextInt(this.joinMessages.size())).replace("{user}", string3);
                     mc.thePlayer.sendChatMessage(string4);
                     if (!this.skip.getValue()) {
                        this.knownPlayers.remove(networkPlayerInfo2);
                     }

                     this.messageTimer.reset();
                  }

                  if (this.skip.getValue()) {
                     this.knownPlayers.remove(networkPlayerInfo2);
                  }
               }
            }
         }

         this.ticksSinceJoin++;
      }
   };

   public Announcer(Category category) {
      super(category);
      this.addSettings(this.join, this.leave, this.skip);
   }

   @Override
   public void onEnable() {
      this.ticksSinceJoin = 0;
      this.messageTimer.reset();
      super.onEnable();
   }

   @Override
   public void onDisable() {
      this.knownPlayers.clear();
      super.onDisable();
   }

   private String getPlayerDisplayName(NetworkPlayerInfo networkPlayerInfo) {
      return networkPlayerInfo.getDisplayName() != null
         ? networkPlayerInfo.getDisplayName().getFormattedText()
         : ScorePlayerTeam.formatPlayerName(networkPlayerInfo.getPlayerTeam(), networkPlayerInfo.getGameProfile().getName());
   }
}
