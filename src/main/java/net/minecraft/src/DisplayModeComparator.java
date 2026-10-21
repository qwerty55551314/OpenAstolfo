package net.minecraft.src;

import java.util.Comparator;
import org.lwjgl.opengl.DisplayMode;

public class DisplayModeComparator implements Comparator {
   @Override
   public int compare(Object o1, Object o2) {
      DisplayMode displaymode = (DisplayMode)o1;
      DisplayMode displaymode1 = (DisplayMode)o2;
      int i2 = displaymode.getWidth();
      int j = displaymode1.getWidth();
      int i = i2;
      if (i != j) {
         i2 = displaymode.getWidth() - displaymode1.getWidth();
      } else {
         i2 = displaymode.getHeight();
         int l = displaymode1.getHeight();
         int k = i2;
         if (k != l) {
            i2 = displaymode.getHeight() - displaymode1.getHeight();
         } else {
            i2 = displaymode.getBitsPerPixel();
            int j1 = displaymode1.getBitsPerPixel();
            int i1 = i2;
            if (i1 != j1) {
               i2 = displaymode.getBitsPerPixel() - displaymode1.getBitsPerPixel();
            } else {
               i2 = displaymode.getFrequency();
               int l1 = displaymode1.getFrequency();
               int k1 = i2;
               i2 = k1 != l1 ? displaymode.getFrequency() - displaymode1.getFrequency() : 0;
            }
         }
      }

      return i2;
   }
}
