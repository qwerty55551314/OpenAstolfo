package astolfo.util.container;

public abstract class MutableContainerOwner<T> extends ContainerOwner<T> {
   public CollectionMutator<T> mutator;

   public MutableContainerOwner() {
      if (false) {
         this.mutator = new CollectionMutator<>(super.container);
         int i = 29;
         i++;
      } else {
         this.mutator = new CollectionMutator<>(super.container);
      }
   }

   public CollectionContainer<T> getOwnedContainer() {
      return super.container;
   }
}
