package astolfo.http;

import java.net.URL;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.entity.Entity;

public abstract class AbstractRequest implements Request {
   public final Map<String, String> parameters = new HashMap<>();
   private static Entity[] H;

   @Override
   public Request addParameter(String string, String string2) throws Exception {
      String s = URLEncoder.encode(string, CHARSET).trim();
      String s1 = URLEncoder.encode(string2, CHARSET).trim();
      this.parameters.put(s, s1);
      return this;
   }

   @Override
   public Request removeParameter(String string) throws Exception {
      String s = URLEncoder.encode(string, CHARSET).trim();
      this.parameters.remove(s);
      return this;
   }

   @Override
   public Map<String, String> getParameters() {
      return Collections.unmodifiableMap(this.parameters);
   }

   public abstract URL buildUrl(URL var1) throws Exception;

   public abstract Map<String, String> getHeaders();

   public static void V(Entity[] entityArray) {
      H = entityArray;
   }

   public static Entity[] p() {
      return H;
   }

   static {
      V(new Entity[2]);
   }
}
