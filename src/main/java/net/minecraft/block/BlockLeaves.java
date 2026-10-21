package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;

public abstract class BlockLeaves extends BlockLeavesBase {
   public static final PropertyBool DECAYABLE = PropertyBool.create("decayable");
   public static final PropertyBool CHECK_DECAY = PropertyBool.create("check_decay");
   public int[] surroundings;
   public int iconIndex;
   public boolean isTransparent;

   public BlockLeaves() {
      super(Material.leaves, false);
      this.setTickRandomly(true);
      this.setCreativeTab(CreativeTabs.tabDecorations);
      this.setHardness(0.2F);
      this.setLightOpacity(1);
      this.setStepSound(Block.soundTypeGrass);
   }

   @Override
   public int getBlockColor() {
      return ColorizerFoliage.getFoliageColor(0.5, 1.0);
   }

   @Override
   public int getRenderColor(IBlockState state) {
      return 4764952;
   }

   @Override
   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
      return BiomeColorHelper.getFoliageColorAtPos(worldIn, pos);
   }

   @Override
   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
      int i = 1 + 1;
      int j = pos.getX();
      int k = pos.getY();
      int l = pos.getZ();
      boolean flag = worldIn.isAreaLoaded(new BlockPos(j - i, k - i, l - i), new BlockPos(j + i, k + i, l + i));
      if (flag) {
         for (int i1 = -1; i1 <= 1; i1++) {
            for (int j1 = -1; j1 <= 1; j1++) {
               int l1 = -1;

               for (int k1 = l1; k1 <= 1; k1++) {
                  BlockPos blockpos = pos.add(i1, j1, k1);
                  IBlockState iblockstate = worldIn.getBlockState(blockpos);
                  if (iblockstate.getBlock().getMaterial() == Material.leaves && !iblockstate.getValue(CHECK_DECAY)) {
                     worldIn.setBlockState(blockpos, iblockstate.withProperty(CHECK_DECAY, true), 4);
                  }
               }
            }
         }
      }
   }

   @Override
   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
      boolean flag = worldIn.isRemote;
      if (!flag) {
         boolean flag1 = state.getValue(CHECK_DECAY);
         if (flag1) {
            boolean flag2 = state.getValue(DECAYABLE);
            if (flag2) {
               int i = 4 + 1;
               int j = pos.getX();
               int k = pos.getY();
               int l = pos.getZ();
               int i1 = 32 * 32;
               int j1 = 32 / 2;
               if (this.surroundings == null) {
                  this.surroundings = new int[32 * 32 * 32];
               }

               boolean flag3 = worldIn.isAreaLoaded(new BlockPos(j - i, k - i, l - i), new BlockPos(j + i, k + i, l + i));
               if (flag3) {
                  BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                  for (int k1 = -4; k1 <= 4; k1++) {
                     for (int l1 = -4; l1 <= 4; l1++) {
                        for (int i2 = -4; i2 <= 4; i2++) {
                           Block block = worldIn.getBlockState(blockpos$mutableblockpos.set(j + k1, k + l1, l + i2)).getBlock();
                           if (block != Blocks.log && block != Blocks.log2) {
                              if (block.getMaterial() == Material.leaves) {
                                 this.surroundings[(k1 + j1) * i1 + (l1 + j1) * 32 + i2 + j1] = -2;
                              } else {
                                 this.surroundings[(k1 + j1) * i1 + (l1 + j1) * 32 + i2 + j1] = -1;
                              }
                           } else {
                              this.surroundings[(k1 + j1) * i1 + (l1 + j1) * 32 + i2 + j1] = 0;
                           }
                        }
                     }
                  }

                  for (int k2 = 1; k2 <= 4; k2++) {
                     for (int l2 = -4; l2 <= 4; l2++) {
                        for (int i3 = -4; i3 <= 4; i3++) {
                           for (int j3 = -4; j3 <= 4; j3++) {
                              if (this.surroundings[(l2 + j1) * i1 + (i3 + j1) * 32 + j3 + j1] == k2 - 1) {
                                 if (this.surroundings[(l2 + j1 - 1) * i1 + (i3 + j1) * 32 + j3 + j1] == -2) {
                                    this.surroundings[(l2 + j1 - 1) * i1 + (i3 + j1) * 32 + j3 + j1] = k2;
                                 }

                                 if (this.surroundings[(l2 + j1 + 1) * i1 + (i3 + j1) * 32 + j3 + j1] == -2) {
                                    this.surroundings[(l2 + j1 + 1) * i1 + (i3 + j1) * 32 + j3 + j1] = k2;
                                 }

                                 if (this.surroundings[(l2 + j1) * i1 + (i3 + j1 - 1) * 32 + j3 + j1] == -2) {
                                    this.surroundings[(l2 + j1) * i1 + (i3 + j1 - 1) * 32 + j3 + j1] = k2;
                                 }

                                 if (this.surroundings[(l2 + j1) * i1 + (i3 + j1 + 1) * 32 + j3 + j1] == -2) {
                                    this.surroundings[(l2 + j1) * i1 + (i3 + j1 + 1) * 32 + j3 + j1] = k2;
                                 }

                                 if (this.surroundings[(l2 + j1) * i1 + (i3 + j1) * 32 + (j3 + j1 - 1)] == -2) {
                                    this.surroundings[(l2 + j1) * i1 + (i3 + j1) * 32 + (j3 + j1 - 1)] = k2;
                                 }

                                 if (this.surroundings[(l2 + j1) * i1 + (i3 + j1) * 32 + j3 + j1 + 1] == -2) {
                                    this.surroundings[(l2 + j1) * i1 + (i3 + j1) * 32 + j3 + j1 + 1] = k2;
                                 }
                              }
                           }
                        }
                     }
                  }
               }

               int j2 = this.surroundings[j1 * i1 + j1 * 32 + j1];
               if (j2 >= 0) {
                  worldIn.setBlockState(pos, state.withProperty(CHECK_DECAY, false), 4);
               } else {
                  this.destroy(worldIn, pos);
               }
            }
         }
      }
   }

   @Override
   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
      boolean flag = worldIn.isRainingAt(pos.up());
      if (flag) {
         boolean flag1 = World.doesBlockHaveSolidTopSurface(worldIn, pos.down());
         if (!flag1) {
            int i = rand.nextInt(15);
            if (i == 1) {
               double d0 = (double)((float)pos.getX() + rand.nextFloat());
               double d1 = (double)pos.getY() - 0.05;
               double d2 = (double)((float)pos.getZ() + rand.nextFloat());
               worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, d0, d1, d2, 0.0, 0.0, 0.0);
            }
         }
      }
   }

   private void destroy(World worldIn, BlockPos pos) {
      this.dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
      worldIn.setBlockToAir(pos);
   }

   @Override
   public int quantityDropped(Random randomIn) {
      return randomIn.nextInt(20) == 0 ? 1 : 0;
   }

   @Override
   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
      return Item.getItemFromBlock(Blocks.sapling);
   }

   @Override
   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
      boolean flag = worldIn.isRemote;
      if (!flag) {
         int i = this.getSaplingDropChance(state);
         if (fortune > 0) {
            i -= 2 << fortune;
            if (i < 10) {
               i = 10;
            }
         }

         int j = worldIn.rand.nextInt(i);
         if (j == 0) {
            Item item = this.getItemDropped(state, worldIn.rand, fortune);
            spawnAsEntity(worldIn, pos, new ItemStack(item, 1, this.damageDropped(state)));
         }

         i = 200;
         if (fortune > 0) {
            i -= 10 << fortune;
            if (i < 40) {
               i = 40;
            }
         }

         this.dropApple(worldIn, pos, state, i);
      }
   }

   public void dropApple(World worldIn, BlockPos pos, IBlockState state, int chance) {
   }

   public int getSaplingDropChance(IBlockState state) {
      return 20;
   }

   @Override
   public boolean isOpaqueCube() {
      return !super.fancyGraphics;
   }

   public void setGraphicsLevel(boolean fancy) {
      this.isTransparent = fancy;
      super.fancyGraphics = fancy;
      this.iconIndex = fancy ? 0 : 1;
   }

   @Override
   public EnumWorldBlockLayer getBlockLayer() {
      return this.isTransparent ? EnumWorldBlockLayer.CUTOUT_MIPPED : EnumWorldBlockLayer.SOLID;
   }

   @Override
   public boolean isVisuallyOpaque() {
      return false;
   }

   public abstract BlockPlanks.EnumType getWoodType(int var1);
}
