package net.minecraft.entity.passive;

import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIOcelotAttack;
import net.minecraft.entity.ai.EntityAIOcelotSit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityOcelot extends EntityTameable {
   private EntityAIAvoidEntity<EntityPlayer> avoidEntity;
   private EntityAITempt aiTempt;

   public EntityOcelot(World worldIn) {
      super(worldIn);
      this.setSize(0.6F, 0.7F);
      ((PathNavigateGround)this.getNavigator()).setAvoidsWater(true);
      this.tasks.addTask(1, new EntityAISwimming(this));
      this.tasks.addTask(2, super.aiSit);
      this.tasks.addTask(3, this.aiTempt = new EntityAITempt(this, 0.6, Items.fish, true));
      this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0, 10.0F, 5.0F));
      this.tasks.addTask(6, new EntityAIOcelotSit(this, 0.8));
      this.tasks.addTask(7, new EntityAILeapAtTarget(this, 0.3F));
      this.tasks.addTask(8, new EntityAIOcelotAttack(this));
      this.tasks.addTask(9, new EntityAIMate(this, 0.8));
      this.tasks.addTask(10, new EntityAIWander(this, 0.8));
      this.tasks.addTask(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
      this.targetTasks.addTask(1, new EntityAITargetNonTamed<>(this, EntityChicken.class, false, (Predicate<? super EntityChicken>)null));
   }

   @Override
   public void entityInit() {
      super.entityInit();
      this.dataWatcher.addObject(18, (byte)0);
   }

   @Override
   public void updateAITasks() {
      if (this.getMoveHelper().isUpdating()) {
         double d0 = this.getMoveHelper().getSpeed();
         if (d0 == 0.6) {
            this.setSneaking(true);
            this.setSprinting(false);
         } else if (d0 == 1.33) {
            this.setSneaking(false);
            this.setSprinting(true);
         } else {
            this.setSneaking(false);
            this.setSprinting(false);
         }
      } else {
         this.setSneaking(false);
         this.setSprinting(false);
      }
   }

   @Override
   public boolean canDespawn() {
      boolean flag = this.isTamed();
      if (!flag) {
         int i = this.ticksExisted;
         if (i > 2400) {
            return true;
         }
      }

      return false;
   }

   @Override
   public void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3F);
   }

   @Override
   public void fall(float distance, float damageMultiplier) {
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound tagCompound) {
      super.writeEntityToNBT(tagCompound);
      tagCompound.setInteger("CatType", this.getTameSkin());
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound tagCompund) {
      super.readEntityFromNBT(tagCompund);
      this.setTameSkin(tagCompund.getInteger("CatType"));
   }

   @Override
   public String getLivingSound() {
      boolean flag = this.isTamed();
      String s;
      if (flag) {
         boolean flag1 = this.isInLove();
         s = flag1 ? "mob.cat.purr" : (this.rand.nextInt(4) == 0 ? "mob.cat.purreow" : "mob.cat.meow");
      } else {
         s = "";
      }

      return s;
   }

   @Override
   public String getHurtSound() {
      return "mob.cat.hitt";
   }

   @Override
   public String getDeathSound() {
      return "mob.cat.hitt";
   }

   @Override
   public float getSoundVolume() {
      return 0.4F;
   }

   @Override
   public Item getDropItem() {
      return Items.leather;
   }

   @Override
   public boolean attackEntityAsMob(Entity entityIn) {
      return entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0F);
   }

   @Override
   public boolean attackEntityFrom(DamageSource source, float amount) {
      if (this.isEntityInvulnerable(source)) {
         return false;
      } else {
         super.aiSit.setSitting(false);
         return super.attackEntityFrom(source, amount);
      }
   }

   @Override
   public void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
   }

   @Override
   public boolean interact(EntityPlayer player) {
      ItemStack itemstack = player.inventory.getCurrentItem();
      boolean flag = this.isTamed();
      if (flag) {
         if (this.isOwner(player) && !this.worldObj.isRemote && !this.isBreedingItem(itemstack)) {
            super.aiSit.setSitting(!this.isSitting());
         }
      } else {
         boolean flag1 = this.aiTempt.isRunning();
         if (flag1 && itemstack != null && itemstack.getItem() == Items.fish && player.getDistanceSqToEntity(this) < 9.0) {
            if (!player.capabilities.isCreativeMode) {
               itemstack.stackSize--;
            }

            if (itemstack.stackSize <= 0) {
               player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
            }

            if (!this.worldObj.isRemote) {
               if (this.rand.nextInt(3) == 0) {
                  this.setTamed(true);
                  this.setTameSkin(1 + this.worldObj.rand.nextInt(3));
                  this.setOwnerId(player.getUniqueID().toString());
                  this.playTameEffect(true);
                  super.aiSit.setSitting(true);
                  this.worldObj.setEntityState(this, (byte)7);
               } else {
                  this.playTameEffect(false);
                  this.worldObj.setEntityState(this, (byte)6);
               }
            }

            return true;
         }
      }

      return super.interact(player);
   }

   public EntityOcelot createChild(EntityAgeable ageable) {
      int b0 = 54;
      EntityOcelot entityocelot = new EntityOcelot(this.worldObj);
      if (this.isTamed()) {
         entityocelot.setOwnerId(this.getOwnerId());
         entityocelot.setTamed(true);
         entityocelot.setTameSkin(this.getTameSkin());
      }

      return entityocelot;
   }

   @Override
   public boolean isBreedingItem(ItemStack stack) {
      return stack != null && stack.getItem() == Items.fish;
   }

   @Override
   public boolean canMateWith(EntityAnimal otherAnimal) {
      if (otherAnimal == this) {
         return false;
      } else {
         boolean flag = this.isTamed();
         if (!flag) {
            return false;
         } else if (!(otherAnimal instanceof EntityOcelot)) {
            return false;
         } else {
            EntityOcelot entityocelot = (EntityOcelot)otherAnimal;
            boolean flag1 = entityocelot.isTamed();
            boolean flag4;
            if (!flag1) {
               flag4 = false;
            } else {
               boolean flag2 = this.isInLove();
               if (flag2) {
                  boolean flag3 = entityocelot.isInLove();
                  if (flag3) {
                     return true;
                  }
               }

               flag4 = false;
            }

            return flag4;
         }
      }
   }

   public int getTameSkin() {
      return this.dataWatcher.getWatchableObjectByte(18);
   }

   public void setTameSkin(int skinId) {
      this.dataWatcher.updateObject(18, (byte)skinId);
   }

   @Override
   public boolean getCanSpawnHere() {
      return this.worldObj.rand.nextInt(3) != 0;
   }

   @Override
   public boolean isNotColliding() {
      if (this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), this)
         && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty()
         && !this.worldObj.isAnyLiquid(this.getEntityBoundingBox())) {
         BlockPos blockpos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);
         if (blockpos.getY() < this.worldObj.getSeaLevel()) {
            return false;
         }

         Block block = this.worldObj.getBlockState(blockpos.down()).getBlock();
         if (block == Blocks.grass || block.getMaterial() == Material.leaves) {
            return true;
         }
      }

      return false;
   }

   @Override
   public String getName() {
      return this.hasCustomName() ? this.getCustomNameTag() : (this.isTamed() ? StatCollector.translateToLocal("entity.Cat.name") : super.getName());
   }

   @Override
   public void setTamed(boolean tamed) {
      super.setTamed(tamed);
   }

   @Override
   public void setupTamedAI() {
      if (this.avoidEntity == null) {
         this.avoidEntity = new EntityAIAvoidEntity<>(this, EntityPlayer.class, 16.0F, 0.8, 1.33);
      }

      this.tasks.removeTask(this.avoidEntity);
      if (!this.isTamed()) {
         this.tasks.addTask(4, this.avoidEntity);
      }
   }

   @Override
   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
      livingdata = super.onInitialSpawn(difficulty, livingdata);
      if (this.worldObj.rand.nextInt(7) == 0) {
         for (int i = 0; i < 2; i++) {
            EntityOcelot entityocelot = new EntityOcelot(this.worldObj);
            entityocelot.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
            entityocelot.setGrowingAge(-24000);
            this.worldObj.spawnEntityInWorld(entityocelot);
         }
      }

      return livingdata;
   }
}
