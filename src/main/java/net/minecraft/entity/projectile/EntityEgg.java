package net.minecraft.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityEgg extends EntityThrowable {
   public EntityEgg(World worldIn) {
      super(worldIn);
   }

   public EntityEgg(World worldIn, EntityLivingBase throwerIn) {
      super(worldIn, throwerIn);
   }

   public EntityEgg(World worldIn, double x, double y, double z) {
      super(worldIn, x, y, z);
   }

   @Override
   public void onImpact(MovingObjectPosition movingObjectPosition) {
      Entity entity = movingObjectPosition.entityHit;
      if (entity != null) {
         movingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.0F);
      }

      boolean flag = super.worldObj.isRemote;
      if (!flag) {
         int j = super.rand.nextInt(8);
         if (j == 0) {
            int b0 = 1;
            int k = super.rand.nextInt(32);
            if (k == 0) {
               b0 = 4;
            }

            for (int i = 0; i < b0; i++) {
               EntityChicken entitychicken = new EntityChicken(super.worldObj);
               entitychicken.setGrowingAge(-24000);
               entitychicken.setLocationAndAngles(super.posX, super.posY, super.posZ, super.rotationYaw, 0.0F);
               super.worldObj.spawnEntityInWorld(entitychicken);
            }
         }
      }

      for (int l = 0; l < 8; l++) {
         super.worldObj
            .spawnParticle(
               EnumParticleTypes.ITEM_CRACK,
               super.posX,
               super.posY,
               super.posZ,
               ((double)super.rand.nextFloat() - 0.5) * 0.08,
               ((double)super.rand.nextFloat() - 0.5) * 0.08,
               ((double)super.rand.nextFloat() - 0.5) * 0.08,
               Item.getIdFromItem(Items.egg)
            );
      }

      if (!super.worldObj.isRemote) {
         this.setDead();
      }
   }
}
