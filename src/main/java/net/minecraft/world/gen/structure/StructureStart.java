package net.minecraft.world.gen.structure;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;

public abstract class StructureStart {
   public LinkedList<StructureComponent> components = new LinkedList<>();
   public StructureBoundingBox boundingBox;
   private int chunkPosX;
   private int chunkPosZ;

   public StructureStart() {
   }

   public StructureStart(int chunkX, int chunkZ) {
      this.chunkPosX = chunkX;
      this.chunkPosZ = chunkZ;
   }

   public StructureBoundingBox getBoundingBox() {
      return this.boundingBox;
   }

   public LinkedList<StructureComponent> getComponents() {
      return this.components;
   }

   public void generateStructure(World worldIn, Random rand, StructureBoundingBox structurebb) {
      Iterator iterator = this.components.iterator();

      while (iterator.hasNext()) {
         StructureComponent structurecomponent = (StructureComponent)iterator.next();
         if (structurecomponent.getBoundingBox().intersectsWith(structurebb) && !structurecomponent.addComponentParts(worldIn, rand, structurebb)) {
            iterator.remove();
         }
      }
   }

   public void updateBoundingBox() {
      this.boundingBox = StructureBoundingBox.getNewBoundingBox();

      for (StructureComponent structurecomponent : this.components) {
         this.boundingBox.expandTo(structurecomponent.getBoundingBox());
      }
   }

   public NBTTagCompound writeStructureComponentsToNBT(int chunkX, int chunkZ) {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      nbttagcompound.setString("id", MapGenStructureIO.getStructureStartName(this));
      nbttagcompound.setInteger("ChunkX", chunkX);
      nbttagcompound.setInteger("ChunkZ", chunkZ);
      Object object = null;
      nbttagcompound.setTag("BB", this.boundingBox.toNBTTagIntArray());
      NBTTagList nbttaglist = new NBTTagList();

      for (StructureComponent structurecomponent : this.components) {
         nbttaglist.appendTag(structurecomponent.createStructureBaseNBT());
      }

      nbttagcompound.setTag("Children", nbttaglist);
      this.writeToNBT(nbttagcompound);
      return nbttagcompound;
   }

   public void writeToNBT(NBTTagCompound tagCompound) {
   }

   public void readStructureComponentsFromNBT(World worldIn, NBTTagCompound tagCompound) {
      this.chunkPosX = tagCompound.getInteger("ChunkX");
      this.chunkPosZ = tagCompound.getInteger("ChunkZ");
      if (tagCompound.hasKey("BB")) {
         this.boundingBox = new StructureBoundingBox(tagCompound.getIntArray("BB"));
      }

      NBTTagList nbttaglist = tagCompound.getTagList("Children", 10);

      for (int i = 0; i < nbttaglist.tagCount(); i++) {
         this.components.add(MapGenStructureIO.getStructureComponent(nbttaglist.getCompoundTagAt(i), worldIn));
      }

      this.readFromNBT(tagCompound);
   }

   public void readFromNBT(NBTTagCompound tagCompound) {
   }

   public void markAvailableHeight(World worldIn, Random rand, int i) {
      int i1 = worldIn.getSeaLevel() - i;
      int j = this.boundingBox.getYSize() + 1;
      if (j < i1) {
         j += rand.nextInt(i1 - j);
      }

      int k = j - this.boundingBox.maxY;
      this.boundingBox.offset(0, k, 0);

      for (StructureComponent structurecomponent : this.components) {
         structurecomponent.func_181138_a(0, k, 0);
      }
   }

   public void setRandomHeight(World worldIn, Random rand, int i, int j) {
      int i1 = j - i + 1 - this.boundingBox.getYSize();
      int j1;
      if (i1 > 1) {
         j1 = i + rand.nextInt(i1);
      } else {
         j1 = i;
      }

      int k = j1 - this.boundingBox.minY;
      this.boundingBox.offset(0, k, 0);

      for (StructureComponent structurecomponent : this.components) {
         structurecomponent.func_181138_a(0, k, 0);
      }
   }

   public boolean isSizeableStructure() {
      return true;
   }

   public boolean func_175788_a(ChunkCoordIntPair pair) {
      return true;
   }

   public void func_175787_b(ChunkCoordIntPair pair) {
   }

   public int getChunkPosX() {
      return this.chunkPosX;
   }

   public int getChunkPosZ() {
      return this.chunkPosZ;
   }
}
