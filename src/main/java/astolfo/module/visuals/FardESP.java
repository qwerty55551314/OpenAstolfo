package astolfo.module.visuals;

import astolfo.event.EventHandler;
import astolfo.event.EventPriority;
import astolfo.event.Listener;
import astolfo.event.render.RenderEvent;
import astolfo.module.Category;
import astolfo.module.Module;

public final class FardESP extends Module {
   @EventHandler(value = RenderEvent.class, priority = EventPriority.LOWEST)
   private Listener<RenderEvent> onRender = renderEvent -> {};

   public FardESP() {
      super("FardESP", Category.VISUALS);
   }
}
