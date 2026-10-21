package astolfo.server;

import astolfo.ClientAccess;

public final class HypixelServer extends Server {
   private static String Y;

   public HypixelServer() {
      super("net", "hypixel", ServerType.HYPIXEL);
   }

   @Override
   public boolean isBrandValid() {
      return ClientAccess.mc.thePlayer.getClientBrand().toLowerCase().contains("hypixel");
   }

   public static void V(String string) {
      Y = string;
   }

   static {
      V("QYo5Qc");
   }
}
