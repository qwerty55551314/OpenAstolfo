package astolfo.account;

import astolfo.config.ConfigFiles;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.util.UUIDTypeAdapter;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import org.apache.commons.lang3.tuple.Pair;

public class MicrosoftAuth {
   public static Pair<String, String> exchangeAuthCode(String string) throws IllegalArgumentException, Exception {
      AuthHttp authhttp = new AuthHttp("https://login.live.com/oauth20_token.srf");
      authhttp.setHeader("Content-Type", "application/x-www-form-urlencoded");
      HashMap hashmap = new HashMap();
      hashmap.put("client_id", "54fd49e4-2103-4044-9603-2b028c814ec3");
      hashmap.put("code", string);
      hashmap.put("grant_type", "authorization_code");
      hashmap.put("redirect_uri", "http://localhost:59125");
      hashmap.put("scope", "XboxLive.signin XboxLive.offline_access");
      authhttp.postForm(hashmap);
      if (authhttp.getResponseCode() >= 200 && authhttp.getResponseCode() < 300) {
         JsonObject jsonobject = ConfigFiles.GSON.fromJson(authhttp.readResponse(), JsonObject.class);
         return Pair.of(jsonobject.get("access_token").getAsString(), jsonobject.get("refresh_token").getAsString());
      } else {
         throw new IllegalArgumentException("authCode2Token response: " + authhttp.getResponseCode());
      }
   }

   public static Pair<String, String> refreshMicrosoftToken(String string) throws IllegalArgumentException, Exception {
      AuthHttp authhttp = new AuthHttp("https://login.live.com/oauth20_token.srf");
      authhttp.setGetMethod();
      HashMap hashmap = new HashMap();
      hashmap.put("client_id", "54fd49e4-2103-4044-9603-2b028c814ec3");
      hashmap.put("refresh_token", string);
      hashmap.put("grant_type", "refresh_token");
      hashmap.put("redirect_uri", "http://localhost:59125");
      authhttp.postForm(hashmap);
      if (authhttp.getResponseCode() >= 200 && authhttp.getResponseCode() < 300) {
         JsonObject jsonobject = ConfigFiles.GSON.fromJson(authhttp.readResponse(), JsonObject.class);
         return Pair.of(jsonobject.get("access_token").getAsString(), jsonobject.get("refresh_token").getAsString());
      } else {
         throw new IllegalArgumentException("refreshToken response: " + authhttp.getResponseCode());
      }
   }

   public static String authXboxLive(String string) throws IllegalArgumentException, Exception {
      AuthHttp authhttp = new AuthHttp("https://user.auth.xboxlive.com/user/authenticate");
      authhttp.setHeader("Content-Type", "application/json");
      authhttp.setHeader("Accept", "application/json");
      JsonObject jsonobject = new JsonObject();
      JsonObject jsonobject1 = new JsonObject();
      jsonobject1.addProperty("AuthMethod", "RPS");
      jsonobject1.addProperty("SiteName", "user.auth.xboxlive.com");
      jsonobject1.addProperty("RpsTicket", "d=" + string);
      jsonobject.add("Properties", jsonobject1);
      jsonobject.addProperty("RelyingParty", "http://auth.xboxlive.com");
      jsonobject.addProperty("TokenType", "JWT");
      authhttp.post(jsonobject.toString());
      if (authhttp.getResponseCode() >= 200 && authhttp.getResponseCode() < 300) {
         return ConfigFiles.GSON.fromJson(authhttp.readResponse(), JsonObject.class).get("Token").getAsString();
      } else {
         throw new IllegalArgumentException("authXBL response: " + authhttp.getResponseCode());
      }
   }

   public static Pair<String, String> authXsts(String string) throws AuthException, IllegalArgumentException, Exception {
      AuthHttp authhttp = new AuthHttp("https://xsts.auth.xboxlive.com/xsts/authorize");
      authhttp.setHeader("Content-Type", "application/json");
      authhttp.setHeader("Accept", "application/json");
      JsonObject jsonobject = new JsonObject();
      JsonObject jsonobject1 = new JsonObject();
      JsonArray jsonarray = new JsonArray();
      JsonPrimitive jsonprimitive = new JsonPrimitive(string);
      jsonarray.add(jsonprimitive);
      jsonobject1.add("UserTokens", jsonarray);
      jsonobject1.addProperty("SandboxId", "RETAIL");
      jsonobject.add("Properties", jsonobject1);
      jsonobject.addProperty("RelyingParty", "rp://api.minecraftservices.com/");
      jsonobject.addProperty("TokenType", "JWT");
      authhttp.post(jsonobject.toString());
      int i = authhttp.getResponseCode();
      if (i == 401) {
         throw new AuthException("ias.msauth.error.noxbox");
      } else {
         int j = authhttp.getResponseCode();
         if (j >= 200 && authhttp.getResponseCode() < 300) {
            JsonObject jsonobject2 = ConfigFiles.GSON.fromJson(authhttp.readResponse(), JsonObject.class);
            return Pair.of(
               jsonobject2.get("Token").getAsString(),
               jsonobject2.getAsJsonObject("DisplayClaims").getAsJsonArray("xui").get(0).getAsJsonObject().get("uhs").getAsString()
            );
         } else {
            throw new IllegalArgumentException("authXSTS response: " + authhttp.getResponseCode());
         }
      }
   }

