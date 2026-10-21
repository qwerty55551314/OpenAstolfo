package astolfo.auth.net;

import java.util.function.Supplier;

public interface PacketWriterRegistry {
   void registerPacketId(short var1, String var2);

   Short getPacketId(String var1);

   void registerWriter(String var1, PacketWriter<?> var2);

   PacketWriter<?> getWriter(String var1);

   PacketWriter<?> getWriterById(short var1);

   void registerSupplier(String var1, Supplier<?> var2);

   Supplier<?> getSupplier(String var1);

   Supplier<?> getSupplierById(short var1);
}
