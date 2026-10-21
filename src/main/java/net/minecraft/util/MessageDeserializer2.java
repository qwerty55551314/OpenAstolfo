package net.minecraft.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import java.util.List;
import net.minecraft.network.PacketBuffer;

public class MessageDeserializer2 extends ByteToMessageDecoder {
   private static final String b = "length wider than 21-bit";

   @Override
   public void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
      byteBuf.markReaderIndex();
      byte[] abyte = new byte[3];

      for (int i = 0; i < abyte.length; i++) {
         boolean flag = byteBuf.isReadable();
         if (!flag) {
            byteBuf.resetReaderIndex();
            return;
         }

         abyte[i] = byteBuf.readByte();
         if (abyte[i] >= 0) {
            PacketBuffer packetbuffer = new PacketBuffer(Unpooled.wrappedBuffer(abyte));

            try {
               int j = packetbuffer.readVarIntFromBuffer();
               int k = byteBuf.readableBytes();
               if (k < j) {
                  byteBuf.resetReaderIndex();
                  return;
               }

               list.add(byteBuf.readBytes(j));
            } finally {
               packetbuffer.release();
            }

            return;
         }
      }

      throw new CorruptedFrameException(b);
   }
}
