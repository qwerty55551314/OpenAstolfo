package astolfo.auth.net;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.function.Consumer;

public interface ChannelOpener {
   SocketChannel openChannelWith(Consumer<SocketChannel> var1) throws IOException;

   SocketChannel openChannel() throws IOException;
}
