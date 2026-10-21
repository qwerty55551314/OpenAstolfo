package net.minecraft.world.storage;

import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IProgressUpdate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SaveFormatOld implements ISaveFormat {
   private static final Logger logger = LogManager.getLogger();
   public final File savesDirectory;

   public SaveFormatOld(File savesDirectoryIn) {
      if (!savesDirectoryIn.exists()) {
         savesDirectoryIn.mkdirs();
      }

      this.savesDirectory = savesDirectoryIn;
   }

   @Override
   public String getName() {
      return "Old Format";
   }

   @Override
   public List<SaveFormatComparator> getSaveList() throws AnvilConverterException {
      ArrayList arraylist = Lists.newArrayList();

      for (int i = 0; i < 5; i++) {
         String s = "World" + (i + 1);
         WorldInfo worldinfo = this.getWorldInfo(s);
         if (worldinfo != null) {
            arraylist.add(
               new SaveFormatComparator(
                  s,
                  "",
                  worldinfo.getLastTimePlayed(),
                  worldinfo.getSizeOnDisk(),
                  worldinfo.getGameType(),
                  false,
                  worldinfo.isHardcoreModeEnabled(),
                  worldinfo.areCommandsAllowed()
               )
            );
         }
      }

      return arraylist;
   }

   @Override
   public void flushCache() {
   }

   @Override
   public WorldInfo getWorldInfo(String saveName) {
      Object object = null;
      File file1 = new File(this.savesDirectory, saveName);
      if (!file1.exists()) {
         return null;
      } else {
         File file2 = new File(file1, "level.dat");
         boolean flag = file2.exists();
         if (flag) {
            try {
               NBTTagCompound nbttagcompound2 = CompressedStreamTools.readCompressed(new FileInputStream(file2));
               NBTTagCompound nbttagcompound3 = nbttagcompound2.getCompoundTag("Data");
               return new WorldInfo(nbttagcompound3);
            } catch (Exception exception1) {
               logger.error("Exception reading " + file2, (Throwable)exception1);
            }
         }

         file2 = new File(file1, "level.dat_old");
         if (file2.exists()) {
            try {
               NBTTagCompound nbttagcompound = CompressedStreamTools.readCompressed(new FileInputStream(file2));
               NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("Data");
               return new WorldInfo(nbttagcompound1);
            } catch (Exception exception) {
               logger.error("Exception reading " + file2, (Throwable)exception);
            }
         }

         return null;
      }
   }

   @Override
   public void renameWorld(String dirName, String newName) {
      Object object = null;
      File file1 = new File(this.savesDirectory, dirName);
      if (file1.exists()) {
         File file2 = new File(file1, "level.dat");
         if (file2.exists()) {
            try {
               NBTTagCompound nbttagcompound = CompressedStreamTools.readCompressed(new FileInputStream(file2));
               NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("Data");
               nbttagcompound1.setString("LevelName", newName);
               CompressedStreamTools.writeCompressed(nbttagcompound, new FileOutputStream(file2));
            } catch (Exception exception) {
               exception.printStackTrace();
            }
         }
      }
   }

   @Override
   public boolean isNewLevelIdAcceptable(String saveName) {
      Object object = null;
      File file1 = new File(this.savesDirectory, saveName);
      if (file1.exists()) {
         return false;
      } else {
         try {
            file1.mkdir();
            file1.delete();
            return true;
         } catch (Throwable throwable) {
            logger.warn("Couldn't make new level", throwable);
            return false;
         }
      }
   }

   @Override
   public boolean deleteWorldDirectory(String saveName) {
      Object object = null;
      File file1 = new File(this.savesDirectory, saveName);
      boolean flag = file1.exists();
      if (!flag) {
         return true;
      } else {
         logger.info("Deleting level " + saveName);

         for (int i = 1; i <= 5; i++) {
            logger.info("Attempt " + i + "...");
            boolean flag1 = deleteFiles(file1.listFiles());
            if (flag1) {
               break;
            }

            logger.warn("Unsuccessful in deleting contents.");
            if (i < 5) {
               try {
                  Thread.sleep(500L);
               } catch (InterruptedException interruptedexception) {
               }
            }
         }

         return file1.delete();
      }
   }

   public static boolean deleteFiles(File[] files) {
      for (int i = 0; i < files.length; i++) {
         File file1 = files[i];
         logger.debug("Deleting " + file1);
         if (file1.isDirectory() && !deleteFiles(file1.listFiles())) {
            logger.warn("Couldn't delete directory " + file1);
            return false;
         }

         if (!file1.delete()) {
            logger.warn("Couldn't delete file " + file1);
            return false;
         }
      }

      return true;
   }

   @Override
   public ISaveHandler getSaveLoader(String saveName, boolean storePlayerdata) {
      return new SaveHandler(this.savesDirectory, saveName, storePlayerdata);
   }

   @Override
   public boolean isConvertible(String saveName) {
      return false;
   }

   @Override
   public boolean isOldMapFormat(String saveName) {
      return false;
   }

   @Override
   public boolean convertMapFormat(String filename, IProgressUpdate progressCallback) {
      return false;
   }

   @Override
   public boolean canLoadWorld(String saveName) {
      File file1 = new File(this.savesDirectory, saveName);
      return file1.isDirectory();
   }
}
