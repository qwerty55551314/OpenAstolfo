package net.minecraft.client.resources;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.client.resources.data.LanguageMetadataSection;
import net.minecraft.util.StringTranslate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LanguageManager implements IResourceManagerReloadListener {
   private static final Logger logger = LogManager.getLogger();
   private final IMetadataSerializer theMetadataSerializer;
   private String currentLanguage;
   public static final Locale currentLocale = new Locale();
   private Map<String, Language> languageMap = Maps.newHashMap();

   public LanguageManager(IMetadataSerializer theMetadataSerializerIn, String currentLanguageIn) {
      this.theMetadataSerializer = theMetadataSerializerIn;
      this.currentLanguage = currentLanguageIn;
      I18n.setLocale(currentLocale);
   }

   public void parseLanguageMetadata(List<IResourcePack> resourcesPacks) {
      this.languageMap.clear();

      for (IResourcePack iresourcepack : resourcesPacks) {
         try {
            LanguageMetadataSection languagemetadatasection = iresourcepack.getPackMetadata(this.theMetadataSerializer, "language");
            if (languagemetadatasection != null) {
               for (Language language : languagemetadatasection.getLanguages()) {
                  Map map1 = this.languageMap;
                  String s = language.getLanguageCode();
                  Map map = map1;
                  if (!map.containsKey(s)) {
                     this.languageMap.put(language.getLanguageCode(), language);
                  }
               }
            }
         } catch (RuntimeException runtimeexception) {
            logger.warn("Unable to parse metadata section of resourcepack: " + iresourcepack.getPackName(), (Throwable)runtimeexception);
         } catch (IOException ioexception) {
            logger.warn("Unable to parse metadata section of resourcepack: " + iresourcepack.getPackName(), (Throwable)ioexception);
         }
      }
   }

   @Override
   public void onResourceManagerReload(IResourceManager resourceManager) {
      Object object = null;
      ArrayList arraylist = Lists.newArrayList("en_US");
      if (!"en_US".equals(this.currentLanguage)) {
         arraylist.add(this.currentLanguage);
      }

      currentLocale.loadLocaleDataFiles(resourceManager, arraylist);
      StringTranslate.replaceWith(currentLocale.properties);
   }

   public boolean isCurrentLocaleUnicode() {
      return currentLocale.isUnicode();
   }

   public boolean isCurrentLanguageBidirectional() {
      Language language = this.getCurrentLanguage();
      if (language != null) {
         boolean flag = this.getCurrentLanguage().isBidirectional();
         if (flag) {
            return true;
         }
      }

      return false;
   }

   public void setCurrentLanguage(Language currentLanguageIn) {
      this.currentLanguage = currentLanguageIn.getLanguageCode();
   }

   public Language getCurrentLanguage() {
      return this.languageMap.containsKey(this.currentLanguage) ? this.languageMap.get(this.currentLanguage) : this.languageMap.get("en_US");
   }

   public SortedSet<Language> getLanguages() {
      return Sets.newTreeSet(this.languageMap.values());
   }
}
