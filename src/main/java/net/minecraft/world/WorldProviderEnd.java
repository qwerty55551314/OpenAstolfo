package net.minecraft.world;

import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderEnd;

public class WorldProviderEnd extends WorldProvider {
   @Override
   public void registerWorldChunkManager() {
      super.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.sky, 0.0F);
      super.dimensionId = 1;
      super.hasNoSky = true;
   }

   @Override
   public IChunkProvider createChunkGenerator() {
      return new ChunkProviderEnd(super.worldObj, super.worldObj.getSeed());
   }

   @Override
   public float calculateCelestialAngle(long worldTime, float partialTicks) {
      return 0.0F;
   }

   @Override
   public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks) {
      return null;
   }

   @Override
   public Vec3 getFogColor(float f, float g) {
      float f1 = MathHelper.cos(f * (float) Math.PI * 2.0F) * 2.0F + 0.5F;
      f1 = MathHelper.clamp_float(f1, 0.0F, 1.0F);
      float f1x = (float)(10518688 >> 16 & 0xFF) / 255.0F;
      float f2 = (float)(10518688 >> 8 & 0xFF) / 255.0F;
      float f3 = (float)(10518688 & 0xFF) / 255.0F;
      f1x *= f1 * 0.0F + 0.15F;
      f2 *= f1 * 0.0F + 0.15F;
      f3 *= f1 * 0.0F + 0.15F;
      return new Vec3((double)f1x, (double)f2, (double)f3);
   }

   @Override
   public boolean isSkyColored() {
      return false;
   }

   @Override
   public boolean canRespawnHere() {
      return false;
   }

   @Override
   public boolean isSurfaceWorld() {
      return false;
   }

   @Override
   public float getCloudHeight() {
      return 8.0F;
   }

   @Override
   public boolean canCoordinateBeSpawn(int x, int z) {
      return super.worldObj.getGroundAboveSeaLevel(new BlockPos(x, 0, z)).getMaterial().blocksMovement();
   }

   @Override
   public BlockPos getSpawnCoordinate() {
      return new BlockPos(100, 50, 0);
   }

   @Override
   public int getAverageGroundLevel() {
      return 50;
   }

   @Override
   public boolean doesXZShowFog(int x, int z) {
      return true;
   }

   @Override
   public String getDimensionName() {
      return "The End";
   }

   @Override
   public String getInternalNameSuffix() {
      return "_end";
   }
}
