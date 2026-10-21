package net.minecraft.world;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.MathHelper;

public class Teleporter {
   private final WorldServer worldServerInstance;
   private final Random random;
   private final LongHashMap destinationCoordinateCache = new LongHashMap();
   private final List<Long> destinationCoordinateKeys = Lists.newArrayList();

   public Teleporter(WorldServer worldIn) {
      this.worldServerInstance = worldIn;
      this.random = new Random(worldIn.getSeed());
   }

   public void placeInPortal(Entity entityIn, float rotationYaw) {
      if (this.worldServerInstance.provider.getDimensionId() != 1) {
         if (!this.placeInExistingPortal(entityIn, rotationYaw)) {
            this.makePortal(entityIn);
            this.placeInExistingPortal(entityIn, rotationYaw);
         }
      } else {
         int i = MathHelper.floor_double(entityIn.posX);
         int j = MathHelper.floor_double(entityIn.posY) - 1;
         int k = MathHelper.floor_double(entityIn.posZ);

         for (int l = -2; l <= 2; l++) {
            for (int i1 = -2; i1 <= 2; i1++) {
               for (int j1 = -1; j1 < 3; j1++) {
                  int k1 = i + i1 * 1 + l * 0;
                  int l1 = j + j1;
                  int i2 = k + i1 * 0 - l * 1;
                  boolean flag = j1 < 0;
                  this.worldServerInstance.setBlockState(new BlockPos(k1, l1, i2), flag ? Blocks.obsidian.getDefaultState() : Blocks.air.getDefaultState());
               }
            }
         }

         entityIn.setLocationAndAngles((double)i, (double)j, (double)k, entityIn.rotationYaw, 0.0F);
         entityIn.motionX = entityIn.motionY = entityIn.motionZ = 0.0;
      }
   }

   public boolean placeInExistingPortal(Entity entityIn, float rotationYaw) {
      double d0 = -1.0;
      int i = MathHelper.floor_double(entityIn.posX);
      int j = MathHelper.floor_double(entityIn.posZ);
      boolean flag = true;
      Object object = BlockPos.ORIGIN;
      long k = ChunkCoordIntPair.chunkXZ2Int(i, j);
      LongHashMap longhashmap = this.destinationCoordinateCache;
      if (longhashmap.containsItem(k)) {
         Teleporter.PortalPosition teleporter$portalposition = (Teleporter.PortalPosition)this.destinationCoordinateCache.getValueByKey(k);
         d0 = 0.0;
         object = teleporter$portalposition;
         teleporter$portalposition.lastUpdateTime = this.worldServerInstance.getTotalWorldTime();
         flag = false;
      } else {
         BlockPos blockpos2 = new BlockPos(entityIn);

         for (int l = -128; l <= 128; l++) {
            for (int i1 = -128; i1 <= 128; i1++) {
               BlockPos blockpos1 = blockpos2.add(l, this.worldServerInstance.getActualHeight() - 1 - blockpos2.getY(), i1);

               while (blockpos1.getY() >= 0) {
                  BlockPos blockpos = blockpos1.down();
                  if (this.worldServerInstance.getBlockState(blockpos1).getBlock() == Blocks.portal) {
                     while (this.worldServerInstance.getBlockState(blockpos = blockpos1.down()).getBlock() == Blocks.portal) {
                        blockpos1 = blockpos;
                     }

                     double d1 = blockpos1.distanceSq(blockpos2);
                     if (d0 < 0.0 || d1 < d0) {
                        d0 = d1;
                        object = blockpos1;
                     }
                  }

                  blockpos1 = blockpos;
               }
            }
         }
      }

      double d8;
      int j1 = (d8 = d0 - 0.0) == 0.0 ? 0 : (d8 < 0.0 ? -1 : 1);
      if (j1 >= 0) {
         if (flag) {
            this.destinationCoordinateCache.add(k, new Teleporter.PortalPosition(this, (BlockPos)object, this.worldServerInstance.getTotalWorldTime()));
            this.destinationCoordinateKeys.add(k);
         }

         double d5 = (double)((BlockPos)object).getX() + 0.5;
         double d6 = (double)((BlockPos)object).getY() + 0.5;
         double d7 = (double)((BlockPos)object).getZ() + 0.5;
         BlockPattern.PatternHelper blockpattern$patternhelper = Blocks.portal.func_181089_f(this.worldServerInstance, (BlockPos)object);
         boolean flag1 = blockpattern$patternhelper.getFinger().rotateY().getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE;
         double d2 = blockpattern$patternhelper.getFinger().getAxis() == EnumFacing.Axis.X
            ? (double)blockpattern$patternhelper.getPos().getZ()
            : (double)blockpattern$patternhelper.getPos().getX();
         d6 = (double)(blockpattern$patternhelper.getPos().getY() + 1) - entityIn.func_181014_aG().yCoord * (double)blockpattern$patternhelper.func_181119_e();
         if (flag1) {
            d2++;
         }

         if (blockpattern$patternhelper.getFinger().getAxis() == EnumFacing.Axis.X) {
            d7 = d2
               + (1.0 - entityIn.func_181014_aG().xCoord)
                  * (double)blockpattern$patternhelper.func_181118_d()
                  * (double)blockpattern$patternhelper.getFinger().rotateY().getAxisDirection().getOffset();
         } else {
            d5 = d2
               + (1.0 - entityIn.func_181014_aG().xCoord)
                  * (double)blockpattern$patternhelper.func_181118_d()
                  * (double)blockpattern$patternhelper.getFinger().rotateY().getAxisDirection().getOffset();
         }

         float f = 0.0F;
         float f1 = 0.0F;
         float f2 = 0.0F;
         float f3 = 0.0F;
         if (blockpattern$patternhelper.getFinger().getOpposite() == entityIn.getTeleportDirection()) {
            f = 1.0F;
            f1 = 1.0F;
         } else if (blockpattern$patternhelper.getFinger().getOpposite() == entityIn.getTeleportDirection().getOpposite()) {
            f = -1.0F;
            f1 = -1.0F;
         } else if (blockpattern$patternhelper.getFinger().getOpposite() == entityIn.getTeleportDirection().rotateY()) {
            f2 = 1.0F;
            f3 = -1.0F;
         } else {
            f2 = -1.0F;
            f3 = 1.0F;
         }

         double d3 = entityIn.motionX;
         double d4 = entityIn.motionZ;
         entityIn.motionX = d3 * (double)f + d4 * (double)f3;
         entityIn.motionZ = d3 * (double)f2 + d4 * (double)f1;
         entityIn.rotationYaw = rotationYaw
            - (float)(entityIn.getTeleportDirection().getOpposite().getHorizontalIndex() * 90)
            + (float)(blockpattern$patternhelper.getFinger().getHorizontalIndex() * 90);
         entityIn.setLocationAndAngles(d5, d6, d7, entityIn.rotationYaw, entityIn.rotationPitch);
         return true;
      } else {
         return false;
      }
   }

