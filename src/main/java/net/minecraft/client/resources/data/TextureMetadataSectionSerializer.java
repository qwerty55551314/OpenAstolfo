package net.minecraft.client.resources.data;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import net.minecraft.util.JsonUtils;

public class TextureMetadataSectionSerializer extends BaseMetadataSectionSerializer<TextureMetadataSection> {
   public TextureMetadataSection deserialize(JsonElement jsonElement, Type type2, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
      JsonObject jsonobject = jsonElement.getAsJsonObject();
      boolean flag = JsonUtils.getBoolean(jsonobject, "blur", false);
      boolean flag1 = JsonUtils.getBoolean(jsonobject, "clamp", false);
      ArrayList arraylist = Lists.newArrayList();
      if (jsonobject.has("mipmaps")) {
         try {
            JsonArray jsonarray = jsonobject.getAsJsonArray("mipmaps");

            for (int i = 0; i < jsonarray.size(); i++) {
               JsonElement jsonelement = jsonarray.get(i);
               if (jsonelement.isJsonPrimitive()) {
                  try {
                     arraylist.add(jsonelement.getAsInt());
                  } catch (NumberFormatException numberformatexception) {
                     throw new JsonParseException("Invalid texture->mipmap->" + i + ": expected number, was " + jsonelement, numberformatexception);
                  }
               } else if (jsonelement.isJsonObject()) {
                  throw new JsonParseException("Invalid texture->mipmap->" + i + ": expected number, was " + jsonelement);
               }
            }
         } catch (ClassCastException classcastexception) {
            throw new JsonParseException("Invalid texture->mipmaps: expected array, was " + jsonobject.get("mipmaps"), classcastexception);
         }
      }

      return new TextureMetadataSection(flag, flag1, arraylist);
   }

   @Override
   public String getSectionName() {
      return "texture";
   }
}
