package astolfo.auth.net;

import java.util.Collection;

public interface PacketRegistry {
   void registerPacketId(short var1, String var2);

   Short getPacketId(String var1);

   void registerReader(String var1, PacketReader<?> var2);

   PacketReader<?> getReader(String var1);

   PacketReader<?> getReaderById(short var1);

   <T> void addPacketListener(String var1, String var2, PacketListener<T> var3);

   void removePacketListener(String var1, String var2);

   void removePacketListeners(String var1);

   boolean hasPacketListener(String var1, String var2);

   boolean hasPacketListeners(String var1);

   Collection<PacketListener<?>> getPacketListeners(String var1);

   Collection<PacketListener<?>> getPacketListenersById(short var1);
}
