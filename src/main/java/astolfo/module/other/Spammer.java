package astolfo.module.other;

import astolfo.event.EventHandler;
import astolfo.event.Listener;
import astolfo.event.network.PacketDirection;
import astolfo.event.network.PacketEvent;
import astolfo.event.player.UpdateEvent;
import astolfo.event.world.WorldLoadEvent;
import astolfo.module.Category;
import astolfo.module.Module;
import astolfo.setting.BooleanSetting;
import astolfo.setting.MapSetting;
import astolfo.setting.MapSettingBuilder;
import astolfo.setting.NumberSetting;
import astolfo.setting.StringSetting;
import astolfo.util.Stopwatch;
import net.minecraft.network.play.client.C01PacketChatMessage;
import org.apache.commons.lang3.RandomStringUtils;

public final class Spammer extends Module {
   private final NumberSetting<Integer> delay = new NumberSetting<>("Delay", "Delay between each message", 3000, 1, 30000, "ms");
   private final StringSetting message = new StringSetting("Message", "Message to spam", "cocaine");
   private final BooleanSetting multiLine = new BooleanSetting("Multi-Line", "Sends multiple different lines (; separator)", true);
   private final BooleanSetting hypixel = new BooleanSetting("Hypixel", "Switches lobbies in hypixel", false);
   private final MapSetting random = new MapSettingBuilder("Random")
      .ordered()
      .description("random options")
      .add("Enable", new BooleanSetting("Enable", "Randomizes message at the end", false))
      .add("Letters", new BooleanSetting("Letters", "Uses letters to randomize", false))
      .add("Numbers", new BooleanSetting("Numbers", "Uses numbers to randomize", false))
      .add("Length", new NumberSetting<>("Length", "Length of randomized string", 5, 1, 12))
      .build();
   private final Stopwatch spamTimer = new Stopwatch();
   private int lineIndex;
   @EventHandler(PacketEvent.class)
   private Listener<PacketEvent> onPacket = packetEvent -> {
      if (packetEvent.getDirection() == PacketDirection.RECEIVED && packetEvent.getPacket() instanceof C01PacketChatMessage && !this.hypixel.getValue()) {
         this.spamTimer.reset();
      }
   };
   private boolean sentFirstMessage;
   private final String[] hubLobbies = new String[]{"", "sw", "bw", "duels"};
   @EventHandler(UpdateEvent.class)
   private Listener<UpdateEvent> onUpdate = updateEvent -> {
      String string = this.message.getValue();
      if (this.message.getValue().contains(";") && this.multiLine.getValue()) {
         String[] strings = this.message.getValue().split(";");
         if (this.lineIndex >= strings.length) {
            this.lineIndex = 0;
         }

         string = strings[this.lineIndex];
         this.lineIndex++;
      }

      boolean bl = this.random.getSetting("Enable").asBoolean();
      int i = this.random.getSetting("Length").asInt();
      boolean bl2 = this.random.getSetting("Letters").asBoolean();
      boolean bl3 = this.random.getSetting("Numbers").asBoolean();
      String string2 = RandomStringUtils.random(i, bl2, bl3);
      if (this.hypixel.getValue()) {
         if (!this.sentFirstMessage) {
            mc.thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(string + (bl ? string2 : "")));
            this.sentFirstMessage = true;
         } else if (this.spamTimer.hasElapsed(200.0F)) {
            mc.thePlayer.sendChatMessage("/hub " + this.hubLobbies[(int)(Math.random() * this.hubLobbies.length)]);
            this.spamTimer.reset();
         }
      } else {
         if (this.spamTimer.hasElapsed(this.delay.getValue().intValue())) {
            if (string.startsWith("-") || string.startsWith(" -")) {
               return;
            }

            mc.thePlayer.sendChatMessage(string + (bl ? string2 : ""));
            this.spamTimer.reset();
         }
      }
   };
   @EventHandler(WorldLoadEvent.class)
   private Listener<WorldLoadEvent> onWorldLoad = worldLoadEvent -> {
      this.spamTimer.reset();
      this.sentFirstMessage = false;
   };

   public Spammer(Category category) {
      super(category);
      this.addSettings(this.message, this.delay, this.random, this.multiLine, this.hypixel);
   }
}
