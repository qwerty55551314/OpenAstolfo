package astolfo.account;

import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;

public interface Authenticator {
   String getUsername();

   void login(Minecraft var1, Consumer<Throwable> var2);

   boolean canLogin();

   boolean canEdit();

   void markUsed();

   int getUseCount();

   long getLastUsed();

   default UUID getUuid() {
      return null;
   }
}
