package shadersmod.client;

import java.util.Iterator;
import java.util.NoSuchElementException;
import net.minecraft.src.BlockPosM;
import net.minecraft.util.BlockPos;

public class IteratorAxis implements Iterator<BlockPos> {
   private double yDelta;
   private double zDelta;
   private int xStart;
   private int xEnd;
   private double yStart;
   private double yEnd;
   private double zStart;
   private double zEnd;
   private int xNext;
   private double yNext;
   private double zNext;
   private BlockPosM pos = new BlockPosM(0, 0, 0);
   private boolean hasNext = false;

   public IteratorAxis(BlockPos posStart, BlockPos posEnd, double yDelta, double zDelta) {
      this.yDelta = yDelta;
      this.zDelta = zDelta;
      this.xStart = posStart.getX();
      this.xEnd = posEnd.getX();
      this.yStart = (double)posStart.getY();
      this.yEnd = (double)posEnd.getY() - 0.5;
      this.zStart = (double)posStart.getZ();
      this.zEnd = (double)posEnd.getZ() - 0.5;
      this.xNext = this.xStart;
      this.yNext = this.yStart;
      this.zNext = this.zStart;
      this.hasNext = this.xNext < this.xEnd && this.yNext < this.yEnd && this.zNext < this.zEnd;
   }

   @Override
   public boolean hasNext() {
      return this.hasNext;
   }

   public BlockPos next() {
      if (!this.hasNext) {
         throw new NoSuchElementException();
      } else {
         IteratorAxis iteratoraxis;
         boolean flag;
         label17: {
            this.pos.setXyz((double)this.xNext, this.yNext, this.zNext);
            this.nextPos();
            int i = this.xNext;
            iteratoraxis = this;
            if (i < this.xEnd) {
               double d0;
               int j = (d0 = this.yNext - this.yEnd) == 0.0 ? 0 : (d0 < 0.0 ? -1 : 1);
               iteratoraxis = this;
               if (j < 0) {
                  double d1;
                  int k = (d1 = this.zNext - this.zEnd) == 0.0 ? 0 : (d1 < 0.0 ? -1 : 1);
                  iteratoraxis = this;
                  if (k < 0) {
                     flag = true;
                     break label17;
                  }
               }
            }

            flag = false;
         }

         iteratoraxis.hasNext = flag;
         return this.pos;
      }
   }

   private void nextPos() {
      boolean flag = true;
      this.zNext++;
      double d0;
      int i = (d0 = this.zNext - this.zEnd) == 0.0 ? 0 : (d0 < 0.0 ? -1 : 1);
      if (i >= 0) {
         this.zNext = this.zStart;
         this.yNext++;
         double d1;
         int j = (d1 = this.yNext - this.yEnd) == 0.0 ? 0 : (d1 < 0.0 ? -1 : 1);
         if (j >= 0) {
            this.yNext = this.yStart;
            this.yStart = this.yStart + this.yDelta;
            this.yEnd = this.yEnd + this.yDelta;
            this.yNext = this.yStart;
            this.zStart = this.zStart + this.zDelta;
            this.zEnd = this.zEnd + this.zDelta;
            this.zNext = this.zStart;
            this.xNext++;
            if (this.xNext >= this.xEnd) {
            }
         }
      }
   }

   @Override
   public void remove() {
      throw new RuntimeException("Not implemented");
   }

   public static void main(String[] args) throws Exception {
      boolean flag = false;
      BlockPos blockpos = new BlockPos(-2, 10, 20);
      BlockPos blockpos1 = new BlockPos(2, 12, 22);
      double d0 = -0.5;
      double d1 = 0.5;
      IteratorAxis iteratoraxis = new IteratorAxis(blockpos, blockpos1, d0, d1);
      System.out.println("Start: " + blockpos + ", end: " + blockpos1 + ", yDelta: " + d0 + ", zDelta: " + d1);

      while (iteratoraxis.hasNext()) {
         BlockPos blockpos2 = iteratoraxis.next();
         System.out.println("" + blockpos2);
      }
   }
}
