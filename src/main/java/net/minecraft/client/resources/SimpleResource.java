package net.minecraft.client.resources;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;

public class SimpleResource implements IResource {
   private final Map<String, IMetadataSection> mapMetadataSections = Maps.newHashMap();
   private final String resourcePackName;
   private final ResourceLocation srResourceLocation;
   private final InputStream resourceInputStream;
   private final InputStream mcmetaInputStream;
   private final IMetadataSerializer srMetadataSerializer;
   private boolean mcmetaJsonChecked;
   private JsonObject mcmetaJson;
   private static final long b = 4182245716107722783L;

   public SimpleResource(
      String resourcePackNameIn,
      ResourceLocation srResourceLocationIn,
      InputStream resourceInputStreamIn,
      InputStream mcmetaInputStreamIn,
      IMetadataSerializer srMetadataSerializerIn
   ) {
      this.resourcePackName = resourcePackNameIn;
      this.srResourceLocation = srResourceLocationIn;
      this.resourceInputStream = resourceInputStreamIn;
      this.mcmetaInputStream = mcmetaInputStreamIn;
      this.srMetadataSerializer = srMetadataSerializerIn;
   }

   @Override
   public ResourceLocation getResourceLocation() {
      return this.srResourceLocation;
   }

   @Override
   public InputStream getInputStream() {
      return this.resourceInputStream;
   }

   @Override
   public boolean hasMetadata() {
      return this.mcmetaInputStream != null;
   }

   @Override
   public <T extends IMetadataSection> T getMetadata(String string) {
      if (!this.hasMetadata()) {
         return null;
      } else {
         JsonObject jsonobject = this.mcmetaJson;
         if (jsonobject == null && !this.mcmetaJsonChecked) {
            this.mcmetaJsonChecked = true;
            BufferedReader bufferedreader = null;

            try {
               bufferedreader = new BufferedReader(new InputStreamReader(this.mcmetaInputStream));
               this.mcmetaJson = new JsonParser().parse(bufferedreader).getAsJsonObject();
            } finally {
               IOUtils.closeQuietly((Reader)bufferedreader);
            }
         }

         IMetadataSection imetadatasection = this.mapMetadataSections.get(string);
         if (imetadatasection == null) {
            imetadatasection = this.srMetadataSerializer.parseMetadataSection(string, this.mcmetaJson);
         }

         return (T)imetadatasection;
      }
   }

   @Override
   public String getResourcePackName() {
      return this.resourcePackName;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (!(object instanceof SimpleResource)) {
         return false;
      } else {
         SimpleResource simpleresource = (SimpleResource)object;
         ResourceLocation resourcelocation = this.srResourceLocation;
         if (resourcelocation != null) {
            if (!this.srResourceLocation.equals(simpleresource.srResourceLocation)) {
               return false;
            }
         } else if (simpleresource.srResourceLocation != null) {
            return false;
         }

         String s = this.resourcePackName;
         if (s != null) {
            if (!this.resourcePackName.equals(simpleresource.resourcePackName)) {
               return false;
            }
         } else if (simpleresource.resourcePackName != null) {
            return false;
         }

         return true;
      }
   }

   @Override
   public int hashCode() {
      String s = this.resourcePackName;
      int i = s != null ? this.resourcePackName.hashCode() : 0;
      int k = (int)b * i;
      ResourceLocation resourcelocation = this.srResourceLocation;
      int j = k;
      return j + (resourcelocation != null ? this.srResourceLocation.hashCode() : 0);
   }
}
