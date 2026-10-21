package net.minecraft.src;

import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

public class WorldServerOF extends WorldServer {
   private MinecraftServer mcServer;

   public WorldServerOF(MinecraftServer par1MinecraftServer, ISaveHandler par2iSaveHandler, WorldInfo worldInfo, int par4, Profiler par6Profiler) {
      super(par1MinecraftServer, par2iSaveHandler, worldInfo, par4, par6Profiler);
      this.mcServer = par1MinecraftServer;
   }

   @Override
   public void tick() {
      super.tick();
      boolean flag = Config.isTimeDefault();
      if (!flag) {
         this.fixWorldTime();
      }

      boolean flag1 = Config.waterOpacityChanged;
      if (flag1) {
         Config.waterOpacityChanged = false;
         ClearWater.updateWaterOpacity(Config.getGameSettings(), this);
      }
   }

   @Override
   public void updateWeather() {
      if (!Config.isWeatherEnabled()) {
         this.fixWorldWeather();
      }

      super.updateWeather();
   }

   private void fixWorldWeather() {
      if (super.worldInfo.isRaining() || super.worldInfo.isThundering()) {
         super.worldInfo.setRainTime(0);
         super.worldInfo.setRaining(false);
         this.setRainStrength(0.0F);
         super.worldInfo.setThunderTime(0);
         super.worldInfo.setThundering(false);
         this.setThunderStrength(0.0F);
         this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(2, 0.0F));
         this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(7, 0.0F));
         this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(8, 0.0F));
      }
   }

   private void fixWorldTime() {
      if (super.worldInfo.getGameType().getID() == 1) {
         long i = this.getWorldTime();
         long j = i % 24000L;
         boolean flag = Config.isTimeDayOnly();
         if (flag) {
            if (j <= 1000L) {
               this.setWorldTime(i - j + 1001L);
            }

            if (j >= 11000L) {
               this.setWorldTime(i - j + 24001L);
            }
         }

         boolean flag1 = Config.isTimeNightOnly();
         if (flag1) {
            long l;
            int k = (l = j - 14000L) == 0L ? 0 : (l < 0L ? -1 : 1);
            if (k <= 0) {
               this.setWorldTime(i - j + 14001L);
            }

            if (j >= 22000L) {
               this.setWorldTime(i - j + 24000L + 14001L);
            }
         }
      }
   }
}
