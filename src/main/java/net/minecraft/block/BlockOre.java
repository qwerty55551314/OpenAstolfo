package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockOre extends Block {
   public BlockOre() {
      this(Material.rock.getMaterialMapColor());
   }

   public BlockOre(MapColor mapColor) {
      super(Material.rock, mapColor);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   @Override
   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
      Block block = Blocks.coal_ore;
      Item item;
      if (this == block) {
         item = Items.coal;
      } else {
         Block block1 = Blocks.diamond_ore;
         if (this == block1) {
            item = Items.diamond;
         } else {
            Block block2 = Blocks.lapis_ore;
            if (this == block2) {
               item = Items.dye;
            } else {
               Block block3 = Blocks.emerald_ore;
               item = this == block3 ? Items.emerald : (this == Blocks.quartz_ore ? Items.quartz : Item.getItemFromBlock(this));
            }
         }
      }

      return item;
   }

   @Override
   public int quantityDropped(Random randomIn) {
      return this == Blocks.lapis_ore ? 4 + randomIn.nextInt(5) : 1;
   }

   @Override
   public int quantityDroppedWithBonus(int fortune, Random randomIn) {
      if (fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped(this.getBlockState().getValidStates().iterator().next(), randomIn, fortune)) {
         int i = randomIn.nextInt(fortune + 2) - 1;
         if (i < 0) {
            i = 0;
         }

         return this.quantityDropped(randomIn) * (i + 1);
      } else {
         return this.quantityDropped(randomIn);
      }
   }

   @Override
   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
      super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
      if (this.getItemDropped(state, worldIn.rand, fortune) != Item.getItemFromBlock(this)) {
         int i = 0;
         Block block = Blocks.coal_ore;
         if (this == block) {
            i = MathHelper.getRandomIntegerInRange(worldIn.rand, 0, 2);
         } else {
            Block block1 = Blocks.diamond_ore;
            if (this == block1) {
               i = MathHelper.getRandomIntegerInRange(worldIn.rand, 3, 7);
            } else {
               Block block2 = Blocks.emerald_ore;
               if (this == block2) {
                  i = MathHelper.getRandomIntegerInRange(worldIn.rand, 3, 7);
               } else {
                  Block block3 = Blocks.lapis_ore;
                  if (this == block3) {
                     i = MathHelper.getRandomIntegerInRange(worldIn.rand, 2, 5);
                  } else if (this == Blocks.quartz_ore) {
                     i = MathHelper.getRandomIntegerInRange(worldIn.rand, 2, 5);
                  }
               }
            }
         }

         this.dropXpOnBlockBreak(worldIn, pos, i);
      }
   }

   @Override
   public int getDamageValue(World worldIn, BlockPos pos) {
      return 0;
   }

   @Override
   public int damageDropped(IBlockState state) {
      return this == Blocks.lapis_ore ? EnumDyeColor.BLUE.getDyeDamage() : 0;
   }
}
