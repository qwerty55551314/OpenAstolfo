package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityAIFollowOwner extends EntityAIBase {
   private EntityTameable thePet;
   private EntityLivingBase theOwner;
   public World theWorld;
   private double followSpeed;
   private PathNavigate petPathfinder;
   private int field_75343_h;
   public float maxDist;
   public float minDist;
   private boolean field_75344_i;
   private static final String b = "Unsupported mob type for FollowOwnerGoal";
   private static final long c = 1162328002676129802L;

   public EntityAIFollowOwner(EntityTameable thePetIn, double followSpeedIn, float minDistIn, float maxDistIn) {
      this.thePet = thePetIn;
      this.theWorld = thePetIn.worldObj;
      this.followSpeed = followSpeedIn;
      this.petPathfinder = thePetIn.getNavigator();
      this.minDist = minDistIn;
      this.maxDist = maxDistIn;
      this.setMutexBits(3);
      if (!(thePetIn.getNavigator() instanceof PathNavigateGround)) {
         throw new IllegalArgumentException(b);
      }
   }

   @Override
   public boolean shouldExecute() {
      EntityLivingBase entitylivingbase = this.thePet.getOwner();
      if (entitylivingbase == null) {
         return false;
      } else {
         boolean flag = entitylivingbase instanceof EntityPlayer;
         if (flag && ((EntityPlayer)entitylivingbase).isSpectator()) {
            return false;
         } else {
            boolean flag1 = this.thePet.isSitting();
            if (flag1) {
               return false;
            } else {
               double d0;
               int i = (d0 = this.thePet.getDistanceSqToEntity(entitylivingbase) - (double)(this.minDist * this.minDist)) == 0.0 ? 0 : (d0 < 0.0 ? -1 : 1);
               if (i < 0) {
                  return false;
               } else {
                  this.theOwner = entitylivingbase;
                  return true;
               }
            }
         }
      }
   }

   @Override
   public boolean continueExecuting() {
      boolean flag = this.petPathfinder.noPath();
      if (!flag) {
         double d0;
         int i = (d0 = this.thePet.getDistanceSqToEntity(this.theOwner) - (double)(this.maxDist * this.maxDist)) == 0.0 ? 0 : (d0 < 0.0 ? -1 : 1);
         if (i > 0) {
            boolean flag1 = this.thePet.isSitting();
            if (!flag1) {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public void startExecuting() {
      this.field_75343_h = 0;
      this.field_75344_i = ((PathNavigateGround)this.thePet.getNavigator()).getAvoidsWater();
      ((PathNavigateGround)this.thePet.getNavigator()).setAvoidsWater(false);
   }

   @Override
   public void resetTask() {
      this.theOwner = null;
      this.petPathfinder.clearPathEntity();
      ((PathNavigateGround)this.thePet.getNavigator()).setAvoidsWater(true);
   }

   private boolean func_181065_a(BlockPos blockPos) {
      IBlockState iblockstate = this.theWorld.getBlockState(blockPos);
      Block block = iblockstate.getBlock();
      boolean flag1;
      if (block == Blocks.air) {
         flag1 = true;
      } else {
         boolean flag = block.isFullCube();
         flag1 = !flag;
      }

      return flag1;
   }

   @Override
   public void updateTask() {
      this.thePet.getLookHelper().setLookPositionWithEntity(this.theOwner, 10.0F, (float)this.thePet.getVerticalFaceSpeed());
      boolean flag = this.thePet.isSitting();
      if (!flag) {
         int j1 = --this.field_75343_h;
         if (j1 <= 0) {
            this.field_75343_h = (int)c;
            boolean flag1 = this.petPathfinder.tryMoveToEntityLiving(this.theOwner, this.followSpeed);
            if (!flag1) {
               boolean flag2 = this.thePet.getLeashed();
               if (!flag2) {
                  double d0;
                  int k1 = (d0 = this.thePet.getDistanceSqToEntity(this.theOwner) - 144.0) == 0.0 ? 0 : (d0 < 0.0 ? -1 : 1);
                  if (k1 >= 0) {
                     int i = MathHelper.floor_double(this.theOwner.posX) - 2;
                     int j = MathHelper.floor_double(this.theOwner.posZ) - 2;
                     int k = MathHelper.floor_double(this.theOwner.getEntityBoundingBox().minY);

                     for (int l = 0; l <= 4; l++) {
                        for (int i1 = 0; i1 <= 4; i1++) {
                           if (l < 1 || i1 < 1 || l > 3 || i1 > 3) {
                              boolean flag3 = World.doesBlockHaveSolidTopSurface(this.theWorld, new BlockPos(i + l, k - 1, j + i1));
                              if (flag3) {
                                 boolean flag4 = this.func_181065_a(new BlockPos(i + l, k, j + i1));
                                 if (flag4 && this.func_181065_a(new BlockPos(i + l, k + 1, j + i1))) {
                                    this.thePet
                                       .setLocationAndAngles(
                                          (double)((float)(i + l) + 0.5F),
                                          (double)k,
                                          (double)((float)(j + i1) + 0.5F),
                                          this.thePet.rotationYaw,
                                          this.thePet.rotationPitch
                                       );
                                    this.petPathfinder.clearPathEntity();
                                    return;
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }
}
