package astolfo.auth.net;

import java.io.Closeable;
import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;

public interface NioConnection extends Closeable {
   void start(SocketChannel var1) throws IOException;

   @Override
   void close() throws IOException;

   void registerChannel(SocketChannel var1, int var2) throws ClosedChannelException;
}
