package net.minecraft.pathfinding;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.pathfinder.SwimNodeProcessor;

public class PathNavigateSwimmer extends PathNavigate {
   private static final long c = -8874399540258537466L;

   public PathNavigateSwimmer(EntityLiving entitylivingIn, World worldIn) {
      super(entitylivingIn, worldIn);
   }

   @Override
   public PathFinder getPathFinder() {
      return new PathFinder(new SwimNodeProcessor());
   }

   @Override
   public boolean canNavigate() {
      return this.isInLiquid();
   }

   @Override
   public Vec3 getEntityPosition() {
      return new Vec3(super.theEntity.posX, super.theEntity.posY + (double)super.theEntity.height * 0.5, super.theEntity.posZ);
   }

   @Override
   public void pathFollow() {
      Vec3 vec3 = this.getEntityPosition();
      float f = super.theEntity.width * super.theEntity.width;
      int i = (int)c;
      if (vec3.squareDistanceTo(super.currentPath.getVectorFromIndex(super.theEntity, super.currentPath.getCurrentPathIndex())) < (double)f) {
         super.currentPath.incrementPathIndex();
      }

      for (int j = Math.min(super.currentPath.getCurrentPathIndex() + i, super.currentPath.getCurrentPathLength() - 1);
         j > super.currentPath.getCurrentPathIndex();
         j--
      ) {
         Vec3 vec31 = super.currentPath.getVectorFromIndex(super.theEntity, j);
         if (vec31.squareDistanceTo(vec3) <= 36.0 && this.isDirectPathBetweenPoints(vec3, vec31, 0, 0, 0)) {
            super.currentPath.setCurrentPathIndex(j);
            break;
         }
      }

      this.checkForStuck(vec3);
   }

   @Override
   public void removeSunnyPath() {
      super.removeSunnyPath();
   }

   @Override
   public boolean isDirectPathBetweenPoints(Vec3 posVec31, Vec3 posVec32, int sizeX, int sizeY, int sizeZ) {
      boolean flag = false;
      MovingObjectPosition movingobjectposition = super.worldObj
         .rayTraceBlocks(posVec31, new Vec3(posVec32.xCoord, posVec32.yCoord + (double)super.theEntity.height * 0.5, posVec32.zCoord), false, true, false);
      return movingobjectposition == null || movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.MISS;
   }
}
