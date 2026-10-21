package astolfo.auth.net;

import java.io.IOException;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PacketConnection extends AbstractNioConnection {
   private final ConnectionListener connectionListener;
   private final Map<Short, PacketReader<?>> readers;
   private final Map<Short, List<PacketListener<?>>> listeners;
   private final PacketOutput packetOutput;
   private final Runnable onOpen;
   private SocketWriter socketWriter;
   private PacketReceiver receiver;
   private SocketChannel socketChannel;

   public PacketConnection(
      ConnectionListener connectionListener,
      Map<Short, PacketReader<?>> map,
      Map<Short, List<PacketListener<?>>> map2,
      PacketOutput packetOutput,
      Runnable runnable
   ) {
      this.connectionListener = connectionListener;
      this.readers = map;
      this.listeners = map2;
      this.packetOutput = packetOutput;
      this.onOpen = runnable;
   }

   @Override
   public void start(SocketChannel socketChannel) throws IOException {
      super.start(socketChannel);
      Object object = null;
      this.socketChannel = socketChannel;
      this.socketWriter = new SocketWriter(socketChannel, super.selector);
      this.receiver = new PacketReceiver(socketChannel, this.readers, this.listeners);
      this.packetOutput.attach(this.socketWriter);
      this.onOpen.run();

      while (true) {
         try {
            super.selector.select();
         } catch (ClosedSelectorException | IOException ioexception2) {
            this.connectionListener.onError(this, "sel", ioexception2);
            return;
         }

         Iterator iterator = super.selector.selectedKeys().iterator();

         while (iterator.hasNext()) {
            SelectionKey selectionkey = (SelectionKey)iterator.next();
            iterator.remove();
            if (selectionkey.isValid()) {
               if (selectionkey.isWritable()) {
                  try {
                     this.socketWriter.flush();
                  } catch (IOException ioexception) {
                     this.connectionListener.onError(this, "write", ioexception);
                  }
               } else if (selectionkey.isReadable()) {
                  try {
                     if (this.receiver.receive() == ReceiveResult.C) {
                        this.connectionListener.onClose(this, "eof");
                     }
                  } catch (IOException ioexception1) {
                     this.connectionListener.onError(this, "retrieve", ioexception1);
                  }
               }
            }
         }
      }
   }

   @Override
   public void close() throws IOException {
      try {
         super.selector.close();
      } finally {
         this.socketChannel.close();
      }
   }
}
