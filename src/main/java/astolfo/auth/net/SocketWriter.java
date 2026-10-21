package astolfo.auth.net;

import astolfo.auth.net.scramble.ScramblerRange;
import astolfo.auth.net.scramble.Scramblers;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

public final class SocketWriter {
   private final SocketChannel socketChannel;
   private final Selector selector;
   private final Queue<OutgoingPacket> pendingPackets;
   private final ByteBuffer headerBuffer;
   private final ByteBuffer chunkHeaderBuffer;
   private final ByteBuffer[] chunkBuffers;
   private boolean writePending;
   private boolean headerWritten;
   private boolean headerPrepared;
   private boolean chunkHeaderWritten;
   private boolean chunkPrepared;
   private byte xorKey;

   public SocketWriter(SocketChannel socketChannel, Selector selector) {
      this.socketChannel = socketChannel;
      this.selector = selector;
      if (null != null) {
         this.pendingPackets = new ArrayDeque<>();
         this.headerBuffer = ByteBuffer.allocateDirect(5);
         this.chunkHeaderBuffer = ByteBuffer.allocateDirect(3);
         this.chunkBuffers = new ByteBuffer[2];
         this.chunkBuffers[0] = this.chunkHeaderBuffer;
         int i = 0;
         i++;
      } else {
         this.pendingPackets = new ArrayDeque<>();
         this.headerBuffer = ByteBuffer.allocateDirect(5);
         this.chunkHeaderBuffer = ByteBuffer.allocateDirect(3);
         this.chunkBuffers = new ByteBuffer[2];
         this.chunkBuffers[0] = this.chunkHeaderBuffer;
      }
   }

   public void flush() throws IOException {
      while (!this.pendingPackets.isEmpty()) {
         OutgoingPacket outgoingpacket = this.pendingPackets.peek();
         Queue queue = outgoingpacket.chunks;
         if (!this.headerWritten) {
            if (!this.headerPrepared) {
               this.xorKey = RandomValues.randomByte();
               this.headerBuffer.put(XorMasks.xor72(this.xorKey));
               this.headerBuffer.putShort((short)(queue.size() ^ this.xorKey));
               this.headerBuffer.putShort((short)(outgoingpacket.id ^ this.xorKey));
               ((Buffer)this.headerBuffer).flip();
               this.headerPrepared = true;
            }

            this.socketChannel.write(this.headerBuffer);
            if (this.headerBuffer.hasRemaining()) {
               return;
            }

            ((Buffer)this.headerBuffer).clear();
            this.headerWritten = true;
         }

         while (!queue.isEmpty()) {
            PacketChunk packetchunk = (PacketChunk)queue.peek();
            ByteBuffer bytebuffer = packetchunk.buffer;
            if (this.chunkHeaderWritten) {
               this.socketChannel.write(bytebuffer);
            } else {
               if (!this.chunkPrepared) {
                  int i = 0;

                  for (int j = bytebuffer.limit(); i < j; i++) {
                     bytebuffer.put(i, (byte)(bytebuffer.get(i) ^ this.xorKey));
                  }

                  ScramblerRange scramblerrange = pickRandomScrambler();
                  scramblerrange.scrambler.scramble(bytebuffer);
                  ((Buffer)this.chunkHeaderBuffer).clear();
                  this.chunkHeaderBuffer.put(randomScramblerId(scramblerrange));
                  this.chunkHeaderBuffer.putShort((short)(bytebuffer.position() ^ this.xorKey));
                  ((Buffer)this.chunkHeaderBuffer).flip();
                  ((Buffer)bytebuffer).flip();
                  this.chunkBuffers[1] = bytebuffer;
                  this.chunkPrepared = true;
               }

               this.socketChannel.write(this.chunkBuffers);
               if (this.chunkHeaderBuffer.hasRemaining()) {
                  return;
               }

               this.chunkHeaderWritten = true;
            }

            if (bytebuffer.hasRemaining()) {
               return;
            }

            this.chunkHeaderWritten = false;
            this.chunkPrepared = false;
            queue.remove();
         }

         this.headerWritten = false;
         this.headerPrepared = false;
         this.pendingPackets.remove();
      }

      this.writePending = false;
      this.socketChannel.keyFor(this.selector).interestOps(1);
   }

   private static ScramblerRange pickRandomScrambler() {
      List list = Scramblers.INSTANCE.getRanges();
      return (ScramblerRange)list.get(ThreadLocalRandom.current().nextInt(list.size()));
   }

   private static byte randomScramblerId(ScramblerRange scramblerRange) {
      return (byte)ThreadLocalRandom.current().nextInt(scramblerRange.minId, scramblerRange.maxId);
   }

   public SocketChannel getSocketChannel() {
      return this.socketChannel;
   }

   public Selector getSelector() {
      return this.selector;
   }

   public void enqueue(OutgoingPacket outgoingPacket) {
      this.pendingPackets.add(outgoingPacket);
   }

   public void requestWrite() {
      if (!this.writePending) {
         this.writePending = true;
         this.socketChannel.keyFor(this.selector).interestOps(4);
         this.selector.wakeup();
      }
   }
}
