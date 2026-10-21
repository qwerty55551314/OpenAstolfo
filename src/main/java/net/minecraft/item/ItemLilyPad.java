package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemLilyPad extends ItemColored {
   public ItemLilyPad(Block block) {
      super(block, false);
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

            BlockPos blockpos1 = blockpos.up();
            IBlockState iblockstate = worldIn.getBlockState(blockpos);
            if (iblockstate.getBlock().getMaterial() == Material.water) {
               int i = iblockstate.getValue(BlockLiquid.LEVEL);
               if (i == 0) {
                  boolean flag1 = worldIn.isAirBlock(blockpos1);
                  if (flag1) {
                     worldIn.setBlockState(blockpos1, Blocks.waterlily.getDefaultState());
                     if (!playerIn.capabilities.isCreativeMode) {
                        itemStackIn.stackSize--;
                     }

                     playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
                  }
               }
            }
         }

         return itemStackIn;
      }
   }

   @Override
   public int getColorFromItemStack(ItemStack stack, int renderPass) {
      return Blocks.waterlily.getRenderColor(Blocks.waterlily.getStateFromMeta(stack.getMetadata()));
   }
}
