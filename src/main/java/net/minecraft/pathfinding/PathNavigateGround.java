package net.minecraft.pathfinding;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.pathfinder.WalkNodeProcessor;

public class PathNavigateGround extends PathNavigate {
   public WalkNodeProcessor nodeProcessor;
   private boolean shouldAvoidSun;
   private static final long i = -1421992564227047408L;

   public PathNavigateGround(EntityLiving entitylivingIn, World worldIn) {
      super(entitylivingIn, worldIn);
   }

   @Override
   public PathFinder getPathFinder() {
      this.nodeProcessor = new WalkNodeProcessor();
      this.nodeProcessor.setEnterDoors(true);
      return new PathFinder(this.nodeProcessor);
   }

   @Override
   public boolean canNavigate() {
      return super.theEntity.onGround
         || this.getCanSwim() && this.isInLiquid()
         || super.theEntity.isRiding() && super.theEntity instanceof EntityZombie && super.theEntity.ridingEntity instanceof EntityChicken;
   }

   @Override
   public Vec3 getEntityPosition() {
      return new Vec3(super.theEntity.posX, (double)this.getPathablePosY(), super.theEntity.posZ);
   }

   private int getPathablePosY() {
      if (super.theEntity.isInWater() && this.getCanSwim()) {
         int ix = (int)super.theEntity.getEntityBoundingBox().minY;
         Block block = super.worldObj
            .getBlockState(new BlockPos(MathHelper.floor_double(super.theEntity.posX), ix, MathHelper.floor_double(super.theEntity.posZ)))
            .getBlock();
         int j = 0;

         while (block == Blocks.flowing_water || block == Blocks.water) {
            block = super.worldObj
               .getBlockState(new BlockPos(MathHelper.floor_double(super.theEntity.posX), ++ix, MathHelper.floor_double(super.theEntity.posZ)))
               .getBlock();
            if (++j > (int)i) {
               return (int)super.theEntity.getEntityBoundingBox().minY;
            }
         }

         return ix;
      } else {
         return (int)(super.theEntity.getEntityBoundingBox().minY + 0.5);
      }
   }

   @Override
   public void removeSunnyPath() {
      super.removeSunnyPath();
      boolean flag = this.shouldAvoidSun;
      if (flag) {
         boolean flag1 = super.worldObj
            .canSeeSky(
               new BlockPos(
                  MathHelper.floor_double(super.theEntity.posX),
                  (int)(super.theEntity.getEntityBoundingBox().minY + 0.5),
                  MathHelper.floor_double(super.theEntity.posZ)
               )
            );
         if (flag1) {
            return;
         }

         for (int ix = 0; ix < super.currentPath.getCurrentPathLength(); ix++) {
            PathPoint pathpoint = super.currentPath.getPathPointFromIndex(ix);
            if (super.worldObj.canSeeSky(new BlockPos(pathpoint.xCoord, pathpoint.yCoord, pathpoint.zCoord))) {
               super.currentPath.setCurrentPathLength(ix - 1);
               return;
            }
         }
      }
   }

