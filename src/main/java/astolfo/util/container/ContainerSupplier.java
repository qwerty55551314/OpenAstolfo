package astolfo.util.container;

public interface ContainerSupplier<R extends Container<?>> {
   R getContainer();
}
