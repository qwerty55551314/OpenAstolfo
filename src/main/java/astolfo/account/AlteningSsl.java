package astolfo.account;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import net.minecraft.entity.Entity;

public class AlteningSsl {
   private boolean initialized = false;
   private static Entity[] D;

   public void initialize() {
      if (!this.initialized) {
         this.installTrustAllManager();
         this.whitelistTheAltening();
         this.initialized = true;
      }
   }

   private void installTrustAllManager() {
      TrustManager[] atrustmanager = new TrustManager[]{new AlteningSsl.AlteningTrustManager()};

      try {
         SSLContext sslcontext = SSLContext.getInstance("SSL");
         sslcontext.init(null, atrustmanager, new SecureRandom());
         HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());
      } catch (Exception exception) {
      }
   }

   private void whitelistTheAltening() {
      HttpsURLConnection.setDefaultHostnameVerifier(
         (string, sslSession) -> string.equals("authserver.thealtening.com") || string.equals("sessionserver.thealtening.com")
      );
   }

   public boolean isInitialized() {
      return this.initialized;
   }

   public void setInitialized(boolean bl) {
      this.initialized = bl;
   }

   public static void y(Entity[] entityArray) {
      D = entityArray;
   }

   public static Entity[] U() {
      return D;
   }

   static {
      y(new Entity[3]);
   }

   public class AlteningTrustManager implements X509TrustManager {
      @Override
      public X509Certificate[] getAcceptedIssuers() {
         return null;
      }

      @Override
      public void checkClientTrusted(X509Certificate[] x509Certificates, String string) {
      }

      @Override
      public void checkServerTrusted(X509Certificate[] x509Certificates, String string) {
      }
   }
}
