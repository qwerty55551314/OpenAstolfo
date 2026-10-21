package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldServer;

public class CommandSaveAll extends CommandBase {
   @Override
   public String getCommandName() {
      return "save-all";
   }

   @Override
   public String getCommandUsage(ICommandSender sender) {
      return "commands.save.usage";
   }

   @Override
   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
      MinecraftServer minecraftserver = MinecraftServer.getServer();
      Object object = null;
      sender.addChatMessage(new ChatComponentTranslation("commands.save.start"));
      ServerConfigurationManager serverconfigurationmanager = minecraftserver.getConfigurationManager();
      if (serverconfigurationmanager != null) {
         minecraftserver.getConfigurationManager().saveAllPlayerData();
      }

      try {
         for (int i = 0; i < minecraftserver.worldServers.length; i++) {
            WorldServer worldserver1 = minecraftserver.worldServers[i];
            if (worldserver1 != null) {
               WorldServer worldserver = minecraftserver.worldServers[i];
               boolean flag = worldserver.disableLevelSaving;
               worldserver.disableLevelSaving = false;
               worldserver.saveAllChunks(true, (IProgressUpdate)null);
               worldserver.disableLevelSaving = flag;
            }
         }

         int j = args.length;
         if (j > 0) {
            boolean flag1 = "flush".equals(args[0]);
            if (flag1) {
               sender.addChatMessage(new ChatComponentTranslation("commands.save.flushStart"));

               for (int k = 0; k < minecraftserver.worldServers.length; k++) {
                  WorldServer worldserver2 = minecraftserver.worldServers[k];
                  if (worldserver2 != null) {
                     WorldServer worldserver3 = minecraftserver.worldServers[k];
                     boolean flag2 = worldserver3.disableLevelSaving;
                     worldserver3.disableLevelSaving = false;
                     worldserver3.saveChunkData();
                     worldserver3.disableLevelSaving = flag2;
                  }
               }

               sender.addChatMessage(new ChatComponentTranslation("commands.save.flushEnd"));
            }
         }
      } catch (MinecraftException minecraftexception) {
         notifyOperators(sender, this, "commands.save.failed", new Object[]{minecraftexception.getMessage()});
         return;
      }

      notifyOperators(sender, this, "commands.save.success", new Object[0]);
   }
}
