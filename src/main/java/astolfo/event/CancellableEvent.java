package astolfo.event;

public abstract class CancellableEvent implements Event, Cancellable {
   private boolean cancelled;
   private static String[] w;

   @Override
   public boolean isCancelled() {
      return this.cancelled;
   }

   @Override
   public void setCancelled(boolean bl) {
      this.cancelled = bl;
   }

   @Override
   public void cancel() {
      this.cancelled = true;
   }
}
