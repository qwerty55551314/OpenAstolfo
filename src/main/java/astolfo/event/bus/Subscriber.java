package astolfo.event.bus;

import astolfo.event.Event;
import astolfo.event.EventHandler;
import astolfo.event.Listener;
import java.lang.reflect.AnnotatedElement;

public final class Subscriber<O, E extends Event> implements Subscription<O, E> {
   private final O owner;
   private final SubscriptionFilter[] filters;
   private final int priority;
   private final Class<E> eventType;
   private Listener<E> listener;

   public Subscriber(O object, AnnotatedElement annotatedElement, EventHandler eventHandler, EventFilters eventFilters, InvocationStrategy invocationStrategy) {
      this(
         (O)object,
         (Class<E>)eventHandler.value(),
         invocationStrategy.createDispatcher((Class<E>)eventHandler.value(), object, annotatedElement),
         eventHandler.priority().getLevel(),
         eventFilters != null ? eventFilters.value() : null
      );
   }

   public Subscriber(O object, Class<E> class2, Listener<E> listener, int i, Class<? extends SubscriptionFilter>... classs) {
      this.owner = (O)object;
      this.eventType = class2;
      this.listener = listener;
      this.priority = i;
      this.filters = this.instantiateFilters(classs);
      if (false) {
         int i1 = 29;
         i1++;
      }
   }

   private SubscriptionFilter[] instantiateFilters(Class<? extends SubscriptionFilter>[] classs) {
      if (classs == null) {
         return null;
      } else {
         try {
            SubscriptionFilter[] asubscriptionfilter = new SubscriptionFilter[classs.length];

            for (int i = 0; i < classs.length; i++) {
               asubscriptionfilter[i] = (SubscriptionFilter)classs[i].newInstance();
            }

            return asubscriptionfilter;
         } catch (InstantiationException instantiationexception) {
            throw new RuntimeException("One or multiple of the specified filter classes can't be instantiated:\n" + instantiationexception.getMessage());
         } catch (IllegalAccessException illegalaccessexception) {
            throw new RuntimeException(
               "Access exception encountered creating filters for method/field in \""
                  + this.getOwner().toString()
                  + "\":\n"
                  + illegalaccessexception.getMessage()
            );
         }
      }
   }

   @Override
   public O getOwner() {
      return this.owner;
   }

   @Override
   public SubscriptionFilter[] getFilters() {
      return this.filters;
   }

   @Override
   public void invoke(E event) {
      this.listener.accept((E)event);
   }

   @Override
   public Class<E> getEventType() {
      return this.eventType;
   }

   @Override
   public int getPriority() {
      return this.priority;
   }

   @Override
   public Listener<E> getListener() {
      return this.listener;
   }

   public static boolean p() {
      return false;
   }
}
