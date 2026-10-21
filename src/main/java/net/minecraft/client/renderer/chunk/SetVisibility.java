package net.minecraft.client.renderer.chunk;

import java.util.BitSet;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.util.EnumFacing;

public class SetVisibility {
   private static final int COUNT_FACES = EnumFacing.values().length;
   private final BitSet bitSet = new BitSet(COUNT_FACES * COUNT_FACES);
   private static final String b = "  ";

   public void setManyVisible(Set<EnumFacing> set) {
      Iterator iterator = set.iterator();
      if ("PFxko" != null) {
         while (iterator.hasNext()) {
            EnumFacing enumfacing2 = (EnumFacing)iterator.next();

            for (EnumFacing enumfacing3 : set) {
               this.setVisible(enumfacing2, enumfacing3, true);
            }
         }
      } else {
         while (iterator.hasNext()) {
            EnumFacing enumfacing = (EnumFacing)iterator.next();
            Iterator iterator1 = set.iterator();
            if (!iterator1.hasNext()) {
               break;
            }

            EnumFacing enumfacing1 = (EnumFacing)iterator1.next();
            this.setVisible(enumfacing, enumfacing1, true);
         }
      }
   }

   public void setVisible(EnumFacing facing, EnumFacing facing2, boolean bl) {
      this.bitSet.set(facing.ordinal() + facing2.ordinal() * COUNT_FACES, bl);
      this.bitSet.set(facing2.ordinal() + facing.ordinal() * COUNT_FACES, bl);
   }

   public void setAllVisible(boolean visible) {
      this.bitSet.set(0, this.bitSet.size(), visible);
   }

   public boolean isVisible(EnumFacing facing, EnumFacing facing2) {
      return this.bitSet.get(facing.ordinal() + facing2.ordinal() * COUNT_FACES);
   }

   @Override
   public String toString() {
      StringBuilder stringbuilder = new StringBuilder();
      stringbuilder.append(' ');

      for (EnumFacing enumfacing : EnumFacing.values()) {
         stringbuilder.append(' ').append(enumfacing.toString().toUpperCase().charAt(0));
      }

      stringbuilder.append('\n');

      for (EnumFacing enumfacing2 : EnumFacing.values()) {
         stringbuilder.append(enumfacing2.toString().toUpperCase().charAt(0));

         for (EnumFacing enumfacing1 : EnumFacing.values()) {
            if (enumfacing2 == enumfacing1) {
               stringbuilder.append(b);
            } else {
               boolean flag = this.isVisible(enumfacing2, enumfacing1);
               stringbuilder.append(' ').append((char)(flag ? 'Y' : 'n'));
            }
         }

         stringbuilder.append('\n');
      }

      return stringbuilder.toString();
   }
}
