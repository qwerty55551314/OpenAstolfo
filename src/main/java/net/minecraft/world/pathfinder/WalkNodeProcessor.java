package net.minecraft.world.pathfinder;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;

public class WalkNodeProcessor extends NodeProcessor {
   private boolean canEnterDoors;
   private boolean canBreakDoors;
   private boolean avoidsWater;
   private boolean canSwim;
   private boolean shouldAvoidWater;

   @Override
   public void initProcessor(IBlockAccess iblockaccessIn, Entity entityIn) {
      super.initProcessor(iblockaccessIn, entityIn);
      this.shouldAvoidWater = this.avoidsWater;
   }

   @Override
   public void postProcess() {
      super.postProcess();
      this.avoidsWater = this.shouldAvoidWater;
   }

   @Override
   public PathPoint getPathPointTo(Entity entityIn) {
      int i;
      if (this.canSwim && entityIn.isInWater()) {
         i = (int)entityIn.getEntityBoundingBox().minY;
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(
            MathHelper.floor_double(entityIn.posX), i, MathHelper.floor_double(entityIn.posZ)
         );

         for (Block block = super.blockaccess.getBlockState(blockpos$mutableblockpos).getBlock();
            block == Blocks.flowing_water || block == Blocks.water;
            block = super.blockaccess.getBlockState(blockpos$mutableblockpos).getBlock()
         ) {
            blockpos$mutableblockpos.set(MathHelper.floor_double(entityIn.posX), ++i, MathHelper.floor_double(entityIn.posZ));
         }

         this.avoidsWater = false;
      } else {
         i = MathHelper.floor_double(entityIn.getEntityBoundingBox().minY + 0.5);
      }

      return this.openPoint(MathHelper.floor_double(entityIn.getEntityBoundingBox().minX), i, MathHelper.floor_double(entityIn.getEntityBoundingBox().minZ));
   }

   @Override
   public PathPoint getPathPointToCoords(Entity entityIn, double x, double y, double target) {
      return this.openPoint(
         MathHelper.floor_double(x - (double)(entityIn.width / 2.0F)),
         MathHelper.floor_double(y),
         MathHelper.floor_double(target - (double)(entityIn.width / 2.0F))
      );
   }

   @Override
   public int findPathOptions(PathPoint[] pathOptions, Entity entityIn, PathPoint currentPoint, PathPoint targetPoint, float maxDistance) {
      int i = 0;
      int b0 = 0;
      int i1 = currentPoint.yCoord + 1;
      int l = currentPoint.zCoord;
      int k = i1;
      int j = currentPoint.xCoord;
      if (this.getVerticalOffset(entityIn, j, k, l) == 1) {
         b0 = 1;
      }

      PathPoint pathpoint = this.getSafePoint(entityIn, currentPoint.xCoord, currentPoint.yCoord, currentPoint.zCoord + 1, b0);
      PathPoint pathpoint1 = this.getSafePoint(entityIn, currentPoint.xCoord - 1, currentPoint.yCoord, currentPoint.zCoord, b0);
      PathPoint pathpoint2 = this.getSafePoint(entityIn, currentPoint.xCoord + 1, currentPoint.yCoord, currentPoint.zCoord, b0);
      PathPoint pathpoint3 = this.getSafePoint(entityIn, currentPoint.xCoord, currentPoint.yCoord, currentPoint.zCoord - 1, b0);
      if (pathpoint != null && !pathpoint.visited && pathpoint.distanceTo(targetPoint) < maxDistance) {
         pathOptions[i++] = pathpoint;
      }

      if (pathpoint1 != null && !pathpoint1.visited && pathpoint1.distanceTo(targetPoint) < maxDistance) {
         pathOptions[i++] = pathpoint1;
      }

      if (pathpoint2 != null && !pathpoint2.visited && pathpoint2.distanceTo(targetPoint) < maxDistance) {
         pathOptions[i++] = pathpoint2;
      }

      if (pathpoint3 != null) {
         boolean flag = pathpoint3.visited;
         if (!flag && pathpoint3.distanceTo(targetPoint) < maxDistance) {
            pathOptions[i++] = pathpoint3;
         }
      }

      return i;
   }

