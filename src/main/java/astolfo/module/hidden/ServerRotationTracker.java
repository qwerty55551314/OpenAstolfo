package astolfo.module.hidden;

import astolfo.event.EventHandler;
import astolfo.event.EventPriority;
import astolfo.event.EventState;
import astolfo.event.Listener;
import astolfo.event.network.PacketDirection;
import astolfo.event.network.PacketEvent;
import astolfo.event.player.MotionEvent;
import astolfo.event.player.RotationYawEvent;
import astolfo.event.render.RenderLivingEvent;
import astolfo.module.HiddenModule;
import net.minecraft.network.play.server.S03PacketTimeUpdate;

public final class ServerRotationTracker extends HiddenModule {
   private static long lastPacketTime;
   private static long lastTimeUpdateTime;
   private float serverYaw;
   private float serverPitch;
   private float prevServerPitch;
   @EventHandler(value = MotionEvent.class, priority = EventPriority.HIGHEST)
   private Listener<MotionEvent> onMotion = motionEvent -> {
      if (motionEvent.getState() == EventState.PRE) {
         this.prevServerPitch = this.serverPitch;
         this.serverYaw = motionEvent.getYaw();
         this.serverPitch = motionEvent.getPitch();
      }
   };
   @EventHandler(RotationYawEvent.class)
   private Listener<RotationYawEvent> onRotationYaw = rotationYawEvent -> rotationYawEvent.setYaw(this.serverYaw);
   @EventHandler(RenderLivingEvent.class)
   private Listener<RenderLivingEvent> onRenderLiving = renderLivingEvent -> {
      if (renderLivingEvent.getEntity() == mc.thePlayer) {
         renderLivingEvent.setPrevPitch(this.prevServerPitch);
         renderLivingEvent.setPitch(this.serverPitch);
         renderLivingEvent.cancel();
      }
   };
   @EventHandler(PacketEvent.class)
   private Listener<PacketEvent> onPacket = packetEvent -> {
      if (packetEvent.getDirection() == PacketDirection.RECEIVED) {
         if (packetEvent.getPacket() instanceof S03PacketTimeUpdate) {
            lastTimeUpdateTime = System.currentTimeMillis();
         }

         lastPacketTime = System.currentTimeMillis();
      }
   };

   public ServerRotationTracker() {
      client.getEventBus().subscribe(this);
   }

   public static long getTimeSinceLastPacket() {
      return mc.getCurrentServerData() == null ? 0L : System.currentTimeMillis() - lastPacketTime;
   }

   public static long getTimeSinceTimeUpdate() {
      return mc.getCurrentServerData() == null ? 0L : System.currentTimeMillis() - lastTimeUpdateTime;
   }
}
