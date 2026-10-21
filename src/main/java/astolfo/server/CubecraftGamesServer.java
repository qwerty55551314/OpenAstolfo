package astolfo.server;

public class CubecraftGamesServer extends Server {
   public CubecraftGamesServer() {
      super("net", "cubecraftgames", ServerType.CUBECRAFT);
   }

   @Override
   public boolean isBrandValid() {
      return true;
   }
}
