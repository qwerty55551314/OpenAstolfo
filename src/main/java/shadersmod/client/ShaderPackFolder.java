package shadersmod.client;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import net.minecraft.src.StrUtils;

public class ShaderPackFolder implements IShaderPack {
   public File packFile;

   public ShaderPackFolder(String name, File file) {
      this.packFile = file;
   }

   @Override
   public void close() {
   }

   @Override
   public InputStream getResourceAsStream(String resName) {
      try {
         String s = StrUtils.removePrefixSuffix(resName, "/", "/");
         File file1 = new File(this.packFile, s);
         return !file1.exists() ? null : new BufferedInputStream(new FileInputStream(file1));
      } catch (Exception exception) {
         return null;
      }
   }

   @Override
   public boolean hasDirectory(String name) {
      boolean flag = true;
      File file1 = new File(this.packFile, name.substring(1));
      return !file1.exists() ? false : file1.isDirectory();
   }

   @Override
   public String getName() {
      return this.packFile.getName();
   }
}
