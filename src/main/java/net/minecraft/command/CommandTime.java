package net.minecraft.command;

import java.util.List;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.WorldServer;

public class CommandTime extends CommandBase {
   @Override
   public String getCommandName() {
      return "time";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender sender) {
      return "commands.time.usage";
   }

   @Override
   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
      int j = args.length;
      if (j > 1) {
         boolean flag = args[0].equals("set");
         if (flag) {
            int i1;
            if (args[1].equals("day")) {
               i1 = 1000;
            } else if (args[1].equals("night")) {
               i1 = 13000;
            } else {
               i1 = parseInt(args[1], 0);
            }

            this.setTime(sender, i1);
            notifyOperators(sender, this, "commands.time.set", new Object[]{i1});
            return;
         }

         boolean flag1 = args[0].equals("add");
         if (flag1) {
            int l = parseInt(args[1], 0);
            this.addTime(sender, l);
            notifyOperators(sender, this, "commands.time.added", new Object[]{l});
            return;
         }

         boolean flag2 = args[0].equals("query");
         if (flag2) {
            boolean flag3 = args[1].equals("daytime");
            if (flag3) {
               int k = (int)(sender.getEntityWorld().getWorldTime() % 2147483647L);
               sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, k);
               notifyOperators(sender, this, "commands.time.query", new Object[]{k});
               return;
            }

            boolean flag4 = args[1].equals("gametime");
            if (flag4) {
               int i = (int)(sender.getEntityWorld().getTotalWorldTime() % 2147483647L);
               sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, i);
               notifyOperators(sender, this, "commands.time.query", new Object[]{i});
               return;
            }
         }
      }

      throw new WrongUsageException("commands.time.usage");
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
      int i = args.length;
      List list;
      if (i == 1) {
         list = getListOfStringsMatchingLastWord(args, new String[]{"set", "add", "query"});
      } else {
         int j = args.length;
         list = j == 2 && args[0].equals("set")
            ? getListOfStringsMatchingLastWord(args, new String[]{"day", "night"})
            : (args.length == 2 && args[0].equals("query") ? getListOfStringsMatchingLastWord(args, new String[]{"daytime", "gametime"}) : null);
      }

      return list;
   }

   public void setTime(ICommandSender sender, int time) {
      for (int i = 0; i < MinecraftServer.getServer().worldServers.length; i++) {
         MinecraftServer.getServer().worldServers[i].setWorldTime((long)time);
      }
   }

   public void addTime(ICommandSender sender, int time) {
      for (int i = 0; i < MinecraftServer.getServer().worldServers.length; i++) {
         WorldServer worldserver = MinecraftServer.getServer().worldServers[i];
         worldserver.setWorldTime(worldserver.getWorldTime() + (long)time);
      }
   }
}
