package net.minecraft.client.multiplayer;

import java.net.IDN;
import java.util.Hashtable;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;

public class ServerAddress {
   private final String ipAddress;
   private final int serverPort;

   private ServerAddress(String address, int port) {
      this.ipAddress = address;
      this.serverPort = port;
   }

   public String getIP() {
      return IDN.toASCII(this.ipAddress);
   }

   public int getPort() {
      return this.serverPort;
   }

   public static ServerAddress fromString(String string) {
      if (string == null) {
         return null;
      } else {
         String[] astring = string.split(":");
         boolean flag = string.startsWith("[");
         if (flag) {
            int i = string.indexOf("]");
            if (i > 0) {
               String s = string.substring(1, i);
               String s1 = string.substring(i + 1).trim();
               if (s1.startsWith(":") && s1.length() > 0) {
                  s1 = s1.substring(1);
                  astring = new String[]{s, s1};
               } else {
                  astring = new String[]{s};
               }
            }
         }

         if (astring.length > 2) {
            astring = new String[]{string};
         }

         String s2 = astring[0];
         int j = astring.length;
         int k = j > 1 ? parseIntWithDefault(astring[1], 25565) : 25565;
         if (k == 25565) {
            String[] astring1 = getServerAddress(s2);
            s2 = astring1[0];
            k = parseIntWithDefault(astring1[1], 25565);
         }

         return new ServerAddress(s2, k);
      }
   }

   private static String[] getServerAddress(String string) {
      try {
         Class.forName("com.sun.jndi.dns.DnsContextFactory");
         Hashtable hashtable = new Hashtable();
         hashtable.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
         hashtable.put("java.naming.provider.url", "dns:");
         hashtable.put("com.sun.jndi.dns.timeout.retries", "1");
         InitialDirContext initialdircontext = new InitialDirContext(hashtable);
         Attributes attributes = initialdircontext.getAttributes("_minecraft._tcp." + string, new String[]{"SRV"});
         String[] astring = attributes.get("srv").get().toString().split(" ", 4);
         return new String[]{astring[3], astring[2]};
      } catch (Throwable throwable) {
         return new String[]{string, Integer.toString(25565)};
      }
   }

   private static int parseIntWithDefault(String string, int i) {
      try {
         return Integer.parseInt(string.trim());
      } catch (Exception exception) {
         return i;
      }
   }
}
