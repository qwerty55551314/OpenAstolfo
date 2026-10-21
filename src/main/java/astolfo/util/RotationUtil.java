package astolfo.util;

import astolfo.ClientAccess;
import com.sun.istack.internal.NotNull;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public final class RotationUtil implements ClientAccess {
   public static Random random = new Random();
   public static int keepRotationTicks;
   public static AimRotation targetRotation;
   public static AimRotation serverRotation = new AimRotation(0.0F, 0.0F);
   public static boolean rotationFlag = false;
   public static double randomOffsetX = random.nextDouble();
   public static double randomOffsetY = random.nextDouble();
   public static double randomOffsetZ = random.nextDouble();

   public static AimRotation getRotationTo(Vec3 vec3, boolean bl) {
      Vec3 vec31 = new Vec3(mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY + (double)mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
      if (bl) {
         vec31.addVector(mc.thePlayer.motionX, mc.thePlayer.motionY, mc.thePlayer.motionZ);
      }

      double d0 = vec3.xCoord - vec31.xCoord;
      double d1 = vec3.yCoord - vec31.yCoord;
      double d2 = vec3.zCoord - vec31.zCoord;
      return new AimRotation(
         MathHelper.wrapAngleTo180_float((float)Math.toDegrees(Math.atan2(d2, d0)) - 90.0F),
         MathHelper.wrapAngleTo180_float((float)(-Math.toDegrees(Math.atan2(d1, Math.sqrt(d0 * d0 + d2 * d2)))))
      );
   }

   public static Vec3 getBoxCenter(AxisAlignedBB axisAlignedBB) {
      return new Vec3(
         axisAlignedBB.minX + (axisAlignedBB.maxX - axisAlignedBB.minX) * 0.5,
         axisAlignedBB.minY + (axisAlignedBB.maxY - axisAlignedBB.minY) * 0.5,
         axisAlignedBB.minZ + (axisAlignedBB.maxZ - axisAlignedBB.minZ) * 0.5
      );
   }

   public static AimPoint findBestAimPoint(AxisAlignedBB axisAlignedBB, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
      if (bl) {
         Vec3 vec32 = new Vec3(
            axisAlignedBB.minX + (axisAlignedBB.maxX - axisAlignedBB.minX) * (randomOffsetX * 0.3 + 1.0),
            axisAlignedBB.minY + (axisAlignedBB.maxY - axisAlignedBB.minY) * (randomOffsetY * 0.3 + 1.0),
            axisAlignedBB.minZ + (axisAlignedBB.maxZ - axisAlignedBB.minZ) * (randomOffsetZ * 0.3 + 1.0)
         );
         return new AimPoint(vec32, getRotationTo(vec32, bl3));
      } else {
         Vec3 vec3 = new Vec3(
            axisAlignedBB.minX + (axisAlignedBB.maxX - axisAlignedBB.minX) * randomOffsetX * 0.8,
            axisAlignedBB.minY + (axisAlignedBB.maxY - axisAlignedBB.minY) * randomOffsetY * 0.8,
            axisAlignedBB.minZ + (axisAlignedBB.maxZ - axisAlignedBB.minZ) * randomOffsetZ * 0.8
         );
         AimRotation aimrotation = getRotationTo(vec3, bl3);
         AimPoint aimpoint = null;

         for (double d0 = 0.15; d0 < 0.85; d0 += 0.1) {
            for (double d1 = 0.15; d1 < 1.0; d1 += 0.1) {
               for (double d2 = 0.15; d2 < 0.85; d2 += 0.1) {
                  Vec3 vec31 = new Vec3(
                     axisAlignedBB.minX + (axisAlignedBB.maxX - axisAlignedBB.minX) * d0,
                     axisAlignedBB.minY + (axisAlignedBB.maxY - axisAlignedBB.minY) * d1,
                     axisAlignedBB.minZ + (axisAlignedBB.maxZ - axisAlignedBB.minZ) * d2
                  );
                  AimRotation aimrotation1 = getRotationTo(vec31, bl3);
                  if (bl4 || canSeePoint(vec31)) {
                     AimPoint aimpoint1 = new AimPoint(vec31, aimrotation1);
                     if (aimpoint == null
                        || (
                           bl2
                              ? getRotationDifference(aimpoint1.getRotation(), aimrotation) < getRotationDifference(aimpoint.getRotation(), aimrotation)
                              : getDifferenceToServerRotation(aimpoint1.getRotation()) < getDifferenceToServerRotation(aimpoint.getRotation())
                        )) {
                        aimpoint = aimpoint1;
                     }
                  }
               }
            }
         }

         return aimpoint;
      }
   }

   public static double getRotationDifferenceToEntity(Entity entity) {
      AimRotation aimrotation = getRotationTo(getBoxCenter(entity.getEntityBoundingBox()), true);
      return getRotationDifference(aimrotation, new AimRotation(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch));
   }

   public static double getDifferenceToServerRotation(AimRotation aimRotation) {
      return serverRotation == null ? 0.0 : getRotationDifference(aimRotation, serverRotation);
   }

   public static double getRotationDifference(AimRotation aimRotation, AimRotation aimRotation2) {
      return Math.hypot((double)getAngleDifference(aimRotation.getYaw(), aimRotation2.getYaw()), (double)(aimRotation.getPitch() - aimRotation2.getPitch()));
   }

   @NotNull
   public static AimRotation limitRotationStep(AimRotation aimRotation, AimRotation aimRotation2, float f) {
      float f1 = getAngleDifference(aimRotation2.getYaw(), aimRotation.getYaw());
      float f1x = getAngleDifference(aimRotation2.getPitch(), aimRotation.getPitch());
      return new AimRotation(aimRotation.getYaw() + (f1 > f ? f : Math.max(f1, -f)), aimRotation.getPitch() + (f1x > f ? f : Math.max(f1x, -f)));
   }

   private static float getAngleDifference(float f, float g) {
      return ((f - g) % 360.0F + 540.0F) % 360.0F - 180.0F;
   }

   public static Vec3 getLookVector(AimRotation aimRotation) {
      float f = MathHelper.cos(-aimRotation.getYaw() * (float) (Math.PI / 180.0) - (float) Math.PI);
      float f1 = MathHelper.sin(-aimRotation.getYaw() * (float) (Math.PI / 180.0) - (float) Math.PI);
      float f2 = -MathHelper.cos(-aimRotation.getPitch() * (float) (Math.PI / 180.0));
      float f3 = MathHelper.sin(-aimRotation.getPitch() * (float) (Math.PI / 180.0));
      return new Vec3((double)(f1 * f2), (double)f3, (double)(f * f2));
   }

   public static boolean isFaced(Entity entity, double d) {
      return RaycastUtil.raycastEntity(d, entity2 -> entity2 == entity) != null;
   }

   public static boolean canSeePoint(Vec3 vec3) {
      Vec3 vec31 = new Vec3(mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY + (double)mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
      return mc.theWorld.rayTraceBlocks(vec31, vec3) == null;
   }

   public static void setTargetRotation(AimRotation aimRotation, boolean bl) {
      setTargetRotationFor(aimRotation, 0, bl);
   }

   public static void setTargetRotationFor(AimRotation aimRotation, int i, boolean bl) {
      boolean flag = Double.isNaN((double)aimRotation.getYaw());
      if (!flag) {
         boolean flag1 = Double.isNaN((double)aimRotation.getPitch());
         if (!flag1) {
            float f;
            int i1 = (f = aimRotation.getPitch() - 90.0F) == 0.0F ? 0 : (f < 0.0F ? -1 : 1);
            if (i1 <= 0) {
               float f1;
               int j = (f1 = aimRotation.getPitch() - -90.0F) == 0.0F ? 0 : (f1 < 0.0F ? -1 : 1);
               if (j >= 0) {
                  if (bl) {
                     aimRotation.applyGcdFix(mc.gameSettings.mouseSensitivity);
                  }

                  targetRotation = aimRotation;
                  keepRotationTicks = i;
                  return;
               }
            }
         }
      }
   }

   public static void resetTargetRotation() {
      keepRotationTicks = 0;
      targetRotation = null;
   }
}
