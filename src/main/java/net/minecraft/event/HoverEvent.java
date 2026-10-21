package net.minecraft.event;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.util.IChatComponent;

public class HoverEvent {
   private final HoverEvent.Action action;
   private final IChatComponent value;

   public HoverEvent(HoverEvent.Action actionIn, IChatComponent valueIn) {
      this.action = actionIn;
      this.value = valueIn;
   }

   public HoverEvent.Action getAction() {
      return this.action;
   }

   public IChatComponent getValue() {
      return this.value;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else {
         if (object != null) {
            Class oclass = this.getClass();
            if (oclass == object.getClass()) {
               HoverEvent hoverevent = (HoverEvent)object;
               if (this.action != hoverevent.action) {
                  return false;
               }

               IChatComponent ichatcomponent = this.value;
               if (ichatcomponent != null) {
                  if (!this.value.equals(hoverevent.value)) {
                     return false;
                  }
               } else if (hoverevent.value != null) {
                  return false;
               }

               return true;
            }
         }

         return false;
      }
   }

   @Override
   public String toString() {
      return "HoverEvent{action=" + this.action + ", value='" + this.value + '\'' + '}';
   }

   @Override
   public int hashCode() {
      int i = this.action.hashCode();
      return 31 * i + (this.value != null ? this.value.hashCode() : 0);
   }

   public static enum Action {
      SHOW_TEXT("show_text", true),
      SHOW_ACHIEVEMENT("show_achievement", true),
      SHOW_ITEM("show_item", true),
      SHOW_ENTITY("show_entity", true);

      private static final Map<String, HoverEvent.Action> nameMapping = Maps.newHashMap();
      private final boolean allowedInChat;
      private final String canonicalName;

      private Action(String string2, boolean flag) {
         this.canonicalName = string2;
         this.allowedInChat = flag;
      }

      public boolean shouldAllowInChat() {
         return this.allowedInChat;
      }

      public String getCanonicalName() {
         return this.canonicalName;
      }

      public static HoverEvent.Action getValueByCanonicalName(String canonicalNameIn) {
         return nameMapping.get(canonicalNameIn);
      }

      static {
         for (HoverEvent.Action hoverevent$action : values()) {
            nameMapping.put(hoverevent$action.getCanonicalName(), hoverevent$action);
         }
      }
   }
}
