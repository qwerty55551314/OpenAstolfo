package net.minecraft.command;

import java.util.List;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class CommandParticle extends CommandBase {
   @Override
   public String getCommandName() {
      return "particle";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender sender) {
      return "commands.particle.usage";
   }

   @Override
   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
      int k = args.length;
      if (k < 8) {
         throw new WrongUsageException("commands.particle.usage");
      } else {
         boolean flag = false;
         EnumParticleTypes enumparticletypes = null;

         for (EnumParticleTypes enumparticletypes1 : EnumParticleTypes.values()) {
            boolean flag2 = enumparticletypes1.hasArguments();
            if (flag2) {
               if (args[0].startsWith(enumparticletypes1.getParticleName())) {
                  flag = true;
                  enumparticletypes = enumparticletypes1;
                  break;
               }
            } else {
               boolean flag3 = args[0].equals(enumparticletypes1.getParticleName());
               if (flag3) {
                  flag = true;
                  enumparticletypes = enumparticletypes1;
                  break;
               }
            }
         }

         if (!flag) {
            throw new CommandException("commands.particle.notFound", args[0]);
         } else {
            String s = args[0];
            Vec3 vec3 = sender.getPositionVector();
            double d6 = (double)((float)parseDouble(vec3.xCoord, args[1], true));
            double d0 = (double)((float)parseDouble(vec3.yCoord, args[2], true));
            double d1 = (double)((float)parseDouble(vec3.zCoord, args[3], true));
            double d2 = (double)((float)parseDouble(args[4]));
            double d3 = (double)((float)parseDouble(args[5]));
            double d4 = (double)((float)parseDouble(args[6]));
            double d5 = (double)((float)parseDouble(args[7]));
            int i = 0;
            int l = args.length;
            if (l > 8) {
               i = parseInt(args[8], 0);
            }

            boolean flag1 = false;
            int i1 = args.length;
            if (i1 > 9) {
               boolean flag4 = "force".equals(args[9]);
               if (flag4) {
                  flag1 = true;
               }
            }

            World world = sender.getEntityWorld();
            if (world instanceof WorldServer) {
               WorldServer worldserver = (WorldServer)world;
               int[] aint = new int[enumparticletypes.getArgumentCount()];
               if (enumparticletypes.hasArguments()) {
                  String[] astring = args[0].split("_", 3);

                  for (int j = 1; j < astring.length; j++) {
                     try {
                        aint[j - 1] = Integer.parseInt(astring[j]);
                     } catch (NumberFormatException numberformatexception) {
                        throw new CommandException("commands.particle.notFound", args[0]);
                     }
                  }
               }

               worldserver.spawnParticle(enumparticletypes, flag1, d6, d0, d1, i, d2, d3, d4, d5, aint);
               notifyOperators(sender, this, "commands.particle.success", new Object[]{s, Math.max(i, 1)});
            }
         }
      }
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
      int i = args.length;
      List list;
      if (i == 1) {
         list = getListOfStringsMatchingLastWord(args, EnumParticleTypes.getParticleNames());
      } else {
         int j = args.length;
         list = j > 1 && args.length <= 4
            ? func_175771_a(args, 1, pos)
            : (args.length == 10 ? getListOfStringsMatchingLastWord(args, new String[]{"normal", "force"}) : null);
      }

      return list;
   }
}
