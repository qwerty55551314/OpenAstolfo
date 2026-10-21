package astolfo.auth.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public final class AddressHandshakeReader {
   private final ByteBuffer lengthBuffer = ByteBuffer.allocateDirect(4);

   public HandshakeAddresses readAddresses(SocketChannel socketChannel) throws IOException {
      boolean flag = socketChannel.isBlocking();
      socketChannel.configureBlocking(true);
      Object object = null;
      socketChannel.read(this.lengthBuffer);
      ((Buffer)this.lengthBuffer).flip();
      int i = this.lengthBuffer.getInt();
      ((Buffer)this.lengthBuffer).clear();
      ByteBuffer bytebuffer = ByteBuffer.allocate(i);
      socketChannel.read(bytebuffer);
      ((Buffer)bytebuffer).flip();
      byte[] abyte = new byte[i];
      bytebuffer.get(abyte);
      socketChannel.configureBlocking(flag);
      String s = new String(abyte, StandardCharsets.UTF_8);
      String s1 = ((InetSocketAddress)socketChannel.getRemoteAddress()).getAddress().getHostAddress();
      return new HandshakeAddresses(s, s1);
   }
}
