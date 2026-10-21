package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;

public class StructureVillagePieces {
   public static void registerVillagePieces() {
      MapGenStructureIO.registerStructureComponent(StructureVillagePieces.House1.class, "ViBH");
      MapGenStructureIO.registerStructureComponent(StructureVillagePieces.Field1.class, "ViDF");
      MapGenStructureIO.registerStructureComponent(StructureVillagePieces.Field2.class, "ViF");
      MapGenStructureIO.registerStructureComponent(StructureVillagePieces.Torch.class, "ViL");
      MapGenStructureIO.registerStructureComponent(StructureVillagePieces.Hall.class, "ViPH");
      MapGenStructureIO.registerStructureComponent(StructureVillagePieces.House4Garden.class, "ViSH");
      MapGenStructureIO.registerStructureComponent(StructureVillagePieces.WoodHut.class, "ViSmH");
      MapGenStructureIO.registerStructureComponent(StructureVillagePieces.Church.class, "ViST");
      MapGenStructureIO.registerStructureComponent(StructureVillagePieces.House2.class, "ViS");
      MapGenStructureIO.registerStructureComponent(StructureVillagePieces.Start.class, "ViStart");
      MapGenStructureIO.registerStructureComponent(StructureVillagePieces.Path.class, "ViSR");
      MapGenStructureIO.registerStructureComponent(StructureVillagePieces.House3.class, "ViTRH");
      MapGenStructureIO.registerStructureComponent(StructureVillagePieces.Well.class, "ViW");
   }

   public static List<StructureVillagePieces.PieceWeight> getStructureVillageWeightedPieceList(Random randomIn, int size) {
      ArrayList arraylist = Lists.newArrayList();
      Object object = null;
      arraylist.add(
         new StructureVillagePieces.PieceWeight(
            StructureVillagePieces.House4Garden.class, 4, MathHelper.getRandomIntegerInRange(randomIn, 2 + size, 4 + size * 2)
         )
      );
      arraylist.add(
         new StructureVillagePieces.PieceWeight(StructureVillagePieces.Church.class, 20, MathHelper.getRandomIntegerInRange(randomIn, 0 + size, 1 + size))
      );
      arraylist.add(
         new StructureVillagePieces.PieceWeight(StructureVillagePieces.House1.class, 20, MathHelper.getRandomIntegerInRange(randomIn, 0 + size, 2 + size))
      );
      arraylist.add(
         new StructureVillagePieces.PieceWeight(StructureVillagePieces.WoodHut.class, 3, MathHelper.getRandomIntegerInRange(randomIn, 2 + size, 5 + size * 3))
      );
      arraylist.add(
         new StructureVillagePieces.PieceWeight(StructureVillagePieces.Hall.class, 15, MathHelper.getRandomIntegerInRange(randomIn, 0 + size, 2 + size))
      );
      arraylist.add(
         new StructureVillagePieces.PieceWeight(StructureVillagePieces.Field1.class, 3, MathHelper.getRandomIntegerInRange(randomIn, 1 + size, 4 + size))
      );
      arraylist.add(
         new StructureVillagePieces.PieceWeight(StructureVillagePieces.Field2.class, 3, MathHelper.getRandomIntegerInRange(randomIn, 2 + size, 4 + size * 2))
      );
      arraylist.add(new StructureVillagePieces.PieceWeight(StructureVillagePieces.House2.class, 15, MathHelper.getRandomIntegerInRange(randomIn, 0, 1 + size)));
      arraylist.add(
         new StructureVillagePieces.PieceWeight(StructureVillagePieces.House3.class, 8, MathHelper.getRandomIntegerInRange(randomIn, 0 + size, 3 + size * 2))
      );
      Iterator iterator = arraylist.iterator();

      while (iterator.hasNext()) {
         if (((StructureVillagePieces.PieceWeight)iterator.next()).villagePiecesLimit == 0) {
            iterator.remove();
         }
      }

      return arraylist;
   }

   private static int func_75079_a(List<StructureVillagePieces.PieceWeight> list) {
      boolean flag = false;
      int i = 0;

      for (StructureVillagePieces.PieceWeight structurevillagepieces$pieceweight : list) {
         int j = structurevillagepieces$pieceweight.villagePiecesLimit;
         if (j > 0 && structurevillagepieces$pieceweight.villagePiecesSpawned < structurevillagepieces$pieceweight.villagePiecesLimit) {
            flag = true;
         }

         i += structurevillagepieces$pieceweight.villagePieceWeight;
      }

      return flag ? i : -1;
   }

