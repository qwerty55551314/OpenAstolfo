package net.minecraft.command;

import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.WorldSettings;

public class CommandGameMode extends CommandBase {
   @Override
   public String getCommandName() {
      return "gamemode";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender sender) {
      return "commands.gamemode.usage";
   }

   @Override
   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
      if (args.length <= 0) {
         throw new WrongUsageException("commands.gamemode.usage");
      } else {
         WorldSettings.GameType worldsettings$gametype = this.getGameModeFromCommand(sender, args[0]);
         EntityPlayerMP entityplayermp = args.length >= 2 ? getPlayer(sender, args[1]) : getCommandSenderAsPlayer(sender);
         entityplayermp.setGameType(worldsettings$gametype);
         entityplayermp.fallDistance = 0.0F;
         if (sender.getEntityWorld().getGameRules().getBoolean("sendCommandFeedback")) {
            entityplayermp.addChatMessage(new ChatComponentTranslation("gameMode.changed"));
         }

         ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("gameMode." + worldsettings$gametype.getName());
         if (entityplayermp != sender) {
            notifyOperators(sender, this, 1, "commands.gamemode.success.other", new Object[]{entityplayermp.getName(), chatcomponenttranslation});
         } else {
            notifyOperators(sender, this, 1, "commands.gamemode.success.self", new Object[]{chatcomponenttranslation});
         }
      }
   }

   public WorldSettings.GameType getGameModeFromCommand(ICommandSender iCommandSender, String string) throws CommandException, NumberInvalidException {
      boolean flag = string.equalsIgnoreCase(WorldSettings.GameType.SURVIVAL.getName());
      if (!flag) {
         boolean flag1 = string.equalsIgnoreCase("s");
         if (!flag1) {
            boolean flag2 = string.equalsIgnoreCase(WorldSettings.GameType.CREATIVE.getName());
            if (!flag2) {
               boolean flag3 = string.equalsIgnoreCase("c");
               if (!flag3) {
                  boolean flag4 = string.equalsIgnoreCase(WorldSettings.GameType.ADVENTURE.getName());
                  if (!flag4) {
                     boolean flag5 = string.equalsIgnoreCase("a");
                     if (!flag5) {
                        boolean flag6 = string.equalsIgnoreCase(WorldSettings.GameType.SPECTATOR.getName());
                        if (!flag6) {
                           boolean flag7 = string.equalsIgnoreCase("sp");
                           if (!flag7) {
                              return WorldSettings.getGameTypeById(parseInt(string, 0, WorldSettings.GameType.values().length - 2));
                           }
                        }

                        return WorldSettings.GameType.SPECTATOR;
                     }
                  }

                  return WorldSettings.GameType.ADVENTURE;
               }
            }

            return WorldSettings.GameType.CREATIVE;
         }
      }

      return WorldSettings.GameType.SURVIVAL;
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
      return args.length == 1
         ? getListOfStringsMatchingLastWord(args, new String[]{"survival", "creative", "adventure", "spectator"})
         : (args.length == 2 ? getListOfStringsMatchingLastWord(args, this.getListOfPlayerUsernames()) : null);
   }

   public String[] getListOfPlayerUsernames() {
      return MinecraftServer.getServer().getAllUsernames();
   }

   @Override
   public boolean isUsernameIndex(String[] args, int index) {
      return index == 1;
   }
}
