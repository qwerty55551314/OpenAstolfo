package net.minecraft.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;

public class ChatStyle {
   private ChatStyle parentStyle;
   private EnumChatFormatting color;
   private Boolean bold;
   private Boolean italic;
   private Boolean underlined;
   private Boolean strikethrough;
   private Boolean obfuscated;
   private ClickEvent chatClickEvent;
   private HoverEvent chatHoverEvent;
   private String insertion;
   private static final ChatStyle rootStyle = new ChatStyle() {
      private static final String h = "Style.ROOT";

      @Override
      public EnumChatFormatting getColor() {
         return null;
      }

      @Override
      public boolean getBold() {
         return false;
      }

      @Override
      public boolean getItalic() {
         return false;
      }

      @Override
      public boolean getStrikethrough() {
         return false;
      }

      @Override
      public boolean getUnderlined() {
         return false;
      }

      @Override
      public boolean getObfuscated() {
         return false;
      }

      @Override
      public ClickEvent getChatClickEvent() {
         return null;
      }

      @Override
      public HoverEvent getChatHoverEvent() {
         return null;
      }

      @Override
      public String getInsertion() {
         return null;
      }

      @Override
      public ChatStyle setColor(EnumChatFormatting colorIn) {
         throw new UnsupportedOperationException();
      }

      @Override
      public ChatStyle setBold(Boolean boldIn) {
         throw new UnsupportedOperationException();
      }

      @Override
      public ChatStyle setItalic(Boolean italicIn) {
         throw new UnsupportedOperationException();
      }

      @Override
      public ChatStyle setStrikethrough(Boolean strikethroughIn) {
         throw new UnsupportedOperationException();
      }

      @Override
      public ChatStyle setUnderlined(Boolean underlinedIn) {
         throw new UnsupportedOperationException();
      }

      @Override
      public ChatStyle setObfuscated(Boolean obfuscatedIn) {
         throw new UnsupportedOperationException();
      }

      @Override
      public ChatStyle setChatClickEvent(ClickEvent event) {
         throw new UnsupportedOperationException();
      }

      @Override
      public ChatStyle setChatHoverEvent(HoverEvent event) {
         throw new UnsupportedOperationException();
      }

      @Override
      public ChatStyle setParentStyle(ChatStyle parent) {
         throw new UnsupportedOperationException();
      }

      @Override
      public String toString() {
         return h;
      }

      @Override
      public ChatStyle createShallowCopy() {
         return this;
      }

      @Override
      public ChatStyle createDeepCopy() {
         return this;
      }

      @Override
      public String getFormattingCode() {
         return "";
      }
   };

   public EnumChatFormatting getColor() {
      return this.color == null ? this.getParent().getColor() : this.color;
   }

   public boolean getBold() {
      return this.bold == null ? this.getParent().getBold() : this.bold;
   }

   public boolean getItalic() {
      return this.italic == null ? this.getParent().getItalic() : this.italic;
   }

   public boolean getStrikethrough() {
      return this.strikethrough == null ? this.getParent().getStrikethrough() : this.strikethrough;
   }

   public boolean getUnderlined() {
      return this.underlined == null ? this.getParent().getUnderlined() : this.underlined;
   }

   public boolean getObfuscated() {
      return this.obfuscated == null ? this.getParent().getObfuscated() : this.obfuscated;
   }

   public boolean isEmpty() {
      return this.bold == null
         && this.italic == null
         && this.strikethrough == null
         && this.underlined == null
         && this.obfuscated == null
         && this.color == null
         && this.chatClickEvent == null
         && this.chatHoverEvent == null;
   }

   public ClickEvent getChatClickEvent() {
      return this.chatClickEvent == null ? this.getParent().getChatClickEvent() : this.chatClickEvent;
   }

   public HoverEvent getChatHoverEvent() {
      return this.chatHoverEvent == null ? this.getParent().getChatHoverEvent() : this.chatHoverEvent;
   }

   public String getInsertion() {
      return this.insertion == null ? this.getParent().getInsertion() : this.insertion;
   }

   public ChatStyle setColor(EnumChatFormatting color) {
      this.color = color;
      return this;
   }

   public ChatStyle setBold(Boolean boldIn) {
      this.bold = boldIn;
      return this;
   }

   public ChatStyle setItalic(Boolean italic) {
      this.italic = italic;
      return this;
   }

   public ChatStyle setStrikethrough(Boolean strikethrough) {
      this.strikethrough = strikethrough;
      return this;
   }

   public ChatStyle setUnderlined(Boolean underlined) {
      this.underlined = underlined;
      return this;
   }

   public ChatStyle setObfuscated(Boolean obfuscated) {
      this.obfuscated = obfuscated;
      return this;
   }

   public ChatStyle setChatClickEvent(ClickEvent event) {
      this.chatClickEvent = event;
      return this;
   }

   public ChatStyle setChatHoverEvent(HoverEvent event) {
      this.chatHoverEvent = event;
      return this;
   }

   public ChatStyle setInsertion(String insertion) {
      this.insertion = insertion;
      return this;
   }

