package astolfo.module.combat;

import astolfo.event.EventHandler;
import astolfo.event.Listener;
import astolfo.event.network.PacketDirection;
import astolfo.event.network.PacketEvent;
import astolfo.event.player.UpdateEvent;
import astolfo.module.Category;
import astolfo.module.Module;
import astolfo.module.exploits.RiderGod;
import astolfo.setting.BooleanSetting;
import astolfo.setting.EnumSetting;
import astolfo.setting.NumberSetting;
import astolfo.util.GameUtil;
import astolfo.util.Stopwatch;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.EnumChatFormatting;

public final class Criticals extends Module {
   public static final EnumSetting<CriticalsMode> mode = new EnumSetting<>("Mode", "Set your Critical mode", CriticalsMode.PACKET);
   public static final NumberSetting<Float> offset = new NumberSetting<>("Offset", "Offset used for Edit mode", 0.1337F, 0.0627F, 0.42F);
   public static final NumberSetting<Integer> ticks = new NumberSetting<>("Ticks", "Wait ticks", 15, 0, 20);
   private final BooleanSetting safe = new BooleanSetting("Safe", "Crits when the target isnt hurt", false);
   private final Stopwatch critTimer = new Stopwatch();
   public static boolean canCrit;
   private int waitTicks;
   @EventHandler(UpdateEvent.class)
   private Listener<UpdateEvent> onUpdate = updateEvent -> {
      this.updateDisplayName();
      if (!GameUtil.canCriticalHit()) {
         canCrit = false;
         this.waitTicks = 0;
      } else if (this.waitTicks >= ticks.getValue() || ticks.getValue() == 0) {
         canCrit = true;
         this.waitTicks = 0;
      } else if (!canCrit) {
         this.waitTicks++;
      }
   };
   @EventHandler(PacketEvent.class)
   private Listener<PacketEvent> onPacket = packetEvent -> {
      if (packetEvent.getDirection() == PacketDirection.OUTGOING) {
         if (mc.theWorld == null) {
            return;
         }

         if (client.getModuleManager().get(RiderGod.class).isEnabled()) {
            return;
         }

         if (packetEvent.getPacket() instanceof C02PacketUseEntity) {
            C02PacketUseEntity c02PacketUseEntity = (C02PacketUseEntity)packetEvent.getPacket();
            if (c02PacketUseEntity.getAction().equals(C02PacketUseEntity.Action.ATTACK)
               && GameUtil.canCriticalHit()
               && mode.getValue() != CriticalsMode.WATCHDOG
               && c02PacketUseEntity.getEntityFromWorld(mc.theWorld) instanceof EntityLivingBase
               && (
                  canCrit
                     || this.safe.getValue() && this.critTimer.hasElapsed(350.0F) && ((EntityLivingBase)c02PacketUseEntity.getEntityFromWorld(mc.theWorld)).hurtTime == 0
               )) {
               this.doCritical((EntityLivingBase)c02PacketUseEntity.getEntityFromWorld(mc.theWorld));
            }
         }
      }
   };
   private final double[] critOffsets = new double[]{0.06252F + Math.random() / 1.0E7, 1.73E-11, 1.73E-11};

   public Criticals(Category category) {
      super(category);
      this.addSettings(mode, offset, ticks, this.safe);
   }

   private void doCritical(EntityLivingBase entityLivingBase) {
      switch ((CriticalsMode)mode.getValue()) {
         case PACKET:
            mc.thePlayer
               .sendQueue
               .sendPacketNoEvent(
                  new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.06251 + 1.0E-5, mc.thePlayer.posZ, false)
               );
            mc.thePlayer
               .sendQueue
               .sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
         case WATCHDOG:
         default:
            this.critTimer.reset();
            canCrit = false;
            this.waitTicks = 0;
      }
   }

   @Override
   public void updateDisplayName() {
      this.setDisplayName(
         this.getName()
            + " "
            + EnumChatFormatting.GRAY
            + CriticalsMode.displayNameOf(mode.getValue())
            + " "
            + (this.safe.getValue() ? "Safe" : ticks.getValue())
      );
      super.updateDisplayName();
   }
}
