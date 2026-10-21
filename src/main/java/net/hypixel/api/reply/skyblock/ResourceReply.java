package net.hypixel.api.reply.skyblock;

import com.google.gson.JsonObject;
import net.hypixel.api.reply.AbstractReply;

public class ResourceReply extends AbstractReply {
   private long lastUpdated;
   private JsonObject response;

   public ResourceReply(JsonObject response) {
      this.response = response;
      super.success = response.has("success") && response.get("success").getAsBoolean();
      super.cause = response.has("cause") ? response.get("cause").getAsString() : null;
      this.lastUpdated = response.has("lastUpdated") ? response.get("lastUpdated").getAsLong() : -1L;
   }

   public JsonObject getResponse() {
      JsonObject jsonobject = this.response;
      if (jsonobject != null) {
         JsonObject jsonobject1 = this.response;
         if (!jsonobject1.isJsonNull()) {
            return this.response.getAsJsonObject();
         }
      }

      return null;
   }

   public long getLastUpdated() {
      return this.lastUpdated;
   }

   @Override
   public String toString() {
      return "ResourceReply{lastUpdated=" + this.lastUpdated + ", response=" + this.response + "} " + super.toString();
   }
}
