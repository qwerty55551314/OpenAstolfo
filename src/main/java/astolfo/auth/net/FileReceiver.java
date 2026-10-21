package astolfo.auth.net;

import java.io.Closeable;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class FileReceiver implements Closeable {
   public final FileReceiverListener listener;
   public final boolean threaded;
   public Selector selector;
   public Thread selectThread;

   public FileReceiver(boolean bl) {
      this(new DefaultFileReceiverListener(), bl);
   }

   public FileReceiver(FileReceiverListener fileReceiverListener, boolean bl) {
      this.listener = fileReceiverListener;
      this.threaded = bl;
   }

   public void start() throws IOException {
      this.selector = Selector.open();
      if (this.threaded) {
         this.selectThread = new Thread(this::selectLoop);
         this.selectThread.start();
      } else {
         this.selectLoop();
      }
   }

   private void selectLoop() {
      while (!Thread.currentThread().isInterrupted()) {
         synchronized (this) {
            ;
         }

         try {
            this.selector.select();
         } catch (IOException ioexception1) {
            this.listener.onReceiverError(this, "sel", ioexception1);
            return;
         }

         Iterator iterator = this.selector.selectedKeys().iterator();

         while (iterator.hasNext()) {
            SelectionKey selectionkey = (SelectionKey)iterator.next();
            iterator.remove();
            if (selectionkey.isValid() && selectionkey.isReadable()) {
               FileReceiveSession filereceivesession = (FileReceiveSession)selectionkey.attachment();

               try {
                  filereceivesession.handleRead((SocketChannel)selectionkey.channel());
               } catch (IOException ioexception) {
                  this.listener.onSessionError(this, "retrieve", ioexception, filereceivesession);
               }
            }
         }
      }
   }

   public void addSession(SocketChannel socketChannel, FileReceiveSession fileReceiveSession) throws IOException {
      socketChannel.configureBlocking(false);
      synchronized (this) {
         this.selector.wakeup();
         socketChannel.register(this.selector, 1, fileReceiveSession);
      }
   }

   @Override
   public void close() throws IOException {
      if (this.selectThread != null) {
         this.selectThread.interrupt();
      }

      this.selector.close();
   }
}
