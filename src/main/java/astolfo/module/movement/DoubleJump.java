package astolfo.module.movement;

import astolfo.ClientAccess;
import astolfo.event.EventHandler;
import astolfo.event.EventState;
import astolfo.event.Listener;
import astolfo.event.network.PacketDirection;
import astolfo.event.network.PacketEvent;
import astolfo.event.player.UpdateEvent;
import astolfo.module.Category;
import astolfo.module.Module;
import astolfo.setting.EnumSetting;
import astolfo.setting.Setting;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

public final class DoubleJump extends Module {
   private final EnumSetting<DoubleJumpMode> mode = new EnumSetting<>("Mode", "Set your doublejump mode", DoubleJumpMode.NCP);
   private boolean jumpPending;
   private boolean airJumped;
   @EventHandler(UpdateEvent.class)
   private Listener<UpdateEvent> onUpdate = this::onUpdate;
   @EventHandler(PacketEvent.class)
   private Listener<PacketEvent> onPacket = this::onPacket;

   public DoubleJump(Category category) {
      super(category);
      this.addSettings(new Setting[]{this.mode});
   }

   @Override
   public void onDisable() {
      if (ClientAccess.mc.thePlayer != null) {
         ClientAccess.mc.thePlayer.jumpTicks = 10;
         ClientAccess.mc.thePlayer.onGround = false;
      }

      super.onDisable();
   }

   @Override
   public void updateDisplayName() {
      this.setDisplayName("Double Jump " + EnumChatFormatting.GRAY + DoubleJumpMode.displayNameOf(this.mode.getValue()));
      super.updateDisplayName();
   }

   private boolean isFalling() {
      return ClientAccess.mc.thePlayer.motionY < -0.25;
   }

   private void onPacket(PacketEvent packetEvent) {
      if (packetEvent.getDirection() == PacketDirection.OUTGOING) {
         boolean flag = packetEvent.getPacket() instanceof C03PacketPlayer;
         if (flag && this.jumpPending && this.mode.getValue() == DoubleJumpMode.NCP) {
            this.jumpPending = false;
         }
      }
   }

   private void onUpdate(UpdateEvent updateEvent) {
      this.updateDisplayName();
      if (updateEvent.getState() == EventState.PRE) {
         switch ((DoubleJumpMode)this.mode.getValue()) {
            case NCP:
               boolean flag = ClientAccess.mc.thePlayer.isCollidedHorizontally;
               if (flag && this.isFalling()) {
                  if (ClientAccess.mc.thePlayer.onGround) {
                     this.jumpPending = true;
                     this.airJumped = false;
                  } else if (!this.airJumped) {
                     ClientAccess.mc.thePlayer.jump();
                     this.jumpPending = true;
                     this.airJumped = true;
                  }
               } else if (ClientAccess.mc.thePlayer.onGround) {
                  this.airJumped = false;
               }
               break;
            case VANILLA:
               boolean flag1 = Keyboard.isKeyDown(ClientAccess.mc.gameSettings.keyBindJump.getKeyCode());
               if (flag1 && this.isFalling()) {
                  ClientAccess.mc.thePlayer.jumpTicks = 0;
                  ClientAccess.mc.thePlayer.onGround = true;
               }
         }
      }
   }
}
