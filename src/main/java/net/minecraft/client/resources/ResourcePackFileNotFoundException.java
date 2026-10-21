package net.minecraft.client.resources;

import java.io.File;
import java.io.FileNotFoundException;

public class ResourcePackFileNotFoundException extends FileNotFoundException {
   private static final String b = "'%s' in ResourcePack '%s'";

   public ResourcePackFileNotFoundException(File resourcePack, String string) {
      super(String.format(b, string, resourcePack));
   }
}
