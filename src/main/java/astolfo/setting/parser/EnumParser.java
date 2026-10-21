package astolfo.setting.parser;

import astolfo.setting.Parser;
import java.util.Optional;

public class EnumParser implements Parser<Enum<?>> {
   @Override
   public Optional<Enum<?>> parse(String string, Class<? extends Enum<?>> class2) {
      for (Enum oenum : (Enum[])class2.getEnumConstants()) {
         if (oenum.name().equalsIgnoreCase(string)) {
            return Optional.ofNullable(oenum);
         }
      }

      return Optional.empty();
   }
}
