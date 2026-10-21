package net.minecraft.entity.projectile;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityWitherSkull extends EntityFireball {
   private static final String gb = "mobGriefing";

   public EntityWitherSkull(World worldIn) {
      super(worldIn);
      this.setSize(0.3125F, 0.3125F);
   }

   public EntityWitherSkull(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
      super(worldIn, shooter, accelX, accelY, accelZ);
      this.setSize(0.3125F, 0.3125F);
   }

   @Override
   public float getMotionFactor() {
      return this.isInvulnerable() ? 0.73F : super.getMotionFactor();
   }

   public EntityWitherSkull(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
      super(worldIn, x, y, z, accelX, accelY, accelZ);
      this.setSize(0.3125F, 0.3125F);
   }

   @Override
   public boolean isBurning() {
      return false;
   }

   @Override
   public float getExplosionResistance(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn) {
      float f = super.getExplosionResistance(explosionIn, worldIn, pos, blockStateIn);
      Block block = blockStateIn.getBlock();
      if (this.isInvulnerable() && EntityWither.canDestroyBlock(block)) {
         f = Math.min(0.8F, f);
      }

      return f;
   }

   @Override
   public void onImpact(MovingObjectPosition movingObject) {
      if (!super.worldObj.isRemote) {
         if (movingObject.entityHit != null) {
            EntityLivingBase entitylivingbase = super.shootingEntity;
            if (entitylivingbase != null) {
               if (movingObject.entityHit.attackEntityFrom(DamageSource.causeMobDamage(super.shootingEntity), 8.0F)) {
                  if (!movingObject.entityHit.isEntityAlive()) {
                     super.shootingEntity.heal(5.0F);
                  } else {
                     this.applyEnchantments(super.shootingEntity, movingObject.entityHit);
                  }
               }
            } else {
               movingObject.entityHit.attackEntityFrom(DamageSource.magic, 5.0F);
            }

            boolean flag = movingObject.entityHit instanceof EntityLivingBase;
            if (flag) {
               int b0 = 0;
               EnumDifficulty enumdifficulty2 = super.worldObj.getDifficulty();
               EnumDifficulty enumdifficulty1 = EnumDifficulty.NORMAL;
               EnumDifficulty enumdifficulty = enumdifficulty2;
               if (enumdifficulty == enumdifficulty1) {
                  b0 = 10;
               } else if (super.worldObj.getDifficulty() == EnumDifficulty.HARD) {
                  b0 = 40;
               }

               if (b0 > 0) {
                  ((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(Potion.wither.id, 20 * b0, 1));
               }
            }
         }

         super.worldObj.newExplosion(this, super.posX, super.posY, super.posZ, 1.0F, false, super.worldObj.getGameRules().getBoolean(gb));
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

   @Override
   public void entityInit() {
      super.dataWatcher.addObject(10, (byte)0);
   }

   public boolean isInvulnerable() {
      return super.dataWatcher.getWatchableObjectByte(10) == 1;
   }

   public void setInvulnerable(boolean invulnerable) {
      super.dataWatcher.updateObject(10, (byte)(invulnerable ? 1 : 0));
   }
}