   public static String authMinecraft(String string, String string2) throws IllegalArgumentException, Exception {
      AuthHttp authhttp = new AuthHttp("https://api.minecraftservices.com/authentication/login_with_xbox");
      authhttp.setHeader("Content-Type", "application/json");
      authhttp.setHeader("Accept", "application/json");
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("identityToken", "XBL3.0 x=" + string + ";" + string2);
      authhttp.post(jsonobject.toString());
      if (authhttp.getResponseCode() >= 200 && authhttp.getResponseCode() < 300) {
         return ConfigFiles.GSON.fromJson(authhttp.readResponse(), JsonObject.class).get("access_token").getAsString();
      } else {
         throw new IllegalArgumentException("authMinecraft response: " + authhttp.getResponseCode());
      }
   }

   public static void I_void(String string) throws AuthException, IllegalArgumentException, Exception {
      AuthHttp authhttp = new AuthHttp("https://api.minecraftservices.com/entitlements/mcstore");
      authhttp.setHeader("Authorization", "Bearer " + string);
      authhttp.setGetMethod();
      if (authhttp.getResponseCode() >= 200 && authhttp.getResponseCode() < 300) {
         if (ConfigFiles.GSON.fromJson(authhttp.readResponse(), JsonObject.class).getAsJsonArray("items").size() == 0) {
            throw new AuthException("ias.msauth.error.gamenotowned");
         }
      } else {
         throw new IllegalArgumentException("checkGameOwnership response: " + authhttp.getResponseCode());
      }
   }

   public static Pair<UUID, String> getProfile(String string) throws IllegalArgumentException, Exception {
      AuthHttp authhttp = new AuthHttp("https://api.minecraftservices.com/minecraft/profile");
      authhttp.setHeader("Authorization", "Bearer " + string);
      authhttp.setGetMethod();
      if (authhttp.getResponseCode() >= 200 && authhttp.getResponseCode() < 300) {
         JsonObject jsonobject = ConfigFiles.GSON.fromJson(authhttp.readResponse(), JsonObject.class);
         return Pair.of(UUIDTypeAdapter.fromString(jsonobject.get("id").getAsString()), jsonobject.get("name").getAsString());
      } else {
         throw new IllegalArgumentException("getProfile response: " + authhttp.getResponseCode());
      }
   }

   public static MojangAuth loginMojang(String string, String string2) throws AuthException, IOException {
      AuthHttp authhttp = new AuthHttp("https://authserver.mojang.com/authenticate");
      authhttp.setHeader("Content-Type", "application/json");
      UUID uuid = UUID.randomUUID();
      JsonObject jsonobject = new JsonObject();
      JsonObject jsonobject1 = new JsonObject();
      jsonobject1.addProperty("name", "Minecraft");
      jsonobject1.addProperty("version", 1);
      jsonobject.add("agent", jsonobject1);
      jsonobject.addProperty("username", string);
      jsonobject.addProperty("password", string2);
      jsonobject.addProperty("clientToken", UUIDTypeAdapter.fromUUID(uuid));
      authhttp.post(jsonobject.toString());
      if (authhttp.getResponseCode() >= 200 && authhttp.getResponseCode() < 300) {
         JsonObject jsonobject3 = new Gson().fromJson(authhttp.readResponse(), JsonObject.class);
         String s4 = jsonobject3.get("accessToken").getAsString();
         UUID uuid1 = UUIDTypeAdapter.fromString(jsonobject3.get("clientToken").getAsString());
         if (!uuid1.equals(uuid)) {
            throw new AuthException("ias.mojauth.unknown", "Response token " + uuid1 + " is not equals to sent token " + uuid);
         } else {
            UUID uuid2 = UUIDTypeAdapter.fromString(jsonobject3.getAsJsonObject("selectedProfile").get("id").getAsString());
            String s3 = jsonobject3.getAsJsonObject("selectedProfile").get("name").getAsString();
            return new MojangAuth(s3, s4, uuid, uuid2);
         }
      } else {
         new JsonObject();
         String s = authhttp.readErrorResponse();

         JsonObject jsonobject2;
         try {
            jsonobject2 = new Gson().fromJson(s, JsonObject.class);
         } catch (Exception exception) {
            if (s.toLowerCase().contains("cloudfront")) {
               throw new AuthException("ias.mojauth.toofast", s);
            }

            throw new AuthException("ias.mojauth.unknown", s);
         }

         String s1 = jsonobject2.get("error").getAsString();
         boolean flag = s1.equals("ForbiddenOperationException");
         if (flag) {
            String s2 = jsonobject2.get("errorMessage").getAsString();
            if (s2.equals("Invalid credentials. Invalid username or password.")) {
               throw new AuthException("ias.mojauth.invalidcreds", jsonobject2.toString());
            }

            if (s2.equals("Invalid credentials.")) {
               throw new AuthException("ias.mojauth.toofast", jsonobject2.toString());
            }
         }

         if (s1.equals("ResourceException")) {
            throw new AuthException("ias.mojauth.migrated", jsonobject2.toString());
         } else {
            throw new AuthException("ias.mojauth.unknown", jsonobject2.toString());
         }
      }
   }
}
