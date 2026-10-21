package astolfo.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public final class Response {
   private static final String LINE_SEPARATOR = System.lineSeparator();
   private final int statusCode;
   private final String body;
   private final Map<String, List<String>> headers;
   private static final long c = -6498816265136635504L;

   private Response(int i, String string, Map<String, List<String>> map) {
      this.statusCode = i;
      this.body = string;
      this.headers = map;
   }

   public static final Response fromConnection(HttpURLConnection httpURLConnection, boolean bl) throws IOException {
      String s = readBody(httpURLConnection, bl);
      int i = httpURLConnection.getResponseCode();
      httpURLConnection.disconnect();
      return new Response(i, s, httpURLConnection.getHeaderFields());
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static String readBody(URLConnection urlConnection, boolean bl) throws IOException {
      boolean flag1 = false;
      StringBuilder stringbuilder = new StringBuilder();
      HttpURLConnection httpurlconnection = (HttpURLConnection)urlConnection;
      InputStream inputstream = httpurlconnection.getResponseCode() < (int)c ? urlConnection.getInputStream() : httpurlconnection.getErrorStream();
      BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
      Throwable throwable = null;

      while (true) {
         boolean flag = false /* VF: Semaphore variable */;

         try {
            flag = true;
            String s;
            if ((s = bufferedreader.readLine()) == null) {
               flag = false;
               break;
            }

            stringbuilder.append(s);
            if (bl) {
               stringbuilder.append(LINE_SEPARATOR);
            }
         } catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
         } finally {
            if (flag) {
               if (bufferedreader != null) {
                  if (throwable != null) {
                     try {
                        bufferedreader.close();
                     } catch (Throwable throwable1) {
                        throwable.addSuppressed(throwable1);
                     }
                  } else {
                     bufferedreader.close();
                  }
               }
            }
         }
      }

      if (bufferedreader != null) {
         bufferedreader.close();
      }

      return stringbuilder.toString();
   }

   public int getStatusCode() {
      return this.statusCode;
   }

   public String getBody() {
      return this.body;
   }

   public Map<String, List<String>> getHeaders() {
      return this.headers;
   }
}
