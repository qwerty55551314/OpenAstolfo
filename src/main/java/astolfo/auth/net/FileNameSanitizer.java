package astolfo.auth.net;

public final class FileNameSanitizer {
   private static final int MAX_LENGTH = 128;
   private static final String[] forbiddenSequences = new String[]{
      "..", "?", "[", "]", "=", "<", ">", ":", ";", ",", "\"", "\"", "&", "$", "#", "*", "(", ")", "|", "~", "`", "!", "{", "}"
   };

   public static String sanitize(String string) {
      for (String s : forbiddenSequences) {
         string = string.replace(s, "-");
      }

      if (string.length() > 128) {
         string = string.substring(0, 128);
      }

      return string;
   }
}
