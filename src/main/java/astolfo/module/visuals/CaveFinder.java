package astolfo.module.visuals;

import astolfo.event.CancellableEvent;
import astolfo.event.EventHandler;
import astolfo.event.EventPriority;
import astolfo.event.Listener;
import astolfo.event.render.SetupTerrainEvent;
import astolfo.module.Category;
import astolfo.module.Module;
import org.lwjgl.opengl.GL11;

public final class CaveFinder extends Module {
   @EventHandler(value = SetupTerrainEvent.class, priority = EventPriority.LOWEST)
   private final Listener<SetupTerrainEvent> onSetupTerrain = CancellableEvent::cancel;

   public CaveFinder(Category category) {
      super(category);
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

   public static void cullFrontFaces() {
      GL11.glCullFace(1028);
   }

   public static void cullBackFaces() {
      GL11.glCullFace(1029);
   }
}
