package net.minecraft.block.properties;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import net.minecraft.util.IStringSerializable;

public class PropertyEnum<T extends Enum<T> & IStringSerializable> extends PropertyHelper<T> {
   private final ImmutableSet<T> allowedValues;
   private final Map<String, T> nameToValue = Maps.newHashMap();
   private static final String g = "Multiple values have the same name '";

   public PropertyEnum(String name, Class<T> valueClass, Collection<T> allowedValues) {
      super(name, valueClass);
      this.allowedValues = ImmutableSet.copyOf(allowedValues);

      for (Enum oenum : allowedValues) {
         String s = ((IStringSerializable)oenum).getName();
         if (this.nameToValue.containsKey(s)) {
            throw new IllegalArgumentException(g + s + "'");
         }

         this.nameToValue.put(s, (T)oenum);
      }
   }

   @Override
   public Collection<T> getAllowedValues() {
      return this.allowedValues;
   }

   public String getName(T value) {
      return ((IStringSerializable)value).getName();
   }

   public static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> create(String name, Class<T> clazz) {
      return create(name, clazz, Predicates.alwaysTrue());
   }

   public static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> create(String name, Class<T> clazz, Predicate<T> filter) {
      return create(name, clazz, Collections2.filter(Lists.newArrayList((T[])clazz.getEnumConstants()), filter));
   }

   public static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> create(String name, Class<T> clazz, T... values) {
      return create(name, clazz, Lists.newArrayList((T[])values));
   }

   public static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> create(String name, Class<T> clazz, Collection<T> values) {
      return new PropertyEnum<>(name, clazz, values);
   }
}
