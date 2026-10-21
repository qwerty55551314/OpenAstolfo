package astolfo.setting;

import astolfo.setting.parser.BooleanParser;
import astolfo.setting.parser.CharacterParser;
import astolfo.setting.parser.ClassParser;
import astolfo.setting.parser.EnumParser;
import astolfo.setting.parser.NumberParser;
import astolfo.setting.parser.StringParser;
import java.util.HashMap;
import java.util.Map;

public class Parsers {
   private Map<Class<?>, Parser<?>> parsers = new HashMap<>();

   public Parsers() {
      this.parsers.put(Class.class, new ClassParser());
      this.parsers.put(Boolean.class, new BooleanParser());
      this.parsers.put(Enum.class, new EnumParser());
      this.parsers.put(Number.class, new NumberParser());
      this.parsers.put(Character.class, new CharacterParser());
      this.parsers.put(String.class, new StringParser());
   }

   public Parser<?> get(Class<?> class2) {
      return this.parsers.get(class2);
   }
}
