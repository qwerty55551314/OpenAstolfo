package net.minecraft.client.resources;

import com.google.common.collect.Sets;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.io.filefilter.DirectoryFileFilter;

public class FolderResourcePack extends AbstractResourcePack {
   private static final String f = "assets/";

   public FolderResourcePack(File resourcePackFileIn) {
      super(resourcePackFileIn);
   }

   @Override
   public InputStream getInputStreamByName(String name) throws IOException {
      return new BufferedInputStream(new FileInputStream(new File(super.resourcePackFile, name)));
   }

   @Override
   public boolean hasResourceName(String name) {
      return new File(super.resourcePackFile, name).isFile();
   }

   @Override
   public Set<String> getResourceDomains() {
      HashSet hashset = Sets.newHashSet();
      Object object = null;
      File file1 = new File(super.resourcePackFile, f);
      if (file1.isDirectory()) {
         for (File file2 : file1.listFiles((FileFilter)DirectoryFileFilter.DIRECTORY)) {
            String s = getRelativeName(file1, file2);
            boolean flag = s.equals(s.toLowerCase());
            if (!flag) {
               this.logNameNotLowercase(s);
            } else {
               hashset.add(s.substring(0, s.length() - 1));
            }
         }
      }

      return hashset;
   }
}
