package net.minecraft.entity.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityExpBottle extends EntityThrowable {
   private static final long gb = 4754797580020352978L;

   public EntityExpBottle(World worldIn) {
      super(worldIn);
   }

   public EntityExpBottle(World worldIn, EntityLivingBase entityLivingBase) {
      super(worldIn, entityLivingBase);
   }

   public EntityExpBottle(World worldIn, double x, double y, double z) {
      super(worldIn, x, y, z);
   }

   @Override
   public float getGravityVelocity() {
      return 0.07F;
   }

   @Override
   public float getVelocity() {
      return 0.7F;
   }

   @Override
   public float getInaccuracy() {
      return -20.0F;
   }

   @Override
   public void onImpact(MovingObjectPosition movingObjectPosition) {
      if (!super.worldObj.isRemote) {
         super.worldObj.playAuxSFX((int)gb, new BlockPos(this), 0);
         int i = 3 + super.worldObj.rand.nextInt(5) + super.worldObj.rand.nextInt(5);

         while (i > 0) {
            int j = EntityXPOrb.getXPSplit(i);
            i -= j;
            super.worldObj.spawnEntityInWorld(new EntityXPOrb(super.worldObj, super.posX, super.posY, super.posZ, j));
         }

         this.setDead();
      }
   }
}
