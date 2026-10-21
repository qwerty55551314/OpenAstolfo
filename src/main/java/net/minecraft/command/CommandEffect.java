package net.minecraft.command;

import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;

public class CommandEffect extends CommandBase {
   @Override
   public String getCommandName() {
      return "effect";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender sender) {
      return "commands.effect.usage";
   }

   @Override
   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
      if (args.length < 2) {
         throw new WrongUsageException("commands.effect.usage");
      } else {
         EntityLivingBase entitylivingbase = getEntity(sender, args[0], EntityLivingBase.class);
         boolean flag1 = args[1].equals("clear");
         if (flag1) {
            if (entitylivingbase.getActivePotionEffects().isEmpty()) {
               throw new CommandException("commands.effect.failure.notActive.all", entitylivingbase.getName());
            } else {
               entitylivingbase.clearActivePotions();
               notifyOperators(sender, this, "commands.effect.success.removed.all", new Object[]{entitylivingbase.getName()});
            }
         } else {
            int i;
            try {
               i = parseInt(args[1], 1);
            } catch (NumberInvalidException numberinvalidexception) {
               Potion potion = Potion.getPotionFromResourceLocation(args[1]);
               if (potion == null) {
                  throw numberinvalidexception;
               }

               i = potion.id;
            }

            int j = 600;
            int k1 = 30;
            int k = 0;
            if (i >= 0 && i < Potion.potionTypes.length) {
               Potion potion2 = Potion.potionTypes[i];
               if (potion2 != null) {
                  Potion potion1 = Potion.potionTypes[i];
                  int l = args.length;
                  if (l >= 3) {
                     k1 = parseInt(args[2], 0, 1000000);
                     if (potion1.isInstant()) {
                        j = k1;
                     } else {
                        j = k1 * 20;
                     }
                  } else {
                     boolean flag2 = potion1.isInstant();
                     if (flag2) {
                        j = 1;
                     }
                  }

                  int i1 = args.length;
                  if (i1 >= 4) {
                     k = parseInt(args[3], 0, 255);
                  }

                  boolean flag = true;
                  int j1 = args.length;
                  if (j1 >= 5 && "true".equalsIgnoreCase(args[4])) {
                     flag = false;
                  }

                  if (k1 > 0) {
                     PotionEffect potioneffect = new PotionEffect(i, j, k, false, flag);
                     entitylivingbase.addPotionEffect(potioneffect);
                     notifyOperators(
                        sender,
                        this,
                        "commands.effect.success",
                        new Object[]{new ChatComponentTranslation(potioneffect.getEffectName()), i, k, entitylivingbase.getName(), k1}
                     );
                  } else {
                     if (!entitylivingbase.isPotionActive(i)) {
                        throw new CommandException(
                           "commands.effect.failure.notActive", new ChatComponentTranslation(potion1.getName()), entitylivingbase.getName()
                        );
                     }

                     entitylivingbase.removePotionEffect(i);
                     notifyOperators(
                        sender,
                        this,
                        "commands.effect.success.removed",
                        new Object[]{new ChatComponentTranslation(potion1.getName()), entitylivingbase.getName()}
                     );
                  }

                  return;
               }
            }

            throw new NumberInvalidException("commands.effect.notFound", i);
         }
      }
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
      int i = args.length;
      List list;
      if (i == 1) {
         list = getListOfStringsMatchingLastWord(args, this.getAllUsernames());
      } else {
         int j = args.length;
         list = j == 2
            ? getListOfStringsMatchingLastWord(args, Potion.getPotionLocations())
            : (args.length == 5 ? getListOfStringsMatchingLastWord(args, new String[]{"true", "false"}) : null);
      }

      return list;
   }

   public String[] getAllUsernames() {
      return MinecraftServer.getServer().getAllUsernames();
   }

   @Override
   public boolean isUsernameIndex(String[] args, int index) {
      return index == 0;
   }
}
