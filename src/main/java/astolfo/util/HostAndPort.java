package astolfo.util;

public final class HostAndPort {
   public static final String SEPARATOR = ":";
   private final String host;
   private final int port;
   private static boolean O;

   public HostAndPort(String string, int i) {
      this.host = string;
      this.port = i;
   }

   public String getHost() {
      return this.host;
   }

   public int getPort() {
      return this.port;
   }

   @Override
   public int hashCode() {
      int i = 1;
      i = 37 * i + this.host.hashCode();
      return 37 * i + this.port;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else {
         Class oclass = object.getClass();
         if (oclass != HostAndPort.class) {
            return false;
         } else {
            HostAndPort hostandport = (HostAndPort)object;
            boolean flag = hostandport.host.equals(this.host);
            if (flag) {
               int i = hostandport.port;
               if (i == this.port) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   @Override
   public String toString() {
      return super.toString();
   }

   public static void s(boolean flag) {
      O = flag;
   }

   public static boolean D() {
      return O;
   }

   public static boolean j() {
      return true;
   }

   static {
      s(false);
   }
}
