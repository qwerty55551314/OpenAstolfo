package net.minecraft.command;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class CommandReplaceItem extends CommandBase {
   private static final Map<String, Integer> SHORTCUTS = Maps.newHashMap();

   @Override
   public String getCommandName() {
      return "replaceitem";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender sender) {
      return "commands.replaceitem.usage";
   }

   @Override
   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
      int i1 = args.length;
      if (i1 < 1) {
         throw new WrongUsageException("commands.replaceitem.usage");
      } else {
         boolean flag1 = args[0].equals("entity");
         boolean flag;
         if (flag1) {
            flag = false;
         } else {
            boolean flag2 = args[0].equals("block");
            if (!flag2) {
               throw new WrongUsageException("commands.replaceitem.usage");
            }

            flag = true;
         }

         int i;
         if (flag) {
            if (args.length < 6) {
               throw new WrongUsageException("commands.replaceitem.block.usage");
            }

            i = 4;
         } else {
            int j1 = args.length;
            if (j1 < 4) {
               throw new WrongUsageException("commands.replaceitem.entity.usage");
            }

            i = 2;
         }

         int j = this.getSlotForShortcut(args[i++]);

         Item item;
         try {
            item = getItemByText(sender, args[i]);
         } catch (NumberInvalidException numberinvalidexception) {
            if (Block.getBlockFromName(args[i]) != Blocks.air) {
               throw numberinvalidexception;
            }

            item = null;
         }

         i++;
         int k1 = args.length;
         int k = k1 > i ? parseInt(args[i++], 1, 64) : 1;
         int l1 = args.length;
         int l = l1 > i ? parseInt(args[i++]) : 0;
         ItemStack itemstack = new ItemStack(item, k, l);
         if (args.length > i) {
            String s = getChatComponentFromNthArg(sender, args, i).getUnformattedText();

            try {
               itemstack.setTagCompound(JsonToNBT.getTagFromJson(s));
            } catch (NBTException nbtexception) {
               throw new CommandException("commands.replaceitem.tagError", nbtexception.getMessage());
            }
         }

         if (itemstack.getItem() == null) {
            itemstack = null;
         }

         if (flag) {
            sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, 0);
            BlockPos blockpos = parseBlockPos(sender, args, 1, false);
            World world = sender.getEntityWorld();
            TileEntity tileentity = world.getTileEntity(blockpos);
            if (tileentity == null || !(tileentity instanceof IInventory)) {
               throw new CommandException("commands.replaceitem.noContainer", blockpos.getX(), blockpos.getY(), blockpos.getZ());
            }

            IInventory iinventory = (IInventory)tileentity;
            if (j >= 0 && j < iinventory.getSizeInventory()) {
               iinventory.setInventorySlotContents(j, itemstack);
            }
         } else {
            Entity entity = getEntity(sender, args[1]);
            sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, 0);
            boolean flag3 = entity instanceof EntityPlayer;
            if (flag3) {
               ((EntityPlayer)entity).inventoryContainer.detectAndSendChanges();
            }

            boolean flag4 = entity.replaceItemInInventory(j, itemstack);
            if (!flag4) {
               throw new CommandException("commands.replaceitem.failed", j, k, itemstack == null ? "Air" : itemstack.getChatComponent());
            }

            if (entity instanceof EntityPlayer) {
               ((EntityPlayer)entity).inventoryContainer.detectAndSendChanges();
            }
         }

         sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, k);
         String s1 = "commands.replaceitem.success";
         Object[] aobject1;
         Object[] aobject = aobject1 = new Object[]{j, k, null};
         aobject1[2] = itemstack == null ? "Air" : itemstack.getChatComponent();
         notifyOperators(sender, this, "commands.replaceitem.success", aobject);
      }
   }

   private int getSlotForShortcut(String shortcut) throws CommandException {
      if (!SHORTCUTS.containsKey(shortcut)) {
         throw new CommandException("commands.generic.parameter.invalid", shortcut);
      } else {
         return SHORTCUTS.get(shortcut);
      }
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
      int i = args.length;
      List list;
      if (i == 1) {
         list = getListOfStringsMatchingLastWord(args, new String[]{"entity", "block"});
      } else {
         int j = args.length;
         if (j == 2 && args[0].equals("entity")) {
            list = getListOfStringsMatchingLastWord(args, this.getUsernames());
         } else {
            int k = args.length;
            if (k >= 2 && args.length <= 4 && args[0].equals("block")) {
               list = func_175771_a(args, 1, pos);
            } else {
               int l = args.length;
               if (l != 3 || !args[0].equals("entity")) {
                  int i1 = args.length;
                  if (i1 != 5 || !args[0].equals("block")) {
                     int j1 = args.length;
                     if (j1 == 4 && args[0].equals("entity") || args.length == 6 && args[0].equals("block")) {
                        return getListOfStringsMatchingLastWord(args, Item.itemRegistry.getKeys());
                     }

                     return null;
                  }
               }

               list = getListOfStringsMatchingLastWord(args, SHORTCUTS.keySet());
            }
         }
      }

      return list;
   }

   public String[] getUsernames() {
      return MinecraftServer.getServer().getAllUsernames();
   }

   @Override
   public boolean isUsernameIndex(String[] args, int index) {
      int i = args.length;
      if (i > 0) {
         boolean flag = args[0].equals("entity");
         if (flag && index == 1) {
            return true;
         }
      }

      return false;
   }

   static {
      for (int i = 0; i < 54; i++) {
         SHORTCUTS.put("slot.container." + i, i);
      }

      for (int j = 0; j < 9; j++) {
         SHORTCUTS.put("slot.hotbar." + j, j);
      }

      for (int k = 0; k < 27; k++) {
         SHORTCUTS.put("slot.inventory." + k, 9 + k);
      }

      for (int l = 0; l < 27; l++) {
         SHORTCUTS.put("slot.enderchest." + l, 200 + l);
      }

      for (int i1 = 0; i1 < 8; i1++) {
         SHORTCUTS.put("slot.villager." + i1, 300 + i1);
      }

      for (int j1 = 0; j1 < 15; j1++) {
         SHORTCUTS.put("slot.horse." + j1, 500 + j1);
      }

      SHORTCUTS.put("slot.weapon", 99);
      SHORTCUTS.put("slot.armor.head", 103);
      SHORTCUTS.put("slot.armor.chest", 102);
      SHORTCUTS.put("slot.armor.legs", 101);
      SHORTCUTS.put("slot.armor.feet", 100);
      SHORTCUTS.put("slot.horse.saddle", 400);
      SHORTCUTS.put("slot.horse.armor", 401);
      SHORTCUTS.put("slot.horse.chest", 499);
   }
}
