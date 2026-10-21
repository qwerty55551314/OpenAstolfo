package net.minecraft.entity.monster;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public abstract class EntityMob extends EntityCreature implements IMob {
   private static String[] Wu;

   public EntityMob(World worldIn) {
      super(worldIn);
      super.experienceValue = 5;
   }

   @Override
   public void onLivingUpdate() {
      this.updateArmSwingProgress();
      float f = this.getBrightness(1.0F);
      if (f > 0.5F) {
         this.entityAge += 2;
      }

      super.onLivingUpdate();
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (!this.worldObj.isRemote && this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL) {
         this.setDead();
      }
   }

   @Override
   public String getSwimSound() {
      return "game.hostile.swim";
   }

   @Override
   public String getSplashSound() {
      return "game.hostile.swim.splash";
   }

   @Override
   public boolean attackEntityFrom(DamageSource source, float amount) {
      boolean flag = this.isEntityInvulnerable(source);
      if (flag) {
         return false;
      } else {
         boolean flag1 = super.attackEntityFrom(source, amount);
         if (!flag1) {
            return false;
         } else {
            Entity entity = source.getEntity();
            return this.riddenByEntity != entity && this.ridingEntity != entity ? true : true;
         }
      }
   }

   @Override
   public String getHurtSound() {
      return "game.hostile.hurt";
   }

   @Override
   public String getDeathSound() {
      return "game.hostile.die";
   }

   @Override
   public String getFallSoundString(int damageValue) {
      return damageValue > 4 ? "game.hostile.hurt.fall.big" : "game.hostile.hurt.fall.small";
   }

   @Override
   public boolean attackEntityAsMob(Entity entityIn) {
      float f = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
      int i = 0;
      boolean flag1 = entityIn instanceof EntityLivingBase;
      if (flag1) {
         f += EnchantmentHelper.getModifierForCreature(this.getHeldItem(), ((EntityLivingBase)entityIn).getCreatureAttribute());
         i += EnchantmentHelper.getKnockbackModifier(this);
      }

      boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), f);
      if (flag) {
         if (i > 0) {
            entityIn.addVelocity(
               (double)(-MathHelper.sin(this.rotationYaw * (float) Math.PI / 180.0F) * (float)i * 0.5F),
               0.1,
               (double)(MathHelper.cos(this.rotationYaw * (float) Math.PI / 180.0F) * (float)i * 0.5F)
            );
            this.motionX *= 0.6;
            this.motionZ *= 0.6;
         }

         int j = EnchantmentHelper.getFireAspectModifier(this);
         if (j > 0) {
            entityIn.setFire(j * 4);
         }

         this.applyEnchantments(this, entityIn);
      }

      return flag;
   }

   @Override
   public float getBlockPathWeight(BlockPos pos) {
      return 0.5F - this.worldObj.getLightBrightness(pos);
   }

   public boolean isValidLightLevel() {
      boolean flag1 = false;
      BlockPos blockpos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);
      int k = this.worldObj.getLightFor(EnumSkyBlock.SKY, blockpos);
      if (k > this.rand.nextInt(32)) {
         return false;
      } else {
         int i = this.worldObj.getLightFromNeighbors(blockpos);
         boolean flag = this.worldObj.isThundering();
         if (flag) {
            int j = this.worldObj.getSkylightSubtracted();
            this.worldObj.setSkylightSubtracted(10);
            i = this.worldObj.getLightFromNeighbors(blockpos);
            this.worldObj.setSkylightSubtracted(j);
         }

         return i <= this.rand.nextInt(8);
      }
   }

   @Override
   public boolean getCanSpawnHere() {
      if (this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL) {
         boolean flag = this.isValidLightLevel();
         if (flag) {
            boolean flag1 = super.getCanSpawnHere();
            if (flag1) {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
   }

   @Override
   public boolean canDropLoot() {
      return true;
   }

   public static void d(String[] stringArray) {
      Wu = stringArray;
   }

   static {
      d((String[])null);
   }
}
