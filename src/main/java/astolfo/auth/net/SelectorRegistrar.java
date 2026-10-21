package astolfo.auth.net;

import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;

public interface SelectorRegistrar {
   void register(NioConnection var1, SocketChannel var2) throws ClosedChannelException;
}
