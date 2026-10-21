package net.minecraft.world.pathfinder;

import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;

public abstract class NodeProcessor {
   public IBlockAccess blockaccess;
   public IntHashMap<PathPoint> pointMap = new IntHashMap<>();
   public int entitySizeX;
   public int entitySizeY;
   public int entitySizeZ;
   private static int[] t;

   public void initProcessor(IBlockAccess iblockaccessIn, Entity entityIn) {
      this.blockaccess = iblockaccessIn;
      this.pointMap.clearMap();
      this.entitySizeX = MathHelper.floor_float(entityIn.width + 1.0F);
      this.entitySizeY = MathHelper.floor_float(entityIn.height + 1.0F);
      this.entitySizeZ = MathHelper.floor_float(entityIn.width + 1.0F);
   }

   public void postProcess() {
   }

   public PathPoint openPoint(int x, int y, int z) {
      int i = PathPoint.makeHash(x, y, z);
      PathPoint pathpoint = this.pointMap.lookup(i);
      if (pathpoint == null) {
         pathpoint = new PathPoint(x, y, z);
         this.pointMap.addKey(i, pathpoint);
      }

      return pathpoint;
   }

   public abstract PathPoint getPathPointTo(Entity var1);

   public abstract PathPoint getPathPointToCoords(Entity var1, double var2, double var4, double var6);

   public abstract int findPathOptions(PathPoint[] var1, Entity var2, PathPoint var3, PathPoint var4, float var5);

   public static void H(int[] iArray) {
      t = iArray;
   }

   public static int[] p() {
      return t;
   }
}
