package net.minecraft.client.renderer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class ActiveRenderInfo {
   private static final IntBuffer VIEWPORT = GLAllocation.createDirectIntBuffer(16);
   private static final FloatBuffer MODELVIEW = GLAllocation.createDirectFloatBuffer(16);
   private static final FloatBuffer PROJECTION = GLAllocation.createDirectFloatBuffer(16);
   private static final FloatBuffer OBJECTCOORDS = GLAllocation.createDirectFloatBuffer(3);
   private static Vec3 position = new Vec3(0.0, 0.0, 0.0);
   private static float rotationX;
   private static float rotationXZ;
   private static float rotationZ;
   private static float rotationYZ;
   private static float rotationXY;

   public static void updateRenderInfo(EntityPlayer entityplayerIn, boolean bl) {
      GlStateManager.getFloat(EntityRenderer.authMatrixParam, MODELVIEW);
      GlStateManager.getFloat(EntityRenderer.authMatrixParam + 1, PROJECTION);
      GL11.glGetInteger(2978, VIEWPORT);
      float f = (float)((VIEWPORT.get(0) + VIEWPORT.get(2)) / 2);
      float f1 = (float)((VIEWPORT.get(1) + VIEWPORT.get(3)) / 2);
      GLU.gluUnProject(f, f1, 0.0F, MODELVIEW, PROJECTION, VIEWPORT, OBJECTCOORDS);
      position = new Vec3((double)OBJECTCOORDS.get(0), (double)OBJECTCOORDS.get(1), (double)OBJECTCOORDS.get(2));
      int i = bl ? 1 : 0;
      float f2 = entityplayerIn.rotationPitch;
      float f3 = entityplayerIn.rotationYaw;
      rotationX = MathHelper.cos(f3 * (float) Math.PI / 180.0F) * (float)(1 - i * 2);
      rotationZ = MathHelper.sin(f3 * (float) Math.PI / 180.0F) * (float)(1 - i * 2);
      rotationYZ = -rotationZ * MathHelper.sin(f2 * (float) Math.PI / 180.0F) * (float)(1 - i * 2);
      rotationXY = rotationX * MathHelper.sin(f2 * (float) Math.PI / 180.0F) * (float)(1 - i * 2);
      rotationXZ = MathHelper.cos(f2 * (float) Math.PI / 180.0F);
   }

   public static Vec3 projectViewFromEntity(Entity entity, double d) {
      double d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * d;
      double d1 = entity.prevPosY + (entity.posY - entity.prevPosY) * d;
      double d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * d;
      double d3 = d0 + position.xCoord;
      double d4 = d1 + position.yCoord;
      double d5 = d2 + position.zCoord;
      return new Vec3(d3, d4, d5);
   }

   public static Block getBlockAtEntityViewpoint(World worldIn, Entity entity, float f) {
      Vec3 vec3 = projectViewFromEntity(entity, (double)f);
      int b0 = 16;
      BlockPos blockpos = new BlockPos(vec3);
      IBlockState iblockstate = worldIn.getBlockState(blockpos);
      Block block = iblockstate.getBlock();
      if (block.getMaterial().isLiquid()) {
         float f1 = 0.0F;
         if (iblockstate.getBlock() instanceof BlockLiquid) {
            f1 = BlockLiquid.getLiquidHeightPercent(iblockstate.getValue(BlockLiquid.LEVEL)) - 0.11111111F;
         }

         float f1x = (float)(blockpos.getY() + 1) - f1;
         if (vec3.yCoord >= (double)f1x) {
            block = worldIn.getBlockState(blockpos.up()).getBlock();
         }
      }

      return block;
   }

   public static Vec3 getPosition() {
      return position;
   }

   public static float getRotationX() {
      return rotationX;
   }

   public static float getRotationXZ() {
      return rotationXZ;
   }

   public static float getRotationZ() {
      return rotationZ;
   }

   public static float getRotationYZ() {
      return rotationYZ;
   }

   public static float getRotationXY() {
      return rotationXY;
   }
}
