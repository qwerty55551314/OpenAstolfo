package net.minecraft.client.resources.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.StringUtils;

public class ModelResourceLocation extends ResourceLocation {
   private final String variant;
   private static Entity[] T;

   public ModelResourceLocation(int i, String... strings) {
      super(0, strings[0], strings[1]);
      this.variant = StringUtils.isEmpty(strings[2]) ? "normal" : strings[2].toLowerCase();
   }

   public ModelResourceLocation(String string) {
      this(0, parsePathString(string));
   }

   public ModelResourceLocation(ResourceLocation resourceLocation, String string) {
      this(resourceLocation.toString(), string);
   }

   public ModelResourceLocation(String string, String string2) {
      this(0, parsePathString(string + '#' + (string2 == null ? "normal" : string2)));
   }

   public static String[] parsePathString(String string) {
      String[] astring = new String[]{null, string, null};
      int i = string.indexOf(35);
      String s = string;
      if (i >= 0) {
         astring[2] = string.substring(i + 1, string.length());
         if (i > 1) {
            s = string.substring(0, i);
         }
      }

      System.arraycopy(ResourceLocation.splitObjectName(s), 0, astring, 0, 2);
      return astring;
   }

   public String getVariant() {
      return this.variant;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else {
         boolean flag = object instanceof ModelResourceLocation;
         if (flag && super.equals(object)) {
            ModelResourceLocation modelresourcelocation = (ModelResourceLocation)object;
            return this.variant.equals(modelresourcelocation.variant);
         } else {
            return false;
         }
      }
   }

   @Override
   public int hashCode() {
      return 31 * super.hashCode() + this.variant.hashCode();
   }

   @Override
   public String toString() {
      return super.toString() + '#' + this.variant;
   }

   public static void j(Entity[] entityArray) {
      T = entityArray;
   }

   public static Entity[] f() {
      return T;
   }

   static {
      j((Entity[])null);
   }
}
