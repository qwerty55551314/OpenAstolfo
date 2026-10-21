package astolfo.auth.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.AlreadyConnectedException;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public final class AddressHandshake {
   private final ByteBuffer lengthBuffer = ByteBuffer.allocateDirect(4);

   public void connect(SocketChannel socketChannel, SocketAddress socketAddress) throws IOException {
      if (socketChannel.isConnected()) {
         throw new AlreadyConnectedException();
      } else {
         boolean flag = socketChannel.isBlocking();
         socketChannel.configureBlocking(true);
         socketChannel.connect(socketAddress);
         this.sendLocalAddress(socketChannel);
         socketChannel.configureBlocking(flag);
      }
   }

   private void sendLocalAddress(SocketChannel socketChannel) throws IOException {
      String s = ((InetSocketAddress)socketChannel.getLocalAddress()).getAddress().getHostAddress();
      byte[] abyte = s.getBytes(StandardCharsets.UTF_8);
      int i = abyte.length;
      this.lengthBuffer.putInt(i);
      ((Buffer)this.lengthBuffer).flip();
      socketChannel.write(this.lengthBuffer);
      ((Buffer)this.lengthBuffer).clear();
      ByteBuffer bytebuffer = ByteBuffer.allocate(i);
      bytebuffer.put(abyte);
      ((Buffer)bytebuffer).flip();
      socketChannel.write(bytebuffer);
   }
}
