package net.minecraft.src;

import java.util.ArrayList;

public class CompactArrayList {
   private ArrayList list = null;
   private int initialCapacity = 0;
   private float loadFactor = 1.0F;
   private int countValid = 0;
   private static final long b = -7190864315476344822L;

   public CompactArrayList() {
      this((int)b, 0.75F);
   }

   public CompactArrayList(int initialCapacity) {
      this(initialCapacity, 0.75F);
   }

   public CompactArrayList(int initialCapacity, float loadFactor) {
      this.list = new ArrayList(initialCapacity);
      this.initialCapacity = initialCapacity;
      this.loadFactor = loadFactor;
   }

   public void insert(int i, Object object) {
      if (object != null) {
         this.countValid++;
      }

      this.list.add(i, object);
   }

   public boolean add(Object element) {
      if (element != null) {
         this.countValid++;
      }

      return this.list.add(element);
   }

   public Object set(int index, Object element) {
      Object object = this.list.set(index, element);
      if (element != object) {
         if (object == null) {
            this.countValid++;
         }

         if (element == null) {
            this.countValid--;
         }
      }

      return object;
   }

   public Object remove(int index) {
      Object object = this.list.remove(index);
      if (object != null) {
         this.countValid--;
      }

      return object;
   }

   public void clear() {
      this.list.clear();
      this.countValid = 0;
   }

   public void v_void() {
      int k = this.countValid;
      if (k <= 0 && this.list.size() <= 0) {
         this.clear();
      } else {
         int l = this.list.size();
         if (l > this.initialCapacity) {
            float f = (float)this.countValid * 1.0F / (float)this.list.size();
            float f1;
            int i1 = (f1 = f - this.loadFactor) == 0.0F ? 0 : (f1 < 0.0F ? -1 : 1);
            if (i1 <= 0) {
               int i = 0;

               for (int j = 0; j < this.list.size(); j++) {
                  Object object = this.list.get(j);
                  if (object != null) {
                     if (j != i) {
                        this.list.set(i, object);
                     }

                     i++;
                  }
               }

               for (int j1 = this.list.size() - 1; j1 >= i; j1--) {
                  this.list.remove(j1);
               }
            }
         }
      }
   }

   public boolean contains(Object elem) {
      return this.list.contains(elem);
   }

   public Object get(int index) {
      return this.list.get(index);
   }

   public boolean isEmpty() {
      return this.list.isEmpty();
   }

   public int size() {
      return this.list.size();
   }

   public int getCountValid() {
      return this.countValid;
   }
}
