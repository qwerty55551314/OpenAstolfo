package net.minecraft.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map.Entry;

public interface IChatComponent extends Iterable<IChatComponent> {
   IChatComponent setChatStyle(ChatStyle var1);

   ChatStyle getChatStyle();

   IChatComponent appendText(String var1);

   IChatComponent appendSibling(IChatComponent var1);

   String getUnformattedTextForChat();

   String getUnformattedText();

   String getFormattedText();

   List<IChatComponent> getSiblings();

   IChatComponent createCopy();

   public static class Serializer implements JsonDeserializer<IChatComponent>, JsonSerializer<IChatComponent> {
      private static final Gson GSON;

      public IChatComponent deserialize(JsonElement jsonElement, Type type2, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
         boolean flag = jsonElement.isJsonPrimitive();
         if (flag) {
            return new ChatComponentText(jsonElement.getAsString());
         } else if (!jsonElement.isJsonObject()) {
            if (jsonElement.isJsonArray()) {
               JsonArray jsonarray1 = jsonElement.getAsJsonArray();
               IChatComponent ichatcomponent = null;

               for (JsonElement jsonelement : jsonarray1) {
                  IChatComponent ichatcomponent1 = this.deserialize(jsonelement, jsonelement.getClass(), jsonDeserializationContext);
                  if (ichatcomponent == null) {
                     ichatcomponent = ichatcomponent1;
                  } else {
                     ichatcomponent.appendSibling(ichatcomponent1);
                  }
               }

               return ichatcomponent;
            } else {
               throw new JsonParseException("Don't know how to turn " + jsonElement.toString() + " into a Component");
            }
         } else {
            JsonObject jsonobject = jsonElement.getAsJsonObject();
            boolean flag1 = jsonobject.has("text");
            Object object;
            if (flag1) {
               object = new ChatComponentText(jsonobject.get("text").getAsString());
            } else {
               boolean flag2 = jsonobject.has("translate");
               if (flag2) {
                  String s = jsonobject.get("translate").getAsString();
                  if (jsonobject.has("with")) {
                     JsonArray jsonarray = jsonobject.getAsJsonArray("with");
                     Object[] aobject = new Object[jsonarray.size()];

                     for (int i = 0; i < aobject.length; i++) {
                        aobject[i] = this.deserialize(jsonarray.get(i), type2, jsonDeserializationContext);
                        if (aobject[i] instanceof ChatComponentText) {
                           ChatComponentText chatcomponenttext = (ChatComponentText)aobject[i];
                           if (chatcomponenttext.getChatStyle().isEmpty() && chatcomponenttext.getSiblings().isEmpty()) {
                              aobject[i] = chatcomponenttext.getChatComponentText_TextValue();
                           }
                        }
                     }

                     object = new ChatComponentTranslation(s, aobject);
                  } else {
                     object = new ChatComponentTranslation(s);
                  }
               } else {
                  boolean flag3 = jsonobject.has("score");
                  if (flag3) {
                     JsonObject jsonobject1 = jsonobject.getAsJsonObject("score");
                     if (!jsonobject1.has("name") || !jsonobject1.has("objective")) {
                        throw new JsonParseException("A score component needs a least a name and an objective");
                     }

                     object = new ChatComponentScore(JsonUtils.getString(jsonobject1, "name"), JsonUtils.getString(jsonobject1, "objective"));
                     if (jsonobject1.has("value")) {
                        ((ChatComponentScore)object).setValue(JsonUtils.getString(jsonobject1, "value"));
                     }
                  } else {
                     if (!jsonobject.has("selector")) {
                        throw new JsonParseException("Don't know how to turn " + jsonElement.toString() + " into a Component");
                     }

                     object = new ChatComponentSelector(JsonUtils.getString(jsonobject, "selector"));
                  }
               }
            }

            if (jsonobject.has("extra")) {
               JsonArray jsonarray2 = jsonobject.getAsJsonArray("extra");
               int j = jsonarray2.size();
               if (j <= 0) {
                  throw new JsonParseException("Unexpected empty array of components");
               }

               for (int k = 0; k < jsonarray2.size(); k++) {
                  ((IChatComponent)object).appendSibling(this.deserialize(jsonarray2.get(k), type2, jsonDeserializationContext));
               }
            }

            ((IChatComponent)object).setChatStyle(jsonDeserializationContext.deserialize(jsonElement, ChatStyle.class));
            return (IChatComponent)object;
         }
      }

