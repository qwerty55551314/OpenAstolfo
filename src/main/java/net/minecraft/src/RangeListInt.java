package net.minecraft.src;

public class RangeListInt {
   private RangeInt[] ranges = new RangeInt[0];
   private static final String b = ", ";

   public void addRange(RangeInt ri) {
      this.ranges = (RangeInt[])Config.addObjectToArray(this.ranges, ri);
   }

   public boolean isInRange(int val) {
      for (int i = 0; i < this.ranges.length; i++) {
         RangeInt rangeint = this.ranges[i];
         if (rangeint.isInRange(val)) {
            return true;
         }
      }

      return false;
   }

   public int getCountRanges() {
      return this.ranges.length;
   }

   public RangeInt getRange(int i) {
      return this.ranges[i];
   }

   @Override
   public String toString() {
      boolean flag = false;
      StringBuffer stringbuffer = new StringBuffer();
      stringbuffer.append("[");

      for (int i = 0; i < this.ranges.length; i++) {
         RangeInt rangeint = this.ranges[i];
         if (i > 0) {
            stringbuffer.append(b);
         }

         stringbuffer.append(rangeint.toString());
      }

      stringbuffer.append("]");
      return stringbuffer.toString();
   }
}
