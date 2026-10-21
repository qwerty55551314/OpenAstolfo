package astolfo.auth.net;

public interface ChannelGroup {
   void addChannel(Channel var1);

   void removeChannel(Channel var1);

   void clearChannels();

   Channel get(String var1);
}
