package net.minecraft.client.resources.data;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import net.minecraft.util.JsonUtils;
import org.apache.commons.lang3.Validate;

public class FontMetadataSectionSerializer extends BaseMetadataSectionSerializer<FontMetadataSection> {
   public FontMetadataSection deserialize(JsonElement jsonElement, Type type2, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
      JsonObject jsonobject = jsonElement.getAsJsonObject();
      float[] afloat = new float[256];
      float[] afloat1 = new float[256];
      float[] afloat2 = new float[256];
      float f = 1.0F;
      float f1 = 0.0F;
      float f2 = 0.0F;
      boolean flag = jsonobject.has("characters");
      if (flag) {
         if (!jsonobject.get("characters").isJsonObject()) {
            throw new JsonParseException("Invalid font->characters: expected object, was " + jsonobject.get("characters"));
         }

         JsonObject jsonobject1 = jsonobject.getAsJsonObject("characters");
         boolean flag1 = jsonobject1.has("default");
         if (flag1) {
            if (!jsonobject1.get("default").isJsonObject()) {
               throw new JsonParseException("Invalid font->characters->default: expected object, was " + jsonobject1.get("default"));
            }

            JsonObject jsonobject2 = jsonobject1.getAsJsonObject("default");
            f = JsonUtils.getFloat(jsonobject2, "width", f);
            Validate.inclusiveBetween(0.0, Float.MAX_VALUE, (double)f, "Invalid default width");
            f1 = JsonUtils.getFloat(jsonobject2, "spacing", f1);
            Validate.inclusiveBetween(0.0, Float.MAX_VALUE, (double)f1, "Invalid default spacing");
            f2 = JsonUtils.getFloat(jsonobject2, "left", f1);
            Validate.inclusiveBetween(0.0, Float.MAX_VALUE, (double)f2, "Invalid default left");
         }

         for (int i = 0; i < 256; i++) {
            JsonElement jsonelement = jsonobject1.get(Integer.toString(i));
            float f3 = f;
            float f4 = f1;
            float f5 = f2;
            if (jsonelement != null) {
               JsonObject jsonobject3 = JsonUtils.getJsonObject(jsonelement, "characters[" + i + "]");
               f3 = JsonUtils.getFloat(jsonobject3, "width", f);
               Validate.inclusiveBetween(0.0, Float.MAX_VALUE, (double)f3, "Invalid width");
               f4 = JsonUtils.getFloat(jsonobject3, "spacing", f1);
               Validate.inclusiveBetween(0.0, Float.MAX_VALUE, (double)f4, "Invalid spacing");
               f5 = JsonUtils.getFloat(jsonobject3, "left", f2);
               Validate.inclusiveBetween(0.0, Float.MAX_VALUE, (double)f5, "Invalid left");
            }

            afloat[i] = f3;
            afloat1[i] = f4;
            afloat2[i] = f5;
         }
      }

      return new FontMetadataSection(afloat, afloat2, afloat1);
   }

   @Override
   public String getSectionName() {
      return "font";
   }
}
