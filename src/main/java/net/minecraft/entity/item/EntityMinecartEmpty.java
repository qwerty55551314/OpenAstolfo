package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityMinecartEmpty extends EntityMinecart {
   private static final long cb = -6097683783027261430L;

   public EntityMinecartEmpty(World worldIn) {
      super(worldIn);
   }

   public EntityMinecartEmpty(World worldIn, double x, double y, double z) {
      super(worldIn, x, y, z);
   }

   @Override
   public boolean interactFirst(EntityPlayer playerIn) {
      Entity entity = super.riddenByEntity;
      if (entity != null && super.riddenByEntity instanceof EntityPlayer && super.riddenByEntity != playerIn) {
         return true;
      } else if (super.riddenByEntity != null && super.riddenByEntity != playerIn) {
         return false;
      } else {
         boolean flag = super.worldObj.isRemote;
         if (!flag) {
            playerIn.mountEntity(this);
         }

         return true;
      }
   }

   @Override
   public void onActivatorRailPass(int x, int y, int z, boolean receivingPower) {
      if (receivingPower) {
         if (super.riddenByEntity != null) {
            super.riddenByEntity.mountEntity((Entity)null);
         }

         if (this.getRollingAmplitude() == 0) {
            this.setRollingDirection(-this.getRollingDirection());
            this.setRollingAmplitude((int)cb);
            this.setDamage(50.0F);
            this.setBeenAttacked();
         }
      }
   }

   @Override
   public EntityMinecart.EnumMinecartType getMinecartType() {
      return EntityMinecart.EnumMinecartType.RIDEABLE;
   }
}
