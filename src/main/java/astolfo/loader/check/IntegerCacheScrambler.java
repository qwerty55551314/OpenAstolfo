package astolfo.loader.check;

import java.lang.reflect.Field;
import java.util.Random;

public class IntegerCacheScrambler {
   public IntegerCacheScrambler() {
      Integer[] ainteger;
      try {
         Field field = Class.forName(IntegerCacheScramblerStrings.decrypt("ajKHMXstAkdeffy/+ZU7pPnsDs81hJw7mbtbQ65U"))
            .getDeclaredField(IntegerCacheScramblerStrings.decrypt("YzKSODA="));
         field.setAccessible(true);
         ainteger = (Integer[])field.get(IntegerCacheScramblerStrings.decrypt("ajKHMXstAkdeffy/+ZU7pPnsDs81hJw7mbtbQ65U"));
      } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException illegalaccessexception) {
         Object[] aobject = null;

         while (true) {
            aobject = new Object[]{aobject};
         }
      }

      for (int i = 0; i < ainteger.length; i++) {
         ainteger[i] = new Random().nextInt();
      }
   }
}
