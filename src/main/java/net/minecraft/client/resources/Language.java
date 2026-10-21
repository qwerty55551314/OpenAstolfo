package net.minecraft.client.resources;

public class Language implements Comparable<Language> {
   private final String languageCode;
   private final String region;
   private final String name;
   private final boolean bidirectional;
   private static final String b = "%s (%s)";

   public Language(String languageCodeIn, String regionIn, String nameIn, boolean bidirectionalIn) {
      this.languageCode = languageCodeIn;
      this.region = regionIn;
      this.name = nameIn;
      this.bidirectional = bidirectionalIn;
   }

   public String getLanguageCode() {
      return this.languageCode;
   }

   public boolean isBidirectional() {
      return this.bidirectional;
   }

   @Override
   public String toString() {
      return String.format(b, this.name, this.region);
   }

   @Override
   public boolean equals(Object object) {
      boolean flag1;
      if (this == object) {
         flag1 = true;
      } else {
         boolean flag = object instanceof Language;
         flag1 = !flag ? false : this.languageCode.equals(((Language)object).languageCode);
      }

      return flag1;
   }

   @Override
   public int hashCode() {
      return this.languageCode.hashCode();
   }

   public int compareTo(Language language) {
      return this.languageCode.compareTo(language.languageCode);
   }
}
