package astolfo.loader.check;

import astolfo.util.collection.ShapedMap;
import java.lang.reflect.InvocationTargetException;

public final class GuardedConstructor {
   public static final ShapedMap<Integer, GuardedConstructor> instances = new ShapedMap<>();
   private final int id;

   public GuardedConstructor(int i) {
      this.id = i;
   }

   public void clearProperties() {
      System.setProperties(null);
   }

   public static Object newInstance(Class<?> class2, Object... objects) {
      if (!instances.isEmpty()) {
         instances.get(objects.length - 1).clearProperties();
      }

      try {
         return class2.getConstructors()[0].newInstance(objects);
      } catch (InvocationTargetException | IllegalAccessException | InstantiationException instantiationexception) {
         throw new RuntimeException();
      }
   }
}
