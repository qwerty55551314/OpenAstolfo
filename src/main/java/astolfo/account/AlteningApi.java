package astolfo.account;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public final class AlteningApi {
   private String apiKey;
   private final String baseUrl = "http://api.thealtening.com/v1/";
   private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

   public AlteningApi(String string) {
      this.apiKey = string;
   }

   public AlteningLicense getLicense() throws IOException {
      URLConnection urlconnection = new URL(this.appendApiKey("http://api.thealtening.com/v1/" + "license")).openConnection();
      String s = new String(StreamReader.getInstance().readAllBytes(urlconnection.getInputStream()));
      return this.gson.fromJson(s, AlteningLicense.class);
   }

   public AlteningAccountData generateAccount(AlteningLicense alteningLicense) throws IOException {
      HttpURLConnection httpurlconnection = (HttpURLConnection)new URL(this.appendApiKey("http://api.thealtening.com/v1/" + "generate")).openConnection();
      InputStream inputstream = httpurlconnection.getInputStream();
      String s = new String(StreamReader.getInstance().readAllBytes(inputstream));
      if (alteningLicense.isPremium()) {
         inputstream.close();
         httpurlconnection.disconnect();
         return this.gson.fromJson(s, AlteningAccountData.class);
      } else {
         if (inputstream != null) {
            inputstream.close();
         }

         if (httpurlconnection != null) {
            httpurlconnection.disconnect();
         }

         return null;
      }
   }

   public boolean favoriteAccount(AlteningAccountData alteningAccountData) throws IOException {
      URLConnection urlconnection = new URL(this.buildAccountUrl("http://api.thealtening.com/v1/" + "favorite", alteningAccountData)).openConnection();
      String s = new String(StreamReader.getInstance().readAllBytes(urlconnection.getInputStream()));
      return s.isEmpty();
   }

   public boolean privateAccount(AlteningAccountData alteningAccountData) throws IOException {
      URLConnection urlconnection = new URL(this.buildAccountUrl("http://api.thealtening.com/v1/" + "private", alteningAccountData)).openConnection();
      String s = new String(StreamReader.getInstance().readAllBytes(urlconnection.getInputStream()));
      return s.isEmpty();
   }

   private String appendApiKey(String string) {
      return string + "?token=" + this.apiKey;
   }

   private String buildAccountUrl(String string, AlteningAccountData alteningAccountData) {
      return string + "?token=" + this.apiKey + "&acctoken=" + alteningAccountData.getToken();
   }

   public String getApiKey() {
      return this.apiKey;
   }

   public void setApiKey(String string) {
      this.apiKey = string;
   }
}
