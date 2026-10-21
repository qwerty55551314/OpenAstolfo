package astolfo.auth.net;

public class ConnectorFactoryBuilder {
   private static final ConnectionOptions defaultOptions = ConnectionOptions.ofSize(8);
   public final boolean extended;
   public boolean extendedEnabled;

   public ConnectorFactoryBuilder(boolean bl) {
      this.extended = bl;
   }

   public ConnectorFactoryBuilder() {
      this(false);
   }

   public ConnectorFactory build() {
      ConnectionOptions connectionoptions = defaultOptions;
      boolean flag = this.extended;
      if (flag) {
         boolean flag1 = this.extendedEnabled;
         if (flag1) {
            connectionoptions = ConnectionOptions.ofSize(16);
         }
      }

      return new ConnectorFactory();
   }
}
