package astolfo.event.bus;

import astolfo.event.Event;
import java.util.function.BiPredicate;

public abstract class SubscriptionFilter<E extends Event> implements BiPredicate<Subscription<?, E>, E> {
}
