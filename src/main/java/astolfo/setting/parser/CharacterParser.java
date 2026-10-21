package astolfo.setting.parser;

import astolfo.setting.Parser;
import java.util.Optional;

public class CharacterParser implements Parser<Character> {
   private static final String b = "Can't parse a value which is larger than I expected";

   @Override
   public Optional<Character> parse(String string, Class<? extends Character> class2) {
      if (string.length() > 1) {
         try {
            throw new Exception(b);
         } catch (Exception exception) {
            exception.printStackTrace();
            return Optional.empty();
         }
      } else {
         Character character = string.charAt(0);
         return Optional.ofNullable(character);
      }
   }

   public Optional<Character[]> parseArray(String string, Class<? extends Character> class2) {
      int i = string.length();
      Character[] acharacter = new Character[i];

      for (int j = 0; j < i; j++) {
         acharacter[j] = new Character(string.charAt(j));
      }

      return Optional.ofNullable(acharacter);
   }
}
