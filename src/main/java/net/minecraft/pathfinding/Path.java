package net.minecraft.pathfinding;

public class Path {
   private PathPoint[] pathPoints;
   private int count;
   private static final String b = "OW KNOWS!";
   private static final long c = -6084360137345072128L;

   public Path() {
      this.pathPoints = new PathPoint[(int)c];
   }

   public PathPoint addPoint(PathPoint point) {
      if (point.index >= 0) {
         throw new IllegalStateException(b);
      } else {
         if (this.count == this.pathPoints.length) {
            PathPoint[] apathpoint = new PathPoint[this.count << 1];
            System.arraycopy(this.pathPoints, 0, apathpoint, 0, this.count);
            this.pathPoints = apathpoint;
         }

         this.pathPoints[this.count] = point;
         point.index = this.count;
         this.sortBack(this.count++);
         return point;
      }
   }

   public void clearPath() {
      this.count = 0;
   }

   public PathPoint dequeue() {
      PathPoint pathpoint = this.pathPoints[0];
      this.pathPoints[0] = this.pathPoints[--this.count];
      this.pathPoints[this.count] = null;
      if (this.count > 0) {
         this.sortForward(0);
      }

      pathpoint.index = -1;
      return pathpoint;
   }

   public void changeDistance(PathPoint pathPoint, float f) {
      float f1 = pathPoint.distanceToTarget;
      pathPoint.distanceToTarget = f;
      if (f < f1) {
         this.sortBack(pathPoint.index);
      } else {
         this.sortForward(pathPoint.index);
      }
   }

   private void sortBack(int i) {
      PathPoint pathpoint = this.pathPoints[i];
      float f = pathpoint.distanceToTarget;

      while (i > 0) {
         int i1 = i - 1 >> 1;
         PathPoint pathpoint1 = this.pathPoints[i1];
         if (f >= pathpoint1.distanceToTarget) {
            break;
         }

         this.pathPoints[i] = pathpoint1;
         pathpoint1.index = i;
         i = i1;
      }

      this.pathPoints[i] = pathpoint;
      pathpoint.index = i;
   }

   private void sortForward(int i) {
      PathPoint pathpoint = this.pathPoints[i];
      float f = pathpoint.distanceToTarget;

      while (true) {
         int i1 = 1 + (i << 1);
         int j = i1 + 1;
         if (i1 >= this.count) {
            break;
         }

         PathPoint pathpoint1 = this.pathPoints[i1];
         float f1 = pathpoint1.distanceToTarget;
         PathPoint pathpoint2;
         float f2;
         if (j >= this.count) {
            pathpoint2 = null;
            f2 = Float.POSITIVE_INFINITY;
         } else {
            pathpoint2 = this.pathPoints[j];
            f2 = pathpoint2.distanceToTarget;
         }

         float f3;
         int k = (f3 = f1 - f2) == 0.0F ? 0 : (f3 < 0.0F ? -1 : 1);
         if (k < 0) {
            if (f1 >= f) {
               break;
            }

            this.pathPoints[i] = pathpoint1;
            pathpoint1.index = i;
            i = i1;
         } else {
            float f4;
            int l = (f4 = f2 - f) == 0.0F ? 0 : (f4 < 0.0F ? -1 : 1);
            if (l >= 0) {
               break;
            }

            this.pathPoints[i] = pathpoint2;
            pathpoint2.index = i;
            i = j;
         }
      }

      this.pathPoints[i] = pathpoint;
      pathpoint.index = i;
   }

   public boolean isPathEmpty() {
      return this.count == 0;
   }
}
