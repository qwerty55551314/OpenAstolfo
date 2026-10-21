package astolfo.module.combat;

import astolfo.Astolfo;
import astolfo.ClientAccess;
import astolfo.event.EventHandler;
import astolfo.event.Listener;
import astolfo.event.render.RenderEvent;
import astolfo.event.render.RenderType;
import astolfo.module.Category;
import astolfo.module.Module;
import astolfo.setting.BooleanSetting;
import astolfo.setting.MapSetting;
import astolfo.setting.MapSettingBuilder;
import astolfo.setting.NumberSetting;
import astolfo.setting.Setting;
import astolfo.util.GameUtil;
import astolfo.util.Rotation;
import astolfo.util.Rotator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vector3d;
import org.lwjgl.input.Mouse;

public final class AimAssist extends Module {
   private final MapSetting checks = new MapSettingBuilder("Checks")
      .ordered()
      .description("entity checks")
      .add("Invisible", new BooleanSetting("Invisible", "Aims at invisible players", false))
      .add("Teams", new BooleanSetting("Teams", "Doesnt aim at teammates", false))
      .build();
   private final NumberSetting<Double> range = new NumberSetting<>("Range", "Distance of blocks", 4.0, 3.0, 6.0);
   private final NumberSetting<Integer> fov = new NumberSetting<>("FOV", "Field of view", 360, 0, 360);
   private final NumberSetting<Integer> speed = new NumberSetting<>("Speed", "Speed of AimAssist", 1, 0, 10);
   private final BooleanSetting onMouse = new BooleanSetting("On-Mouse", "On mouse only", true);
   private final Rotator rotator = new Rotator(false, 0.0F);
   private int resetTicks;
   @EventHandler(RenderEvent.class)
   private Listener<RenderEvent> onRender = this::onRender;

   public AimAssist(Category category) {
      super(category);
      this.addSettings(new Setting[]{this.checks, this.range, this.fov, this.speed, this.onMouse});
   }

   private boolean isValidTarget(EntityPlayer entityPlayer) {
      boolean flag = ClientAccess.mc.thePlayer.canAttackPlayer(entityPlayer);
      if (!flag) {
         return false;
      } else {
         Astolfo astolfo = ClientAccess.client;
         boolean flag1 = astolfo.getRelationManager().isFriend(entityPlayer);
         if (flag1) {
            return false;
         } else {
            if (GameUtil.isOnSameTeam(ClientAccess.mc.thePlayer, entityPlayer)) {
               boolean flag2 = this.checks.getSetting("Teams").asBoolean();
               if (flag2) {
                  return false;
               }
            }

            if (entityPlayer != ClientAccess.mc.thePlayer) {
               boolean flag3 = GameUtil.isFullyInvisible(entityPlayer);
               if (!flag3 || this.checks.getSetting("Invisible").asBoolean()) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   public float[] getRotationDifference(Rotation rotation) {
      return new float[]{
         MathHelper.wrapAngleTo180_float(ClientAccess.mc.thePlayer.rotationYaw) - rotation.getYaw(),
         ClientAccess.mc.thePlayer.rotationPitch - rotation.getPitch()
      };
   }

   private AimTarget findBestTarget() {
      AimTarget aimtarget = null;
      float f = (float)this.fov.getValue().intValue() / 2.0F;

      for (EntityPlayer entityplayer : ClientAccess.mc.theWorld.playerEntities) {
         if (this.isValidTarget(entityplayer)) {
            AimTarget aimtarget1 = this.createAimTarget(entityplayer);
            if (aimtarget1 != null) {
               float f1 = AimTarget.angleDistanceOf(aimtarget1);
               if (f1 < f) {
                  aimtarget = aimtarget1;
                  f = AimTarget.angleDistanceOf(aimtarget1);
               }
            }
         }
      }

      return aimtarget;
   }

   private AimTarget createAimTarget(EntityPlayer entityPlayer) {
      double d0 = this.range.asDouble();
      if ((double)ClientAccess.mc.thePlayer.getDistanceToEntity(entityPlayer) <= d0) {
         Vector3d vector3d = new Vector3d(
            ClientAccess.mc.thePlayer.posX, ClientAccess.mc.thePlayer.posY + (double)ClientAccess.mc.thePlayer.getEyeHeight(), ClientAccess.mc.thePlayer.posZ
         );
         Vector3d vector3d1 = new Vector3d(entityPlayer.posX, entityPlayer.posY + (double)entityPlayer.height * 0.5 / 1.0, entityPlayer.posZ);
         Rotation rotation = this.rotator.rotationTo(vector3d1, vector3d);
         return new AimTarget(entityPlayer, rotation, (float)KillAura.rotationDistance(rotation));
      } else {
         return null;
      }
   }

   private void onRender(RenderEvent renderEvent) {
      if (renderEvent.getType() == RenderType.WORLD) {
         Minecraft minecraft = ClientAccess.mc;
         if (minecraft.thePlayer == null) {
            return;
         }

         GuiScreen guiscreen = ClientAccess.mc.currentScreen;
         if (guiscreen != null) {
            return;
         }

         if (this.onMouse.getValue() && !Mouse.isButtonDown(0)) {
            return;
         }

         this.resetTicks = 0;
         if (ClientAccess.mc.objectMouseOver != null && ClientAccess.mc.objectMouseOver.entityHit != null) {
            return;
         }

         AimTarget aimtarget = this.findBestTarget();
         if (aimtarget != null) {
            float[] afloat = this.getRotationDifference(AimTarget.rotationOf(aimtarget));
            int i = this.speed.getValue();
            int j = this.speed.getValue();
            float f6;
            int k = (f6 = afloat[0] - 0.0F) == 0.0F ? 0 : (f6 < 0.0F ? -1 : 1);
            if (k > 0) {
               i = -i;
            }

            if (afloat[1] < 0.0F) {
               j = -j;
            }

            float f = ClientAccess.mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
            float f1 = f * f * f * 8.0F;
            float f2 = (float)i * f1;
            float f3 = (float)j * f1;
            EntityPlayerSP entityplayersp1 = ClientAccess.mc.thePlayer;
            float f7 = Math.abs(afloat[1]);
            float f5 = 10.0F;
            float f4 = f7;
            EntityPlayerSP entityplayersp = entityplayersp1;
            entityplayersp.setAngles(f2, f4 > f5 ? f3 * 1.0F : 0.0F);
         }
      }
   }
}
