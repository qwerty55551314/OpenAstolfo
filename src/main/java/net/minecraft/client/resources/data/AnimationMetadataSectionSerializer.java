package net.minecraft.client.resources.data;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import net.minecraft.util.JsonUtils;
import org.apache.commons.lang3.Validate;

public class AnimationMetadataSectionSerializer
   extends BaseMetadataSectionSerializer<AnimationMetadataSection>
   implements JsonSerializer<AnimationMetadataSection> {
   public AnimationMetadataSection deserialize(JsonElement jsonElement, Type type2, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
      ArrayList arraylist = Lists.newArrayList();
      JsonObject jsonobject = JsonUtils.getJsonObject(jsonElement, "metadata section");
      int i = JsonUtils.getInt(jsonobject, "frametime", 1);
      if (i != 1) {
         Validate.inclusiveBetween(1L, 2147483647L, (long)i, "Invalid default frame time");
      }

      boolean flag = jsonobject.has("frames");
      if (flag) {
         try {
            JsonArray jsonarray = JsonUtils.getJsonArray(jsonobject, "frames");

            for (int j = 0; j < jsonarray.size(); j++) {
               JsonElement jsonelement = jsonarray.get(j);
               AnimationFrame animationframe = this.parseAnimationFrame(j, jsonelement);
               if (animationframe != null) {
                  arraylist.add(animationframe);
               }
            }
         } catch (ClassCastException classcastexception) {
            throw new JsonParseException("Invalid animation->frames: expected array, was " + jsonobject.get("frames"), classcastexception);
         }
      }

      int k = JsonUtils.getInt(jsonobject, "width", -1);
      int l = JsonUtils.getInt(jsonobject, "height", -1);
      if (k != -1) {
         Validate.inclusiveBetween(1L, 2147483647L, (long)k, "Invalid width");
      }

      if (l != -1) {
         Validate.inclusiveBetween(1L, 2147483647L, (long)l, "Invalid height");
      }

      boolean flag1 = JsonUtils.getBoolean(jsonobject, "interpolate", false);
      return new AnimationMetadataSection(arraylist, k, l, i, flag1);
   }

   private AnimationFrame parseAnimationFrame(int i, JsonElement jsonElement) {
      boolean flag = jsonElement.isJsonPrimitive();
      if (flag) {
         return new AnimationFrame(JsonUtils.getInt(jsonElement, "frames[" + i + "]"));
      } else if (jsonElement.isJsonObject()) {
         JsonObject jsonobject = JsonUtils.getJsonObject(jsonElement, "frames[" + i + "]");
         int i1 = JsonUtils.getInt(jsonobject, "time", -1);
         boolean flag1 = jsonobject.has("time");
         if (flag1) {
            Validate.inclusiveBetween(1L, 2147483647L, (long)i1, "Invalid frame time");
         }

         int j = JsonUtils.getInt(jsonobject, "index");
         Validate.inclusiveBetween(0L, 2147483647L, (long)j, "Invalid frame index");
         return new AnimationFrame(j, i1);
      } else {
         return null;
      }
   }

   public JsonElement serialize(AnimationMetadataSection animationMetadataSection, Type type2, JsonSerializationContext jsonSerializationContext) {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("frametime", animationMetadataSection.getFrameTime());
      if (animationMetadataSection.getFrameWidth() != -1) {
         jsonobject.addProperty("width", animationMetadataSection.getFrameWidth());
      }

      if (animationMetadataSection.getFrameHeight() != -1) {
         jsonobject.addProperty("height", animationMetadataSection.getFrameHeight());
      }

      if (animationMetadataSection.getFrameCount() > 0) {
         JsonArray jsonarray = new JsonArray();

         for (int i = 0; i < animationMetadataSection.getFrameCount(); i++) {
            if (animationMetadataSection.frameHasTime(i)) {
               JsonObject jsonobject1 = new JsonObject();
               jsonobject1.addProperty("index", animationMetadataSection.getFrameIndex(i));
               jsonobject1.addProperty("time", animationMetadataSection.getFrameTimeSingle(i));
               jsonarray.add(jsonobject1);
            } else {
               jsonarray.add(new JsonPrimitive(animationMetadataSection.getFrameIndex(i)));
            }
         }

         jsonobject.add("frames", jsonarray);
      }

      return jsonobject;
   }

   @Override
   public String getSectionName() {
      return "animation";
   }
}
