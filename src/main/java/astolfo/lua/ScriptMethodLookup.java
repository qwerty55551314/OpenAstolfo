package astolfo.lua;

import java.lang.reflect.Method;

public class ScriptMethodLookup {
   public static String findMethodNameByScriptName(Class<?> class2, String string) {
      for (Method method : class2.getDeclaredMethods()) {
         if (method.isAnnotationPresent(ScriptName.class)) {
            ScriptName scriptname = method.getAnnotation(ScriptName.class);
            if (scriptname.value().equals(string)) {
               return method.getName();
            }
         }
      }

      return null;
   }
}
