package net.minecraft.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntitySmallFireball extends EntityFireball {
   private static final String gb = "mobGriefing";

   public EntitySmallFireball(World worldIn) {
      super(worldIn);
      this.setSize(0.3125F, 0.3125F);
   }

   public EntitySmallFireball(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
      super(worldIn, shooter, accelX, accelY, accelZ);
      this.setSize(0.3125F, 0.3125F);
   }

   public EntitySmallFireball(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
      super(worldIn, x, y, z, accelX, accelY, accelZ);
      this.setSize(0.3125F, 0.3125F);
   }

   @Override
   public void onImpact(MovingObjectPosition movingObject) {
      if (!super.worldObj.isRemote) {
         Entity entity = movingObject.entityHit;
         if (entity != null) {
            boolean flag = movingObject.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, super.shootingEntity), 5.0F);
            if (flag) {
               this.applyEnchantments(super.shootingEntity, movingObject.entityHit);
               if (!movingObject.entityHit.isImmuneToFire()) {
                  movingObject.entityHit.setFire(5);
               }
            }
         } else {
            boolean flag2 = true;
            EntityLivingBase entitylivingbase = super.shootingEntity;
            if (entitylivingbase != null) {
               boolean flag1 = super.shootingEntity instanceof EntityLiving;
               if (flag1) {
                  flag2 = super.worldObj.getGameRules().getBoolean(gb);
               }
            }

            if (flag2) {
               BlockPos blockpos = movingObject.getBlockPos().offset(movingObject.sideHit);
               if (super.worldObj.isAirBlock(blockpos)) {
                  super.worldObj.setBlockState(blockpos, Blocks.fire.getDefaultState());
               }
            }
         }

         this.setDead();
      }
   }

   @Override
   public boolean canBeCollidedWith() {
      return false;
   }

   @Override
   public boolean attackEntityFrom(DamageSource source, float amount) {
      return false;
   }
}