   public ChatStyle setParentStyle(ChatStyle parent) {
      this.parentStyle = parent;
      return this;
   }

   public String getFormattingCode() {
      if (this.isEmpty()) {
         return this.parentStyle != null ? this.parentStyle.getFormattingCode() : "";
      } else {
         StringBuilder stringbuilder = new StringBuilder();
         if (this.getColor() != null) {
            stringbuilder.append(this.getColor());
         }

         if (this.getBold()) {
            stringbuilder.append(EnumChatFormatting.BOLD);
         }

         if (this.getItalic()) {
            stringbuilder.append(EnumChatFormatting.ITALIC);
         }

         if (this.getUnderlined()) {
            stringbuilder.append(EnumChatFormatting.UNDERLINE);
         }

         if (this.getObfuscated()) {
            stringbuilder.append(EnumChatFormatting.OBFUSCATED);
         }

         if (this.getStrikethrough()) {
            stringbuilder.append(EnumChatFormatting.STRIKETHROUGH);
         }

         return stringbuilder.toString();
      }
   }

   private ChatStyle getParent() {
      return this.parentStyle == null ? rootStyle : this.parentStyle;
   }

   @Override
   public String toString() {
      return "Style{hasParent="
         + (this.parentStyle != null)
         + ", color="
         + this.color
         + ", bold="
         + this.bold
         + ", italic="
         + this.italic
         + ", underlined="
         + this.underlined
         + ", obfuscated="
         + this.obfuscated
         + ", clickEvent="
         + this.getChatClickEvent()
         + ", hoverEvent="
         + this.getChatHoverEvent()
         + ", insertion="
         + this.getInsertion()
         + '}';
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (!(object instanceof ChatStyle)) {
         return false;
      } else {
         ChatStyle chatstyle = (ChatStyle)object;
         boolean flag = this.getBold();
         return flag == chatstyle.getBold()
            && this.getColor() == chatstyle.getColor()
            && this.getItalic() == chatstyle.getItalic()
            && this.getObfuscated() == chatstyle.getObfuscated()
            && this.getStrikethrough() == chatstyle.getStrikethrough()
            && this.getUnderlined() == chatstyle.getUnderlined()
            && (this.getChatClickEvent() != null ? this.getChatClickEvent().equals(chatstyle.getChatClickEvent()) : chatstyle.getChatClickEvent() == null)
            && (this.getChatHoverEvent() != null ? this.getChatHoverEvent().equals(chatstyle.getChatHoverEvent()) : chatstyle.getChatHoverEvent() == null)
            && (this.getInsertion() != null ? this.getInsertion().equals(chatstyle.getInsertion()) : chatstyle.getInsertion() == null);
      }
   }

   @Override
   public int hashCode() {
      int i = this.color.hashCode();
      i = 31 * i + this.bold.hashCode();
      i = 31 * i + this.italic.hashCode();
      i = 31 * i + this.underlined.hashCode();
      i = 31 * i + this.strikethrough.hashCode();
      i = 31 * i + this.obfuscated.hashCode();
      i = 31 * i + this.chatClickEvent.hashCode();
      i = 31 * i + this.chatHoverEvent.hashCode();
      return 31 * i + this.insertion.hashCode();
   }

   public ChatStyle createShallowCopy() {
      ChatStyle chatstyle = new ChatStyle();
      chatstyle.bold = this.bold;
      chatstyle.italic = this.italic;
      chatstyle.strikethrough = this.strikethrough;
      chatstyle.underlined = this.underlined;
      chatstyle.obfuscated = this.obfuscated;
      chatstyle.color = this.color;
      chatstyle.chatClickEvent = this.chatClickEvent;
      chatstyle.chatHoverEvent = this.chatHoverEvent;
      chatstyle.parentStyle = this.parentStyle;
      chatstyle.insertion = this.insertion;
      return chatstyle;
   }

   public ChatStyle createDeepCopy() {
      ChatStyle chatstyle = new ChatStyle();
      chatstyle.setBold(this.getBold());
      chatstyle.setItalic(this.getItalic());
      chatstyle.setStrikethrough(this.getStrikethrough());
      chatstyle.setUnderlined(this.getUnderlined());
      chatstyle.setObfuscated(this.getObfuscated());
      chatstyle.setColor(this.getColor());
      chatstyle.setChatClickEvent(this.getChatClickEvent());
      chatstyle.setChatHoverEvent(this.getChatHoverEvent());
      chatstyle.setInsertion(this.getInsertion());
      return chatstyle;
   }

