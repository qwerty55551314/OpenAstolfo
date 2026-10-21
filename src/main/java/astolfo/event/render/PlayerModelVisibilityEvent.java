package astolfo.event.render;

import astolfo.event.CancellableEvent;
import net.minecraft.entity.Entity;

public class PlayerModelVisibilityEvent extends CancellableEvent {
   private Entity entity;

   public PlayerModelVisibilityEvent(Entity entity) {
      this.entity = entity;
   }

   public Entity getEntity() {
      return this.entity;
   }
}
