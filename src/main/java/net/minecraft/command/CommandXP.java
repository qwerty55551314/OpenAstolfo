package net.minecraft.command;

import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandXP extends CommandBase {
   @Override
   public String getCommandName() {
      return "xp";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender sender) {
      return "commands.xp.usage";
   }

   @Override
   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
      if (args.length <= 0) {
         throw new WrongUsageException("commands.xp.usage");
      } else {
         String s = args[0];
         boolean flag2 = s.endsWith("l");
         boolean flag = flag2 || s.endsWith("L");
         if (flag && s.length() > 1) {
            s = s.substring(0, s.length() - 1);
         }

         int i = parseInt(s);
         boolean flag1 = i < 0;
         if (flag1) {
            i *= -1;
         }

         EntityPlayerMP entityplayermp = args.length > 1 ? getPlayer(sender, args[1]) : getCommandSenderAsPlayer(sender);
         if (flag) {
            sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, entityplayermp.experienceLevel);
            if (flag1) {
               entityplayermp.addExperienceLevel(-i);
               notifyOperators(sender, this, "commands.xp.success.negative.levels", new Object[]{i, entityplayermp.getName()});
            } else {
               entityplayermp.addExperienceLevel(i);
               notifyOperators(sender, this, "commands.xp.success.levels", new Object[]{i, entityplayermp.getName()});
            }
         } else {
            sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, entityplayermp.experienceTotal);
            if (flag1) {
               throw new CommandException("commands.xp.failure.widthdrawXp");
            }

            entityplayermp.addExperience(i);
            notifyOperators(sender, this, "commands.xp.success", new Object[]{i, entityplayermp.getName()});
         }
      }
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
      return args.length == 2 ? getListOfStringsMatchingLastWord(args, this.getAllUsernames()) : null;
   }

   public String[] getAllUsernames() {
      return MinecraftServer.getServer().getAllUsernames();
   }

   @Override
   public boolean isUsernameIndex(String[] args, int index) {
      return index == 1;
   }
}
