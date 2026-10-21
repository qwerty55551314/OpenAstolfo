package astolfo.module.visuals;

import astolfo.event.CancellableEvent;
import astolfo.event.EventHandler;
import astolfo.event.EventPriority;
import astolfo.event.Listener;
import astolfo.event.player.PushOutOfBlocksEvent;
import astolfo.event.render.RenderEvent;
import astolfo.event.render.RenderType;
import astolfo.event.render.SetupTerrainEvent;
import astolfo.module.Category;
import astolfo.module.Module;
import astolfo.setting.BooleanSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;

public final class NoRender extends Module {
   public static final BooleanSetting smart = new BooleanSetting("Smart", "smart entity rendering hack", true);
   private final BooleanSetting tnt = new BooleanSetting("TNT", "Removes Tnt", true);
   public static final BooleanSetting visGraph = new BooleanSetting("Vis-Graph", "", true);
   public static final BooleanSetting culling = new BooleanSetting("Culling", "disables culling", true);
   @EventHandler(value = RenderEvent.class, priority = EventPriority.LOWEST)
   private Listener<RenderEvent> onRender = renderEvent -> {
      if (this.tnt.getValue() && renderEvent.getType() == RenderType.WORLD) {
         for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity instanceof EntityTNTPrimed) {
               mc.theWorld.removeEntity(entity);
            }
         }
      }
   };
   @EventHandler(SetupTerrainEvent.class)
   private Listener<SetupTerrainEvent> onSetupTerrain = setupTerrainEvent -> {
      if (culling.getValue()) {
         setupTerrainEvent.cancel();
      }
   };
   @EventHandler(PushOutOfBlocksEvent.class)
   private Listener<PushOutOfBlocksEvent> onPushOutOfBlocks = CancellableEvent::cancel;

   public NoRender(Category category) {
      super(category);
      this.addSettings(smart, visGraph, this.tnt, culling);
      this.setHidden(false);
   }

   @Override
   public void onEnable() {
      mc.renderGlobal.loadRenderers();
      super.onEnable();
   }

   @Override
   public void onDisable() {
      mc.renderGlobal.loadRenderers();
      super.onDisable();
   }
}
