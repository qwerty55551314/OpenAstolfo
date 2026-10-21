package net.minecraft.world.gen.feature;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldGenDungeons extends WorldGenerator {
   private static final Logger J = LogManager.getLogger();
   private static final String[] SPAWNERTYPES = new String[]{"Skeleton", "Zombie", "Zombie", "Spider"};
   private static final List<WeightedRandomChestContent> CHESTCONTENT = Lists.newArrayList(
      new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 10),
      new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 4, 10),
      new WeightedRandomChestContent(Items.bread, 0, 1, 1, 10),
      new WeightedRandomChestContent(Items.wheat, 0, 1, 4, 10),
      new WeightedRandomChestContent(Items.gunpowder, 0, 1, 4, 10),
      new WeightedRandomChestContent(Items.string, 0, 1, 4, 10),
      new WeightedRandomChestContent(Items.bucket, 0, 1, 1, 10),
      new WeightedRandomChestContent(Items.golden_apple, 0, 1, 1, 1),
      new WeightedRandomChestContent(Items.redstone, 0, 1, 4, 10),
      new WeightedRandomChestContent(Items.record_13, 0, 1, 1, 4),
      new WeightedRandomChestContent(Items.record_cat, 0, 1, 1, 4),
      new WeightedRandomChestContent(Items.name_tag, 0, 1, 1, 10),
      new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 2),
      new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 5),
      new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1)
   );

   @Override
   public boolean generate(World worldIn, Random rand, BlockPos position) {
      int i = rand.nextInt(2) + 2;
      int j = -i - 1;
      int k = i + 1;
      int l = rand.nextInt(2) + 2;
      int i1 = -l - 1;
      int j1 = l + 1;
      int k1 = 0;

      for (int l1 = j; l1 <= k; l1++) {
         for (int i2 = -1; i2 <= 4; i2++) {
            for (int j2 = i1; j2 <= j1; j2++) {
               BlockPos blockpos = position.add(l1, i2, j2);
               Material material = worldIn.getBlockState(blockpos).getBlock().getMaterial();
               boolean flag = material.isSolid();
               if (i2 == -1 && !flag) {
                  return false;
               }

               if (i2 == 4 && !flag) {
                  return false;
               }

               if ((l1 == j || l1 == k || j2 == i1 || j2 == j1) && i2 == 0 && worldIn.isAirBlock(blockpos) && worldIn.isAirBlock(blockpos.up())) {
                  k1++;
               }
            }
         }
      }

      if (k1 >= 1 && k1 <= 5) {
         for (int l2 = j; l2 <= k; l2++) {
            for (int j3 = 3; j3 >= -1; j3--) {
               for (int l3 = i1; l3 <= j1; l3++) {
                  BlockPos blockpos1 = position.add(l2, j3, l3);
                  if (l2 != j && j3 != -1 && l3 != i1 && l2 != k && j3 != 4 && l3 != j1) {
                     if (worldIn.getBlockState(blockpos1).getBlock() != Blocks.chest) {
                        worldIn.setBlockToAir(blockpos1);
                     }
                  } else if (blockpos1.getY() >= 0 && !worldIn.getBlockState(blockpos1.down()).getBlock().getMaterial().isSolid()) {
                     worldIn.setBlockToAir(blockpos1);
                  } else if (worldIn.getBlockState(blockpos1).getBlock().getMaterial().isSolid() && worldIn.getBlockState(blockpos1).getBlock() != Blocks.chest
                     )
                   {
                     if (j3 == -1 && rand.nextInt(4) != 0) {
                        worldIn.setBlockState(blockpos1, Blocks.mossy_cobblestone.getDefaultState(), 2);
                     } else {
                        worldIn.setBlockState(blockpos1, Blocks.cobblestone.getDefaultState(), 2);
                     }
                  }
               }
            }
         }

         for (int i3 = 0; i3 < 2; i3++) {
            for (int k3 = 0; k3 < 3; k3++) {
               int i4 = position.getX() + rand.nextInt(i * 2 + 1) - i;
               int j4 = position.getY();
               int k4 = position.getZ() + rand.nextInt(l * 2 + 1) - l;
               BlockPos blockpos2 = new BlockPos(i4, j4, k4);
               if (worldIn.isAirBlock(blockpos2)) {
                  int k2 = 0;

                  for (Object object : EnumFacing.Plane.HORIZONTAL) {
                     if (worldIn.getBlockState(blockpos2.offset((EnumFacing)object)).getBlock().getMaterial().isSolid()) {
                        k2++;
                     }
                  }

                  if (k2 == 1) {
                     worldIn.setBlockState(blockpos2, Blocks.chest.correctFacing(worldIn, blockpos2, Blocks.chest.getDefaultState()), 2);
                     List list = WeightedRandomChestContent.func_177629_a(CHESTCONTENT, Items.enchanted_book.getRandom(rand));
                     TileEntity tileentity1 = worldIn.getTileEntity(blockpos2);
                     if (tileentity1 instanceof TileEntityChest) {
                        WeightedRandomChestContent.generateChestContents(rand, list, (TileEntityChest)tileentity1, 8);
                     }
                     break;
                  }
               }
            }
         }

         worldIn.setBlockState(position, Blocks.mob_spawner.getDefaultState(), 2);
         TileEntity tileentity = worldIn.getTileEntity(position);
         if (tileentity instanceof TileEntityMobSpawner) {
            ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic().setEntityName(this.pickMobSpawner(rand));
         } else {
            J.error("Failed to fetch mob spawner entity at (" + position.getX() + ", " + position.getY() + ", " + position.getZ() + ")");
         }

         return true;
      } else {
         return false;
      }
   }

   private String pickMobSpawner(Random random2) {
      return SPAWNERTYPES[random2.nextInt(SPAWNERTYPES.length)];
   }

   static {
      int short1 = 6729;
   }
}
