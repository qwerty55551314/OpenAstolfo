package astolfo.module.movement;

import astolfo.event.EventHandler;
import astolfo.event.EventPriority;
import astolfo.event.EventState;
import astolfo.event.Listener;
import astolfo.event.network.PacketDirection;
import astolfo.event.network.PacketEvent;
import astolfo.event.player.MotionEvent;
import astolfo.event.player.MoveEvent;
import astolfo.event.player.TeleportEvent;
import astolfo.event.player.UpdateEvent;
import astolfo.event.world.WorldLoadEvent;
import astolfo.module.Category;
import astolfo.module.Module;
import astolfo.module.player.Scaffold;
import astolfo.setting.BooleanSetting;
import astolfo.setting.EnumSetting;
import astolfo.setting.NumberSetting;
import astolfo.util.GameUtil;
import astolfo.util.Stopwatch;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public final class AntiFall extends Module {
   private final EnumSetting<AntiFallMode> mode = new EnumSetting<>("Mode", "Set your AntiFall mode", AntiFallMode.SET_BACK);
   private final NumberSetting<Integer> height = new NumberSetting<>("Height", "Height", 2, 1, 10);
   private final BooleanSetting voidOnly = new BooleanSetting("Void-Only", "Prevents you from void only", true);
   private Vec3 lastSafePos;
   private final Stopwatch voidTimer = new Stopwatch();
   @EventHandler(WorldLoadEvent.class)
   private Listener<WorldLoadEvent> onWorldLoad = worldLoadEvent -> this.lastSafePos = null;
   private Stopwatch safePosTimer = new Stopwatch();
   @EventHandler(UpdateEvent.class)
   private Listener<UpdateEvent> onUpdate = updateEvent -> {
      this.updateDisplayName();
      if (updateEvent.getState() == EventState.PRE && mc.thePlayer != null) {
         boolean bl = NoFall.u_boolean();
         if (mc.thePlayer.onGround && !bl && mc.thePlayer.isCollidedVertically && !Scaffold.isOnEdge()) {
            this.lastSafePos = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
            this.safePosTimer.reset();
         }

         if (this.mode.getValue() == AntiFallMode.LAG
            && this.isOverVoid()
            && this.lastSafePos != null
            && mc.thePlayer.posY - this.lastSafePos.yCoord < -this.height.getValue()
            && mc.thePlayer.ticksExisted % 5 == 0) {
            mc.thePlayer.setPosition(this.lastSafePos.xCoord, this.lastSafePos.yCoord, this.lastSafePos.zCoord);
            this.voidTimer.reset();
         }
      }
   };
   private boolean tooManyTeleports;
   private int teleportCount;
   private boolean freezing;
   @EventHandler(PacketEvent.class)
   private final Listener<PacketEvent> onPacket = packetEvent -> {
      if (mc.thePlayer != null) {
         if (packetEvent.getDirection() == PacketDirection.OUTGOING) {
            if (packetEvent.getPacket() instanceof C03PacketPlayer && this.isOverVoid() && this.mode.getValue() == AntiFallMode.LAG) {
               packetEvent.cancel();
            }
         } else if (packetEvent.getPacket() instanceof S08PacketPlayerPosLook && this.freezing) {
            mc.thePlayer.fallDistance = 0.0F;
         }
      }
   };
   @EventHandler(TeleportEvent.class)
   private Listener<TeleportEvent> onTeleport = teleportEvent -> {
      if (teleportEvent.isLagback()) {
         this.teleportCount++;
         if (this.teleportCount > 10) {
            this.tooManyTeleports = true;
         }
      }
   };
   @EventHandler(value = MoveEvent.class, priority = EventPriority.HIGHEST)
   private Listener<MoveEvent> onMove = moveEvent -> {
      if (moveEvent.getState() == EventState.PRE && this.freezing) {
         GameUtil.setMoveSpeed(moveEvent, 0.0, 90.0F);
         moveEvent.setY(mc.thePlayer.motionY = 0.0);
      }
   };
   @EventHandler(MotionEvent.class)
   private Listener<MotionEvent> onMotion = motionEvent -> {
      this.freezing = false;
      if (mc.thePlayer.fallDistance > (this.voidOnly.getValue() ? 3.0 : this.height.getValue().intValue())
         && !client.getModuleManager().get(Flight.class).isEnabled()
         && !client.getModuleManager().get(LongJump.class).isEnabled()
         && (!this.voidOnly.getValue() || !NoFall.P_boolean())
         && !mc.thePlayer.onGround
         && !mc.thePlayer.isCollidedVertically) {
         if (this.mode.getValue() == AntiFallMode.SET_BACK) {
            if (this.lastSafePos != null) {
               mc.thePlayer.setPosition(this.lastSafePos.xCoord, this.lastSafePos.yCoord, this.lastSafePos.zCoord);
            }
         } else if (this.mode.getValue() != AntiFallMode.LAG) {
            if (this.lastSafePos != null) {
               motionEvent.setMinY(motionEvent.getMinY() + 3.0);
            } else if (this.voidTimer.hasElapsed(1000.0F)) {
               motionEvent.setZ(motionEvent.getZ() + 25.0);
               motionEvent.setOnGround(false);
               mc.thePlayer.fallDistance = 0.0F;
               this.voidTimer.reset();
            }
         }
      }
   };

   public AntiFall(Category category) {
      super(category);
      this.addSettings(this.mode, this.height, this.voidOnly);
   }

   private boolean isOverVoid() {
      double d = mc.thePlayer.posX - mc.thePlayer.prevPosX;
      double e = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
      double d2 = MathHelper.sqrt_double(d * d + e * e);
      double d3 = d2 / 0.05F;

      for (double d4 = 0.0; d4 < d3 - 1.0; d4 += 0.2) {
         double d5 = -Math.sin(Math.toRadians(mc.thePlayer.rotationYaw));
         double d6 = Math.cos(Math.toRadians(mc.thePlayer.rotationYaw));
         double d7 = mc.thePlayer.movementInput.moveForward * d4 * d5 + mc.thePlayer.movementInput.moveStrafe * d4 * d6;
         double d8 = mc.thePlayer.movementInput.moveForward * d4 * d6 - mc.thePlayer.movementInput.moveStrafe * d4 * d5;

         for (int i = -7; i < 0; i++) {
            Block block = mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX + d7, mc.thePlayer.posY + i, mc.thePlayer.posZ + d8)).getBlock();
            Block block2 = mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX + d7, mc.thePlayer.posY + i, mc.thePlayer.posZ + d8)).getBlock();
            if (block != Blocks.air || block2 != Blocks.air) {
               return false;
            }
         }
      }

      return NoFall.u_boolean()
         && !mc.thePlayer.isCollidedVertically
         && !mc.thePlayer.onGround
         && mc.thePlayer.fallDistance > 0.0F
         && GameUtil.isOnHypixel()
         && this.lastSafePos != null
         && !client.getModuleManager().get(Flight.class).isEnabled()
         && !client.getModuleManager().get(LongJump.class).isEnabled();
   }

   @Override
   public void onEnable() {
      this.lastSafePos = null;
      super.onEnable();
   }

   @Override
   public void updateDisplayName() {
      this.setDisplayName("Anti Fall " + EnumChatFormatting.GRAY + AntiFallMode.displayNameOf(this.mode.getValue()));
      super.updateDisplayName();
   }
}
