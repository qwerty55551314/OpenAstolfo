package net.minecraft.client.resources.data;

import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Map.Entry;
import net.minecraft.client.resources.Language;
import net.minecraft.util.JsonUtils;

public class LanguageMetadataSectionSerializer extends BaseMetadataSectionSerializer<LanguageMetadataSection> {
   public LanguageMetadataSection deserialize(JsonElement jsonElement, Type type2, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
      JsonObject jsonobject = jsonElement.getAsJsonObject();
      HashSet hashset = Sets.newHashSet();

      for (Entry entry : jsonobject.entrySet()) {
         String s = (String)entry.getKey();
         JsonObject jsonobject1 = JsonUtils.getJsonObject((JsonElement)entry.getValue(), "language");
         String s1 = JsonUtils.getString(jsonobject1, "region");
         String s2 = JsonUtils.getString(jsonobject1, "name");
         boolean flag = JsonUtils.getBoolean(jsonobject1, "bidirectional", false);
         boolean flag1 = s1.isEmpty();
         if (flag1) {
            throw new JsonParseException("Invalid language->'" + s + "'->region: empty value");
         }

         boolean flag2 = s2.isEmpty();
         if (flag2) {
            throw new JsonParseException("Invalid language->'" + s + "'->name: empty value");
         }

         if (!hashset.add(new Language(s, s1, s2, flag))) {
            throw new JsonParseException("Duplicate language->'" + s + "' defined");
         }
      }

      return new LanguageMetadataSection(hashset);
   }

   @Override
   public String getSectionName() {
      return "language";
   }
}
