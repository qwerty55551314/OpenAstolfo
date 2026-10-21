package astolfo.module.player;

import astolfo.Astolfo;
import astolfo.ClientAccess;
import astolfo.event.CancellableEvent;
import astolfo.event.EventHandler;
import astolfo.event.EventState;
import astolfo.event.Listener;
import astolfo.event.game.MouseClickEvent;
import astolfo.event.network.PacketDirection;
import astolfo.event.network.PacketEvent;
import astolfo.event.player.MoveEvent;
import astolfo.event.player.PushOutOfBlocksEvent;
import astolfo.event.player.TeleportEvent;
import astolfo.event.render.RenderModelEvent;
import astolfo.event.render.SetupTerrainEvent;
import astolfo.event.world.WorldLoadEvent;
import astolfo.module.Category;
import astolfo.module.Module;
import astolfo.module.ModuleManager;
import astolfo.module.movement.Flight;
import astolfo.setting.BooleanSetting;
import astolfo.setting.Setting;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public final class Freecam extends Module {
   private Entity viewedEntity;
   public static final BooleanSetting view = new BooleanSetting("View", "Let's you spectate other players", true);
   private final BooleanSetting staticFlight = new BooleanSetting("Static-Flight", "Doesnt use the ingame minecraft flying", false);
   @EventHandler(WorldLoadEvent.class)
   private Listener<WorldLoadEvent> onWorldLoad = this::onWorldLoad;
   @EventHandler(PacketEvent.class)
   private Listener<PacketEvent> onPacket = Freecam::onPacket;
   @EventHandler(TeleportEvent.class)
   private Listener<TeleportEvent> onTeleport = teleportEvent -> {
      if (teleportEvent.isLagback()) {
         teleportEvent.applyToEntity(ClientAccess.mc.theWorld.getEntityByID(-1337));
         teleportEvent.cancel();
      }
   };
   @EventHandler(MoveEvent.class)
   private Listener<MoveEvent> onMove = this::onMove;
   @EventHandler(RenderModelEvent.class)
   private Listener<RenderModelEvent> onRenderModel = this::onRenderLiving;
   @EventHandler(MouseClickEvent.class)
   private Listener<MouseClickEvent> onMouseClick = mouseClickEvent -> {
      if (mouseClickEvent.button == 1) {
         if (view.getValue()) {
            view.set(false);
         } else {
            Entity entity = ClientAccess.mc.objectMouseOver.entityHit;
            if (entity instanceof EntityPlayer) {
               this.viewedEntity = entity;
               view.set(true);
            }
         }
      }

      if (view.getValue()) {
         mouseClickEvent.cancel();
      }
   };
   @EventHandler(SetupTerrainEvent.class)
   private Listener<SetupTerrainEvent> onSetupTerrain = setupTerrainEvent -> {
      setupTerrainEvent.cancel();
      if (view.getValue() && this.viewedEntity != null) {
         if (Mouse.isButtonDown(0)) {
            ClientAccess.mc.thePlayer.setPosition(this.viewedEntity.posX, this.viewedEntity.posY, this.viewedEntity.posZ);
            this.viewedEntity.rotationYaw = ClientAccess.mc.thePlayer.rotationYaw;
            this.viewedEntity.rotationPitch = ClientAccess.mc.thePlayer.rotationPitch;
            this.viewedEntity.setRotationYawHead(ClientAccess.mc.thePlayer.getRotationYawHead());
         } else {
            ClientAccess.mc.thePlayer.copyLocationAndAnglesFrom(this.viewedEntity);
         }
      }
   };
   @EventHandler(PushOutOfBlocksEvent.class)
   private Listener<PushOutOfBlocksEvent> onPushOutOfBlocks = CancellableEvent::cancel;

   public Freecam(Category category) {
      super(category);
      this.addSettings(new Setting[]{view, this.staticFlight});
   }

   @Override
   public void onEnable() {
      if (ClientAccess.mc.thePlayer != null) {
         this.spawnFakePlayer(-1337);
         ClientAccess.mc.thePlayer.onGround = false;
         ClientAccess.mc.thePlayer.motionY = 0.2;
      }

      super.onEnable();
   }

   @Override
   public void onDisable() {
      Entity entity = ClientAccess.mc.theWorld.removeEntityFromWorld(-1337);
      if (entity != null) {
         ClientAccess.mc.thePlayer.copyLocationAndAnglesFrom(entity);
      }

      if (ClientAccess.mc.thePlayer != null) {
         ClientAccess.mc.thePlayer.noClip = true;
         ClientAccess.mc.thePlayer.capabilities.isFlying = false;
         ClientAccess.mc.thePlayer.motionX = ClientAccess.mc.thePlayer.motionY = ClientAccess.mc.thePlayer.motionZ = 0.0;
      }

      super.onDisable();
   }

   private EntityOtherPlayerMP spawnFakePlayer(int i) {
      if (ClientAccess.mc.theWorld == null) {
         return null;
      } else {
         if (i > 0) {
            i *= -1;
         }

         EntityOtherPlayerMP entityotherplayermp = new EntityOtherPlayerMP(ClientAccess.mc.theWorld, ClientAccess.mc.thePlayer.getGameProfile());
         entityotherplayermp.copyLocationAndAnglesFrom(ClientAccess.mc.thePlayer);
         entityotherplayermp.rotationYawHead = ClientAccess.mc.thePlayer.rotationYawHead;
         entityotherplayermp.inventory.copyInventory(ClientAccess.mc.thePlayer.inventory);
         ClientAccess.mc.theWorld.addEntityToWorld(i, entityotherplayermp);
         return entityotherplayermp;
      }
   }

   private void onRenderLiving(RenderModelEvent renderModelEvent) {
      if (renderModelEvent.getState() == EventState.PRE && renderModelEvent.getEntity().getEntityId() == -1337) {
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, (ClientAccess.mc.thePlayer.getDistanceToEntity(renderModelEvent.getEntity()) - 0.2F) / 2.0F);
      }

      if (renderModelEvent.getEntity() == this.viewedEntity) {
         renderModelEvent.cancel();
      }
   }

   private void onMove(MoveEvent moveEvent) {
      Astolfo astolfo = ClientAccess.client;
      ModuleManager modulemanager1 = astolfo.getModuleManager();
      Class<Flight> oclass = Flight.class;
      ModuleManager modulemanager = modulemanager1;
      boolean flag = modulemanager.get(oclass).isEnabled();
      if (!flag && !this.staticFlight.getValue()) {
         ClientAccess.mc.thePlayer.capabilities.isFlying = true;
      }

      boolean flag1 = this.staticFlight.getValue();
      if (flag1) {
         ClientAccess.mc.thePlayer.motionY = 0.0;
         if (ClientAccess.mc.inGameHasFocus) {
            boolean flag2 = Keyboard.isKeyDown(ClientAccess.mc.gameSettings.keyBindJump.getKeyCode());
            if (flag2) {
               ClientAccess.mc.thePlayer.motionY = 3.0;
            }

            if (Keyboard.isKeyDown(ClientAccess.mc.gameSettings.keyBindSneak.getKeyCode())) {
               ClientAccess.mc.thePlayer.motionY = -3.0;
            }
         }
      }

      ClientAccess.mc.thePlayer.noClip = true;
   }

   private static void onPacket(PacketEvent packetEvent) {
      if (packetEvent.getDirection() == PacketDirection.OUTGOING) {
         boolean flag = packetEvent.getPacket() instanceof C0BPacketEntityAction;
         if (flag) {
            C0BPacketEntityAction c0bpacketentityaction = (C0BPacketEntityAction)packetEvent.getPacket();
            if (c0bpacketentityaction.getAction() == C0BPacketEntityAction.Action.OPEN_INVENTORY) {
               packetEvent.cancel();
            }
         } else if (packetEvent.getPacket() instanceof C08PacketPlayerBlockPlacement
            || packetEvent.getPacket() instanceof C03PacketPlayer
            || packetEvent.getPacket() instanceof C02PacketUseEntity
            || packetEvent.getPacket() instanceof C07PacketPlayerDigging) {
            packetEvent.cancel();
         }
      }
   }

   private void onWorldLoad(WorldLoadEvent worldLoadEvent) {
      this.setEnabled(false);
   }
}
