package net.minecraft.world.chunk.storage;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.NibbleArray;

public class ChunkLoader {
   public static ChunkLoader.AnvilConverterData load(NBTTagCompound nbt) {
      int i = nbt.getInteger("xPos");
      int j = nbt.getInteger("zPos");
      ChunkLoader.AnvilConverterData chunkloader$anvilconverterdata = new ChunkLoader.AnvilConverterData(i, j);
      chunkloader$anvilconverterdata.blocks = nbt.getByteArray("Blocks");
      chunkloader$anvilconverterdata.data = new NibbleArrayReader(nbt.getByteArray("Data"), 7);
      chunkloader$anvilconverterdata.skyLight = new NibbleArrayReader(nbt.getByteArray("SkyLight"), 7);
      chunkloader$anvilconverterdata.blockLight = new NibbleArrayReader(nbt.getByteArray("BlockLight"), 7);
      chunkloader$anvilconverterdata.heightmap = nbt.getByteArray("HeightMap");
      chunkloader$anvilconverterdata.terrainPopulated = nbt.getBoolean("TerrainPopulated");
      chunkloader$anvilconverterdata.entities = nbt.getTagList("Entities", 10);
      chunkloader$anvilconverterdata.tileEntities = nbt.getTagList("TileEntities", 10);
      chunkloader$anvilconverterdata.tileTicks = nbt.getTagList("TileTicks", 10);

      try {
         chunkloader$anvilconverterdata.lastUpdated = nbt.getLong("LastUpdate");
      } catch (ClassCastException classcastexception) {
         chunkloader$anvilconverterdata.lastUpdated = (long)nbt.getInteger("LastUpdate");
      }

      return chunkloader$anvilconverterdata;
   }

   public static void convertToAnvilFormat(ChunkLoader.AnvilConverterData anvilConverterData, NBTTagCompound compound, WorldChunkManager worldChunkManager) {
      compound.setInteger("xPos", anvilConverterData.x);
      compound.setInteger("zPos", anvilConverterData.z);
      compound.setLong("LastUpdate", anvilConverterData.lastUpdated);
      int[] aint = new int[anvilConverterData.heightmap.length];

      for (int i = 0; i < anvilConverterData.heightmap.length; i++) {
         aint[i] = anvilConverterData.heightmap[i];
      }

      compound.setIntArray("HeightMap", aint);
      compound.setBoolean("TerrainPopulated", anvilConverterData.terrainPopulated);
      NBTTagList nbttaglist1 = new NBTTagList();

      for (int j = 0; j < 8; j++) {
         boolean flag = true;

         for (int k = 0; k < 16 && flag; k++) {
            for (int l = 0; l < 16 && flag; l++) {
               for (int i1 = 0; i1 < 16; i1++) {
                  int j1 = k << 11 | i1 << 7 | l + (j << 4);
                  byte b0 = anvilConverterData.blocks[j1];
                  if (b0 != 0) {
                     flag = false;
                     break;
                  }
               }
            }
         }

         if (!flag) {
            byte[] abyte1 = new byte[4096];
            NibbleArray nibblearray = new NibbleArray();
            NibbleArray nibblearray1 = new NibbleArray();
            NibbleArray nibblearray2 = new NibbleArray();

            for (int l2 = 0; l2 < 16; l2++) {
               for (int k1 = 0; k1 < 16; k1++) {
                  for (int l1 = 0; l1 < 16; l1++) {
                     int i2 = l2 << 11 | l1 << 7 | k1 + (j << 4);
                     byte b1 = anvilConverterData.blocks[i2];
                     abyte1[k1 << 8 | l1 << 4 | l2] = (byte)(b1 & 255);
                     nibblearray.set(l2, k1, l1, anvilConverterData.data.get(l2, k1 + (j << 4), l1));
                     nibblearray1.set(l2, k1, l1, anvilConverterData.skyLight.get(l2, k1 + (j << 4), l1));
                     nibblearray2.set(l2, k1, l1, anvilConverterData.blockLight.get(l2, k1 + (j << 4), l1));
                  }
               }
            }

            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setByte("Y", (byte)(j & 0xFF));
            nbttagcompound.setByteArray("Blocks", abyte1);
            nbttagcompound.setByteArray("Data", nibblearray.getData());
            nbttagcompound.setByteArray("SkyLight", nibblearray1.getData());
            nbttagcompound.setByteArray("BlockLight", nibblearray2.getData());
            nbttaglist1.appendTag(nbttagcompound);
         }
      }

      compound.setTag("Sections", nbttaglist1);
      byte[] abyte = new byte[256];
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for (int j2 = 0; j2 < 16; j2++) {
         for (int k2 = 0; k2 < 16; k2++) {
            blockpos$mutableblockpos.set(anvilConverterData.x << 4 | j2, 0, anvilConverterData.z << 4 | k2);
            abyte[k2 << 4 | j2] = (byte)(worldChunkManager.getBiomeGenerator(blockpos$mutableblockpos, BiomeGenBase.field_180279_ad).biomeID & 0xFF);
         }
      }

      compound.setByteArray("Biomes", abyte);
      compound.setTag("Entities", anvilConverterData.entities);
      NBTTagList nbttaglist = anvilConverterData.tileEntities;
      compound.setTag("TileEntities", nbttaglist);
      if (anvilConverterData.tileTicks != null) {
         compound.setTag("TileTicks", anvilConverterData.tileTicks);
      }
   }

   public static class AnvilConverterData {
      public long lastUpdated;
      public boolean terrainPopulated;
      public byte[] heightmap;
      public NibbleArrayReader blockLight;
      public NibbleArrayReader skyLight;
      public NibbleArrayReader data;
      public byte[] blocks;
      public NBTTagList entities;
      public NBTTagList tileEntities;
      public NBTTagList tileTicks;
      public final int x;
      public final int z;

      public AnvilConverterData(int xIn, int zIn) {
         this.x = xIn;
         this.z = zIn;
      }
   }
}
