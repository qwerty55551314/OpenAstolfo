package net.minecraft.world.chunk.storage;

import com.google.common.collect.Lists;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.SaveFormatComparator;
import net.minecraft.world.storage.SaveFormatOld;
import net.minecraft.world.storage.WorldInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnvilSaveConverter extends SaveFormatOld {
   private static final Logger logger = LogManager.getLogger();

   public AnvilSaveConverter(File savesDirectoryIn) {
      super(savesDirectoryIn);
   }

   @Override
   public String getName() {
      return "Anvil";
   }

   @Override
   public List<SaveFormatComparator> getSaveList() throws AnvilConverterException {
      File file2 = super.savesDirectory;
      if (file2 != null) {
         boolean flag1 = super.savesDirectory.exists();
         if (flag1 && super.savesDirectory.isDirectory()) {
            ArrayList arraylist = Lists.newArrayList();
            File[] afile = super.savesDirectory.listFiles();

            for (File file1 : afile) {
               if (file1.isDirectory()) {
                  String s = file1.getName();
                  WorldInfo worldinfo = this.getWorldInfo(s);
                  if (worldinfo != null && (worldinfo.getSaveVersion() == 19132 || worldinfo.getSaveVersion() == 19133)) {
                     boolean flag = worldinfo.getSaveVersion() != this.getSaveVersion();
                     String s1 = worldinfo.getWorldName();
                     if (StringUtils.isEmpty(s1)) {
                        s1 = s;
                     }

                     long i = 0L;
                     arraylist.add(
                        new SaveFormatComparator(
                           s,
                           s1,
                           worldinfo.getLastTimePlayed(),
                           i,
                           worldinfo.getGameType(),
                           flag,
                           worldinfo.isHardcoreModeEnabled(),
                           worldinfo.areCommandsAllowed()
                        )
                     );
                  }
               }
            }

            return arraylist;
         }
      }

      throw new AnvilConverterException("Unable to read or access folder where game worlds are saved!");
   }

   public int getSaveVersion() {
      return 19133;
   }

   @Override
   public void flushCache() {
      RegionFileCache.clearRegionFileReferences();
   }

   @Override
   public ISaveHandler getSaveLoader(String saveName, boolean storePlayerdata) {
      return new AnvilSaveHandler(super.savesDirectory, saveName, storePlayerdata);
   }

   @Override
   public boolean isConvertible(String saveName) {
      WorldInfo worldinfo = this.getWorldInfo(saveName);
      if (worldinfo != null) {
         int i = worldinfo.getSaveVersion();
         if (i == 19132) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean isOldMapFormat(String saveName) {
      WorldInfo worldinfo = this.getWorldInfo(saveName);
      if (worldinfo != null) {
         int i = worldinfo.getSaveVersion();
         if (i != this.getSaveVersion()) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean convertMapFormat(String filename, IProgressUpdate progressCallback) {
      progressCallback.setLoadingProgress(0);
      ArrayList arraylist = Lists.newArrayList();
      ArrayList arraylist1 = Lists.newArrayList();
      ArrayList arraylist2 = Lists.newArrayList();
      File file1 = new File(super.savesDirectory, filename);
      File file2 = new File(file1, "DIM-1");
      File file3 = new File(file1, "DIM1");
      logger.info("Scanning folders...");
      this.addRegionFilesToCollection(file1, arraylist);
      boolean flag = file2.exists();
      if (flag) {
         this.addRegionFilesToCollection(file2, arraylist1);
      }

      boolean flag1 = file3.exists();
      if (flag1) {
         this.addRegionFilesToCollection(file3, arraylist2);
      }

      int i = arraylist.size() + arraylist1.size() + arraylist2.size();
      logger.info("Total conversion count is " + i);
      WorldInfo worldinfo = this.getWorldInfo(filename);
      Object object;
      if (worldinfo.getTerrainType() == WorldType.FLAT) {
         object = new WorldChunkManagerHell(BiomeGenBase.plains, 0.5F);
      } else {
         object = new WorldChunkManager(worldinfo.getSeed(), worldinfo.getTerrainType(), worldinfo.getGeneratorOptions());
      }

      this.convertFile(new File(file1, "region"), arraylist, (WorldChunkManager)object, 0, i, progressCallback);
      this.convertFile(new File(file2, "region"), arraylist1, new WorldChunkManagerHell(BiomeGenBase.hell, 0.0F), arraylist.size(), i, progressCallback);
      this.convertFile(
         new File(file3, "region"), arraylist2, new WorldChunkManagerHell(BiomeGenBase.sky, 0.0F), arraylist.size() + arraylist1.size(), i, progressCallback
      );
      worldinfo.setSaveVersion(19133);
      if (worldinfo.getTerrainType() == WorldType.DEFAULT_1_1) {
         worldinfo.setTerrainType(WorldType.DEFAULT);
      }

      this.createFile(filename);
      ISaveHandler isavehandler = this.getSaveLoader(filename, false);
      isavehandler.saveWorldInfo(worldinfo);
      return true;
   }

   private void createFile(String filename) {
      String s = "CJpss";
      File file1 = new File(super.savesDirectory, filename);
      if (!file1.exists()) {
         logger.warn("Unable to create level.dat_mcr backup");
      } else {
         File file2 = new File(file1, "level.dat");
         if (!file2.exists()) {
            logger.warn("Unable to create level.dat_mcr backup");
         } else {
            File file3 = new File(file1, "level.dat_mcr");
            if (!file2.renameTo(file3)) {
               logger.warn("Unable to create level.dat_mcr backup");
            }
         }
      }
   }

   private void convertFile(File file, Iterable<File> iterable, WorldChunkManager worldChunkManager, int i, int j, IProgressUpdate iProgressUpdate) {
      for (File file1 : iterable) {
         this.convertChunks(file, file1, worldChunkManager, i, j, iProgressUpdate);
         i++;
         int i1 = (int)Math.round(100.0 * (double)i / (double)j);
         iProgressUpdate.setLoadingProgress(i1);
      }
   }

   private void convertChunks(File file, File file2, WorldChunkManager worldChunkManager, int i, int j, IProgressUpdate progressCallback) {
      try {
         String s = file2.getName();
         RegionFile regionfile = new RegionFile(file2);
         RegionFile regionfile1 = new RegionFile(new File(file, s.substring(0, s.length() - ".mcr".length()) + ".mca"));

         for (int i1 = 0; i1 < 32; i1++) {
            for (int j1 = 0; j1 < 32; j1++) {
               if (regionfile.isChunkSaved(i1, j1) && !regionfile1.isChunkSaved(i1, j1)) {
                  DataInputStream datainputstream = regionfile.getChunkDataInputStream(i1, j1);
                  if (datainputstream == null) {
                     logger.warn("Failed to fetch input stream");
                  } else {
                     NBTTagCompound nbttagcompound = CompressedStreamTools.read(datainputstream);
                     datainputstream.close();
                     NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("Level");
                     ChunkLoader.AnvilConverterData chunkloader$anvilconverterdata = ChunkLoader.load(nbttagcompound1);
                     NBTTagCompound nbttagcompound2 = new NBTTagCompound();
                     NBTTagCompound nbttagcompound3 = new NBTTagCompound();
                     nbttagcompound2.setTag("Level", nbttagcompound3);
                     ChunkLoader.convertToAnvilFormat(chunkloader$anvilconverterdata, nbttagcompound3, worldChunkManager);
                     DataOutputStream dataoutputstream = regionfile1.getChunkDataOutputStream(i1, j1);
                     CompressedStreamTools.write(nbttagcompound2, dataoutputstream);
                     dataoutputstream.close();
                  }
               }
            }

            int k = (int)Math.round(100.0 * (double)(i * 1024) / (double)(j * 1024));
            int l = (int)Math.round(100.0 * (double)((i1 + 1) * 32 + i * 1024) / (double)(j * 1024));
            if (l > k) {
               progressCallback.setLoadingProgress(l);
            }
         }

         regionfile.close();
         regionfile1.close();
      } catch (IOException ioexception) {
         ioexception.printStackTrace();
      }
   }

   private void addRegionFilesToCollection(File worldDir, Collection<File> collection) {
      File file1 = new File(worldDir, "region");
      File[] afile = file1.listFiles(new FilenameFilter() {
         private static final String b = ".mcr";

         @Override
         public boolean accept(File file, String string) {
            return string.endsWith(b);
         }
      });
      if (afile != null) {
         Collections.addAll(collection, afile);
      }
   }
}
