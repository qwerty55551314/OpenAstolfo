package net.minecraft.block.properties;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;

public class PropertyInteger extends PropertyHelper<Integer> {
   private final ImmutableSet<Integer> allowedValues;

   public PropertyInteger(String name, int minIn, int maxIn) {
      super(name, Integer.class);
      if (minIn < 0) {
         throw new IllegalArgumentException("Min value of " + name + " must be 0 or greater");
      } else if (maxIn <= minIn) {
         throw new IllegalArgumentException("Max value of " + name + " must be greater than min (" + minIn + ")");
      } else {
         HashSet hashset = Sets.newHashSet();

         for (int i = minIn; i <= maxIn; i++) {
            hashset.add(i);
         }

         this.allowedValues = ImmutableSet.copyOf(hashset);
      }
   }

   @Override
   public Collection<Integer> getAllowedValues() {
      return this.allowedValues;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (object == null || this.getClass() != object.getClass()) {
         return false;
      } else if (!super.equals(object)) {
         return false;
      } else {
         PropertyInteger propertyinteger = (PropertyInteger)object;
         return this.allowedValues.equals(propertyinteger.allowedValues);
      }
   }

   @Override
   public int hashCode() {
      int i = super.hashCode();
      return 31 * i + this.allowedValues.hashCode();
   }

   public static PropertyInteger create(String name, int minIn, int maxIn) {
      return new PropertyInteger(name, minIn, maxIn);
   }

   public String getName(Integer value) {
      return value.toString();
   }
}