   public boolean makePortal(Entity entityIn) {
      double d0 = -1.0;
      int i = MathHelper.floor_double(entityIn.posX);
      int j = MathHelper.floor_double(entityIn.posY);
      int k = MathHelper.floor_double(entityIn.posZ);
      int l = i;
      int i1 = j;
      int j1 = k;
      int k1 = 0;
      int l1 = this.random.nextInt(4);
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for (int i2 = i - 16; i2 <= i + 16; i2++) {
         double d1 = (double)i2 + 0.5 - entityIn.posX;
         int k5 = k - 16;

         for (int k2 = k5; k2 <= k + 16; k2++) {
            double d2 = (double)k2 + 0.5 - entityIn.posZ;

            label294:
            for (int i3 = this.worldServerInstance.getActualHeight() - 1; i3 >= 0; i3--) {
               if (this.worldServerInstance.isAirBlock(blockpos$mutableblockpos.set(i2, i3, k2))) {
                  while (i3 > 0 && this.worldServerInstance.isAirBlock(blockpos$mutableblockpos.set(i2, i3 - 1, k2))) {
                     i3--;
                  }

                  for (int j3 = l1; j3 < l1 + 4; j3++) {
                     int k3 = j3 % 2;
                     int l3 = 1 - k3;
                     if (j3 % 4 >= 2) {
                        k3 = -k3;
                        l3 = -l3;
                     }

                     for (int i4 = 0; i4 < 3; i4++) {
                        for (int j4 = 0; j4 < 4; j4++) {
                           for (int k4 = -1; k4 < 4; k4++) {
                              int l4 = i2 + (j4 - 1) * k3 + i4 * l3;
                              int i5 = i3 + k4;
                              int j5 = k2 + (j4 - 1) * l3 - i4 * k3;
                              blockpos$mutableblockpos.set(l4, i5, j5);
                              if (k4 < 0 && !this.worldServerInstance.getBlockState(blockpos$mutableblockpos).getBlock().getMaterial().isSolid()
                                 || k4 >= 0 && !this.worldServerInstance.isAirBlock(blockpos$mutableblockpos)) {
                                 continue label294;
                              }
                           }
                        }
                     }

                     double d5 = (double)i3 + 0.5 - entityIn.posY;
                     double d7 = d1 * d1 + d5 * d5 + d2 * d2;
                     if (d0 < 0.0 || d7 < d0) {
                        d0 = d7;
                        l = i2;
                        i1 = i3;
                        j1 = k2;
                        k1 = j3 % 4;
                     }
                  }
               }
            }
         }
      }

      double d9;
      int l5 = (d9 = d0 - 0.0) == 0.0 ? 0 : (d9 < 0.0 ? -1 : 1);
      if (l5 < 0) {
         for (int k6 = i - 16; k6 <= i + 16; k6++) {
            double d3 = (double)k6 + 0.5 - entityIn.posX;

            for (int j7 = k - 16; j7 <= k + 16; j7++) {
               double d4 = (double)j7 + 0.5 - entityIn.posZ;

               label231:
               for (int i8 = this.worldServerInstance.getActualHeight() - 1; i8 >= 0; i8--) {
                  if (this.worldServerInstance.isAirBlock(blockpos$mutableblockpos.set(k6, i8, j7))) {
                     while (i8 > 0 && this.worldServerInstance.isAirBlock(blockpos$mutableblockpos.set(k6, i8 - 1, j7))) {
                        i8--;
                     }

                     for (int l8 = l1; l8 < l1 + 2; l8++) {
                        int l9 = l8 % 2;
                        int l10 = 1 - l9;

                        for (int l11 = 0; l11 < 4; l11++) {
                           for (int l12 = -1; l12 < 4; l12++) {
                              int k13 = k6 + (l11 - 1) * l9;
                              int l13 = i8 + l12;
                              int i14 = j7 + (l11 - 1) * l10;
                              blockpos$mutableblockpos.set(k13, l13, i14);
                              if (l12 < 0 && !this.worldServerInstance.getBlockState(blockpos$mutableblockpos).getBlock().getMaterial().isSolid()
                                 || l12 >= 0 && !this.worldServerInstance.isAirBlock(blockpos$mutableblockpos)) {
                                 continue label231;
                              }
                           }
                        }

                        double d6 = (double)i8 + 0.5 - entityIn.posY;
                        double d8 = d3 * d3 + d6 * d6 + d4 * d4;
                        if (d0 < 0.0 || d8 < d0) {
                           d0 = d8;
                           l = k6;
                           i1 = i8;
                           j1 = j7;
                           k1 = l8 % 2;
                        }
                     }
                  }
               }
            }
         }
      }

      int l6 = l;
      int i7 = i1;
      int j2 = j1;
      int k7 = k1 % 2;
      int l7 = 1 - k7;
      int i6 = k1 % 4;
      if (i6 >= 2) {
         k7 = -k7;
         l7 = -l7;
      }

      double d10;
      int j6 = (d10 = d0 - 0.0) == 0.0 ? 0 : (d10 < 0.0 ? -1 : 1);
      if (j6 < 0) {
         i1 = MathHelper.clamp_int(i1, 70, this.worldServerInstance.getActualHeight() - 10);
         i7 = i1;

         for (int l2 = -1; l2 <= 1; l2++) {
            for (int j8 = 1; j8 < 3; j8++) {
               for (int i9 = -1; i9 < 3; i9++) {
                  int i10 = l6 + (j8 - 1) * k7 + l2 * l7;
                  int i11 = i7 + i9;
                  int i12 = j2 + (j8 - 1) * l7 - l2 * k7;
                  boolean flag = i9 < 0;
                  this.worldServerInstance.setBlockState(new BlockPos(i10, i11, i12), flag ? Blocks.obsidian.getDefaultState() : Blocks.air.getDefaultState());
               }
            }
         }
      }

      IBlockState iblockstate = Blocks.portal.getDefaultState().withProperty(BlockPortal.AXIS, k7 != 0 ? EnumFacing.Axis.X : EnumFacing.Axis.Z);

      for (int k8 = 0; k8 < 4; k8++) {
         for (int j9 = 0; j9 < 4; j9++) {
            for (int j10 = -1; j10 < 4; j10++) {
               int j11 = l6 + (j9 - 1) * k7;
               int j12 = i7 + j10;
               int i13 = j2 + (j9 - 1) * l7;
               boolean flag1 = j9 == 0 || j9 == 3 || j10 == -1 || j10 == 3;
               this.worldServerInstance.setBlockState(new BlockPos(j11, j12, i13), flag1 ? Blocks.obsidian.getDefaultState() : iblockstate, 2);
            }
         }

         for (int k9 = 0; k9 < 4; k9++) {
            for (int k10 = -1; k10 < 4; k10++) {
               int k11 = l6 + (k9 - 1) * k7;
               int k12 = i7 + k10;
               int j13 = j2 + (k9 - 1) * l7;
               BlockPos blockpos = new BlockPos(k11, k12, j13);
               this.worldServerInstance.notifyNeighborsOfStateChange(blockpos, this.worldServerInstance.getBlockState(blockpos).getBlock());
            }
         }
      }

      return true;
   }

   public void removeStalePortalLocations(long worldTime) {
      if (worldTime % 100L == 0L) {
         Iterator iterator = this.destinationCoordinateKeys.iterator();
         long i = worldTime - 300L;

         while (iterator.hasNext()) {
            Long olong = (Long)iterator.next();
            Teleporter.PortalPosition teleporter$portalposition = (Teleporter.PortalPosition)this.destinationCoordinateCache.getValueByKey(olong);
            if (teleporter$portalposition == null || teleporter$portalposition.lastUpdateTime < i) {
               iterator.remove();
               this.destinationCoordinateCache.remove(olong);
            }
         }
      }
   }

   public class PortalPosition extends BlockPos {
      public long lastUpdateTime;
      final Teleporter field_85088_e;

      public PortalPosition(Teleporter this$0, BlockPos pos, long lastUpdate) {
         super(pos.getX(), pos.getY(), pos.getZ());
         this.field_85088_e = this$0;
         this.lastUpdateTime = lastUpdate;
      }
   }
}