   @Override
   public boolean isDirectPathBetweenPoints(Vec3 posVec31, Vec3 posVec32, int sizeX, int sizeY, int sizeZ) {
      int ix = MathHelper.floor_double(posVec31.xCoord);
      int j = MathHelper.floor_double(posVec31.zCoord);
      double d0 = posVec32.xCoord - posVec31.xCoord;
      double d1 = posVec32.zCoord - posVec31.zCoord;
      double d2 = d0 * d0 + d1 * d1;
      double d8 = 1.0E-8;
      if (d2 < d8) {
         return false;
      } else {
         double d3 = 1.0 / Math.sqrt(d2);
         d0 *= d3;
         d1 *= d3;
         sizeX += 2;
         sizeZ += 2;
         boolean flag = this.isSafeToStandAt(ix, (int)posVec31.yCoord, j, sizeX, sizeY, sizeZ, posVec31, d0, d1);
         if (!flag) {
            return false;
         } else {
            sizeX -= 2;
            sizeZ -= 2;
            double d4 = 1.0 / Math.abs(d0);
            double d5 = 1.0 / Math.abs(d1);
            double d6 = (double)(ix * 1) - posVec31.xCoord;
            double d7 = (double)(j * 1) - posVec31.zCoord;
            double d9;
            int i2 = (d9 = d0 - 0.0) == 0.0 ? 0 : (d9 < 0.0 ? -1 : 1);
            if (i2 >= 0) {
               d6++;
            }

            double d10;
            int j2 = (d10 = d1 - 0.0) == 0.0 ? 0 : (d10 < 0.0 ? -1 : 1);
            if (j2 >= 0) {
               d7++;
            }

            d6 /= d0;
            d7 /= d1;
            double d11;
            int k2 = (d11 = d0 - 0.0) == 0.0 ? 0 : (d11 < 0.0 ? -1 : 1);
            int k = k2 < 0 ? -1 : 1;
            double d12;
            int l2 = (d12 = d1 - 0.0) == 0.0 ? 0 : (d12 < 0.0 ? -1 : 1);
            int l = l2 < 0 ? -1 : 1;
            int i1 = MathHelper.floor_double(posVec32.xCoord);
            int j1 = MathHelper.floor_double(posVec32.zCoord);
            int k1 = i1 - ix;
            int l1 = j1 - j;

            do {
               if (k1 * k <= 0) {
                  int i3 = l1 * l;
                  if (i3 <= 0) {
                     return true;
                  }
               }

               double d13;
               int j3 = (d13 = d6 - d7) == 0.0 ? 0 : (d13 < 0.0 ? -1 : 1);
               if (j3 < 0) {
                  d6 += d4;
                  ix += k;
                  k1 = i1 - ix;
               } else {
                  d7 += d5;
                  j += l;
                  l1 = j1 - j;
               }
            } while (this.isSafeToStandAt(ix, (int)posVec31.yCoord, j, sizeX, sizeY, sizeZ, posVec31, d0, d1));

            return false;
         }
      }
   }

   private boolean isSafeToStandAt(int x, int y, int z, int sizeX, int sizeY, int sizeZ, Vec3 vec31, double d, double e) {
      int ix = x - sizeX / 2;
      int j = z - sizeZ / 2;
      boolean flag = this.isPositionClear(ix, y, j, sizeX, sizeY, sizeZ, vec31, d, e);
      if (!flag) {
         return false;
      } else {
         for (int k = ix; k < ix + sizeX; k++) {
            for (int l = j; l < j + sizeZ; l++) {
               double d0 = (double)k + 0.5 - vec31.xCoord;
               double d1 = (double)l + 0.5 - vec31.zCoord;
               if (d0 * d + d1 * e >= 0.0) {
                  Block block = super.worldObj.getBlockState(new BlockPos(k, y - 1, l)).getBlock();
                  Material material = block.getMaterial();
                  if (material == Material.air) {
                     return false;
                  }

                  if (material == Material.water && !super.theEntity.isInWater()) {
                     return false;
                  }

                  if (material == Material.lava) {
                     return false;
                  }
               }
            }
         }

         return true;
      }
   }

   private boolean isPositionClear(int i, int j, int k, int l, int m, int n, Vec3 vec3, double d, double e) {
      boolean flag = true;

      for (BlockPos blockpos : BlockPos.getAllInBox(new BlockPos(i, j, k), new BlockPos(i + l - 1, j + m - 1, k + n - 1))) {
         double d0 = (double)blockpos.getX() + 0.5 - vec3.xCoord;
         double d1 = (double)blockpos.getZ() + 0.5 - vec3.zCoord;
         if (d0 * d + d1 * e >= 0.0) {
            Block block = super.worldObj.getBlockState(blockpos).getBlock();
            if (!block.isPassable(super.worldObj, blockpos)) {
               return false;
            }
         }
      }

      return true;
   }

   public void setAvoidsWater(boolean avoidsWater) {
      this.nodeProcessor.setAvoidsWater(avoidsWater);
   }

   public boolean getAvoidsWater() {
      return this.nodeProcessor.getAvoidsWater();
   }

   public void setBreakDoors(boolean canBreakDoors) {
      this.nodeProcessor.setBreakDoors(canBreakDoors);
   }

   public void setEnterDoors(boolean par1) {
      this.nodeProcessor.setEnterDoors(par1);
   }

   public boolean getEnterDoors() {
      return this.nodeProcessor.getEnterDoors();
   }

   public void setCanSwim(boolean canSwim) {
      this.nodeProcessor.setCanSwim(canSwim);
   }

   public boolean getCanSwim() {
      return this.nodeProcessor.getCanSwim();
   }

   public void setAvoidSun(boolean par1) {
      this.shouldAvoidSun = par1;
   }
}
