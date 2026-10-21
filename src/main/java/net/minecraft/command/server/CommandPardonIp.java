package net.minecraft.command.server;

import java.util.List;
import java.util.regex.Matcher;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandPardonIp extends CommandBase {
   @Override
   public String getCommandName() {
      return "pardon-ip";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 3;
   }

   @Override
   public boolean canCommandSenderUseCommand(ICommandSender sender) {
      boolean flag = MinecraftServer.getServer().getConfigurationManager().getBannedIPs().isLanServer();
      if (flag) {
         boolean flag1 = super.canCommandSenderUseCommand(sender);
         if (flag1) {
            return true;
         }
      }

      return false;
   }

   @Override
   public String getCommandUsage(ICommandSender sender) {
      return "commands.unbanip.usage";
   }

   @Override
   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
      if (args.length == 1 && args[0].length() > 1) {
         Matcher matcher = CommandBanIp.field_147211_a.matcher(args[0]);
         if (matcher.matches()) {
            MinecraftServer.getServer().getConfigurationManager().getBannedIPs().removeEntry(args[0]);
            notifyOperators(sender, this, "commands.unbanip.success", new Object[]{args[0]});
         } else {
            throw new SyntaxErrorException("commands.unbanip.invalid");
         }
      } else {
         throw new WrongUsageException("commands.unbanip.usage");
      }
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
      return args.length == 1 ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getConfigurationManager().getBannedIPs().getKeys()) : null;
   }
}
