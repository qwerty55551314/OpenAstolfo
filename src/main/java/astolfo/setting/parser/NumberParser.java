package astolfo.setting.parser;

import astolfo.setting.Parser;
import java.util.Optional;

public class NumberParser implements Parser<Number> {
   private static final String b = ".0";

   @Override
   public Optional<Number> parse(String string, Class<? extends Number> class2) {
      try {
         Class<Byte> oclass = Byte.class;
         if (class2 == oclass) {
            return Optional.ofNullable(Byte.parseByte(string));
         } else {
            Class<Short> oclass1 = Short.class;
            if (class2 == oclass1) {
               return Optional.ofNullable(Short.parseShort(string));
            } else {
               Class<Integer> oclass2 = Integer.class;
               if (class2 == oclass2) {
                  return Optional.ofNullable(Integer.parseInt(string.replace(b, "")));
               } else {
                  Class<Long> oclass3 = Long.class;
                  if (class2 == oclass3) {
                     return Optional.ofNullable(Long.parseLong(string));
                  } else {
                     Class<Float> oclass4 = Float.class;
                     if (class2 == oclass4) {
                        return Optional.ofNullable(Float.parseFloat(string));
                     } else {
                        return class2 == Double.class ? Optional.ofNullable(Double.parseDouble(string)) : Optional.empty();
                     }
                  }
               }
            }
         }
      } catch (Exception exception) {
         return Optional.empty();
      }
   }
}
