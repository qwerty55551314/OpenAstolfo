package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ItemShears extends Item {
   private static final long b = -8908532401274093330L;

   public ItemShears() {
      this.setMaxStackSize(1);
      this.setMaxDamage((int)b);
      this.setCreativeTab(CreativeTabs.tabTools);
   }

   @Override
   public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, BlockPos pos, EntityLivingBase playerIn) {
      if (blockIn.getMaterial() != Material.leaves
         && blockIn != Blocks.web
         && blockIn != Blocks.tallgrass
         && blockIn != Blocks.vine
         && blockIn != Blocks.tripwire
         && blockIn != Blocks.wool) {
         return super.onBlockDestroyed(stack, worldIn, blockIn, pos, playerIn);
      } else {
         stack.damageItem(1, playerIn);
         return true;
      }
   }

   @Override
   public boolean canHarvestBlock(Block blockIn) {
      if (blockIn != Blocks.web) {
         BlockRedstoneWire blockredstonewire = Blocks.redstone_wire;
         if (blockIn != blockredstonewire && blockIn != Blocks.tripwire) {
            return false;
         }
      }

      return true;
   }

   @Override
   public float getStrVsBlock(ItemStack stack, Block state) {
      return state != Blocks.web && state.getMaterial() != Material.leaves ? (state == Blocks.wool ? 5.0F : super.getStrVsBlock(stack, state)) : 15.0F;
   }
}
