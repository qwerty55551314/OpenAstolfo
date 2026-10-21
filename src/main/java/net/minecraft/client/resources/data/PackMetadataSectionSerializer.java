package net.minecraft.client.resources.data;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.JsonUtils;

public class PackMetadataSectionSerializer extends BaseMetadataSectionSerializer<PackMetadataSection> implements JsonSerializer<PackMetadataSection> {
   public PackMetadataSection deserialize(JsonElement jsonElement, Type type2, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
      JsonObject jsonobject = jsonElement.getAsJsonObject();
      IChatComponent ichatcomponent = jsonDeserializationContext.deserialize(jsonobject.get("description"), IChatComponent.class);
      if (ichatcomponent == null) {
         throw new JsonParseException("Invalid/missing description!");
      } else {
         int i = JsonUtils.getInt(jsonobject, "pack_format");
         return new PackMetadataSection(ichatcomponent, i);
      }
   }

   public JsonElement serialize(PackMetadataSection packMetadataSection, Type type2, JsonSerializationContext jsonSerializationContext) {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("pack_format", packMetadataSection.getPackFormat());
      jsonobject.add("description", jsonSerializationContext.serialize(packMetadataSection.getPackDescription()));
      return jsonobject;
   }

   @Override
   public String getSectionName() {
      return "pack";
   }
}
