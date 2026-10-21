package astolfo.module.visuals;

import astolfo.ClientAccess;
import astolfo.event.EventHandler;
import astolfo.event.Listener;
import astolfo.event.render.RenderEvent;
import astolfo.event.render.RenderType;
import astolfo.event.world.WorldLoadEvent;
import astolfo.module.Category;
import astolfo.module.Module;
import astolfo.render.DamageParticle;
import astolfo.setting.BooleanSetting;
import astolfo.setting.ColorSetting;
import astolfo.setting.NumberSetting;
import astolfo.setting.Setting;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public final class DamageParticles extends Module {
   private final Map<Integer, Float> lastHealthById = new HashMap<>();
   private final NumberSetting<Integer> duration = new NumberSetting<>("Duration", "Duration of each particle", 20, 1, 70);
   private final NumberSetting<Float> gravity = new NumberSetting<>("Gravity", "Gravity applied", 0.1F, 0.01F, 1.0F);
   private final NumberSetting<Float> size = new NumberSetting<>("Size", "Size of each particle", 3.0F, 0.1F, 10.0F);
   private final BooleanSetting grow = new BooleanSetting("Grow", "Grows particles", true);
   private final BooleanSetting random = new BooleanSetting("Random", "Random position", true);
   private final BooleanSetting staticSetting = new BooleanSetting("Static", "Stays", false);
   private final BooleanSetting hearts = new BooleanSetting("Hearts", "Show hearts inside of full health value", false);
   private final ColorSetting healColor = new ColorSetting("Heal-Color", "Heal-Color", Color.GREEN);
   private final ColorSetting damageColor = new ColorSetting("Damage-Color", "Damage-Color", Color.RED);
   @EventHandler(RenderEvent.class)
   private final Listener<RenderEvent> onRender = this::onRender;
   @EventHandler(WorldLoadEvent.class)
   private final Listener<WorldLoadEvent> onWorldLoad = this::onWorldLoad;

   public DamageParticles(Category category) {
      super(category);
      this.addSettings(
         new Setting[]{this.healColor, this.damageColor, this.duration, this.gravity, this.size, this.grow, this.random, this.staticSetting, this.hearts}
      );
      this.setHidden(false);
   }

   private void spawnDamageParticle(Entity entity, int i) {
      if (i != 0) {
         World world = entity.worldObj;
         double d0 = this.random.getValue() ? world.rand.nextGaussian() * 0.02 : 0.0;
         double d1 = 0.5;
         double d2 = this.random.getValue() ? world.rand.nextGaussian() * 0.02 : 0.0;
         DamageParticle damageparticle = new DamageParticle(
            i,
            world,
            entity.posX,
            entity.posY + (double)entity.height,
            entity.posZ,
            d0,
            d1,
            d2,
            this.gravity.getValue(),
            this.size.getValue(),
            this.duration.getValue(),
            this.grow.getValue(),
            this.staticSetting.getValue(),
            this.hearts.getValue(),
            this.healColor.getValue(),
            this.damageColor.getValue()
         );
         ClientAccess.mc.effectRenderer.addEffect(damageparticle);
      }
   }

   private void onWorldLoad(WorldLoadEvent worldLoadEvent) {
      this.lastHealthById.clear();
   }

   private void onRender(RenderEvent renderEvent) {
      if (renderEvent.getType() == RenderType.WORLD) {
         for (Entity entity : ClientAccess.mc.theWorld.loadedEntityList) {
            boolean flag = entity instanceof EntityLivingBase;
            if (flag && !(entity instanceof EntityPlayerSP)) {
               EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
               boolean flag1 = Double.isNaN((double)entitylivingbase.getHealth());
               if (!flag1) {
                  int i = (int)Math.ceil((double)entitylivingbase.getHealth());
                  Float f = this.lastHealthById.get(entitylivingbase.getEntityId());
                  if (f != null) {
                     int j = (int)Math.ceil((double)f.floatValue());
                     this.spawnDamageParticle(entity, j - i);
                  }

                  this.lastHealthById.put(entity.getEntityId(), entitylivingbase.getHealth());
               }
            }
         }
      }
   }
}
