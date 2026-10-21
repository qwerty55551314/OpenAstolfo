package astolfo.account;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mojang.util.UUIDTypeAdapter;
import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;

public class MojangAuth implements Authenticator {
   private String username;
   private String accessToken;
   private UUID clientToken;
   private UUID uuid;
   public int useCount;
   public long lastUsed;

   public MojangAuth(String string, String string2, UUID uuid, UUID uuid2) {
      this.username = string;
      this.accessToken = string2;
      this.clientToken = uuid;
      this.uuid = uuid2;
   }

   @Override
   public String getUsername() {
      return this.username;
   }

   @Override
   public void login(Minecraft minecraft, Consumer<Throwable> consumer) {
   }

   public boolean validate() throws Exception {
      AuthHttp authhttp = new AuthHttp("https://authserver.mojang.com/validate");
      authhttp.setHeader("Content-Type", "application/json");
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("accessToken", this.accessToken);
      jsonobject.addProperty("clientToken", UUIDTypeAdapter.fromUUID(this.clientToken));
      authhttp.post(jsonobject.toString());
      return authhttp.getResponseCode() >= 200 && authhttp.getResponseCode() < 300;
   }

   public void refresh() throws Exception {
      AuthHttp authhttp = new AuthHttp("https://authserver.mojang.com/refresh");
      authhttp.setHeader("Content-Type", "application/json");
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("accessToken", this.accessToken);
      jsonobject.addProperty("clientToken", UUIDTypeAdapter.fromUUID(this.clientToken));
      authhttp.post(jsonobject.toString());
      if (authhttp.getResponseCode() >= 200 && authhttp.getResponseCode() < 300) {
         JsonObject jsonobject1 = new Gson().fromJson(authhttp.readResponse(), JsonObject.class);
         this.accessToken = jsonobject1.get("accessToken").getAsString();
         this.clientToken = UUIDTypeAdapter.fromString(jsonobject1.get("clientToken").getAsString());
         this.uuid = UUIDTypeAdapter.fromString(jsonobject1.getAsJsonObject("selectedProfile").get("id").getAsString());
         this.username = jsonobject1.getAsJsonObject("selectedProfile").get("name").getAsString();
      } else {
         throw new AuthException("ias.mojauth.unknown", authhttp.readErrorResponse());
      }
   }

   @Override
   public boolean canLogin() {
      return true;
   }

   @Override
   public boolean canEdit() {
      return true;
   }

   @Override
   public int getUseCount() {
      return this.useCount;
   }

   @Override
   public long getLastUsed() {
      return this.lastUsed;
   }

   @Override
   public void markUsed() {
      this.useCount++;
      this.lastUsed = System.currentTimeMillis();
   }

   @Override
   public UUID getUuid() {
      return this.uuid;
   }
}
