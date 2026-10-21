package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class StructureMineshaftPieces {
   private static final List<WeightedRandomChestContent> CHEST_CONTENT_WEIGHT_LIST = Lists.newArrayList(
      new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10),
      new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5),
      new WeightedRandomChestContent(Items.redstone, 0, 4, 9, 5),
      new WeightedRandomChestContent(Items.dye, EnumDyeColor.BLUE.getDyeDamage(), 4, 9, 5),
      new WeightedRandomChestContent(Items.diamond, 0, 1, 2, 3),
      new WeightedRandomChestContent(Items.coal, 0, 3, 8, 10),
      new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15),
      new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 1),
      new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.rail), 0, 4, 8, 1),
      new WeightedRandomChestContent(Items.melon_seeds, 0, 2, 4, 10),
      new WeightedRandomChestContent(Items.pumpkin_seeds, 0, 2, 4, 10),
      new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3),
      new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1)
   );

   public static void registerStructurePieces() {
      MapGenStructureIO.registerStructureComponent(StructureMineshaftPieces.Corridor.class, "MSCorridor");
      MapGenStructureIO.registerStructureComponent(StructureMineshaftPieces.Cross.class, "MSCrossing");
      MapGenStructureIO.registerStructureComponent(StructureMineshaftPieces.Room.class, "MSRoom");
      MapGenStructureIO.registerStructureComponent(StructureMineshaftPieces.Stairs.class, "MSStairs");
   }

   private static StructureComponent func_175892_a(List<StructureComponent> listIn, Random rand, int x, int y, int z, EnumFacing facing, int typeIn) {
      int i = rand.nextInt(100);
      if (i >= 80) {
         StructureBoundingBox structureboundingbox = StructureMineshaftPieces.Cross.func_175813_a(listIn, rand, x, y, z, facing);
         if (structureboundingbox != null) {
            return new StructureMineshaftPieces.Cross(typeIn, rand, structureboundingbox, facing);
         }
      } else if (i >= 70) {
         StructureBoundingBox structureboundingbox1 = StructureMineshaftPieces.Stairs.func_175812_a(listIn, rand, x, y, z, facing);
         if (structureboundingbox1 != null) {
            return new StructureMineshaftPieces.Stairs(typeIn, rand, structureboundingbox1, facing);
         }
      } else {
         StructureBoundingBox structureboundingbox2 = StructureMineshaftPieces.Corridor.func_175814_a(listIn, rand, x, y, z, facing);
         if (structureboundingbox2 != null) {
            return new StructureMineshaftPieces.Corridor(typeIn, rand, structureboundingbox2, facing);
         }
      }

      return null;
   }

   private static StructureComponent func_175890_b(
      StructureComponent componentIn, List<StructureComponent> listIn, Random rand, int x, int y, int z, EnumFacing facing, int typeIn
   ) {
      if (typeIn > 8) {
         return null;
      } else {
         int i = Math.abs(x - componentIn.getBoundingBox().minX);
         if (i <= 80 && Math.abs(z - componentIn.getBoundingBox().minZ) <= 80) {
            StructureComponent structurecomponent = func_175892_a(listIn, rand, x, y, z, facing, typeIn + 1);
            if (structurecomponent != null) {
               listIn.add(structurecomponent);
               structurecomponent.buildComponent(componentIn, listIn, rand);
            }

            return structurecomponent;
         } else {
            return null;
         }
      }
   }

   public static class Corridor extends StructureComponent {
      private boolean hasRails;
      private boolean hasSpiders;
      private boolean spawnerPlaced;
      private int sectionCount;

      public Corridor() {
      }

      @Override
      public void writeStructureToNBT(NBTTagCompound tagCompound) {
         tagCompound.setBoolean("hr", this.hasRails);
         tagCompound.setBoolean("sc", this.hasSpiders);
         tagCompound.setBoolean("hps", this.spawnerPlaced);
         tagCompound.setInteger("Num", this.sectionCount);
      }

      @Override
      public void readStructureFromNBT(NBTTagCompound tagCompound) {
         this.hasRails = tagCompound.getBoolean("hr");
         this.hasSpiders = tagCompound.getBoolean("sc");
         this.spawnerPlaced = tagCompound.getBoolean("hps");
         this.sectionCount = tagCompound.getInteger("Num");
      }

      public Corridor(int typeIn, Random rand, StructureBoundingBox structurebb, EnumFacing facing) {
         super(typeIn);
         super.coordBaseMode = facing;
         super.boundingBox = structurebb;
         this.hasRails = rand.nextInt(3) == 0;
         this.hasSpiders = !this.hasRails && rand.nextInt(23) == 0;
         if (super.coordBaseMode != EnumFacing.NORTH && super.coordBaseMode != EnumFacing.SOUTH) {
            this.sectionCount = structurebb.getXSize() / 5;
         } else {
            this.sectionCount = structurebb.getZSize() / 5;
         }
      }

      public static StructureBoundingBox func_175814_a(List<StructureComponent> list, Random rand, int x, int y, int z, EnumFacing facing) {
         Object object = null;
         StructureBoundingBox structureboundingbox = new StructureBoundingBox(x, y, z, x, y + 2, z);

         int i;
         for (i = rand.nextInt(3) + 2; i > 0; i--) {
            int j = i * 5;
            switch (facing) {
               case NORTH:
                  structureboundingbox.maxX = x + 2;
                  structureboundingbox.minZ = z - (j - 1);
                  break;
               case SOUTH:
                  structureboundingbox.maxX = x + 2;
                  structureboundingbox.maxZ = z + (j - 1);
                  break;
               case WEST:
                  structureboundingbox.minX = x - (j - 1);
                  structureboundingbox.maxZ = z + 2;
                  break;
               case EAST:
                  structureboundingbox.maxX = x + (j - 1);
                  structureboundingbox.maxZ = z + 2;
            }

            if (StructureComponent.findIntersecting(list, structureboundingbox) == null) {
               break;
            }
         }

         return i > 0 ? structureboundingbox : null;
      }

      @Override
      public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
         int i = this.getComponentType();
         int j = rand.nextInt(4);
         if (super.coordBaseMode != null) {
            switch (super.coordBaseMode) {
               case NORTH:
                  if (j <= 1) {
                     StructureMineshaftPieces.func_175890_b(
                        componentIn,
                        listIn,
                        rand,
                        super.boundingBox.minX,
                        super.boundingBox.minY - 1 + rand.nextInt(3),
                        super.boundingBox.minZ - 1,
                        super.coordBaseMode,
                        i
                     );
                  } else if (j == 2) {
                     StructureMineshaftPieces.func_175890_b(
                        componentIn,
                        listIn,
                        rand,
                        super.boundingBox.minX - 1,
                        super.boundingBox.minY - 1 + rand.nextInt(3),
                        super.boundingBox.minZ,
                        EnumFacing.WEST,
                        i
                     );
                  } else {
                     StructureMineshaftPieces.func_175890_b(
                        componentIn,
                        listIn,
                        rand,
                        super.boundingBox.maxX + 1,
                        super.boundingBox.minY - 1 + rand.nextInt(3),
                        super.boundingBox.minZ,
                        EnumFacing.EAST,
                        i
                     );
                  }
                  break;
               case SOUTH:
                  if (j <= 1) {
                     StructureMineshaftPieces.func_175890_b(
                        componentIn,
                        listIn,
                        rand,
                        super.boundingBox.minX,
                        super.boundingBox.minY - 1 + rand.nextInt(3),
                        super.boundingBox.maxZ + 1,
                        super.coordBaseMode,
                        i
                     );
                  } else if (j == 2) {
                     StructureMineshaftPieces.func_175890_b(
                        componentIn,
                        listIn,
                        rand,
                        super.boundingBox.minX - 1,
                        super.boundingBox.minY - 1 + rand.nextInt(3),
                        super.boundingBox.maxZ - 3,
                        EnumFacing.WEST,
                        i
                     );
                  } else {
                     StructureMineshaftPieces.func_175890_b(
                        componentIn,
                        listIn,
                        rand,
                        super.boundingBox.maxX + 1,
                        super.boundingBox.minY - 1 + rand.nextInt(3),
                        super.boundingBox.maxZ - 3,
                        EnumFacing.EAST,
                        i
                     );
                  }
                  break;
               case WEST:
                  if (j <= 1) {
                     StructureMineshaftPieces.func_175890_b(
                        componentIn,
                        listIn,
                        rand,
                        super.boundingBox.minX - 1,
                        super.boundingBox.minY - 1 + rand.nextInt(3),
                        super.boundingBox.minZ,
                        super.coordBaseMode,
                        i
                     );
                  } else if (j == 2) {
                     StructureMineshaftPieces.func_175890_b(
                        componentIn,
                        listIn,
                        rand,
                        super.boundingBox.minX,
                        super.boundingBox.minY - 1 + rand.nextInt(3),
                        super.boundingBox.minZ - 1,
                        EnumFacing.NORTH,
                        i
                     );
                  } else {
                     StructureMineshaftPieces.func_175890_b(
                        componentIn,
                        listIn,
                        rand,
                        super.boundingBox.minX,
                        super.boundingBox.minY - 1 + rand.nextInt(3),
                        super.boundingBox.maxZ + 1,
                        EnumFacing.SOUTH,
                        i
                     );
                  }
                  break;
               case EAST:
                  if (j <= 1) {
                     StructureMineshaftPieces.func_175890_b(
                        componentIn,
                        listIn,
                        rand,
                        super.boundingBox.maxX + 1,
                        super.boundingBox.minY - 1 + rand.nextInt(3),
                        super.boundingBox.minZ,
                        super.coordBaseMode,
                        i
                     );
                  } else if (j == 2) {
                     StructureMineshaftPieces.func_175890_b(
                        componentIn,
                        listIn,
                        rand,
                        super.boundingBox.maxX - 3,
                        super.boundingBox.minY - 1 + rand.nextInt(3),
                        super.boundingBox.minZ - 1,
                        EnumFacing.NORTH,
                        i
                     );
                  } else {
                     StructureMineshaftPieces.func_175890_b(
                        componentIn,
                        listIn,
                        rand,
                        super.boundingBox.maxX - 3,
                        super.boundingBox.minY - 1 + rand.nextInt(3),
                        super.boundingBox.maxZ + 1,
                        EnumFacing.SOUTH,
                        i
                     );
                  }
            }
         }

         if (i < 8) {
            if (super.coordBaseMode != EnumFacing.NORTH && super.coordBaseMode != EnumFacing.SOUTH) {
               for (int i1 = super.boundingBox.minX + 3; i1 + 3 <= super.boundingBox.maxX; i1 += 5) {
                  int j1 = rand.nextInt(5);
                  if (j1 == 0) {
                     StructureMineshaftPieces.func_175890_b(
                        componentIn, listIn, rand, i1, super.boundingBox.minY, super.boundingBox.minZ - 1, EnumFacing.NORTH, i + 1
                     );
                  } else if (j1 == 1) {
                     StructureMineshaftPieces.func_175890_b(
                        componentIn, listIn, rand, i1, super.boundingBox.minY, super.boundingBox.maxZ + 1, EnumFacing.SOUTH, i + 1
                     );
                  }
               }
            } else {
               for (int k = super.boundingBox.minZ + 3; k + 3 <= super.boundingBox.maxZ; k += 5) {
                  int l = rand.nextInt(5);
                  if (l == 0) {
                     StructureMineshaftPieces.func_175890_b(
                        componentIn, listIn, rand, super.boundingBox.minX - 1, super.boundingBox.minY, k, EnumFacing.WEST, i + 1
                     );
                  } else if (l == 1) {
                     StructureMineshaftPieces.func_175890_b(
                        componentIn, listIn, rand, super.boundingBox.maxX + 1, super.boundingBox.minY, k, EnumFacing.EAST, i + 1
                     );
                  }
               }
            }
         }
      }

      @Override
      public boolean generateChestContents(
         World worldIn, StructureBoundingBox boundingBoxIn, Random rand, int x, int y, int z, List<WeightedRandomChestContent> listIn, int maxIn
      ) {
         Object object = null;
         BlockPos blockpos = new BlockPos(this.getXWithOffset(x, z), this.getYWithOffset(y), this.getZWithOffset(x, z));
         if (boundingBoxIn.isVecInside(blockpos) && worldIn.getBlockState(blockpos).getBlock().getMaterial() == Material.air) {
            int i = rand.nextBoolean() ? 1 : 0;
            worldIn.setBlockState(blockpos, Blocks.rail.getStateFromMeta(this.getMetadataWithOffset(Blocks.rail, i)), 2);
            EntityMinecartChest entityminecartchest = new EntityMinecartChest(
               worldIn, (double)((float)blockpos.getX() + 0.5F), (double)((float)blockpos.getY() + 0.5F), (double)((float)blockpos.getZ() + 0.5F)
            );
            WeightedRandomChestContent.generateChestContents(rand, listIn, entityminecartchest, maxIn);
            worldIn.spawnEntityInWorld(entityminecartchest);
            return true;
         } else {
            return false;
         }
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         boolean flag = this.isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn);
         if (flag) {
            return false;
         } else {
            int i = this.sectionCount * 5 - 1;
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 2, 1, i, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.func_175805_a(
               worldIn, structureBoundingBoxIn, randomIn, 0.8F, 0, 2, 0, 2, 2, i, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false
            );
            boolean flag1 = this.hasSpiders;
            if (flag1) {
               this.func_175805_a(
                  worldIn, structureBoundingBoxIn, randomIn, 0.6F, 0, 0, 0, 2, 1, i, Blocks.web.getDefaultState(), Blocks.air.getDefaultState(), false
               );
            }

            for (int j = 0; j < this.sectionCount; j++) {
               int k = 2 + j * 5;
               this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, k, 0, 1, k, Blocks.oak_fence.getDefaultState(), Blocks.air.getDefaultState(), false);
               IBlockState iblockstate4 = Blocks.oak_fence.getDefaultState();
               IBlockState iblockstate1 = Blocks.air.getDefaultState();
               IBlockState iblockstate = iblockstate4;
               this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 0, k, 2, 1, k, iblockstate, iblockstate1, false);
               if (randomIn.nextInt(4) == 0) {
                  this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, k, 0, 2, k, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
                  this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 2, k, 2, 2, k, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
               } else {
                  this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, k, 2, 2, k, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
               }

               this.randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 0, 2, k - 1, Blocks.web.getDefaultState());
               this.randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 2, 2, k - 1, Blocks.web.getDefaultState());
               this.randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 0, 2, k + 1, Blocks.web.getDefaultState());
               this.randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 2, 2, k + 1, Blocks.web.getDefaultState());
               this.randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.05F, 0, 2, k - 2, Blocks.web.getDefaultState());
               this.randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.05F, 2, 2, k - 2, Blocks.web.getDefaultState());
               this.randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.05F, 0, 2, k + 2, Blocks.web.getDefaultState());
               this.randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.05F, 2, 2, k + 2, Blocks.web.getDefaultState());
               this.randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.05F, 1, 2, k - 1, Blocks.torch.getStateFromMeta(EnumFacing.UP.getIndex()));
               this.randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.05F, 1, 2, k + 1, Blocks.torch.getStateFromMeta(EnumFacing.UP.getIndex()));
               int k1 = randomIn.nextInt(100);
               if (k1 == 0) {
                  this.generateChestContents(
                     worldIn,
                     structureBoundingBoxIn,
                     randomIn,
                     2,
                     0,
                     k - 1,
                     WeightedRandomChestContent.func_177629_a(StructureMineshaftPieces.CHEST_CONTENT_WEIGHT_LIST, Items.enchanted_book.getRandom(randomIn)),
                     3 + randomIn.nextInt(4)
                  );
               }

               int l1 = randomIn.nextInt(100);
               if (l1 == 0) {
                  this.generateChestContents(
                     worldIn,
                     structureBoundingBoxIn,
                     randomIn,
                     0,
                     0,
                     k + 1,
                     WeightedRandomChestContent.func_177629_a(StructureMineshaftPieces.CHEST_CONTENT_WEIGHT_LIST, Items.enchanted_book.getRandom(randomIn)),
                     3 + randomIn.nextInt(4)
                  );
               }

               boolean flag2 = this.hasSpiders;
               if (flag2) {
                  boolean flag3 = this.spawnerPlaced;
                  if (!flag3) {
                     int l = this.getYWithOffset(0);
                     int i1 = k - 1 + randomIn.nextInt(3);
                     int j1 = this.getXWithOffset(1, i1);
                     i1 = this.getZWithOffset(1, i1);
                     BlockPos blockpos = new BlockPos(j1, l, i1);
                     if (structureBoundingBoxIn.isVecInside(blockpos)) {
                        this.spawnerPlaced = true;
                        worldIn.setBlockState(blockpos, Blocks.mob_spawner.getDefaultState(), 2);
                        TileEntity tileentity = worldIn.getTileEntity(blockpos);
                        if (tileentity instanceof TileEntityMobSpawner) {
                           ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic().setEntityName("CaveSpider");
                        }
                     }
                  }
               }
            }

            for (int i2 = 0; i2 <= 2; i2++) {
               for (int k2 = 0; k2 <= i; k2++) {
                  int b0 = -1;
                  IBlockState iblockstate3 = this.getBlockStateFromPos(worldIn, i2, b0, k2, structureBoundingBoxIn);
                  if (iblockstate3.getBlock().getMaterial() == Material.air) {
                     int b1 = -1;
                     this.setBlockState(worldIn, Blocks.planks.getDefaultState(), i2, b1, k2, structureBoundingBoxIn);
                  }
               }
            }

            boolean flag4 = this.hasRails;
            if (flag4) {
               for (int j2 = 0; j2 <= i; j2++) {
                  IBlockState iblockstate2 = this.getBlockStateFromPos(worldIn, 1, -1, j2, structureBoundingBoxIn);
                  if (iblockstate2.getBlock().getMaterial() != Material.air && iblockstate2.getBlock().isFullBlock()) {
                     this.randomlyPlaceBlock(
                        worldIn, structureBoundingBoxIn, randomIn, 0.7F, 1, 0, j2, Blocks.rail.getStateFromMeta(this.getMetadataWithOffset(Blocks.rail, 0))
                     );
                  }
               }
            }

            return true;
         }
      }
   }

   public static class Cross extends StructureComponent {
      private EnumFacing corridorDirection;
      private boolean isMultipleFloors;

      public Cross() {
      }

      @Override
      public void writeStructureToNBT(NBTTagCompound tagCompound) {
         tagCompound.setBoolean("tf", this.isMultipleFloors);
         tagCompound.setInteger("D", this.corridorDirection.getHorizontalIndex());
      }

      @Override
      public void readStructureFromNBT(NBTTagCompound tagCompound) {
         this.isMultipleFloors = tagCompound.getBoolean("tf");
         this.corridorDirection = EnumFacing.getHorizontal(tagCompound.getInteger("D"));
      }

      public Cross(int typeIn, Random rand, StructureBoundingBox structurebb, EnumFacing facing) {
         super(typeIn);
         this.corridorDirection = facing;
         super.boundingBox = structurebb;
         this.isMultipleFloors = structurebb.getYSize() > 3;
      }

      public static StructureBoundingBox func_175813_a(List<StructureComponent> listIn, Random rand, int x, int y, int z, EnumFacing facing) {
         Object object = null;
         StructureBoundingBox structureboundingbox = new StructureBoundingBox(x, y, z, x, y + 2, z);
         if (rand.nextInt(4) == 0) {
            structureboundingbox.maxY += 4;
         }

         switch (facing) {
            case NORTH:
               structureboundingbox.minX = x - 1;
               structureboundingbox.maxX = x + 3;
               structureboundingbox.minZ = z - 4;
               break;
            case SOUTH:
               structureboundingbox.minX = x - 1;
               structureboundingbox.maxX = x + 3;
               structureboundingbox.maxZ = z + 4;
               break;
            case WEST:
               structureboundingbox.minX = x - 4;
               structureboundingbox.minZ = z - 1;
               structureboundingbox.maxZ = z + 3;
               break;
            case EAST:
               structureboundingbox.maxX = x + 4;
               structureboundingbox.minZ = z - 1;
               structureboundingbox.maxZ = z + 3;
         }

         return StructureComponent.findIntersecting(listIn, structureboundingbox) != null ? null : structureboundingbox;
      }

      @Override
      public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
         int i = this.getComponentType();
         switch (this.corridorDirection) {
            case NORTH:
               StructureMineshaftPieces.func_175890_b(
                  componentIn, listIn, rand, super.boundingBox.minX + 1, super.boundingBox.minY, super.boundingBox.minZ - 1, EnumFacing.NORTH, i
               );
               StructureMineshaftPieces.func_175890_b(
                  componentIn, listIn, rand, super.boundingBox.minX - 1, super.boundingBox.minY, super.boundingBox.minZ + 1, EnumFacing.WEST, i
               );
               StructureMineshaftPieces.func_175890_b(
                  componentIn, listIn, rand, super.boundingBox.maxX + 1, super.boundingBox.minY, super.boundingBox.minZ + 1, EnumFacing.EAST, i
               );
               break;
            case SOUTH:
               StructureMineshaftPieces.func_175890_b(
                  componentIn, listIn, rand, super.boundingBox.minX + 1, super.boundingBox.minY, super.boundingBox.maxZ + 1, EnumFacing.SOUTH, i
               );
               StructureMineshaftPieces.func_175890_b(
                  componentIn, listIn, rand, super.boundingBox.minX - 1, super.boundingBox.minY, super.boundingBox.minZ + 1, EnumFacing.WEST, i
               );
               StructureMineshaftPieces.func_175890_b(
                  componentIn, listIn, rand, super.boundingBox.maxX + 1, super.boundingBox.minY, super.boundingBox.minZ + 1, EnumFacing.EAST, i
               );
               break;
            case WEST:
               StructureMineshaftPieces.func_175890_b(
                  componentIn, listIn, rand, super.boundingBox.minX + 1, super.boundingBox.minY, super.boundingBox.minZ - 1, EnumFacing.NORTH, i
               );
               StructureMineshaftPieces.func_175890_b(
                  componentIn, listIn, rand, super.boundingBox.minX + 1, super.boundingBox.minY, super.boundingBox.maxZ + 1, EnumFacing.SOUTH, i
               );
               StructureMineshaftPieces.func_175890_b(
                  componentIn, listIn, rand, super.boundingBox.minX - 1, super.boundingBox.minY, super.boundingBox.minZ + 1, EnumFacing.WEST, i
               );
               break;
            case EAST:
               StructureMineshaftPieces.func_175890_b(
                  componentIn, listIn, rand, super.boundingBox.minX + 1, super.boundingBox.minY, super.boundingBox.minZ - 1, EnumFacing.NORTH, i
               );
               StructureMineshaftPieces.func_175890_b(
                  componentIn, listIn, rand, super.boundingBox.minX + 1, super.boundingBox.minY, super.boundingBox.maxZ + 1, EnumFacing.SOUTH, i
               );
               StructureMineshaftPieces.func_175890_b(
                  componentIn, listIn, rand, super.boundingBox.maxX + 1, super.boundingBox.minY, super.boundingBox.minZ + 1, EnumFacing.EAST, i
               );
         }

         boolean flag = this.isMultipleFloors;
         if (flag) {
            boolean flag1 = rand.nextBoolean();
            if (flag1) {
               StructureMineshaftPieces.func_175890_b(
                  componentIn, listIn, rand, super.boundingBox.minX + 1, super.boundingBox.minY + 3 + 1, super.boundingBox.minZ - 1, EnumFacing.NORTH, i
               );
            }

            boolean flag2 = rand.nextBoolean();
            if (flag2) {
               StructureMineshaftPieces.func_175890_b(
                  componentIn, listIn, rand, super.boundingBox.minX - 1, super.boundingBox.minY + 3 + 1, super.boundingBox.minZ + 1, EnumFacing.WEST, i
               );
            }

            boolean flag3 = rand.nextBoolean();
            if (flag3) {
               StructureMineshaftPieces.func_175890_b(
                  componentIn, listIn, rand, super.boundingBox.maxX + 1, super.boundingBox.minY + 3 + 1, super.boundingBox.minZ + 1, EnumFacing.EAST, i
               );
            }

            if (rand.nextBoolean()) {
               StructureMineshaftPieces.func_175890_b(
                  componentIn, listIn, rand, super.boundingBox.minX + 1, super.boundingBox.minY + 3 + 1, super.boundingBox.maxZ + 1, EnumFacing.SOUTH, i
               );
            }
         }
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         boolean flag = this.isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn);
         if (flag) {
            return false;
         } else {
            if (this.isMultipleFloors) {
               this.fillWithBlocks(
                  worldIn,
                  structureBoundingBoxIn,
                  super.boundingBox.minX + 1,
                  super.boundingBox.minY,
                  super.boundingBox.minZ,
                  super.boundingBox.maxX - 1,
                  super.boundingBox.minY + 3 - 1,
                  super.boundingBox.maxZ,
                  Blocks.air.getDefaultState(),
                  Blocks.air.getDefaultState(),
                  false
               );
               this.fillWithBlocks(
                  worldIn,
                  structureBoundingBoxIn,
                  super.boundingBox.minX,
                  super.boundingBox.minY,
                  super.boundingBox.minZ + 1,
                  super.boundingBox.maxX,
                  super.boundingBox.minY + 3 - 1,
                  super.boundingBox.maxZ - 1,
                  Blocks.air.getDefaultState(),
                  Blocks.air.getDefaultState(),
                  false
               );
               this.fillWithBlocks(
                  worldIn,
                  structureBoundingBoxIn,
                  super.boundingBox.minX + 1,
                  super.boundingBox.maxY - 2,
                  super.boundingBox.minZ,
                  super.boundingBox.maxX - 1,
                  super.boundingBox.maxY,
                  super.boundingBox.maxZ,
                  Blocks.air.getDefaultState(),
                  Blocks.air.getDefaultState(),
                  false
               );
               this.fillWithBlocks(
                  worldIn,
                  structureBoundingBoxIn,
                  super.boundingBox.minX,
                  super.boundingBox.maxY - 2,
                  super.boundingBox.minZ + 1,
                  super.boundingBox.maxX,
                  super.boundingBox.maxY,
                  super.boundingBox.maxZ - 1,
                  Blocks.air.getDefaultState(),
                  Blocks.air.getDefaultState(),
                  false
               );
               this.fillWithBlocks(
                  worldIn,
                  structureBoundingBoxIn,
                  super.boundingBox.minX + 1,
                  super.boundingBox.minY + 3,
                  super.boundingBox.minZ + 1,
                  super.boundingBox.maxX - 1,
                  super.boundingBox.minY + 3,
                  super.boundingBox.maxZ - 1,
                  Blocks.air.getDefaultState(),
                  Blocks.air.getDefaultState(),
                  false
               );
            } else {
               this.fillWithBlocks(
                  worldIn,
                  structureBoundingBoxIn,
                  super.boundingBox.minX + 1,
                  super.boundingBox.minY,
                  super.boundingBox.minZ,
                  super.boundingBox.maxX - 1,
                  super.boundingBox.maxY,
                  super.boundingBox.maxZ,
                  Blocks.air.getDefaultState(),
                  Blocks.air.getDefaultState(),
                  false
               );
               this.fillWithBlocks(
                  worldIn,
                  structureBoundingBoxIn,
                  super.boundingBox.minX,
                  super.boundingBox.minY,
                  super.boundingBox.minZ + 1,
                  super.boundingBox.maxX,
                  super.boundingBox.maxY,
                  super.boundingBox.maxZ - 1,
                  Blocks.air.getDefaultState(),
                  Blocks.air.getDefaultState(),
                  false
               );
            }

            this.fillWithBlocks(
               worldIn,
               structureBoundingBoxIn,
               super.boundingBox.minX + 1,
               super.boundingBox.minY,
               super.boundingBox.minZ + 1,
               super.boundingBox.minX + 1,
               super.boundingBox.maxY,
               super.boundingBox.minZ + 1,
               Blocks.planks.getDefaultState(),
               Blocks.air.getDefaultState(),
               false
            );
            this.fillWithBlocks(
               worldIn,
               structureBoundingBoxIn,
               super.boundingBox.minX + 1,
               super.boundingBox.minY,
               super.boundingBox.maxZ - 1,
               super.boundingBox.minX + 1,
               super.boundingBox.maxY,
               super.boundingBox.maxZ - 1,
               Blocks.planks.getDefaultState(),
               Blocks.air.getDefaultState(),
               false
            );
            this.fillWithBlocks(
               worldIn,
               structureBoundingBoxIn,
               super.boundingBox.maxX - 1,
               super.boundingBox.minY,
               super.boundingBox.minZ + 1,
               super.boundingBox.maxX - 1,
               super.boundingBox.maxY,
               super.boundingBox.minZ + 1,
               Blocks.planks.getDefaultState(),
               Blocks.air.getDefaultState(),
               false
            );
            this.fillWithBlocks(
               worldIn,
               structureBoundingBoxIn,
               super.boundingBox.maxX - 1,
               super.boundingBox.minY,
               super.boundingBox.maxZ - 1,
               super.boundingBox.maxX - 1,
               super.boundingBox.maxY,
               super.boundingBox.maxZ - 1,
               Blocks.planks.getDefaultState(),
               Blocks.air.getDefaultState(),
               false
            );

            for (int i = super.boundingBox.minX; i <= super.boundingBox.maxX; i++) {
               int k = super.boundingBox.minZ;

               for (int j = k; j <= super.boundingBox.maxZ; j++) {
                  if (this.getBlockStateFromPos(worldIn, i, super.boundingBox.minY - 1, j, structureBoundingBoxIn).getBlock().getMaterial() == Material.air) {
                     this.setBlockState(worldIn, Blocks.planks.getDefaultState(), i, super.boundingBox.minY - 1, j, structureBoundingBoxIn);
                  }
               }
            }

            return true;
         }
      }
   }

   public static class Room extends StructureComponent {
      private List<StructureBoundingBox> roomsLinkedToTheRoom = Lists.newLinkedList();

      public Room() {
      }

      public Room(int typeIn, Random rand, int x, int z) {
         super(typeIn);
         super.boundingBox = new StructureBoundingBox(x, 50, z, x + 7 + rand.nextInt(6), 54 + rand.nextInt(6), z + 7 + rand.nextInt(6));
      }

      @Override
      public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
         int i = this.getComponentType();
         int j = super.boundingBox.getYSize() - 3 - 1;
         if (j <= 0) {
            j = 1;
         }

         int k = 0;

         while (k < super.boundingBox.getXSize()) {
            k += rand.nextInt(super.boundingBox.getXSize());
            int l2 = k + 3;
            int i1 = super.boundingBox.getXSize();
            int l = l2;
            if (l > i1) {
               break;
            }

            StructureComponent structurecomponent = StructureMineshaftPieces.func_175890_b(
               componentIn,
               listIn,
               rand,
               super.boundingBox.minX + k,
               super.boundingBox.minY + rand.nextInt(j) + 1,
               super.boundingBox.minZ - 1,
               EnumFacing.NORTH,
               i
            );
            if (structurecomponent != null) {
               StructureBoundingBox structureboundingbox = structurecomponent.getBoundingBox();
               this.roomsLinkedToTheRoom
                  .add(
                     new StructureBoundingBox(
                        structureboundingbox.minX,
                        structureboundingbox.minY,
                        super.boundingBox.minZ,
                        structureboundingbox.maxX,
                        structureboundingbox.maxY,
                        super.boundingBox.minZ + 1
                     )
                  );
            }

            k += 4;
         }

         k = 0;

         while (k < super.boundingBox.getXSize()) {
            k += rand.nextInt(super.boundingBox.getXSize());
            int j2 = k + 3;
            int k1 = super.boundingBox.getXSize();
            int j1 = j2;
            if (j1 > k1) {
               break;
            }

            StructureComponent structurecomponent1 = StructureMineshaftPieces.func_175890_b(
               componentIn,
               listIn,
               rand,
               super.boundingBox.minX + k,
               super.boundingBox.minY + rand.nextInt(j) + 1,
               super.boundingBox.maxZ + 1,
               EnumFacing.SOUTH,
               i
            );
            if (structurecomponent1 != null) {
               StructureBoundingBox structureboundingbox1 = structurecomponent1.getBoundingBox();
               this.roomsLinkedToTheRoom
                  .add(
                     new StructureBoundingBox(
                        structureboundingbox1.minX,
                        structureboundingbox1.minY,
                        super.boundingBox.maxZ - 1,
                        structureboundingbox1.maxX,
                        structureboundingbox1.maxY,
                        super.boundingBox.maxZ
                     )
                  );
            }

            k += 4;
         }

         k = 0;

         while (k < super.boundingBox.getZSize()) {
            k += rand.nextInt(super.boundingBox.getZSize());
            int k2 = k + 3;
            int i2 = super.boundingBox.getZSize();
            int l1 = k2;
            if (l1 > i2) {
               break;
            }

            StructureComponent structurecomponent2 = StructureMineshaftPieces.func_175890_b(
               componentIn,
               listIn,
               rand,
               super.boundingBox.minX - 1,
               super.boundingBox.minY + rand.nextInt(j) + 1,
               super.boundingBox.minZ + k,
               EnumFacing.WEST,
               i
            );
            if (structurecomponent2 != null) {
               StructureBoundingBox structureboundingbox2 = structurecomponent2.getBoundingBox();
               this.roomsLinkedToTheRoom
                  .add(
                     new StructureBoundingBox(
                        super.boundingBox.minX,
                        structureboundingbox2.minY,
                        structureboundingbox2.minZ,
                        super.boundingBox.minX + 1,
                        structureboundingbox2.maxY,
                        structureboundingbox2.maxZ
                     )
                  );
            }

            k += 4;
         }

         k = 0;

         while (k < super.boundingBox.getZSize()) {
            k += rand.nextInt(super.boundingBox.getZSize());
            if (k + 3 > super.boundingBox.getZSize()) {
               break;
            }

            StructureComponent structurecomponent3 = StructureMineshaftPieces.func_175890_b(
               componentIn,
               listIn,
               rand,
               super.boundingBox.maxX + 1,
               super.boundingBox.minY + rand.nextInt(j) + 1,
               super.boundingBox.minZ + k,
               EnumFacing.EAST,
               i
            );
            if (structurecomponent3 != null) {
               StructureBoundingBox structureboundingbox3 = structurecomponent3.getBoundingBox();
               this.roomsLinkedToTheRoom
                  .add(
                     new StructureBoundingBox(
                        super.boundingBox.maxX - 1,
                        structureboundingbox3.minY,
                        structureboundingbox3.minZ,
                        super.boundingBox.maxX,
                        structureboundingbox3.maxY,
                        structureboundingbox3.maxZ
                     )
                  );
            }

            k += 4;
         }
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         if (this.isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn)) {
            return false;
         } else {
            this.fillWithBlocks(
               worldIn,
               structureBoundingBoxIn,
               super.boundingBox.minX,
               super.boundingBox.minY,
               super.boundingBox.minZ,
               super.boundingBox.maxX,
               super.boundingBox.minY,
               super.boundingBox.maxZ,
               Blocks.dirt.getDefaultState(),
               Blocks.air.getDefaultState(),
               true
            );
            this.fillWithBlocks(
               worldIn,
               structureBoundingBoxIn,
               super.boundingBox.minX,
               super.boundingBox.minY + 1,
               super.boundingBox.minZ,
               super.boundingBox.maxX,
               Math.min(super.boundingBox.minY + 3, super.boundingBox.maxY),
               super.boundingBox.maxZ,
               Blocks.air.getDefaultState(),
               Blocks.air.getDefaultState(),
               false
            );

            for (StructureBoundingBox structureboundingbox : this.roomsLinkedToTheRoom) {
               this.fillWithBlocks(
                  worldIn,
                  structureBoundingBoxIn,
                  structureboundingbox.minX,
                  structureboundingbox.maxY - 2,
                  structureboundingbox.minZ,
                  structureboundingbox.maxX,
                  structureboundingbox.maxY,
                  structureboundingbox.maxZ,
                  Blocks.air.getDefaultState(),
                  Blocks.air.getDefaultState(),
                  false
               );
            }

            this.randomlyRareFillWithBlocks(
               worldIn,
               structureBoundingBoxIn,
               super.boundingBox.minX,
               super.boundingBox.minY + 4,
               super.boundingBox.minZ,
               super.boundingBox.maxX,
               super.boundingBox.maxY,
               super.boundingBox.maxZ,
               Blocks.air.getDefaultState(),
               false
            );
            return true;
         }
      }

      @Override
      public void func_181138_a(int i, int j, int k) {
         super.func_181138_a(i, j, k);

         for (StructureBoundingBox structureboundingbox : this.roomsLinkedToTheRoom) {
            structureboundingbox.offset(i, j, k);
         }
      }

      @Override
      public void writeStructureToNBT(NBTTagCompound tagCompound) {
         Object object = null;
         NBTTagList nbttaglist = new NBTTagList();

         for (StructureBoundingBox structureboundingbox : this.roomsLinkedToTheRoom) {
            nbttaglist.appendTag(structureboundingbox.toNBTTagIntArray());
         }

         tagCompound.setTag("Entrances", nbttaglist);
      }

      @Override
      public void readStructureFromNBT(NBTTagCompound tagCompound) {
         NBTTagList nbttaglist = tagCompound.getTagList("Entrances", 11);

         for (int i = 0; i < nbttaglist.tagCount(); i++) {
            this.roomsLinkedToTheRoom.add(new StructureBoundingBox(nbttaglist.getIntArrayAt(i)));
         }
      }
   }

   public static class Stairs extends StructureComponent {
      public Stairs() {
      }

      public Stairs(int typeIn, Random rand, StructureBoundingBox structurebb, EnumFacing facing) {
         super(typeIn);
         super.coordBaseMode = facing;
         super.boundingBox = structurebb;
      }

      @Override
      public void writeStructureToNBT(NBTTagCompound tagCompound) {
      }

      @Override
      public void readStructureFromNBT(NBTTagCompound tagCompound) {
      }

      public static StructureBoundingBox func_175812_a(List<StructureComponent> listIn, Random rand, int x, int y, int z, EnumFacing facing) {
         Object object = null;
         StructureBoundingBox structureboundingbox = new StructureBoundingBox(x, y - 5, z, x, y + 2, z);
         switch (facing) {
            case NORTH:
               structureboundingbox.maxX = x + 2;
               structureboundingbox.minZ = z - 8;
               break;
            case SOUTH:
               structureboundingbox.maxX = x + 2;
               structureboundingbox.maxZ = z + 8;
               break;
            case WEST:
               structureboundingbox.minX = x - 8;
               structureboundingbox.maxZ = z + 2;
               break;
            case EAST:
               structureboundingbox.maxX = x + 8;
               structureboundingbox.maxZ = z + 2;
         }

         return StructureComponent.findIntersecting(listIn, structureboundingbox) != null ? null : structureboundingbox;
      }

      @Override
      public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
         int i = this.getComponentType();
         if (super.coordBaseMode != null) {
            switch (super.coordBaseMode) {
               case NORTH:
                  StructureMineshaftPieces.func_175890_b(
                     componentIn, listIn, rand, super.boundingBox.minX, super.boundingBox.minY, super.boundingBox.minZ - 1, EnumFacing.NORTH, i
                  );
                  break;
               case SOUTH:
                  StructureMineshaftPieces.func_175890_b(
                     componentIn, listIn, rand, super.boundingBox.minX, super.boundingBox.minY, super.boundingBox.maxZ + 1, EnumFacing.SOUTH, i
                  );
                  break;
               case WEST:
                  StructureMineshaftPieces.func_175890_b(
                     componentIn, listIn, rand, super.boundingBox.minX - 1, super.boundingBox.minY, super.boundingBox.minZ, EnumFacing.WEST, i
                  );
                  break;
               case EAST:
                  StructureMineshaftPieces.func_175890_b(
                     componentIn, listIn, rand, super.boundingBox.maxX + 1, super.boundingBox.minY, super.boundingBox.minZ, EnumFacing.EAST, i
                  );
            }
         }
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         boolean flag = this.isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn);
         if (flag) {
            return false;
         } else {
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 2, 7, 1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 7, 2, 2, 8, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);

            for (int i = 0; i < 5; i++) {
               int j = 5 - i;
               this.fillWithBlocks(
                  worldIn,
                  structureBoundingBoxIn,
                  0,
                  j - (i < 4 ? 1 : 0),
                  2 + i,
                  2,
                  7 - i,
                  2 + i,
                  Blocks.air.getDefaultState(),
                  Blocks.air.getDefaultState(),
                  false
               );
            }

            return true;
         }
      }
   }
}
