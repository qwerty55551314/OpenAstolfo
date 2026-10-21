package net.minecraft.src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import net.minecraft.client.Minecraft;

public class HttpUtils {
   public static final String SERVER_URL = "http://s.optifine.net";
   public static final String POST_URL = "http://optifine.net";

   public static byte[] get(String urlStr) throws IOException {
      HttpURLConnection httpurlconnection = null;

      byte[] abyte;
      try {
         URL url = new URL(urlStr);
         httpurlconnection = (HttpURLConnection)url.openConnection(Minecraft.getMinecraft().getProxy());
         httpurlconnection.setDoInput(true);
         httpurlconnection.setDoOutput(false);
         httpurlconnection.connect();
         if (httpurlconnection.getResponseCode() / 100 != 2) {
            if (httpurlconnection.getErrorStream() != null) {
               Config.readAll(httpurlconnection.getErrorStream());
            }

            throw new IOException("HTTP response: " + httpurlconnection.getResponseCode());
         }

         InputStream inputstream = httpurlconnection.getInputStream();
         byte[] abyte1 = new byte[httpurlconnection.getContentLength()];
         int i = 0;

         do {
            int j = inputstream.read(abyte1, i, abyte1.length - i);
            if (j < 0) {
               throw new IOException("Input stream closed: " + urlStr);
            }

            i += j;
         } while (i < abyte1.length);

         abyte = abyte1;
      } finally {
         if (httpurlconnection != null) {
            httpurlconnection.disconnect();
         }
      }

      return abyte;
   }

   public static String post(String urlStr, Map headers, byte[] content) throws IOException {
      HttpURLConnection httpurlconnection = null;

      String s;
      try {
         URL url = new URL(urlStr);
         httpurlconnection = (HttpURLConnection)url.openConnection(Minecraft.getMinecraft().getProxy());
         httpurlconnection.setRequestMethod("POST");
         if (headers != null) {
            for (Object object : headers.keySet()) {
               String s1 = "" + headers.get(object);
               httpurlconnection.setRequestProperty((String)object, s1);
            }
         }

         httpurlconnection.setRequestProperty("Content-Type", "text/plain");
         httpurlconnection.setRequestProperty("Content-Length", "" + content.length);
         httpurlconnection.setRequestProperty("Content-Language", "en-US");
         httpurlconnection.setUseCaches(false);
         httpurlconnection.setDoInput(true);
         httpurlconnection.setDoOutput(true);
         OutputStream outputstream = httpurlconnection.getOutputStream();
         outputstream.write(content);
         outputstream.flush();
         outputstream.close();
         InputStream inputstream = httpurlconnection.getInputStream();
         InputStreamReader inputstreamreader = new InputStreamReader(inputstream, "ASCII");
         BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
         StringBuffer stringbuffer = new StringBuffer();

         String s2;
         while ((s2 = bufferedreader.readLine()) != null) {
            stringbuffer.append(s2);
            stringbuffer.append('\r');
         }

         bufferedreader.close();
         s = stringbuffer.toString();
      } finally {
         if (httpurlconnection != null) {
            httpurlconnection.disconnect();
         }
      }

      return s;
   }
}
