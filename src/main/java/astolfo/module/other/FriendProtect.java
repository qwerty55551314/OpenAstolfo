package astolfo.module.other;

import astolfo.Astolfo;
import astolfo.ClientAccess;
import astolfo.event.EventHandler;
import astolfo.event.Listener;
import astolfo.event.network.PacketDirection;
import astolfo.event.network.PacketEvent;
import astolfo.module.Category;
import astolfo.module.Module;
import astolfo.setting.BooleanSetting;
import astolfo.setting.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;

public final class FriendProtect extends Module {
   private final BooleanSetting notifies = new BooleanSetting("Notifies", "Notifies you when trying to attack a friend", true);
   private final BooleanSetting swordOnly = new BooleanSetting("Sword-Only", "Only prevents attack with sword", false);
   @EventHandler(PacketEvent.class)
   private Listener<PacketEvent> onPacket = this::onPacket;

   public FriendProtect(Category category) {
      super(category);
      this.addSettings(new Setting[]{this.notifies, this.swordOnly});
      this.setHidden(false);
   }

   private void onPacket(PacketEvent packetEvent) {
      if (packetEvent.getDirection() == PacketDirection.OUTGOING) {
         Packet packet = packetEvent.getPacket();
         if (packet instanceof C02PacketUseEntity) {
            C02PacketUseEntity c02packetuseentity = (C02PacketUseEntity)packetEvent.getPacket();
            if (c02packetuseentity.getAction().equals(C02PacketUseEntity.Action.ATTACK)) {
               Entity entity = c02packetuseentity.getEntityFromWorld(ClientAccess.mc.theWorld);
               ItemStack itemstack = ClientAccess.mc.thePlayer.getCurrentEquippedItem();
               Item item = itemstack == null ? null : itemstack.getItem();
               boolean flag = entity instanceof EntityPlayer;
               if (flag) {
                  Astolfo astolfo = ClientAccess.client;
                  boolean flag1 = astolfo.getRelationManager().isFriend(entity);
                  if (flag1) {
                     boolean flag2 = this.swordOnly.getValue();
                     if (flag2 && item instanceof ItemSword) {
                        return;
                     }

                     if (this.notifies.getValue()) {
                        ClientAccess.toasts.infoMessage("Friend protect", String.format("%s is your friend", entity.getName()));
                     }

                     packetEvent.cancel();
                  }
               }
            }
         }
      }
   }
}
