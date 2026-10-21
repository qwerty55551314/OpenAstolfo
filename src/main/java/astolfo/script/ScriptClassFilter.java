package astolfo.script;

import jdk.nashorn.api.scripting.ClassFilter;

public class ScriptClassFilter implements ClassFilter {
   private static String X;

   public boolean exposeToScripts(String string) {
      boolean flag = string.contains("org.lwjgl");
      if (!flag || string.equals("org.lwjgl.Sys")) {
         boolean flag1 = string.equals("java.lang.Math");
         if (!flag1 && !string.equals("java.lang.Array") && !string.equals("java.util.Timer") && !string.equals("java.util.concurrent.TimeUnit")) {
            return false;
         }
      }

      return true;
   }

   public static void q(String string) {
      X = string;
   }

   public static String D() {
      return X;
   }

   static {
      q((String)null);
   }
}