      private void serializeChatStyle(ChatStyle style, JsonObject object, JsonSerializationContext ctx) {
         JsonElement jsonelement = ctx.serialize(style);
         if (jsonelement.isJsonObject()) {
            JsonObject jsonobject = (JsonObject)jsonelement;

            for (Entry entry : jsonobject.entrySet()) {
               object.add((String)entry.getKey(), (JsonElement)entry.getValue());
            }
         }
      }

      public JsonElement serialize(IChatComponent iChatComponent, Type type2, JsonSerializationContext jsonSerializationContext) {
         boolean flag = iChatComponent instanceof ChatComponentText;
         if (flag) {
            boolean flag1 = iChatComponent.getChatStyle().isEmpty();
            if (flag1 && iChatComponent.getSiblings().isEmpty()) {
               return new JsonPrimitive(((ChatComponentText)iChatComponent).getChatComponentText_TextValue());
            }
         }

         JsonObject jsonobject = new JsonObject();
         boolean flag2 = iChatComponent.getChatStyle().isEmpty();
         if (!flag2) {
            this.serializeChatStyle(iChatComponent.getChatStyle(), jsonobject, jsonSerializationContext);
         }

         boolean flag3 = iChatComponent.getSiblings().isEmpty();
         if (!flag3) {
            JsonArray jsonarray = new JsonArray();

            for (IChatComponent ichatcomponent : iChatComponent.getSiblings()) {
               jsonarray.add(this.serialize(ichatcomponent, ichatcomponent.getClass(), jsonSerializationContext));
            }

            jsonobject.add("extra", jsonarray);
         }

         boolean flag4 = iChatComponent instanceof ChatComponentText;
         if (flag4) {
            jsonobject.addProperty("text", ((ChatComponentText)iChatComponent).getChatComponentText_TextValue());
         } else {
            boolean flag5 = iChatComponent instanceof ChatComponentTranslation;
            if (flag5) {
               ChatComponentTranslation chatcomponenttranslation = (ChatComponentTranslation)iChatComponent;
               jsonobject.addProperty("translate", chatcomponenttranslation.getKey());
               if (chatcomponenttranslation.getFormatArgs() != null && chatcomponenttranslation.getFormatArgs().length > 0) {
                  JsonArray jsonarray1 = new JsonArray();

                  for (Object object : chatcomponenttranslation.getFormatArgs()) {
                     if (object instanceof IChatComponent) {
                        jsonarray1.add(this.serialize((IChatComponent)object, object.getClass(), jsonSerializationContext));
                     } else {
                        jsonarray1.add(new JsonPrimitive(String.valueOf(object)));
                     }
                  }

                  jsonobject.add("with", jsonarray1);
               }
            } else {
               boolean flag6 = iChatComponent instanceof ChatComponentScore;
               if (flag6) {
                  ChatComponentScore chatcomponentscore = (ChatComponentScore)iChatComponent;
                  JsonObject jsonobject1 = new JsonObject();
                  jsonobject1.addProperty("name", chatcomponentscore.getName());
                  jsonobject1.addProperty("objective", chatcomponentscore.getObjective());
                  jsonobject1.addProperty("value", chatcomponentscore.getUnformattedTextForChat());
                  jsonobject.add("score", jsonobject1);
               } else {
                  if (!(iChatComponent instanceof ChatComponentSelector)) {
                     throw new IllegalArgumentException("Don't know how to serialize " + iChatComponent + " as a Component");
                  }

                  ChatComponentSelector chatcomponentselector = (ChatComponentSelector)iChatComponent;
                  jsonobject.addProperty("selector", chatcomponentselector.getSelector());
               }
            }
         }

         return jsonobject;
      }

      public static String componentToJson(IChatComponent component) {
         return GSON.toJson(component);
      }

      public static IChatComponent jsonToComponent(String json) {
         return GSON.fromJson(json, IChatComponent.class);
      }

      static {
         GsonBuilder gsonbuilder = new GsonBuilder();
         gsonbuilder.registerTypeHierarchyAdapter(IChatComponent.class, new IChatComponent.Serializer());
         gsonbuilder.registerTypeHierarchyAdapter(ChatStyle.class, new ChatStyle.Serializer());
         gsonbuilder.registerTypeAdapterFactory(new EnumTypeAdapterFactory());
         GSON = gsonbuilder.create();
      }
   }
}
