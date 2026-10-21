package astolfo.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

public class HttpUtil {
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
            stringBuilder.append(s).append("\n");
         }

         bufferedreader.close();
      } catch (Exception exception) {
      }
   }

   public static String get(HttpRequest httpRequest) {
      return get(httpRequest, Proxy.NO_PROXY);
   }

   public static String get(HttpRequest httpRequest, Proxy proxy) {
      try {
         String s = httpRequest.buildParameterString();
         URL url = new URL(httpRequest.url + (s.isEmpty() ? "" : "?" + s));
         HttpURLConnection httpurlconnection = (HttpURLConnection)url.openConnection(proxy);
         httpurlconnection.setRequestMethod("GET");
         httpurlconnection.setRequestProperty("User-Agent", httpRequest.userAgent);
         httpurlconnection.setRequestProperty("Accept", httpRequest.accept);
         httpurlconnection.setRequestProperty("Content-Type", httpRequest.contentType);

         for (int i = 0; i < httpRequest.headerKeys.length; i++) {
            httpurlconnection.setRequestProperty(httpRequest.headerKeys[i], httpRequest.headerValues[i]);
         }

         StringBuilder stringbuilder = new StringBuilder();
         readResponse(httpurlconnection, stringbuilder);
         return stringbuilder.toString();
      } catch (Exception exception) {
         return null;
      }
   }

   public static String post(HttpRequest httpRequest) {
      return post(httpRequest, Proxy.NO_PROXY);
   }

   public static String post(HttpRequest httpRequest, Proxy proxy) {
      try {
         URL url = new URL(httpRequest.url);
         HttpURLConnection httpurlconnection = (HttpURLConnection)url.openConnection(proxy);
         httpurlconnection.setRequestMethod("POST");
         httpurlconnection.setRequestProperty("User-Agent", httpRequest.userAgent);
         httpurlconnection.setRequestProperty("Accept", httpRequest.accept);
         httpurlconnection.setRequestProperty("Content-Type", httpRequest.contentType);

         for (int i = 0; i < httpRequest.headerKeys.length; i++) {
            httpurlconnection.setRequestProperty(httpRequest.headerKeys[i], httpRequest.headerValues[i]);
         }

         httpurlconnection.setDoInput(true);
         httpurlconnection.setDoOutput(true);
         DataOutputStream dataoutputstream = new DataOutputStream(httpurlconnection.getOutputStream());
         dataoutputstream.writeBytes(httpRequest.buildParameterString());
         dataoutputstream.flush();
         dataoutputstream.close();
         StringBuilder stringbuilder = new StringBuilder();
         readResponse(httpurlconnection, stringbuilder);
         return stringbuilder.toString();
      } catch (Exception exception) {
         return null;
      }
   }
}
