package astolfo.lua.world;

import astolfo.ClientAccess;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.BlockPos;
import net.minecraft.world.chunk.Chunk;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public final class Biome extends ZeroArgFunction {
   @Override
   public LuaValue call() {
      try {
         BlockPos blockpos = new BlockPos(
            ClientAccess.mc.getRenderViewEntity().posX,
            ClientAccess.mc.getRenderViewEntity().getEntityBoundingBox().minY,
            ClientAccess.mc.getRenderViewEntity().posZ
         );
         WorldClient worldclient = ClientAccess.mc.theWorld;
         if (worldclient != null) {
            WorldClient worldclient1 = ClientAccess.mc.theWorld;
            if (worldclient1.isBlockLoaded(blockpos)) {
               Chunk chunk = ClientAccess.mc.theWorld.getChunkFromBlockCoords(blockpos);
               return valueOf(chunk.getBiome(blockpos, ClientAccess.mc.theWorld.getWorldChunkManager()).biomeName);
            }
         }

         return NIL;
      } catch (Exception exception) {
         return NIL;
      }
   }
}
