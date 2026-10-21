package astolfo.auth;

import astolfo.auth.fingerprint.DefaultFingerprintRegistry;
import astolfo.auth.fingerprint.FingerprintRegistry;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class AuthUtil {
   private static final FingerprintRegistry fingerprintRegistry = new DefaultFingerprintRegistry();
   private static final HwidBuilder hwidBuilder = new HwidOptions()
      .withUsername(true)
      .withCpu(true)
      .withBaseboard(true)
      .withGpu(true)
      .withMemory(true)
      .withDisk(true)
      .build();

   private static void readResponse(HttpURLConnection httpURLConnection, StringBuilder stringBuilder) {
      try {
         InputStream inputstream;
         if (httpURLConnection.getResponseCode() == 200) {
            inputstream = httpURLConnection.getInputStream();
         } else {
            inputstream = httpURLConnection.getErrorStream();
         }

         if (inputstream == null) {
            throw new Exception();
         }

         InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
         BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

         String s;
         while ((s = bufferedreader.readLine()) != null) {
            stringBuilder.append(s).append(AuthUtilStrings.decrypt("rKA="));
         }

         bufferedreader.close();
      } catch (Exception exception) {
      }
   }

   public static String httpGet(AuthHttpRequest authHttpRequest) {
      return httpGetWithProxy(authHttpRequest, Proxy.NO_PROXY);
   }

   public static String httpGetWithProxy(AuthHttpRequest authHttpRequest, Proxy proxy) {
      try {
         String s = authHttpRequest.buildBody();
         URL url = new URL(authHttpRequest.url + (s.isEmpty() ? "" : "?" + s));
         HttpURLConnection httpurlconnection = (HttpURLConnection)url.openConnection(proxy);
         httpurlconnection.setRequestMethod(AuthUtilStrings.decrypt("t4un"));
         httpurlconnection.setRequestProperty(AuthUtilStrings.decrypt("pb2WuLcxehEzMg=="), authHttpRequest.userAgent);
         httpurlconnection.setRequestProperty(AuthUtilStrings.decrypt("sa2Qr+oE"), authHttpRequest.accept);
         httpurlconnection.setRequestProperty(AuthUtilStrings.decrypt("s6Gdvv8eaVkJP/5C"), authHttpRequest.contentType);

         for (int i = 0; i < authHttpRequest.headerNames.length; i++) {
            httpurlconnection.setRequestProperty(authHttpRequest.headerNames[i], authHttpRequest.headerValues[i]);
         }

         StringBuilder stringbuilder = new StringBuilder();
         readResponse(httpurlconnection, stringbuilder);
         return stringbuilder.toString();
      } catch (Exception exception) {
         NullPointerException nullpointerexception = new NullPointerException();
         nullpointerexception.setStackTrace(new StackTraceElement[0]);
         throw nullpointerexception;
      }
   }

   public static String httpGetOrError(AuthHttpRequest authHttpRequest) {
      try {
         String s = authHttpRequest.buildBody();
         URL url = new URL(authHttpRequest.url + (s.isEmpty() ? "" : "?" + s));
         HttpURLConnection httpurlconnection = (HttpURLConnection)url.openConnection(Proxy.NO_PROXY);
         httpurlconnection.setRequestMethod(AuthUtilStrings.decrypt("t4un"));
         httpurlconnection.setRequestProperty(AuthUtilStrings.decrypt("pb2WuLcxehEzMg=="), authHttpRequest.userAgent);
         httpurlconnection.setRequestProperty(AuthUtilStrings.decrypt("sa2Qr+oE"), authHttpRequest.accept);
         httpurlconnection.setRequestProperty(AuthUtilStrings.decrypt("s6Gdvv8eaVkJP/5C"), authHttpRequest.contentType);

         for (int i = 0; i < authHttpRequest.headerNames.length; i++) {
            httpurlconnection.setRequestProperty(authHttpRequest.headerNames[i], authHttpRequest.headerValues[i]);
         }

         StringBuilder stringbuilder = new StringBuilder();
         readResponse(httpurlconnection, stringbuilder);
         return stringbuilder.toString();
      } catch (IOException ioexception) {
         return ioexception.getMessage();
      }
   }

   public static String httpPost(AuthHttpRequest authHttpRequest) {
      return httpPostWithProxy(authHttpRequest, Proxy.NO_PROXY);
   }

   public static String httpPostWithProxy(AuthHttpRequest authHttpRequest, Proxy proxy) {
      try {
         URL url = new URL(authHttpRequest.url);
         HttpURLConnection httpurlconnection = (HttpURLConnection)url.openConnection(proxy);
         httpurlconnection.setRequestMethod(AuthUtilStrings.decrypt("oIGgng=="));
         httpurlconnection.setRequestProperty(AuthUtilStrings.decrypt("pb2WuLcxehEzMg=="), authHttpRequest.userAgent);
         httpurlconnection.setRequestProperty(AuthUtilStrings.decrypt("sa2Qr+oE"), authHttpRequest.accept);
         httpurlconnection.setRequestProperty(AuthUtilStrings.decrypt("s6Gdvv8eaVkJP/5C"), authHttpRequest.contentType);

         for (int i = 0; i < authHttpRequest.headerNames.length; i++) {
            httpurlconnection.setRequestProperty(authHttpRequest.headerNames[i], authHttpRequest.headerValues[i]);
         }

         httpurlconnection.setDoInput(true);
         httpurlconnection.setDoOutput(true);
         DataOutputStream dataoutputstream = new DataOutputStream(httpurlconnection.getOutputStream());
         dataoutputstream.writeBytes(authHttpRequest.buildBody());
         dataoutputstream.flush();
         dataoutputstream.close();
         StringBuilder stringbuilder = new StringBuilder();
         readResponse(httpurlconnection, stringbuilder);
         return stringbuilder.toString().replace("\n", "");
      } catch (Exception exception) {
         NullPointerException nullpointerexception = new NullPointerException();
         nullpointerexception.setStackTrace(new StackTraceElement[0]);
         throw nullpointerexception;
      }
   }

   public static void openUrl(String string) {
      try {
         browseUri(new URI(string));
      } catch (URISyntaxException urisyntaxexception) {
         urisyntaxexception.printStackTrace();
      }
   }

   private static void browseUri(URI uri) {
      try {
         Desktop.getDesktop().browse(uri);
      } catch (IOException ioexception) {
         ioexception.printStackTrace();
      }
   }

   public static String getHwid() {
      return md5(hwidBuilder.getRawHwid());
   }

   public static String md5(String string) {
      try {
         MessageDigest messagedigest = MessageDigest.getInstance("MD5");
         byte[] abyte = messagedigest.digest(string.getBytes());
         StringBuilder stringbuilder = new StringBuilder();

         for (byte b0 : abyte) {
            String s = Integer.toHexString(255 & b0);
            if (s.length() == 1) {
               stringbuilder.append('0');
            }

            stringbuilder.append(s);
         }

         return stringbuilder.toString();
      } catch (NoSuchAlgorithmException nosuchalgorithmexception) {
         throw new RuntimeException(nosuchalgorithmexception);
      }
   }

   static {
      fingerprintRegistry.init();
   }
}