   public static class Serializer implements JsonDeserializer<ChatStyle>, JsonSerializer<ChatStyle> {
      public ChatStyle deserialize(JsonElement jsonElement, Type type2, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
         if (jsonElement.isJsonObject()) {
            ChatStyle chatstyle = new ChatStyle();
            JsonObject jsonobject = jsonElement.getAsJsonObject();
            if (jsonobject == null) {
               return null;
            } else {
               boolean flag = jsonobject.has("bold");
               if (flag) {
                  chatstyle.bold = jsonobject.get("bold").getAsBoolean();
               }

               boolean flag1 = jsonobject.has("italic");
               if (flag1) {
                  chatstyle.italic = jsonobject.get("italic").getAsBoolean();
               }

               boolean flag2 = jsonobject.has("underlined");
               if (flag2) {
                  chatstyle.underlined = jsonobject.get("underlined").getAsBoolean();
               }

               boolean flag3 = jsonobject.has("strikethrough");
               if (flag3) {
                  chatstyle.strikethrough = jsonobject.get("strikethrough").getAsBoolean();
               }

               boolean flag4 = jsonobject.has("obfuscated");
               if (flag4) {
                  chatstyle.obfuscated = jsonobject.get("obfuscated").getAsBoolean();
               }

               boolean flag5 = jsonobject.has("color");
               if (flag5) {
                  chatstyle.color = jsonDeserializationContext.deserialize(jsonobject.get("color"), EnumChatFormatting.class);
               }

               boolean flag6 = jsonobject.has("insertion");
               if (flag6) {
                  chatstyle.insertion = jsonobject.get("insertion").getAsString();
               }

               boolean flag7 = jsonobject.has("clickEvent");
               if (flag7) {
                  JsonObject jsonobject1 = jsonobject.getAsJsonObject("clickEvent");
                  if (jsonobject1 != null) {
                     JsonPrimitive jsonprimitive = jsonobject1.getAsJsonPrimitive("action");
                     ClickEvent.Action clickevent$action = jsonprimitive == null
                        ? null
                        : ClickEvent.Action.getValueByCanonicalName(jsonprimitive.getAsString());
                     JsonPrimitive jsonprimitive1 = jsonobject1.getAsJsonPrimitive("value");
                     String s = jsonprimitive1 == null ? null : jsonprimitive1.getAsString();
                     if (clickevent$action != null && s != null && clickevent$action.shouldAllowInChat()) {
                        chatstyle.chatClickEvent = new ClickEvent(clickevent$action, s);
                     }
                  }
               }

               if (jsonobject.has("hoverEvent")) {
                  JsonObject jsonobject2 = jsonobject.getAsJsonObject("hoverEvent");
                  if (jsonobject2 != null) {
                     JsonPrimitive jsonprimitive2 = jsonobject2.getAsJsonPrimitive("action");
                     HoverEvent.Action hoverevent$action = jsonprimitive2 == null
                        ? null
                        : HoverEvent.Action.getValueByCanonicalName(jsonprimitive2.getAsString());
                     IChatComponent ichatcomponent = jsonDeserializationContext.deserialize(jsonobject2.get("value"), IChatComponent.class);
                     if (hoverevent$action != null && ichatcomponent != null && hoverevent$action.shouldAllowInChat()) {
                        chatstyle.chatHoverEvent = new HoverEvent(hoverevent$action, ichatcomponent);
                     }
                  }
               }

               return chatstyle;
            }
         } else {
            return null;
         }
      }

      public JsonElement serialize(ChatStyle chatStyle, Type type2, JsonSerializationContext jsonSerializationContext) {
         if (chatStyle.isEmpty()) {
            return null;
         } else {
            JsonObject jsonobject = new JsonObject();
            Boolean obool = chatStyle.bold;
            if (obool != null) {
               jsonobject.addProperty("bold", chatStyle.bold);
            }

            Boolean obool1 = chatStyle.italic;
            if (obool1 != null) {
               jsonobject.addProperty("italic", chatStyle.italic);
            }

            Boolean obool2 = chatStyle.underlined;
            if (obool2 != null) {
               jsonobject.addProperty("underlined", chatStyle.underlined);
            }

            Boolean obool3 = chatStyle.strikethrough;
            if (obool3 != null) {
               jsonobject.addProperty("strikethrough", chatStyle.strikethrough);
            }

            if (chatStyle.obfuscated != null) {
               jsonobject.addProperty("obfuscated", chatStyle.obfuscated);
            }

            if (chatStyle.color != null) {
               jsonobject.add("color", jsonSerializationContext.serialize(chatStyle.color));
            }

            if (chatStyle.insertion != null) {
               jsonobject.add("insertion", jsonSerializationContext.serialize(chatStyle.insertion));
            }

            if (chatStyle.chatClickEvent != null) {
               JsonObject jsonobject1 = new JsonObject();
               jsonobject1.addProperty("action", chatStyle.chatClickEvent.getAction().getCanonicalName());
               jsonobject1.addProperty("value", chatStyle.chatClickEvent.getValue());
               jsonobject.add("clickEvent", jsonobject1);
            }

            if (chatStyle.chatHoverEvent != null) {
               JsonObject jsonobject2 = new JsonObject();
               jsonobject2.addProperty("action", chatStyle.chatHoverEvent.getAction().getCanonicalName());
               jsonobject2.add("value", jsonSerializationContext.serialize(chatStyle.chatHoverEvent.getValue()));
               jsonobject.add("hoverEvent", jsonobject2);
            }

            return jsonobject;
         }
      }
   }
}
