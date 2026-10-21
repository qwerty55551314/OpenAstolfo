package astolfo.gui.hud;

import astolfo.Astolfo;
import astolfo.gui.component.Component;
import astolfo.module.combat.KillAura;
import astolfo.module.hidden.BotDetector;
import astolfo.module.other.AutoHypixel;
import astolfo.render.RenderUtil;
import astolfo.setting.MapSetting;
import astolfo.util.GameUtil;
import java.awt.Color;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;

public class RadarHud extends Component {
   private MapSetting entities;
   private int spacing;
   private final int friendColor = new Color(15, 176, 245, 255).getRGB();
   private final int defaultColor = new Color(200, 200, 200).getRGB();
   private final int enemyColor = new Color(200, 60, 71).getRGB();

   public RadarHud(float f, float g, int i, int j) {
      super("radar", f, g, i, j);
      this.setDraggable(true);
   }

   @Override
   public void onClick(int i) {
   }

   @Override
   public void render(int i, int j) {
      float f = this.getX();
      float f1 = this.getYPos();
      float f2 = (float)this.getWidth();
      float f3 = (float)this.getHeight();
      RenderUtil.drawRect((double)(f - 0.5F), (double)(f1 - 0.5F), (double)(f + f2 + 0.5F), (double)(f1 + f3 + 0.5F), new Color(0, 0, 0, 150).getRGB());
      RenderUtil.drawThinBorderedRect(f, f1, f + f2, f1 + f3, new Color(17, 17, 17, 50).getRGB(), new Color(17, 17, 17, 197).getRGB());
      RenderUtil.drawRect((double)f, (double)f1, (double)(f + f2), (double)(f1 + 0.5F), client.getColorSetting().getValue().getRGB());
      RenderUtil.drawRect((double)f, (double)(f1 + 0.5F), (double)(f + f2), (double)(f1 + 1.0F), client.getColorSetting().getValue().darker().getRGB());
      RenderUtil.drawLine(1.0F, f + f2 / 2.0F, f1 + f3, f + f2 / 2.0F, f1 + 1.0F, -1);
      RenderUtil.drawLine(1.0F, f, f1 + f3 / 2.0F, f + f2, f1 + f3 / 2.0F, -1);

      for (Entity entity : mc.theWorld.loadedEntityList) {
         if (this.shouldDisplay(entity)) {
            float f4 = (float)Math.toRadians((double)mc.thePlayer.rotationYaw);
            float f5 = (float)(mc.thePlayer.posX - entity.posX);
            float f6 = (float)(mc.thePlayer.posZ - entity.posZ);
            float f7 = (float)((double)f6 * Math.cos((double)f4) - (double)f5 * Math.sin((double)f4)) / (float)this.spacing;
            float f8 = (float)((double)f5 * Math.cos((double)f4) + (double)f6 * Math.sin((double)f4)) / (float)this.spacing;
            float f9 = f + f2 / 2.0F + f8;
            float f10 = f1 + f3 / 2.0F + f7;
            float f11;
            int i1 = (f11 = f9 - f) == 0.0F ? 0 : (f11 < 0.0F ? -1 : 1);
            if (i1 < 0) {
               f9 = f;
            }

            float f12;
            int j1 = (f12 = f9 - (f + f2 - 3.0F)) == 0.0F ? 0 : (f12 < 0.0F ? -1 : 1);
            if (j1 > 0) {
               f9 = f + f2 - 3.0F;
            }

            float f13;
            int k = (f13 = f10 - (f1 + 0.5F)) == 0.0F ? 0 : (f13 < 0.0F ? -1 : 1);
            if (k < 0) {
               f10 = f1 + 1.5F;
            }

            if (f10 >= f1 + f3 - 3.0F) {
               f10 = f1 + f3 - 3.0F;
            }

            RenderUtil.drawRect((double)f9, (double)f10, (double)(f9 + 2.0F), (double)(f10 + 2.0F), this.getEntityColor(entity));
         }
      }
   }

   private boolean shouldDisplay(Entity entity) {
      if ((!(entity instanceof EntityPlayer) || BotDetector.bots.contains(entity) || entity == mc.thePlayer)
         && (entity != mc.thePlayer || mc.gameSettings.thirdPersonView <= 0 || !this.entities.getSetting("Local").asBoolean())) {
         boolean flag = entity instanceof EntityMob;
         if (!flag || !this.entities.getSetting("Monsters").asBoolean()) {
            boolean flag1 = entity instanceof EntityAnimal;
            if (!flag1 || !this.entities.getSetting("Animals").asBoolean()) {
               boolean flag2 = entity instanceof EntityTameable;
               if (!flag2 || !this.entities.getSetting("Passives").asBoolean()) {
                  boolean flag3 = entity instanceof EntityItem;
                  if (!flag3 || !this.entities.getSetting("Items").asBoolean()) {
                     boolean flag4 = entity instanceof EntityAgeable;
                     if (flag4) {
                        boolean flag5 = this.entities.getSetting("Passives").asBoolean();
                        if (flag5) {
                           return true;
                        }
                     }

                     return false;
                  }
               }
            }
         }

         return true;
      } else if (GameUtil.isFullyInvisible(entity) && !this.entities.getSetting("Invisibles").asBoolean()) {
         return false;
      } else {
         return GameUtil.isOnHypixel() && GameUtil.scoreboardTitleContains("PIT") && AutoHypixel.pitSpawnY != 0.0 && entity.posY > AutoHypixel.pitSpawnY
            ? false
            : this.entities.getSetting("Players").asBoolean();
      }
   }

   private int getEntityColor(Entity entity) {
      Astolfo astolfo = client;
      boolean flag = astolfo.getRelationManager().isEnemy(entity);
      if (flag) {
         return this.enemyColor;
      } else {
         Astolfo astolfo1 = client;
         boolean flag1 = astolfo1.getRelationManager().isFriend(entity);
         if (flag1) {
            return this.friendColor;
         } else {
            boolean flag2 = entity instanceof EntityItem;
            if (flag2) {
               return Color.WHITE.getRGB();
            } else {
               boolean flag3 = AutoHypixel.bountiedPlayers.contains(entity);
               if (flag3) {
                  return Color.YELLOW.getRGB();
               } else {
                  return entity == KillAura.target ? client.getColorSetting().getValue().getRGB() : this.defaultColor;
               }
            }
         }
      }
   }

   public void setEntities(MapSetting mapSetting) {
      this.entities = mapSetting;
   }

   public void setSpacing(int i) {
      this.spacing = i;
   }
}
