package net.minecraft.command;

import java.util.List;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.EnumDifficulty;

public class CommandDifficulty extends CommandBase {
   @Override
   public String getCommandName() {
      return "difficulty";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender sender) {
      return "commands.difficulty.usage";
   }

   @Override
   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
      if (args.length <= 0) {
         throw new WrongUsageException("commands.difficulty.usage");
      } else {
         EnumDifficulty enumdifficulty = this.getDifficultyFromCommand(args[0]);
         MinecraftServer.getServer().setDifficultyForAllWorlds(enumdifficulty);
         notifyOperators(sender, this, "commands.difficulty.success", new Object[]{new ChatComponentTranslation(enumdifficulty.getDifficultyResourceKey())});
      }
   }

   public EnumDifficulty getDifficultyFromCommand(String string) throws CommandException, NumberInvalidException {
      boolean flag = string.equalsIgnoreCase("peaceful");
      if (!flag) {
         boolean flag1 = string.equalsIgnoreCase("p");
         if (!flag1) {
            boolean flag2 = string.equalsIgnoreCase("easy");
            if (!flag2) {
               boolean flag3 = string.equalsIgnoreCase("e");
               if (!flag3) {
                  boolean flag4 = string.equalsIgnoreCase("normal");
                  if (!flag4) {
                     boolean flag5 = string.equalsIgnoreCase("n");
                     if (!flag5) {
                        boolean flag6 = string.equalsIgnoreCase("hard");
                        if (!flag6) {
                           boolean flag7 = string.equalsIgnoreCase("h");
                           if (!flag7) {
                              return EnumDifficulty.getDifficultyEnum(parseInt(string, 0, 3));
                           }
                        }

                        return EnumDifficulty.HARD;
                     }
                  }

                  return EnumDifficulty.NORMAL;
               }
            }

            return EnumDifficulty.EASY;
         }
      }

      return EnumDifficulty.PEACEFUL;
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
      return args.length == 1 ? getListOfStringsMatchingLastWord(args, new String[]{"peaceful", "easy", "normal", "hard"}) : null;
   }
}
