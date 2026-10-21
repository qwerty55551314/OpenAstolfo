package net.minecraft.network.status.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.status.INetHandlerStatusClient;

public class S01PacketPong implements Packet<INetHandlerStatusClient> {
   private long clientTime;
   private static Entity[] l;

   public S01PacketPong() {
   }

   public S01PacketPong(long time) {
      this.clientTime = time;
   }

   @Override
   public void readPacketData(PacketBuffer buf) throws IOException {
      this.clientTime = buf.readLong();
   }

   @Override
   public void writePacketData(PacketBuffer buf) throws IOException {
      buf.writeLong(this.clientTime);
   }

   public void processPacket(INetHandlerStatusClient handler) {
      handler.handlePong(this);
   }

   public static void G(Entity[] entityArray) {
      l = entityArray;
   }

   public static Entity[] K() {
      return l;
   }

   static {
      G(new Entity[1]);
   }
}
