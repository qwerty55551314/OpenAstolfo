package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;

public class EntityEnderCrystal extends Entity {
   public int innerRotation;
   public int health;

   public EntityEnderCrystal(World worldIn) {
      super(worldIn);
      super.preventEntitySpawning = true;
      this.setSize(2.0F, 2.0F);
      this.health = 5;
      this.innerRotation = super.rand.nextInt(100000);
   }

   public EntityEnderCrystal(World worldIn, double x, double y, double z) {
      this(worldIn);
      this.setPosition(x, y, z);
   }

   @Override
   public boolean canTriggerWalking() {
      return false;
   }

   @Override
   public void entityInit() {
      super.dataWatcher.addObject(8, this.health);
   }

   @Override
   public void onUpdate() {
      super.prevPosX = super.posX;
      super.prevPosY = super.posY;
      super.prevPosZ = super.posZ;
      this.innerRotation++;
      super.dataWatcher.updateObject(8, this.health);
      int i = MathHelper.floor_double(super.posX);
      int j = MathHelper.floor_double(super.posY);
      int k = MathHelper.floor_double(super.posZ);
      World world = super.worldObj;
      if (world.provider instanceof WorldProviderEnd) {
         World world2 = super.worldObj;
         BlockPos blockpos = new BlockPos(i, j, k);
         World world1 = world2;
         if (world1.getBlockState(blockpos).getBlock() != Blocks.fire) {
            super.worldObj.setBlockState(new BlockPos(i, j, k), Blocks.fire.getDefaultState());
         }
      }
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound tagCompound) {
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound tagCompund) {
   }

   @Override
   public boolean canBeCollidedWith() {
      return true;
   }

   @Override
   public boolean attackEntityFrom(DamageSource source, float amount) {
      boolean flag = this.isEntityInvulnerable(source);
      if (flag) {
         return false;
      } else {
         boolean flag1 = super.isDead;
         if (!flag1 && !super.worldObj.isRemote) {
            this.health = 0;
            if (this.health <= 0) {
               this.setDead();
               if (!super.worldObj.isRemote) {
                  super.worldObj.createExplosion((Entity)null, super.posX, super.posY, super.posZ, 6.0F, true);
               }
            }
         }

         return true;
      }
   }
}
