package net.minecraft.src;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class Json {
   public static float getFloat(JsonObject obj, String field, float def) {
      JsonElement jsonelement = obj.get(field);
      return jsonelement == null ? def : jsonelement.getAsFloat();
   }

   public static boolean getBoolean(JsonObject obj, String field, boolean def) {
      JsonElement jsonelement = obj.get(field);
      return jsonelement == null ? def : jsonelement.getAsBoolean();
   }

   public static String getString(JsonObject jsonObj, String field) {
      return getString(jsonObj, field, (String)null);
   }

   public static String getString(JsonObject jsonObj, String field, String def) {
      JsonElement jsonelement = jsonObj.get(field);
      return jsonelement == null ? def : jsonelement.getAsString();
   }

   public static float[] parseFloatArray(JsonElement jsonElement, int lenIn) {
      return parseFloatArray(jsonElement, lenIn, (float[])null);
   }

   public static float[] parseFloatArray(JsonElement jsonElement, int lenIn, float[] def) {
      if (jsonElement == null) {
         return def;
      } else {
         JsonArray jsonarray = jsonElement.getAsJsonArray();
         int j = jsonarray.size();
         if (j != lenIn) {
            throw new JsonParseException("Wrong array length: " + jsonarray.size() + ", should be: " + lenIn + ", array: " + jsonarray);
         } else {
            float[] afloat = new float[jsonarray.size()];

            for (int i = 0; i < afloat.length; i++) {
               afloat[i] = jsonarray.get(i).getAsFloat();
            }

            return afloat;
         }
      }
   }

   public static int[] parseIntArray(JsonElement jsonElement, int lenIn) {
      return parseIntArray(jsonElement, lenIn, (int[])null);
   }

   public static int[] parseIntArray(JsonElement jsonElement, int lenIn, int[] def) {
      if (jsonElement == null) {
         return def;
      } else {
         JsonArray jsonarray = jsonElement.getAsJsonArray();
         int j = jsonarray.size();
         if (j != lenIn) {
            throw new JsonParseException("Wrong array length: " + jsonarray.size() + ", should be: " + lenIn + ", array: " + jsonarray);
         } else {
            int[] aint = new int[jsonarray.size()];

            for (int i = 0; i < aint.length; i++) {
               aint[i] = jsonarray.get(i).getAsInt();
            }

            return aint;
         }
      }
   }
}
