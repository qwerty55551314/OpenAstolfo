package net.minecraft.entity.monster;

import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAICreeperSwell;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityCreeper extends EntityMob {
   private int lastActiveTime;
   private int timeSinceIgnited;
   private int fuseTime = 30;
   private int explosionRadius = 3;
   private int field_175494_bm = 0;

   public EntityCreeper(World worldIn) {
      super(worldIn);
      super.tasks.addTask(1, new EntityAISwimming(this));
      super.tasks.addTask(2, new EntityAICreeperSwell(this));
      super.tasks.addTask(3, new EntityAIAvoidEntity<>(this, EntityOcelot.class, 6.0F, 1.0, 1.2));
      super.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0, false));
      super.tasks.addTask(5, new EntityAIWander(this, 0.8));
      super.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      super.tasks.addTask(6, new EntityAILookIdle(this));
      super.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
      super.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
   }

   @Override
   public void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
   }

   @Override
   public int getMaxFallHeight() {
      return this.getAttackTarget() == null ? 3 : 3 + (int)(this.getHealth() - 1.0F);
   }

   @Override
   public void fall(float distance, float damageMultiplier) {
      super.fall(distance, damageMultiplier);
      this.timeSinceIgnited = (int)((float)this.timeSinceIgnited + distance * 1.5F);
      if (this.timeSinceIgnited > this.fuseTime - 5) {
         this.timeSinceIgnited = this.fuseTime - 5;
      }
   }

   @Override
   public void entityInit() {
      super.entityInit();
      this.dataWatcher.addObject(16, (byte)-1);
      this.dataWatcher.addObject(17, (byte)0);
      this.dataWatcher.addObject(18, (byte)0);
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound tagCompound) {
      super.writeEntityToNBT(tagCompound);
      if (this.dataWatcher.getWatchableObjectByte(17) == 1) {
         tagCompound.setBoolean("powered", true);
      }

      tagCompound.setShort("Fuse", (short)this.fuseTime);
      tagCompound.setByte("ExplosionRadius", (byte)this.explosionRadius);
      tagCompound.setBoolean("ignited", this.hasIgnited());
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound tagCompund) {
      super.readEntityFromNBT(tagCompund);
      DataWatcher datawatcher1 = this.dataWatcher;
      boolean flag = tagCompund.getBoolean("powered");
      DataWatcher datawatcher = datawatcher1;
      datawatcher.updateObject(17, (byte)(flag ? 1 : 0));
      boolean flag1 = tagCompund.hasKey("Fuse", 99);
      if (flag1) {
         this.fuseTime = tagCompund.getShort("Fuse");
      }

      boolean flag2 = tagCompund.hasKey("ExplosionRadius", 99);
      if (flag2) {
         this.explosionRadius = tagCompund.getByte("ExplosionRadius");
      }

      if (tagCompund.getBoolean("ignited")) {
         this.ignite();
      }
   }

   @Override
   public void onUpdate() {
      if (this.isEntityAlive()) {
         this.lastActiveTime = this.timeSinceIgnited;
         if (this.hasIgnited()) {
            this.setCreeperState(1);
         }

         int i = this.getCreeperState();
         if (i > 0 && this.timeSinceIgnited == 0) {
            this.playSound("creeper.primed", 1.0F, 0.5F);
         }

         this.timeSinceIgnited += i;
         if (this.timeSinceIgnited < 0) {
            this.timeSinceIgnited = 0;
         }

         if (this.timeSinceIgnited >= this.fuseTime) {
            this.timeSinceIgnited = this.fuseTime;
            this.explode();
         }
      }

      super.onUpdate();
   }

   @Override
   public String getHurtSound() {
      return "mob.creeper.say";
   }

   @Override
   public String getDeathSound() {
      return "mob.creeper.death";
   }

   @Override
   public void onDeath(DamageSource cause) {
      super.onDeath(cause);
      boolean flag = cause.getEntity() instanceof EntitySkeleton;
      if (flag) {
         int i = Item.getIdFromItem(Items.record_13);
         int j = Item.getIdFromItem(Items.record_wait);
         int k = i + this.rand.nextInt(j - i + 1);
         this.dropItem(Item.getItemById(k), 1);
      } else if (cause.getEntity() instanceof EntityCreeper) {
         Entity entity = cause.getEntity();
         if (entity != this) {
            boolean flag1 = ((EntityCreeper)cause.getEntity()).getPowered();
            if (flag1 && ((EntityCreeper)cause.getEntity()).isAIEnabled()) {
               ((EntityCreeper)cause.getEntity()).func_175493_co();
               this.entityDropItem(new ItemStack(Items.skull, 1, 4), 0.0F);
            }
         }
      }
   }

   @Override
   public boolean attackEntityAsMob(Entity entityIn) {
      return true;
   }

   public boolean getPowered() {
      return this.dataWatcher.getWatchableObjectByte(17) == 1;
   }

   public float getCreeperFlashIntensity(float f) {
      return ((float)this.lastActiveTime + (float)(this.timeSinceIgnited - this.lastActiveTime) * f) / (float)(this.fuseTime - 2);
   }

   @Override
   public Item getDropItem() {
      return Items.gunpowder;
   }

   public int getCreeperState() {
      return this.dataWatcher.getWatchableObjectByte(16);
   }

   public void setCreeperState(int state) {
      this.dataWatcher.updateObject(16, (byte)state);
   }

   @Override
   public void onStruckByLightning(EntityLightningBolt lightningBolt) {
      super.onStruckByLightning(lightningBolt);
      this.dataWatcher.updateObject(17, (byte)1);
   }

   @Override
   public boolean interact(EntityPlayer player) {
      ItemStack itemstack = player.inventory.getCurrentItem();
      if (itemstack != null && itemstack.getItem() == Items.flint_and_steel) {
         this.worldObj.playSoundEffect(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5, "fire.ignite", 1.0F, this.rand.nextFloat() * 0.4F + 0.8F);
         player.swingItem();
         boolean flag = this.worldObj.isRemote;
         if (!flag) {
            this.ignite();
            itemstack.damageItem(1, player);
            return true;
         }
      }

      return super.interact(player);
   }

   private void explode() {
      if (!this.worldObj.isRemote) {
         boolean flag = this.worldObj.getGameRules().getBoolean("mobGriefing");
         float f = this.getPowered() ? 2.0F : 1.0F;
         this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, (float)this.explosionRadius * f, flag);
         this.setDead();
      }
   }

   public boolean hasIgnited() {
      return this.dataWatcher.getWatchableObjectByte(18) != 0;
   }

   public void ignite() {
      this.dataWatcher.updateObject(18, (byte)1);
   }

   public boolean isAIEnabled() {
      int i = this.field_175494_bm;
      if (i < 1) {
         boolean flag = this.worldObj.getGameRules().getBoolean("doMobLoot");
         if (flag) {
            return true;
         }
      }

      return false;
   }

   public void func_175493_co() {
      this.field_175494_bm++;
   }
}
