package astolfo.auth.net;

import java.io.IOException;
import java.net.SocketAddress;

public interface Connector {
   void connect(Connection var1, ChannelOpener var2, SocketAddress var3) throws IOException;
}
