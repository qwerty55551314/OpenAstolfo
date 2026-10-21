package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.util.zip.Deflater;

public class NettyCompressionEncoder extends MessageToByteEncoder<ByteBuf> {
   private final byte[] buffer;
   private final Deflater deflater;
   private int treshold;
   private static final long c = 1155236431849857024L;

   public NettyCompressionEncoder(int treshold) {
      this.buffer = new byte[(int)c];
      this.treshold = treshold;
      this.deflater = new Deflater();
   }

   public void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
      boolean flag = true;
      int i = byteBuf.readableBytes();
      PacketBuffer packetbuffer = new PacketBuffer(byteBuf2);
      if (i < this.treshold) {
         packetbuffer.writeVarIntToBuffer(0);
         packetbuffer.writeBytes(byteBuf);
      } else {
         byte[] abyte = new byte[i];
         byteBuf.readBytes(abyte);
         packetbuffer.writeVarIntToBuffer(abyte.length);
         this.deflater.setInput(abyte, 0, i);
         this.deflater.finish();

         while (!this.deflater.finished()) {
            int j = this.deflater.deflate(this.buffer);
            packetbuffer.writeBytes(this.buffer, 0, j);
         }

         this.deflater.reset();
      }
   }

   public void setCompressionTreshold(int treshold) {
      this.treshold = treshold;
   }
}
