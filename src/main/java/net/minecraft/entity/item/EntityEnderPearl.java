package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityEnderPearl extends EntityThrowable {
   public EntityLivingBase field_181555_c;
   private static final String gb = "doMobSpawning";
   private static final long hb = 2154663317195456544L;

   public EntityEnderPearl(World worldIn) {
      super(worldIn);
   }

   public EntityEnderPearl(World worldIn, EntityLivingBase entityLivingBase) {
      super(worldIn, entityLivingBase);
      this.field_181555_c = entityLivingBase;
   }

   public EntityEnderPearl(World worldIn, double x, double y, double z) {
      super(worldIn, x, y, z);
   }

   @Override
   public void onImpact(MovingObjectPosition movingObjectPosition) {
      EntityLivingBase entitylivingbase = this.getThrower();
      Entity entity = movingObjectPosition.entityHit;
      if (entity != null) {
         Entity entity1 = movingObjectPosition.entityHit;
         if (entity1 == this.field_181555_c) {
            return;
         }

         movingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, entitylivingbase), 0.0F);
      }

      for (int i = 0; i < (int)hb; i++) {
         super.worldObj
            .spawnParticle(
               EnumParticleTypes.PORTAL,
               super.posX,
               super.posY + super.rand.nextDouble() * 2.0,
               super.posZ,
               super.rand.nextGaussian(),
               0.0,
               super.rand.nextGaussian()
            );
      }

      boolean flag = super.worldObj.isRemote;
      if (!flag) {
         if (entitylivingbase instanceof EntityPlayerMP) {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)entitylivingbase;
            if (entityplayermp.playerNetServerHandler.getNetworkManager().isChannelOpen() && entityplayermp.worldObj == super.worldObj) {
               boolean flag1 = entityplayermp.isPlayerSleeping();
               if (!flag1) {
                  float f;
                  int j = (f = super.rand.nextFloat() - 0.05F) == 0.0F ? 0 : (f < 0.0F ? -1 : 1);
                  if (j < 0 && super.worldObj.getGameRules().getBoolean(gb)) {
                     EntityEndermite entityendermite = new EntityEndermite(super.worldObj);
                     entityendermite.setSpawnedByPlayer(true);
                     entityendermite.setLocationAndAngles(
                        entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, entitylivingbase.rotationYaw, entitylivingbase.rotationPitch
                     );
                     super.worldObj.spawnEntityInWorld(entityendermite);
                  }

                  boolean flag2 = entitylivingbase.isRiding();
                  if (flag2) {
                     entitylivingbase.mountEntity((Entity)null);
                  }

                  entitylivingbase.setPositionAndUpdate(super.posX, super.posY, super.posZ);
                  entitylivingbase.fallDistance = 0.0F;
                  entitylivingbase.attackEntityFrom(DamageSource.fall, 5.0F);
               }
            }
         } else if (entitylivingbase != null) {
            entitylivingbase.setPositionAndUpdate(super.posX, super.posY, super.posZ);
            entitylivingbase.fallDistance = 0.0F;
         }

         this.setDead();
      }
   }

   @Override
   public void onUpdate() {
      EntityLivingBase entitylivingbase = this.getThrower();
      if (entitylivingbase != null) {
         boolean flag = entitylivingbase instanceof EntityPlayer;
         if (flag && !entitylivingbase.isEntityAlive()) {
            this.setDead();
            return;
         }
      }

      super.onUpdate();
   }
}
