package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class StructureStrongholdPieces {
   private static final StructureStrongholdPieces.PieceWeight[] pieceWeightArray = new StructureStrongholdPieces.PieceWeight[]{
      new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.Straight.class, 40, 0),
      new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.Prison.class, 5, 5),
      new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.LeftTurn.class, 20, 0),
      new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.RightTurn.class, 20, 0),
      new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.RoomCrossing.class, 10, 6),
      new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.StairsStraight.class, 5, 5),
      new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.Stairs.class, 5, 5),
      new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.Crossing.class, 5, 4),
      new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.ChestCorridor.class, 5, 4),
      new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.Library.class, 10, 2) {
         @Override
         public boolean canSpawnMoreStructuresOfType(int i) {
            boolean flag = super.canSpawnMoreStructuresOfType(i);
            return flag && i > 4;
         }
      },
      new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.PortalRoom.class, 20, 1) {
         @Override
         public boolean canSpawnMoreStructuresOfType(int i) {
            boolean flag = super.canSpawnMoreStructuresOfType(i);
            return flag && i > 5;
         }
      }
   };
   private static List<StructureStrongholdPieces.PieceWeight> structurePieceList;
   private static Class<? extends StructureStrongholdPieces.Stronghold> strongComponentType;
   public static int totalWeight;
   private static final StructureStrongholdPieces.Stones strongholdStones = new StructureStrongholdPieces.Stones();

   public static void registerStrongholdPieces() {
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.ChestCorridor.class, "SHCC");
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.Corridor.class, "SHFC");
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.Crossing.class, "SH5C");
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.LeftTurn.class, "SHLT");
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.Library.class, "SHLi");
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.PortalRoom.class, "SHPR");
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.Prison.class, "SHPH");
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.RightTurn.class, "SHRT");
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.RoomCrossing.class, "SHRC");
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.Stairs.class, "SHSD");
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.Stairs2.class, "SHStart");
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.Straight.class, "SHS");
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.StairsStraight.class, "SHSSD");
   }

   public static void prepareStructurePieces() {
      structurePieceList = Lists.newArrayList();

      for (StructureStrongholdPieces.PieceWeight structurestrongholdpieces$pieceweight : pieceWeightArray) {
         structurestrongholdpieces$pieceweight.instancesSpawned = 0;
         structurePieceList.add(structurestrongholdpieces$pieceweight);
      }

      strongComponentType = null;
   }

   private static boolean canAddStructurePieces() {
      boolean flag = false;
      totalWeight = 0;

      for (StructureStrongholdPieces.PieceWeight structurestrongholdpieces$pieceweight : structurePieceList) {
         if (structurestrongholdpieces$pieceweight.instancesLimit > 0
            && structurestrongholdpieces$pieceweight.instancesSpawned < structurestrongholdpieces$pieceweight.instancesLimit) {
            flag = true;
         }

         totalWeight = totalWeight + structurestrongholdpieces$pieceweight.pieceWeight;
      }

      return flag;
   }

   private static StructureStrongholdPieces.Stronghold func_175954_a(
      Class<? extends StructureStrongholdPieces.Stronghold> class2,
      List<StructureComponent> list,
      Random random2,
      int i,
      int j,
      int k,
      EnumFacing enumFacing,
      int l
   ) {
      Object object = null;
      Class<StructureStrongholdPieces.Straight> oclass = StructureStrongholdPieces.Straight.class;
      if (class2 == oclass) {
         object = StructureStrongholdPieces.Straight.A(list, random2, i, j, k, enumFacing, l);
      } else {
         Class<StructureStrongholdPieces.Prison> oclass1 = StructureStrongholdPieces.Prison.class;
         if (class2 == oclass1) {
            object = StructureStrongholdPieces.Prison.b(list, random2, i, j, k, enumFacing, l);
         } else {
            Class<StructureStrongholdPieces.LeftTurn> oclass2 = StructureStrongholdPieces.LeftTurn.class;
            if (class2 == oclass2) {
               object = StructureStrongholdPieces.LeftTurn.func_175867_a(list, random2, i, j, k, enumFacing, l);
            } else {
               Class<StructureStrongholdPieces.RightTurn> oclass3 = StructureStrongholdPieces.RightTurn.class;
               if (class2 == oclass3) {
                  object = StructureStrongholdPieces.RightTurn.func_175867_a(list, random2, i, j, k, enumFacing, l);
               } else {
                  Class<StructureStrongholdPieces.RoomCrossing> oclass4 = StructureStrongholdPieces.RoomCrossing.class;
                  if (class2 == oclass4) {
                     object = StructureStrongholdPieces.RoomCrossing.Y(list, random2, i, j, k, enumFacing, l);
                  } else {
                     Class<StructureStrongholdPieces.StairsStraight> oclass5 = StructureStrongholdPieces.StairsStraight.class;
                     if (class2 == oclass5) {
                        object = StructureStrongholdPieces.StairsStraight.b(list, random2, i, j, k, enumFacing, l);
                     } else {
                        Class<StructureStrongholdPieces.Stairs> oclass6 = StructureStrongholdPieces.Stairs.class;
                        if (class2 == oclass6) {
                           object = StructureStrongholdPieces.Stairs.func_175863_a(list, random2, i, j, k, enumFacing, l);
                        } else {
                           Class<StructureStrongholdPieces.Crossing> oclass7 = StructureStrongholdPieces.Crossing.class;
                           if (class2 == oclass7) {
                              object = StructureStrongholdPieces.Crossing.G(list, random2, i, j, k, enumFacing, l);
                           } else {
                              Class<StructureStrongholdPieces.ChestCorridor> oclass8 = StructureStrongholdPieces.ChestCorridor.class;
                              if (class2 == oclass8) {
                                 object = StructureStrongholdPieces.ChestCorridor.g(list, random2, i, j, k, enumFacing, l);
                              } else {
                                 Class<StructureStrongholdPieces.Library> oclass9 = StructureStrongholdPieces.Library.class;
                                 if (class2 == oclass9) {
                                    object = StructureStrongholdPieces.Library.J(list, random2, i, j, k, enumFacing, l);
                                 } else if (class2 == StructureStrongholdPieces.PortalRoom.class) {
                                    object = StructureStrongholdPieces.PortalRoom.func_175865_a(list, random2, i, j, k, enumFacing, l);
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return (StructureStrongholdPieces.Stronghold)object;
   }

   private static StructureStrongholdPieces.Stronghold func_175955_b(
      StructureStrongholdPieces.Stairs2 stairs2, List<StructureComponent> list, Random random2, int i, int j, int k, EnumFacing enumFacing, int l
   ) {
      if (!canAddStructurePieces()) {
         return null;
      } else {
         Class oclass = strongComponentType;
         if (oclass != null) {
            StructureStrongholdPieces.Stronghold structurestrongholdpieces$stronghold = func_175954_a(
               strongComponentType, list, random2, i, j, k, enumFacing, l
            );
            strongComponentType = null;
            if (structurestrongholdpieces$stronghold != null) {
               return structurestrongholdpieces$stronghold;
            }
         }

         int j1 = 0;

         while (j1 < 5) {
            j1++;
            int i1 = random2.nextInt(totalWeight);

            for (StructureStrongholdPieces.PieceWeight structurestrongholdpieces$pieceweight : structurePieceList) {
               i1 -= structurestrongholdpieces$pieceweight.pieceWeight;
               if (i1 < 0) {
                  if (!structurestrongholdpieces$pieceweight.canSpawnMoreStructuresOfType(l)
                     || structurestrongholdpieces$pieceweight == stairs2.strongholdPieceWeight) {
                     break;
                  }

                  StructureStrongholdPieces.Stronghold structurestrongholdpieces$stronghold1 = func_175954_a(
                     structurestrongholdpieces$pieceweight.pieceClass, list, random2, i, j, k, enumFacing, l
                  );
                  if (structurestrongholdpieces$stronghold1 != null) {
                     structurestrongholdpieces$pieceweight.instancesSpawned++;
                     stairs2.strongholdPieceWeight = structurestrongholdpieces$pieceweight;
                     if (!structurestrongholdpieces$pieceweight.canSpawnMoreStructures()) {
                        structurePieceList.remove(structurestrongholdpieces$pieceweight);
                     }

                     return structurestrongholdpieces$stronghold1;
                  }
               }
            }
         }

         StructureBoundingBox structureboundingbox = StructureStrongholdPieces.Corridor.V(list, random2, i, j, k, enumFacing);
         return structureboundingbox != null && structureboundingbox.minY > 1
            ? new StructureStrongholdPieces.Corridor(l, random2, structureboundingbox, enumFacing)
            : null;
      }
   }

   private static StructureComponent func_175953_c(
      StructureStrongholdPieces.Stairs2 stairs2, List<StructureComponent> list, Random random2, int i, int j, int k, EnumFacing enumFacing, int l
   ) {
      if (l > 50) {
         return null;
      } else {
         int i1 = Math.abs(i - stairs2.getBoundingBox().minX);
         if (i1 <= 112 && Math.abs(k - stairs2.getBoundingBox().minZ) <= 112) {
            StructureStrongholdPieces.Stronghold structurestrongholdpieces$stronghold = func_175955_b(stairs2, list, random2, i, j, k, enumFacing, l + 1);
            if (structurestrongholdpieces$stronghold != null) {
               list.add(structurestrongholdpieces$stronghold);
               stairs2.field_75026_c.add(structurestrongholdpieces$stronghold);
            }

            return structurestrongholdpieces$stronghold;
         } else {
            return null;
         }
      }
   }

   static StructureStrongholdPieces.Stones access$200() {
      return strongholdStones;
   }

   static StructureComponent access$000(
      StructureStrongholdPieces.Stairs2 stairs2, List list, Random random, int i, int i2, int i3, EnumFacing enumFacing, int i4
   ) {
      return func_175953_c(stairs2, list, random, i, i2, i3, enumFacing, i4);
   }

   public static class ChestCorridor extends StructureStrongholdPieces.Stronghold {
      private static final List<WeightedRandomChestContent> strongholdChestContents = Lists.newArrayList(
         new WeightedRandomChestContent(Items.ender_pearl, 0, 1, 1, 10),
         new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3),
         new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10),
         new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5),
         new WeightedRandomChestContent(Items.redstone, 0, 4, 9, 5),
         new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15),
         new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15),
         new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 5),
         new WeightedRandomChestContent(Items.iron_sword, 0, 1, 1, 5),
         new WeightedRandomChestContent(Items.iron_chestplate, 0, 1, 1, 5),
         new WeightedRandomChestContent(Items.iron_helmet, 0, 1, 1, 5),
         new WeightedRandomChestContent(Items.iron_leggings, 0, 1, 1, 5),
         new WeightedRandomChestContent(Items.iron_boots, 0, 1, 1, 5),
         new WeightedRandomChestContent(Items.golden_apple, 0, 1, 1, 1),
         new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 1),
         new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1),
         new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1),
         new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1)
      );
      private boolean hasMadeChest;

      public ChestCorridor() {
      }

      public ChestCorridor(int i, Random random2, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(i);
         super.coordBaseMode = enumFacing;
         super.field_143013_d = this.getRandomDoor(random2);
         super.boundingBox = structureBoundingBox;
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
      public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
         this.getNextComponentNormal((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 1);
      }

      public static StructureStrongholdPieces.ChestCorridor g(List<StructureComponent> list, Random random2, int i, int j, int k, EnumFacing enumFacing, int l) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, -1, -1, 0, 5, 5, 7, enumFacing);
         return canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(list, structureboundingbox) == null
            ? new StructureStrongholdPieces.ChestCorridor(l, random2, structureboundingbox, enumFacing)
            : null;
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         boolean flag = this.isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn);
         if (flag) {
            return false;
         } else {
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 4, 6, true, randomIn, StructureStrongholdPieces.access$200());
            this.placeDoor(worldIn, randomIn, structureBoundingBoxIn, super.field_143013_d, 1, 1, 0);
            this.placeDoor(worldIn, randomIn, structureBoundingBoxIn, StructureStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 6);
            this.fillWithBlocks(
               worldIn, structureBoundingBoxIn, 3, 1, 2, 3, 1, 4, Blocks.stonebrick.getDefaultState(), Blocks.stonebrick.getDefaultState(), false
            );
            this.setBlockState(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), 3, 1, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), 3, 1, 5, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), 3, 2, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), 3, 2, 4, structureBoundingBoxIn);

            for (int i = 2; i <= 4; i++) {
               this.setBlockState(
                  worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), 2, 1, i, structureBoundingBoxIn
               );
            }

            boolean flag1 = this.hasMadeChest;
            if (!flag1 && structureBoundingBoxIn.isVecInside(new BlockPos(this.getXWithOffset(3, 3), this.getYWithOffset(2), this.getZWithOffset(3, 3)))) {
               this.hasMadeChest = true;
               this.generateChestContents(
                  worldIn,
                  structureBoundingBoxIn,
                  randomIn,
                  3,
                  2,
                  3,
                  WeightedRandomChestContent.func_177629_a(strongholdChestContents, Items.enchanted_book.getRandom(randomIn)),
                  2 + randomIn.nextInt(2)
               );
            }

            return true;
         }
      }
   }

   public static class Corridor extends StructureStrongholdPieces.Stronghold {
      private int v;

      public Corridor() {
      }

      public Corridor(int i, Random random2, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(i);
         super.coordBaseMode = enumFacing;
         super.boundingBox = structureBoundingBox;
         this.v = enumFacing != EnumFacing.NORTH && enumFacing != EnumFacing.SOUTH ? structureBoundingBox.getXSize() : structureBoundingBox.getZSize();
      }

      @Override
      public void writeStructureToNBT(NBTTagCompound tagCompound) {
         super.writeStructureToNBT(tagCompound);
         tagCompound.setInteger("Steps", this.v);
      }

      @Override
      public void readStructureFromNBT(NBTTagCompound tagCompound) {
         super.readStructureFromNBT(tagCompound);
         this.v = tagCompound.getInteger("Steps");
      }

      public static StructureBoundingBox V(List<StructureComponent> list, Random random2, int i, int j, int k, EnumFacing enumFacing) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, -1, -1, 0, 5, 5, 4, enumFacing);
         StructureComponent structurecomponent = StructureComponent.findIntersecting(list, structureboundingbox);
         if (structurecomponent == null) {
            return null;
         } else {
            int j1 = structurecomponent.getBoundingBox().minY;
            if (j1 == structureboundingbox.minY) {
               for (int i1 = 3; i1 >= 1; i1--) {
                  structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, -1, -1, 0, 5, 5, i1 - 1, enumFacing);
                  if (!structurecomponent.getBoundingBox().intersectsWith(structureboundingbox)) {
                     return StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, -1, -1, 0, 5, 5, i1, enumFacing);
                  }
               }
            }

            return null;
         }
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         boolean flag = this.isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn);
         if (flag) {
            return false;
         } else {
            for (int i = 0; i < this.v; i++) {
               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 0, 0, i, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 1, 0, i, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 2, 0, i, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 3, 0, i, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 4, 0, i, structureBoundingBoxIn);

               for (int j = 1; j <= 3; j++) {
                  this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 0, j, i, structureBoundingBoxIn);
                  this.setBlockState(worldIn, Blocks.air.getDefaultState(), 1, j, i, structureBoundingBoxIn);
                  this.setBlockState(worldIn, Blocks.air.getDefaultState(), 2, j, i, structureBoundingBoxIn);
                  this.setBlockState(worldIn, Blocks.air.getDefaultState(), 3, j, i, structureBoundingBoxIn);
                  this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 4, j, i, structureBoundingBoxIn);
               }

               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 0, 4, i, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 1, 4, i, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 2, 4, i, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 3, 4, i, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 4, 4, i, structureBoundingBoxIn);
            }

            return true;
         }
      }
   }

   public static class Crossing extends StructureStrongholdPieces.Stronghold {
      private boolean W;
      private boolean O;
      private boolean y;
      private boolean Q;

      public Crossing() {
      }

      public Crossing(int i, Random random2, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(i);
         super.coordBaseMode = enumFacing;
         super.field_143013_d = this.getRandomDoor(random2);
         super.boundingBox = structureBoundingBox;
         this.W = random2.nextBoolean();
         this.O = random2.nextBoolean();
         this.y = random2.nextBoolean();
         this.Q = random2.nextInt(3) > 0;
      }

      @Override
      public void writeStructureToNBT(NBTTagCompound tagCompound) {
         super.writeStructureToNBT(tagCompound);
         tagCompound.setBoolean("leftLow", this.W);
         tagCompound.setBoolean("leftHigh", this.O);
         tagCompound.setBoolean("rightLow", this.y);
         tagCompound.setBoolean("rightHigh", this.Q);
      }

      @Override
      public void readStructureFromNBT(NBTTagCompound tagCompound) {
         super.readStructureFromNBT(tagCompound);
         this.W = tagCompound.getBoolean("leftLow");
         this.O = tagCompound.getBoolean("leftHigh");
         this.y = tagCompound.getBoolean("rightLow");
         this.Q = tagCompound.getBoolean("rightHigh");
      }

      @Override
      public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
         int i = 3;
         int j = 5;
         if (super.coordBaseMode == EnumFacing.WEST || super.coordBaseMode == EnumFacing.NORTH) {
            i = 8 - i;
            j = 8 - j;
         }

         this.getNextComponentNormal((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 5, 1);
         boolean flag = this.W;
         if (flag) {
            this.getNextComponentX((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, i, 1);
         }

         boolean flag1 = this.O;
         if (flag1) {
            this.getNextComponentX((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, j, 7);
         }

         boolean flag2 = this.y;
         if (flag2) {
            this.getNextComponentZ((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, i, 1);
         }

         if (this.Q) {
            this.getNextComponentZ((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, j, 7);
         }
      }

      public static StructureStrongholdPieces.Crossing G(List<StructureComponent> list, Random random2, int i, int j, int k, EnumFacing enumFacing, int l) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, -4, -3, 0, 10, 9, 11, enumFacing);
         return canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(list, structureboundingbox) == null
            ? new StructureStrongholdPieces.Crossing(l, random2, structureboundingbox, enumFacing)
            : null;
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         boolean flag = this.isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn);
         if (flag) {
            return false;
         } else {
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 9, 8, 10, true, randomIn, StructureStrongholdPieces.access$200());
            this.placeDoor(worldIn, randomIn, structureBoundingBoxIn, super.field_143013_d, 4, 3, 0);
            boolean flag1 = this.W;
            if (flag1) {
               this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 1, 0, 5, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            }

            boolean flag2 = this.y;
            if (flag2) {
               this.fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 3, 1, 9, 5, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            }

            boolean flag3 = this.O;
            if (flag3) {
               this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 7, 0, 7, 9, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            }

            boolean flag4 = this.Q;
            if (flag4) {
               this.fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 5, 7, 9, 7, 9, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            }

            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 10, 7, 3, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, 2, 1, 8, 2, 6, false, randomIn, StructureStrongholdPieces.access$200());
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 5, 4, 4, 9, false, randomIn, StructureStrongholdPieces.access$200());
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 8, 1, 5, 8, 4, 9, false, randomIn, StructureStrongholdPieces.access$200());
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, 4, 7, 3, 4, 9, false, randomIn, StructureStrongholdPieces.access$200());
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, 3, 5, 3, 3, 6, false, randomIn, StructureStrongholdPieces.access$200());
            this.fillWithBlocks(
               worldIn, structureBoundingBoxIn, 1, 3, 4, 3, 3, 4, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false
            );
            this.fillWithBlocks(
               worldIn, structureBoundingBoxIn, 1, 4, 6, 3, 4, 6, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false
            );
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 5, 1, 7, 7, 1, 8, false, randomIn, StructureStrongholdPieces.access$200());
            this.fillWithBlocks(
               worldIn, structureBoundingBoxIn, 5, 1, 9, 7, 1, 9, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false
            );
            this.fillWithBlocks(
               worldIn, structureBoundingBoxIn, 5, 2, 7, 7, 2, 7, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false
            );
            this.fillWithBlocks(
               worldIn, structureBoundingBoxIn, 4, 5, 7, 4, 5, 9, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false
            );
            this.fillWithBlocks(
               worldIn, structureBoundingBoxIn, 8, 5, 7, 8, 5, 9, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false
            );
            this.fillWithBlocks(
               worldIn, structureBoundingBoxIn, 5, 5, 7, 7, 5, 9, Blocks.double_stone_slab.getDefaultState(), Blocks.double_stone_slab.getDefaultState(), false
            );
            this.setBlockState(worldIn, Blocks.torch.getDefaultState(), 6, 5, 6, structureBoundingBoxIn);
            return true;
         }
      }
   }

   public static class LeftTurn extends StructureStrongholdPieces.Stronghold {
      public LeftTurn() {
      }

      public LeftTurn(int i, Random random2, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(i);
         super.coordBaseMode = enumFacing;
         super.field_143013_d = this.getRandomDoor(random2);
         super.boundingBox = structureBoundingBox;
      }

      @Override
      public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
         if (super.coordBaseMode != EnumFacing.NORTH && super.coordBaseMode != EnumFacing.EAST) {
            this.getNextComponentZ((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 1);
         } else {
            this.getNextComponentX((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 1);
         }
      }

      public static StructureStrongholdPieces.LeftTurn func_175867_a(
         List<StructureComponent> list, Random random2, int i, int j, int k, EnumFacing enumFacing, int l
      ) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, -1, -1, 0, 5, 5, 5, enumFacing);
         return canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(list, structureboundingbox) == null
            ? new StructureStrongholdPieces.LeftTurn(l, random2, structureboundingbox, enumFacing)
            : null;
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         if (this.isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn)) {
            return false;
         } else {
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 4, 4, true, randomIn, StructureStrongholdPieces.access$200());
            this.placeDoor(worldIn, randomIn, structureBoundingBoxIn, super.field_143013_d, 1, 1, 0);
            if (super.coordBaseMode != EnumFacing.NORTH && super.coordBaseMode != EnumFacing.EAST) {
               this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 3, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            } else {
               this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            }

            return true;
         }
      }
   }

   public static class Library extends StructureStrongholdPieces.Stronghold {
      private static final List<WeightedRandomChestContent> strongholdLibraryChestContents = Lists.newArrayList(
         new WeightedRandomChestContent(Items.book, 0, 1, 3, 20),
         new WeightedRandomChestContent(Items.paper, 0, 2, 7, 20),
         new WeightedRandomChestContent(Items.map, 0, 1, 1, 1),
         new WeightedRandomChestContent(Items.compass, 0, 1, 1, 1)
      );
      private boolean isLargeRoom;

      public Library() {
      }

      public Library(int i, Random random2, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(i);
         super.coordBaseMode = enumFacing;
         super.field_143013_d = this.getRandomDoor(random2);
         super.boundingBox = structureBoundingBox;
         this.isLargeRoom = structureBoundingBox.getYSize() > 6;
      }

      @Override
      public void writeStructureToNBT(NBTTagCompound tagCompound) {
         super.writeStructureToNBT(tagCompound);
         tagCompound.setBoolean("Tall", this.isLargeRoom);
      }

      @Override
      public void readStructureFromNBT(NBTTagCompound tagCompound) {
         super.readStructureFromNBT(tagCompound);
         this.isLargeRoom = tagCompound.getBoolean("Tall");
      }

      public static StructureStrongholdPieces.Library J(List<StructureComponent> list, Random random2, int i, int j, int k, EnumFacing enumFacing, int l) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, -4, -1, 0, 14, 11, 15, enumFacing);
         if (!canStrongholdGoDeeper(structureboundingbox) || StructureComponent.findIntersecting(list, structureboundingbox) != null) {
            structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, -4, -1, 0, 14, 6, 15, enumFacing);
            if (!canStrongholdGoDeeper(structureboundingbox) || StructureComponent.findIntersecting(list, structureboundingbox) != null) {
               return null;
            }
         }

         return new StructureStrongholdPieces.Library(l, random2, structureboundingbox, enumFacing);
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         boolean flag = this.isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn);
         if (flag) {
            return false;
         } else {
            int b0 = 11;
            boolean flag1 = this.isLargeRoom;
            if (!flag1) {
               b0 = 6;
            }

            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 13, b0 - 1, 14, true, randomIn, StructureStrongholdPieces.access$200());
            this.placeDoor(worldIn, randomIn, structureBoundingBoxIn, super.field_143013_d, 4, 1, 0);
            this.func_175805_a(
               worldIn, structureBoundingBoxIn, randomIn, 0.07F, 2, 1, 1, 11, 4, 13, Blocks.web.getDefaultState(), Blocks.web.getDefaultState(), false
            );

            for (int i = 1; i <= 13; i++) {
               int j = (i - 1) % 4;
               if (j == 0) {
                  this.fillWithBlocks(
                     worldIn, structureBoundingBoxIn, 1, 1, i, 1, 4, i, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false
                  );
                  this.fillWithBlocks(
                     worldIn, structureBoundingBoxIn, 12, 1, i, 12, 4, i, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false
                  );
                  this.setBlockState(worldIn, Blocks.torch.getDefaultState(), 2, 3, i, structureBoundingBoxIn);
                  this.setBlockState(worldIn, Blocks.torch.getDefaultState(), 11, 3, i, structureBoundingBoxIn);
                  if (this.isLargeRoom) {
                     this.fillWithBlocks(
                        worldIn, structureBoundingBoxIn, 1, 6, i, 1, 9, i, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false
                     );
                     this.fillWithBlocks(
                        worldIn, structureBoundingBoxIn, 12, 6, i, 12, 9, i, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false
                     );
                  }
               } else {
                  this.fillWithBlocks(
                     worldIn, structureBoundingBoxIn, 1, 1, i, 1, 4, i, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false
                  );
                  this.fillWithBlocks(
                     worldIn, structureBoundingBoxIn, 12, 1, i, 12, 4, i, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false
                  );
                  if (this.isLargeRoom) {
                     this.fillWithBlocks(
                        worldIn, structureBoundingBoxIn, 1, 6, i, 1, 9, i, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false
                     );
                     this.fillWithBlocks(
                        worldIn, structureBoundingBoxIn, 12, 6, i, 12, 9, i, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false
                     );
                  }
               }
            }

            for (int b1 = 3; b1 < 12; b1 += 2) {
               this.fillWithBlocks(
                  worldIn, structureBoundingBoxIn, 3, 1, b1, 4, 3, b1, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false
               );
               this.fillWithBlocks(
                  worldIn, structureBoundingBoxIn, 6, 1, b1, 7, 3, b1, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false
               );
               this.fillWithBlocks(
                  worldIn, structureBoundingBoxIn, 9, 1, b1, 10, 3, b1, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false
               );
            }

            boolean flag2 = this.isLargeRoom;
            if (flag2) {
               this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 1, 3, 5, 13, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
               this.fillWithBlocks(
                  worldIn, structureBoundingBoxIn, 10, 5, 1, 12, 5, 13, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false
               );
               this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 5, 1, 9, 5, 2, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
               this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 5, 12, 9, 5, 13, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
               this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 9, 5, 11, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 8, 5, 11, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 9, 5, 10, structureBoundingBoxIn);
               this.fillWithBlocks(
                  worldIn, structureBoundingBoxIn, 3, 6, 2, 3, 6, 12, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false
               );
               this.fillWithBlocks(
                  worldIn, structureBoundingBoxIn, 10, 6, 2, 10, 6, 10, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false
               );
               this.fillWithBlocks(
                  worldIn, structureBoundingBoxIn, 4, 6, 2, 9, 6, 2, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false
               );
               this.fillWithBlocks(
                  worldIn, structureBoundingBoxIn, 4, 6, 12, 8, 6, 12, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false
               );
               this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 9, 6, 11, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 8, 6, 11, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 9, 6, 10, structureBoundingBoxIn);
               int k = this.getMetadataWithOffset(Blocks.ladder, 3);
               this.setBlockState(worldIn, Blocks.ladder.getStateFromMeta(k), 10, 1, 13, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.ladder.getStateFromMeta(k), 10, 2, 13, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.ladder.getStateFromMeta(k), 10, 3, 13, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.ladder.getStateFromMeta(k), 10, 4, 13, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.ladder.getStateFromMeta(k), 10, 5, 13, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.ladder.getStateFromMeta(k), 10, 6, 13, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.ladder.getStateFromMeta(k), 10, 7, 13, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 7 - 1, 9, 7, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 7, 9, 7, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 7 - 1, 8, 7, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 7, 8, 7, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 7 - 1, 7, 7, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 7, 7, 7, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 7 - 2, 7, 7, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 7 + 1, 7, 7, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 7 - 1, 7, 7 - 1, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 7 - 1, 7, 7 + 1, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 7, 7, 7 - 1, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 7, 7, 7 + 1, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.torch.getDefaultState(), 7 - 2, 8, 7, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.torch.getDefaultState(), 7 + 1, 8, 7, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.torch.getDefaultState(), 7 - 1, 8, 7 - 1, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.torch.getDefaultState(), 7 - 1, 8, 7 + 1, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.torch.getDefaultState(), 7, 8, 7 - 1, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.torch.getDefaultState(), 7, 8, 7 + 1, structureBoundingBoxIn);
            }

            this.generateChestContents(
               worldIn,
               structureBoundingBoxIn,
               randomIn,
               3,
               3,
               5,
               WeightedRandomChestContent.func_177629_a(strongholdLibraryChestContents, Items.enchanted_book.getRandom(randomIn, 1, 5, 2)),
               1 + randomIn.nextInt(4)
            );
            boolean flag3 = this.isLargeRoom;
            if (flag3) {
               this.setBlockState(worldIn, Blocks.air.getDefaultState(), 12, 9, 1, structureBoundingBoxIn);
               this.generateChestContents(
                  worldIn,
                  structureBoundingBoxIn,
                  randomIn,
                  12,
                  8,
                  1,
                  WeightedRandomChestContent.func_177629_a(strongholdLibraryChestContents, Items.enchanted_book.getRandom(randomIn, 1, 5, 2)),
                  1 + randomIn.nextInt(4)
               );
            }

            return true;
         }
      }
   }

   public static class PieceWeight {
      public Class<? extends StructureStrongholdPieces.Stronghold> pieceClass;
      public final int pieceWeight;
      public int instancesSpawned;
      public int instancesLimit;

      public PieceWeight(Class<? extends StructureStrongholdPieces.Stronghold> class2, int i, int j) {
         this.pieceClass = class2;
         this.pieceWeight = i;
         this.instancesLimit = j;
      }

      public boolean canSpawnMoreStructuresOfType(int i) {
         return this.instancesLimit == 0 || this.instancesSpawned < this.instancesLimit;
      }

      public boolean canSpawnMoreStructures() {
         return this.instancesLimit == 0 || this.instancesSpawned < this.instancesLimit;
      }
   }

   public static class PortalRoom extends StructureStrongholdPieces.Stronghold {
      private boolean hasSpawner;

      public PortalRoom() {
      }

      public PortalRoom(int i, Random random2, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(i);
         super.coordBaseMode = enumFacing;
         super.boundingBox = structureBoundingBox;
      }

      @Override
      public void writeStructureToNBT(NBTTagCompound tagCompound) {
         super.writeStructureToNBT(tagCompound);
         tagCompound.setBoolean("Mob", this.hasSpawner);
      }

      @Override
      public void readStructureFromNBT(NBTTagCompound tagCompound) {
         super.readStructureFromNBT(tagCompound);
         this.hasSpawner = tagCompound.getBoolean("Mob");
      }

      @Override
      public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
         if (componentIn != null) {
            ((StructureStrongholdPieces.Stairs2)componentIn).strongholdPortalRoom = this;
         }
      }

      public static StructureStrongholdPieces.PortalRoom func_175865_a(
         List<StructureComponent> list, Random random2, int i, int j, int k, EnumFacing enumFacing, int l
      ) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, -4, -1, 0, 11, 8, 16, enumFacing);
         return canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(list, structureboundingbox) == null
            ? new StructureStrongholdPieces.PortalRoom(l, random2, structureboundingbox, enumFacing)
            : null;
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 10, 7, 15, false, randomIn, StructureStrongholdPieces.access$200());
         this.placeDoor(worldIn, randomIn, structureBoundingBoxIn, StructureStrongholdPieces.Stronghold.Door.GRATES, 4, 1, 0);
         int i = 6;
         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, i, 1, 1, i, 14, false, randomIn, StructureStrongholdPieces.access$200());
         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 9, i, 1, 9, i, 14, false, randomIn, StructureStrongholdPieces.access$200());
         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 2, i, 1, 8, i, 2, false, randomIn, StructureStrongholdPieces.access$200());
         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 2, i, 14, 8, i, 14, false, randomIn, StructureStrongholdPieces.access$200());
         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 2, 1, 4, false, randomIn, StructureStrongholdPieces.access$200());
         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 8, 1, 1, 9, 1, 4, false, randomIn, StructureStrongholdPieces.access$200());
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 1, 1, 1, 1, 1, 3, Blocks.flowing_lava.getDefaultState(), Blocks.flowing_lava.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 9, 1, 1, 9, 1, 3, Blocks.flowing_lava.getDefaultState(), Blocks.flowing_lava.getDefaultState(), false
         );
         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 3, 1, 8, 7, 1, 12, false, randomIn, StructureStrongholdPieces.access$200());
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 4, 1, 9, 6, 1, 11, Blocks.flowing_lava.getDefaultState(), Blocks.flowing_lava.getDefaultState(), false
         );

         for (int b0 = 3; b0 < 14; b0 += 2) {
            this.fillWithBlocks(
               worldIn, structureBoundingBoxIn, 0, 3, b0, 0, 4, b0, Blocks.iron_bars.getDefaultState(), Blocks.iron_bars.getDefaultState(), false
            );
            this.fillWithBlocks(
               worldIn, structureBoundingBoxIn, 10, 3, b0, 10, 4, b0, Blocks.iron_bars.getDefaultState(), Blocks.iron_bars.getDefaultState(), false
            );
         }

         for (int b1 = 2; b1 < 9; b1 += 2) {
            this.fillWithBlocks(
               worldIn, structureBoundingBoxIn, b1, 3, 15, b1, 4, 15, Blocks.iron_bars.getDefaultState(), Blocks.iron_bars.getDefaultState(), false
            );
         }

         int j4 = this.getMetadataWithOffset(Blocks.stone_brick_stairs, 3);
         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 5, 6, 1, 7, false, randomIn, StructureStrongholdPieces.access$200());
         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 2, 6, 6, 2, 7, false, randomIn, StructureStrongholdPieces.access$200());
         this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 3, 7, 6, 3, 7, false, randomIn, StructureStrongholdPieces.access$200());

         for (int j = 4; j <= 6; j++) {
            this.setBlockState(worldIn, Blocks.stone_brick_stairs.getStateFromMeta(j4), j, 1, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.stone_brick_stairs.getStateFromMeta(j4), j, 2, 5, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.stone_brick_stairs.getStateFromMeta(j4), j, 3, 6, structureBoundingBoxIn);
         }

         int k4 = EnumFacing.NORTH.getHorizontalIndex();
         int k = EnumFacing.SOUTH.getHorizontalIndex();
         int l = EnumFacing.EAST.getHorizontalIndex();
         int i1 = EnumFacing.WEST.getHorizontalIndex();
         if (super.coordBaseMode != null) {
            switch (super.coordBaseMode) {
               case SOUTH:
                  k4 = EnumFacing.SOUTH.getHorizontalIndex();
                  k = EnumFacing.NORTH.getHorizontalIndex();
                  break;
               case WEST:
                  k4 = EnumFacing.WEST.getHorizontalIndex();
                  k = EnumFacing.EAST.getHorizontalIndex();
                  l = EnumFacing.SOUTH.getHorizontalIndex();
                  i1 = EnumFacing.NORTH.getHorizontalIndex();
                  break;
               case EAST:
                  k4 = EnumFacing.EAST.getHorizontalIndex();
                  k = EnumFacing.WEST.getHorizontalIndex();
                  l = EnumFacing.SOUTH.getHorizontalIndex();
                  i1 = EnumFacing.NORTH.getHorizontalIndex();
            }
         }

         IBlockState iblockstate12 = Blocks.end_portal_frame.getStateFromMeta(k4);
         PropertyBool propertybool12 = BlockEndPortalFrame.EYE;
         float f;
         int j1 = (f = randomIn.nextFloat() - 0.9F) == 0.0F ? 0 : (f < 0.0F ? -1 : 1);
         PropertyBool propertybool = propertybool12;
         IBlockState iblockstate = iblockstate12;
         this.setBlockState(worldIn, iblockstate.withProperty(propertybool, j1 > 0), 4, 3, 8, structureBoundingBoxIn);
         iblockstate12 = Blocks.end_portal_frame.getStateFromMeta(k4);
         propertybool12 = BlockEndPortalFrame.EYE;
         float f1;
         int k1 = (f1 = randomIn.nextFloat() - 0.9F) == 0.0F ? 0 : (f1 < 0.0F ? -1 : 1);
         PropertyBool propertybool1 = propertybool12;
         IBlockState iblockstate1 = iblockstate12;
         this.setBlockState(worldIn, iblockstate1.withProperty(propertybool1, k1 > 0), 5, 3, 8, structureBoundingBoxIn);
         iblockstate12 = Blocks.end_portal_frame.getStateFromMeta(k4);
         propertybool12 = BlockEndPortalFrame.EYE;
         float f2;
         int l1 = (f2 = randomIn.nextFloat() - 0.9F) == 0.0F ? 0 : (f2 < 0.0F ? -1 : 1);
         PropertyBool propertybool2 = propertybool12;
         IBlockState iblockstate2 = iblockstate12;
         this.setBlockState(worldIn, iblockstate2.withProperty(propertybool2, l1 > 0), 6, 3, 8, structureBoundingBoxIn);
         iblockstate12 = Blocks.end_portal_frame.getStateFromMeta(k);
         propertybool12 = BlockEndPortalFrame.EYE;
         float f3;
         int i2 = (f3 = randomIn.nextFloat() - 0.9F) == 0.0F ? 0 : (f3 < 0.0F ? -1 : 1);
         PropertyBool propertybool3 = propertybool12;
         IBlockState iblockstate3 = iblockstate12;
         this.setBlockState(worldIn, iblockstate3.withProperty(propertybool3, i2 > 0), 4, 3, 12, structureBoundingBoxIn);
         iblockstate12 = Blocks.end_portal_frame.getStateFromMeta(k);
         propertybool12 = BlockEndPortalFrame.EYE;
         float f4;
         int j2 = (f4 = randomIn.nextFloat() - 0.9F) == 0.0F ? 0 : (f4 < 0.0F ? -1 : 1);
         PropertyBool propertybool4 = propertybool12;
         IBlockState iblockstate4 = iblockstate12;
         this.setBlockState(worldIn, iblockstate4.withProperty(propertybool4, j2 > 0), 5, 3, 12, structureBoundingBoxIn);
         iblockstate12 = Blocks.end_portal_frame.getStateFromMeta(k);
         propertybool12 = BlockEndPortalFrame.EYE;
         float f5;
         int k2 = (f5 = randomIn.nextFloat() - 0.9F) == 0.0F ? 0 : (f5 < 0.0F ? -1 : 1);
         PropertyBool propertybool5 = propertybool12;
         IBlockState iblockstate5 = iblockstate12;
         this.setBlockState(worldIn, iblockstate5.withProperty(propertybool5, k2 > 0), 6, 3, 12, structureBoundingBoxIn);
         iblockstate12 = Blocks.end_portal_frame.getStateFromMeta(l);
         propertybool12 = BlockEndPortalFrame.EYE;
         float f6;
         int l2 = (f6 = randomIn.nextFloat() - 0.9F) == 0.0F ? 0 : (f6 < 0.0F ? -1 : 1);
         PropertyBool propertybool6 = propertybool12;
         IBlockState iblockstate6 = iblockstate12;
         this.setBlockState(worldIn, iblockstate6.withProperty(propertybool6, l2 > 0), 3, 3, 9, structureBoundingBoxIn);
         iblockstate12 = Blocks.end_portal_frame.getStateFromMeta(l);
         propertybool12 = BlockEndPortalFrame.EYE;
         float f7;
         int i3 = (f7 = randomIn.nextFloat() - 0.9F) == 0.0F ? 0 : (f7 < 0.0F ? -1 : 1);
         PropertyBool propertybool7 = propertybool12;
         IBlockState iblockstate7 = iblockstate12;
         this.setBlockState(worldIn, iblockstate7.withProperty(propertybool7, i3 > 0), 3, 3, 10, structureBoundingBoxIn);
         iblockstate12 = Blocks.end_portal_frame.getStateFromMeta(l);
         propertybool12 = BlockEndPortalFrame.EYE;
         float f8;
         int j3 = (f8 = randomIn.nextFloat() - 0.9F) == 0.0F ? 0 : (f8 < 0.0F ? -1 : 1);
         PropertyBool propertybool8 = propertybool12;
         IBlockState iblockstate8 = iblockstate12;
         this.setBlockState(worldIn, iblockstate8.withProperty(propertybool8, j3 > 0), 3, 3, 11, structureBoundingBoxIn);
         iblockstate12 = Blocks.end_portal_frame.getStateFromMeta(i1);
         propertybool12 = BlockEndPortalFrame.EYE;
         float f9;
         int k3 = (f9 = randomIn.nextFloat() - 0.9F) == 0.0F ? 0 : (f9 < 0.0F ? -1 : 1);
         PropertyBool propertybool9 = propertybool12;
         IBlockState iblockstate9 = iblockstate12;
         this.setBlockState(worldIn, iblockstate9.withProperty(propertybool9, k3 > 0), 7, 3, 9, structureBoundingBoxIn);
         iblockstate12 = Blocks.end_portal_frame.getStateFromMeta(i1);
         propertybool12 = BlockEndPortalFrame.EYE;
         float f10;
         int l3 = (f10 = randomIn.nextFloat() - 0.9F) == 0.0F ? 0 : (f10 < 0.0F ? -1 : 1);
         PropertyBool propertybool10 = propertybool12;
         IBlockState iblockstate10 = iblockstate12;
         this.setBlockState(worldIn, iblockstate10.withProperty(propertybool10, l3 > 0), 7, 3, 10, structureBoundingBoxIn);
         iblockstate12 = Blocks.end_portal_frame.getStateFromMeta(i1);
         propertybool12 = BlockEndPortalFrame.EYE;
         float f11;
         int i4 = (f11 = randomIn.nextFloat() - 0.9F) == 0.0F ? 0 : (f11 < 0.0F ? -1 : 1);
         PropertyBool propertybool11 = propertybool12;
         IBlockState iblockstate11 = iblockstate12;
         this.setBlockState(worldIn, iblockstate11.withProperty(propertybool11, i4 > 0), 7, 3, 11, structureBoundingBoxIn);
         boolean flag = this.hasSpawner;
         if (!flag) {
            i = this.getYWithOffset(3);
            BlockPos blockpos = new BlockPos(this.getXWithOffset(5, 6), i, this.getZWithOffset(5, 6));
            if (structureBoundingBoxIn.isVecInside(blockpos)) {
               this.hasSpawner = true;
               worldIn.setBlockState(blockpos, Blocks.mob_spawner.getDefaultState(), 2);
               TileEntity tileentity = worldIn.getTileEntity(blockpos);
               if (tileentity instanceof TileEntityMobSpawner) {
                  ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic().setEntityName("Silverfish");
               }
            }
         }

         return true;
      }
   }

   public static class Prison extends StructureStrongholdPieces.Stronghold {
      public Prison() {
      }

      public Prison(int i, Random random2, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(i);
         super.coordBaseMode = enumFacing;
         super.field_143013_d = this.getRandomDoor(random2);
         super.boundingBox = structureBoundingBox;
      }

      @Override
      public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
         this.getNextComponentNormal((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 1);
      }

      public static StructureStrongholdPieces.Prison b(List<StructureComponent> list, Random random2, int i, int j, int k, EnumFacing enumFacing, int l) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, -1, -1, 0, 9, 5, 11, enumFacing);
         return canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(list, structureboundingbox) == null
            ? new StructureStrongholdPieces.Prison(l, random2, structureboundingbox, enumFacing)
            : null;
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         if (this.isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn)) {
            return false;
         } else {
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 8, 4, 10, true, randomIn, StructureStrongholdPieces.access$200());
            this.placeDoor(worldIn, randomIn, structureBoundingBoxIn, super.field_143013_d, 1, 1, 0);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 10, 3, 3, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 3, 1, false, randomIn, StructureStrongholdPieces.access$200());
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 3, 4, 3, 3, false, randomIn, StructureStrongholdPieces.access$200());
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 7, 4, 3, 7, false, randomIn, StructureStrongholdPieces.access$200());
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 9, 4, 3, 9, false, randomIn, StructureStrongholdPieces.access$200());
            this.fillWithBlocks(
               worldIn, structureBoundingBoxIn, 4, 1, 4, 4, 3, 6, Blocks.iron_bars.getDefaultState(), Blocks.iron_bars.getDefaultState(), false
            );
            this.fillWithBlocks(
               worldIn, structureBoundingBoxIn, 5, 1, 5, 7, 3, 5, Blocks.iron_bars.getDefaultState(), Blocks.iron_bars.getDefaultState(), false
            );
            this.setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), 4, 3, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), 4, 3, 8, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.iron_door.getStateFromMeta(this.getMetadataWithOffset(Blocks.iron_door, 3)), 4, 1, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.iron_door.getStateFromMeta(this.getMetadataWithOffset(Blocks.iron_door, 3) + 8), 4, 2, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.iron_door.getStateFromMeta(this.getMetadataWithOffset(Blocks.iron_door, 3)), 4, 1, 8, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.iron_door.getStateFromMeta(this.getMetadataWithOffset(Blocks.iron_door, 3) + 8), 4, 2, 8, structureBoundingBoxIn);
            return true;
         }
      }
   }

   public static class RightTurn extends StructureStrongholdPieces.LeftTurn {
      @Override
      public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
         if (super.coordBaseMode != EnumFacing.NORTH && super.coordBaseMode != EnumFacing.EAST) {
            this.getNextComponentX((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 1);
         } else {
            this.getNextComponentZ((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 1);
         }
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         if (this.isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn)) {
            return false;
         } else {
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 4, 4, true, randomIn, StructureStrongholdPieces.access$200());
            this.placeDoor(worldIn, randomIn, structureBoundingBoxIn, super.field_143013_d, 1, 1, 0);
            if (super.coordBaseMode != EnumFacing.NORTH && super.coordBaseMode != EnumFacing.EAST) {
               this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            } else {
               this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 3, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            }

            return true;
         }
      }
   }

   public static class RoomCrossing extends StructureStrongholdPieces.Stronghold {
      private static final List<WeightedRandomChestContent> strongholdRoomCrossingChestContents = Lists.newArrayList(
         new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10),
         new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5),
         new WeightedRandomChestContent(Items.redstone, 0, 4, 9, 5),
         new WeightedRandomChestContent(Items.coal, 0, 3, 8, 10),
         new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15),
         new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15),
         new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 1)
      );
      public int roomType;

      public RoomCrossing() {
      }

      public RoomCrossing(int i, Random random2, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(i);
         super.coordBaseMode = enumFacing;
         super.field_143013_d = this.getRandomDoor(random2);
         super.boundingBox = structureBoundingBox;
         this.roomType = random2.nextInt(5);
      }

      @Override
      public void writeStructureToNBT(NBTTagCompound tagCompound) {
         super.writeStructureToNBT(tagCompound);
         tagCompound.setInteger("Type", this.roomType);
      }

      @Override
      public void readStructureFromNBT(NBTTagCompound tagCompound) {
         super.readStructureFromNBT(tagCompound);
         this.roomType = tagCompound.getInteger("Type");
      }

      @Override
      public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
         this.getNextComponentNormal((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 4, 1);
         this.getNextComponentX((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 4);
         this.getNextComponentZ((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 4);
      }

      public static StructureStrongholdPieces.RoomCrossing Y(List<StructureComponent> list, Random random2, int i, int j, int k, EnumFacing enumFacing, int l) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, -4, -1, 0, 11, 7, 11, enumFacing);
         return canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(list, structureboundingbox) == null
            ? new StructureStrongholdPieces.RoomCrossing(l, random2, structureboundingbox, enumFacing)
            : null;
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         boolean flag = this.isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn);
         if (flag) {
            return false;
         } else {
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 10, 6, 10, true, randomIn, StructureStrongholdPieces.access$200());
            this.placeDoor(worldIn, randomIn, structureBoundingBoxIn, super.field_143013_d, 4, 1, 0);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 10, 6, 3, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 4, 0, 3, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 1, 4, 10, 3, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            int j = this.roomType;
            switch (j) {
               case 0:
                  this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 5, 1, 5, structureBoundingBoxIn);
                  this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 5, 2, 5, structureBoundingBoxIn);
                  this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 5, 3, 5, structureBoundingBoxIn);
                  this.setBlockState(worldIn, Blocks.torch.getDefaultState(), 4, 3, 5, structureBoundingBoxIn);
                  this.setBlockState(worldIn, Blocks.torch.getDefaultState(), 6, 3, 5, structureBoundingBoxIn);
                  this.setBlockState(worldIn, Blocks.torch.getDefaultState(), 5, 3, 4, structureBoundingBoxIn);
                  this.setBlockState(worldIn, Blocks.torch.getDefaultState(), 5, 3, 6, structureBoundingBoxIn);
                  this.setBlockState(worldIn, Blocks.stone_slab.getDefaultState(), 4, 1, 4, structureBoundingBoxIn);
                  this.setBlockState(worldIn, Blocks.stone_slab.getDefaultState(), 4, 1, 5, structureBoundingBoxIn);
                  this.setBlockState(worldIn, Blocks.stone_slab.getDefaultState(), 4, 1, 6, structureBoundingBoxIn);
                  this.setBlockState(worldIn, Blocks.stone_slab.getDefaultState(), 6, 1, 4, structureBoundingBoxIn);
                  this.setBlockState(worldIn, Blocks.stone_slab.getDefaultState(), 6, 1, 5, structureBoundingBoxIn);
                  this.setBlockState(worldIn, Blocks.stone_slab.getDefaultState(), 6, 1, 6, structureBoundingBoxIn);
                  this.setBlockState(worldIn, Blocks.stone_slab.getDefaultState(), 5, 1, 4, structureBoundingBoxIn);
                  this.setBlockState(worldIn, Blocks.stone_slab.getDefaultState(), 5, 1, 6, structureBoundingBoxIn);
                  break;
               case 1:
                  for (int j1 = 0; j1 < 5; j1++) {
                     this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 3, 1, 3 + j1, structureBoundingBoxIn);
                     this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 7, 1, 3 + j1, structureBoundingBoxIn);
                     this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 3 + j1, 1, 3, structureBoundingBoxIn);
                     this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 3 + j1, 1, 7, structureBoundingBoxIn);
                  }

                  this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 5, 1, 5, structureBoundingBoxIn);
                  this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 5, 2, 5, structureBoundingBoxIn);
                  this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 5, 3, 5, structureBoundingBoxIn);
                  this.setBlockState(worldIn, Blocks.flowing_water.getDefaultState(), 5, 4, 5, structureBoundingBoxIn);
                  break;
               case 2:
                  for (int i = 1; i <= 9; i++) {
                     this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 1, 3, i, structureBoundingBoxIn);
                     this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 9, 3, i, structureBoundingBoxIn);
                  }

                  for (int k = 1; k <= 9; k++) {
                     this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), k, 3, 1, structureBoundingBoxIn);
                     this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), k, 3, 9, structureBoundingBoxIn);
                  }

                  this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 5, 1, 4, structureBoundingBoxIn);
                  this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 5, 1, 6, structureBoundingBoxIn);
                  this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 5, 3, 4, structureBoundingBoxIn);
                  this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 5, 3, 6, structureBoundingBoxIn);
                  this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, 1, 5, structureBoundingBoxIn);
                  this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 6, 1, 5, structureBoundingBoxIn);
                  this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, 3, 5, structureBoundingBoxIn);
                  this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 6, 3, 5, structureBoundingBoxIn);

                  for (int l = 1; l <= 3; l++) {
                     this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, l, 4, structureBoundingBoxIn);
                     this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 6, l, 4, structureBoundingBoxIn);
                     this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, l, 6, structureBoundingBoxIn);
                     this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 6, l, 6, structureBoundingBoxIn);
                  }

                  this.setBlockState(worldIn, Blocks.torch.getDefaultState(), 5, 3, 5, structureBoundingBoxIn);

                  for (int i1 = 2; i1 <= 8; i1++) {
                     this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 2, 3, i1, structureBoundingBoxIn);
                     this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 3, 3, i1, structureBoundingBoxIn);
                     if (i1 <= 3 || i1 >= 7) {
                        this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 4, 3, i1, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 5, 3, i1, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 6, 3, i1, structureBoundingBoxIn);
                     }

                     this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 7, 3, i1, structureBoundingBoxIn);
                     this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 8, 3, i1, structureBoundingBoxIn);
                  }

                  this.setBlockState(
                     worldIn,
                     Blocks.ladder.getStateFromMeta(this.getMetadataWithOffset(Blocks.ladder, EnumFacing.WEST.getIndex())),
                     9,
                     1,
                     3,
                     structureBoundingBoxIn
                  );
                  this.setBlockState(
                     worldIn,
                     Blocks.ladder.getStateFromMeta(this.getMetadataWithOffset(Blocks.ladder, EnumFacing.WEST.getIndex())),
                     9,
                     2,
                     3,
                     structureBoundingBoxIn
                  );
                  this.setBlockState(
                     worldIn,
                     Blocks.ladder.getStateFromMeta(this.getMetadataWithOffset(Blocks.ladder, EnumFacing.WEST.getIndex())),
                     9,
                     3,
                     3,
                     structureBoundingBoxIn
                  );
                  this.generateChestContents(
                     worldIn,
                     structureBoundingBoxIn,
                     randomIn,
                     3,
                     4,
                     8,
                     WeightedRandomChestContent.func_177629_a(strongholdRoomCrossingChestContents, Items.enchanted_book.getRandom(randomIn)),
                     1 + randomIn.nextInt(4)
                  );
            }

            return true;
         }
      }
   }

   public static class Stairs extends StructureStrongholdPieces.Stronghold {
      private boolean field_75024_a;

      public Stairs() {
      }

      public Stairs(int i, Random random2, int j, int k) {
         super(i);
         this.field_75024_a = true;
         super.coordBaseMode = EnumFacing.Plane.HORIZONTAL.random(random2);
         super.field_143013_d = StructureStrongholdPieces.Stronghold.Door.OPENING;
         switch (super.coordBaseMode) {
            case SOUTH:
            case NORTH:
               super.boundingBox = new StructureBoundingBox(j, 64, k, j + 5 - 1, 74, k + 5 - 1);
               break;
            default:
               super.boundingBox = new StructureBoundingBox(j, 64, k, j + 5 - 1, 74, k + 5 - 1);
         }
      }

      public Stairs(int i, Random random2, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(i);
         this.field_75024_a = false;
         super.coordBaseMode = enumFacing;
         super.field_143013_d = this.getRandomDoor(random2);
         super.boundingBox = structureBoundingBox;
      }

      @Override
      public void writeStructureToNBT(NBTTagCompound tagCompound) {
         super.writeStructureToNBT(tagCompound);
         tagCompound.setBoolean("Source", this.field_75024_a);
      }

      @Override
      public void readStructureFromNBT(NBTTagCompound tagCompound) {
         super.readStructureFromNBT(tagCompound);
         this.field_75024_a = tagCompound.getBoolean("Source");
      }

      @Override
      public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
         if (this.field_75024_a) {
            StructureStrongholdPieces.strongComponentType = StructureStrongholdPieces.Crossing.class;
         }

         this.getNextComponentNormal((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 1);
      }

      public static StructureStrongholdPieces.Stairs func_175863_a(
         List<StructureComponent> list, Random random2, int i, int j, int k, EnumFacing enumFacing, int l
      ) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, -1, -7, 0, 5, 11, 5, enumFacing);
         return canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(list, structureboundingbox) == null
            ? new StructureStrongholdPieces.Stairs(l, random2, structureboundingbox, enumFacing)
            : null;
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         if (this.isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn)) {
            return false;
         } else {
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 10, 4, true, randomIn, StructureStrongholdPieces.access$200());
            this.placeDoor(worldIn, randomIn, structureBoundingBoxIn, super.field_143013_d, 1, 7, 0);
            this.placeDoor(worldIn, randomIn, structureBoundingBoxIn, StructureStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 4);
            this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 2, 6, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 1, 5, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 1, 6, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 1, 5, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 1, 4, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 1, 5, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 2, 4, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 3, 3, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 3, 4, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 3, 3, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 3, 2, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 3, 3, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 2, 2, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 1, 1, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 1, 2, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 1, 1, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 1, 1, 3, structureBoundingBoxIn);
            return true;
         }
      }
   }

   public static class Stairs2 extends StructureStrongholdPieces.Stairs {
      public StructureStrongholdPieces.PieceWeight strongholdPieceWeight;
      public StructureStrongholdPieces.PortalRoom strongholdPortalRoom;
      public List<StructureComponent> field_75026_c = Lists.newArrayList();

      public Stairs2() {
      }

      public Stairs2(int i, Random random2, int j, int k) {
         super(0, random2, j, k);
      }

      @Override
      public BlockPos getBoundingBoxCenter() {
         return this.strongholdPortalRoom != null ? this.strongholdPortalRoom.getBoundingBoxCenter() : super.getBoundingBoxCenter();
      }
   }

   public static class StairsStraight extends StructureStrongholdPieces.Stronghold {
      public StairsStraight() {
      }

      public StairsStraight(int i, Random random2, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(i);
         super.coordBaseMode = enumFacing;
         super.field_143013_d = this.getRandomDoor(random2);
         super.boundingBox = structureBoundingBox;
      }

      @Override
      public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
         this.getNextComponentNormal((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 1);
      }

      public static StructureStrongholdPieces.StairsStraight b(List<StructureComponent> list, Random random2, int i, int j, int k, EnumFacing enumFacing, int l) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, -1, -7, 0, 5, 11, 8, enumFacing);
         return canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(list, structureboundingbox) == null
            ? new StructureStrongholdPieces.StairsStraight(l, random2, structureboundingbox, enumFacing)
            : null;
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         if (this.isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn)) {
            return false;
         } else {
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 10, 7, true, randomIn, StructureStrongholdPieces.access$200());
            this.placeDoor(worldIn, randomIn, structureBoundingBoxIn, super.field_143013_d, 1, 7, 0);
            this.placeDoor(worldIn, randomIn, structureBoundingBoxIn, StructureStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 7);
            int i = this.getMetadataWithOffset(Blocks.stone_stairs, 2);

            for (int j = 0; j < 6; j++) {
               this.setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(i), 1, 6 - j, 1 + j, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(i), 2, 6 - j, 1 + j, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(i), 3, 6 - j, 1 + j, structureBoundingBoxIn);
               if (j < 5) {
                  this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 1, 5 - j, 1 + j, structureBoundingBoxIn);
                  this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 2, 5 - j, 1 + j, structureBoundingBoxIn);
                  this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 3, 5 - j, 1 + j, structureBoundingBoxIn);
               }
            }

            return true;
         }
      }
   }

   public static class Stones extends StructureComponent.BlockSelector {
      public Stones() {
      }

      @Override
      public void selectBlocks(Random rand, int x, int y, int z, boolean bl) {
         if (bl) {
            float f = rand.nextFloat();
            float f1;
            int i = (f1 = f - 0.2F) == 0.0F ? 0 : (f1 < 0.0F ? -1 : 1);
            if (i < 0) {
               super.blockstate = Blocks.stonebrick.getStateFromMeta(BlockStoneBrick.CRACKED_META);
            } else {
               float f2;
               int j = (f2 = f - 0.5F) == 0.0F ? 0 : (f2 < 0.0F ? -1 : 1);
               if (j < 0) {
                  super.blockstate = Blocks.stonebrick.getStateFromMeta(BlockStoneBrick.MOSSY_META);
               } else if (f < 0.55F) {
                  super.blockstate = Blocks.monster_egg.getStateFromMeta(BlockSilverfish.EnumType.STONEBRICK.getMetadata());
               } else {
                  super.blockstate = Blocks.stonebrick.getDefaultState();
               }
            }
         } else {
            super.blockstate = Blocks.air.getDefaultState();
         }
      }

   }

   public static class Straight extends StructureStrongholdPieces.Stronghold {
      private boolean expandsX;
      private boolean expandsZ;

      public Straight() {
      }

      public Straight(int i, Random random2, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(i);
         super.coordBaseMode = enumFacing;
         super.field_143013_d = this.getRandomDoor(random2);
         super.boundingBox = structureBoundingBox;
         this.expandsX = random2.nextInt(2) == 0;
         this.expandsZ = random2.nextInt(2) == 0;
      }

      @Override
      public void writeStructureToNBT(NBTTagCompound tagCompound) {
         super.writeStructureToNBT(tagCompound);
         tagCompound.setBoolean("Left", this.expandsX);
         tagCompound.setBoolean("Right", this.expandsZ);
      }

      @Override
      public void readStructureFromNBT(NBTTagCompound tagCompound) {
         super.readStructureFromNBT(tagCompound);
         this.expandsX = tagCompound.getBoolean("Left");
         this.expandsZ = tagCompound.getBoolean("Right");
      }

      @Override
      public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
         this.getNextComponentNormal((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 1);
         if (this.expandsX) {
            this.getNextComponentX((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 2);
         }

         if (this.expandsZ) {
            this.getNextComponentZ((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 2);
         }
      }

      public static StructureStrongholdPieces.Straight A(List<StructureComponent> list, Random random2, int i, int j, int k, EnumFacing enumFacing, int l) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, -1, -1, 0, 5, 5, 7, enumFacing);
         return canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(list, structureboundingbox) == null
            ? new StructureStrongholdPieces.Straight(l, random2, structureboundingbox, enumFacing)
            : null;
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         boolean flag = this.isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn);
         if (flag) {
            return false;
         } else {
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 4, 6, true, randomIn, StructureStrongholdPieces.access$200());
            this.placeDoor(worldIn, randomIn, structureBoundingBoxIn, super.field_143013_d, 1, 1, 0);
            this.placeDoor(worldIn, randomIn, structureBoundingBoxIn, StructureStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 6);
            this.randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 1, 2, 1, Blocks.torch.getDefaultState());
            this.randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 3, 2, 1, Blocks.torch.getDefaultState());
            this.randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 1, 2, 5, Blocks.torch.getDefaultState());
            this.randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 3, 2, 5, Blocks.torch.getDefaultState());
            boolean flag1 = this.expandsX;
            if (flag1) {
               this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 2, 0, 3, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            }

            boolean flag2 = this.expandsZ;
            if (flag2) {
               this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 2, 4, 3, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            }

            return true;
         }
      }
   }

   public abstract static class Stronghold extends StructureComponent {
      public StructureStrongholdPieces.Stronghold.Door field_143013_d = StructureStrongholdPieces.Stronghold.Door.OPENING;

      public Stronghold() {
      }

      public Stronghold(int i) {
         super(i);
      }

      @Override
      public void writeStructureToNBT(NBTTagCompound tagCompound) {
         tagCompound.setString("EntryDoor", this.field_143013_d.name());
      }

      @Override
      public void readStructureFromNBT(NBTTagCompound tagCompound) {
         this.field_143013_d = StructureStrongholdPieces.Stronghold.Door.valueOf(tagCompound.getString("EntryDoor"));
      }

      public void placeDoor(
         World worldIn, Random random2, StructureBoundingBox structureBoundingBox, StructureStrongholdPieces.Stronghold.Door door, int i, int j, int k
      ) {
         switch (door) {
            case OPENING:
            default:
               this.fillWithBlocks(
                  worldIn, structureBoundingBox, i, j, k, i + 3 - 1, j + 3 - 1, k, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false
               );
               break;
            case WOOD_DOOR:
               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), i, j, k, structureBoundingBox);
               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), i, j + 1, k, structureBoundingBox);
               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), i, j + 2, k, structureBoundingBox);
               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), i + 1, j + 2, k, structureBoundingBox);
               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), i + 2, j + 2, k, structureBoundingBox);
               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), i + 2, j + 1, k, structureBoundingBox);
               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), i + 2, j, k, structureBoundingBox);
               this.setBlockState(worldIn, Blocks.oak_door.getDefaultState(), i + 1, j, k, structureBoundingBox);
               this.setBlockState(worldIn, Blocks.oak_door.getStateFromMeta(8), i + 1, j + 1, k, structureBoundingBox);
               break;
            case GRATES:
               this.setBlockState(worldIn, Blocks.air.getDefaultState(), i + 1, j, k, structureBoundingBox);
               this.setBlockState(worldIn, Blocks.air.getDefaultState(), i + 1, j + 1, k, structureBoundingBox);
               this.setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), i, j, k, structureBoundingBox);
               this.setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), i, j + 1, k, structureBoundingBox);
               this.setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), i, j + 2, k, structureBoundingBox);
               this.setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), i + 1, j + 2, k, structureBoundingBox);
               this.setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), i + 2, j + 2, k, structureBoundingBox);
               this.setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), i + 2, j + 1, k, structureBoundingBox);
               this.setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), i + 2, j, k, structureBoundingBox);
               break;
            case IRON_DOOR:
               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), i, j, k, structureBoundingBox);
               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), i, j + 1, k, structureBoundingBox);
               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), i, j + 2, k, structureBoundingBox);
               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), i + 1, j + 2, k, structureBoundingBox);
               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), i + 2, j + 2, k, structureBoundingBox);
               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), i + 2, j + 1, k, structureBoundingBox);
               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), i + 2, j, k, structureBoundingBox);
               this.setBlockState(worldIn, Blocks.iron_door.getDefaultState(), i + 1, j, k, structureBoundingBox);
               this.setBlockState(worldIn, Blocks.iron_door.getStateFromMeta(8), i + 1, j + 1, k, structureBoundingBox);
               this.setBlockState(
                  worldIn, Blocks.stone_button.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_button, 4)), i + 2, j + 1, k + 1, structureBoundingBox
               );
               this.setBlockState(
                  worldIn, Blocks.stone_button.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_button, 3)), i + 2, j + 1, k - 1, structureBoundingBox
               );
         }
      }

      public StructureStrongholdPieces.Stronghold.Door getRandomDoor(Random random2) {
         int i = random2.nextInt(5);
         switch (i) {
            case 0:
            case 1:
            default:
               return StructureStrongholdPieces.Stronghold.Door.OPENING;
            case 2:
               return StructureStrongholdPieces.Stronghold.Door.WOOD_DOOR;
            case 3:
               return StructureStrongholdPieces.Stronghold.Door.GRATES;
            case 4:
               return StructureStrongholdPieces.Stronghold.Door.IRON_DOOR;
         }
      }

      public StructureComponent getNextComponentNormal(StructureStrongholdPieces.Stairs2 stairs2, List<StructureComponent> list, Random random2, int i, int j) {
         if (super.coordBaseMode != null) {
            switch (super.coordBaseMode) {
               case SOUTH:
                  return StructureStrongholdPieces.access$000(
                     stairs2,
                     list,
                     random2,
                     super.boundingBox.minX + i,
                     super.boundingBox.minY + j,
                     super.boundingBox.maxZ + 1,
                     super.coordBaseMode,
                     this.getComponentType()
                  );
               case WEST:
                  return StructureStrongholdPieces.access$000(
                     stairs2,
                     list,
                     random2,
                     super.boundingBox.minX - 1,
                     super.boundingBox.minY + j,
                     super.boundingBox.minZ + i,
                     super.coordBaseMode,
                     this.getComponentType()
                  );
               case EAST:
                  return StructureStrongholdPieces.access$000(
                     stairs2,
                     list,
                     random2,
                     super.boundingBox.maxX + 1,
                     super.boundingBox.minY + j,
                     super.boundingBox.minZ + i,
                     super.coordBaseMode,
                     this.getComponentType()
                  );
               case NORTH:
                  return StructureStrongholdPieces.access$000(
                     stairs2,
                     list,
                     random2,
                     super.boundingBox.minX + i,
                     super.boundingBox.minY + j,
                     super.boundingBox.minZ - 1,
                     super.coordBaseMode,
                     this.getComponentType()
                  );
            }
         }

         return null;
      }

      public StructureComponent getNextComponentX(StructureStrongholdPieces.Stairs2 stairs2, List<StructureComponent> list, Random random2, int i, int j) {
         if (super.coordBaseMode != null) {
            switch (super.coordBaseMode) {
               case SOUTH:
                  return StructureStrongholdPieces.access$000(
                     stairs2,
                     list,
                     random2,
                     super.boundingBox.minX - 1,
                     super.boundingBox.minY + i,
                     super.boundingBox.minZ + j,
                     EnumFacing.WEST,
                     this.getComponentType()
                  );
               case WEST:
                  return StructureStrongholdPieces.access$000(
                     stairs2,
                     list,
                     random2,
                     super.boundingBox.minX + j,
                     super.boundingBox.minY + i,
                     super.boundingBox.minZ - 1,
                     EnumFacing.NORTH,
                     this.getComponentType()
                  );
               case EAST:
                  return StructureStrongholdPieces.access$000(
                     stairs2,
                     list,
                     random2,
                     super.boundingBox.minX + j,
                     super.boundingBox.minY + i,
                     super.boundingBox.minZ - 1,
                     EnumFacing.NORTH,
                     this.getComponentType()
                  );
               case NORTH:
                  return StructureStrongholdPieces.access$000(
                     stairs2,
                     list,
                     random2,
                     super.boundingBox.minX - 1,
                     super.boundingBox.minY + i,
                     super.boundingBox.minZ + j,
                     EnumFacing.WEST,
                     this.getComponentType()
                  );
            }
         }

         return null;
      }

      public StructureComponent getNextComponentZ(StructureStrongholdPieces.Stairs2 stairs2, List<StructureComponent> list, Random random2, int i, int j) {
         if (super.coordBaseMode != null) {
            switch (super.coordBaseMode) {
               case SOUTH:
                  return StructureStrongholdPieces.access$000(
                     stairs2,
                     list,
                     random2,
                     super.boundingBox.maxX + 1,
                     super.boundingBox.minY + i,
                     super.boundingBox.minZ + j,
                     EnumFacing.EAST,
                     this.getComponentType()
                  );
               case WEST:
                  return StructureStrongholdPieces.access$000(
                     stairs2,
                     list,
                     random2,
                     super.boundingBox.minX + j,
                     super.boundingBox.minY + i,
                     super.boundingBox.maxZ + 1,
                     EnumFacing.SOUTH,
                     this.getComponentType()
                  );
               case EAST:
                  return StructureStrongholdPieces.access$000(
                     stairs2,
                     list,
                     random2,
                     super.boundingBox.minX + j,
                     super.boundingBox.minY + i,
                     super.boundingBox.maxZ + 1,
                     EnumFacing.SOUTH,
                     this.getComponentType()
                  );
               case NORTH:
                  return StructureStrongholdPieces.access$000(
                     stairs2,
                     list,
                     random2,
                     super.boundingBox.maxX + 1,
                     super.boundingBox.minY + i,
                     super.boundingBox.minZ + j,
                     EnumFacing.EAST,
                     this.getComponentType()
                  );
            }
         }

         return null;
      }

      public static boolean canStrongholdGoDeeper(StructureBoundingBox structureBoundingBox) {
         if (structureBoundingBox != null) {
            int i = structureBoundingBox.minY;
            if (i > 10) {
               return true;
            }
         }

         return false;
      }

      public static enum Door {
         OPENING,
         WOOD_DOOR,
         GRATES,
         IRON_DOOR;
      }
   }
}
