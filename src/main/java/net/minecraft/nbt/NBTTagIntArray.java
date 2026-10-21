package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagIntArray extends NBTBase {
   private int[] intArray;

   public NBTTagIntArray() {
   }

   public NBTTagIntArray(int[] ints) {
      this.intArray = ints;
   }

   @Override
   public void write(DataOutput output) throws IOException {
      output.writeInt(this.intArray.length);

      for (int i = 0; i < this.intArray.length; i++) {
         output.writeInt(this.intArray[i]);
      }
   }

   @Override
   public void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
      sizeTracker.read(192L);
      int i = input.readInt();
      sizeTracker.read((long)(32 * i));
      this.intArray = new int[i];

      for (int j = 0; j < i; j++) {
         this.intArray[j] = input.readInt();
      }
   }

   @Override
   public byte getId() {
      return 11;
   }

   @Override
   public String toString() {
      String s = "[";

      for (int i : this.intArray) {
         s = s + i + ",";
      }

      return s + "]";
   }

   @Override
   public NBTBase copy() {
      int[] aint = new int[this.intArray.length];
      System.arraycopy(this.intArray, 0, aint, 0, this.intArray.length);
      return new NBTTagIntArray(aint);
   }

   @Override
   public boolean equals(Object object) {
      return super.equals(object) ? Arrays.equals(this.intArray, ((NBTTagIntArray)object).intArray) : false;
   }

   @Override
   public int hashCode() {
      return super.hashCode() ^ Arrays.hashCode(this.intArray);
   }

   public int[] getIntArray() {
      return this.intArray;
   }
}
