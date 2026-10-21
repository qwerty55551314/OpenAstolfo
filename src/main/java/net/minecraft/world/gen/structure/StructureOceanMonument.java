package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class StructureOceanMonument extends MapGenStructure {
   private int field_175800_f = 32;
   private int field_175801_g = 5;
   public static final List<BiomeGenBase> field_175802_d = Arrays.asList(
      BiomeGenBase.ocean, BiomeGenBase.deepOcean, BiomeGenBase.river, BiomeGenBase.frozenOcean, BiomeGenBase.frozenRiver
   );
   private static final List<BiomeGenBase.SpawnListEntry> field_175803_h = Lists.newArrayList();

   public StructureOceanMonument() {
   }

   public StructureOceanMonument(Map<String, String> map) {
      this();

      for (Entry entry : map.entrySet()) {
         if (((String)entry.getKey()).equals("spacing")) {
            this.field_175800_f = MathHelper.parseIntWithDefaultAndMax((String)entry.getValue(), this.field_175800_f, 1);
         } else if (((String)entry.getKey()).equals("separation")) {
            this.field_175801_g = MathHelper.parseIntWithDefaultAndMax((String)entry.getValue(), this.field_175801_g, 1);
         }
      }
   }

   @Override
   public String getStructureName() {
      return "Monument";
   }

   @Override
   public boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
      int i = chunkX;
      int j = chunkZ;
      if (chunkX < 0) {
         chunkX -= this.field_175800_f - 1;
      }

      if (chunkZ < 0) {
         chunkZ -= this.field_175800_f - 1;
      }

      int k = chunkX / this.field_175800_f;
      int l = chunkZ / this.field_175800_f;
      Random random = super.worldObj.setRandomSeed(k, l, 10387313);
      k *= this.field_175800_f;
      l *= this.field_175800_f;
      k += (random.nextInt(this.field_175800_f - this.field_175801_g) + random.nextInt(this.field_175800_f - this.field_175801_g)) / 2;
      l += (random.nextInt(this.field_175800_f - this.field_175801_g) + random.nextInt(this.field_175800_f - this.field_175801_g)) / 2;
      if (i == k && j == l) {
         if (super.worldObj.getWorldChunkManager().getBiomeGenerator(new BlockPos(i * 16 + 8, 64, j * 16 + 8), (BiomeGenBase)null) != BiomeGenBase.deepOcean) {
            return false;
         }

         boolean flag = super.worldObj.getWorldChunkManager().areBiomesViable(i * 16 + 8, j * 16 + 8, 29, field_175802_d);
         if (flag) {
            return true;
         }
      }

      return false;
   }

   @Override
   public StructureStart getStructureStart(int chunkX, int chunkZ) {
      return new StructureOceanMonument.StartMonument(super.worldObj, super.rand, chunkX, chunkZ);
   }

   public List<BiomeGenBase.SpawnListEntry> getScatteredFeatureSpawnList() {
      return field_175803_h;
   }

   static {
      field_175803_h.add(new BiomeGenBase.SpawnListEntry(EntityGuardian.class, 1, 2, 4));
   }

   public static class StartMonument extends StructureStart {
      private Set<ChunkCoordIntPair> field_175791_c = Sets.newHashSet();
      private boolean field_175790_d;

      public StartMonument() {
      }

      public StartMonument(World worldIn, Random random2, int i, int j) {
         super(i, j);
         this.func_175789_b(worldIn, random2, i, j);
      }

      private void func_175789_b(World worldIn, Random random2, int i, int j) {
         random2.setSeed(worldIn.getSeed());
         long i1 = random2.nextLong();
         long j1 = random2.nextLong();
         long k = (long)i * i1;
         long l = (long)j * j1;
         random2.setSeed(k ^ l ^ worldIn.getSeed());
         int i1x = i * 16 + 8 - 29;
         int j1x = j * 16 + 8 - 29;
         EnumFacing enumfacing = EnumFacing.Plane.HORIZONTAL.random(random2);
         super.components.add(new StructureOceanMonumentPieces.MonumentBuilding(random2, i1x, j1x, enumfacing));
         this.updateBoundingBox();
         this.field_175790_d = true;
      }

      @Override
      public void generateStructure(World worldIn, Random randIn, StructureBoundingBox structurebb) {
         if (!this.field_175790_d) {
            super.components.clear();
            this.func_175789_b(worldIn, randIn, this.getChunkPosX(), this.getChunkPosZ());
         }

         super.generateStructure(worldIn, randIn, structurebb);
      }

      @Override
      public boolean func_175788_a(ChunkCoordIntPair pair) {
         return this.field_175791_c.contains(pair) ? false : super.func_175788_a(pair);
      }

      @Override
      public void func_175787_b(ChunkCoordIntPair pair) {
         super.func_175787_b(pair);
         this.field_175791_c.add(pair);
      }

      @Override
      public void writeToNBT(NBTTagCompound tagCompound) {
         super.writeToNBT(tagCompound);
         Object object = null;
         NBTTagList nbttaglist = new NBTTagList();

         for (ChunkCoordIntPair chunkcoordintpair : this.field_175791_c) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setInteger("X", chunkcoordintpair.chunkXPos);
            nbttagcompound.setInteger("Z", chunkcoordintpair.chunkZPos);
            nbttaglist.appendTag(nbttagcompound);
         }

         tagCompound.setTag("Processed", nbttaglist);
      }

      @Override
      public void readFromNBT(NBTTagCompound tagCompound) {
         super.readFromNBT(tagCompound);
         if (tagCompound.hasKey("Processed", 9)) {
            NBTTagList nbttaglist = tagCompound.getTagList("Processed", 10);

            for (int i = 0; i < nbttaglist.tagCount(); i++) {
               NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
               this.field_175791_c.add(new ChunkCoordIntPair(nbttagcompound.getInteger("X"), nbttagcompound.getInteger("Z")));
            }
         }
      }
   }
}
