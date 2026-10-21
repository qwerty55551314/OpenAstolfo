package net.minecraft.server.management;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;

public class ItemInWorldManager {
   public World theWorld;
   public EntityPlayerMP thisPlayerMP;
   private WorldSettings.GameType gameType = WorldSettings.GameType.NOT_SET;
   private boolean isDestroyingBlock;
   private int initialDamage;
   private BlockPos field_180240_f = BlockPos.ORIGIN;
   private int curblockDamage;
   private boolean receivedFinishDiggingPacket;
   private BlockPos field_180241_i = BlockPos.ORIGIN;
   private int initialBlockDamage;
   private int durabilityRemainingOnBlock = -1;
   private static final long j = -6710022656306968623L;

   public ItemInWorldManager(World worldIn) {
      this.theWorld = worldIn;
   }

   public void setGameType(WorldSettings.GameType typeIn) {
      this.gameType = typeIn;
      typeIn.configurePlayerCapabilities(this.thisPlayerMP.capabilities);
      this.thisPlayerMP.sendPlayerAbilities();
      this.thisPlayerMP
         .mcServer
         .getConfigurationManager()
         .sendPacketToAllPlayers(new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.UPDATE_GAME_MODE, this.thisPlayerMP));
   }

   public WorldSettings.GameType getGameType() {
      return this.gameType;
   }

   public boolean survivalOrAdventure() {
      return this.gameType.isSurvivalOrAdventure();
   }

   public boolean isCreative() {
      return this.gameType.isCreative();
   }

   public void initializeGameType(WorldSettings.GameType typeIn) {
      if (this.gameType == WorldSettings.GameType.NOT_SET) {
         this.gameType = typeIn;
      }

      this.setGameType(this.gameType);
   }

   public void updateBlockRemoving() {
      int b0 = 49;
      this.curblockDamage++;
      if (this.receivedFinishDiggingPacket) {
         int i = this.curblockDamage - this.initialBlockDamage;
         Block block = this.theWorld.getBlockState(this.field_180241_i).getBlock();
         if (block.getMaterial() == Material.air) {
            this.receivedFinishDiggingPacket = false;
         } else {
            float f = block.getPlayerRelativeBlockHardness(this.thisPlayerMP, this.thisPlayerMP.worldObj, this.field_180241_i) * (float)(i + 1);
            int jx = (int)(f * 10.0F);
            if (jx != this.durabilityRemainingOnBlock) {
               this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), this.field_180241_i, jx);
               this.durabilityRemainingOnBlock = jx;
            }

            if (f >= 1.0F) {
               this.receivedFinishDiggingPacket = false;
               this.tryHarvestBlock(this.field_180241_i);
            }
         }
      } else if (this.isDestroyingBlock) {
         Block block1 = this.theWorld.getBlockState(this.field_180240_f).getBlock();
         if (block1.getMaterial() == Material.air) {
            this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), this.field_180240_f, -1);
            this.durabilityRemainingOnBlock = -1;
            this.isDestroyingBlock = false;
         } else {
            int k = this.curblockDamage - this.initialDamage;
            float f1 = block1.getPlayerRelativeBlockHardness(this.thisPlayerMP, this.thisPlayerMP.worldObj, this.field_180241_i) * (float)(k + 1);
            int l = (int)(f1 * 10.0F);
            if (l != this.durabilityRemainingOnBlock) {
               this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), this.field_180240_f, l);
               this.durabilityRemainingOnBlock = l;
            }
         }
      }
   }

   public void onBlockClicked(BlockPos pos, EnumFacing side) {
      if (this.isCreative()) {
         if (!this.theWorld.extinguishFire((EntityPlayer)null, pos, side)) {
            this.tryHarvestBlock(pos);
         }
      } else {
         Block block = this.theWorld.getBlockState(pos).getBlock();
         boolean flag = this.gameType.isAdventure();
         if (flag) {
            if (this.gameType == WorldSettings.GameType.SPECTATOR) {
               return;
            }

            if (!this.thisPlayerMP.isAllowEdit()) {
               ItemStack itemstack = this.thisPlayerMP.getCurrentEquippedItem();
               if (itemstack == null) {
                  return;
               }

               if (!itemstack.canDestroy(block)) {
                  return;
               }
            }
         }

         this.theWorld.extinguishFire((EntityPlayer)null, pos, side);
         this.initialDamage = this.curblockDamage;
         float f = 1.0F;
         Material material2 = block.getMaterial();
         Material material1 = Material.air;
         Material material = material2;
         if (material != material1) {
            block.onBlockClicked(this.theWorld, pos, this.thisPlayerMP);
            f = block.getPlayerRelativeBlockHardness(this.thisPlayerMP, this.thisPlayerMP.worldObj, pos);
         }

         if (block.getMaterial() != Material.air) {
            float f1;
            int jx = (f1 = f - 1.0F) == 0.0F ? 0 : (f1 < 0.0F ? -1 : 1);
            if (jx >= 0) {
               this.tryHarvestBlock(pos);
               return;
            }
         }

         this.isDestroyingBlock = true;
         this.field_180240_f = pos;
         int i = (int)(f * 10.0F);
         this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), pos, i);
         this.durabilityRemainingOnBlock = i;
      }
   }

   public void blockRemoving(BlockPos pos) {
      boolean flag = pos.equals(this.field_180240_f);
      if (flag) {
         int i = this.curblockDamage - this.initialDamage;
         Block block = this.theWorld.getBlockState(pos).getBlock();
         if (block.getMaterial() != Material.air) {
            float f = block.getPlayerRelativeBlockHardness(this.thisPlayerMP, this.thisPlayerMP.worldObj, pos) * (float)(i + 1);
            float f1;
            int jx = (f1 = f - 0.7F) == 0.0F ? 0 : (f1 < 0.0F ? -1 : 1);
            if (jx >= 0) {
               this.isDestroyingBlock = false;
               this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), pos, -1);
               this.tryHarvestBlock(pos);
            } else if (!this.receivedFinishDiggingPacket) {
               this.isDestroyingBlock = false;
               this.receivedFinishDiggingPacket = true;
               this.field_180241_i = pos;
               this.initialBlockDamage = this.initialDamage;
            }
         }
      }
   }

   public void cancelDestroyingBlock() {
      this.isDestroyingBlock = false;
      this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), this.field_180240_f, -1);
   }

   private boolean removeBlock(BlockPos pos) {
      IBlockState iblockstate = this.theWorld.getBlockState(pos);
      iblockstate.getBlock().onBlockHarvested(this.theWorld, pos, iblockstate, this.thisPlayerMP);
      boolean flag = this.theWorld.setBlockToAir(pos);
      if (flag) {
         iblockstate.getBlock().onBlockDestroyedByPlayer(this.theWorld, pos, iblockstate);
      }

      return flag;
   }

   public boolean tryHarvestBlock(BlockPos pos) {
      if (this.gameType.isCreative() && this.thisPlayerMP.getHeldItem() != null && this.thisPlayerMP.getHeldItem().getItem() instanceof ItemSword) {
         return false;
      } else {
         IBlockState iblockstate = this.theWorld.getBlockState(pos);
         TileEntity tileentity = this.theWorld.getTileEntity(pos);
         boolean flag1 = this.gameType.isAdventure();
         if (flag1) {
            if (this.gameType == WorldSettings.GameType.SPECTATOR) {
               return false;
            }

            if (!this.thisPlayerMP.isAllowEdit()) {
               ItemStack itemstack = this.thisPlayerMP.getCurrentEquippedItem();
               if (itemstack == null) {
                  return false;
               }

               if (!itemstack.canDestroy(iblockstate.getBlock())) {
                  return false;
               }
            }
         }

         this.theWorld.playAuxSFXAtEntity(this.thisPlayerMP, (int)j, pos, Block.getStateId(iblockstate));
         boolean flag2 = this.removeBlock(pos);
         if (this.isCreative()) {
            this.thisPlayerMP.playerNetServerHandler.sendPacket(new S23PacketBlockChange(this.theWorld, pos));
         } else {
            ItemStack itemstack1 = this.thisPlayerMP.getCurrentEquippedItem();
            boolean flag = this.thisPlayerMP.canHarvestBlock(iblockstate.getBlock());
            if (itemstack1 != null) {
               itemstack1.onBlockDestroyed(this.theWorld, iblockstate.getBlock(), pos, this.thisPlayerMP);
               int i = itemstack1.stackSize;
               if (i == 0) {
                  this.thisPlayerMP.destroyCurrentEquippedItem();
               }
            }

            if (flag2 && flag) {
               iblockstate.getBlock().harvestBlock(this.theWorld, this.thisPlayerMP, pos, iblockstate, tileentity);
            }
         }

         return flag2;
      }
   }

   public boolean tryUseItem(EntityPlayer player, World worldIn, ItemStack stack) {
      if (this.gameType == WorldSettings.GameType.SPECTATOR) {
         return false;
      } else {
         int i = stack.stackSize;
         int jx = stack.getMetadata();
         ItemStack itemstack = stack.useItemRightClick(worldIn, player);
         if (itemstack != stack || itemstack != null && (itemstack.stackSize != i || itemstack.getMaxItemUseDuration() > 0 || itemstack.getMetadata() != jx)) {
            player.inventory.mainInventory[player.inventory.currentItem] = itemstack;
            boolean flag = this.isCreative();
            if (flag) {
               itemstack.stackSize = i;
               if (itemstack.isItemStackDamageable()) {
                  itemstack.setItemDamage(jx);
               }
            }

            int k = itemstack.stackSize;
            if (k == 0) {
               player.inventory.mainInventory[player.inventory.currentItem] = null;
            }

            boolean flag1 = player.isUsingItem();
            if (!flag1) {
               ((EntityPlayerMP)player).sendContainerToPlayer(player.inventoryContainer);
            }

            return true;
         } else {
            return false;
         }
      }
   }

   public boolean activateBlockOrUseItem(
      EntityPlayer player, World worldIn, ItemStack stack, BlockPos pos, EnumFacing side, float offsetX, float offsetY, float offsetZ
   ) {
      if (this.gameType == WorldSettings.GameType.SPECTATOR) {
         TileEntity tileentity = worldIn.getTileEntity(pos);
         boolean flag1 = tileentity instanceof ILockableContainer;
         if (flag1) {
            Block block = worldIn.getBlockState(pos).getBlock();
            ILockableContainer ilockablecontainer = (ILockableContainer)tileentity;
            if (ilockablecontainer instanceof TileEntityChest && block instanceof BlockChest) {
               ilockablecontainer = ((BlockChest)block).getLockableContainer(worldIn, pos);
            }

            if (ilockablecontainer != null) {
               player.displayGUIChest(ilockablecontainer);
               return true;
            }
         } else {
            boolean flag2 = tileentity instanceof IInventory;
            if (flag2) {
               player.displayGUIChest((IInventory)tileentity);
               return true;
            }
         }

         return false;
      } else {
         label40: {
            if (player.isSneaking()) {
               ItemStack itemstack = player.getHeldItem();
               if (itemstack != null) {
                  break label40;
               }
            }

            IBlockState iblockstate = worldIn.getBlockState(pos);
            if (iblockstate.getBlock().onBlockActivated(worldIn, pos, iblockstate, player, side, offsetX, offsetY, offsetZ)) {
               return true;
            }
         }

         if (stack == null) {
            return false;
         } else {
            boolean flag3 = this.isCreative();
            if (flag3) {
               int jx = stack.getMetadata();
               int i = stack.stackSize;
               boolean flag = stack.onItemUse(player, worldIn, pos, side, offsetX, offsetY, offsetZ);
               stack.setItemDamage(jx);
               stack.stackSize = i;
               return flag;
            } else {
               return stack.onItemUse(player, worldIn, pos, side, offsetX, offsetY, offsetZ);
            }
         }
      }
   }

   public void setWorld(WorldServer serverWorld) {
      this.theWorld = serverWorld;
   }
}
