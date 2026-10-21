package astolfo.auth.net;

public interface PacketWriter<T> {
   default void write(T object, PacketBuffer packetBuffer) {
   }
}
