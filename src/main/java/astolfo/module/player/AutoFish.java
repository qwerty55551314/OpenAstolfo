package astolfo.module.player;

import astolfo.ClientAccess;
import astolfo.event.EventHandler;
import astolfo.event.EventState;
import astolfo.event.Listener;
import astolfo.event.network.PacketEvent;
import astolfo.event.player.MotionEvent;
import astolfo.event.player.UpdateEvent;
import astolfo.module.Category;
import astolfo.module.Module;
import astolfo.setting.EnumSetting;
import astolfo.setting.Setting;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.util.EnumChatFormatting;
import org.apache.commons.lang3.StringUtils;

public final class AutoFish extends Module {
   private final EnumSetting<AutoFishMode> mode = new EnumSetting<>("Mode", "Set your AutoFish mode", AutoFishMode.SOUND);
   private boolean fishBiting;
   private boolean rodCast;
   @EventHandler(UpdateEvent.class)
   private Listener<UpdateEvent> onUpdate = this::onUpdate;
   @EventHandler(MotionEvent.class)
   private Listener<MotionEvent> onMotion = this::onMotion;
   @EventHandler(PacketEvent.class)
   private Listener<PacketEvent> onPacket = this::onPacket;

   public AutoFish(Category category) {
      super(category);
      this.addSettings(new Setting[]{this.mode});
   }

   @Override
   public void updateDisplayName() {
      this.setDisplayName("Auto Fish " + EnumChatFormatting.GRAY + AutoFishMode.displayNameOf(this.mode.getValue()));
      super.updateDisplayName();
   }

   private EntityFishHook getFishHook() {
      if (this.rodCast) {
         Minecraft minecraft = ClientAccess.mc;
         if (minecraft.thePlayer.fishEntity != null) {
            return ClientAccess.mc.thePlayer.fishEntity;
         }

         for (Entity entity : ClientAccess.mc.theWorld.loadedEntityList) {
            if (entity instanceof EntityFishHook) {
               EntityPlayer entityplayer = ((EntityFishHook)entity).angler;
               if (entityplayer == ClientAccess.mc.thePlayer) {
                  return (EntityFishHook)entity;
               }
            }
         }
      }

      return null;
   }

   private boolean a_boolean() {
      EntityPlayerSP entityplayersp = ClientAccess.mc.thePlayer;
      if (entityplayersp != null) {
         ItemStack itemstack = ClientAccess.mc.thePlayer.getCurrentEquippedItem();
         if (itemstack != null) {
            boolean flag = ClientAccess.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemFishingRod;
            if (flag) {
               return true;
            }
         }
      }

      return false;
   }

   private void onPacket(PacketEvent packetEvent) {
      switch (packetEvent.getDirection()) {
         case RECEIVED:
            if (packetEvent.getPacket() instanceof S29PacketSoundEffect) {
               if (this.mode.getValue() != AutoFishMode.SOUND) {
                  return;
               }

               S29PacketSoundEffect s29packetsoundeffect = (S29PacketSoundEffect)packetEvent.getPacket();
               boolean flag1 = s29packetsoundeffect.getSoundName().equals("random.splash");
               if ((flag1 || s29packetsoundeffect.getSoundName().equals("game.neutral.swim.splash")) && ClientAccess.mc.thePlayer.fishEntity != null) {
                  this.fishBiting = true;
               }
            }
            break;
         case OUTGOING:
            boolean flag2 = packetEvent.getPacket() instanceof C08PacketPlayerBlockPlacement;
            if (flag2) {
               boolean flag = this.a_boolean();
               if (flag && ((C08PacketPlayerBlockPlacement)packetEvent.getPacket()).getPlacedBlockDirection() == 255) {
                  this.rodCast = !this.rodCast;
               }
            }

            if (packetEvent.getPacket() instanceof C09PacketHeldItemChange) {
               this.rodCast = false;
            }
      }
   }

   private void onMotion(MotionEvent motionEvent) {
      if (motionEvent.getState() == EventState.PRE) {
         boolean flag = this.a_boolean();
         if (flag) {
            if (!this.rodCast) {
               Minecraft minecraft = ClientAccess.mc;
               if (minecraft.objectMouseOver.getBlockPos() != null) {
                  boolean flag1 = ClientAccess.mc.theWorld.getBlockState(ClientAccess.mc.objectMouseOver.getBlockPos()).getBlock() instanceof BlockLiquid;
                  if (flag1) {
                     ClientAccess.mc.rightClickMouse();
                  }
               }
            } else {
               EntityFishHook entityfishhook = this.getFishHook();
               if (entityfishhook != null) {
                  switch ((AutoFishMode)this.mode.getValue()) {
                     case MOTION:
                        if (entityfishhook.isInWater() && entityfishhook.ticksExisted > 30 && entityfishhook.motionY <= -0.06) {
                           this.fishBiting = true;
                        }
                     case TEXT:
                        if (!StringUtils.isEmpty(ClientAccess.mc.ingameGUI.displayedSubTitle)
                           && ClientAccess.mc.ingameGUI.displayedSubTitle.toLowerCase().contains("reel")) {
                           this.fishBiting = true;
                        }
                  }
               }
            }

            if (this.fishBiting) {
               ClientAccess.toasts.successMessage("Found fish", "Caught fish!");
               ClientAccess.mc.rightClickMouse();
               this.fishBiting = this.rodCast = false;
            }
         }
      }
   }

   private void onUpdate(UpdateEvent updateEvent) {
      this.updateDisplayName();
   }
}
