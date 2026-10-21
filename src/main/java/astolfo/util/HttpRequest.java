package astolfo.util;

public class HttpRequest {
   public String url;
   public String body;
   public String userAgent;
   public String accept;
   public String contentType;
   public String[] paramKeys;
   public String[] paramValues;
   public String[] headerKeys;
   public String[] headerValues;

   public HttpRequest() {
      if (true) {
         this.paramKeys = new String[0];
         this.paramValues = new String[0];
         this.headerKeys = new String[0];
         this.headerValues = new String[0];
         this.setUserAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.13) Gecko/20080311 Firefox/2.0.0.13");
         this.setAccept("application/json");
         this.setContentType("application/json");
      } else {
         this.paramKeys = new String[0];
         this.paramValues = new String[0];
         this.headerKeys = new String[0];
         this.headerValues = new String[0];
         this.setUserAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.13) Gecko/20080311 Firefox/2.0.0.13");
         this.setAccept("application/json");
         this.setContentType("application/json");
         int i = 0;
         i++;
      }
   }

   public HttpRequest setUrl(String string) {
      this.url = string;
      return this;
   }

   public HttpRequest setUserAgent(String string) {
      this.userAgent = string;
      return this;
   }

   public HttpRequest setAccept(String string) {
      this.accept = string;
      return this;
   }

   public HttpRequest setContentType(String string) {
      this.contentType = string;
      return this;
   }

   public HttpRequest setParamKeys(String... strings) {
      this.paramKeys = strings;
      return this;
   }

   public HttpRequest setParamValues(String... strings) {
      this.paramValues = strings;
      return this;
   }

   public HttpRequest setBody(String string) {
      this.body = string;
      return this;
   }

   public HttpRequest setHeaderKeys(String... strings) {
      this.headerKeys = strings;
      return this;
   }

   public HttpRequest setHeaderValues(String... strings) {
      this.headerValues = strings;
      return this;
   }

   public String buildParameterString() {
      if (this.body != null) {
         return this.body;
      } else {
         StringBuilder stringbuilder = new StringBuilder();

         for (int i = 0; i < this.paramKeys.length; i++) {
            stringbuilder.append(this.paramKeys[i]).append("=").append(this.paramValues[i]);
         }

         return stringbuilder.toString();
      }
   }

   public static boolean Z() {
      return true;
   }
}
