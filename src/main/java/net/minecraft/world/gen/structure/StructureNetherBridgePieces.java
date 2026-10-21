package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class StructureNetherBridgePieces {
   private static final StructureNetherBridgePieces.PieceWeight[] primaryComponents = new StructureNetherBridgePieces.PieceWeight[]{
      new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Straight.class, 30, 0, true),
      new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Crossing3.class, 10, 4),
      new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Crossing.class, 10, 4),
      new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Stairs.class, 10, 3),
      new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Throne.class, 5, 2),
      new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Entrance.class, 5, 1)
   };
   private static final StructureNetherBridgePieces.PieceWeight[] secondaryComponents = new StructureNetherBridgePieces.PieceWeight[]{
      new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Corridor5.class, 25, 0, true),
      new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Crossing2.class, 15, 5),
      new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Corridor2.class, 5, 10),
      new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Corridor.class, 5, 10),
      new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Corridor3.class, 10, 3, true),
      new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Corridor4.class, 7, 2),
      new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.NetherStalkRoom.class, 5, 2)
   };

   public static void registerNetherFortressPieces() {
      MapGenStructureIO.registerStructureComponent(StructureNetherBridgePieces.Crossing3.class, "NeBCr");
      MapGenStructureIO.registerStructureComponent(StructureNetherBridgePieces.End.class, "NeBEF");
      MapGenStructureIO.registerStructureComponent(StructureNetherBridgePieces.Straight.class, "NeBS");
      MapGenStructureIO.registerStructureComponent(StructureNetherBridgePieces.Corridor3.class, "NeCCS");
      MapGenStructureIO.registerStructureComponent(StructureNetherBridgePieces.Corridor4.class, "NeCTB");
      MapGenStructureIO.registerStructureComponent(StructureNetherBridgePieces.Entrance.class, "NeCE");
      MapGenStructureIO.registerStructureComponent(StructureNetherBridgePieces.Crossing2.class, "NeSCSC");
      MapGenStructureIO.registerStructureComponent(StructureNetherBridgePieces.Corridor.class, "NeSCLT");
      MapGenStructureIO.registerStructureComponent(StructureNetherBridgePieces.Corridor5.class, "NeSC");
      MapGenStructureIO.registerStructureComponent(StructureNetherBridgePieces.Corridor2.class, "NeSCRT");
      MapGenStructureIO.registerStructureComponent(StructureNetherBridgePieces.NetherStalkRoom.class, "NeCSR");
      MapGenStructureIO.registerStructureComponent(StructureNetherBridgePieces.Throne.class, "NeMT");
      MapGenStructureIO.registerStructureComponent(StructureNetherBridgePieces.Crossing.class, "NeRC");
      MapGenStructureIO.registerStructureComponent(StructureNetherBridgePieces.Stairs.class, "NeSR");
      MapGenStructureIO.registerStructureComponent(StructureNetherBridgePieces.Start.class, "NeStart");
   }

   private static StructureNetherBridgePieces.Piece func_175887_b(
      StructureNetherBridgePieces.PieceWeight pieceWeight, List<StructureComponent> list, Random random2, int i, int j, int k, EnumFacing enumFacing, int l
   ) {
      Class oclass = pieceWeight.weightClass;
      Object object = null;
      Class<StructureNetherBridgePieces.Straight> oclass1 = StructureNetherBridgePieces.Straight.class;
      if (oclass == oclass1) {
         object = StructureNetherBridgePieces.Straight.o(list, random2, i, j, k, enumFacing, l);
      } else {
         Class<StructureNetherBridgePieces.Crossing3> oclass2 = StructureNetherBridgePieces.Crossing3.class;
         if (oclass == oclass2) {
            object = StructureNetherBridgePieces.Crossing3.func_175885_a(list, random2, i, j, k, enumFacing, l);
         } else {
            Class<StructureNetherBridgePieces.Crossing> oclass3 = StructureNetherBridgePieces.Crossing.class;
            if (oclass == oclass3) {
               object = StructureNetherBridgePieces.Crossing.u(list, random2, i, j, k, enumFacing, l);
            } else {
               Class<StructureNetherBridgePieces.Stairs> oclass4 = StructureNetherBridgePieces.Stairs.class;
               if (oclass == oclass4) {
                  object = StructureNetherBridgePieces.Stairs.func_175872_a(list, random2, i, j, k, l, enumFacing);
               } else {
                  Class<StructureNetherBridgePieces.Throne> oclass5 = StructureNetherBridgePieces.Throne.class;
                  if (oclass == oclass5) {
                     object = StructureNetherBridgePieces.Throne.d(list, random2, i, j, k, l, enumFacing);
                  } else {
                     Class<StructureNetherBridgePieces.Entrance> oclass6 = StructureNetherBridgePieces.Entrance.class;
                     if (oclass == oclass6) {
                        object = StructureNetherBridgePieces.Entrance.a(list, random2, i, j, k, enumFacing, l);
                     } else {
                        Class<StructureNetherBridgePieces.Corridor5> oclass7 = StructureNetherBridgePieces.Corridor5.class;
                        if (oclass == oclass7) {
                           object = StructureNetherBridgePieces.Corridor5.v(list, random2, i, j, k, enumFacing, l);
                        } else {
                           Class<StructureNetherBridgePieces.Corridor2> oclass8 = StructureNetherBridgePieces.Corridor2.class;
                           if (oclass == oclass8) {
                              object = StructureNetherBridgePieces.Corridor2.p(list, random2, i, j, k, enumFacing, l);
                           } else {
                              Class<StructureNetherBridgePieces.Corridor> oclass9 = StructureNetherBridgePieces.Corridor.class;
                              if (oclass == oclass9) {
                                 object = StructureNetherBridgePieces.Corridor.K(list, random2, i, j, k, enumFacing, l);
                              } else {
                                 Class<StructureNetherBridgePieces.Corridor3> oclass10 = StructureNetherBridgePieces.Corridor3.class;
                                 if (oclass == oclass10) {
                                    object = StructureNetherBridgePieces.Corridor3.z(list, random2, i, j, k, enumFacing, l);
                                 } else {
                                    Class<StructureNetherBridgePieces.Corridor4> oclass11 = StructureNetherBridgePieces.Corridor4.class;
                                    if (oclass == oclass11) {
                                       object = StructureNetherBridgePieces.Corridor4.g(list, random2, i, j, k, enumFacing, l);
                                    } else {
                                       Class<StructureNetherBridgePieces.Crossing2> oclass12 = StructureNetherBridgePieces.Crossing2.class;
                                       if (oclass == oclass12) {
                                          object = StructureNetherBridgePieces.Crossing2.s(list, random2, i, j, k, enumFacing, l);
                                       } else if (oclass == StructureNetherBridgePieces.NetherStalkRoom.class) {
                                          object = StructureNetherBridgePieces.NetherStalkRoom.N(list, random2, i, j, k, enumFacing, l);
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
         }
      }

      return (StructureNetherBridgePieces.Piece)object;
   }

   public static class Corridor extends StructureNetherBridgePieces.Piece {
      private boolean G;

      public Corridor() {
      }

      public Corridor(int i, Random random2, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(i);
         super.coordBaseMode = enumFacing;
         super.boundingBox = structureBoundingBox;
         this.G = random2.nextInt(3) == 0;
      }

      @Override
      public void readStructureFromNBT(NBTTagCompound tagCompound) {
         super.readStructureFromNBT(tagCompound);
         this.G = tagCompound.getBoolean("Chest");
      }

      @Override
      public void writeStructureToNBT(NBTTagCompound tagCompound) {
         super.writeStructureToNBT(tagCompound);
         tagCompound.setBoolean("Chest", this.G);
      }

      @Override
      public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
         this.getNextComponentX((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 0, 1, true);
      }

      public static StructureNetherBridgePieces.Corridor K(List<StructureComponent> list, Random random2, int i, int j, int k, EnumFacing enumFacing, int l) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, -1, 0, 0, 5, 7, 5, enumFacing);
         return isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(list, structureboundingbox) == null
            ? new StructureNetherBridgePieces.Corridor(l, random2, structureboundingbox, enumFacing)
            : null;
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 1, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 4, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 4, 2, 0, 4, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 4, 3, 1, 4, 4, 1, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 4, 3, 3, 4, 4, 3, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 5, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 2, 4, 3, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 1, 3, 4, 1, 4, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 3, 3, 4, 3, 4, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         boolean flag = this.G;
         if (flag && structureBoundingBoxIn.isVecInside(new BlockPos(this.getXWithOffset(3, 3), this.getYWithOffset(2), this.getZWithOffset(3, 3)))) {
            this.G = false;
            this.generateChestContents(
               worldIn, structureBoundingBoxIn, randomIn, 3, 2, 3, StructureNetherBridgePieces.Piece.field_111019_a, 2 + randomIn.nextInt(4)
            );
         }

         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 6, 0, 4, 6, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );

         for (int i = 0; i <= 4; i++) {
            for (int j = 0; j <= 4; j++) {
               this.replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), i, -1, j, structureBoundingBoxIn);
            }
         }

         return true;
      }
   }

   public static class Corridor2 extends StructureNetherBridgePieces.Piece {
      private boolean g;

      public Corridor2() {
      }

      public Corridor2(int i, Random random2, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(i);
         super.coordBaseMode = enumFacing;
         super.boundingBox = structureBoundingBox;
         this.g = random2.nextInt(3) == 0;
      }

      @Override
      public void readStructureFromNBT(NBTTagCompound tagCompound) {
         super.readStructureFromNBT(tagCompound);
         this.g = tagCompound.getBoolean("Chest");
      }

      @Override
      public void writeStructureToNBT(NBTTagCompound tagCompound) {
         super.writeStructureToNBT(tagCompound);
         tagCompound.setBoolean("Chest", this.g);
      }

      @Override
      public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
         this.getNextComponentZ((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 0, 1, true);
      }

      public static StructureNetherBridgePieces.Corridor2 p(List<StructureComponent> list, Random random2, int i, int j, int k, EnumFacing enumFacing, int l) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, -1, 0, 0, 5, 7, 5, enumFacing);
         return isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(list, structureboundingbox) == null
            ? new StructureNetherBridgePieces.Corridor2(l, random2, structureboundingbox, enumFacing)
            : null;
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 1, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 4, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 3, 1, 0, 4, 1, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 3, 3, 0, 4, 3, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 4, 2, 0, 4, 5, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 1, 2, 4, 4, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 1, 3, 4, 1, 4, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 3, 3, 4, 3, 4, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         boolean flag = this.g;
         if (flag && structureBoundingBoxIn.isVecInside(new BlockPos(this.getXWithOffset(1, 3), this.getYWithOffset(2), this.getZWithOffset(1, 3)))) {
            this.g = false;
            this.generateChestContents(
               worldIn, structureBoundingBoxIn, randomIn, 1, 2, 3, StructureNetherBridgePieces.Piece.field_111019_a, 2 + randomIn.nextInt(4)
            );
         }

         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 6, 0, 4, 6, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );

         for (int i = 0; i <= 4; i++) {
            for (int j = 0; j <= 4; j++) {
               this.replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), i, -1, j, structureBoundingBoxIn);
            }
         }

         return true;
      }
   }

   public static class Corridor3 extends StructureNetherBridgePieces.Piece {
      public Corridor3() {
      }

      public Corridor3(int i, Random random2, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(i);
         super.coordBaseMode = enumFacing;
         super.boundingBox = structureBoundingBox;
      }

      @Override
      public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
         this.getNextComponentNormal((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 1, 0, true);
      }

      public static StructureNetherBridgePieces.Corridor3 z(List<StructureComponent> list, Random random2, int i, int j, int k, EnumFacing enumFacing, int l) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, -1, -7, 0, 5, 14, 10, enumFacing);
         return isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(list, structureboundingbox) == null
            ? new StructureNetherBridgePieces.Corridor3(l, random2, structureboundingbox, enumFacing)
            : null;
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         int i = this.getMetadataWithOffset(Blocks.nether_brick_stairs, 2);

         for (int j = 0; j <= 9; j++) {
            int k = Math.max(1, 7 - j);
            int l = Math.min(Math.max(k + 5, 14 - j), 13);
            int i1 = j;
            this.fillWithBlocks(
               worldIn, structureBoundingBoxIn, 0, 0, j, 4, k, j, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
            );
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, k + 1, j, 3, l - 1, j, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            if (j <= 6) {
               this.setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(i), 1, k + 1, j, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(i), 2, k + 1, j, structureBoundingBoxIn);
               this.setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(i), 3, k + 1, j, structureBoundingBoxIn);
            }

            this.fillWithBlocks(
               worldIn, structureBoundingBoxIn, 0, l, j, 4, l, j, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
            );
            this.fillWithBlocks(
               worldIn, structureBoundingBoxIn, 0, k + 1, j, 0, l - 1, j, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
            );
            this.fillWithBlocks(
               worldIn, structureBoundingBoxIn, 4, k + 1, j, 4, l - 1, j, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
            );
            if ((j & 1) == 0) {
               this.fillWithBlocks(
                  worldIn,
                  structureBoundingBoxIn,
                  0,
                  k + 2,
                  j,
                  0,
                  k + 3,
                  j,
                  Blocks.nether_brick_fence.getDefaultState(),
                  Blocks.nether_brick_fence.getDefaultState(),
                  false
               );
               this.fillWithBlocks(
                  worldIn,
                  structureBoundingBoxIn,
                  4,
                  k + 2,
                  j,
                  4,
                  k + 3,
                  j,
                  Blocks.nether_brick_fence.getDefaultState(),
                  Blocks.nether_brick_fence.getDefaultState(),
                  false
               );
            }

            for (int j1 = 0; j1 <= 4; j1++) {
               this.replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), j1, -1, i1, structureBoundingBoxIn);
            }
         }

         return true;
      }
   }

   public static class Corridor4 extends StructureNetherBridgePieces.Piece {
      public Corridor4() {
      }

      public Corridor4(int i, Random random2, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(i);
         super.coordBaseMode = enumFacing;
         super.boundingBox = structureBoundingBox;
      }

      @Override
      public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
         int b0 = 1;
         EnumFacing enumfacing1 = EnumFacing.WEST;
         EnumFacing enumfacing = super.coordBaseMode;
         if (enumfacing == enumfacing1 || super.coordBaseMode == EnumFacing.NORTH) {
            b0 = 5;
         }

         StructureNetherBridgePieces.Start structurenetherbridgepieces$start2 = (StructureNetherBridgePieces.Start)componentIn;
         int i = rand.nextInt(8);
         StructureNetherBridgePieces.Start structurenetherbridgepieces$start = structurenetherbridgepieces$start2;
         this.getNextComponentX(structurenetherbridgepieces$start, listIn, rand, 0, b0, i > 0);
         structurenetherbridgepieces$start2 = (StructureNetherBridgePieces.Start)componentIn;
         int j = rand.nextInt(8);
         StructureNetherBridgePieces.Start structurenetherbridgepieces$start1 = structurenetherbridgepieces$start2;
         this.getNextComponentZ(structurenetherbridgepieces$start1, listIn, rand, 0, b0, j > 0);
      }

      public static StructureNetherBridgePieces.Corridor4 g(List<StructureComponent> list, Random random2, int i, int j, int k, EnumFacing enumFacing, int l) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, -3, 0, 0, 9, 7, 9, enumFacing);
         return isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(list, structureboundingbox) == null
            ? new StructureNetherBridgePieces.Corridor4(l, random2, structureboundingbox, enumFacing)
            : null;
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 0, 0, 8, 1, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 8, 5, 8, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 6, 0, 8, 6, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 2, 0, 2, 5, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 6, 2, 0, 8, 5, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 1, 3, 0, 1, 4, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 7, 3, 0, 7, 4, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 2, 4, 8, 2, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 4, 2, 2, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 4, 7, 2, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 3, 8, 8, 3, 8, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 3, 6, 0, 3, 7, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 8, 3, 6, 8, 3, 7, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 3, 4, 0, 5, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 8, 3, 4, 8, 5, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 1, 3, 5, 2, 5, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 6, 3, 5, 7, 5, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 1, 4, 5, 1, 5, 5, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 7, 4, 5, 7, 5, 5, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false
         );

         for (int i = 0; i <= 5; i++) {
            for (int j = 0; j <= 8; j++) {
               this.replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), j, -1, i, structureBoundingBoxIn);
            }
         }

         return true;
      }
   }

   public static class Corridor5 extends StructureNetherBridgePieces.Piece {
      public Corridor5() {
      }

      public Corridor5(int i, Random random2, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(i);
         super.coordBaseMode = enumFacing;
         super.boundingBox = structureBoundingBox;
      }

      @Override
      public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
         this.getNextComponentNormal((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 1, 0, true);
      }

      public static StructureNetherBridgePieces.Corridor5 v(List<StructureComponent> list, Random random2, int i, int j, int k, EnumFacing enumFacing, int l) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, -1, 0, 0, 5, 7, 5, enumFacing);
         return isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(list, structureboundingbox) == null
            ? new StructureNetherBridgePieces.Corridor5(l, random2, structureboundingbox, enumFacing)
            : null;
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 1, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 4, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 4, 2, 0, 4, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 3, 1, 0, 4, 1, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 3, 3, 0, 4, 3, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 4, 3, 1, 4, 4, 1, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 4, 3, 3, 4, 4, 3, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 6, 0, 4, 6, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );

         for (int i = 0; i <= 4; i++) {
            for (int j = 0; j <= 4; j++) {
               this.replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), i, -1, j, structureBoundingBoxIn);
            }
         }

         return true;
      }
   }

   public static class Crossing extends StructureNetherBridgePieces.Piece {
      public Crossing() {
      }

      public Crossing(int i, Random random2, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(i);
         super.coordBaseMode = enumFacing;
         super.boundingBox = structureBoundingBox;
      }

      @Override
      public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
         this.getNextComponentNormal((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 2, 0, false);
         this.getNextComponentX((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 0, 2, false);
         this.getNextComponentZ((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 0, 2, false);
      }

      public static StructureNetherBridgePieces.Crossing u(List<StructureComponent> list, Random random2, int i, int j, int k, EnumFacing enumFacing, int l) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, -2, 0, 0, 7, 9, 7, enumFacing);
         return isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(list, structureboundingbox) == null
            ? new StructureNetherBridgePieces.Crossing(l, random2, structureboundingbox, enumFacing)
            : null;
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 0, 0, 6, 1, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 6, 7, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 2, 0, 1, 6, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 2, 6, 1, 6, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 5, 2, 0, 6, 6, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 5, 2, 6, 6, 6, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 6, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 2, 5, 0, 6, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 6, 2, 0, 6, 6, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 6, 2, 5, 6, 6, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 2, 6, 0, 4, 6, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 2, 5, 0, 4, 5, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 2, 6, 6, 4, 6, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 2, 5, 6, 4, 5, 6, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 6, 2, 0, 6, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 5, 2, 0, 5, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 6, 6, 2, 6, 6, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 6, 5, 2, 6, 5, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false
         );

         for (int i = 0; i <= 6; i++) {
            for (int j = 0; j <= 6; j++) {
               this.replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), i, -1, j, structureBoundingBoxIn);
            }
         }

         return true;
      }
   }

   public static class Crossing2 extends StructureNetherBridgePieces.Piece {
      public Crossing2() {
      }

      public Crossing2(int i, Random random2, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(i);
         super.coordBaseMode = enumFacing;
         super.boundingBox = structureBoundingBox;
      }

      @Override
      public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
         this.getNextComponentNormal((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 1, 0, true);
         this.getNextComponentX((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 0, 1, true);
         this.getNextComponentZ((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 0, 1, true);
      }

      public static StructureNetherBridgePieces.Crossing2 s(List<StructureComponent> list, Random random2, int i, int j, int k, EnumFacing enumFacing, int l) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, -1, 0, 0, 5, 7, 5, enumFacing);
         return isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(list, structureboundingbox) == null
            ? new StructureNetherBridgePieces.Crossing2(l, random2, structureboundingbox, enumFacing)
            : null;
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 1, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 4, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 5, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 4, 2, 0, 4, 5, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 2, 4, 0, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 4, 2, 4, 4, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 6, 0, 4, 6, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );

         for (int i = 0; i <= 4; i++) {
            for (int j = 0; j <= 4; j++) {
               this.replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), i, -1, j, structureBoundingBoxIn);
            }
         }

         return true;
      }
   }

   public static class Crossing3 extends StructureNetherBridgePieces.Piece {
      public Crossing3() {
      }

      public Crossing3(int i, Random random2, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(i);
         super.coordBaseMode = enumFacing;
         super.boundingBox = structureBoundingBox;
      }

      public Crossing3(Random random2, int i, int j) {
         super(0);
         super.coordBaseMode = EnumFacing.Plane.HORIZONTAL.random(random2);
         switch (super.coordBaseMode) {
            case NORTH:
            case SOUTH:
               super.boundingBox = new StructureBoundingBox(i, 64, j, i + 19 - 1, 73, j + 19 - 1);
               break;
            default:
               super.boundingBox = new StructureBoundingBox(i, 64, j, i + 19 - 1, 73, j + 19 - 1);
         }
      }

      @Override
      public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
         this.getNextComponentNormal((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 8, 3, false);
         this.getNextComponentX((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 3, 8, false);
         this.getNextComponentZ((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 3, 8, false);
      }

      public static StructureNetherBridgePieces.Crossing3 func_175885_a(
         List<StructureComponent> list, Random random2, int i, int j, int k, EnumFacing enumFacing, int l
      ) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, -8, -3, 0, 19, 10, 19, enumFacing);
         return isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(list, structureboundingbox) == null
            ? new StructureNetherBridgePieces.Crossing3(l, random2, structureboundingbox, enumFacing)
            : null;
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 7, 3, 0, 11, 4, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 3, 7, 18, 4, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 5, 0, 10, 7, 18, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 8, 18, 7, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 7, 5, 0, 7, 5, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 7, 5, 11, 7, 5, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 11, 5, 0, 11, 5, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 11, 5, 11, 11, 5, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 5, 7, 7, 5, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 11, 5, 7, 18, 5, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 5, 11, 7, 5, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 11, 5, 11, 18, 5, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 7, 2, 0, 11, 2, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 7, 2, 13, 11, 2, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 7, 0, 0, 11, 1, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 7, 0, 15, 11, 1, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );

         for (int i = 7; i <= 11; i++) {
            for (int j = 0; j <= 2; j++) {
               this.replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), i, -1, j, structureBoundingBoxIn);
               this.replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), i, -1, 18 - j, structureBoundingBoxIn);
            }
         }

         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 2, 7, 5, 2, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 13, 2, 7, 18, 2, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 0, 7, 3, 1, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 15, 0, 7, 18, 1, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );

         for (int k = 0; k <= 2; k++) {
            for (int l = 7; l <= 11; l++) {
               this.replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), k, -1, l, structureBoundingBoxIn);
               this.replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), 18 - k, -1, l, structureBoundingBoxIn);
            }
         }

         return true;
      }
   }

   public static class End extends StructureNetherBridgePieces.Piece {
      private int fillSeed;

      public End() {
      }

      public End(int i, Random random2, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(i);
         super.coordBaseMode = enumFacing;
         super.boundingBox = structureBoundingBox;
         this.fillSeed = random2.nextInt();
      }

      public static StructureNetherBridgePieces.End D(List<StructureComponent> list, Random random2, int i, int j, int k, EnumFacing enumFacing, int l) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, -1, -3, 0, 5, 10, 8, enumFacing);
         return isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(list, structureboundingbox) == null
            ? new StructureNetherBridgePieces.End(l, random2, structureboundingbox, enumFacing)
            : null;
      }

      @Override
      public void readStructureFromNBT(NBTTagCompound tagCompound) {
         super.readStructureFromNBT(tagCompound);
         this.fillSeed = tagCompound.getInteger("Seed");
      }

      @Override
      public void writeStructureToNBT(NBTTagCompound tagCompound) {
         super.writeStructureToNBT(tagCompound);
         tagCompound.setInteger("Seed", this.fillSeed);
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         Random random = new Random((long)this.fillSeed);

         for (int i = 0; i <= 4; i++) {
            for (int j = 3; j <= 4; j++) {
               int k = random.nextInt(8);
               this.fillWithBlocks(
                  worldIn, structureBoundingBoxIn, i, j, 0, i, j, k, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
               );
            }
         }

         int l = random.nextInt(8);
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 5, 0, 0, 5, l, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         l = random.nextInt(8);
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 4, 5, 0, 4, 5, l, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );

         for (int i1 = 0; i1 <= 4; i1++) {
            int k1 = random.nextInt(5);
            this.fillWithBlocks(
               worldIn, structureBoundingBoxIn, i1, 2, 0, i1, 2, k1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
            );
         }

         for (int j1 = 0; j1 <= 4; j1++) {
            for (int l1 = 0; l1 <= 1; l1++) {
               int i2 = random.nextInt(3);
               this.fillWithBlocks(
                  worldIn, structureBoundingBoxIn, j1, l1, 0, j1, l1, i2, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
               );
            }
         }

         return true;
      }
   }

   public static class Entrance extends StructureNetherBridgePieces.Piece {
      public Entrance() {
      }

      public Entrance(int i, Random random2, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(i);
         super.coordBaseMode = enumFacing;
         super.boundingBox = structureBoundingBox;
      }

      @Override
      public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
         this.getNextComponentNormal((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 5, 3, true);
      }

      public static StructureNetherBridgePieces.Entrance a(List<StructureComponent> list, Random random2, int i, int j, int k, EnumFacing enumFacing, int l) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, -5, -3, 0, 13, 14, 13, enumFacing);
         return isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(list, structureboundingbox) == null
            ? new StructureNetherBridgePieces.Entrance(l, random2, structureboundingbox, enumFacing)
            : null;
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 3, 0, 12, 4, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 12, 13, 12, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 5, 0, 1, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 11, 5, 0, 12, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 2, 5, 11, 4, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 8, 5, 11, 10, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 5, 9, 11, 7, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 2, 5, 0, 4, 12, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 8, 5, 0, 10, 12, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 5, 9, 0, 7, 12, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 2, 11, 2, 10, 12, 10, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 5, 8, 0, 7, 8, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false
         );

         for (int b0 = 1; b0 <= 11; b0 += 2) {
            this.fillWithBlocks(
               worldIn,
               structureBoundingBoxIn,
               b0,
               10,
               0,
               b0,
               11,
               0,
               Blocks.nether_brick_fence.getDefaultState(),
               Blocks.nether_brick_fence.getDefaultState(),
               false
            );
            this.fillWithBlocks(
               worldIn,
               structureBoundingBoxIn,
               b0,
               10,
               12,
               b0,
               11,
               12,
               Blocks.nether_brick_fence.getDefaultState(),
               Blocks.nether_brick_fence.getDefaultState(),
               false
            );
            this.fillWithBlocks(
               worldIn,
               structureBoundingBoxIn,
               0,
               10,
               b0,
               0,
               11,
               b0,
               Blocks.nether_brick_fence.getDefaultState(),
               Blocks.nether_brick_fence.getDefaultState(),
               false
            );
            this.fillWithBlocks(
               worldIn,
               structureBoundingBoxIn,
               12,
               10,
               b0,
               12,
               11,
               b0,
               Blocks.nether_brick_fence.getDefaultState(),
               Blocks.nether_brick_fence.getDefaultState(),
               false
            );
            this.setBlockState(worldIn, Blocks.nether_brick.getDefaultState(), b0, 13, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.nether_brick.getDefaultState(), b0, 13, 12, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.nether_brick.getDefaultState(), 0, 13, b0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.nether_brick.getDefaultState(), 12, 13, b0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), b0 + 1, 13, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), b0 + 1, 13, 12, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, b0 + 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 12, 13, b0 + 1, structureBoundingBoxIn);
         }

         this.setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 12, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 12, 13, 0, structureBoundingBoxIn);

         for (int b1 = 3; b1 <= 9; b1 += 2) {
            this.fillWithBlocks(
               worldIn,
               structureBoundingBoxIn,
               1,
               7,
               b1,
               1,
               8,
               b1,
               Blocks.nether_brick_fence.getDefaultState(),
               Blocks.nether_brick_fence.getDefaultState(),
               false
            );
            this.fillWithBlocks(
               worldIn,
               structureBoundingBoxIn,
               11,
               7,
               b1,
               11,
               8,
               b1,
               Blocks.nether_brick_fence.getDefaultState(),
               Blocks.nether_brick_fence.getDefaultState(),
               false
            );
         }

         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 4, 2, 0, 8, 2, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 2, 4, 12, 2, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 4, 0, 0, 8, 1, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 4, 0, 9, 8, 1, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 0, 4, 3, 1, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 9, 0, 4, 12, 1, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );

         for (int j = 4; j <= 8; j++) {
            for (int i = 0; i <= 2; i++) {
               this.replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), j, -1, i, structureBoundingBoxIn);
               this.replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), j, -1, 12 - i, structureBoundingBoxIn);
            }
         }

         for (int k = 0; k <= 2; k++) {
            for (int l = 4; l <= 8; l++) {
               this.replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), k, -1, l, structureBoundingBoxIn);
               this.replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), 12 - k, -1, l, structureBoundingBoxIn);
            }
         }

         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 5, 5, 5, 7, 5, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 6, 6, 4, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.setBlockState(worldIn, Blocks.nether_brick.getDefaultState(), 6, 0, 6, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.flowing_lava.getDefaultState(), 6, 5, 6, structureBoundingBoxIn);
         BlockPos blockpos = new BlockPos(this.getXWithOffset(6, 6), this.getYWithOffset(5), this.getZWithOffset(6, 6));
         boolean flag = structureBoundingBoxIn.isVecInside(blockpos);
         if (flag) {
            worldIn.forceBlockUpdateTick(Blocks.flowing_lava, blockpos, randomIn);
         }

         return true;
      }
   }

   public static class NetherStalkRoom extends StructureNetherBridgePieces.Piece {
      public NetherStalkRoom() {
      }

      public NetherStalkRoom(int i, Random random2, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(i);
         super.coordBaseMode = enumFacing;
         super.boundingBox = structureBoundingBox;
      }

      @Override
      public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
         this.getNextComponentNormal((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 5, 3, true);
         this.getNextComponentNormal((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 5, 11, true);
      }

      public static StructureNetherBridgePieces.NetherStalkRoom N(
         List<StructureComponent> list, Random random2, int i, int j, int k, EnumFacing enumFacing, int l
      ) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, -5, -3, 0, 13, 14, 13, enumFacing);
         return isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(list, structureboundingbox) == null
            ? new StructureNetherBridgePieces.NetherStalkRoom(l, random2, structureboundingbox, enumFacing)
            : null;
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 3, 0, 12, 4, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 12, 13, 12, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 5, 0, 1, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 11, 5, 0, 12, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 2, 5, 11, 4, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 8, 5, 11, 10, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 5, 9, 11, 7, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 2, 5, 0, 4, 12, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 8, 5, 0, 10, 12, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 5, 9, 0, 7, 12, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 2, 11, 2, 10, 12, 10, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );

         for (int b0 = 1; b0 <= 11; b0 += 2) {
            this.fillWithBlocks(
               worldIn,
               structureBoundingBoxIn,
               b0,
               10,
               0,
               b0,
               11,
               0,
               Blocks.nether_brick_fence.getDefaultState(),
               Blocks.nether_brick_fence.getDefaultState(),
               false
            );
            this.fillWithBlocks(
               worldIn,
               structureBoundingBoxIn,
               b0,
               10,
               12,
               b0,
               11,
               12,
               Blocks.nether_brick_fence.getDefaultState(),
               Blocks.nether_brick_fence.getDefaultState(),
               false
            );
            this.fillWithBlocks(
               worldIn,
               structureBoundingBoxIn,
               0,
               10,
               b0,
               0,
               11,
               b0,
               Blocks.nether_brick_fence.getDefaultState(),
               Blocks.nether_brick_fence.getDefaultState(),
               false
            );
            this.fillWithBlocks(
               worldIn,
               structureBoundingBoxIn,
               12,
               10,
               b0,
               12,
               11,
               b0,
               Blocks.nether_brick_fence.getDefaultState(),
               Blocks.nether_brick_fence.getDefaultState(),
               false
            );
            this.setBlockState(worldIn, Blocks.nether_brick.getDefaultState(), b0, 13, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.nether_brick.getDefaultState(), b0, 13, 12, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.nether_brick.getDefaultState(), 0, 13, b0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.nether_brick.getDefaultState(), 12, 13, b0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), b0 + 1, 13, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), b0 + 1, 13, 12, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, b0 + 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 12, 13, b0 + 1, structureBoundingBoxIn);
         }

         this.setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 12, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 0, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 12, 13, 0, structureBoundingBoxIn);

         for (int b1 = 3; b1 <= 9; b1 += 2) {
            this.fillWithBlocks(
               worldIn,
               structureBoundingBoxIn,
               1,
               7,
               b1,
               1,
               8,
               b1,
               Blocks.nether_brick_fence.getDefaultState(),
               Blocks.nether_brick_fence.getDefaultState(),
               false
            );
            this.fillWithBlocks(
               worldIn,
               structureBoundingBoxIn,
               11,
               7,
               b1,
               11,
               8,
               b1,
               Blocks.nether_brick_fence.getDefaultState(),
               Blocks.nether_brick_fence.getDefaultState(),
               false
            );
         }

         int i1 = this.getMetadataWithOffset(Blocks.nether_brick_stairs, 3);

         for (int i = 0; i <= 6; i++) {
            int j = i + 4;

            for (int k = 5; k <= 7; k++) {
               this.setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(i1), k, 5 + i, j, structureBoundingBoxIn);
            }

            if (j >= 5 && j <= 8) {
               this.fillWithBlocks(
                  worldIn, structureBoundingBoxIn, 5, 5, j, 7, i + 4, j, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
               );
            } else if (j >= 9 && j <= 10) {
               this.fillWithBlocks(
                  worldIn, structureBoundingBoxIn, 5, 8, j, 7, i + 4, j, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
               );
            }

            if (i >= 1) {
               this.fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 6 + i, j, 7, 9 + i, j, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            }
         }

         for (int j1 = 5; j1 <= 7; j1++) {
            this.setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(i1), j1, 12, 11, structureBoundingBoxIn);
         }

         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 5, 6, 7, 5, 7, 7, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 7, 6, 7, 7, 7, 7, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false
         );
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 13, 12, 7, 13, 12, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 2, 5, 2, 3, 5, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 2, 5, 9, 3, 5, 10, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 2, 5, 4, 2, 5, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 9, 5, 2, 10, 5, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 9, 5, 9, 10, 5, 10, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 10, 5, 4, 10, 5, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         int k1 = this.getMetadataWithOffset(Blocks.nether_brick_stairs, 0);
         int l1 = this.getMetadataWithOffset(Blocks.nether_brick_stairs, 1);
         this.setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(l1), 4, 5, 2, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(l1), 4, 5, 3, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(l1), 4, 5, 9, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(l1), 4, 5, 10, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(k1), 8, 5, 2, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(k1), 8, 5, 3, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(k1), 8, 5, 9, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(k1), 8, 5, 10, structureBoundingBoxIn);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 4, 4, 4, 4, 8, Blocks.soul_sand.getDefaultState(), Blocks.soul_sand.getDefaultState(), false);
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 4, 4, 9, 4, 8, Blocks.soul_sand.getDefaultState(), Blocks.soul_sand.getDefaultState(), false);
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 3, 5, 4, 4, 5, 8, Blocks.nether_wart.getDefaultState(), Blocks.nether_wart.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 8, 5, 4, 9, 5, 8, Blocks.nether_wart.getDefaultState(), Blocks.nether_wart.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 4, 2, 0, 8, 2, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 2, 4, 12, 2, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 4, 0, 0, 8, 1, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 4, 0, 9, 8, 1, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 0, 4, 3, 1, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 9, 0, 4, 12, 1, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );

         for (int i2 = 4; i2 <= 8; i2++) {
            for (int l = 0; l <= 2; l++) {
               this.replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), i2, -1, l, structureBoundingBoxIn);
               this.replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), i2, -1, 12 - l, structureBoundingBoxIn);
            }
         }

         for (int j2 = 0; j2 <= 2; j2++) {
            for (int k2 = 4; k2 <= 8; k2++) {
               this.replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), j2, -1, k2, structureBoundingBoxIn);
               this.replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), 12 - j2, -1, k2, structureBoundingBoxIn);
            }
         }

         return true;
      }
   }

   public abstract static class Piece extends StructureComponent {
      public static final List<WeightedRandomChestContent> field_111019_a = Lists.newArrayList(
         new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 5),
         new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 5),
         new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 15),
         new WeightedRandomChestContent(Items.golden_sword, 0, 1, 1, 5),
         new WeightedRandomChestContent(Items.golden_chestplate, 0, 1, 1, 5),
         new WeightedRandomChestContent(Items.flint_and_steel, 0, 1, 1, 5),
         new WeightedRandomChestContent(Items.nether_wart, 0, 3, 7, 5),
         new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 10),
         new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 8),
         new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 5),
         new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 3),
         new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.obsidian), 0, 2, 4, 2)
      );

      public Piece() {
      }

      public Piece(int i) {
         super(i);
      }

      @Override
      public void readStructureFromNBT(NBTTagCompound tagCompound) {
      }

      @Override
      public void writeStructureToNBT(NBTTagCompound tagCompound) {
      }

      private int getTotalWeight(List<StructureNetherBridgePieces.PieceWeight> list) {
         boolean flag = false;
         int i = 0;

         for (StructureNetherBridgePieces.PieceWeight structurenetherbridgepieces$pieceweight : list) {
            int j = structurenetherbridgepieces$pieceweight.field_78824_d;
            if (j > 0 && structurenetherbridgepieces$pieceweight.field_78827_c < structurenetherbridgepieces$pieceweight.field_78824_d) {
               flag = true;
            }

            i += structurenetherbridgepieces$pieceweight.field_78826_b;
         }

         return flag ? i : -1;
      }

      private StructureNetherBridgePieces.Piece func_175871_a(
         StructureNetherBridgePieces.Start start2,
         List<StructureNetherBridgePieces.PieceWeight> list,
         List<StructureComponent> list2,
         Random random2,
         int i,
         int j,
         int k,
         EnumFacing enumFacing,
         int l
      ) {
         int i1 = this.getTotalWeight(list);
         boolean flag = i1 > 0 && l <= 30;
         int j1 = 0;

         while (j1 < 5 && flag) {
            j1++;
            int k1 = random2.nextInt(i1);

            for (StructureNetherBridgePieces.PieceWeight structurenetherbridgepieces$pieceweight : list) {
               k1 -= structurenetherbridgepieces$pieceweight.field_78826_b;
               if (k1 < 0) {
                  if (!structurenetherbridgepieces$pieceweight.func_78822_a(l)
                     || structurenetherbridgepieces$pieceweight == start2.theNetherBridgePieceWeight && !structurenetherbridgepieces$pieceweight.field_78825_e) {
                     break;
                  }

                  StructureNetherBridgePieces.Piece structurenetherbridgepieces$piece = StructureNetherBridgePieces.func_175887_b(
                     structurenetherbridgepieces$pieceweight, list2, random2, i, j, k, enumFacing, l
                  );
                  if (structurenetherbridgepieces$piece != null) {
                     structurenetherbridgepieces$pieceweight.field_78827_c++;
                     start2.theNetherBridgePieceWeight = structurenetherbridgepieces$pieceweight;
                     if (!structurenetherbridgepieces$pieceweight.func_78823_a()) {
                        list.remove(structurenetherbridgepieces$pieceweight);
                     }

                     return structurenetherbridgepieces$piece;
                  }
               }
            }
         }

         return StructureNetherBridgePieces.End.D(list2, random2, i, j, k, enumFacing, l);
      }

      private StructureComponent func_175870_a(
         StructureNetherBridgePieces.Start start2, List<StructureComponent> list, Random random2, int i, int j, int k, EnumFacing enumFacing, int l, boolean bl
      ) {
         int i1 = Math.abs(i - start2.getBoundingBox().minX);
         if (i1 <= 112 && Math.abs(k - start2.getBoundingBox().minZ) <= 112) {
            List list1 = start2.primaryWeights;
            if (bl) {
               list1 = start2.secondaryWeights;
            }

            StructureNetherBridgePieces.Piece structurenetherbridgepieces$piece = this.func_175871_a(start2, list1, list, random2, i, j, k, enumFacing, l + 1);
            if (structurenetherbridgepieces$piece != null) {
               list.add(structurenetherbridgepieces$piece);
               start2.field_74967_d.add(structurenetherbridgepieces$piece);
            }

            return structurenetherbridgepieces$piece;
         } else {
            return StructureNetherBridgePieces.End.D(list, random2, i, j, k, enumFacing, l);
         }
      }

      public StructureComponent getNextComponentNormal(
         StructureNetherBridgePieces.Start start2, List<StructureComponent> list, Random random2, int i, int j, boolean bl
      ) {
         if (super.coordBaseMode != null) {
            switch (super.coordBaseMode) {
               case NORTH:
                  return this.func_175870_a(
                     start2,
                     list,
                     random2,
                     super.boundingBox.minX + i,
                     super.boundingBox.minY + j,
                     super.boundingBox.minZ - 1,
                     super.coordBaseMode,
                     this.getComponentType(),
                     bl
                  );
               case SOUTH:
                  return this.func_175870_a(
                     start2,
                     list,
                     random2,
                     super.boundingBox.minX + i,
                     super.boundingBox.minY + j,
                     super.boundingBox.maxZ + 1,
                     super.coordBaseMode,
                     this.getComponentType(),
                     bl
                  );
               case WEST:
                  return this.func_175870_a(
                     start2,
                     list,
                     random2,
                     super.boundingBox.minX - 1,
                     super.boundingBox.minY + j,
                     super.boundingBox.minZ + i,
                     super.coordBaseMode,
                     this.getComponentType(),
                     bl
                  );
               case EAST:
                  return this.func_175870_a(
                     start2,
                     list,
                     random2,
                     super.boundingBox.maxX + 1,
                     super.boundingBox.minY + j,
                     super.boundingBox.minZ + i,
                     super.coordBaseMode,
                     this.getComponentType(),
                     bl
                  );
            }
         }

         return null;
      }

      public StructureComponent getNextComponentX(
         StructureNetherBridgePieces.Start start2, List<StructureComponent> list, Random random2, int i, int j, boolean bl
      ) {
         if (super.coordBaseMode != null) {
            switch (super.coordBaseMode) {
               case NORTH:
                  return this.func_175870_a(
                     start2,
                     list,
                     random2,
                     super.boundingBox.minX - 1,
                     super.boundingBox.minY + i,
                     super.boundingBox.minZ + j,
                     EnumFacing.WEST,
                     this.getComponentType(),
                     bl
                  );
               case SOUTH:
                  return this.func_175870_a(
                     start2,
                     list,
                     random2,
                     super.boundingBox.minX - 1,
                     super.boundingBox.minY + i,
                     super.boundingBox.minZ + j,
                     EnumFacing.WEST,
                     this.getComponentType(),
                     bl
                  );
               case WEST:
                  return this.func_175870_a(
                     start2,
                     list,
                     random2,
                     super.boundingBox.minX + j,
                     super.boundingBox.minY + i,
                     super.boundingBox.minZ - 1,
                     EnumFacing.NORTH,
                     this.getComponentType(),
                     bl
                  );
               case EAST:
                  return this.func_175870_a(
                     start2,
                     list,
                     random2,
                     super.boundingBox.minX + j,
                     super.boundingBox.minY + i,
                     super.boundingBox.minZ - 1,
                     EnumFacing.NORTH,
                     this.getComponentType(),
                     bl
                  );
            }
         }

         return null;
      }

      public StructureComponent getNextComponentZ(
         StructureNetherBridgePieces.Start start2, List<StructureComponent> list, Random random2, int i, int j, boolean bl
      ) {
         if (super.coordBaseMode != null) {
            switch (super.coordBaseMode) {
               case NORTH:
                  return this.func_175870_a(
                     start2,
                     list,
                     random2,
                     super.boundingBox.maxX + 1,
                     super.boundingBox.minY + i,
                     super.boundingBox.minZ + j,
                     EnumFacing.EAST,
                     this.getComponentType(),
                     bl
                  );
               case SOUTH:
                  return this.func_175870_a(
                     start2,
                     list,
                     random2,
                     super.boundingBox.maxX + 1,
                     super.boundingBox.minY + i,
                     super.boundingBox.minZ + j,
                     EnumFacing.EAST,
                     this.getComponentType(),
                     bl
                  );
               case WEST:
                  return this.func_175870_a(
                     start2,
                     list,
                     random2,
                     super.boundingBox.minX + j,
                     super.boundingBox.minY + i,
                     super.boundingBox.maxZ + 1,
                     EnumFacing.SOUTH,
                     this.getComponentType(),
                     bl
                  );
               case EAST:
                  return this.func_175870_a(
                     start2,
                     list,
                     random2,
                     super.boundingBox.minX + j,
                     super.boundingBox.minY + i,
                     super.boundingBox.maxZ + 1,
                     EnumFacing.SOUTH,
                     this.getComponentType(),
                     bl
                  );
            }
         }

         return null;
      }

      public static boolean isAboveGround(StructureBoundingBox structureBoundingBox) {
         if (structureBoundingBox != null) {
            int i = structureBoundingBox.minY;
            if (i > 10) {
               return true;
            }
         }

         return false;
      }
   }

   public static class PieceWeight {
      public Class<? extends StructureNetherBridgePieces.Piece> weightClass;
      public final int field_78826_b;
      public int field_78827_c;
      public int field_78824_d;
      public boolean field_78825_e;

      public PieceWeight(Class<? extends StructureNetherBridgePieces.Piece> class2, int i, int j, boolean bl) {
         this.weightClass = class2;
         this.field_78826_b = i;
         this.field_78824_d = j;
         this.field_78825_e = bl;
      }

      public PieceWeight(Class<? extends StructureNetherBridgePieces.Piece> class2, int i, int j) {
         this(class2, i, j, false);
      }

      public boolean func_78822_a(int i) {
         return this.field_78824_d == 0 || this.field_78827_c < this.field_78824_d;
      }

      public boolean func_78823_a() {
         return this.field_78824_d == 0 || this.field_78827_c < this.field_78824_d;
      }
   }

   public static class Stairs extends StructureNetherBridgePieces.Piece {
      public Stairs() {
      }

      public Stairs(int i, Random random2, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(i);
         super.coordBaseMode = enumFacing;
         super.boundingBox = structureBoundingBox;
      }

      @Override
      public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
         this.getNextComponentZ((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 6, 2, false);
      }

      public static StructureNetherBridgePieces.Stairs func_175872_a(
         List<StructureComponent> list, Random random2, int i, int j, int k, int l, EnumFacing enumFacing
      ) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, -2, 0, 0, 7, 11, 7, enumFacing);
         return isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(list, structureboundingbox) == null
            ? new StructureNetherBridgePieces.Stairs(l, random2, structureboundingbox, enumFacing)
            : null;
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 0, 0, 6, 1, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 6, 10, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 2, 0, 1, 8, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 5, 2, 0, 6, 8, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 2, 1, 0, 8, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 6, 2, 1, 6, 8, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 1, 2, 6, 5, 8, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 3, 2, 0, 5, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 6, 3, 2, 6, 5, 2, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 6, 3, 4, 6, 5, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false
         );
         this.setBlockState(worldIn, Blocks.nether_brick.getDefaultState(), 5, 2, 5, structureBoundingBoxIn);
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 4, 2, 5, 4, 3, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 3, 2, 5, 3, 4, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 2, 2, 5, 2, 5, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 1, 2, 5, 1, 6, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 1, 7, 1, 5, 7, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 8, 2, 6, 8, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 2, 6, 0, 4, 8, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 2, 5, 0, 4, 5, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false
         );

         for (int i = 0; i <= 6; i++) {
            for (int j = 0; j <= 6; j++) {
               this.replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), i, -1, j, structureBoundingBoxIn);
            }
         }

         return true;
      }
   }

   public static class Start extends StructureNetherBridgePieces.Crossing3 {
      public StructureNetherBridgePieces.PieceWeight theNetherBridgePieceWeight;
      public List<StructureNetherBridgePieces.PieceWeight> primaryWeights;
      public List<StructureNetherBridgePieces.PieceWeight> secondaryWeights;
      public List<StructureComponent> field_74967_d = Lists.newArrayList();

      public Start() {
      }

      public Start(Random random2, int i, int j) {
         super(random2, i, j);
         this.primaryWeights = Lists.newArrayList();

         for (StructureNetherBridgePieces.PieceWeight structurenetherbridgepieces$pieceweight : StructureNetherBridgePieces.primaryComponents) {
            structurenetherbridgepieces$pieceweight.field_78827_c = 0;
            this.primaryWeights.add(structurenetherbridgepieces$pieceweight);
         }

         this.secondaryWeights = Lists.newArrayList();

         for (StructureNetherBridgePieces.PieceWeight structurenetherbridgepieces$pieceweight1 : StructureNetherBridgePieces.secondaryComponents) {
            structurenetherbridgepieces$pieceweight1.field_78827_c = 0;
            this.secondaryWeights.add(structurenetherbridgepieces$pieceweight1);
         }
      }

      @Override
      public void readStructureFromNBT(NBTTagCompound tagCompound) {
         super.readStructureFromNBT(tagCompound);
      }

      @Override
      public void writeStructureToNBT(NBTTagCompound tagCompound) {
         super.writeStructureToNBT(tagCompound);
      }
   }

   public static class Straight extends StructureNetherBridgePieces.Piece {
      public Straight() {
      }

      public Straight(int i, Random random2, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(i);
         super.coordBaseMode = enumFacing;
         super.boundingBox = structureBoundingBox;
      }

      @Override
      public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
         this.getNextComponentNormal((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 1, 3, false);
      }

      public static StructureNetherBridgePieces.Straight o(List<StructureComponent> list, Random random2, int i, int j, int k, EnumFacing enumFacing, int l) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, -1, -3, 0, 5, 10, 19, enumFacing);
         return isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(list, structureboundingbox) == null
            ? new StructureNetherBridgePieces.Straight(l, random2, structureboundingbox, enumFacing)
            : null;
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 3, 0, 4, 4, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 0, 3, 7, 18, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 5, 0, 0, 5, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 4, 5, 0, 4, 5, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 2, 0, 4, 2, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 2, 13, 4, 2, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 1, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 0, 15, 4, 1, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );

         for (int i = 0; i <= 4; i++) {
            for (int j = 0; j <= 2; j++) {
               this.replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), i, -1, j, structureBoundingBoxIn);
               this.replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), i, -1, 18 - j, structureBoundingBoxIn);
            }
         }

         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 4, 1, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 3, 4, 0, 4, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn,
            structureBoundingBoxIn,
            0,
            3,
            14,
            0,
            4,
            14,
            Blocks.nether_brick_fence.getDefaultState(),
            Blocks.nether_brick_fence.getDefaultState(),
            false
         );
         this.fillWithBlocks(
            worldIn,
            structureBoundingBoxIn,
            0,
            1,
            17,
            0,
            4,
            17,
            Blocks.nether_brick_fence.getDefaultState(),
            Blocks.nether_brick_fence.getDefaultState(),
            false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 4, 1, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 4, 3, 4, 4, 4, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn,
            structureBoundingBoxIn,
            4,
            3,
            14,
            4,
            4,
            14,
            Blocks.nether_brick_fence.getDefaultState(),
            Blocks.nether_brick_fence.getDefaultState(),
            false
         );
         this.fillWithBlocks(
            worldIn,
            structureBoundingBoxIn,
            4,
            1,
            17,
            4,
            4,
            17,
            Blocks.nether_brick_fence.getDefaultState(),
            Blocks.nether_brick_fence.getDefaultState(),
            false
         );
         return true;
      }
   }

   public static class Throne extends StructureNetherBridgePieces.Piece {
      private boolean hasSpawner;

      public Throne() {
      }

      public Throne(int i, Random random2, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(i);
         super.coordBaseMode = enumFacing;
         super.boundingBox = structureBoundingBox;
      }

      @Override
      public void readStructureFromNBT(NBTTagCompound tagCompound) {
         super.readStructureFromNBT(tagCompound);
         this.hasSpawner = tagCompound.getBoolean("Mob");
      }

      @Override
      public void writeStructureToNBT(NBTTagCompound tagCompound) {
         super.writeStructureToNBT(tagCompound);
         tagCompound.setBoolean("Mob", this.hasSpawner);
      }

      public static StructureNetherBridgePieces.Throne d(List<StructureComponent> list, Random random2, int i, int j, int k, int l, EnumFacing enumFacing) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(i, j, k, -2, 0, 0, 7, 8, 9, enumFacing);
         return isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(list, structureboundingbox) == null
            ? new StructureNetherBridgePieces.Throne(l, random2, structureboundingbox, enumFacing)
            : null;
      }

      @Override
      public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
         this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 6, 7, 7, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 1, 0, 0, 5, 1, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 1, 2, 1, 5, 2, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 1, 3, 2, 5, 3, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 1, 4, 3, 5, 4, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 1, 2, 0, 1, 4, 2, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 5, 2, 0, 5, 4, 2, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 1, 5, 2, 1, 5, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 5, 5, 2, 5, 5, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 5, 3, 0, 5, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 6, 5, 3, 6, 5, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 1, 5, 8, 5, 5, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false
         );
         this.setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 1, 6, 3, structureBoundingBoxIn);
         this.setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 5, 6, 3, structureBoundingBoxIn);
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 0, 6, 3, 0, 6, 8, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 6, 6, 3, 6, 6, 8, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 1, 6, 8, 5, 7, 8, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false
         );
         this.fillWithBlocks(
            worldIn, structureBoundingBoxIn, 2, 8, 8, 4, 8, 8, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false
         );
         boolean flag = this.hasSpawner;
         if (!flag) {
            BlockPos blockpos = new BlockPos(this.getXWithOffset(3, 5), this.getYWithOffset(5), this.getZWithOffset(3, 5));
            if (structureBoundingBoxIn.isVecInside(blockpos)) {
               this.hasSpawner = true;
               worldIn.setBlockState(blockpos, Blocks.mob_spawner.getDefaultState(), 2);
               TileEntity tileentity = worldIn.getTileEntity(blockpos);
               if (tileentity instanceof TileEntityMobSpawner) {
                  ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic().setEntityName("Blaze");
               }
            }
         }

         for (int i = 0; i <= 6; i++) {
            for (int j = 0; j <= 6; j++) {
               this.replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), i, -1, j, structureBoundingBoxIn);
            }
         }

         return true;
      }
   }
}
