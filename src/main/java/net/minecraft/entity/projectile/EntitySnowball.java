package net.minecraft.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntitySnowball extends EntityThrowable {
   private static final long gb = 5204993041214996488L;

   public EntitySnowball(World worldIn) {
      super(worldIn);
   }

   public EntitySnowball(World worldIn, EntityLivingBase throwerIn) {
      super(worldIn, throwerIn);
   }

   public EntitySnowball(World worldIn, double x, double y, double z) {
      super(worldIn, x, y, z);
   }

   @Override
   public void onImpact(MovingObjectPosition movingObjectPosition) {
      if (movingObjectPosition.entityHit != null) {
         int b0 = 0;
         boolean flag = movingObjectPosition.entityHit instanceof EntityBlaze;
         if (flag) {
            b0 = 3;
         }

         movingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float)b0);
      }

      for (int i = 0; i < (int)gb; i++) {
         super.worldObj.spawnParticle(EnumParticleTypes.SNOWBALL, super.posX, super.posY, super.posZ, 0.0, 0.0, 0.0);
      }

      if (!super.worldObj.isRemote) {
         this.setDead();
      }
   }
}
