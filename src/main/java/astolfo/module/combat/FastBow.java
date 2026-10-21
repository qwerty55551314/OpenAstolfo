package astolfo.module.combat;

import astolfo.event.EventHandler;
import astolfo.event.EventState;
import astolfo.event.Listener;
import astolfo.event.player.UpdateEvent;
import astolfo.module.Category;
import astolfo.module.Module;
import astolfo.setting.EnumSetting;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;

public final class FastBow extends Module {
   private final EnumSetting<FastBowMode> mode = new EnumSetting<>("Mode", "Set your FastBow mode", FastBowMode.VANILLA);
   @EventHandler(UpdateEvent.class)
   private Listener<UpdateEvent> onUpdate = updateEvent -> {
      this.updateDisplayName();
      if (updateEvent.getState() == EventState.PRE && this.mode.getValue() == FastBowMode.VANILLA && this.canFastShoot()) {
         mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
         mc.thePlayer.inventory.getCurrentItem().getItem().onItemRightClick(mc.thePlayer.inventory.getCurrentItem(), mc.theWorld, mc.thePlayer);

         for (int i = 0; i < 20; i++) {
            mc.thePlayer.sendQueue.sendPacketNoEvent(new C03PacketPlayer(true));
         }

         mc.thePlayer
            .sendQueue
            .sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
         mc.thePlayer.inventory.getCurrentItem().getItem().onPlayerStoppedUsing(mc.thePlayer.inventory.getCurrentItem(), mc.theWorld, mc.thePlayer, 10);
      }
   };

   public FastBow(Category category) {
      super(category);
      this.addSettings(this.mode);
   }

   @Override
   public void updateDisplayName() {
      this.setDisplayName("Fast Bow " + EnumChatFormatting.GRAY + FastBowMode.displayNameOf(this.mode.getValue()));
      super.updateDisplayName();
   }

   private boolean canFastShoot() {
      return mc.thePlayer.inventory.getCurrentItem() != null
         && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow
         && mc.thePlayer.getHealth() > 0.0F
         && mc.thePlayer.onGround
         && mc.gameSettings.keyBindUseItem.pressed;
   }
}
