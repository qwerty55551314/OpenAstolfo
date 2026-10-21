package net.minecraft.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityLargeFireball extends EntityFireball {
   public int explosionPower = 1;

   public EntityLargeFireball(World worldIn) {
      super(worldIn);
   }

   public EntityLargeFireball(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
      super(worldIn, x, y, z, accelX, accelY, accelZ);
   }

   public EntityLargeFireball(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
      super(worldIn, shooter, accelX, accelY, accelZ);
   }

   @Override
   public void onImpact(MovingObjectPosition movingObject) {
      if (!super.worldObj.isRemote) {
         Entity entity = movingObject.entityHit;
         if (entity != null) {
            movingObject.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, super.shootingEntity), 6.0F);
            this.applyEnchantments(super.shootingEntity, movingObject.entityHit);
         }

         boolean flag = super.worldObj.getGameRules().getBoolean("mobGriefing");
         super.worldObj.newExplosion((Entity)null, super.posX, super.posY, super.posZ, (float)this.explosionPower, flag, flag);
         this.setDead();
      }
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound tagCompound) {
      super.writeEntityToNBT(tagCompound);
      tagCompound.setInteger("ExplosionPower", this.explosionPower);
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound tagCompund) {
      super.readEntityFromNBT(tagCompund);
      if (tagCompund.hasKey("ExplosionPower", 99)) {
         this.explosionPower = tagCompund.getInteger("ExplosionPower");
      }
   }
}
