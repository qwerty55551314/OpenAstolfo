package net.minecraft.item;

import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

public class ItemPickaxe extends ItemTool {
   private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(
      Blocks.activator_rail,
      Blocks.coal_ore,
      Blocks.cobblestone,
      Blocks.detector_rail,
      Blocks.diamond_block,
      Blocks.diamond_ore,
      Blocks.double_stone_slab,
      Blocks.golden_rail,
      Blocks.gold_block,
      Blocks.gold_ore,
      Blocks.ice,
      Blocks.iron_block,
      Blocks.iron_ore,
      Blocks.lapis_block,
      Blocks.lapis_ore,
      Blocks.lit_redstone_ore,
      Blocks.mossy_cobblestone,
      Blocks.netherrack,
      Blocks.packed_ice,
      Blocks.rail,
      Blocks.redstone_ore,
      Blocks.sandstone,
      Blocks.red_sandstone,
      Blocks.stone,
      Blocks.stone_slab
   );

   public ItemPickaxe(Item.ToolMaterial material) {
      super(2.0F, material, EFFECTIVE_ON);
   }

   @Override
   public boolean canHarvestBlock(Block blockIn) {
      Block block = Blocks.obsidian;
      boolean flag;
      if (blockIn == block) {
         flag = super.toolMaterial.getHarvestLevel() == 3;
      } else {
         Block block1 = Blocks.diamond_block;
         if (blockIn != block1) {
            Block block2 = Blocks.diamond_ore;
            if (blockIn != block2) {
               Block block3 = Blocks.emerald_ore;
               if (blockIn != block3) {
                  Block block4 = Blocks.emerald_block;
                  if (blockIn != block4) {
                     Block block5 = Blocks.gold_block;
                     if (blockIn != block5) {
                        Block block6 = Blocks.gold_ore;
                        if (blockIn != block6) {
                           Block block7 = Blocks.iron_block;
                           if (blockIn != block7) {
                              Block block8 = Blocks.iron_ore;
                              if (blockIn != block8) {
                                 Block block9 = Blocks.lapis_block;
                                 if (blockIn != block9) {
                                    Block block10 = Blocks.lapis_ore;
                                    if (blockIn != block10) {
                                       Block block11 = Blocks.redstone_ore;
                                       if (blockIn != block11 && blockIn != Blocks.lit_redstone_ore) {
                                          Material material4 = blockIn.getMaterial();
                                          Material material1 = Material.rock;
                                          Material material = material4;
                                          if (material == material1) {
                                             flag = true;
                                          } else {
                                             Material material5 = blockIn.getMaterial();
                                             Material material3 = Material.iron;
                                             Material material2 = material5;
                                             flag = material2 == material3 ? true : blockIn.getMaterial() == Material.anvil;
                                          }

                                          return flag;
                                       } else {
                                          int i = super.toolMaterial.getHarvestLevel();
                                          return i >= 2;
                                       }
                                    }
                                 }

                                 return super.toolMaterial.getHarvestLevel() >= 1;
                              }
                           }

                           return super.toolMaterial.getHarvestLevel() >= 1;
                        }
                     }

                     return super.toolMaterial.getHarvestLevel() >= 2;
                  }
               }

               return super.toolMaterial.getHarvestLevel() >= 2;
            }
         }

         flag = super.toolMaterial.getHarvestLevel() >= 2;
      }

      return flag;
   }

   @Override
   public float getStrVsBlock(ItemStack stack, Block state) {
      Material material4 = state.getMaterial();
      Material material1 = Material.iron;
      Material material = material4;
      if (material != material1) {
         material4 = state.getMaterial();
         Material material3 = Material.anvil;
         Material material2 = material4;
         if (material2 != material3 && state.getMaterial() != Material.rock) {
            return super.getStrVsBlock(stack, state);
         }
      }

      return super.efficiencyOnProperMaterial;
   }
}
