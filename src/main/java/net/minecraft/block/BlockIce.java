package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class BlockIce extends BlockBreakable {
   private static final long l = -3187943336631599093L;

   public BlockIce() {
      super(Material.ice, false);
      super.slipperiness = 0.98F;
      this.setTickRandomly(true);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   @Override
   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.TRANSLUCENT;
   }

   @Override
   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te) {
      player.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
      player.addExhaustion(0.025F);
      boolean flag = this.canSilkHarvest();
      if (flag && EnchantmentHelper.getSilkTouchModifier(player)) {
         ItemStack itemstack = this.createStackedBlock(state);
         if (itemstack != null) {
            spawnAsEntity(worldIn, pos, itemstack);
         }
      } else {
         boolean flag1 = worldIn.provider.doesWaterVaporize();
         if (flag1) {
            worldIn.setBlockToAir(pos);
            return;
         }

         int i = EnchantmentHelper.getFortuneModifier(player);
         this.dropBlockAsItem(worldIn, pos, state, i);
         Material material = worldIn.getBlockState(pos.down()).getBlock().getMaterial();
         boolean flag2 = material.blocksMovement();
         if (flag2 || material.isLiquid()) {
            worldIn.setBlockState(pos, Blocks.flowing_water.getDefaultState());
         }
      }
   }

   @Override
   public int quantityDropped(Random randomIn) {
      return 0;
   }

   @Override
   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
      int i = worldIn.getLightFor(EnumSkyBlock.BLOCK, pos);
      if (i > (int)l - this.getLightOpacity()) {
         boolean flag = worldIn.provider.doesWaterVaporize();
         if (flag) {
            worldIn.setBlockToAir(pos);
         } else {
            this.dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
            worldIn.setBlockState(pos, Blocks.water.getDefaultState());
         }
      }
   }

   @Override
   public int getMobilityFlag() {
      return 0;
   }
}
