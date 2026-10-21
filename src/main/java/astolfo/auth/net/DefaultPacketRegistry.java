package astolfo.auth.net;

import astolfo.util.collection.ShapedMap;
import java.util.Collection;
import java.util.Map;

public class DefaultPacketRegistry implements PacketRegistry {
   private final Map<Short, PacketReader<?>> readers = new ShapedMap<>();
   private final Map<String, Short> packetIds = new ShapedMap<>();
   private final Map<Short, Map<String, PacketListener<?>>> listeners = new ShapedMap<>();

   @Override
   public void registerPacketId(short s, String string) {
      this.packetIds.putIfAbsent(string, s);
   }

   @Override
   public Short getPacketId(String string) {
      return this.packetIds.get(string);
   }

   @Override
   public void registerReader(String string, PacketReader<?> packetReader) {
      Short oshort = this.packetIds.get(string);
      if (oshort == null) {
         throw new UnknownPacketException(string);
      } else {
         this.readers.put(oshort, packetReader);
      }
   }

   @Override
   public PacketReader<?> getReader(String string) {
      Short oshort = this.packetIds.get(string);
      if (oshort == null) {
         throw new UnknownPacketException(string);
      } else {
         return this.readers.get(oshort);
      }
   }

   @Override
   public PacketReader<?> getReaderById(short s) {
      return this.readers.get(s);
   }

   @Override
   public <T> void addPacketListener(String string, String string2, PacketListener<T> packetListener) {
      Short oshort = this.packetIds.get(string);
      if (oshort == null) {
         throw new UnknownPacketException(string);
      } else {
         this.listeners.computeIfAbsent(oshort, shortValue -> new ShapedMap<>()).putIfAbsent(string2, packetListener);
      }
   }

   @Override
   public void removePacketListener(String string, String string2) {
      Short oshort = this.packetIds.get(string);
      if (oshort == null) {
         throw new UnknownPacketException(string);
      } else {
         Map map = this.listeners.get(oshort);
         if (map != null) {
            map.remove(string2);
         }
      }
   }

   @Override
   public void removePacketListeners(String string) {
      Short oshort = this.packetIds.get(string);
      if (oshort == null) {
         throw new UnknownPacketException(string);
      } else {
         this.listeners.remove(oshort);
      }
   }

   @Override
   public boolean hasPacketListener(String string, String string2) {
      Short oshort = this.packetIds.get(string);
      if (oshort == null) {
         throw new UnknownPacketException(string);
      } else {
         Map map = this.listeners.get(oshort);
         return map == null ? false : map.containsKey(string2);
      }
   }

   @Override
   public boolean hasPacketListeners(String string) {
      Short oshort = this.packetIds.get(string);
      if (oshort == null) {
         throw new UnknownPacketException(string);
      } else {
         return this.listeners.containsKey(oshort);
      }
   }

   @Override
   public Collection<PacketListener<?>> getPacketListeners(String string) {
      Short oshort = this.packetIds.get(string);
      if (oshort == null) {
         throw new UnknownPacketException(string);
      } else {
         Map map = this.listeners.get(oshort);
         return map == null ? null : map.values();
      }
   }

   @Override
   public Collection<PacketListener<?>> getPacketListenersById(short s) {
      Map map = this.listeners.get(s);
      return map == null ? null : map.values();
   }
}
