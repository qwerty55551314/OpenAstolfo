package astolfo.http;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public interface Request {
   String CHARSET = StandardCharsets.UTF_8.name();
   String PARAM_SEPARATOR = "&";
   String KEY_VALUE_SEPARATOR = "=";
   String QUERY_PREFIX = "?";

   Map<String, String> getParameters();

   String getMethod();

   ContentType getContentType();

   int getContentLength();

   Request addParameter(String var1, String var2) throws Exception;

   Request removeParameter(String var1) throws Exception;

   String getBody() throws Exception;

   default RequestWrapper wrap(Request request) {
      return new RequestWrapper(request);
   }

   default RequestWrapper toWrapper() {
      return new RequestWrapper(this);
   }
}
