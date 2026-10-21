package net.minecraft.util;

import org.apache.commons.lang3.Validate;

public class ResourceLocation {
   public final String resourceDomain;
   public final String resourcePath;
   private static int M;
   private static final String c = "minecraft";

   public ResourceLocation(int i, String... resourceName) {
      this.resourceDomain = org.apache.commons.lang3.StringUtils.isEmpty(resourceName[0]) ? c : resourceName[0].toLowerCase();
      this.resourcePath = resourceName[1];
      Validate.notNull(this.resourcePath);
   }

   public ResourceLocation(String resourceName) {
      this(0, splitObjectName(resourceName));
   }

   public ResourceLocation(String resourceDomainIn, String resourcePathIn) {
      this(0, resourceDomainIn, resourcePathIn);
   }

   public static String[] splitObjectName(String toSplit) {
      String[] astring = new String[]{null, toSplit};
      int i = toSplit.indexOf(58);
      if (i >= 0) {
         astring[1] = toSplit.substring(i + 1, toSplit.length());
         if (i > 1) {
            astring[0] = toSplit.substring(0, i);
         }
      }

      return astring;
   }

   public String getResourcePath() {
      return this.resourcePath;
   }

   public String getResourceDomain() {
      return this.resourceDomain;
   }

   @Override
   public String toString() {
      return this.resourceDomain + ':' + this.resourcePath;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (!(object instanceof ResourceLocation)) {
         return false;
      } else {
         ResourceLocation resourcelocation = (ResourceLocation)object;
         boolean flag = this.resourceDomain.equals(resourcelocation.resourceDomain);
         if (flag) {
            boolean flag1 = this.resourcePath.equals(resourcelocation.resourcePath);
            if (flag1) {
               return true;
            }
         }

         return false;
      }
   }

   @Override
   public int hashCode() {
      return 31 * this.resourceDomain.hashCode() + this.resourcePath.hashCode();
   }

   public static int H() {
      return 0;
   }
}