   private static StructureVillagePieces.Village func_176065_a(
      StructureVillagePieces.Start startIn,
      StructureVillagePieces.PieceWeight weight,
      List<StructureComponent> list,
      Random rand,
      int i,
      int j,
      int k,
      EnumFacing facing,
      int l
   ) {
      Class oclass = weight.villagePieceClass;
      Object object = null;
      Class<StructureVillagePieces.House4Garden> oclass1 = StructureVillagePieces.House4Garden.class;
      if (oclass == oclass1) {
         object = StructureVillagePieces.House4Garden.i(startIn, list, rand, i, j, k, facing, l);
      } else {
         Class<StructureVillagePieces.Church> oclass2 = StructureVillagePieces.Church.class;
         if (oclass == oclass2) {
            object = StructureVillagePieces.Church.z(startIn, list, rand, i, j, k, facing, l);
         } else {
            Class<StructureVillagePieces.House1> oclass3 = StructureVillagePieces.House1.class;
            if (oclass == oclass3) {
               object = StructureVillagePieces.House1.i(startIn, list, rand, i, j, k, facing, l);
            } else {
               Class<StructureVillagePieces.WoodHut> oclass4 = StructureVillagePieces.WoodHut.class;
               if (oclass == oclass4) {
                  object = StructureVillagePieces.WoodHut.func_175853_a(startIn, list, rand, i, j, k, facing, l);
               } else {
                  Class<StructureVillagePieces.Hall> oclass5 = StructureVillagePieces.Hall.class;
                  if (oclass == oclass5) {
                     object = StructureVillagePieces.Hall.a(startIn, list, rand, i, j, k, facing, l);
                  } else {
                     Class<StructureVillagePieces.Field1> oclass6 = StructureVillagePieces.Field1.class;
                     if (oclass == oclass6) {
                        object = StructureVillagePieces.Field1.func_175851_a(startIn, list, rand, i, j, k, facing, l);
                     } else {
                        Class<StructureVillagePieces.Field2> oclass7 = StructureVillagePieces.Field2.class;
                        if (oclass == oclass7) {
                           object = StructureVillagePieces.Field2.func_175852_a(startIn, list, rand, i, j, k, facing, l);
                        } else {
                           Class<StructureVillagePieces.House2> oclass8 = StructureVillagePieces.House2.class;
                           if (oclass == oclass8) {
                              object = StructureVillagePieces.House2.func_175855_a(startIn, list, rand, i, j, k, facing, l);
                           } else if (oclass == StructureVillagePieces.House3.class) {
                              object = StructureVillagePieces.House3.func_175849_a(startIn, list, rand, i, j, k, facing, l);
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return (StructureVillagePieces.Village)object;
   }

   private static StructureVillagePieces.Village func_176067_c(
      StructureVillagePieces.Start startIn, List<StructureComponent> list, Random rand, int i, int j, int k, EnumFacing facing, int l
   ) {
      int i1 = func_75079_a(startIn.structureVillageWeightedPieceList);
      if (i1 <= 0) {
         return null;
      } else {
         int j1 = 0;

         while (j1 < 5) {
            j1++;
            int k1 = rand.nextInt(i1);

            for (StructureVillagePieces.PieceWeight structurevillagepieces$pieceweight : startIn.structureVillageWeightedPieceList) {
               k1 -= structurevillagepieces$pieceweight.villagePieceWeight;
               if (k1 < 0) {
                  if (!structurevillagepieces$pieceweight.canSpawnMoreVillagePiecesOfType(l)
                     || structurevillagepieces$pieceweight == startIn.structVillagePieceWeight && startIn.structureVillageWeightedPieceList.size() > 1) {
                     break;
                  }

                  StructureVillagePieces.Village structurevillagepieces$village = func_176065_a(
                     startIn, structurevillagepieces$pieceweight, list, rand, i, j, k, facing, l
                  );
                  if (structurevillagepieces$village != null) {
                     structurevillagepieces$pieceweight.villagePiecesSpawned++;
                     startIn.structVillagePieceWeight = structurevillagepieces$pieceweight;
                     if (!structurevillagepieces$pieceweight.canSpawnMoreVillagePieces()) {
                        startIn.structureVillageWeightedPieceList.remove(structurevillagepieces$pieceweight);
                     }

                     return structurevillagepieces$village;
                  }
               }
            }
         }

         StructureBoundingBox structureboundingbox = StructureVillagePieces.Torch.func_175856_a(startIn, list, rand, i, j, k, facing);
         return structureboundingbox != null ? new StructureVillagePieces.Torch(startIn, l, rand, structureboundingbox, facing) : null;
      }
   }

   private static StructureComponent func_176066_d(
      StructureVillagePieces.Start startIn, List<StructureComponent> list, Random rand, int i, int j, int k, EnumFacing facing, int l
   ) {
      if (l > 50) {
         return null;
      } else {
         int j1 = Math.abs(i - startIn.getBoundingBox().minX);
         if (j1 <= 112 && Math.abs(k - startIn.getBoundingBox().minZ) <= 112) {
            StructureVillagePieces.Village structurevillagepieces$village = func_176067_c(startIn, list, rand, i, j, k, facing, l + 1);
            if (structurevillagepieces$village != null) {
               int i1 = (structurevillagepieces$village.boundingBox.minX + structurevillagepieces$village.boundingBox.maxX) / 2;
               int j1x = (structurevillagepieces$village.boundingBox.minZ + structurevillagepieces$village.boundingBox.maxZ) / 2;
               int k1 = structurevillagepieces$village.boundingBox.maxX - structurevillagepieces$village.boundingBox.minX;
               int l1 = structurevillagepieces$village.boundingBox.maxZ - structurevillagepieces$village.boundingBox.minZ;
               int i1x = k1 > l1 ? k1 : l1;
               boolean flag = startIn.getWorldChunkManager().areBiomesViable(i1, j1x, i1x / 2 + 4, MapGenVillage.villageSpawnBiomes);
               if (flag) {
                  list.add(structurevillagepieces$village);
                  startIn.field_74932_i.add(structurevillagepieces$village);
                  return structurevillagepieces$village;
               }
            }

            return null;
         } else {
            return null;
         }
      }
   }

   private static StructureComponent func_176069_e(
      StructureVillagePieces.Start startIn, List<StructureComponent> list, Random rand, int i, int j, int k, EnumFacing facing, int l
   ) {
      int j1 = 3 + startIn.terrainType;
      if (l > j1) {
         return null;
      } else {
         int k1 = Math.abs(i - startIn.getBoundingBox().minX);
         if (k1 <= 112 && Math.abs(k - startIn.getBoundingBox().minZ) <= 112) {
            StructureBoundingBox structureboundingbox = StructureVillagePieces.Path.func_175848_a(startIn, list, rand, i, j, k, facing);
            if (structureboundingbox != null && structureboundingbox.minY > 10) {
               StructureVillagePieces.Path structurevillagepieces$path = new StructureVillagePieces.Path(startIn, l, rand, structureboundingbox, facing);
               int i1 = (structurevillagepieces$path.boundingBox.minX + structurevillagepieces$path.boundingBox.maxX) / 2;
               int j1x = (structurevillagepieces$path.boundingBox.minZ + structurevillagepieces$path.boundingBox.maxZ) / 2;
               int k1x = structurevillagepieces$path.boundingBox.maxX - structurevillagepieces$path.boundingBox.minX;
               int l1 = structurevillagepieces$path.boundingBox.maxZ - structurevillagepieces$path.boundingBox.minZ;
               int i1x = k1x > l1 ? k1x : l1;
               boolean flag = startIn.getWorldChunkManager().areBiomesViable(i1, j1x, i1x / 2 + 4, MapGenVillage.villageSpawnBiomes);
               if (flag) {
                  list.add(structurevillagepieces$path);
                  startIn.field_74930_j.add(structurevillagepieces$path);
                  return structurevillagepieces$path;
               }
            }

            return null;
         } else {
            return null;
         }
      }
   }

   static StructureComponent access$100(StructureVillagePieces.Start start, List list, Random random, int i, int i2, int i3, EnumFacing enumFacing, int i4) {
      return func_176066_d(start, list, random, i, i2, i3, enumFacing, i4);
   }

   public static class Church extends StructureVillagePieces.Village {
      public Church() {
      }

      public Church(StructureVillagePieces.Start startIn, int i, Random rand, StructureBoundingBox structureBoundingBox, EnumFacing facing) {
         super(startIn, i);
         super.coordBaseMode = facing;
         super.boundingBox = structureBoundingBox;
      }

      public static StructureVillagePieces.Church z(
         StructureVillagePieces.Start startIn, List<StructureComponent> list, Random rand, int i, int j, int k, EnumFacing facing, int l
      ) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, 0, 0, 0, 5, 12, 9, facing);
         return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(list, structureboundingbox) == null
            ? new StructureVillagePieces.Church(startIn, l, rand, structureboundingbox, facing)
            : null;
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         int l = super.field_143015_k;
         if (l < 0) {
            super.field_143015_k = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);
            if (super.field_143015_k < 0) {
               return true;
            }

            super.boundingBox.offset(0, super.field_143015_k - super.boundingBox.maxY + 12 - 1, 0);
         }

         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 3, 3, 7, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 1, 3, 9, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 1, 0, 0, 3, 0, 8, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 1, 1, 0, 3, 10, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 10, 3, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 10, 3, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 0, 4, 0, 4, 7, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 4, 0, 4, 4, 4, 7, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 1, 1, 8, 3, 4, 8, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 1, 5, 4, 3, 10, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 1, 5, 5, 3, 5, 7, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 9, 0, 4, 9, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 4, 0, 4, 4, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 0, 11, 2, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, 11, 2, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 2, 11, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 2, 11, 4, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 1, 1, 6, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 1, 1, 7, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 2, 1, 7, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 3, 1, 6, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 3, 1, 7, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 1, 1, 5, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 2, 1, 6, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 3, 1, 5, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 1)), 1, 2, 7, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 0)), 3, 2, 7, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 3, 2, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 4, 2, 2, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 4, 3, 2, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 6, 2, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 7, 2, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 4, 6, 2, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 4, 7, 2, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 6, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 7, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 6, 4, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 7, 4, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 3, 6, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 4, 3, 6, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 3, 8, structureBoundingBoxIn);
         this.setBlockState(
            worldIn, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, super.coordBaseMode.getOpposite()), 2, 4, 7, structureBoundingBoxIn
         );
         this.setBlockState(
            worldIn, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, super.coordBaseMode.rotateY()), 1, 4, 6, structureBoundingBoxIn
         );
         this.setBlockState(
            worldIn, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, super.coordBaseMode.rotateYCCW()), 3, 4, 6, structureBoundingBoxIn
         );
         this.setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, super.coordBaseMode), 2, 4, 5, structureBoundingBoxIn);
         int i = this.getMetadataWithOffset(Blocks.ladder, 4);

         for (int j = 1; j <= 9; j++) {
            this.setBlockState(worldIn, Blocks.ladder.getStateFromMeta(i), 3, j, 3, structureBoundingBoxIn);
         }

         this.setBlockState(worldIn, Blocks.air.getDefaultState(), 2, 1, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.air.getDefaultState(), 2, 2, 0, structureBoundingBoxIn);
         this.placeDoorCurrentPosition(
            worldIn, structureBoundingBoxIn, randomIn, 2, 1, 0, EnumFacing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, 1))
         );
         Material material2 = this.getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getBlock().getMaterial();
         Material material1 = Material.air;
         Material material = material2;
         if (material == material1 && this.getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getBlock().getMaterial() != Material.air) {
            this.setBlockState(
               worldIn, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 2, 0, -1, structureBoundingBoxIn
            );
         }

         for (int i1 = 0; i1 < 9; i1++) {
            for (int k = 0; k < 5; k++) {
               this.clearCurrentPositionBlocksUpwards(worldIn, k, 12, i1, structureBoundingBoxIn);
               this.replaceAirAndLiquidDownwards(worldIn, Blocks.cobblestone.getDefaultState(), k, -1, i1, structureBoundingBoxIn);
            }
         }

         this.spawnVillagers(worldIn, structureBoundingBoxIn, 2, 1, 2, 1);
         return true;
      }

      @Override
      public int func_180779_c(int i, int j) {
         return 2;
      }
   }

   public static class Field1 extends StructureVillagePieces.Village {
      private Block cropTypeA;
      private Block cropTypeB;
      private Block cropTypeC;
      private Block cropTypeD;

      public Field1() {
      }

      public Field1(StructureVillagePieces.Start startIn, int i, Random rand, StructureBoundingBox structureBoundingBox, EnumFacing facing) {
         super(startIn, i);
         super.coordBaseMode = facing;
         super.boundingBox = structureBoundingBox;
         this.cropTypeA = this.func_151559_a(rand);
         this.cropTypeB = this.func_151559_a(rand);
         this.cropTypeC = this.func_151559_a(rand);
         this.cropTypeD = this.func_151559_a(rand);
      }

      @Override
      public void writeStructureToNBT(NBTTagCompound tagCompound) {
         super.writeStructureToNBT(tagCompound);
         tagCompound.setInteger("CA", Block.blockRegistry.getIDForObject(this.cropTypeA));
         tagCompound.setInteger("CB", Block.blockRegistry.getIDForObject(this.cropTypeB));
         tagCompound.setInteger("CC", Block.blockRegistry.getIDForObject(this.cropTypeC));
         tagCompound.setInteger("CD", Block.blockRegistry.getIDForObject(this.cropTypeD));
      }

      @Override
      public void readStructureFromNBT(NBTTagCompound tagCompound) {
         super.readStructureFromNBT(tagCompound);
         this.cropTypeA = Block.getBlockById(tagCompound.getInteger("CA"));
         this.cropTypeB = Block.getBlockById(tagCompound.getInteger("CB"));
         this.cropTypeC = Block.getBlockById(tagCompound.getInteger("CC"));
         this.cropTypeD = Block.getBlockById(tagCompound.getInteger("CD"));
      }

      private Block func_151559_a(Random rand) {
         switch (rand.nextInt(5)) {
            case 0:
               return Blocks.carrots;
            case 1:
               return Blocks.potatoes;
            default:
               return Blocks.wheat;
         }
      }

      public static StructureVillagePieces.Field1 func_175851_a(
         StructureVillagePieces.Start startIn, List<StructureComponent> list, Random rand, int i, int j, int k, EnumFacing facing, int l
      ) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, 0, 0, 0, 13, 4, 9, facing);
         return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(list, structureboundingbox) == null
            ? new StructureVillagePieces.Field1(startIn, l, rand, structureboundingbox, facing)
            : null;
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         int k = super.field_143015_k;
         if (k < 0) {
            super.field_143015_k = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);
            if (super.field_143015_k < 0) {
               return true;
            }

            super.boundingBox.offset(0, super.field_143015_k - super.boundingBox.maxY + 4 - 1, 0);
         }

         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 12, 4, 8, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 2, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 1, 5, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 0, 1, 8, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 0, 1, 11, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 0, 0, 6, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 0, 0, 12, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 11, 0, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 8, 11, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 0, 1, 3, 0, 7, Blocks.water.getDefaultState(), Blocks.water.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 0, 1, 9, 0, 7, Blocks.water.getDefaultState(), Blocks.water.getDefaultState(), false);

         for (int i = 1; i <= 7; i++) {
            this.setBlockState(worldIn, this.cropTypeA.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 1, 1, i, structureBoundingBoxIn);
            this.setBlockState(worldIn, this.cropTypeA.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 2, 1, i, structureBoundingBoxIn);
            this.setBlockState(worldIn, this.cropTypeB.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 4, 1, i, structureBoundingBoxIn);
            this.setBlockState(worldIn, this.cropTypeB.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 5, 1, i, structureBoundingBoxIn);
            this.setBlockState(worldIn, this.cropTypeC.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 7, 1, i, structureBoundingBoxIn);
            this.setBlockState(worldIn, this.cropTypeC.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 8, 1, i, structureBoundingBoxIn);
            this.setBlockState(worldIn, this.cropTypeD.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 10, 1, i, structureBoundingBoxIn);
            this.setBlockState(worldIn, this.cropTypeD.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 11, 1, i, structureBoundingBoxIn);
         }

         for (int l = 0; l < 9; l++) {
            for (int j = 0; j < 13; j++) {
               this.clearCurrentPositionBlocksUpwards(worldIn, j, 4, l, structureBoundingBoxIn);
               this.replaceAirAndLiquidDownwards(worldIn, Blocks.dirt.getDefaultState(), j, -1, l, structureBoundingBoxIn);
            }
         }

         return true;
      }
   }

   public static class Field2 extends StructureVillagePieces.Village {
      private Block cropTypeA;
      private Block cropTypeB;

      public Field2() {
      }

      public Field2(StructureVillagePieces.Start startIn, int i, Random rand, StructureBoundingBox structureBoundingBox, EnumFacing facing) {
         super(startIn, i);
         super.coordBaseMode = facing;
         super.boundingBox = structureBoundingBox;
         this.cropTypeA = this.func_151560_a(rand);
         this.cropTypeB = this.func_151560_a(rand);
      }

      @Override
      public void writeStructureToNBT(NBTTagCompound tagCompound) {
         super.writeStructureToNBT(tagCompound);
         tagCompound.setInteger("CA", Block.blockRegistry.getIDForObject(this.cropTypeA));
         tagCompound.setInteger("CB", Block.blockRegistry.getIDForObject(this.cropTypeB));
      }

      @Override
      public void readStructureFromNBT(NBTTagCompound tagCompound) {
         super.readStructureFromNBT(tagCompound);
         this.cropTypeA = Block.getBlockById(tagCompound.getInteger("CA"));
         this.cropTypeB = Block.getBlockById(tagCompound.getInteger("CB"));
      }

      private Block func_151560_a(Random rand) {
         switch (rand.nextInt(5)) {
            case 0:
               return Blocks.carrots;
            case 1:
               return Blocks.potatoes;
            default:
               return Blocks.wheat;
         }
      }

      public static StructureVillagePieces.Field2 func_175852_a(
         StructureVillagePieces.Start startIn, List<StructureComponent> list, Random rand, int i, int j, int k, EnumFacing facing, int l
      ) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, 0, 0, 0, 7, 4, 9, facing);
         return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(list, structureboundingbox) == null
            ? new StructureVillagePieces.Field2(startIn, l, rand, structureboundingbox, facing)
            : null;
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         int k = super.field_143015_k;
         if (k < 0) {
            super.field_143015_k = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);
            if (super.field_143015_k < 0) {
               return true;
            }

            super.boundingBox.offset(0, super.field_143015_k - super.boundingBox.maxY + 4 - 1, 0);
         }

         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 6, 4, 8, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 2, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 1, 5, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 0, 0, 6, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 5, 0, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 8, 5, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 0, 1, 3, 0, 7, Blocks.water.getDefaultState(), Blocks.water.getDefaultState(), false);

         for (int i = 1; i <= 7; i++) {
            this.setBlockState(worldIn, this.cropTypeA.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 1, 1, i, structureBoundingBoxIn);
            this.setBlockState(worldIn, this.cropTypeA.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 2, 1, i, structureBoundingBoxIn);
            this.setBlockState(worldIn, this.cropTypeB.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 4, 1, i, structureBoundingBoxIn);
            this.setBlockState(worldIn, this.cropTypeB.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 5, 1, i, structureBoundingBoxIn);
         }

         for (int l = 0; l < 9; l++) {
            for (int j = 0; j < 7; j++) {
               this.clearCurrentPositionBlocksUpwards(worldIn, j, 4, l, structureBoundingBoxIn);
               this.replaceAirAndLiquidDownwards(worldIn, Blocks.dirt.getDefaultState(), j, -1, l, structureBoundingBoxIn);
            }
         }

         return true;
      }
   }

   public static class Hall extends StructureVillagePieces.Village {
      public Hall() {
      }

      public Hall(StructureVillagePieces.Start startIn, int i, Random rand, StructureBoundingBox structureBoundingBox, EnumFacing facing) {
         super(startIn, i);
         super.coordBaseMode = facing;
         super.boundingBox = structureBoundingBox;
      }

      public static StructureVillagePieces.Hall a(
         StructureVillagePieces.Start startIn, List<StructureComponent> list, Random rand, int i, int j, int k, EnumFacing facing, int l
      ) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, 0, 0, 0, 9, 7, 11, facing);
         return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(list, structureboundingbox) == null
            ? new StructureVillagePieces.Hall(startIn, l, rand, structureboundingbox, facing)
            : null;
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         int i1 = super.field_143015_k;
         if (i1 < 0) {
            super.field_143015_k = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);
            if (super.field_143015_k < 0) {
               return true;
            }

            super.boundingBox.offset(0, super.field_143015_k - super.boundingBox.maxY + 7 - 1, 0);
         }

         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 7, 4, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 6, 8, 4, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 0, 6, 8, 0, 10, Blocks.dirt.getDefaultState(), Blocks.dirt.getDefaultState(), false);
         this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 6, 0, 6, structureBoundingBoxIn);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 6, 2, 1, 10, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 1, 6, 8, 1, 10, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 10, 7, 1, 10, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 7, 0, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 3, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 8, 0, 0, 8, 3, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 1, 0, 0, 7, 1, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 1, 0, 5, 7, 1, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 7, 3, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 5, 7, 3, 5, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 1, 8, 4, 1, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 4, 8, 4, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 2, 8, 5, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 0, 4, 2, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 0, 4, 3, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 8, 4, 2, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 8, 4, 3, structureBoundingBoxIn);
         int i = this.getMetadataWithOffset(Blocks.oak_stairs, 3);
         int j = this.getMetadataWithOffset(Blocks.oak_stairs, 2);

         for (int k = -1; k <= 2; k++) {
            for (int l = 0; l <= 8; l++) {
               this.setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(i), l, 4 + k, k, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(j), l, 4 + k, 5 - k, structureBoundingBoxIn);
            }
         }

         this.setBlockState(worldIn, Blocks.log.getDefaultState(), 0, 2, 1, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.log.getDefaultState(), 0, 2, 4, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.log.getDefaultState(), 8, 2, 1, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.log.getDefaultState(), 8, 2, 4, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 3, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 2, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 3, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 2, 5, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 3, 2, 5, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 5, 2, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 6, 2, 5, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 2, 1, 3, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.wooden_pressure_plate.getDefaultState(), 2, 2, 3, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 1, 1, 4, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.oak_stairs, 3)), 2, 1, 4, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.oak_stairs, 1)), 1, 1, 3, structureBoundingBoxIn);
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 5, 0, 1, 7, 0, 3, Blocks.double_stone_slab.getDefaultState(), Blocks.double_stone_slab.getDefaultState(), false
         );
         this.setBlockState(worldIn, Blocks.double_stone_slab.getDefaultState(), 6, 1, 1, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.double_stone_slab.getDefaultState(), 6, 1, 2, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.air.getDefaultState(), 2, 1, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.air.getDefaultState(), 2, 2, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, super.coordBaseMode), 2, 3, 1, structureBoundingBoxIn);
         this.placeDoorCurrentPosition(
            worldIn, structureBoundingBoxIn, randomIn, 2, 1, 0, EnumFacing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, 1))
         );
         if (this.getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getBlock().getMaterial() == Material.air
            && this.getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getBlock().getMaterial() != Material.air) {
            this.setBlockState(
               worldIn, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 2, 0, -1, structureBoundingBoxIn
            );
         }

         this.setBlockState(worldIn, Blocks.air.getDefaultState(), 6, 1, 5, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.air.getDefaultState(), 6, 2, 5, structureBoundingBoxIn);
         this.setBlockState(
            worldIn, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, super.coordBaseMode.getOpposite()), 6, 3, 4, structureBoundingBoxIn
         );
         this.placeDoorCurrentPosition(
            worldIn, structureBoundingBoxIn, randomIn, 6, 1, 5, EnumFacing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, 1))
         );

         for (int j1 = 0; j1 < 5; j1++) {
            for (int k1 = 0; k1 < 9; k1++) {
               this.clearCurrentPositionBlocksUpwards(worldIn, k1, 7, j1, structureBoundingBoxIn);
               this.replaceAirAndLiquidDownwards(worldIn, Blocks.cobblestone.getDefaultState(), k1, -1, j1, structureBoundingBoxIn);
            }
         }

         this.spawnVillagers(worldIn, structureBoundingBoxIn, 4, 1, 2, 2);
         return true;
      }

      @Override
      public int func_180779_c(int i, int j) {
         return i == 0 ? 4 : super.func_180779_c(i, j);
      }
   }

   public static class House1 extends StructureVillagePieces.Village {
      public House1() {
      }

      public House1(StructureVillagePieces.Start startIn, int i, Random rand, StructureBoundingBox structureBoundingBox, EnumFacing facing) {
         super(startIn, i);
         super.coordBaseMode = facing;
         super.boundingBox = structureBoundingBox;
      }

      public static StructureVillagePieces.House1 i(
         StructureVillagePieces.Start startIn, List<StructureComponent> list, Random rand, int i, int j, int k, EnumFacing facing, int l
      ) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, 0, 0, 0, 9, 9, 6, facing);
         return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(list, structureboundingbox) == null
            ? new StructureVillagePieces.House1(startIn, l, rand, structureboundingbox, facing)
            : null;
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         int j1 = super.field_143015_k;
         if (j1 < 0) {
            super.field_143015_k = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);
            if (super.field_143015_k < 0) {
               return true;
            }

            super.boundingBox.offset(0, super.field_143015_k - super.boundingBox.maxY + 9 - 1, 0);
         }

         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 7, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 0, 0, 8, 0, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 5, 0, 8, 5, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 6, 1, 8, 6, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 7, 2, 8, 7, 3, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         int i = this.getMetadataWithOffset(Blocks.oak_stairs, 3);
         int j = this.getMetadataWithOffset(Blocks.oak_stairs, 2);

         for (int k = -1; k <= 2; k++) {
            for (int l = 0; l <= 8; l++) {
               this.setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(i), l, 6 + k, k, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(j), l, 6 + k, 5 - k, structureBoundingBoxIn);
            }
         }

         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 1, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 1, 1, 5, 8, 1, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 8, 1, 0, 8, 1, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 2, 1, 0, 7, 1, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 4, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 2, 5, 0, 4, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 8, 2, 5, 8, 4, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 8, 2, 0, 8, 4, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 1, 0, 4, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 5, 7, 4, 5, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 2, 1, 8, 4, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 7, 4, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 4, 2, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 5, 2, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 6, 2, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 4, 3, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 5, 3, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 6, 3, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 3, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 3, 2, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 3, 3, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 2, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 3, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 3, 2, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 3, 3, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 2, 5, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 3, 2, 5, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 5, 2, 5, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 6, 2, 5, structureBoundingBoxIn);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 1, 7, 4, 1, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 4, 7, 4, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 4, 7, 3, 4, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
         this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 7, 1, 4, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.oak_stairs, 0)), 7, 1, 3, structureBoundingBoxIn);
         int k1 = this.getMetadataWithOffset(Blocks.oak_stairs, 3);
         this.setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(k1), 6, 1, 4, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(k1), 5, 1, 4, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(k1), 4, 1, 4, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(k1), 3, 1, 4, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 6, 1, 3, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.wooden_pressure_plate.getDefaultState(), 6, 2, 3, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 1, 3, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.wooden_pressure_plate.getDefaultState(), 4, 2, 3, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.crafting_table.getDefaultState(), 7, 1, 1, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.air.getDefaultState(), 1, 1, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.air.getDefaultState(), 1, 2, 0, structureBoundingBoxIn);
         this.placeDoorCurrentPosition(
            worldIn, structureBoundingBoxIn, randomIn, 1, 1, 0, EnumFacing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, 1))
         );
         if (this.getBlockStateFromPos(worldIn, 1, 0, -1, structureBoundingBoxIn).getBlock().getMaterial() == Material.air
            && this.getBlockStateFromPos(worldIn, 1, -1, -1, structureBoundingBoxIn).getBlock().getMaterial() != Material.air) {
            this.setBlockState(
               worldIn, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 1, 0, -1, structureBoundingBoxIn
            );
         }

         for (int l1 = 0; l1 < 6; l1++) {
            for (int i1 = 0; i1 < 9; i1++) {
               this.clearCurrentPositionBlocksUpwards(worldIn, i1, 9, l1, structureBoundingBoxIn);
               this.replaceAirAndLiquidDownwards(worldIn, Blocks.cobblestone.getDefaultState(), i1, -1, l1, structureBoundingBoxIn);
            }
         }

         this.spawnVillagers(worldIn, structureBoundingBoxIn, 2, 1, 2, 1);
         return true;
      }

      @Override
      public int func_180779_c(int i, int j) {
         return 1;
      }
   }

   public static class House2 extends StructureVillagePieces.Village {
      private static final List<WeightedRandomChestContent> villageBlacksmithChestContents = Lists.newArrayList(
         new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3),
         new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10),
         new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5),
         new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15),
         new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15),
         new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 5),
         new WeightedRandomChestContent(Items.iron_sword, 0, 1, 1, 5),
         new WeightedRandomChestContent(Items.iron_chestplate, 0, 1, 1, 5),
         new WeightedRandomChestContent(Items.iron_helmet, 0, 1, 1, 5),
         new WeightedRandomChestContent(Items.iron_leggings, 0, 1, 1, 5),
         new WeightedRandomChestContent(Items.iron_boots, 0, 1, 1, 5),
         new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.obsidian), 0, 3, 7, 5),
         new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.sapling), 0, 3, 7, 5),
         new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3),
         new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1),
         new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1),
         new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1)
      );
      private boolean hasMadeChest;

      public House2() {
      }

      public House2(StructureVillagePieces.Start startIn, int i, Random rand, StructureBoundingBox structureBoundingBox, EnumFacing facing) {
         super(startIn, i);
         super.coordBaseMode = facing;
         super.boundingBox = structureBoundingBox;
      }

      public static StructureVillagePieces.House2 func_175855_a(
         StructureVillagePieces.Start startIn, List<StructureComponent> list, Random rand, int i, int j, int k, EnumFacing facing, int l
      ) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, 0, 0, 0, 10, 6, 7, facing);
         return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(list, structureboundingbox) == null
            ? new StructureVillagePieces.House2(startIn, l, rand, structureboundingbox, facing)
            : null;
      }

      @Override
      public void writeStructureToNBT(NBTTagCompound tagCompound) {
         super.writeStructureToNBT(tagCompound);
         tagCompound.setBoolean("Chest", this.hasMadeChest);
      }

      @Override
      public void readStructureFromNBT(NBTTagCompound tagCompound) {
         super.readStructureFromNBT(tagCompound);
         this.hasMadeChest = tagCompound.getBoolean("Chest");
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         int k = super.field_143015_k;
         if (k < 0) {
            super.field_143015_k = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);
            if (super.field_143015_k < 0) {
               return true;
            }

            super.boundingBox.offset(0, super.field_143015_k - super.boundingBox.maxY + 6 - 1, 0);
         }

         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 9, 4, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 0, 0, 9, 0, 6, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 4, 0, 9, 4, 6, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 9, 5, 6, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 1, 8, 5, 5, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 2, 3, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 4, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 0, 3, 4, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 6, 0, 4, 6, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 3, 3, 1, structureBoundingBoxIn);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 2, 3, 3, 2, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 3, 5, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 5, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 6, 5, 3, 6, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 0, 5, 3, 0, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 1, 0, 9, 3, 0, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 6, 1, 4, 9, 4, 6, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         this.setBlockState(worldIn, Blocks.flowing_lava.getDefaultState(), 7, 1, 5, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.flowing_lava.getDefaultState(), 8, 1, 5, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), 9, 2, 5, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), 9, 2, 4, structureBoundingBoxIn);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 2, 4, 8, 2, 5, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 6, 1, 3, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.furnace.getDefaultState(), 6, 2, 3, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.furnace.getDefaultState(), 6, 3, 3, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.double_stone_slab.getDefaultState(), 8, 1, 1, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 4, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 2, 6, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 4, 2, 6, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 2, 1, 4, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.wooden_pressure_plate.getDefaultState(), 2, 2, 4, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 1, 1, 5, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.oak_stairs, 3)), 2, 1, 5, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.oak_stairs, 1)), 1, 1, 4, structureBoundingBoxIn);
         boolean flag = this.hasMadeChest;
         if (!flag && structureBoundingBoxIn.isVecInside(new BlockPos(this.getXWithOffset(5, 5), this.getYWithOffset(1), this.getZWithOffset(5, 5)))) {
            this.hasMadeChest = true;
            this.generateChestContents(worldIn, structureBoundingBoxIn, randomIn, 5, 1, 5, villageBlacksmithChestContents, 3 + randomIn.nextInt(6));
         }

         for (int i = 6; i <= 8; i++) {
            if (this.getBlockStateFromPos(worldIn, i, 0, -1, structureBoundingBoxIn).getBlock().getMaterial() == Material.air
               && this.getBlockStateFromPos(worldIn, i, -1, -1, structureBoundingBoxIn).getBlock().getMaterial() != Material.air) {
               this.setBlockState(
                  worldIn, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), i, 0, -1, structureBoundingBoxIn
               );
            }
         }

         for (int l = 0; l < 7; l++) {
            for (int j = 0; j < 10; j++) {
               this.clearCurrentPositionBlocksUpwards(worldIn, j, 6, l, structureBoundingBoxIn);
               this.replaceAirAndLiquidDownwards(worldIn, Blocks.cobblestone.getDefaultState(), j, -1, l, structureBoundingBoxIn);
            }
         }

         this.spawnVillagers(worldIn, structureBoundingBoxIn, 7, 1, 1, 1);
         return true;
      }

      @Override
      public int func_180779_c(int i, int j) {
         return 3;
      }
   }

   public static class House3 extends StructureVillagePieces.Village {
      public House3() {
      }

      public House3(StructureVillagePieces.Start startIn, int i, Random rand, StructureBoundingBox structureBoundingBox, EnumFacing facing) {
         super(startIn, i);
         super.coordBaseMode = facing;
         super.boundingBox = structureBoundingBox;
      }

      public static StructureVillagePieces.House3 func_175849_a(
         StructureVillagePieces.Start startIn, List<StructureComponent> list, Random rand, int i, int j, int k, EnumFacing facing, int l
      ) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, 0, 0, 0, 9, 7, 12, facing);
         return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(list, structureboundingbox) == null
            ? new StructureVillagePieces.House3(startIn, l, rand, structureboundingbox, facing)
            : null;
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         int k1 = super.field_143015_k;
         if (k1 < 0) {
            super.field_143015_k = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);
            if (super.field_143015_k < 0) {
               return true;
            }

            super.boundingBox.offset(0, super.field_143015_k - super.boundingBox.maxY + 7 - 1, 0);
         }

         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 7, 4, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 6, 8, 4, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 0, 5, 8, 0, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 7, 0, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 3, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 8, 0, 0, 8, 3, 10, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 1, 0, 0, 7, 2, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 1, 0, 5, 2, 1, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 2, 0, 6, 2, 3, 10, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 3, 0, 10, 7, 3, 10, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 7, 3, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 5, 2, 3, 5, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 1, 8, 4, 1, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 4, 3, 4, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 2, 8, 5, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 0, 4, 2, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 0, 4, 3, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 8, 4, 2, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 8, 4, 3, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 8, 4, 4, structureBoundingBoxIn);
         int i = this.getMetadataWithOffset(Blocks.oak_stairs, 3);
         int j = this.getMetadataWithOffset(Blocks.oak_stairs, 2);

         for (int k = -1; k <= 2; k++) {
            for (int l = 0; l <= 8; l++) {
               this.setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(i), l, 4 + k, k, structureBoundingBoxIn);
               if ((k > -1 || l <= 1) && (k > 0 || l <= 3) && (k > 1 || l <= 4 || l >= 6)) {
                  this.setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(j), l, 4 + k, 5 - k, structureBoundingBoxIn);
               }
            }
         }

         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 4, 5, 3, 4, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 4, 2, 7, 4, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 5, 4, 4, 5, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 5, 4, 6, 5, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 6, 3, 5, 6, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         int i2 = this.getMetadataWithOffset(Blocks.oak_stairs, 0);

         for (int j2 = 4; j2 >= 1; j2--) {
            this.setBlockState(worldIn, Blocks.planks.getDefaultState(), j2, 2 + j2, 7 - j2, structureBoundingBoxIn);
            int l1 = 8 - j2;

            for (int i1 = l1; i1 <= 10; i1++) {
               this.setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(i2), j2, 2 + j2, i1, structureBoundingBoxIn);
            }
         }

         int k2 = this.getMetadataWithOffset(Blocks.oak_stairs, 1);
         this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 6, 6, 3, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 7, 5, 4, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(k2), 6, 6, 4, structureBoundingBoxIn);

         for (int l2 = 6; l2 <= 8; l2++) {
            for (int j1 = 5; j1 <= 10; j1++) {
               this.setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(k2), l2, 12 - l2, j1, structureBoundingBoxIn);
            }
         }

         this.setBlockState(worldIn, Blocks.log.getDefaultState(), 0, 2, 1, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.log.getDefaultState(), 0, 2, 4, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 3, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.log.getDefaultState(), 4, 2, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 5, 2, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.log.getDefaultState(), 6, 2, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.log.getDefaultState(), 8, 2, 1, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 2, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 3, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.log.getDefaultState(), 8, 2, 4, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 8, 2, 5, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.log.getDefaultState(), 8, 2, 6, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 7, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 8, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.log.getDefaultState(), 8, 2, 9, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.log.getDefaultState(), 2, 2, 6, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 2, 7, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 2, 8, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.log.getDefaultState(), 2, 2, 9, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.log.getDefaultState(), 4, 4, 10, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 5, 4, 10, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.log.getDefaultState(), 6, 4, 10, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 5, 5, 10, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.air.getDefaultState(), 2, 1, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.air.getDefaultState(), 2, 2, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, super.coordBaseMode), 2, 3, 1, structureBoundingBoxIn);
         this.placeDoorCurrentPosition(
            worldIn, structureBoundingBoxIn, randomIn, 2, 1, 0, EnumFacing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, 1))
         );
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, -1, 3, 2, -1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         Material material2 = this.getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getBlock().getMaterial();
         Material material1 = Material.air;
         Material material = material2;
         if (material == material1 && this.getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getBlock().getMaterial() != Material.air) {
            this.setBlockState(
               worldIn, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 2, 0, -1, structureBoundingBoxIn
            );
         }

         for (int i3 = 0; i3 < 5; i3++) {
            for (int k3 = 0; k3 < 9; k3++) {
               this.clearCurrentPositionBlocksUpwards(worldIn, k3, 7, i3, structureBoundingBoxIn);
               this.replaceAirAndLiquidDownwards(worldIn, Blocks.cobblestone.getDefaultState(), k3, -1, i3, structureBoundingBoxIn);
            }
         }

         for (int j3 = 5; j3 < 11; j3++) {
            for (int l3 = 2; l3 < 9; l3++) {
               this.clearCurrentPositionBlocksUpwards(worldIn, l3, 7, j3, structureBoundingBoxIn);
               this.replaceAirAndLiquidDownwards(worldIn, Blocks.cobblestone.getDefaultState(), l3, -1, j3, structureBoundingBoxIn);
            }
         }

         this.spawnVillagers(worldIn, structureBoundingBoxIn, 4, 1, 2, 2);
         return true;
      }
   }

   public static class House4Garden extends StructureVillagePieces.Village {
      private boolean isRoofAccessible;

      public House4Garden() {
      }

      public House4Garden(StructureVillagePieces.Start startIn, int i, Random rand, StructureBoundingBox structureBoundingBox, EnumFacing facing) {
         super(startIn, i);
         super.coordBaseMode = facing;
         super.boundingBox = structureBoundingBox;
         this.isRoofAccessible = rand.nextBoolean();
      }

      @Override
      public void writeStructureToNBT(NBTTagCompound tagCompound) {
         super.writeStructureToNBT(tagCompound);
         tagCompound.setBoolean("Terrace", this.isRoofAccessible);
      }

      @Override
      public void readStructureFromNBT(NBTTagCompound tagCompound) {
         super.readStructureFromNBT(tagCompound);
         this.isRoofAccessible = tagCompound.getBoolean("Terrace");
      }

      public static StructureVillagePieces.House4Garden i(
         StructureVillagePieces.Start startIn, List<StructureComponent> list, Random rand, int i, int j, int k, EnumFacing facing, int l
      ) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, 0, 0, 0, 5, 6, 5, facing);
         return StructureComponent.findIntersecting(list, structureboundingbox) != null
            ? null
            : new StructureVillagePieces.House4Garden(startIn, l, rand, structureboundingbox, facing);
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         if (super.field_143015_k < 0) {
            super.field_143015_k = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);
            if (super.field_143015_k < 0) {
               return true;
            }

            super.boundingBox.offset(0, super.field_143015_k - super.boundingBox.maxY + 6 - 1, 0);
         }

         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 0, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 0, 4, 4, 4, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 1, 3, 4, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 0, 1, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 0, 2, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 0, 3, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, 1, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, 2, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, 3, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 0, 1, 4, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 0, 2, 4, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 0, 3, 4, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, 1, 4, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, 2, 4, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, 3, 4, structureBoundingBoxIn);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 4, 3, 3, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 2, 4, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 4, 2, 2, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 1, 1, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 1, 2, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 1, 3, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 2, 3, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 3, 3, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 3, 2, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 3, 1, 0, structureBoundingBoxIn);
         if (this.getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getBlock().getMaterial() == Material.air
            && this.getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getBlock().getMaterial() != Material.air) {
            this.setBlockState(
               worldIn, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 2, 0, -1, structureBoundingBoxIn
            );
         }

         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 3, 3, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         boolean flag = this.isRoofAccessible;
         if (flag) {
            this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 0, 5, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 1, 5, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 2, 5, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 3, 5, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 5, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 0, 5, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 1, 5, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 2, 5, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 3, 5, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 5, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 5, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 5, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 5, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 0, 5, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 0, 5, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 0, 5, 3, structureBoundingBoxIn);
         }

         boolean flag1 = this.isRoofAccessible;
         if (flag1) {
            int i = this.getMetadataWithOffset(Blocks.ladder, 3);
            this.setBlockState(worldIn, Blocks.ladder.getStateFromMeta(i), 3, 1, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.ladder.getStateFromMeta(i), 3, 2, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.ladder.getStateFromMeta(i), 3, 3, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.ladder.getStateFromMeta(i), 3, 4, 3, structureBoundingBoxIn);
         }

         this.setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, super.coordBaseMode), 2, 3, 1, structureBoundingBoxIn);

         for (int k = 0; k < 5; k++) {
            for (int j = 0; j < 5; j++) {
               this.clearCurrentPositionBlocksUpwards(worldIn, j, 6, k, structureBoundingBoxIn);
               this.replaceAirAndLiquidDownwards(worldIn, Blocks.cobblestone.getDefaultState(), j, -1, k, structureBoundingBoxIn);
            }
         }

         this.spawnVillagers(worldIn, structureBoundingBoxIn, 1, 1, 2, 1);
         return true;
      }
   }

   public static class Path extends StructureVillagePieces.Road {
      private int length;

      public Path() {
      }

      public Path(StructureVillagePieces.Start startIn, int i, Random rand, StructureBoundingBox structureBoundingBox, EnumFacing facing) {
         super(startIn, i);
         super.coordBaseMode = facing;
         super.boundingBox = structureBoundingBox;
         this.length = Math.max(structureBoundingBox.getXSize(), structureBoundingBox.getZSize());
      }

      @Override
      public void writeStructureToNBT(NBTTagCompound tagCompound) {
         super.writeStructureToNBT(tagCompound);
         tagCompound.setInteger("Length", this.length);
      }

      @Override
      public void readStructureFromNBT(NBTTagCompound tagCompound) {
         super.readStructureFromNBT(tagCompound);
         this.length = tagCompound.getInteger("Length");
      }

      @Override
      public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
         boolean flag = false;

         for (int i = rand.nextInt(5); i < this.length - 8; i += 2 + rand.nextInt(5)) {
            StructureComponent structurecomponent = this.getNextComponentNN((StructureVillagePieces.Start)componentIn, listIn, rand, 0, i);
            if (structurecomponent != null) {
               i += Math.max(structurecomponent.boundingBox.getXSize(), structurecomponent.boundingBox.getZSize());
               flag = true;
            }
         }

         for (int j = rand.nextInt(5); j < this.length - 8; j += 2 + rand.nextInt(5)) {
            StructureComponent structurecomponent1 = this.getNextComponentPP((StructureVillagePieces.Start)componentIn, listIn, rand, 0, j);
            if (structurecomponent1 != null) {
               j += Math.max(structurecomponent1.boundingBox.getXSize(), structurecomponent1.boundingBox.getZSize());
               flag = true;
            }
         }

         if (flag && rand.nextInt(3) > 0 && super.coordBaseMode != null) {
            switch (super.coordBaseMode) {
               case NORTH:
                  StructureVillagePieces.func_176069_e(
                     (StructureVillagePieces.Start)componentIn,
                     listIn,
                     rand,
                     super.boundingBox.minX - 1,
                     super.boundingBox.minY,
                     super.boundingBox.minZ,
                     EnumFacing.WEST,
                     this.getComponentType()
                  );
                  break;
               case SOUTH:
                  StructureVillagePieces.func_176069_e(
                     (StructureVillagePieces.Start)componentIn,
                     listIn,
                     rand,
                     super.boundingBox.minX - 1,
                     super.boundingBox.minY,
                     super.boundingBox.maxZ - 2,
                     EnumFacing.WEST,
                     this.getComponentType()
                  );
                  break;
               case WEST:
                  StructureVillagePieces.func_176069_e(
                     (StructureVillagePieces.Start)componentIn,
                     listIn,
                     rand,
                     super.boundingBox.minX,
                     super.boundingBox.minY,
                     super.boundingBox.minZ - 1,
                     EnumFacing.NORTH,
                     this.getComponentType()
                  );
                  break;
               case EAST:
                  StructureVillagePieces.func_176069_e(
                     (StructureVillagePieces.Start)componentIn,
                     listIn,
                     rand,
                     super.boundingBox.maxX - 2,
                     super.boundingBox.minY,
                     super.boundingBox.minZ - 1,
                     EnumFacing.NORTH,
                     this.getComponentType()
                  );
            }
         }

         if (flag && rand.nextInt(3) > 0 && super.coordBaseMode != null) {
            switch (super.coordBaseMode) {
               case NORTH:
                  StructureVillagePieces.func_176069_e(
                     (StructureVillagePieces.Start)componentIn,
                     listIn,
                     rand,
                     super.boundingBox.maxX + 1,
                     super.boundingBox.minY,
                     super.boundingBox.minZ,
                     EnumFacing.EAST,
                     this.getComponentType()
                  );
                  break;
               case SOUTH:
                  StructureVillagePieces.func_176069_e(
                     (StructureVillagePieces.Start)componentIn,
                     listIn,
                     rand,
                     super.boundingBox.maxX + 1,
                     super.boundingBox.minY,
                     super.boundingBox.maxZ - 2,
                     EnumFacing.EAST,
                     this.getComponentType()
                  );
                  break;
               case WEST:
                  StructureVillagePieces.func_176069_e(
                     (StructureVillagePieces.Start)componentIn,
                     listIn,
                     rand,
                     super.boundingBox.minX,
                     super.boundingBox.minY,
                     super.boundingBox.maxZ + 1,
                     EnumFacing.SOUTH,
                     this.getComponentType()
                  );
                  break;
               case EAST:
                  StructureVillagePieces.func_176069_e(
                     (StructureVillagePieces.Start)componentIn,
                     listIn,
                     rand,
                     super.boundingBox.maxX - 2,
                     super.boundingBox.minY,
                     super.boundingBox.maxZ + 1,
                     EnumFacing.SOUTH,
                     this.getComponentType()
                  );
            }
         }
      }

      public static StructureBoundingBox func_175848_a(
         StructureVillagePieces.Start startIn, List<StructureComponent> list, Random rand, int i, int j, int k, EnumFacing facing
      ) {
         for (int i1 = 7 * MathHelper.getRandomIntegerInRange(rand, 3, 5); i1 >= 7; i1 -= 7) {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, 0, 0, 0, 3, 3, i1, facing);
            if (StructureComponent.findIntersecting(list, structureboundingbox) == null) {
               return structureboundingbox;
            }
         }

         return null;
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         IBlockState iblockstate = this.func_175847_a(Blocks.gravel.getDefaultState());
         IBlockState iblockstate1 = this.func_175847_a(Blocks.cobblestone.getDefaultState());

         for (int i = super.boundingBox.minX; i <= super.boundingBox.maxX; i++) {
            for (int j = super.boundingBox.minZ; j <= super.boundingBox.maxZ; j++) {
               BlockPos blockpos = new BlockPos(i, 64, j);
               if (structureBoundingBoxIn.isVecInside(blockpos)) {
                  blockpos = worldIn.getTopSolidOrLiquidBlock(blockpos).down();
                  worldIn.setBlockState(blockpos, iblockstate, 2);
                  worldIn.setBlockState(blockpos.down(), iblockstate1, 2);
               }
            }
         }

         return true;
      }
   }

   public static class PieceWeight {
      public Class<? extends StructureVillagePieces.Village> villagePieceClass;
      public final int villagePieceWeight;
      public int villagePiecesSpawned;
      public int villagePiecesLimit;

      public PieceWeight(Class<? extends StructureVillagePieces.Village> class2, int i, int j) {
         this.villagePieceClass = class2;
         this.villagePieceWeight = i;
         this.villagePiecesLimit = j;
      }

      public boolean canSpawnMoreVillagePiecesOfType(int i) {
         return this.villagePiecesLimit == 0 || this.villagePiecesSpawned < this.villagePiecesLimit;
      }

      public boolean canSpawnMoreVillagePieces() {
         return this.villagePiecesLimit == 0 || this.villagePiecesSpawned < this.villagePiecesLimit;
      }
   }

   public abstract static class Road extends StructureVillagePieces.Village {
      public Road() {
      }

      public Road(StructureVillagePieces.Start startIn, int typeIn) {
         super(startIn, typeIn);
      }
   }

   public static class Start extends StructureVillagePieces.Well {
      public WorldChunkManager worldChunkMngr;
      public boolean inDesert;
      public int terrainType;
      public StructureVillagePieces.PieceWeight structVillagePieceWeight;
      public List<StructureVillagePieces.PieceWeight> structureVillageWeightedPieceList;
      public List<StructureComponent> field_74932_i = Lists.newArrayList();
      public List<StructureComponent> field_74930_j = Lists.newArrayList();

      public Start() {
      }

      public Start(WorldChunkManager chunkManagerIn, int i, Random rand, int j, int k, List<StructureVillagePieces.PieceWeight> list, int l) {
         super((StructureVillagePieces.Start)null, 0, rand, j, k);
         this.worldChunkMngr = chunkManagerIn;
         this.structureVillageWeightedPieceList = list;
         this.terrainType = l;
         BiomeGenBase biomegenbase = chunkManagerIn.getBiomeGenerator(new BlockPos(j, 0, k), BiomeGenBase.field_180279_ad);
         this.inDesert = biomegenbase == BiomeGenBase.desert || biomegenbase == BiomeGenBase.desertHills;
         this.func_175846_a(this.inDesert);
      }

      public WorldChunkManager getWorldChunkManager() {
         return this.worldChunkMngr;
      }
   }

   public static class Torch extends StructureVillagePieces.Village {
      public Torch() {
      }

      public Torch(StructureVillagePieces.Start startIn, int i, Random rand, StructureBoundingBox structureBoundingBox, EnumFacing facing) {
         super(startIn, i);
         super.coordBaseMode = facing;
         super.boundingBox = structureBoundingBox;
      }

      public static StructureBoundingBox func_175856_a(
         StructureVillagePieces.Start startIn, List<StructureComponent> list, Random rand, int i, int j, int k, EnumFacing facing
      ) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, 0, 0, 0, 3, 4, 2, facing);
         return StructureComponent.findIntersecting(list, structureboundingbox) != null ? null : structureboundingbox;
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         if (super.field_143015_k < 0) {
            super.field_143015_k = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);
            if (super.field_143015_k < 0) {
               return true;
            }

            super.boundingBox.offset(0, super.field_143015_k - super.boundingBox.maxY + 4 - 1, 0);
         }

         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 2, 3, 1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 1, 0, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 1, 1, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 1, 2, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.wool.getStateFromMeta(EnumDyeColor.WHITE.getDyeDamage()), 1, 3, 0, structureBoundingBoxIn);
         EnumFacing enumfacing1 = EnumFacing.EAST;
         EnumFacing enumfacing = super.coordBaseMode;
         boolean flag = enumfacing == enumfacing1 || super.coordBaseMode == EnumFacing.NORTH;
         IBlockState iblockstate = Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, super.coordBaseMode.rotateY());
         this.setBlockState(worldIn, iblockstate, flag ? 2 : 0, 3, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, super.coordBaseMode), 1, 3, 1, structureBoundingBoxIn);
         IBlockState iblockstate1 = Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, super.coordBaseMode.rotateYCCW());
         this.setBlockState(worldIn, iblockstate1, flag ? 0 : 2, 3, 0, structureBoundingBoxIn);
         this.setBlockState(
            worldIn, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, super.coordBaseMode.getOpposite()), 1, 3, -1, structureBoundingBoxIn
         );
         return true;
      }
   }

   public abstract static class Village extends StructureComponent {
      public int field_143015_k = -1;
      private int villagersSpawned;
      private boolean isDesertVillage;

      public Village() {
      }

      public Village(StructureVillagePieces.Start startIn, int typeIn) {
         super(typeIn);
         if (startIn != null) {
            this.isDesertVillage = startIn.inDesert;
         }
      }

      @Override
      public void writeStructureToNBT(NBTTagCompound tagCompound) {
         tagCompound.setInteger("HPos", this.field_143015_k);
         tagCompound.setInteger("VCount", this.villagersSpawned);
         tagCompound.setBoolean("Desert", this.isDesertVillage);
      }

      @Override
      public void readStructureFromNBT(NBTTagCompound tagCompound) {
         this.field_143015_k = tagCompound.getInteger("HPos");
         this.villagersSpawned = tagCompound.getInteger("VCount");
         this.isDesertVillage = tagCompound.getBoolean("Desert");
      }

      public StructureComponent getNextComponentNN(StructureVillagePieces.Start startIn, List<StructureComponent> list, Random rand, int i, int j) {
         if (super.coordBaseMode != null) {
            switch (super.coordBaseMode) {
               case NORTH:
                  return StructureVillagePieces.access$100(
                     startIn,
                     list,
                     rand,
                     super.boundingBox.minX - 1,
                     super.boundingBox.minY + i,
                     super.boundingBox.minZ + j,
                     EnumFacing.WEST,
                     this.getComponentType()
                  );
               case SOUTH:
                  return StructureVillagePieces.access$100(
                     startIn,
                     list,
                     rand,
                     super.boundingBox.minX - 1,
                     super.boundingBox.minY + i,
                     super.boundingBox.minZ + j,
                     EnumFacing.WEST,
                     this.getComponentType()
                  );
               case WEST:
                  return StructureVillagePieces.access$100(
                     startIn,
                     list,
                     rand,
                     super.boundingBox.minX + j,
                     super.boundingBox.minY + i,
                     super.boundingBox.minZ - 1,
                     EnumFacing.NORTH,
                     this.getComponentType()
                  );
               case EAST:
                  return StructureVillagePieces.access$100(
                     startIn,
                     list,
                     rand,
                     super.boundingBox.minX + j,
                     super.boundingBox.minY + i,
                     super.boundingBox.minZ - 1,
                     EnumFacing.NORTH,
                     this.getComponentType()
                  );
            }
         }

         return null;
      }

      public StructureComponent getNextComponentPP(StructureVillagePieces.Start startIn, List<StructureComponent> list, Random rand, int i, int j) {
         if (super.coordBaseMode != null) {
            switch (super.coordBaseMode) {
               case NORTH:
                  return StructureVillagePieces.access$100(
                     startIn,
                     list,
                     rand,
                     super.boundingBox.maxX + 1,
                     super.boundingBox.minY + i,
                     super.boundingBox.minZ + j,
                     EnumFacing.EAST,
                     this.getComponentType()
                  );
               case SOUTH:
                  return StructureVillagePieces.access$100(
                     startIn,
                     list,
                     rand,
                     super.boundingBox.maxX + 1,
                     super.boundingBox.minY + i,
                     super.boundingBox.minZ + j,
                     EnumFacing.EAST,
                     this.getComponentType()
                  );
               case WEST:
                  return StructureVillagePieces.access$100(
                     startIn,
                     list,
                     rand,
                     super.boundingBox.minX + j,
                     super.boundingBox.minY + i,
                     super.boundingBox.maxZ + 1,
                     EnumFacing.SOUTH,
                     this.getComponentType()
                  );
               case EAST:
                  return StructureVillagePieces.access$100(
                     startIn,
                     list,
                     rand,
                     super.boundingBox.minX + j,
                     super.boundingBox.minY + i,
                     super.boundingBox.maxZ + 1,
                     EnumFacing.SOUTH,
                     this.getComponentType()
                  );
            }
         }

         return null;
      }

      public int getAverageGroundLevel(World worldIn, StructureBoundingBox structureBoundingBox) {
         int i = 0;
         Object object = null;
         int j = 0;
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

         for (int k = super.boundingBox.minZ; k <= super.boundingBox.maxZ; k++) {
            int i1 = super.boundingBox.minX;

            for (int l = i1; l <= super.boundingBox.maxX; l++) {
               blockpos$mutableblockpos.set(l, 64, k);
               if (structureBoundingBox.isVecInside(blockpos$mutableblockpos)) {
                  i += Math.max(worldIn.getTopSolidOrLiquidBlock(blockpos$mutableblockpos).getY(), worldIn.provider.getAverageGroundLevel());
                  j++;
               }
            }
         }

         return j == 0 ? -1 : i / j;
      }

      public static boolean canVillageGoDeeper(StructureBoundingBox structureBoundingBox) {
         if (structureBoundingBox != null) {
            int i = structureBoundingBox.minY;
            if (i > 10) {
               return true;
            }
         }

         return false;
      }

      public void spawnVillagers(World worldIn, StructureBoundingBox structureBoundingBox, int i, int j, int k, int l) {
         if (this.villagersSpawned < l) {
            for (int i1 = this.villagersSpawned; i1 < l; i1++) {
               int j1 = this.getXWithOffset(i + i1, k);
               int k1 = this.getYWithOffset(j);
               int l1 = this.getZWithOffset(i + i1, k);
               if (!structureBoundingBox.isVecInside(new BlockPos(j1, k1, l1))) {
                  break;
               }

               this.villagersSpawned++;
               EntityVillager entityvillager = new EntityVillager(worldIn);
               entityvillager.setLocationAndAngles((double)j1 + 0.5, (double)k1, (double)l1 + 0.5, 0.0F, 0.0F);
               entityvillager.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(entityvillager)), (IEntityLivingData)null);
               entityvillager.setProfession(this.func_180779_c(i1, entityvillager.getProfession()));
               worldIn.spawnEntityInWorld(entityvillager);
            }
         }
      }

      public int func_180779_c(int i, int j) {
         return j;
      }

      public IBlockState func_175847_a(IBlockState iBlockState) {
         if (this.isDesertVillage) {
            if (iBlockState.getBlock() == Blocks.log || iBlockState.getBlock() == Blocks.log2) {
               return Blocks.sandstone.getDefaultState();
            }

            if (iBlockState.getBlock() == Blocks.cobblestone) {
               return Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.DEFAULT.getMetadata());
            }

            if (iBlockState.getBlock() == Blocks.planks) {
               return Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata());
            }

            if (iBlockState.getBlock() == Blocks.oak_stairs) {
               return Blocks.sandstone_stairs.getDefaultState().withProperty(BlockStairs.FACING, iBlockState.getValue(BlockStairs.FACING));
            }

            if (iBlockState.getBlock() == Blocks.stone_stairs) {
               return Blocks.sandstone_stairs.getDefaultState().withProperty(BlockStairs.FACING, iBlockState.getValue(BlockStairs.FACING));
            }

            if (iBlockState.getBlock() == Blocks.gravel) {
               return Blocks.sandstone.getDefaultState();
            }
         }

         return iBlockState;
      }

      @Override
      public void setBlockState(World worldIn, IBlockState blockstateIn, int x, int y, int z, StructureBoundingBox boundingboxIn) {
         IBlockState iblockstate = this.func_175847_a(blockstateIn);
         super.setBlockState(worldIn, iblockstate, x, y, z, boundingboxIn);
      }

      @Override
      public void fillWithBlocks(
         World worldIn,
         StructureBoundingBox boundingboxIn,
         int xMin,
         int yMin,
         int zMin,
         int xMax,
         int yMax,
         int zMax,
         IBlockState boundaryBlockState,
         IBlockState insideBlockState,
         boolean existingOnly
      ) {
         IBlockState iblockstate = this.func_175847_a(boundaryBlockState);
         IBlockState iblockstate1 = this.func_175847_a(insideBlockState);
         super.fillWithBlocks(worldIn, boundingboxIn, xMin, yMin, zMin, xMax, yMax, zMax, iblockstate, iblockstate1, existingOnly);
      }

      @Override
      public void replaceAirAndLiquidDownwards(World worldIn, IBlockState blockstateIn, int x, int y, int z, StructureBoundingBox boundingboxIn) {
         IBlockState iblockstate = this.func_175847_a(blockstateIn);
         super.replaceAirAndLiquidDownwards(worldIn, iblockstate, x, y, z, boundingboxIn);
      }

      public void func_175846_a(boolean bl) {
         this.isDesertVillage = bl;
      }
   }

   public static class Well extends StructureVillagePieces.Village {
      public Well() {
      }

      public Well(StructureVillagePieces.Start startIn, int i, Random rand, int j, int k) {
         super(startIn, i);
         super.coordBaseMode = EnumFacing.Plane.HORIZONTAL.random(rand);
         switch (super.coordBaseMode) {
            case NORTH:
            case SOUTH:
               super.boundingBox = new StructureBoundingBox(j, 64, k, j + 6 - 1, 78, k + 6 - 1);
               break;
            default:
               super.boundingBox = new StructureBoundingBox(j, 64, k, j + 6 - 1, 78, k + 6 - 1);
         }
      }

      @Override
      public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
         StructureVillagePieces.func_176069_e(
            (StructureVillagePieces.Start)componentIn,
            listIn,
            rand,
            super.boundingBox.minX - 1,
            super.boundingBox.maxY - 4,
            super.boundingBox.minZ + 1,
            EnumFacing.WEST,
            this.getComponentType()
         );
         StructureVillagePieces.func_176069_e(
            (StructureVillagePieces.Start)componentIn,
            listIn,
            rand,
            super.boundingBox.maxX + 1,
            super.boundingBox.maxY - 4,
            super.boundingBox.minZ + 1,
            EnumFacing.EAST,
            this.getComponentType()
         );
         StructureVillagePieces.func_176069_e(
            (StructureVillagePieces.Start)componentIn,
            listIn,
            rand,
            super.boundingBox.minX + 1,
            super.boundingBox.maxY - 4,
            super.boundingBox.minZ - 1,
            EnumFacing.NORTH,
            this.getComponentType()
         );
         StructureVillagePieces.func_176069_e(
            (StructureVillagePieces.Start)componentIn,
            listIn,
            rand,
            super.boundingBox.minX + 1,
            super.boundingBox.maxY - 4,
            super.boundingBox.maxZ + 1,
            EnumFacing.SOUTH,
            this.getComponentType()
         );
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         int k = super.field_143015_k;
         if (k < 0) {
            super.field_143015_k = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);
            if (super.field_143015_k < 0) {
               return true;
            }

            super.boundingBox.offset(0, super.field_143015_k - super.boundingBox.maxY + 3, 0);
         }

         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 1, 0, 1, 4, 12, 4, Blocks.cobblestone.getDefaultState(), Blocks.flowing_water.getDefaultState(), false
         );
         this.setBlockState(worldIn, Blocks.air.getDefaultState(), 2, 12, 2, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.air.getDefaultState(), 3, 12, 2, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.air.getDefaultState(), 2, 12, 3, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.air.getDefaultState(), 3, 12, 3, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 1, 13, 1, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 1, 14, 1, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 13, 1, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 14, 1, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 1, 13, 4, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 1, 14, 4, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 13, 4, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 14, 4, structureBoundingBoxIn);
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 1, 15, 1, 4, 15, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );

         for (int i = 0; i <= 5; i++) {
            for (int j = 0; j <= 5; j++) {
               if (j == 0 || j == 5 || i == 0 || i == 5) {
                  this.setBlockState(worldIn, Blocks.gravel.getDefaultState(), j, 11, i, structureBoundingBoxIn);
                  this.clearCurrentPositionBlocksUpwards(worldIn, j, 12, i, structureBoundingBoxIn);
               }
            }
         }

         return true;
      }
   }

   public static class WoodHut extends StructureVillagePieces.Village {
      private boolean isTallHouse;
      private int tablePosition;

      public WoodHut() {
      }

      public WoodHut(StructureVillagePieces.Start startIn, int i, Random rand, StructureBoundingBox structureBoundingBox, EnumFacing facing) {
         super(startIn, i);
         super.coordBaseMode = facing;
         super.boundingBox = structureBoundingBox;
         this.isTallHouse = rand.nextBoolean();
         this.tablePosition = rand.nextInt(3);
      }

      @Override
      public void writeStructureToNBT(NBTTagCompound tagCompound) {
         super.writeStructureToNBT(tagCompound);
         tagCompound.setInteger("T", this.tablePosition);
         tagCompound.setBoolean("C", this.isTallHouse);
      }

      @Override
      public void readStructureFromNBT(NBTTagCompound tagCompound) {
         super.readStructureFromNBT(tagCompound);
         this.tablePosition = tagCompound.getInteger("T");
         this.isTallHouse = tagCompound.getBoolean("C");
      }

      public static StructureVillagePieces.WoodHut func_175853_a(
         StructureVillagePieces.Start startIn, List<StructureComponent> list, Random rand, int i, int j, int k, EnumFacing facing, int l
      ) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, 0, 0, 0, 4, 6, 5, facing);
         return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(list, structureboundingbox) == null
            ? new StructureVillagePieces.WoodHut(startIn, l, rand, structureboundingbox, facing)
            : null;
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         int k = super.field_143015_k;
         if (k < 0) {
            super.field_143015_k = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);
            if (super.field_143015_k < 0) {
               return true;
            }

            super.boundingBox.offset(0, super.field_143015_k - super.boundingBox.maxY + 6 - 1, 0);
         }

         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 3, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 0, 0, 3, 0, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false
         );
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 2, 0, 3, Blocks.dirt.getDefaultState(), Blocks.dirt.getDefaultState(), false);
         if (this.isTallHouse) {
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 1, 2, 4, 3, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         } else {
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 1, 2, 5, 3, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         }

         this.setBlockState(worldIn, Blocks.log.getDefaultState(), 1, 4, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.log.getDefaultState(), 2, 4, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.log.getDefaultState(), 1, 4, 4, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.log.getDefaultState(), 2, 4, 4, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.log.getDefaultState(), 0, 4, 1, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.log.getDefaultState(), 0, 4, 2, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.log.getDefaultState(), 0, 4, 3, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.log.getDefaultState(), 3, 4, 1, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.log.getDefaultState(), 3, 4, 2, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.log.getDefaultState(), 3, 4, 3, structureBoundingBoxIn);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 3, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 0, 3, 3, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 4, 0, 3, 4, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 4, 3, 3, 4, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 1, 3, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 2, 3, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 4, 2, 3, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 3, 2, 2, structureBoundingBoxIn);
         if (this.tablePosition > 0) {
            this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), this.tablePosition, 1, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.wooden_pressure_plate.getDefaultState(), this.tablePosition, 2, 3, structureBoundingBoxIn);
         }

         this.setBlockState(worldIn, Blocks.air.getDefaultState(), 1, 1, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.air.getDefaultState(), 1, 2, 0, structureBoundingBoxIn);
         this.placeDoorCurrentPosition(
            worldIn, structureBoundingBoxIn, randomIn, 1, 1, 0, EnumFacing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, 1))
         );
         Material material2 = this.getBlockStateFromPos(worldIn, 1, 0, -1, structureBoundingBoxIn).getBlock().getMaterial();
         Material material1 = Material.air;
         Material material = material2;
         if (material == material1 && this.getBlockStateFromPos(worldIn, 1, -1, -1, structureBoundingBoxIn).getBlock().getMaterial() != Material.air) {
            this.setBlockState(
               worldIn, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 1, 0, -1, structureBoundingBoxIn
            );
         }

         for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
               this.clearCurrentPositionBlocksUpwards(worldIn, j, 6, i, structureBoundingBoxIn);
               this.replaceAirAndLiquidDownwards(worldIn, Blocks.cobblestone.getDefaultState(), j, -1, i, structureBoundingBoxIn);
            }
         }

         this.spawnVillagers(worldIn, structureBoundingBoxIn, 1, 1, 2, 1);
         return true;
      }
   }
}