   private PathPoint getSafePoint(Entity entityIn, int x, int y, int z, int i) {
      PathPoint pathpoint = null;
      int i1 = this.getVerticalOffset(entityIn, x, y, z);
      if (i1 == 2) {
         return this.openPoint(x, y, z);
      } else {
         if (i1 == 1) {
            pathpoint = this.openPoint(x, y, z);
         }

         if (pathpoint == null && i > 0 && i1 != -3 && i1 != -4 && this.getVerticalOffset(entityIn, x, y + i, z) == 1) {
            pathpoint = this.openPoint(x, y + i, z);
            y += i;
         }

         if (pathpoint != null) {
            int j = 0;

            int k;
            for (k = 0; y > 0; pathpoint = this.openPoint(x, y, z)) {
               k = this.getVerticalOffset(entityIn, x, y - 1, z);
               if (this.avoidsWater && k == -1) {
                  return null;
               }

               if (k != 1) {
                  break;
               }

               if (j++ >= entityIn.getMaxFallHeight()) {
                  return null;
               }

               if (--y <= 0) {
                  return null;
               }
            }

            if (k == -2) {
               return null;
            }
         }

         return pathpoint;
      }
   }

   private int getVerticalOffset(Entity entityIn, int x, int y, int z) {
      return func_176170_a(
         super.blockaccess,
         entityIn,
         x,
         y,
         z,
         super.entitySizeX,
         super.entitySizeY,
         super.entitySizeZ,
         this.avoidsWater,
         this.canBreakDoors,
         this.canEnterDoors
      );
   }

   public static int func_176170_a(
      IBlockAccess blockaccessIn,
      Entity entityIn,
      int x,
      int y,
      int z,
      int sizeX,
      int sizeY,
      int sizeZ,
      boolean avoidWater,
      boolean breakDoors,
      boolean enterDoors
   ) {
      Object object = null;
      boolean flag = false;
      BlockPos blockpos = new BlockPos(entityIn);
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for (int i = x; i < x + sizeX; i++) {
         for (int j = y; j < y + sizeY; j++) {
            for (int k = z; k < z + sizeZ; k++) {
               blockpos$mutableblockpos.set(i, j, k);
               Block block = blockaccessIn.getBlockState(blockpos$mutableblockpos).getBlock();
               if (block.getMaterial() != Material.air) {
                  if (block == Blocks.trapdoor || block == Blocks.iron_trapdoor) {
                     flag = true;
                  } else if (block != Blocks.flowing_water && block != Blocks.water) {
                     if (!enterDoors && block instanceof BlockDoor && block.getMaterial() == Material.wood) {
                        return 0;
                     }
                  } else {
                     if (avoidWater) {
                        return -1;
                     }

                     flag = true;
                  }

                  if (entityIn.worldObj.getBlockState(blockpos$mutableblockpos).getBlock() instanceof BlockRailBase) {
                     if (!(entityIn.worldObj.getBlockState(blockpos).getBlock() instanceof BlockRailBase)
                        && !(entityIn.worldObj.getBlockState(blockpos.down()).getBlock() instanceof BlockRailBase)) {
                        return -3;
                     }
                  } else if (!block.isPassable(blockaccessIn, blockpos$mutableblockpos)
                     && (!breakDoors || !(block instanceof BlockDoor) || block.getMaterial() != Material.wood)) {
                     if (block instanceof BlockFence || block instanceof BlockFenceGate || block instanceof BlockWall) {
                        return -3;
                     }

                     if (block == Blocks.trapdoor || block == Blocks.iron_trapdoor) {
                        return -4;
                     }

                     Material material = block.getMaterial();
                     if (material != Material.lava) {
                        return 0;
                     }

                     if (!entityIn.isInLava()) {
                        return -2;
                     }
                  }
               }
            }
         }
      }

      return flag ? 2 : 1;
   }

   public void setEnterDoors(boolean canEnterDoorsIn) {
      this.canEnterDoors = canEnterDoorsIn;
   }

   public void setBreakDoors(boolean canBreakDoorsIn) {
      this.canBreakDoors = canBreakDoorsIn;
   }

   public void setAvoidsWater(boolean avoidsWaterIn) {
      this.avoidsWater = avoidsWaterIn;
   }

   public void setCanSwim(boolean canSwimIn) {
      this.canSwim = canSwimIn;
   }

   public boolean getEnterDoors() {
      return this.canEnterDoors;
   }

   public boolean getCanSwim() {
      return this.canSwim;
   }

   public boolean getAvoidsWater() {
      return this.avoidsWater;
   }
}
