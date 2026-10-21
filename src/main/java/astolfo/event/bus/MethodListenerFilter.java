package astolfo.event.bus;

import astolfo.event.Event;
import astolfo.event.EventHandler;
import java.lang.reflect.Method;

public class MethodListenerFilter implements ListenerFilter<Method> {
   public boolean isListener(Method method) {
      Class[] classs = method.getParameterTypes();
      return ListenerFilter.super.isListener(method) && classs.length == 1 && Event.class.isAssignableFrom(classs[0]);
   }

   public boolean listensTo(Method method, Class<? extends Event> class2) {
      return this.isListener(method) && method.getDeclaredAnnotation(EventHandler.class).value() == class2;
   }
}
