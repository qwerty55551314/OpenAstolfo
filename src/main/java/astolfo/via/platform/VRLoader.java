package astolfo.via.platform;

import astolfo.via.providers.NativeVersionProvider;
import astolfo.via.providers.VRHandItemProvider;
import astolfo.via.providers.VRNativeVersionProvider;
import astolfo.via.providers.VRVersionProvider;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.platform.ViaPlatformLoader;
import com.viaversion.viaversion.api.protocol.version.VersionProvider;
import com.viaversion.viaversion.bungee.providers.BungeeMovementTransmitter;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.HandItemProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;

public class VRLoader implements ViaPlatformLoader {
   @Override
   public void load() {
      Via.getManager().getProviders().use(NativeVersionProvider.class, new VRNativeVersionProvider());
      Via.getManager().getProviders().use(MovementTransmitterProvider.class, new BungeeMovementTransmitter());
      boolean flag = false;
      Via.getManager().getProviders().use(VersionProvider.class, new VRVersionProvider());
      if (Via.getPlatform().getConf().isItemCache()) {
         VRHandItemProvider vrhanditemprovider = new VRHandItemProvider();
         Via.getManager().getProviders().use(HandItemProvider.class, vrhanditemprovider);
      }
   }

   @Override
   public void unload() {
   }
}
