package net.minecraft.item;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemGlassBottle extends Item {
   public ItemGlassBottle() {
      this.setCreativeTab(CreativeTabs.tabBrewing);
   }

   @Override
   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
      String[] astring = new String[0];
      MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(worldIn, playerIn, true);
      if (movingobjectposition == null) {
         return itemStackIn;
      } else {
         if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            BlockPos blockpos = movingobjectposition.getBlockPos();
            boolean flag = worldIn.isBlockModifiable(playerIn, blockpos);
            if (!flag) {
               return itemStackIn;
            }

            if (!playerIn.canPlayerEdit(blockpos.offset(movingobjectposition.sideHit), movingobjectposition.sideHit, itemStackIn)) {
               return itemStackIn;
            }

            if (worldIn.getBlockState(blockpos).getBlock().getMaterial() == Material.water) {
               itemStackIn.stackSize--;
               playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
               int i = itemStackIn.stackSize;
               if (i <= 0) {
                  return new ItemStack(Items.potionitem);
               }

               if (!playerIn.inventory.addItemStackToInventory(new ItemStack(Items.potionitem))) {
                  playerIn.dropPlayerItemWithRandomChoice(new ItemStack(Items.potionitem, 1, 0), false);
               }
            }
         }

         return itemStackIn;
      }
   }
}
