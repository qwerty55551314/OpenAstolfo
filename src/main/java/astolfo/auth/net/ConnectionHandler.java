package astolfo.auth.net;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public interface ConnectionHandler {
   void handleConnected(SocketChannel var1) throws IOException;
}
