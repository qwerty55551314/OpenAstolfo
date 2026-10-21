package astolfo.via.util;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public class VFAddressParser {
   public Integer protocol;
   public String viaSuffix;
   public String serverAddress;
   public String viaOptions;
   private static final String b = "viafabric";
   private static final long c = -4307704811790270369L;

   public VFAddressParser parse(String string) {
      return this.parseWithSuffix(string, b);
   }

   public String getFullSuffix() {
      return this.viaOptions != null && !this.viaOptions.isEmpty() ? this.viaOptions + "." + this.viaSuffix : this.viaSuffix;
   }

   public VFAddressParser parseWithSuffix(String string, String string2) {
      string = StringUtils.removeEnd(string, ".");
      String s = StringUtils.removeEnd(string, "." + string2);
      boolean flag1 = s.equals(string);
      if (flag1) {
         this.serverAddress = string;
         return this;
      } else {
         boolean flag = false;
         ArrayList arraylist = new ArrayList();
         ArrayList arraylist1 = new ArrayList();

         for (String s1 : Lists.reverse(Arrays.asList(s.split(Pattern.quote("."))))) {
            if (!flag && this.parseOption(s1)) {
               arraylist.add(s1);
            } else {
               flag = true;
               arraylist1.add(s1);
            }
         }

         this.serverAddress = String.join(".", Lists.reverse(arraylist1));
         this.viaOptions = String.join(".", Lists.reverse(arraylist));
         this.viaSuffix = string2;
         return this;
      }
   }

   public boolean parseOption(String string) {
      int i = string.length();
      if (i < 2) {
         return false;
      } else {
         boolean flag = string.startsWith("_");
         String s;
         if (flag) {
            s = String.valueOf(string.charAt(1));
         } else {
            char c0 = string.charAt(1);
            if (c0 != (int)c) {
               return false;
            }

            s = String.valueOf(string.charAt(0));
         }

         String s1 = string.substring(2);
         if ("v".equals(s)) {
            this.parseProtocol(s1);
         }

         return true;
      }
   }

   public void parseProtocol(String string) {
      this.protocol = Ints.tryParse(string);
      if (this.protocol == null) {
         ProtocolVersion protocolversion = ProtocolVersion.getClosest(string.replace("_", "."));
         if (protocolversion != null) {
            this.protocol = protocolversion.getVersion();
         }
      }
   }
}
