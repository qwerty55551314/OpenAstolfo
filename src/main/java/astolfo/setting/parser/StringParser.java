package astolfo.setting.parser;

import astolfo.setting.Parser;
import java.util.Optional;

public class StringParser implements Parser<String> {
   private static boolean I;

   @Override
   public Optional<String> parse(String string, Class<? extends String> class2) {
      return Optional.of(string);
   }

   public static void N(boolean flag) {
      I = flag;
   }

   public static boolean k() {
      return I;
   }

   public static boolean B() {
      return true;
   }
}
