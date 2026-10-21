package net.minecraft.world;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;

public class NextTickListEntry implements Comparable<NextTickListEntry> {
   private static long nextTickEntryID;
   private final Block block;
   public final BlockPos position;
   public long scheduledTime;
   public int priority;
   private long tickEntryID;

   public NextTickListEntry(BlockPos positionIn, Block blockIn) {
      this.tickEntryID = nextTickEntryID++;
      this.position = positionIn;
      this.block = blockIn;
   }

   @Override
   public boolean equals(Object object) {
      if (!(object instanceof NextTickListEntry)) {
         return false;
      } else {
         NextTickListEntry nextticklistentry = (NextTickListEntry)object;
         boolean flag = this.position.equals(nextticklistentry.position);
         if (flag) {
            boolean flag1 = Block.isEqualTo(this.block, nextticklistentry.block);
            if (flag1) {
               return true;
            }
         }

         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.position.hashCode();
   }

   public NextTickListEntry setScheduledTime(long scheduledTimeIn) {
      this.scheduledTime = scheduledTimeIn;
      return this;
   }

   public void setPriority(int priorityIn) {
      this.priority = priorityIn;
   }

   public int compareTo(NextTickListEntry nextTickListEntry) {
      long j1;
      int i = (j1 = this.scheduledTime - nextTickListEntry.scheduledTime) == 0L ? 0 : (j1 < 0L ? -1 : 1);
      int j2;
      if (i < 0) {
         j2 = -1;
      } else {
         long k1;
         int j = (k1 = this.scheduledTime - nextTickListEntry.scheduledTime) == 0L ? 0 : (k1 < 0L ? -1 : 1);
         if (j > 0) {
            j2 = 1;
         } else {
            int k = this.priority;
            if (k != nextTickListEntry.priority) {
               j2 = this.priority - nextTickListEntry.priority;
            } else {
               long l1;
               int l = (l1 = this.tickEntryID - nextTickListEntry.tickEntryID) == 0L ? 0 : (l1 < 0L ? -1 : 1);
               if (l < 0) {
                  j2 = -1;
               } else {
                  long i2;
                  int i1 = (i2 = this.tickEntryID - nextTickListEntry.tickEntryID) == 0L ? 0 : (i2 < 0L ? -1 : 1);
                  j2 = i1 > 0 ? 1 : 0;
               }
            }
         }
      }

      return j2;
   }

   @Override
   public String toString() {
      return Block.getIdFromBlock(this.block) + ": " + this.position + ", " + this.scheduledTime + ", " + this.priority + ", " + this.tickEntryID;
   }

   public Block getBlock() {
      return this.block;
   }
}
